package com.microinfra.admin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.microinfra.admin.controller.params.ParamsConverter;
import com.microinfra.admin.controller.params.DeptRequestParams.OrgFormParams;
import com.microinfra.admin.controller.params.DeptRequestParams.QueryOrgParams;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.framework.CurrentUser;
import com.microinfra.framework.HasAuthority;
import com.microinfra.framework.LongIdParam;
import com.microinfra.sysiam.dto.OrgBasic;
import com.microinfra.sysiam.dto.QueryOrgConditions;
import com.microinfra.sysiam.facade.OrgService;
import com.microinfra.sysiam.vo.OrgDetails;
import com.microinfra.sysiam.vo.OrgRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "组织机构管理接口")
@RestController
@Validated
public class OrgController {

	@Resource
	private OrgService orgService;

	@Resource
	private ParamsConverter paramsConverter;

	@ApiOperation(produces = "application/json", value = "获取组织机构选项列表", notes = "获取组织机构选项列表，以树状结构返回")
	@PostMapping("/org/options")
	public List<TreeNode> listOrgsInTree(@ApiIgnore @CurrentUser UserProfile userProfile) {
		return orgService.getOrgTree(userProfile.getType(), userProfile.getTenantId());
	}

	@ApiOperation(produces = "application/json", value = "查询组织机构信息")
	@HasAuthority("org.query")
	@PostMapping("/org/query")
	public PageInfo<OrgRecord> queryOrg(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody QueryOrgParams params) {
		QueryOrgConditions queryConditions = paramsConverter.queryOrgParamsToQueryOrgConditions(params);
		queryConditions.setType(userProfile.getType());
		queryConditions.setTenantId(userProfile.getTenantId());

		return orgService.queryOrg(queryConditions);
	}

	@ApiOperation(produces = "application/json", value = "获取组织机构详细信息")
	@HasAuthority("org.details")
	@PostMapping("/org/details")
	public OrgDetails getOrgDetails(@Validated @RequestBody LongIdParam orgId) {
		return orgService.getOrgDetails(orgId.getId());
	}

	@ApiOperation(produces = "application/json", value = "保存组织机构信息", notes = "保存新增组织机构或修改组织机构信息。返回组织机构ID")
	@HasAuthority("org.save")
	@PostMapping("/org/save")
	public LongId saveOrg(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody OrgFormParams params) throws ServiceException {
		OrgBasic org = paramsConverter.orgFormParamsToOrgBasic(params);
		org.setType(userProfile.getType());
		org.setTenantId(userProfile.getTenantId());
		org.setParentId(0L);
		org.setCreateBy(userProfile.getName());
		Long orgId = orgService.saveOrg(org);

		return new LongId(orgId);
	}

	@ApiOperation(produces = "application/json", value = "删除组织机构", notes = "删除组织机构")
	@HasAuthority("org.remove")
	@PostMapping("/org/remove")
	public void removeOrg(@Validated @RequestBody LongIdParam orgId) {
		orgService.removeOrg(new LongId(orgId.getId()));
	}

}