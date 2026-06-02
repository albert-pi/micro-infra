package com.microinfra.sysiam.facade;

import static com.microinfra.commons.lang.TreeUtils.CHILDREN_SETTER;
import static com.microinfra.commons.lang.TreeUtils.PARENT_CHECKER;
import static com.microinfra.commons.lang.TreeUtils.ROOT_CHECKER;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.AvailableStatus;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.commons.bean.UserType;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.commons.lang.TreeUtils;
import com.microinfra.sysiam.dto.OrgBasic;
import com.microinfra.sysiam.dto.QueryOrgConditions;
import com.microinfra.sysiam.entity.OrgInfo;
import com.microinfra.sysiam.exception.OrgException;
import com.microinfra.sysiam.facade.OrgService;
import com.microinfra.sysiam.service.OrgInfoService;
import com.microinfra.sysiam.vo.OrgDetails;
import com.microinfra.sysiam.vo.OrgRecord;
import com.microinfra.uid.facade.UidService;

import cn.hutool.core.date.DateUtil;

/**
 * <p>
 * 组织机构管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class OrgManager implements OrgService {

	@Resource
	private OrgInfoService orgInfoService;

	@Resource
	private UidService uidService;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	public List<TreeNode> getOrgTreeInPlatform() {
		List<TreeNode> flatOrgsInPlatform = orgInfoService.getFlatOrgsInPlatform();

		return TreeUtils.buildTree(flatOrgsInPlatform, ROOT_CHECKER, PARENT_CHECKER, CHILDREN_SETTER);
	}

	public List<TreeNode> getOrgTreeInTenant(Long tenantId) {
		List<TreeNode> flatOrgsInTenant = orgInfoService.getFlatOrgsInTenant(tenantId);

		return TreeUtils.buildTree(flatOrgsInTenant, ROOT_CHECKER, PARENT_CHECKER, CHILDREN_SETTER);
	}

	@Override
	public OrgDetails getOrgDetails(Long orgId) {
		OrgInfo orgInfo = orgInfoService.getOrgInfo(orgId);
		OrgDetails orgDetails = sysiamBeanConverter.orgInfoToOrgDetails(orgInfo);

		return orgDetails;
	}

	public PageInfo<OrgRecord> queryOrgRecords(QueryOrgConditions queryConditions) {
		Page<OrgRecord> thePage = PageHelper.startPage(queryConditions.getPage(), queryConditions.getSize());
		List<OrgRecord> orgs = orgInfoService.queryOrgRecords(queryConditions);
		PageInfo<OrgRecord> pageInfo = PageInfo.of(thePage);
		pageInfo.setList(orgs);

		return pageInfo;
	}

	public Long createOrg(OrgInfo orgInfo) throws ServiceException {
		OrgInfo theOrg = null;
		if (orgInfo.getType() == 1) {// 平台公司的组织机构
			theOrg = orgInfoService.getOrgInfoInPlatform(orgInfo.getName());
		} else if (orgInfo.getType() == 2) {// 运营商的组织机构
			theOrg = orgInfoService.getOrgInfoInTenant(orgInfo.getTenantId(), orgInfo.getName());
		}
		if (theOrg != null) {
			throw new OrgException("组织机构名称已存在");
		}

		long orgId = uidService.generate();
		orgInfo.setId(orgId);
		orgInfo.setStatus(AvailableStatus.NORMAL.value());
		orgInfo.setDeleteStatus(DeleteStatus.NOT_DELETED.value());
		orgInfo.setCreateTime(DateUtil.date());
		orgInfoService.save(orgInfo);

		return orgId;
	}

	public void updateOrg(OrgInfo orgInfo) throws ServiceException {
		OrgInfo theOrg = orgInfoService.getById(orgInfo.getId());
		OrgInfo org = null;
		if (theOrg.getType() == 1) {// 平台公司的组织机构
			org = orgInfoService.getOrgInfoInPlatform(orgInfo.getName());
		} else if (theOrg.getType() == 2) {// 运营商的组织机构
			org = orgInfoService.getOrgInfoInTenant(orgInfo.getTenantId(), orgInfo.getName());
		}

		if (org != null && !org.getId().equals(theOrg.getId())) {
			throw new OrgException("组织机构名称已存在");
		}

		orgInfoService.updateById(orgInfo);
	}

	public void removeOrg(Long orgId) {
		// TODO 检查部门下的成员
		orgInfoService.removeOrgInfo(orgId);

	}

	@Override
	public List<TreeNode> getOrgTree(Integer type, Long tenantId) {
		List<TreeNode> orgs = Collections.emptyList();
		if (type == UserType.PLATFORM.value()) {
			orgs = this.getOrgTreeInPlatform();
		} else if (type == UserType.TENANT.value()) {
			orgs = this.getOrgTreeInTenant(tenantId);
		}

		return orgs;
	}

	@Override
	public PageInfo<OrgRecord> queryOrg(QueryOrgConditions conditions) {
		return this.queryOrgRecords(conditions);
	}

	@Override
	public Long saveOrg(OrgBasic orgBasic) throws ServiceException {
		Long orgId = orgBasic.getId();
		OrgInfo orgInfo = sysiamBeanConverter.orgBasicToOrgInfo(orgBasic);

		if (orgId == null) {
			orgId = this.createOrg(orgInfo);
		} else {
			this.updateOrg(orgInfo);
		}

		return orgId;
	}

	@Override
	public void removeOrg(LongId orgId) {
		this.removeOrg(orgId.getId());
	}

}