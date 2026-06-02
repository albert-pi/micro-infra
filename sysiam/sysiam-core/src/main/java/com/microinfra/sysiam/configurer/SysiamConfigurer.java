package com.microinfra.sysiam.configurer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 身份和访问管理服务配置
 *
 * @author albert pi
 * @Since 1.0.0
 */
@Configuration
public class SysiamConfigurer {

	@ConditionalOnMissingBean
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
