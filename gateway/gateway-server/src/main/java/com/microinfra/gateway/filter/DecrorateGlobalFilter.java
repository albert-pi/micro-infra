package com.microinfra.gateway.filter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.gateway.GatewayConfig.AuthConfig;
import com.microinfra.gateway.GatewayConfig.TokenConfig;
import com.microinfra.gateway.service.SessionManager;
import com.microinfra.gateway.service.TokenManager;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DecrorateGlobalFilter implements GlobalFilter, Ordered {

	private final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

	private final Joiner joiner = Joiner.on("");

	@Autowired
	private AuthConfig authConfig;

	@Autowired
	private TokenConfig tokenConfig;

	@Resource
	public SessionManager sessionManager;

	@Resource
	public TokenManager tokenManager;

	@Override
	public int getOrder() {
		return -2;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getPath().toString();

		if (ANT_PATH_MATCHER.match("/**/static/login", path) || ANT_PATH_MATCHER.match("/**/static/logout", path)) {
			return chain.filter(exchange);
		} else if (ANT_PATH_MATCHER.match("/**/login", path) || ANT_PATH_MATCHER.match("/**/logout", path)) {
			return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
				ServerHttpRequestDecorator requestDecorator = new ServerHttpRequestDecorator(exchange.getRequest()) {

					@Override
					public Flux<DataBuffer> getBody() {
						if (ANT_PATH_MATCHER.match("/**/login", exchange.getRequest().getPath().toString())) {
							byte[] content = new byte[dataBuffer.readableByteCount()];
							dataBuffer.read(content);
							DataBufferUtils.release(dataBuffer);

							JSONObject requestObject = JSONObject.parseObject(new String(content, StandardCharsets.UTF_8));
							if (requestObject != null) {
								String rememberMe = requestObject.getString("rememberMe");
								if ("true".equalsIgnoreCase(rememberMe) || "on".equalsIgnoreCase(rememberMe) || "yes".equalsIgnoreCase(rememberMe)
										|| "1".equals(rememberMe)) {
									exchange.getAttributes().put("rememberme-flag", Boolean.TRUE);
								}
							}

							return Flux.just(new DefaultDataBufferFactory().wrap(content));
						} else {
							return super.getBody();
						}
					}

				};

				ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {

					@Override
					public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
						if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
							String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
							if (!Strings.isNullOrEmpty(originalResponseContentType) && originalResponseContentType.contains("application/json")) {
								return super.writeWith(Flux.from(body).buffer().map(dataBuffers -> {
									List<String> list = Lists.newArrayList();
									dataBuffers.forEach(dataBuffer -> {
										try {
											byte[] content = new byte[dataBuffer.readableByteCount()];
											dataBuffer.read(content);
											DataBufferUtils.release(dataBuffer);
											list.add(new String(content, StandardCharsets.UTF_8));
										} catch (Exception e) {
											log.info("", e);
										}
									});
									String resp = joiner.join(list);
									if (ANT_PATH_MATCHER.match("/**/login", exchange.getRequest().getPath().toString())) {
										try {
											JSONObject resultObject = JSONObject.parseObject(resp);
											JSONObject dataObject = resultObject.getJSONObject("data");

											UserProfile userProfile = dataObject.getObject("user", UserProfile.class);
											sessionManager.putActiveUser(userProfile);

											Integer validity = ((Boolean) exchange.getAttribute("rememberme-flag")).booleanValue()
													? tokenConfig.getRemembermeValidity()
													: tokenConfig.getValidity();
											String token = tokenManager.generate(userProfile.getId(), validity, tokenConfig.getSecret());
											dataObject.put("token", token);
											resp = resultObject.toJSONString();
										} catch (Exception e) {
											log.info("", e);
										}
									} else if (ANT_PATH_MATCHER.match("/**/logout", exchange.getRequest().getPath().toString())) {
										exchange.getResponse().setStatusCode(HttpStatus.FOUND);
										exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, authConfig.getLoginUrl());
									}

									byte[] finalContent = new String(resp.getBytes(), StandardCharsets.UTF_8).getBytes();
									exchange.getResponse().getHeaders().setContentLength(finalContent.length);

									return exchange.getResponse().bufferFactory().wrap(finalContent);
								}));
							}
						}

						return super.writeWith(body);
					}

					@Override
					public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
						return writeWith(Flux.from(body).flatMapSequential(p -> p));
					}

				};

				return chain.filter(exchange.mutate().request(requestDecorator).response(responseDecorator).build());
			});
		} else {
			return chain.filter(exchange);
		}
	}

}