package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.ServiceItem;
import com.microinfra.sysiam.mapper.ServiceItemMapper;

/**
 * <p>
 * 平台提供的服务项目 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class ServiceItemService extends ServiceImpl<ServiceItemMapper, ServiceItem> {

	public List<DataOption> getServiceItemOptions() {
		return baseMapper.selectServiceItemOptions();
	}

	public ServiceItem getServiceItem(String serviceId) {
		LambdaQueryWrapper<ServiceItem> queryWrapper = Wrappers.<ServiceItem>lambdaQuery();
		queryWrapper.eq(ServiceItem::getId, serviceId);

		return baseMapper.selectOne(queryWrapper);
	}

}