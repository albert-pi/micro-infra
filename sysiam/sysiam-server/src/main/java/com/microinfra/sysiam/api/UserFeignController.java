package com.microinfra.sysiam.api;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.client.UserFeignClient;
import com.microinfra.sysiam.dto.PasswordChanging;
import com.microinfra.sysiam.dto.QueryUserConditions;
import com.microinfra.sysiam.dto.RoleAuthorizing;
import com.microinfra.sysiam.dto.TenantAuthorizing;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.dto.UserIdentity;
import com.microinfra.sysiam.entity.UserInfo;
import com.microinfra.sysiam.facade.SysiamBeanConverter;
import com.microinfra.sysiam.facade.UserManager;
import com.microinfra.sysiam.service.ServiceItemService;
import com.microinfra.sysiam.vo.UserDetails;
import com.microinfra.sysiam.vo.UserRecord;

@RestController
public class UserFeignController implements UserFeignClient {

	@Resource
	private UserManager userManager;

	@Resource
	private ServiceItemService serviceItemService;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	@Override
	public PageInfo<UserRecord> queryUserRecords(QueryUserConditions queryConditions) {
		return userManager.queryUserRecords(queryConditions);
	}

	@Override
	public UserDetails getUserDetails(Long userId) {
		return userManager.getUserDetails(userId);
	}

	@Override
	public Long saveUser(UserBasic userBasic) throws ServiceException {
		Long userId = userBasic.getId();
		if (userId == null && !ObjectUtils.isEmpty(userBasic.getPassword())) {
			throw new ServiceException("账号密码不能为空");
		}

		UserInfo userInfo = sysiamBeanConverter.userBasicToUserInfo(userBasic);
		if (userId == null) {
			userId = userManager.createUser(userInfo, userBasic.getPassword(), userBasic.getOrgIds());
		} else {
			userManager.updateUser(userInfo, userBasic.getPassword(), userBasic.getOrgIds());
		}

		return userId;
	}

	@Override
	public boolean hasAllTenants(Long userId) {
		return userManager.hasAllTenants(userId);
	}

	@Override
	public List<DataOption> getAuthorizedServiceItems(Long userId) {
		List<DataOption> serviceOptions = Collections.emptyList();
		boolean hasAllServiceItems = userManager.hasAllServiceItems(userId);
		if (hasAllServiceItems) {
			serviceOptions = serviceItemService.getServiceItemOptions();
		} else {
			serviceOptions = userManager.getAuthorizedServiceItems(userId);
		}

		return serviceOptions;
	}

	@Override
	public void changePassword(PasswordChanging passwordChanging) throws ServiceException {
		if (!userManager.isPasswordValid(passwordChanging.getUserId(), passwordChanging.getOldPwd())) {
			throw new ServiceException("旧密码错误");
		}

		userManager.changePassword(passwordChanging.getUserId(), passwordChanging.getNewPwd());
	}

	@Override
	public List<DataOption> getAuthorizedRolesOfUser(Long userId) {
		return userManager.getRoleOptionsByUser(userId);
	}

	@Override
	public void authorizeRolesToUser(RoleAuthorizing roleAuthorizing) throws ServiceException {
		userManager.authorizeRolesToUser(roleAuthorizing.getUserId(), roleAuthorizing.getRoleIds(), roleAuthorizing.getAuthorizer());
	}

	@Override
	public List<DataOption> getAuthorizedTenantsOfUser(Long userId) {
		return userManager.getAuthorizedTenants(userId);
	}

	@Override
	public void authorizeTenantsToUser(TenantAuthorizing tenantAuthorizing) throws ServiceException {
		userManager.authorizeTenantsToUser(tenantAuthorizing.getUserId(), tenantAuthorizing.getTenantIds(), tenantAuthorizing.getAuthorizer());
	}

	@Override
	public UserProfile getUserProfileByUid(Long uid) {
		return userManager.getUserProfile(uid);
	}

	@Override
	public UserProfile getUserProfileByName(String name) {
		return userManager.getUserProfileByName(name);
	}

	@Override
	public UserProfile getUserProfileByMobile(String mobile) {
		return userManager.getUserProfileByMobile(mobile);
	}

	@Override
	public boolean checkUserIdentity(UserIdentity userIdentity) throws ServiceException {
		return userManager.checkUserIdentity(userIdentity);
	}

	@Override
	public void removeUser(LongId userId) throws ServiceException {
		userManager.removeAccount(userId.getId());
	}

}