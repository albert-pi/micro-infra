package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.UserOrg;
import com.microinfra.sysiam.mapper.UserOrgMapper;

/**
 * <p>
 * 用户和组织机构关系 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class UserOrgService extends ServiceImpl<UserOrgMapper, UserOrg> {

	public List<DataOption> getOrgOptionsByUser(Long userId) {
		return baseMapper.selectOrgOptionsByUserId(userId);
	}

	public void removeUserOrgs(Long userId) {
		LambdaQueryWrapper<UserOrg> queryWrapper = Wrappers.<UserOrg>lambdaQuery();
		queryWrapper.eq(UserOrg::getUserId, userId);

		baseMapper.delete(queryWrapper);
	}

}