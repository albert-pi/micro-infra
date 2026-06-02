package com.microinfra.store;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
public class StoreConfig {

	@Configuration
	@ConfigurationProperties(prefix = "store")
	@Data
	public static class ProviderConfigs {

		private Map<String, Map<String, String>> providers;

	}

}
