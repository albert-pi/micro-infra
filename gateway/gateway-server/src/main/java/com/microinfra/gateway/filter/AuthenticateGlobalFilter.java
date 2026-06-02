package com.microinfra.gateway.filter;

import static com.microinfra.commons.bean.GlobalConstants.HTTP_HEADER_X_USER_ID;
import static com.microinfra.commons.bean.GlobalConstants.INTERNAL_SERVICE_API_URL_PATTERN;
import static com.microinfra.commons.bean.GlobalConstants.SERVICE_UNAVAILABLE_PROMPTS;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Strings;
import com.microinfra.commons.bean.AvailableStatus;
import com.microinfra.commons.bean.TokenInfo;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.gateway.GatewayConfig.AuthConfig;
import com.microinfra.gateway.GatewayConfig.TokenConfig;
import com.microinfra.gateway.service.SessionManager;
import com.microinfra.gateway.service.TokenManager;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

/**
 * <p>
 * 认证检查Filter
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */

@Slf4j
@Component
public class AuthenticateGlobalFilter implements GlobalFilter, Ordered {

	private final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

	private final List<String> AUTHENTICATE_EXCLUSION_PATHS = new ArrayList<>();

	private final String TOKEN_HEADER_NAME = "Authorization";

	@Autowired
	private AuthConfig authConfig;

	@Autowired
	private TokenConfig tokenConfig;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		AUTHENTICATE_EXCLUSION_PATHS.add("/**/doc.html");
		AUTHENTICATE_EXCLUSION_PATHS.add("/**/v2/api-docs");
		AUTHENTICATE_EXCLUSION_PATHS.add("/**/swagger-resources/**");
		AUTHENTICATE_EXCLUSION_PATHS.add("/**/webjars/**");
		AUTHENTICATE_EXCLUSION_PATHS.add("/**/static/**");
		AUTHENTICATE_EXCLUSION_PATHS.add("/**/login");

		AUTHENTICATE_EXCLUSION_PATHS.add(INTERNAL_SERVICE_API_URL_PATTERN);

		if (!CollectionUtils.isEmpty(authConfig.getExclusions())) {
			AUTHENTICATE_EXCLUSION_PATHS.addAll(authConfig.getExclusions());
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		String tokenHeader = request.getHeaders().getFirst(TOKEN_HEADER_NAME);
		String path = request.getPath().toString();
		for (String exclusionPattern : AUTHENTICATE_EXCLUSION_PATHS) {
			if (ANT_PATH_MATCHER.match(exclusionPattern, path)) {
				if (!Strings.isNullOrEmpty(tokenHeader)) {
					request.getHeaders().remove(TOKEN_HEADER_NAME);
				}

				return chain.filter(exchange);
			}
		}

		if (Strings.isNullOrEmpty(tokenHeader)) {
			// TODO HttpStatus.FOUND 重定向
//			response.setStatusCode(HttpStatus.FOUND);
//			response.getHeaders().set(HttpHeaders.LOCATION, authConfig.getLoginUrl());

			response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			byte[] errorBytes = getBytesFromError(HttpStatus.UNAUTHORIZED.value(), "未登录");

			return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(errorBytes))));
		}

		try {
			Long userId = null;
			byte[] errorBytes = null;
			String token = tokenHeader.replace("Bearer", "").trim();
			Map<String, Object> claims = tokenManager.parse(token, tokenConfig.getSecret());
			if (claims == null) {
//				response.setStatusCode(HttpStatus.FOUND);
//				response.getHeaders().set(HttpHeaders.LOCATION, authenticateConfig.getLoginUrl());

				response.setStatusCode(HttpStatus.BAD_REQUEST);
				errorBytes = getBytesFromError(HttpStatus.BAD_REQUEST.value(), "Token错误，请重新登录");
			} else {
				userId = Long.valueOf((String) claims.get("userId"));
				TokenInfo tokenInfo = tokenManager.getTokenInfo(userId);
				if (tokenInfo == null) {
					sessionManager.removeActiveUser(userId);

//					response.setStatusCode(HttpStatus.FOUND);
//					response.getHeaders().set(HttpHeaders.LOCATION, authenticateConfig.getLoginUrl());
					response.setStatusCode(HttpStatus.UNAUTHORIZED);
					errorBytes = getBytesFromError(HttpStatus.UNAUTHORIZED.value(), "会话过期，请重新登录");
				} else {
					UserProfile theUser = sessionManager.getActiveUser(userId);
					if (theUser == null) {
						response.setStatusCode(HttpStatus.NOT_FOUND);
						errorBytes = getBytesFromError(HttpStatus.NOT_FOUND.value(), "账号不存在");
					} else if (theUser.getStatus().intValue() == AvailableStatus.DISABLED.value()) {
						response.setStatusCode(HttpStatus.FORBIDDEN);
						errorBytes = getBytesFromError(HttpStatus.FORBIDDEN.value(), "账号被禁用");
					}
				}
			}

			if (errorBytes != null) {
				response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
				return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(errorBytes))));
			}

			ServerHttpRequest newRequest = request.mutate().header(HTTP_HEADER_X_USER_ID, userId.toString()).build();
			return chain.filter(exchange.mutate().request(newRequest).build());
		} catch (Exception e) {
			log.error("", e);

			response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
			byte[] errorBytes = getBytesFromError(HttpStatus.SERVICE_UNAVAILABLE.value(), SERVICE_UNAVAILABLE_PROMPTS);

			return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(errorBytes))));
		}
	}

	private byte[] getBytesFromError(int errCode, String errMsg) {
		JSONObject respObject = new JSONObject();
		respObject.put("code", errCode);
		respObject.put("msg", errMsg);
		respObject.put("timestamp", System.currentTimeMillis());

		return respObject.toJSONString().getBytes(StandardCharsets.UTF_8);
	}

}
