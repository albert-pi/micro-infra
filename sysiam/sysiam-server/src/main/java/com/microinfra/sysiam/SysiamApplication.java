package com.microinfra.sysiam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.microinfra")
@MapperScan("com.microinfra.**.mapper")
@EnableDiscoveryClient
@EnableFeignClients("com.microinfra")
@RefreshScope
@SpringBootApplication
public class SysiamApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysiamApplication.class, args);
	}

}
