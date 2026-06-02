package com.microinfra.sysiam.facade;

import static com.microinfra.commons.lang.TreeUtils.CHILDREN_SETTER;
import static com.microinfra.commons.lang.TreeUtils.PARENT_CHECKER;
import static com.microinfra.commons.lang.TreeUtils.ROOT_CHECKER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.AvailableStatus;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.commons.bean.UserType;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.commons.lang.TreeUtils;
import com.microinfra.sysiam.dto.QueryRoleConditions;
import com.microinfra.sysiam.dto.RoleBasic;
import com.microinfra.sysiam.dto.ServiceMenuOption;
import com.microinfra.sysiam.entity.RoleInfo;
import com.microinfra.sysiam.entity.RoleMenu;
import com.microinfra.sysiam.exception.RoleException;
import com.microinfra.sysiam.facade.RoleService;
import com.microinfra.sysiam.service.MenuInfoService;
import com.microinfra.sysiam.service.RoleInfoService;
import com.microinfra.sysiam.service.RoleMenuService;
import com.microinfra.sysiam.service.UserRoleService;
import com.microinfra.sysiam.vo.RoleDetails;
import com.microinfra.sysiam.vo.RoleRecord;
import com.microinfra.uid.facade.UidService;

import cn.hutool.core.date.DateUtil;

/**
 * <p>
 * 角色信息及角色功能分配管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class RoleManager implements RoleService {

	@Resource
	private RoleInfoService roleInfoService;

	@Resource
	private MenuInfoService menuInfoService;

	@Resource
	private RoleMenuService roleMenuService;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private UidService uidService;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	public List<DataOption> getRoleOptionsInPlateform() {
		return roleInfoService.getRoleOptionsInPlatform();
	}

	public List<DataOption> getRoleOptionsInTenant(Long tenantId) {
		return roleInfoService.getRoleOptionsInTenant(tenantId);
	}

	public PageInfo<RoleRecord> queryRoleRecords(QueryRoleConditions queryConditions) {
		Page<RoleRecord> thePage = PageHelper.startPage(queryConditions.getPage(), queryConditions.getSize());
		List<RoleRecord> depts = roleInfoService.queryRoleRecords(queryConditions);
		PageInfo<RoleRecord> pageInfo = PageInfo.of(thePage);
		pageInfo.setList(depts);

		return pageInfo;
	}

	@Override
	public RoleDetails getRoleDetails(Long roleId) {
		RoleInfo roleInfo = roleInfoService.getById(roleId);
		RoleDetails roleDetails = sysiamBeanConverter.roleInfoToRoleDetails(roleInfo);

		List<TreeNode> flatMenusInRole = roleMenuService.getFlatMenusInRole(roleId);
		List<TreeNode> treeMenusInRole = TreeUtils.buildTree(flatMenusInRole, ROOT_CHECKER, PARENT_CHECKER, CHILDREN_SETTER);
		roleDetails.setMenus(treeMenusInRole);

		return roleDetails;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Long createRoleWithMenus(RoleInfo roleInfo, List<ServiceMenuOption> serviceMenus) throws ServiceException {
		RoleInfo theRole = null;
		if (roleInfo.getType() == 1) { // 平台角色
			theRole = roleInfoService.getRoleInfoInPlatform(roleInfo.getName());
		} else if (roleInfo.getType() == 2) { // 运营商角色
			theRole = roleInfoService.getRoleInfoInTenant(roleInfo.getTenantId(), roleInfo.getName());
		}

		if (theRole != null) {
			throw new RoleException("角色名称已存在");
		}

		Date now = DateUtil.date();
		long roleId = uidService.generate();
		roleInfo.setId(roleId);
		roleInfo.setStatus(AvailableStatus.NORMAL.value());
		roleInfo.setDeleteStatus(DeleteStatus.NOT_DELETED.value());
		roleInfo.setCreateTime(now);
		roleInfoService.save(roleInfo);

		if (!CollectionUtils.isEmpty(serviceMenus)) {
			List<RoleMenu> menusInRole = new ArrayList<>();
			for (ServiceMenuOption serviceMenu : serviceMenus) {
				long roleMenuId = uidService.generate();
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setId(roleMenuId);
				roleMenu.setRoleId(roleId);
				roleMenu.setServiceItemId(serviceMenu.getServiceItemId());
				roleMenu.setMenuId(serviceMenu.getMenuId());
				roleMenu.setCreateBy(roleInfo.getCreateBy());
				roleMenu.setCreateTime(now);

				menusInRole.add(roleMenu);
			}

			roleMenuService.saveBatch(menusInRole);
		}

		return roleId;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void updateRoleWithMenus(RoleInfo roleInfo, List<ServiceMenuOption> serviceMenus) throws ServiceException {
		RoleInfo theRole = null;
		if (roleInfo.getType() == 1) { // 平台角色
			theRole = roleInfoService.getRoleInfoInPlatform(roleInfo.getName());
		} else if (roleInfo.getType() == 2) { // 运营商角色
			theRole = roleInfoService.getRoleInfoInTenant(roleInfo.getTenantId(), roleInfo.getName());
		}

		if (theRole != null && !theRole.getId().equals(roleInfo.getId())) {
			throw new RoleException("角色名称已存在");
		}
		roleInfoService.updateById(roleInfo);

		roleMenuService.removeRoleMenus(theRole.getId());

		Date now = DateUtil.date();
		if (!CollectionUtils.isEmpty(serviceMenus)) {
			List<RoleMenu> menusInRole = new ArrayList<>();
			for (ServiceMenuOption serviceMenu : serviceMenus) {
				long roleMenuId = uidService.generate();
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setId(roleMenuId);
				roleMenu.setRoleId(roleInfo.getId());
				roleMenu.setServiceItemId(serviceMenu.getServiceItemId());
				roleMenu.setMenuId(serviceMenu.getMenuId());
				roleMenu.setCreateBy(roleInfo.getCreateBy());
				roleMenu.setCreateTime(now);

				menusInRole.add(roleMenu);
			}

			roleMenuService.saveBatch(menusInRole);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void removeRole(Long roleId) throws RoleException {
		if (userRoleService.hasRoleAssigned(roleId)) {
			throw new RoleException("角色已分配给用户，无法删除");
		}

		roleInfoService.removeRoleInfo(roleId);
		roleMenuService.removeRoleMenus(roleId);
	}

	@Override
	public List<DataOption> getAssignableRoleOptions(Integer type, Long tenantId) {
		List<DataOption> roles = Collections.emptyList();
		// TODO 只能获取当前用户创建的角色？？？

		if (type == UserType.PLATFORM.value()) {
			roles = this.getRoleOptionsInPlateform();
		} else if (type == UserType.TENANT.value()) {
			roles = this.getRoleOptionsInTenant(tenantId);
		}

		return roles;
	}

	@Override
	public PageInfo<RoleRecord> queryRole(QueryRoleConditions conditions) {
		return this.queryRoleRecords(conditions);
	}

	@Override
	public Long saveRole(RoleBasic roleBasic) throws ServiceException {
		Long roleId = roleBasic.getId();

		RoleInfo roleInfo = sysiamBeanConverter.roleBasicToRoleInfo(roleBasic);
		if (roleId == null) {
			roleId = this.createRoleWithMenus(roleInfo, roleBasic.getServiceMenus());
		} else {
			this.updateRoleWithMenus(roleInfo, roleBasic.getServiceMenus());
		}

		return roleId;
	}

	@Override
	public void removeRole(LongId roleId) throws ServiceException {
		// TODO Auto-generated method stub

	}

}