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
import com.microinfra.sysiam.dto.QueryTenantConditions;
import com.microinfra.sysiam.dto.ServiceItemAuthorizing;
import com.microinfra.sysiam.dto.TenantAdministrator;
import com.microinfra.sysiam.dto.TenantBasic;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.facade.TenantService;
import com.microinfra.sysiam.vo.TenantDetails;
import com.microinfra.sysiam.vo.TenantRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("运营商接口")
@FeignClient(name = Sysiam.SERVICE_NAME, contextId = "tenantService", configuration = FeignConfiguration.class)
public interface TenantFeignClient extends TenantService {

	@ApiOperation(produces = "application/json", value = "获取运营商列表选项")
	@GetMapping("/internal/tenant/options")
	public List<DataOption> getTenantOptions();

	@ApiOperation(produces = "application/json", value = "查询运营商信息")
	@PostMapping("/internal/tenant/query")
	public PageInfo<TenantRecord> queryTenantRecords(@RequestBody QueryTenantConditions queryConditions);

	@ApiOperation(produces = "application/json", value = "获取运营商详情")
	@GetMapping("/internal/tenant/details")
	public TenantDetails getTenantDetails(@RequestParam(name = "tenantId") Long tenantId);

	@ApiOperation(produces = "application/json", value = "保存运营商信息")
	@PostMapping("/internal/tenant/save")
	public Long saveTenant(@RequestBody TenantBasic tenantBasic) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "获取管理员信息")
	@GetMapping("/internal/tenant/administrator")
	public UserBasic getAdministrator(@RequestParam(name = "tenantId") Long tenantId);

	@ApiOperation(produces = "application/json", value = "设置管理员")
	@PostMapping("/internal/tenant/administrator/set")
	public void setAdministrator(@RequestBody TenantAdministrator administrator) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "获取运营商可使用的平台服务项目")
	@GetMapping("/internal/tenant/service-item/list")
	public List<DataOption> getAuthorizedServiceItems(@RequestParam(name = "tenantId") Long tenantId);

	@ApiOperation(produces = "application/json", value = "授权服务项目到运营商")
	@PostMapping("/internal/tenant/service-item/authorizing")
	public void authorizeServiceItems(@RequestBody ServiceItemAuthorizing authorizing) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "删除运营商")
	@PostMapping("/internal/tenant/remove")
	public void removeTenant(@RequestBody LongId tenantId);

}