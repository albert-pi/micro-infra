package com.microinfra.passport;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
public class PassportConfig {

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
	public static class PassportIgniteConfig {

		private String workDir;

		private String bind = "0.0.0.0";

		private Integer port = 12800;

	}

	@Configuration
	@ConfigurationProperties(prefix = "ignite.cluster")
	@Data
	public static class PasssportIgniteClusterConfig {

		private List<String> addresses;

		private String multicastGroup;

		private Integer multicastPort;

		private Integer discPort = 49500;

		private Integer commPort = 49100;

	}

	@Configuration
	@ConfigurationProperties(prefix = "passport.kaptcha")
	@Data
	public static class KaptchaConfig {

		private Integer validity = 120;

		// TODO other

	}

}
