package com.microinfra.gateway.service;

import static com.microinfra.commons.bean.GlobalConstants.IGNITE_CACHE_USERPROFILE;
import static com.microinfra.commons.ignite.IgniteConfigurer.TRANSIENT_REGION;

import javax.annotation.PostConstruct;

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

import com.alibaba.fastjson2.JSON;
import com.google.common.base.CaseFormat;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.gateway.GatewayConfig.AppConfig;
import com.microinfra.sysiam.facade.UserService;

@Component
public class SessionManager {

	@Autowired
	private UserService userService;

	@Autowired
	private Ignite ignite;

	@Autowired
	private AppConfig appConfig;

	private IgniteCache<Long, String> userCache;

	@PostConstruct
	public void init() {
		CacheConfiguration<Long, String> userCacheCfg = new CacheConfiguration<Long, String>() //
				.setSqlSchema(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, appConfig.getName())) //
				.setName(IGNITE_CACHE_USERPROFILE) //
				.setCacheMode(CacheMode.REPLICATED) //
				.setDataRegionName(TRANSIENT_REGION) //
				.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL) //
				.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC) //
				.setBackups(1) //
				.setEagerTtl(true) //
				.setPartitionLossPolicy(PartitionLossPolicy.READ_ONLY_SAFE) //
				.setRebalanceMode(CacheRebalanceMode.ASYNC) //
				.setIndexedTypes(new Class[] { Long.class, String.class });

		this.userCache = ignite.getOrCreateCache(userCacheCfg);
	}

	public void putActiveUser(UserProfile userProfile) {
		userCache.put(userProfile.getId(), JSON.toJSONString(userProfile));
	}

	public UserProfile getActiveUser(Long userId) {
		UserProfile userProfile = null;
		String strUserProfile = userCache.get(userId);
		if (strUserProfile == null) {// 如果服务重启，缓存数据丢失，重新加载用户信息
			userProfile = loadUserProfile(userId);
			if (userProfile != null) {
				userCache.put(userId, JSON.toJSONString(userProfile));
			}
		} else {
			userProfile = JSON.parseObject(strUserProfile, UserProfile.class);
		}

		return userProfile;
	}

	// TODO通过MQ（ROCKET MQ或event bus（通过ignite client同步缓存操作））监听用户信息的变更，
	public void refreshActiveUser(UserProfile userProfile) {
		String oldUserProfile = userCache.get(userProfile.getId());
		if (oldUserProfile != null) {
			userCache.put(userProfile.getId(), JSON.toJSONString(userProfile));
		}
	}

	public void removeActiveUser(Long userId) {
		userCache.remove(userId);
	}

	public UserProfile loadUserProfile(Long userId) {
		return userService.getUserProfileByUid(userId);
	}

	public UserProfile loadUserProfileByName(String name) {
		return userService.getUserProfileByName(name);
	}

	public UserProfile loadUserProfileByMobile(String mobile) {
		return userService.getUserProfileByMobile(mobile);
	}

}