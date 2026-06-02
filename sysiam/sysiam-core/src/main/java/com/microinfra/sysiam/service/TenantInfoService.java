package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.sysiam.dto.QueryTenantConditions;
import com.microinfra.sysiam.entity.TenantInfo;
import com.microinfra.sysiam.mapper.TenantInfoMapper;
import com.microinfra.sysiam.vo.TenantRecord;

/**
 * <p>
 * 运营商信息 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class TenantInfoService extends ServiceImpl<TenantInfoMapper, TenantInfo> {

	public List<DataOption> getTenantOptions() {
		return baseMapper.selectTenantOptions();
	}

	public List<TenantRecord> queryTenantRecords(QueryTenantConditions queryConditions) {
		return baseMapper.selectTenantRecordsByParams(queryConditions);
	}

	public TenantInfo getTenantInfo(Long tenantId) {
		LambdaQueryWrapper<TenantInfo> queryWrapper = Wrappers.<TenantInfo>lambdaQuery();
		queryWrapper.eq(TenantInfo::getId, tenantId);
		queryWrapper.eq(TenantInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public TenantInfo getTenantInfo(String name) {
		LambdaQueryWrapper<TenantInfo> queryWrapper = Wrappers.<TenantInfo>lambdaQuery();
		queryWrapper.eq(TenantInfo::getName, name);
		queryWrapper.eq(TenantInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public void removeTenant(Long tenantId) {
		LambdaUpdateWrapper<TenantInfo> updateWrapper = Wrappers.<TenantInfo>lambdaUpdate();
		updateWrapper.set(TenantInfo::getDeleteStatus, DeleteStatus.DELETED.value());
		updateWrapper.eq(TenantInfo::getId, tenantId);

		baseMapper.update(null, updateWrapper);
	}

}