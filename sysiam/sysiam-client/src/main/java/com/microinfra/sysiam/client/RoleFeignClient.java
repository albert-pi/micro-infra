package com.microinfra.sysiam.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.rpc.feign.FeignConfiguration;
import com.microinfra.sysiam.dto.QueryRoleConditions;
import com.microinfra.sysiam.dto.RoleBasic;
import com.microinfra.sysiam.facade.RoleService;
import com.microinfra.sysiam.vo.RoleDetails;
import com.microinfra.sysiam.vo.RoleRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("角色管理接口")
@FeignClient(name = Sysiam.SERVICE_NAME, contextId = "roleService", configuration = FeignConfiguration.class)
public interface RoleFeignClient extends RoleService {

	@ApiOperation(produces = "application/json", value = "获取可用来分配给其它用户的角色选项列表")
	@GetMapping("/internal/role/assignable-options")
	public List<DataOption> getAssignableRoleOptions(@RequestParam(name = "type") Integer type,
			@RequestParam(name = "tenantId", required = false) Long tenantId);

	@ApiOperation(produces = "application/json", value = "查询角色")
	@PostMapping("/internal/role/query")
	public PageInfo<RoleRecord> queryRole(@RequestBody QueryRoleConditions conditions);

	@ApiOperation(produces = "application/json", value = "获取角色详情")
	@GetMapping("/internal/role/details")
	public RoleDetails getRoleDetails(@RequestParam(name = "roleId") Long roleId);

	@ApiOperation(produces = "application/json", value = "保存角色信息")
	@PostMapping("/internal/role/save")
	public Long saveRole(@RequestBody RoleBasic roleBasic) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "删除角色")
	@PostMapping("/internal/role/remove")
	public void removeRole(@RequestBody LongId roleId) throws ServiceException;

}