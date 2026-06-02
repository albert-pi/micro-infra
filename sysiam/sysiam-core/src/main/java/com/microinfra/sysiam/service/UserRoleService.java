package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.UserRole;
import com.microinfra.sysiam.mapper.UserRoleMapper;

/**
 * <p>
 * 用户角色分配 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

	public List<DataOption> getRoleOptionsByUser(Long userId) {
		return baseMapper.selectRoleOptionsByUser(userId);
	}

	public void removeUserRole(Long userId, Long roleId) {
		LambdaQueryWrapper<UserRole> queryWrapper = Wrappers.<UserRole>lambdaQuery();
		queryWrapper.eq(UserRole::getUserId, userId);
		queryWrapper.eq(UserRole::getRoleId, roleId);

		baseMapper.delete(queryWrapper);
	}

	public void removeUserRoles(Long userId) {
		LambdaQueryWrapper<UserRole> queryWrapper = Wrappers.<UserRole>lambdaQuery();
		queryWrapper.eq(UserRole::getUserId, userId);

		baseMapper.delete(queryWrapper);
	}

	public boolean hasRoleAssigned(Long roleId) {
		LambdaQueryWrapper<UserRole> queryWrapper = Wrappers.<UserRole>lambdaQuery();
		queryWrapper.eq(UserRole::getRoleId, roleId);

		return baseMapper.selectCount(queryWrapper) > 0 ? true : false;
	}

}