package com.microinfra.auth;

import static com.microinfra.commons.bean.GlobalConstants.HTTP_HEADER_X_USER_ID;
import static com.microinfra.commons.bean.GlobalConstants.IGNITE_CACHE_USERPROFILE;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientAddressFinder;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.SysException;
import com.microinfra.framework.UserContextHolder;
import com.microinfra.framework.UserProfileEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor, ApplicationListener<UserProfileEvent> {

	@Resource
	private AuthConfig authConfig;

	private static final Cache<Long, UserProfile> localUserProfileCache = Caffeine.newBuilder().expireAfterWrite(24 * 60 * 60, TimeUnit.SECONDS)
			.maximumSize(10_000).build();

	private volatile ClientCache<Long, String> activeUserCache;

	private IgniteClient igniteClient;

	public ClientCache<Long, String> getActiveUserCache() {
		if (this.activeUserCache == null) {
			synchronized (UserInterceptor.class) {
				if (this.activeUserCache == null) {
					if (authConfig.getIgniteServers() == null || authConfig.getIgniteServers().size() == 0) {
						throw new SysException("ignite server not configured");
					}

					try {
						ClientAddressFinder finder = () -> authConfig.getIgniteServers().toArray(new String[authConfig.getIgniteServers().size()]);
						ClientConfiguration cfg = new ClientConfiguration().setAddressesFinder(finder).setPartitionAwarenessEnabled(true)
								.setReconnectThrottlingRetries(0);
						this.igniteClient = Ignition.startClient(cfg);
						this.activeUserCache = igniteClient.cache(IGNITE_CACHE_USERPROFILE);
					} catch (Exception e) {
						log.error("", e);

						throw e;
					}
				}
			}
		}

		return this.activeUserCache;
	}

	@Override
	public void onApplicationEvent(UserProfileEvent event) {
		Long userId = event.getUserProfile().getId();

		switch (event.getEventType()) {
		case UPDATED:
			getActiveUserCache().replace(userId, JSON.toJSONString(event.getUserProfile()));
			if (localUserProfileCache.getIfPresent(userId) != null) {
				localUserProfileCache.put(userId, event.getUserProfile());
			}
			break;
		case REMOVED:
			getActiveUserCache().remove(userId);
			localUserProfileCache.invalidate(userId);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String userId = request.getHeader(HTTP_HEADER_X_USER_ID);
		if (!ObjectUtils.isEmpty(userId)) {
			UserProfile currentUser = getCurrentUser(Long.valueOf(userId));
			if (currentUser != null) {
				UserContextHolder.setCurrentUser(currentUser);

				return true;
			}
		}

		return false;
	}

	private UserProfile getCurrentUser(Long userId) {
		return localUserProfileCache.get(userId, uid -> {
			try {
				String jsonUserProfile = getActiveUserCache().get(Long.valueOf(userId));
				UserProfile userProfile = null;
				if (jsonUserProfile != null) {
					userProfile = JSON.to(UserProfile.class, jsonUserProfile);
				}

				return userProfile;
			} catch (Exception e) {
				log.error("获取当前用户信息错误：", e);

				return null;
			}
		});
	}

	@PreDestroy
	public void close() {
		if (igniteClient != null) {
			igniteClient.close();
		}
	}

}
