package com.microinfra.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.microinfra.auth.UserInterceptor;
import com.microinfra.framework.BaseMvcConfigurer;

/**
 * 管理中心MVC配置
 *
 * @author albert pi
 * @Since 1.0.0
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AppMvcConfigurer extends BaseMvcConfigurer {

	@Autowired
	private UserInterceptor userInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);

		registry.addInterceptor(userInterceptor).addPathPatterns("/**").excludePathPatterns("/**/static/**");
	}

}
