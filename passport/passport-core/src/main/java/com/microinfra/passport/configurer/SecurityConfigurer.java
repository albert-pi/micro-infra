package com.microinfra.passport.configurer;

import static com.microinfra.commons.bean.GlobalConstants.HTTP_HEADER_X_USER_ID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microinfra.commons.bean.LoginData;
import com.microinfra.commons.bean.Result;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.framework.ServletHelper;
import com.microinfra.passport.security.CustomAuthenticationFilter;
import com.microinfra.passport.security.CustomAuthenticationProvider;
import com.microinfra.sysiam.facade.LogService;
import com.microinfra.sysiam.facade.UserService;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security配置
 *
 * @author albert pi
 * @Since 1.0.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(1)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Resource
	private UserService userService;

	@Resource
	private LogService logService;

	@Resource
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
		AuthenticationManager authenticationManager = new ProviderManager(authenticationProvider);
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager, authenticationProvider, logService);

		httpSecurity.csrf().disable() //
				.formLogin().disable() //
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and() //
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //
				.authorizeRequests() //
				.antMatchers("/**/actuator/**", "/**/doc.html", "/**/v2/api-docs", "/**/swagger-resources/**", "/**/webjars/**").anonymous() //
				.antMatchers("/**/static/**", "/**/captcha", "/**/login").anonymous() //
				.anyRequest().authenticated().and() //
				.headers().frameOptions().disable();

		httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler((request, response, authentication) -> {
			String userId = request.getHeader(HTTP_HEADER_X_USER_ID);
			UserProfile userProfile = userService.getUserProfileByUid(Long.valueOf(userId));

			try {
				String clientIp = ServletHelper.getClientIp(request);
				UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
				String clientName = userAgent.getBrowser().getName();
				Integer clientType = 1;
				String os = userAgent.getOs().getName();

				LoginData loginData = new LoginData();
				loginData.setName(userProfile.getName());
				loginData.setUserType(userProfile.getType());
				loginData.setClientIp(clientIp);
				loginData.setClientType(clientType);
				loginData.setClientName(clientName);
				loginData.setOs(os);
				loginData.setType(2);
				loginData.setStatus(0);
				logService.saveLoginLog(loginData);
			} catch (Exception e) {
				log.error("", e);
			}

			ServletHelper.outputJSON(response, Result.<Void>builder().build());
		});

		httpSecurity.addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
