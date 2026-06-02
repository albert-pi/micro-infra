package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.sysiam.entity.ActionInfo;
import com.microinfra.sysiam.mapper.ActionInfoMapper;

/**
 * <p>
 * 系统应用提供的功能接口 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class ActionInfoService extends ServiceImpl<ActionInfoMapper, ActionInfo> {

	public List<ActionInfo> getActionsInApp(String appCode) {
		LambdaQueryWrapper<ActionInfo> queryWrapper = Wrappers.<ActionInfo>lambdaQuery();
		queryWrapper.eq(ActionInfo::getAppCode, appCode);

		return baseMapper.selectList(queryWrapper);
	}

}