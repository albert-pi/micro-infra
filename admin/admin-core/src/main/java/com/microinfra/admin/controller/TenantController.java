package com.microinfra.admin.controller;

import java.util.Collections;
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
import com.google.common.collect.Lists;
import com.microinfra.admin.controller.params.ParamsConverter;
import com.microinfra.admin.controller.params.TenantRequestParams.QueryTenantParams;
import com.microinfra.admin.controller.params.TenantRequestParams.ServiceItemAuthorizingParams;
import com.microinfra.admin.controller.params.TenantRequestParams.TenantAdministratorParams;
import com.microinfra.admin.controller.params.TenantRequestParams.TenantFormParams;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.bean.UserType;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.framework.CurrentUser;
import com.microinfra.framework.HasAuthority;
import com.microinfra.framework.LongIdParam;
import com.microinfra.sysiam.dto.QueryTenantConditions;
import com.microinfra.sysiam.dto.ServiceItemAuthorizing;
import com.microinfra.sysiam.dto.TenantAdministrator;
import com.microinfra.sysiam.dto.TenantBasic;
import com.microinfra.sysiam.facade.RoleService;
import com.microinfra.sysiam.facade.TenantService;
import com.microinfra.sysiam.facade.UserService;
import com.microinfra.sysiam.vo.TenantDetails;
import com.microinfra.sysiam.vo.TenantRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "运营商管理接口")
@RestController
@Validated
public class TenantController {

	@Resource
	private TenantService tenantService;

	@Resource
	private RoleService roleService;

	@Resource
	private UserService userService;

	@Resource
	private ParamsConverter paramsConverter;

	@ApiOperation(produces = "application/json", value = "获取可用的运营商选项列表")
	@GetMapping("/tenant/available-options")
	public List<DataOption> getAvailableTenantOptions(@ApiIgnore @CurrentUser UserProfile userProfile) {
		List<DataOption> tenantOptions = Collections.emptyList();
		if (userProfile.getType() == UserType.PLATFORM.value()) {
			boolean hasAllTenants = userService.hasAllTenants(userProfile.getId());
			if (hasAllTenants) {
				tenantOptions = tenantService.getTenantOptions();
			} else {
				tenantOptions = userProfile.getTenants();
			}
		} else if (userProfile.getType() == UserType.TENANT.value()) {
			tenantOptions = Lists.newArrayList(new DataOption(userProfile.getTenantId().toString(), userProfile.getTenantName()));
		}

		return tenantOptions;
	}

	@ApiOperation(produces = "application/json", value = "查询运营商信息")
	@HasAuthority("tenant.query")
	@GetMapping("/tenant/query")
	public PageInfo<TenantRecord> queryTenantRecords(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated QueryTenantParams params) {
		QueryTenantConditions queryConditions = paramsConverter.queryTenantParamsToQueryTenantConditions(params);
		if (userProfile.getType() == UserType.TENANT.value()) {// 运营商用户
			queryConditions.setTenantId(userProfile.getTenantId());
		}

		return tenantService.queryTenantRecords(queryConditions);
	}

	@ApiOperation(produces = "application/json", value = "获取运营商详细信息")
	@HasAuthority("tenant.details")
	@GetMapping("/tenant/details")
	public TenantDetails getTenantDetails(@RequestParam(name = "id") Long tenantId) {
		return tenantService.getTenantDetails(tenantId);
	}

	@ApiOperation(produces = "application/json", value = "保存运营商信息", notes = "新增运营商或修改运营商信息。返回运营商ID")
	@HasAuthority("tenant.save")
	@PostMapping("/tenant/save")
	public LongId saveTenant(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody TenantFormParams params) throws ServiceException {
		TenantBasic tenantBasic = paramsConverter.tenantFormParamsToTenantBasic(params);
		tenantBasic.setCreateTime(new Date());
		tenantBasic.setCreateBy(userProfile.getName());
		Long tenantId = tenantService.saveTenant(tenantBasic);

		return new LongId(tenantId);
	}

	@ApiOperation(produces = "application/json", value = "删除运营商")
	@HasAuthority("tenant.remove")
	@PostMapping("/tenant/remove")
	public void removeTenant(@Validated @RequestBody LongIdParam tenantId) {
		tenantService.removeTenant(new LongId(tenantId.getId()));
	}

	@ApiOperation(produces = "application/json", value = "获取运营商可使用的平台服务项目")
	@HasAuthority("tenant.service-item.list")
	@GetMapping("/tenant/service-item/list")
	public List<DataOption> getAuthorizedServiceItems(@RequestParam(name = "id") Long tenantId) {
		return tenantService.getAuthorizedServiceItems(tenantId);
	}

	@ApiOperation(produces = "application/json", value = "授权运营商使用平台服务项目")
	@HasAuthority("tenant.service-item.authorizing")
	@PostMapping("/tenant/service-item/authorizing")
	public void authorizeServiceItems(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody ServiceItemAuthorizingParams params) throws ServiceException {
		ServiceItemAuthorizing serverItemAuthorizing = new ServiceItemAuthorizing();
		serverItemAuthorizing.setTenantId(params.getTenantId());
		serverItemAuthorizing.setServiceItemIds(params.getServiceItemIds());
		serverItemAuthorizing.setAuthorizer(userProfile.getName());
		tenantService.authorizeServiceItems(serverItemAuthorizing);
	}

	@ApiOperation(produces = "application/json", value = "设置运营商管理员账号")
	@HasAuthority("tenant.administrator.setting")
	@PostMapping("/tenant/administrator/setting")
	public void setAdministrator(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody TenantAdministratorParams params) throws ServiceException {
		TenantAdministrator administrator = new TenantAdministrator();
		administrator.setTenantId(params.getTenantId());
		administrator.setName(params.getName());
		administrator.setMobile(params.getMobile());
		administrator.setPassword(params.getPassword());
		administrator.setCreateBy(userProfile.getName());

		tenantService.setAdministrator(administrator);
	}

}