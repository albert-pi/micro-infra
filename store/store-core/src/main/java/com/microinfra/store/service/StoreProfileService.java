package com.microinfra.store.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.store.domain.StoreProfile;
import com.microinfra.store.mapper.StoreProfileMapper;

/**
 * <p>
 * 存储配置 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */

@Service
public class StoreProfileService extends ServiceImpl<StoreProfileMapper, StoreProfile> {

	public StoreProfile getStoreProfile(Long tenantId) {
		LambdaQueryWrapper<StoreProfile> queryWrapper = Wrappers.<StoreProfile>lambdaQuery();
		queryWrapper.eq(StoreProfile::getTenantId, tenantId);

		return baseMapper.selectOne(queryWrapper);
	}

}