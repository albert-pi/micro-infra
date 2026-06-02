package com.microinfra.admin.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.microinfra.admin.controller.params.ParamsConverter;
import com.microinfra.admin.controller.params.AccountRequestParams.AuthorizedTenantsParams;
import com.microinfra.admin.controller.params.AccountRequestParams.ChangePasswordParams;
import com.microinfra.admin.controller.params.AccountRequestParams.QueryUserParams;
import com.microinfra.admin.controller.params.AccountRequestParams.RoleAssigningParams;
import com.microinfra.admin.controller.params.AccountRequestParams.UserFormParams;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.framework.CurrentUser;
import com.microinfra.framework.HasAuthority;
import com.microinfra.framework.LongIdParam;
import com.microinfra.framework.UserProfileEvent;
import com.microinfra.sysiam.dto.PasswordChanging;
import com.microinfra.sysiam.dto.QueryUserConditions;
import com.microinfra.sysiam.dto.RoleAuthorizing;
import com.microinfra.sysiam.dto.TenantAuthorizing;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.facade.UserService;
import com.microinfra.sysiam.vo.UserDetails;
import com.microinfra.sysiam.vo.UserRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "用户信息、账号及权限管理接口")
@RestController
@Validated
public class UserController {

	@Resource
	private UserService userService;

	@Resource
	private ParamsConverter paramsConverter;

	@Resource
	private ApplicationContext applicationContext;

	@ApiOperation(produces = "application/json", value = "获取可用的平台服务项目")
	@GetMapping("/user/service-options")
	public List<DataOption> getAvailableServiceItems(@ApiIgnore @CurrentUser UserProfile userProfile) {
		return userService.getAuthorizedServiceItems(userProfile.getId());
	}

	@ApiOperation(produces = "application/json", value = "查询用户信息")
	@HasAuthority("user.query")
	@GetMapping("/user/query")
	public PageInfo<UserRecord> queryUserRecords(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated QueryUserParams params) {
		QueryUserConditions queryConditons = paramsConverter.queryUserParamsToQueryUserConditions(params);
		queryConditons.setType(userProfile.getType());
		queryConditons.setTenantId(userProfile.getTenantId());

		return userService.queryUserRecords(queryConditons);
	}

	@ApiOperation(produces = "application/json", value = "获取用户详细信息")
	@HasAuthority("user.details")
	@GetMapping("/user/details")
	public UserDetails getUserDetails(@RequestParam(name = "id") Long userId) {
		return userService.getUserDetails(userId);
	}

	@ApiOperation(produces = "application/json", value = "保存用户信息", notes = "新增用户或修改用户信息。返回用户ID")
	@HasAuthority("user.save")
	@PostMapping("/user/save")
	public LongId saveUser(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody UserFormParams params) throws ServiceException {
		UserBasic userBasic = paramsConverter.userFormParamsToUserBasic(params);
		userBasic.setType(userProfile.getType());
		userBasic.setCreateBy(userProfile.getName());
		userBasic.setCreateTime(new Date());
		Long userId = userService.saveUser(userBasic);

		if (params.getId() != null) {
			UserProfile user = userService.getUserProfileByUid(params.getId());
			if (userBasic.getId() != null) {
				applicationContext.publishEvent(new UserProfileEvent(this, user, UserProfileEvent.EventType.UPDATED));
			}
		}

		return new LongId(userId);
	}

	@ApiOperation(produces = "application/json", value = "用户修改密码", notes = "用户修改密码")
	@PostMapping("/user/password/change")
	public void changePassword(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody ChangePasswordParams params) throws ServiceException {
		PasswordChanging passwordChanging = new PasswordChanging();
		passwordChanging.setOldPwd(params.getOldPwd());
		passwordChanging.setNewPwd(params.getNewPwd());
		passwordChanging.setUserId(userProfile.getId());
		userService.changePassword(passwordChanging);
	}

	@ApiOperation(produces = "application/json", value = "获取用户已分配的角色")
	@HasAuthority("user.role.list")
	@GetMapping("/user/role/list")
	public List<DataOption> getAuthorizedRolesOfUser(@RequestParam(name = "id") Long userId) {
		return userService.getAuthorizedRolesOfUser(userId);
	}

	@ApiOperation(produces = "application/json", value = "分配指定角色给用户")
	@HasAuthority("user.role.assigning")
	@PostMapping("/user/role/assigning")
	public void assignRolesToUser(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody RoleAssigningParams params) throws ServiceException {
		RoleAuthorizing roleAuthorizing = new RoleAuthorizing();
		roleAuthorizing.setUserId(params.getUserId());
		roleAuthorizing.setRoleIds(params.getRoleIds());
		roleAuthorizing.setAuthorizer(userProfile.getName());
		userService.authorizeRolesToUser(roleAuthorizing);

		UserProfile user = userService.getUserProfileByUid(params.getUserId());
		applicationContext.publishEvent(new UserProfileEvent(this, user, UserProfileEvent.EventType.UPDATED));
	}

	@ApiOperation(produces = "application/json", value = "获取用户可管理的运营商", notes = "获取授权给用户管理的运营商数据")
	@HasAuthority("user.tenant.list")
	@GetMapping("/user/tenant/list")
	public List<DataOption> getAuthorizedTenants(@RequestParam(name = "id") Long userId) {
		return userService.getAuthorizedTenantsOfUser(userId);
	}

	@ApiOperation(produces = "application/json", value = "授权用户管理运营商数据", notes = "授权给用户管理运营商数据")
	@HasAuthority("user.tenant.authorizing")
	@PostMapping("/user/tenant/authorizing")
	public void authorizeTenantsToUser(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody AuthorizedTenantsParams params)
			throws ServiceException {
		TenantAuthorizing tenantAuthorizing = new TenantAuthorizing();
		tenantAuthorizing.setUserId(params.getUserId());
		tenantAuthorizing.setTenantIds(params.getTenantIds());
		tenantAuthorizing.setAuthorizer(userProfile.getName());
		userService.authorizeTenantsToUser(tenantAuthorizing);

		UserProfile user = userService.getUserProfileByUid(params.getUserId());
		applicationContext.publishEvent(new UserProfileEvent(this, user, UserProfileEvent.EventType.UPDATED));
	}

	@ApiOperation(produces = "application/json", value = "删除用户")
	@HasAuthority("user.remove")
	@PostMapping("/user/remove")
	public void removeUser(@Validated @RequestBody LongIdParam userId) throws ServiceException {
		UserProfile user = userService.getUserProfileByUid(userId.getId());
		userService.removeUser(new LongId(userId.getId()));

		applicationContext.publishEvent(new UserProfileEvent(this, user, UserProfileEvent.EventType.REMOVED));
	}

}