package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.sysiam.dto.QueryRoleConditions;
import com.microinfra.sysiam.entity.RoleInfo;
import com.microinfra.sysiam.mapper.RoleInfoMapper;
import com.microinfra.sysiam.vo.RoleRecord;

/**
 * <p>
 * 角色信息 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class RoleInfoService extends ServiceImpl<RoleInfoMapper, RoleInfo> {

	public RoleInfo getRoleInfo(Long roleId) {
		LambdaQueryWrapper<RoleInfo> queryWrapper = Wrappers.<RoleInfo>lambdaQuery();
		queryWrapper.eq(RoleInfo::getId, roleId);
		queryWrapper.eq(RoleInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public RoleInfo getRoleInfoInPlatform(String name) {
		LambdaQueryWrapper<RoleInfo> queryWrapper = Wrappers.<RoleInfo>lambdaQuery();
		queryWrapper.eq(RoleInfo::getType, 1);
		queryWrapper.eq(RoleInfo::getName, name);
		queryWrapper.eq(RoleInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public RoleInfo getRoleInfoInTenant(Long tenantId, String name) {
		LambdaQueryWrapper<RoleInfo> queryWrapper = Wrappers.<RoleInfo>lambdaQuery();
		queryWrapper.eq(RoleInfo::getType, 2);
		queryWrapper.eq(RoleInfo::getTenantId, tenantId);
		queryWrapper.eq(RoleInfo::getName, name);
		queryWrapper.eq(RoleInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public List<DataOption> getRoleOptionsInPlatform() {
		return baseMapper.selectRoleOptionsInPlatform();
	}

	public List<DataOption> getRoleOptionsInTenant(Long tenantId) {
		return baseMapper.selectRoleOptionsInTenant(tenantId);
	}

	public List<RoleRecord> queryRoleRecords(QueryRoleConditions queryConditions) {
		return baseMapper.selectRoleRecordsByParams(queryConditions);
	}

	public void removeRoleInfo(Long roleId) {
		LambdaUpdateWrapper<RoleInfo> updateWrapper = Wrappers.<RoleInfo>lambdaUpdate();
		updateWrapper.set(RoleInfo::getDeleteStatus, DeleteStatus.DELETED.value());
		updateWrapper.eq(RoleInfo::getId, roleId);

		baseMapper.update(null, updateWrapper);
	}

}