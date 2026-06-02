package com.microinfra.sysiam.api;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.UserType;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.client.TenantFeignClient;
import com.microinfra.sysiam.dto.QueryTenantConditions;
import com.microinfra.sysiam.dto.ServiceItemAuthorizing;
import com.microinfra.sysiam.dto.ServiceMenuOption;
import com.microinfra.sysiam.dto.TenantAdministrator;
import com.microinfra.sysiam.dto.TenantBasic;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.entity.RoleInfo;
import com.microinfra.sysiam.entity.TenantInfo;
import com.microinfra.sysiam.entity.UserInfo;
import com.microinfra.sysiam.facade.SysiamBeanConverter;
import com.microinfra.sysiam.facade.RoleManager;
import com.microinfra.sysiam.facade.TenantManager;
import com.microinfra.sysiam.facade.TenantService;
import com.microinfra.sysiam.facade.UserManager;
import com.microinfra.sysiam.vo.TenantDetails;
import com.microinfra.sysiam.vo.TenantRecord;

@RestController
public class TenantFeignController implements TenantFeignClient {

	@Resource
	private TenantManager tenantManager;

	@Resource
	private UserManager userManager;

	@Resource
	private RoleManager roleManager;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	public List<DataOption> getTenantOptions() {
		return tenantManager.getTenantOptions();
	}

	public PageInfo<TenantRecord> queryTenantRecords(QueryTenantConditions queryConditions) {
		return tenantManager.queryTenantRecords(queryConditions);
	}

	public TenantDetails getTenantDetails(Long tenantId) {
		return tenantManager.getTenantDetails(tenantId);
	}

	public Long saveTenant(TenantBasic tenantBasic) throws ServiceException {
		Long tenantId = tenantBasic.getId();
		TenantInfo tenantInfo = sysiamBeanConverter.tenantBasicToTenantInfo(tenantBasic);

		if (tenantId == null) {
			tenantId = tenantManager.createTenant(tenantInfo);
		} else {
			tenantManager.updateTenant(tenantInfo);
		}

		return tenantId;
	}

	public UserBasic getAdministrator(Long tenantId) {
		return userManager.getAdministratorOfTenant(tenantId);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void setAdministrator(TenantAdministrator administrator) throws ServiceException {
		UserBasic userBasic = userManager.getAdministratorOfTenant(administrator.getTenantId());
		boolean hasAdministrator = (userBasic != null);
		if (!hasAdministrator && ObjectUtils.isEmpty(administrator.getPassword())) {
			throw new ServiceException("账号密码不能为空");
		}

		if (!hasAdministrator) {
			userBasic = new UserBasic();
			userBasic.setNick(administrator.getMobile());
		}

		TenantDetails tenantDetails = tenantManager.getTenantDetails(administrator.getTenantId());

		userBasic.setName(administrator.getName());
		userBasic.setPassword(administrator.getPassword());
		userBasic.setMobile(administrator.getMobile());
		userBasic.setType(UserType.TENANT.value());
		userBasic.setTenantId(administrator.getTenantId());
		userBasic.setTenantName(tenantDetails.getName());
		userBasic.setCreateTime(new Date());
		userBasic.setCreateBy(administrator.getCreateBy());

		UserInfo userInfo = sysiamBeanConverter.userBasicToUserInfo(userBasic);
		if (!hasAdministrator) {
			Long userId = userManager.createUser(userInfo, userBasic.getPassword(), userBasic.getOrgIds());

			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setName("运营商管理员");
			roleInfo.setType(UserType.TENANT.value());
			roleInfo.setTenantId(administrator.getTenantId());
			roleInfo.setCreateTime(new Date());
			roleInfo.setCreateBy(administrator.getCreateBy());
			Long roleId = roleManager.createRoleWithMenus(roleInfo, Lists.newArrayList(new ServiceMenuOption(0L, 0L)));// 运营商所有服务项目的所有菜单

			userManager.authorizeRolesToUser(userId, Lists.newArrayList(roleId), administrator.getCreateBy());
			userManager.authorizeServiceItemsToUser(userId, Lists.newArrayList(0L), administrator.getCreateBy());
		} else {
			userManager.updateUser(userInfo, userBasic.getPassword(), userBasic.getOrgIds());
		}
	}

	public List<DataOption> getAuthorizedServiceItems(Long tenantId) {
		return tenantManager.getAuthorizedServices(tenantId);
	}

	public void authorizeServiceItems(ServiceItemAuthorizing authorizing) throws ServiceException {
		tenantManager.authorizeServiceItemsToTenant(authorizing.getTenantId(), authorizing.getServiceItemIds(), authorizing.getAuthorizer());
	}

	public void removeTenant(LongId tenantId) {
		tenantManager.removeTenant(tenantId.getId());
	}

}