package com.microinfra.commons.ignite;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.ClientConnectorConfiguration;
import org.apache.ignite.configuration.ConnectorConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import com.google.common.base.CaseFormat;

/**
 * 配置Ignite
 *
 * @author albert pi
 * @Since 1.0.0
 */
@Configuration
public class IgniteConfigurer {

	public static final String DEFAULT_REGION = DataStorageConfiguration.DFLT_DATA_REG_DEFAULT_NAME;

	public static final String TRANSIENT_REGION = "transient_region";

	public static final Long TRANSIENT_REGION_INITIAL_SIZE = 256L * 1024 * 1024;

	@Value("${spring.application.name:}")
	private String appName;

	@Value("${spring.application.node-id:}")
	private Integer nodeId;

	@Resource
	private IgniteConfig igniteConfig;

	@Bean
	public Ignite startIgnite() {
		IgniteConfiguration igniteConfiguration = buildIgniteConfiguration();
		Ignite ignite = Ignition.start(igniteConfiguration);
		if (!ignite.cluster().state().active()) {
			ignite.cluster().state(ClusterState.ACTIVE);
		}

		return ignite;
	}

	private IgniteConfiguration buildIgniteConfiguration() {
		System.getProperties().put("java.net.preferIPv4Stack", "true"); // using IPv4
		System.getProperties().put("IGNITE_NO_ASCII", "true");
		System.getProperties().put("IGNITE_QUIET", "false");
		System.getProperties().put("IGNITE_TO_STRING_INCLUDE_SENSITIVE", "false");
		System.getProperties().put("IGNITE_UPDATE_NOTIFIER", "false");

		/**
		 * Configure data storage
		 */
		DataRegionConfiguration defaultRegion = new DataRegionConfiguration();
		defaultRegion.setName(DEFAULT_REGION);
		defaultRegion.setInitialSize(DataStorageConfiguration.DFLT_DATA_REGION_INITIAL_SIZE);
		defaultRegion.setMaxSize(DataStorageConfiguration.DFLT_DATA_REGION_MAX_SIZE);// 20% of total memory
		defaultRegion.setPersistenceEnabled(true);

		DataRegionConfiguration transientRegion = new DataRegionConfiguration();
		transientRegion.setName(TRANSIENT_REGION);
		transientRegion.setInitialSize(TRANSIENT_REGION_INITIAL_SIZE);
		transientRegion.setMaxSize(DataStorageConfiguration.DFLT_DATA_REGION_MAX_SIZE);
		transientRegion.setPersistenceEnabled(false);

		DataStorageConfiguration dataStorageCfg = new DataStorageConfiguration();
		dataStorageCfg.setDefaultDataRegionConfiguration(defaultRegion);
		dataStorageCfg.setDataRegionConfigurations(transientRegion);
		dataStorageCfg.setWalSegmentSize(128 * 1024 * 1024);

		String theAppName = Optional.ofNullable(appName).orElseThrow(() -> new RuntimeException("spring.application.name not configured"));
		Integer theNodeId = Optional.ofNullable(nodeId).orElseThrow(() -> new RuntimeException("spring.application.node-id not configured"));
		String instanceName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, theAppName) + "_instance_" + theNodeId;
		String consistentId = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, theAppName) + "_node_" + theNodeId;

		String appHome = System.getProperty("app.home");
		String bindAddress = Optional.ofNullable(igniteConfig.getBind()).filter(bind -> !bind.trim().isEmpty()).orElse("0.0.0.0");
		Integer clientPort = Optional.ofNullable(igniteConfig.getPort()).orElse(12800);
		String workDir = Optional.ofNullable(igniteConfig.getWorkDir()).filter(dir -> !dir.trim().isEmpty()).orElse(appHome.concat(File.separator).concat("work"));

		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		igniteCfg.setIgniteInstanceName(instanceName);
		igniteCfg.setConsistentId(consistentId);
		igniteCfg.setLocalHost(bindAddress);
		igniteCfg.setIgniteHome(appHome);
		igniteCfg.setWorkDirectory(Paths.get(workDir).toAbsolutePath().toString());
		igniteCfg.setDataStorageConfiguration(dataStorageCfg);
		igniteCfg.setConnectorConfiguration(new ConnectorConfiguration().setHost(bindAddress));
		igniteCfg.setGridLogger(new Slf4jLogger());
		igniteCfg.setClientMode(false);
		igniteCfg.setMetricsLogFrequency(0L);

		/**
		 * Configure TCP/IP discovery and communication
		 */
		TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
		if (!ObjectUtils.isEmpty(igniteConfig.getCluster()) && !ObjectUtils.isEmpty(igniteConfig.getCluster().getAddresses())) {// static IP finder
			TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
			ipFinder.setAddresses(igniteConfig.getCluster().getAddresses());
			discoverySpi.setIpFinder(ipFinder);
		} else {// multicast IP finder
			TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
			String multicastGroup = igniteConfig.getCluster().getMulticastGroup();
			Integer multicastPort = igniteConfig.getCluster().getMulticastPort();
			if (multicastGroup != null) {
				ipFinder.setMulticastGroup(multicastGroup);
			}
			if (multicastPort != null) {
				ipFinder.setMulticastPort(multicastPort);
			}

			discoverySpi.setIpFinder(ipFinder);
		}
		discoverySpi.setLocalPort(igniteConfig.getCluster().getDiscPort());
		discoverySpi.setLocalPortRange(10);
		igniteCfg.setDiscoverySpi(discoverySpi);

		TcpCommunicationSpi commSpi = new TcpCommunicationSpi();
		commSpi.setLocalPort(igniteConfig.getCluster().getCommPort());
		commSpi.setLocalPortRange(10);
		igniteCfg.setCommunicationSpi(commSpi);

//		igniteCfg.setPeerClassLoadingEnabled(true);
//		igniteCfg.setDeploymentMode(DeploymentMode.CONTINUOUS);

		ClientConnectorConfiguration clientConnectorCfg = new ClientConnectorConfiguration();
//		ThinClientConfiguration thinClientCfg = new ThinClientConfiguration().setMaxActiveComputeTasksPerConnection(100);
//		clientConnectorCfg.setThinClientConfiguration(thinClientCfg);
		clientConnectorCfg.setThinClientEnabled(true);
		clientConnectorCfg.setPort(clientPort);
		clientConnectorCfg.setPortRange(10);
		igniteCfg.setClientConnectorConfiguration(clientConnectorCfg);

		igniteCfg.setClientConnectorConfiguration(null);

		return igniteCfg;
	}

}
