package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.UserTenant;
import com.microinfra.sysiam.mapper.UserTenantMapper;

/**
 * <p>
 * 授权给用户管理的运营商 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class UserTenantService extends ServiceImpl<UserTenantMapper, UserTenant> {

	public List<DataOption> getAuthorizedTenantOptions(Long userId) {
		return baseMapper.selectTenantOptionsByUserId(userId);
	}

	public boolean hasAllTenants(Long userId) {
		LambdaQueryWrapper<UserTenant> queryWrapper = Wrappers.<UserTenant>lambdaQuery();
		queryWrapper.eq(UserTenant::getUserId, userId);
		queryWrapper.eq(UserTenant::getTenantId, 0);
		queryWrapper.select(UserTenant::getId);

		List<UserTenant> authorizedTenants = baseMapper.selectList(queryWrapper);
		return !CollectionUtils.isEmpty(authorizedTenants) ? true : false;
	}

	public void removeAuthorizedTenant(Long userId, Long tenantId) {
		LambdaQueryWrapper<UserTenant> queryWrapper = Wrappers.<UserTenant>lambdaQuery();
		queryWrapper.eq(UserTenant::getUserId, userId);
		queryWrapper.eq(UserTenant::getTenantId, tenantId);

		baseMapper.delete(queryWrapper);
	}

	public void removeAuthorizedTenants(Long userId) {
		LambdaQueryWrapper<UserTenant> queryWrapper = Wrappers.<UserTenant>lambdaQuery();
		queryWrapper.eq(UserTenant::getUserId, userId);

		baseMapper.delete(queryWrapper);
	}

}