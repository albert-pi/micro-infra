package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.TenantServiceItem;
import com.microinfra.sysiam.mapper.TenantServiceItemMapper;

/**
 * <p>
 * 授权给运营商使用的服务项目 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class TenantServiceItemService extends ServiceImpl<TenantServiceItemMapper, TenantServiceItem> {

	public List<DataOption> getAuthorizedServiceOptions(Long tenantId) {
		return baseMapper.selectServiceOptionsByTenant(tenantId);
	}

	public void removeAuthorizedService(Long tenantId, String serviceId) {
		LambdaQueryWrapper<TenantServiceItem> queryWrapper = Wrappers.<TenantServiceItem>lambdaQuery();
		queryWrapper.eq(TenantServiceItem::getTenantId, tenantId);
		queryWrapper.eq(TenantServiceItem::getServiceId, serviceId);

		baseMapper.delete(queryWrapper);
	}

	public void removeAllAuthorizedServices(Long tenantId) {
		LambdaQueryWrapper<TenantServiceItem> queryWrapper = Wrappers.<TenantServiceItem>lambdaQuery();
		queryWrapper.eq(TenantServiceItem::getTenantId, tenantId);

		baseMapper.delete(queryWrapper);
	}

}