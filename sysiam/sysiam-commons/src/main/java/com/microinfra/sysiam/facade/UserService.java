package com.microinfra.sysiam.facade;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.dto.PasswordChanging;
import com.microinfra.sysiam.dto.QueryUserConditions;
import com.microinfra.sysiam.dto.RoleAuthorizing;
import com.microinfra.sysiam.dto.TenantAuthorizing;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.dto.UserIdentity;
import com.microinfra.sysiam.vo.UserDetails;
import com.microinfra.sysiam.vo.UserRecord;

public interface UserService {

	public PageInfo<UserRecord> queryUserRecords(QueryUserConditions conditions);

	public UserDetails getUserDetails(Long userId);

	public Long saveUser(UserBasic userBasic) throws ServiceException;

	public boolean hasAllTenants(Long userId);

	public List<DataOption> getAuthorizedServiceItems(Long userId);

	public void changePassword(PasswordChanging passwordChanging) throws ServiceException;

	public List<DataOption> getAuthorizedRolesOfUser(Long userId);

	public void authorizeRolesToUser(RoleAuthorizing roleAuthorizing) throws ServiceException;

	public List<DataOption> getAuthorizedTenantsOfUser(Long userId);

	public void authorizeTenantsToUser(TenantAuthorizing tenantAuthorizing) throws ServiceException;

	public UserProfile getUserProfileByUid(Long userId);

	public UserProfile getUserProfileByName(String name);

	public UserProfile getUserProfileByMobile(String mobile);

	public boolean checkUserIdentity(UserIdentity userIdentity) throws ServiceException;

	public void removeUser(LongId userId) throws ServiceException;

}