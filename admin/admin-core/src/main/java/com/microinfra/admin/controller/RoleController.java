package com.microinfra.admin.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.microinfra.admin.controller.params.ParamsConverter;
import com.microinfra.admin.controller.params.RoleRequestParams.QueryRoleParams;
import com.microinfra.admin.controller.params.RoleRequestParams.RoleFormParams;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.framework.CurrentUser;
import com.microinfra.framework.HasAuthority;
import com.microinfra.framework.LongIdParam;
import com.microinfra.sysiam.dto.QueryRoleConditions;
import com.microinfra.sysiam.dto.RoleBasic;
import com.microinfra.sysiam.facade.RoleService;
import com.microinfra.sysiam.vo.RoleDetails;
import com.microinfra.sysiam.vo.RoleRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "角色管理")
@RestController
@Validated
public class RoleController {

	@Resource
	private RoleService roleService;

	@Resource
	private ParamsConverter paramsConverter;

	@ApiOperation(produces = "application/json", value = "获取可用来分配给其它用户的角色选项列表", notes = "获取可用来分配给其它用户的角色选项列表。根据当前用户的权限获取可分配的角色列表")
	@GetMapping("/role/available-options")
	public List<DataOption> getAvailableRoleOptions(@ApiIgnore @CurrentUser UserProfile userProfile) {
		return roleService.getAssignableRoleOptions(userProfile.getType(), userProfile.getTenantId());
	}

	@ApiOperation(produces = "application/json", value = "查询角色信息")
	@HasAuthority("role.query")
	@GetMapping("/role/query")
	public PageInfo<RoleRecord> queryRole(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated QueryRoleParams params) {
		QueryRoleConditions queryConditions = paramsConverter.queryRoleParamsToQueryRoleConditions(params);
		queryConditions.setType(userProfile.getType());
		queryConditions.setTenantId(userProfile.getTenantId());

		return roleService.queryRole(queryConditions);
	}

	@ApiOperation(produces = "application/json", value = "获取角色详细信息")
	@HasAuthority("role.details")
	@GetMapping("/role/details")
	public RoleDetails getRoleDetails(@RequestParam(name = "id") Long roleId) {
		return roleService.getRoleDetails(roleId);
	}

	@ApiOperation(produces = "application/json", value = "保存角色信息", notes = "保存新增角色或修改角色信息。返回角色ID")
	@HasAuthority("role.save")
	@PostMapping("/role/save")
	public LongId saveRole(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody RoleFormParams params) throws ServiceException {
		RoleBasic role = paramsConverter.roleFormParamsToRoleBasic(params);
		role.setType(userProfile.getType());
		role.setTenantId(userProfile.getTenantId());
		role.setCreateTime(new Date());
		role.setCreateBy(userProfile.getName());
		Long roleId = roleService.saveRole(role);

		return new LongId(roleId);
	}

	@ApiOperation(produces = "application/json", value = "删除角色", notes = "删除角色")
	@HasAuthority("role.remove")
	@PostMapping("/role/remove")
	public void removeRole(@Validated @RequestBody LongIdParam roleId) throws ServiceException {
		roleService.removeRole(new LongId(roleId.getId()));
	}

}