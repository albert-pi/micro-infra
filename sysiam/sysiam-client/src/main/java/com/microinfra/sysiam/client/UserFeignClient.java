package com.microinfra.sysiam.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.rpc.feign.FeignConfiguration;
import com.microinfra.sysiam.dto.PasswordChanging;
import com.microinfra.sysiam.dto.QueryUserConditions;
import com.microinfra.sysiam.dto.RoleAuthorizing;
import com.microinfra.sysiam.dto.TenantAuthorizing;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.dto.UserIdentity;
import com.microinfra.sysiam.facade.UserService;
import com.microinfra.sysiam.vo.UserDetails;
import com.microinfra.sysiam.vo.UserRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("用户管理及账号权限接口")
@FeignClient(name = Sysiam.SERVICE_NAME, contextId = "userService", configuration = FeignConfiguration.class)
public interface UserFeignClient extends UserService {

	@ApiOperation(produces = "application/json", value = "查询用户记录")
	@PostMapping("/internal/user/query")
	public PageInfo<UserRecord> queryUserRecords(@RequestBody QueryUserConditions conditions);

	@ApiOperation(produces = "application/json", value = "获取用户详情")
	@GetMapping("/internal/user/details")
	public UserDetails getUserDetails(@RequestParam(name = "userId") Long userId);

	@ApiOperation(produces = "application/json", value = "保存用户信息")
	@PostMapping("/internal/user/save")
	public Long saveUser(@RequestBody UserBasic userBasic) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "用户是否管理所有运营商")
	@GetMapping("/internal/user/has-all-tenants")
	public boolean hasAllTenants(@RequestParam(name = "userId") Long userId);

	@ApiOperation(produces = "application/json", value = "获取用户使用的平台服务项目")
	@GetMapping("/internal/user/service-item/list")
	public List<DataOption> getAuthorizedServiceItems(@RequestParam(name = "userId") Long userId);

	@ApiOperation(produces = "application/json", value = "修改用户密码")
	@PostMapping("/internal/user/password/changing")
	public void changePassword(@RequestBody PasswordChanging passwordChanging) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "获取用户已分配的角色")
	@GetMapping("/internal/user/role/list")
	public List<DataOption> getAuthorizedRolesOfUser(@RequestParam(name = "userId") Long userId);

	@ApiOperation(produces = "application/json", value = "用户角色分配")
	@PostMapping("/internal/user/role/authorizing")
	public void authorizeRolesToUser(@RequestBody RoleAuthorizing roleAuthorizing) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "获取用户已授权的运营商")
	@GetMapping("/internal/user/tenant/list")
	public List<DataOption> getAuthorizedTenantsOfUser(@RequestParam(name = "userId") Long userId);

	@ApiOperation(produces = "application/json", value = "授权用户管理运营商")
	@PostMapping("/internal/user/tenant/authorizing")
	public void authorizeTenantsToUser(@RequestBody TenantAuthorizing tenantAuthorizing) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "根据用户ID获取用户资料")
	@GetMapping("/internal/user/profile-by-uid")
	public UserProfile getUserProfileByUid(@RequestParam(name = "uid") Long userId);

	@ApiOperation(produces = "application/json", value = "根据账号名称获取用户资料")
	@GetMapping("/internal/user/profile-by-name")
	public UserProfile getUserProfileByName(@RequestParam(name = "name") String name);

	@ApiOperation(produces = "application/json", value = "根据账号名称获取用户资料")
	@GetMapping("/internal/user/profile-by-mobile")
	public UserProfile getUserProfileByMobile(@RequestParam(name = "mobile") String mobile);

	@ApiOperation(produces = "application/json", value = "验证用户身份")
	@PostMapping("/internal/user/check-identity")
	public boolean checkUserIdentity(@RequestBody UserIdentity userIdentity) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "删除用户")
	@PostMapping("/internal/user/remove")
	public void removeUser(@RequestBody LongId userId) throws ServiceException;

}