package com.microinfra.gateway;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.microinfra.gateway.route.DynamicRoute;

import lombok.Data;

@Configuration
public class GatewayConfig {

	@Configuration
	@ConfigurationProperties(prefix = "spring.application")
	@Data
	public static class AppConfig {

		private String name;

		private Integer nodeId = 1;

	}

	@Configuration
	@ConfigurationProperties(prefix = "ignite")
	@Data
	public static class GatewayIgniteConfig {

		private String workDir;

		private String bind = "0.0.0.0";

		private Integer port = 11800;

	}

	@Configuration
	@ConfigurationProperties(prefix = "ignite.cluster")
	@Data
	public static class GatewayIgniteClusterConfig {

		private List<String> addresses;

		private String multicastGroup;

		private Integer multicastPort;

		private Integer discPort = 48500;

		private Integer commPort = 48100;

	}

	@Configuration
	@ConfigurationProperties(prefix = "gateway")
	@RefreshScope
	@Data
	public static class BaseConfig {

		private List<DynamicRoute> routes;

	}

	@Configuration
	@ConfigurationProperties(prefix = "gateway.auth")
	@Data
	public static class AuthConfig {

		private String loginUrl;

		private List<String> exclusions;

	}

	@Configuration
	@ConfigurationProperties(prefix = "gateway.token")
	@Data
	public static class TokenConfig {

		private String secret = "F949F55BBCD046FEB39D5214B2CBDD8D";

		private Integer validity = 1800;

		private Integer remembermeValidity = 1209600;

	}

}
