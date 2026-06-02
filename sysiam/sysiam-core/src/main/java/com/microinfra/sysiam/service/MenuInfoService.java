package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.sysiam.entity.MenuInfo;
import com.microinfra.sysiam.mapper.MenuInfoMapper;

/**
 * <p>
 * 服务项目中定义的菜单信息 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class MenuInfoService extends ServiceImpl<MenuInfoMapper, MenuInfo> {

	public List<MenuInfo> getMenusInServiceItem(Long serviceItemId) {
		LambdaQueryWrapper<MenuInfo> queryWrapper = Wrappers.<MenuInfo>lambdaQuery();
		queryWrapper.eq(MenuInfo::getServiceItemId, serviceItemId);
		queryWrapper.eq(MenuInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectList(queryWrapper);
	}

	public MenuInfo getMenuInServiceItemByName(Long serviceItemId, String name) {
		LambdaQueryWrapper<MenuInfo> queryWrapper = Wrappers.<MenuInfo>lambdaQuery();
		queryWrapper.eq(MenuInfo::getServiceItemId, serviceItemId);
		queryWrapper.eq(MenuInfo::getName, name);
		queryWrapper.eq(MenuInfo::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public void removeMenuInfo(Long menuId) {
		LambdaUpdateWrapper<MenuInfo> updateWrapper = Wrappers.<MenuInfo>lambdaUpdate();
		updateWrapper.set(MenuInfo::getDeleteStatus, DeleteStatus.DELETED.value());
		updateWrapper.eq(MenuInfo::getId, menuId);

		baseMapper.update(null, updateWrapper);
	}

}