package com.microinfra.sysiam.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.IdentityType;
import com.microinfra.commons.bean.MenuOption;
import com.microinfra.commons.bean.ServiceItemOption;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.dto.PasswordChanging;
import com.microinfra.sysiam.dto.QueryUserConditions;
import com.microinfra.sysiam.dto.RoleAuthorizing;
import com.microinfra.sysiam.dto.TenantAuthorizing;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.dto.UserIdentity;
import com.microinfra.sysiam.entity.UserCredential;
import com.microinfra.sysiam.entity.UserInfo;
import com.microinfra.sysiam.entity.UserOrg;
import com.microinfra.sysiam.entity.UserRole;
import com.microinfra.sysiam.entity.UserServiceItem;
import com.microinfra.sysiam.entity.UserTenant;
import com.microinfra.sysiam.exception.UserException;
import com.microinfra.sysiam.facade.UserService;
import com.microinfra.sysiam.service.ServiceItemService;
import com.microinfra.sysiam.service.UserCredentialService;
import com.microinfra.sysiam.service.UserInfoService;
import com.microinfra.sysiam.service.UserOrgService;
import com.microinfra.sysiam.service.UserRoleService;
import com.microinfra.sysiam.service.UserServiceItemService;
import com.microinfra.sysiam.service.UserTenantService;
import com.microinfra.sysiam.vo.UserDetails;
import com.microinfra.sysiam.vo.UserRecord;
import com.microinfra.uid.facade.UidService;

import cn.hutool.core.date.DateUtil;

/**
 * <p>
 * 用户信息、账号及权限管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class UserManager implements UserService {

	@Resource
	private ServiceItemService serviceItemService;

	@Resource
	private UserInfoService userInfoService;

	@Resource
	private UserTenantService userTenantService;

	@Resource
	private UserOrgService userOrgService;

	@Resource
	private UserServiceItemService userServiceItemService;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private UserCredentialService userCredentialService;

	@Resource
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Resource
	private UidService uidService;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	public UserProfile getUserProfile(Long userId) {
		UserProfile userProfile = null;
		UserInfo userInfo = userInfoService.getUserInfo(userId);
		if (userInfo != null) {
			userProfile = sysiamBeanConverter.userInfoToUserProfile(userInfo);

			List<DataOption> orgOptions = userOrgService.getOrgOptionsByUser(userInfo.getId());
			userProfile.setOrgs(orgOptions);

			List<DataOption> tenantOptions = userTenantService.getAuthorizedTenantOptions(userInfo.getId());
			userProfile.setTenants(tenantOptions);

			List<ServiceItemOption> serviceItemOptions = userServiceItemService.getAuthorizedServiceItemOptions(userId);
			if (!CollectionUtils.isEmpty(serviceItemOptions)) {
				if (serviceItemOptions.get(0).getServiceItemId() == 0L) {
					serviceItemOptions = serviceItemService.list().stream()
							.map(item -> new ServiceItemOption(item.getAppCode(), item.getId(), item.getServiceName())).collect(Collectors.toList());
				}
			}
			userProfile.setServiceItems(serviceItemOptions);

			List<MenuOption> menuOptions = userInfoService.getAuthorizedMenuOptions(userInfo.getId());
			userProfile.setMenus(menuOptions);

		}

		return userProfile;
	}

	@Override
	public UserProfile getUserProfileByName(String name) {
		UserProfile userProfile = null;
		UserInfo userInfo = userInfoService.getUserInfoByName(name);
		if (userInfo != null) {
			userProfile = sysiamBeanConverter.userInfoToUserProfile(userInfo);

			List<DataOption> orgOptions = userOrgService.getOrgOptionsByUser(userInfo.getId());
			userProfile.setOrgs(orgOptions);

			List<DataOption> tenantOptions = userTenantService.getAuthorizedTenantOptions(userInfo.getId());
			userProfile.setTenants(tenantOptions);

			List<ServiceItemOption> serviceItemOptions = userServiceItemService.getAuthorizedServiceItemOptions(userInfo.getId());
			if (!CollectionUtils.isEmpty(serviceItemOptions)) {
				if (serviceItemOptions.get(0).getServiceItemId() == 0L) {
					serviceItemOptions = serviceItemService.list().stream()
							.map(item -> new ServiceItemOption(item.getAppCode(), item.getId(), item.getServiceName())).collect(Collectors.toList());
				}
			}
			userProfile.setServiceItems(serviceItemOptions);

			List<MenuOption> menuOptions = userInfoService.getAuthorizedMenuOptions(userInfo.getId());
			userProfile.setMenus(menuOptions);

		}

		return userProfile;
	}

	@Override
	public UserProfile getUserProfileByMobile(String mobile) {
		UserProfile userProfile = null;
		UserInfo userInfo = userInfoService.getUserInfoByMobile(mobile);
		if (userInfo != null) {
			userProfile = sysiamBeanConverter.userInfoToUserProfile(userInfo);

			List<DataOption> orgOptions = userOrgService.getOrgOptionsByUser(userInfo.getId());
			userProfile.setOrgs(orgOptions);

			List<DataOption> tenantOptions = userTenantService.getAuthorizedTenantOptions(userInfo.getId());
			userProfile.setTenants(tenantOptions);

			List<ServiceItemOption> serviceItemOptions = userServiceItemService.getAuthorizedServiceItemOptions(userInfo.getId());
			if (!CollectionUtils.isEmpty(serviceItemOptions)) {
				if (serviceItemOptions.get(0).getServiceItemId() == 0L) {
					serviceItemOptions = serviceItemService.list().stream()
							.map(item -> new ServiceItemOption(item.getAppCode(), item.getId(), item.getServiceName())).collect(Collectors.toList());
				}
			}
			userProfile.setServiceItems(serviceItemOptions);

			List<MenuOption> menuOptions = userInfoService.getAuthorizedMenuOptions(userInfo.getId());
			userProfile.setMenus(menuOptions);

		}

		return userProfile;
	}

	@Override
	public UserDetails getUserDetails(Long userId) {
		UserInfo userInfo = userInfoService.getById(userId);
		UserDetails userDetails = sysiamBeanConverter.userInfoToUserDetails(userInfo);

		List<DataOption> orgOptions = userOrgService.getOrgOptionsByUser(userId);
		userDetails.setOrgs(orgOptions);

		List<ServiceItemOption> serviceItemOptions = userServiceItemService.getAuthorizedServiceItemOptions(userId);
		if (!CollectionUtils.isEmpty(serviceItemOptions)) {
			if (serviceItemOptions.get(0).getServiceItemId() == 0L) {
				serviceItemOptions = serviceItemService.list().stream()
						.map(item -> new ServiceItemOption(item.getAppCode(), item.getId(), item.getServiceName())).collect(Collectors.toList());
			}
		}
		userDetails.setServiceItems(serviceItemOptions);

		List<DataOption> roleOptions = userRoleService.getRoleOptionsByUser(userId);
		userDetails.setRoles(roleOptions);

		List<DataOption> tenants = userTenantService.getAuthorizedTenantOptions(userId);
		userDetails.setTenants(tenants);

		return userDetails;
	}

	@Override
	public PageInfo<UserRecord> queryUserRecords(QueryUserConditions queryConditions) {
		Page<UserRecord> thePage = PageHelper.startPage(queryConditions.getPage(), queryConditions.getSize());
		List<UserRecord> users = userInfoService.queryUserRecords(queryConditions);
		PageInfo<UserRecord> pageInfo = PageInfo.of(thePage);
		pageInfo.setList(users);

		return pageInfo;
	}

	public Long createUser(UserInfo userInfo, String password) throws ServiceException {
		return this.createUser(userInfo, password, null);
	}

	// TODO 分平台用户和运营商用户两种情况处理
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Long createUser(UserInfo userInfo, String password, List<Long> orgIds) throws ServiceException {
		UserInfo theUser = userInfoService.getUserInfoByName(userInfo.getName());
		if (theUser != null) {
			throw new UserException("账号名称已存在");
		}

		/**
		 * 保存用户基本信息
		 */
		long userId = uidService.generate();
		userInfo.setId(userId);
		userInfo.setCreateTime(DateUtil.date());
		userInfoService.save(userInfo);

		/**
		 * 保存用户身份凭证信息
		 */
		if (!ObjectUtils.isEmpty(password)) {
			UserCredential credential = new UserCredential();
			long credentialId = uidService.generate();
			credential.setId(credentialId);
			credential.setUserId(userId);
			credential.setIdentityType(IdentityType.NAME_PASSWORD.type());
			credential.setIdentifier(userInfo.getName());
			credential.setCredential(bcryptPasswordEncoder.encode(password));
			credential.setCreateTime(new Date());
			credential.setCreateBy(userInfo.getCreateBy());

			userCredentialService.save(credential);
		}

		/**
		 * 保存用户所属部门信息
		 */
		if (!CollectionUtils.isEmpty(orgIds)) {
			List<UserOrg> userOrgs = new ArrayList<>();
			Date now = new Date();

			for (Long orgId : orgIds) {
				long userDeptId = uidService.generate();

				UserOrg userOrg = new UserOrg();
				userOrg.setId(userDeptId);
				userOrg.setOrgId(orgId);
				userOrg.setUserId(userId);
				userOrg.setCreateTime(now);
				userOrg.setCreateBy(userInfo.getCreateBy());

				userOrgs.add(userOrg);
			}

			userOrgService.saveBatch(userOrgs);
		}

		return userId;
	}

	public void updateUser(UserInfo userInfo, String password) throws ServiceException {
		this.updateUser(userInfo, password, null);
	}

	// TODO 分平台用户和运营商用户两种情况处理
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void updateUser(UserInfo userInfo, String password, List<Long> orgIds) throws ServiceException {
		if (userInfo.getId() == null) {
			throw new UserException("用户ID不能为空");
		}

		UserInfo theUser = userInfoService.getUserInfoByName(userInfo.getName());
		if (theUser != null && !theUser.getId().equals(userInfo.getId())) {
			throw new UserException("账号名称已存在");
		}

		if (theUser == null) {
			theUser = userInfoService.getById(userInfo.getId());
		}
		userInfoService.updateById(userInfo);

		if (!ObjectUtils.isEmpty(password)) {
			userCredentialService.updateUserCredential(userInfo.getId(), IdentityType.NAME_PASSWORD.type(), bcryptPasswordEncoder.encode(password));
		}

		if (!CollectionUtils.isEmpty(orgIds)) {
			userOrgService.removeUserOrgs(userInfo.getId());

			List<UserOrg> userOrgs = new ArrayList<>();
			Date now = DateUtil.date();
			for (Long orgId : orgIds) {
				long userOrgId = uidService.generate();

				UserOrg userOrg = new UserOrg();
				userOrg.setId(userOrgId);
				userOrg.setOrgId(orgId);
				userOrg.setUserId(userInfo.getId());
				userOrg.setCreateTime(now);
				userOrg.setCreateBy(userInfo.getCreateBy());

				userOrgs.add(userOrg);
			}

			userOrgService.saveBatch(userOrgs);
		}
	}

	public boolean isPasswordValid(Long userId, String password) {
		String thePassword = userCredentialService.getUserCredential(userId, IdentityType.NAME_PASSWORD.type());

		return ObjectUtils.isEmpty(password) ? false : bcryptPasswordEncoder.matches(password, thePassword);
	}

	@Override
	public boolean checkUserIdentity(UserIdentity userIdentity) throws UserException {
		String credential = userCredentialService.getUserCredential(userIdentity.getUserId(), userIdentity.getIdentityType().type());

		switch (userIdentity.getIdentityType()) {
		case NAME_PASSWORD:
			if (ObjectUtils.isEmpty(credential)) {
				throw new UserException("账号或密码错误");
			}

			if (!bcryptPasswordEncoder.matches(userIdentity.getCredential(), credential)) {
				throw new UserException("账号或密码错误");
			}

			return true;
		default:
			return false;
		}
	}

	public void changePassword(Long userId, String newPassword) {
		userCredentialService.updateUserCredential(userId, IdentityType.NAME_PASSWORD.type(), bcryptPasswordEncoder.encode(newPassword));
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void removeAccount(Long userId) {
		userInfoService.removeUser(userId);
		userRoleService.removeUserRoles(userId);
		userCredentialService.removeUserCredentials(userId);
	}

	public void disableUser(Long userId) {
		userInfoService.disableUser(userId);
	}

	public void enableUser(Long userId) {
		userInfoService.enableUser(userId);
	}

	public List<DataOption> getAuthorizedTenants(Long userId) {
		return userTenantService.getAuthorizedTenantOptions(userId);
	}

	@Override
	public boolean hasAllTenants(Long userId) {
		return userTenantService.hasAllTenants(userId);
	}

	public UserBasic getAdministratorOfTenant(Long tenantId) {
		UserInfo userInfo = userInfoService.getAdministratorOfTenant(tenantId);
		return userInfo != null ? sysiamBeanConverter.userInfoToUserBasic(userInfo) : null;
	}

	public void authorizeTenantsToUser(Long userId, List<Long> tenantIds, String operator) throws ServiceException {
		List<UserTenant> authorizedTenantList = new ArrayList<>();
		Date now = DateUtil.date();

		userTenantService.removeAuthorizedTenants(userId);
		if (!CollectionUtils.isEmpty(tenantIds)) {
			for (Long tenantId : tenantIds) {
				UserTenant authorizedTenant = new UserTenant();

				long authorizationId = uidService.generate();
				authorizedTenant.setId(authorizationId);
				authorizedTenant.setUserId(userId);
				authorizedTenant.setTenantId(tenantId);
				authorizedTenant.setCreateTime(now);
				authorizedTenant.setCreateBy(operator);

				authorizedTenantList.add(authorizedTenant);
			}
			;
			userTenantService.saveBatch(authorizedTenantList);
		}
	}

	public boolean hasAllServiceItems(Long userId) {
		return userServiceItemService.hasAllServiceItems(userId);
	}

	public void authorizeServiceItemsToUser(Long userId, List<Long> serviceItemIds, String authorizer) throws ServiceException {
		List<UserServiceItem> authorizedServiceItemList = new ArrayList<>();
		Date now = DateUtil.date();

		userServiceItemService.removeAuthorizedServiceItems(userId);
		if (!CollectionUtils.isEmpty(serviceItemIds)) {
			for (Long serviceItemId : serviceItemIds) {
				UserServiceItem authorizedServiceItem = new UserServiceItem();

				long authorizationId = uidService.generate();
				authorizedServiceItem.setId(authorizationId);
				authorizedServiceItem.setUserId(userId);
				authorizedServiceItem.setServiceItemId(serviceItemId);
				authorizedServiceItem.setCreateTime(now);
				authorizedServiceItem.setCreateBy(authorizer);

				authorizedServiceItemList.add(authorizedServiceItem);
			}
			;
			userServiceItemService.saveBatch(authorizedServiceItemList);
		}
	}

	@Override
	public List<DataOption> getAuthorizedServiceItems(Long userId) {
		List<ServiceItemOption> serviceItems = userServiceItemService.getAuthorizedServiceItemOptions(userId);
		if (!CollectionUtils.isEmpty(serviceItems)) {
			if (serviceItems.get(0).getServiceItemId() == 0L) {
				serviceItems = serviceItemService.list().stream().map(item -> new ServiceItemOption(item.getAppCode(), item.getId(), item.getServiceName()))
						.collect(Collectors.toList());
			}
		}

		return serviceItems.stream().map(item -> new DataOption(item.getServiceItemId().toString(), item.getServiceItemName())).collect(Collectors.toList());
	}

	public List<DataOption> getRoleOptionsByUser(Long userId) {
		return userRoleService.getRoleOptionsByUser(userId);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void authorizeRolesToUser(Long userId, List<Long> roleIds, String creator) throws ServiceException {
		List<UserRole> userRoles = new ArrayList<>();
		Date now = DateUtil.date();

		userRoleService.removeUserRoles(userId);
		for (Long roleId : roleIds) {
			UserRole userRole = new UserRole();

			long userRoleId = uidService.generate();
			userRole.setId(userRoleId);
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			userRole.setCreateTime(now);
			userRole.setCreateBy(creator);

			userRoles.add(userRole);
		}

		userRoleService.saveBatch(userRoles);
	}

	@Override
	public Long saveUser(UserBasic userBasic) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(PasswordChanging passwordChanging) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DataOption> getAuthorizedRolesOfUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void authorizeRolesToUser(RoleAuthorizing roleAuthorizing) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DataOption> getAuthorizedTenantsOfUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void authorizeTenantsToUser(TenantAuthorizing tenantAuthorizing) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public UserProfile getUserProfileByUid(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUser(LongId userId) throws ServiceException {
		// TODO Auto-generated method stub

	}

}