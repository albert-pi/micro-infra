package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.sysiam.entity.RoleMenu;
import com.microinfra.sysiam.mapper.RoleMenuMapper;

/**
 * <p>
 * 角色的菜单或功能分配 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {

	public List<TreeNode> getFlatMenusInRole(Long roleId) {
		return baseMapper.selectMenuAsTreeNodeByRole(roleId);
	}

	public void removeRoleMenus(Long roleId) {
		LambdaQueryWrapper<RoleMenu> queryWrapper = Wrappers.<RoleMenu>lambdaQuery();
		queryWrapper.eq(RoleMenu::getRoleId, roleId);

		baseMapper.delete(queryWrapper);
	}

}