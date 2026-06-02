package com.microinfra.commons.ignite;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "ignite")
@Data
public class IgniteConfig {

	@Data
	public static class IgniteCluster {

		private List<String> addresses;

		private String multicastGroup;

		private Integer multicastPort;

		private Integer discPort = 49500;

		private Integer commPort = 49100;

	};

	private String workDir;

	private String bind = "0.0.0.0";

	private Integer port = 12800;

	private IgniteCluster cluster;

}
