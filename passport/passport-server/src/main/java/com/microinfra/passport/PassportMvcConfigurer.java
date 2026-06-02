package com.microinfra.passport;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

import com.microinfra.framework.BaseMvcConfigurer;

/**
 * 认证中心MVC配置
 *
 * @author albert pi
 * @Since 1.0.0
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class PassportMvcConfigurer extends BaseMvcConfigurer {

}
