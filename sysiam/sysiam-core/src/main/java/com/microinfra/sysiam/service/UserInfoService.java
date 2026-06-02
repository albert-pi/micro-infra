package com.microinfra.sysiam.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.AdminFlag;
import com.microinfra.commons.bean.AvailableStatus;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.commons.bean.MenuOption;
import com.microinfra.sysiam.dto.QueryUserConditions;
import com.microinfra.sysiam.entity.UserInfo;
import com.microinfra.sysiam.mapper.UserInfoMapper;
import com.microinfra.sysiam.vo.UserRecord;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfo> {

	public UserInfo getUserInfo(Long userId) {
		LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.<UserInfo>lambdaQuery();
		queryWrapper.eq(UserInfo::getId, userId);
		queryWrapper.eq(UserInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public UserInfo getAdministratorOfTenant(Long tenantId) {
		LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.<UserInfo>lambdaQuery();
		queryWrapper.eq(UserInfo::getTenantId, tenantId);
		queryWrapper.eq(UserInfo::getAdminFlag, AdminFlag.YES.value());
		queryWrapper.eq(UserInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public UserInfo getUserInfoByName(String name) {
		LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.<UserInfo>lambdaQuery();
		queryWrapper.eq(UserInfo::getName, name);
		queryWrapper.eq(UserInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public UserInfo getUserInfoByMobile(String mobile) {
		LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.<UserInfo>lambdaQuery();
		queryWrapper.eq(UserInfo::getMobile, mobile);
		queryWrapper.eq(UserInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public List<UserRecord> queryUserRecords(QueryUserConditions queryConditions) {
		return baseMapper.selectUserRecordsByParams(queryConditions);
	}

	public void updateLastLoginState(String name, String loginIp, Date loginTime) {
		LambdaUpdateWrapper<UserInfo> updateWrapper = Wrappers.<UserInfo>lambdaUpdate();
		updateWrapper.set(UserInfo::getLoginIp, loginIp);
		updateWrapper.set(UserInfo::getLoginTime, loginTime);
		updateWrapper.eq(UserInfo::getName, name);

		baseMapper.update(null, updateWrapper);
	}

	public void removeUser(Long userId) {
		LambdaUpdateWrapper<UserInfo> updateWrapper = Wrappers.<UserInfo>lambdaUpdate();
		updateWrapper.set(UserInfo::getDeleteStatus, DeleteStatus.DELETED.value());
		updateWrapper.eq(UserInfo::getId, userId);

		baseMapper.update(null, updateWrapper);
	}

	public void disableUser(Long userId) {
		LambdaUpdateWrapper<UserInfo> updateWrapper = Wrappers.<UserInfo>lambdaUpdate();
		updateWrapper.set(UserInfo::getStatus, AvailableStatus.DISABLED.value());
		updateWrapper.eq(UserInfo::getId, userId);

		baseMapper.update(null, updateWrapper);
	}

	public void enableUser(Long userId) {
		LambdaUpdateWrapper<UserInfo> updateWrapper = Wrappers.<UserInfo>lambdaUpdate();
		updateWrapper.set(UserInfo::getStatus, AvailableStatus.NORMAL.value());
		updateWrapper.eq(UserInfo::getId, userId);

		baseMapper.update(null, updateWrapper);
	}

	public List<MenuOption> getAuthorizedMenuOptions(Long userId) {
		return baseMapper.selectMenuOptionsByUserId(userId);
	}

}