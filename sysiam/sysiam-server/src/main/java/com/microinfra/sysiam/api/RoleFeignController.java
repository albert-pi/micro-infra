package com.microinfra.sysiam.api;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.UserType;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.client.RoleFeignClient;
import com.microinfra.sysiam.dto.QueryRoleConditions;
import com.microinfra.sysiam.dto.RoleBasic;
import com.microinfra.sysiam.entity.RoleInfo;
import com.microinfra.sysiam.facade.SysiamBeanConverter;
import com.microinfra.sysiam.facade.RoleManager;
import com.microinfra.sysiam.vo.RoleDetails;
import com.microinfra.sysiam.vo.RoleRecord;

@RestController
public class RoleFeignController implements RoleFeignClient {

	@Resource
	private RoleManager roleManager;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	@Override
	public List<DataOption> getAssignableRoleOptions(Integer type, Long tenantId) {
		List<DataOption> roles = Collections.emptyList();
		// TODO 只能获取当前用户创建的角色？？？

		if (type == UserType.PLATFORM.value()) {
			roles = roleManager.getRoleOptionsInPlateform();
		} else if (type == UserType.TENANT.value()) {
			roles = roleManager.getRoleOptionsInTenant(tenantId);
		}

		return roles;
	}

	@Override
	public PageInfo<RoleRecord> queryRole(QueryRoleConditions queryConditions) {
		return roleManager.queryRoleRecords(queryConditions);
	}

	@Override
	public RoleDetails getRoleDetails(Long roleId) {
		return roleManager.getRoleDetails(roleId);
	}

	@Override
	public Long saveRole(RoleBasic roleBasic) throws ServiceException {
		Long roleId = roleBasic.getId();

		RoleInfo roleInfo = sysiamBeanConverter.roleBasicToRoleInfo(roleBasic);
		if (roleId == null) {
			roleId = roleManager.createRoleWithMenus(roleInfo, roleBasic.getServiceMenus());
		} else {
			roleManager.updateRoleWithMenus(roleInfo, roleBasic.getServiceMenus());
		}

		return roleId;
	}

	@Override
	public void removeRole(LongId roleId) throws ServiceException {
		roleManager.removeRole(roleId.getId());
	}

}