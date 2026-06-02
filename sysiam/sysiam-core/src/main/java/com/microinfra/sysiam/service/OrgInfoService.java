package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.sysiam.dto.QueryOrgConditions;
import com.microinfra.sysiam.entity.OrgInfo;
import com.microinfra.sysiam.mapper.OrgInfoMapper;
import com.microinfra.sysiam.vo.OrgRecord;

/**
 * <p>
 * 组织机构信息 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class OrgInfoService extends ServiceImpl<OrgInfoMapper, OrgInfo> {

	public List<TreeNode> getFlatOrgsInPlatform() {
		return baseMapper.selectOrgAsTreeNodeInPlatform();
	}

	public List<TreeNode> getFlatOrgsInTenant(Long tenantId) {
		return baseMapper.selectOrgAsTreeNodeInTenant(tenantId);
	}

	public List<OrgRecord> queryOrgRecords(QueryOrgConditions queryConditions) {
		return baseMapper.selectOrgRecordsByParams(queryConditions);
	}

	public OrgInfo getOrgInfo(Long orgId) {
		LambdaQueryWrapper<OrgInfo> queryWrapper = Wrappers.<OrgInfo>lambdaQuery();
		queryWrapper.eq(OrgInfo::getId, orgId);
		queryWrapper.eq(OrgInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public OrgInfo getOrgInfoInPlatform(String name) {
		LambdaQueryWrapper<OrgInfo> queryWrapper = Wrappers.<OrgInfo>lambdaQuery();
		queryWrapper.eq(OrgInfo::getName, name);
		queryWrapper.eq(OrgInfo::getType, 1);
		queryWrapper.eq(OrgInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public OrgInfo getOrgInfoInTenant(Long tenantId, String name) {
		LambdaQueryWrapper<OrgInfo> queryWrapper = Wrappers.<OrgInfo>lambdaQuery();
		queryWrapper.eq(OrgInfo::getName, name);
		queryWrapper.eq(OrgInfo::getTenantId, tenantId);
		queryWrapper.eq(OrgInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public void removeOrgInfo(Long orgId) {
		LambdaUpdateWrapper<OrgInfo> updateWrapper = Wrappers.<OrgInfo>lambdaUpdate();
		updateWrapper.set(OrgInfo::getDeleteStatus, DeleteStatus.DELETED.value());
		updateWrapper.eq(OrgInfo::getId, orgId);

		baseMapper.update(null, updateWrapper);
	}

}