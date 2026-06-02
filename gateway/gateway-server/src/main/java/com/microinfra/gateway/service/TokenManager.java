package com.microinfra.gateway.service;

import static com.microinfra.commons.ignite.IgniteConfigurer.DEFAULT_REGION;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.expiry.TouchedExpiryPolicy;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.google.common.base.CaseFormat;
import com.microinfra.commons.bean.TokenInfo;
import com.microinfra.gateway.GatewayConfig.AppConfig;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * <p>
 * Token管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Component
public class TokenManager {

	@Autowired
	private Ignite ignite;

	@Autowired
	private AppConfig appConfig;

	private IgniteCache<Long, TokenInfo> tokenCache;

	@PostConstruct
	public void init() {
		CacheConfiguration<Long, TokenInfo> tokenCacheCfg = new CacheConfiguration<Long, TokenInfo>() //
				.setSqlSchema(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, appConfig.getName())) //
				.setName("Token") //
				.setCacheMode(CacheMode.REPLICATED) //
				.setDataRegionName(DEFAULT_REGION) //
				.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL) //
				.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC) //
				.setBackups(1) //
				.setEagerTtl(true) //
				.setPartitionLossPolicy(PartitionLossPolicy.READ_ONLY_SAFE) //
				.setIndexedTypes(new Class[] { Long.class, TokenInfo.class }).setRebalanceMode(CacheRebalanceMode.ASYNC);
		this.tokenCache = ignite.getOrCreateCache(tokenCacheCfg);
	}

	public String generate(Long userId, Integer validity, String secret) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userId.toString());
		claims.put("timestamp", DateUtil.current());

		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secret).compact();
		String tokenHash = DigestUtils.md5DigestAsHex(token.getBytes());
		TokenInfo tokenInfo = new TokenInfo(userId, tokenHash, validity);

		ExpiryPolicy tokenExpiryPolicy = new TouchedExpiryPolicy(new Duration(SECONDS, validity));
		tokenCache.withExpiryPolicy(tokenExpiryPolicy).putAsync(userId, tokenInfo);

		return token;
	}

	public Map<String, Object> parse(String token, String secret) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		return claims;
	}

	public TokenInfo getTokenInfo(Long userId) {
		TokenInfo tokenInfo = tokenCache.get(userId);
		if (tokenInfo != null) {
			ExpiryPolicy tokenExpiryPolicy = new TouchedExpiryPolicy(new Duration(SECONDS, tokenInfo.getValidity()));
			tokenCache.withExpiryPolicy(tokenExpiryPolicy).get(userId);
		}

		return tokenInfo;
	}

	public void removeToken(Long userId) {
		tokenCache.remove(userId);
	}

}
