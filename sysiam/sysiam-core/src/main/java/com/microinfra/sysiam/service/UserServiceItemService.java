package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.ServiceItemOption;
import com.microinfra.sysiam.entity.UserServiceItem;
import com.microinfra.sysiam.mapper.UserServiceItemMapper;

/**
 * <p>
 * 授权给用户使用的服务项目 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class UserServiceItemService extends ServiceImpl<UserServiceItemMapper, UserServiceItem> {

	public List<ServiceItemOption> getAuthorizedServiceItemOptions(Long userId) {
		return baseMapper.selectServiceItemOptionsByUserId(userId);
	}

	public boolean hasAllServiceItems(Long userId) {
		LambdaQueryWrapper<UserServiceItem> queryWrapper = Wrappers.<UserServiceItem>lambdaQuery();
		queryWrapper.eq(UserServiceItem::getUserId, userId);
		queryWrapper.eq(UserServiceItem::getServiceItemId, 0);
		queryWrapper.select(UserServiceItem::getId);

		List<UserServiceItem> authorizedServiceItems = baseMapper.selectList(queryWrapper);
		return !CollectionUtils.isEmpty(authorizedServiceItems) ? true : false;
	}

	public void removeAuthorizedServiceItem(Long userId, Long serviceItemId) {
		LambdaQueryWrapper<UserServiceItem> queryWrapper = Wrappers.<UserServiceItem>lambdaQuery();
		queryWrapper.eq(UserServiceItem::getUserId, userId);
		queryWrapper.eq(UserServiceItem::getServiceItemId, serviceItemId);

		baseMapper.delete(queryWrapper);
	}

	public void removeAuthorizedServiceItems(Long userId) {
		LambdaQueryWrapper<UserServiceItem> queryWrapper = Wrappers.<UserServiceItem>lambdaQuery();
		queryWrapper.eq(UserServiceItem::getUserId, userId);

		baseMapper.delete(queryWrapper);
	}

}