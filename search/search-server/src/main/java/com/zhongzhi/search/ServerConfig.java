package com.zhongzhi.search;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
public class ServerConfig {

	@Configuration
	@ConfigurationProperties(prefix = "spring.application")
	@Data
	public static class AppConfig {

		private String name;

		private Integer nodeId = 1;

	}

}
