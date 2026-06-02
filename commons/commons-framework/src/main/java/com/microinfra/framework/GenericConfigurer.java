package com.microinfra.framework;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericConfigurer {

	@ConditionalOnMissingBean
	@Bean
	public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistration() {
		FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistration = new FilterRegistrationBean<>();

		characterEncodingFilterRegistration.setName("characterEncodingFilterRegistration");
		characterEncodingFilterRegistration.setOrder(1);
		characterEncodingFilterRegistration.addUrlPatterns("/*");
		characterEncodingFilterRegistration.setFilter(new CharacterEncodingFilter());

		return characterEncodingFilterRegistration;
	}

}
