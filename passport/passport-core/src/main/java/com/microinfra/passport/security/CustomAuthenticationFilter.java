package com.microinfra.passport.security;

import static com.microinfra.commons.bean.GlobalConstants.SERVICE_UNAVAILABLE_PROMPTS;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
import static org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices.DEFAULT_PARAMETER;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson2.JSONObject;
import com.microinfra.commons.bean.IdentityType;
import com.microinfra.commons.bean.LoginData;
import com.microinfra.commons.bean.Result;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.bean.UserType;
import com.microinfra.framework.ServletHelper;
import com.microinfra.passport.params.PassportCredentials;
import com.microinfra.sysiam.facade.LogService;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private CustomAuthenticationProvider authenticationProvider;

	private LogService logService;

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager, CustomAuthenticationProvider authenticationProvider, LogService logService) {
		super(new AntPathRequestMatcher("/**/login", "POST"));

		this.setAuthenticationManager(authenticationManager);
		this.authenticationProvider = authenticationProvider;
		this.logService = logService;

		this.setAuthenticationSuccessHandler((request, response, authentication) -> {
			UserProfile userProfile = (UserProfile) authentication.getPrincipal();

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
				loginData.setType(1);
				logService.saveLoginLog(loginData);

				Map<String, Object> result = new HashMap<>();
				result.put("user", userProfile);

				response.setStatus(HttpStatus.OK.value());
				ServletHelper.outputJSON(response, Result.success(result));
			} catch (Exception e) {
				log.error("", e);

				response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
				ServletHelper.outputJSON(response, Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), SERVICE_UNAVAILABLE_PROMPTS));
			}
		});

		this.setAuthenticationFailureHandler((request, response, authenticationException) -> {
			String error = authenticationException.getMessage();
			String userName = (String) request.getAttribute(SPRING_SECURITY_FORM_USERNAME_KEY);

			try {
				String clientIp = ServletHelper.getClientIp(request);
				UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
				String clientName = userAgent.getBrowser().getName();
				Integer clientType = 1;
				String os = userAgent.getOs().getName();

				LoginData loginData = new LoginData();
				loginData.setName(userName == null ? "" : userName);
				loginData.setUserType(UserType.UNKNOWN.value());
				loginData.setClientIp(clientIp);
				loginData.setClientType(clientType);
				loginData.setClientName(clientName);
				loginData.setOs(os);
				loginData.setType(1);
				loginData.setStatus(1);
				loginData.setError(error);
				logService.saveLoginLog(loginData);
			} catch (Exception e) {
				log.error("", e);
			}

			response.setStatus(HttpStatus.BAD_REQUEST.value());
			ServletHelper.outputJSON(response, Result.error(HttpStatus.BAD_REQUEST.value(), error));
		});
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		try {
			StringBuilder bodyContent = new StringBuilder();
			String contentOfLine = "";
			while ((contentOfLine = request.getReader().readLine()) != null) {
				bodyContent.append(contentOfLine);
			}

			if (ObjectUtils.isEmpty(bodyContent.toString())) {
				throw new AuthenticationServiceException("登录参数不能为空");
			}

			JSONObject formParams = JSONObject.parseObject(bodyContent.toString());
			Integer type = formParams.getInteger("type");
			if (type == null) {
				throw new AuthenticationServiceException("登录方式不能为空");
			}
			IdentityType identityType = IdentityType.of(type);
			if (identityType == null) {
				throw new AuthenticationServiceException("不支持的登录方式");
			}

			Authenticator<? extends PassportCredentials> authenticator = this.authenticationProvider.getAuthenticator(identityType);
			Type authenticatorGenericType = this.authenticationProvider.getAuthenticatorGenericType(identityType);
			authenticator.validate(formParams.to(authenticatorGenericType));

			PassportCredentials credentials = formParams.to(authenticatorGenericType);
			String userName = credentials.getUserName();
			String rememberMe = formParams.getString("rememberMe");

			request.setAttribute(DEFAULT_PARAMETER, rememberMe);
			request.setAttribute(SPRING_SECURITY_FORM_USERNAME_KEY, userName);

			UserProfile principal = new UserProfile();
			principal.setName(userName);
			CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(principal, credentials);
			authenticationToken.setDetails(super.authenticationDetailsSource.buildDetails(request));

			return this.getAuthenticationManager().authenticate(authenticationToken);
		} catch (IOException e) {
			log.error("", e);

			throw new AuthenticationServiceException("登录异常");
		}
	}

}