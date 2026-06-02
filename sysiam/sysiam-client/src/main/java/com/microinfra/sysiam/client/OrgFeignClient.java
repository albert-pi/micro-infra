package com.microinfra.sysiam.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.commons.rpc.feign.FeignConfiguration;
import com.microinfra.sysiam.dto.OrgBasic;
import com.microinfra.sysiam.dto.QueryOrgConditions;
import com.microinfra.sysiam.facade.OrgService;
import com.microinfra.sysiam.vo.OrgDetails;
import com.microinfra.sysiam.vo.OrgRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("组织机构管理接口")
@FeignClient(name = Sysiam.SERVICE_NAME, contextId = "orgService", configuration = FeignConfiguration.class)
public interface OrgFeignClient extends OrgService {

	@Override
	@ApiOperation(produces = "application/json", value = "获取组织机构列表，以树状结构进行组织")
	@GetMapping("/internal/org/tree-options")
	public List<TreeNode> getOrgTree(@RequestParam(name = "type") Integer type, @RequestParam(name = "tenantId", required = false) Long tenantId);

	@Override
	@ApiOperation(produces = "application/json", value = "查询组织机构")
	@PostMapping("/internal/org/query")
	public PageInfo<OrgRecord> queryOrg(@RequestBody QueryOrgConditions conditions);

	@Override
	@ApiOperation(produces = "application/json", value = "获取组织机构详情")
	@GetMapping("/internal/org/org/details")
	public OrgDetails getOrgDetails(@RequestParam(name = "orgId") Long orgId);

	@Override
	@ApiOperation(produces = "application/json", value = "保存组织机构信息")
	@PostMapping("/internal/org/save")
	public Long saveOrg(@RequestBody OrgBasic orgBasic) throws ServiceException;

	@Override
	@ApiOperation(produces = "application/json", value = "删除组织机构")
	@PostMapping("/internal/org/remove")
	public void removeOrg(@RequestBody LongId orgId);

}