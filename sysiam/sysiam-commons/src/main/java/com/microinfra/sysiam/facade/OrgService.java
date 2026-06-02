package com.microinfra.sysiam.facade;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.sysiam.dto.OrgBasic;
import com.microinfra.sysiam.dto.QueryOrgConditions;
import com.microinfra.sysiam.vo.OrgDetails;
import com.microinfra.sysiam.vo.OrgRecord;

public interface OrgService {

	public List<TreeNode> getOrgTree(Integer type, Long tenantId);

	public PageInfo<OrgRecord> queryOrg(QueryOrgConditions conditions);

	public OrgDetails getOrgDetails(Long orgId);

	public Long saveOrg(OrgBasic orgBasic) throws ServiceException;

	public void removeOrg(LongId orgId);

}