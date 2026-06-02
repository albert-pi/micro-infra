package com.microinfra.uid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
public class UidConfig {

	@Configuration
	@ConfigurationProperties(prefix = "spring.application")
	@Data
	public static class AppConfig {

		private String name;

		private Integer nodeId = 1;

	}

	@Configuration
	@ConfigurationProperties(prefix = "uid.snowflake")
	@Data
	public static class SnowflakeConfig {

		private int timestampBits = 31;

		private int workerBits = 23;

		private int sequenceBits = 9;

		private String epoch;

		private int boostPower = 3;

		private int paddingFactor = 50;

		private Long scheduleInterval;

	}

}
