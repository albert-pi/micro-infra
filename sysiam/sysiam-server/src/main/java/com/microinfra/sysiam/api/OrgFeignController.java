package com.microinfra.sysiam.api;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.UserType;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.sysiam.dto.OrgBasic;
import com.microinfra.sysiam.dto.QueryOrgConditions;
import com.microinfra.sysiam.entity.OrgInfo;
import com.microinfra.sysiam.facade.SysiamBeanConverter;
import com.microinfra.sysiam.facade.OrgManager;
import com.microinfra.sysiam.facade.OrgService;
import com.microinfra.sysiam.vo.OrgDetails;
import com.microinfra.sysiam.vo.OrgRecord;

@RestController
public class OrgFeignController implements OrgService {

	@Resource
	private OrgManager orgManager;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	public List<TreeNode> getOrgTree(Integer type, Long tenantId) {
		List<TreeNode> orgs = Collections.emptyList();
		if (type == UserType.PLATFORM.value()) {
			orgs = orgManager.getOrgTreeInPlatform();
		} else if (type == UserType.TENANT.value()) {
			orgs = orgManager.getOrgTreeInTenant(tenantId);
		}

		return orgs;
	}

	public PageInfo<OrgRecord> queryOrg(QueryOrgConditions queryConditions) {
		return orgManager.queryOrgRecords(queryConditions);
	}

	public OrgDetails getOrgDetails(Long orgId) {
		return orgManager.getOrgDetails(orgId);
	}

	public Long saveOrg(OrgBasic orgBasic) throws ServiceException {
		Long orgId = orgBasic.getId();
		OrgInfo orgInfo = sysiamBeanConverter.orgBasicToOrgInfo(orgBasic);

		if (orgId == null) {
			orgId = orgManager.createOrg(orgInfo);
		} else {
			orgManager.updateOrg(orgInfo);
		}

		return orgId;
	}

	public void removeOrg(LongId orgId) {
		orgManager.removeOrg(orgId.getId());
	}

}