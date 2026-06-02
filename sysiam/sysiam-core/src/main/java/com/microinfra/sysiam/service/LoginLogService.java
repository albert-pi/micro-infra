package com.microinfra.sysiam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.sysiam.dto.QueryLoginConditions;
import com.microinfra.sysiam.entity.LoginLog;
import com.microinfra.sysiam.mapper.LoginLogMapper;
import com.microinfra.sysiam.vo.LoginRecord;

/**
 * <p>
 * 用户登录日志 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> {

	public List<LoginRecord> queryLoginRecords(QueryLoginConditions queryLoginConditions) {
		QueryWrapper<LoginRecord> queryWrapper = Wrappers.<LoginRecord>query();
		queryWrapper.orderByDesc("log_time");

		Optional.ofNullable(queryLoginConditions.getUserType()).ifPresent(userType -> queryWrapper.ge("user_type", userType));
		Optional.ofNullable(queryLoginConditions.getTenantId()).ifPresent(tenantId -> queryWrapper.ge("tenant_id", tenantId));
		Optional.ofNullable(queryLoginConditions.getLoginStart()).ifPresent(loginStart -> queryWrapper.ge("log_time", loginStart));
		Optional.ofNullable(queryLoginConditions.getLoginEnd()).ifPresent(loginEnd -> queryWrapper.le("log_time", loginEnd));
		Optional.ofNullable(queryLoginConditions.getName()).ifPresent(name -> queryWrapper.eq("name", name));
		Optional.ofNullable(queryLoginConditions.getClientIp()).ifPresent(clientIp -> queryWrapper.eq("client_ip", clientIp));

		return baseMapper.selectLoginRecordsByParams(queryWrapper);
	}

	public void updateClientLocation(String clientIp, String location) {
		LambdaUpdateWrapper<LoginLog> updateWrapper = Wrappers.<LoginLog>lambdaUpdate();
		updateWrapper.set(LoginLog::getLocation, location);
		updateWrapper.eq(LoginLog::getClientIp, clientIp);

		baseMapper.update(null, updateWrapper);
	}

}