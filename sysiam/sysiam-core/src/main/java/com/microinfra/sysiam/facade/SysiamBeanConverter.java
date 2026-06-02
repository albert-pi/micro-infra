package com.microinfra.sysiam.facade;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.microinfra.commons.bean.LoginData;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.sysiam.dto.MenuBasic;
import com.microinfra.sysiam.dto.OrgBasic;
import com.microinfra.sysiam.dto.RoleBasic;
import com.microinfra.sysiam.dto.TenantBasic;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.entity.LoginLog;
import com.microinfra.sysiam.entity.MenuInfo;
import com.microinfra.sysiam.entity.OrgInfo;
import com.microinfra.sysiam.entity.RoleInfo;
import com.microinfra.sysiam.entity.TenantInfo;
import com.microinfra.sysiam.entity.UserInfo;
import com.microinfra.sysiam.vo.LoginRecord;
import com.microinfra.sysiam.vo.MenuDetails;
import com.microinfra.sysiam.vo.MenuRecord;
import com.microinfra.sysiam.vo.OrgDetails;
import com.microinfra.sysiam.vo.RoleDetails;
import com.microinfra.sysiam.vo.TenantDetails;
import com.microinfra.sysiam.vo.UserDetails;

@Mapper(componentModel = "spring")
public interface SysiamBeanConverter {

	@Mapping(target = "avatar", ignore = true)
	@Mapping(target = "deleteStatus", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "loginIp", ignore = true)
	@Mapping(target = "loginTime", ignore = true)
	@Mapping(target = "status", ignore = true)
	UserInfo userBasicToUserInfo(UserBasic userBasic);

	@Mapping(target = "orgIds", ignore = true)
	@Mapping(target = "password", ignore = true)
	UserBasic userInfoToUserBasic(UserInfo userInfo);

	@Mapping(target = "serviceItems", ignore = true)
	@Mapping(target = "appServicesMappings", ignore = true)
	@Mapping(target = "serviceAuthoritiesMappings", ignore = true)
	@Mapping(target = "menus", ignore = true)
	@Mapping(target = "orgs", ignore = true)
	@Mapping(target = "tenants", ignore = true)
	UserProfile userInfoToUserProfile(UserInfo userInfo);

	@Mapping(target = "serviceItems", ignore = true)
	@Mapping(target = "orgs", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "tenants", ignore = true)
	UserDetails userInfoToUserDetails(UserInfo userInfo);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "location", ignore = true)
	@Mapping(target = "logTime", ignore = true)
	@Mapping(target = "nick", ignore = true)
	@Mapping(target = "result", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	LoginLog loginDataToLoginLog(LoginData loginData);

	LoginRecord loginLogToLoginRecord(LoginLog loginLog);

	@Mapping(target = "deleteStatus", ignore = true)
	@Mapping(target = "status", ignore = true)
	TenantInfo tenantBasicToTenantInfo(TenantBasic tenantBasic);

	@Mapping(target = "deleteStatus", ignore = true)
	@Mapping(target = "geoHash", ignore = true)
	TenantInfo tenantDetailsToTenantInfo(TenantDetails tenantDetails);

	@Mapping(target = "services", ignore = true)
	TenantDetails tenantInfoToTenantDetails(TenantInfo tenantInfo);

	@Mapping(target = "deleteStatus", ignore = true)
	@Mapping(target = "sort", ignore = true)
	@Mapping(target = "status", ignore = true)
	OrgInfo orgBasicToOrgInfo(OrgBasic orgBasic);

	@Mapping(target = "parentName", ignore = true)
	@Mapping(target = "tenantName", ignore = true)
	OrgDetails orgInfoToOrgDetails(OrgInfo orgInfo);

	@Mapping(target = "actionId", ignore = true)
	@Mapping(target = "deleteStatus", ignore = true)
	@Mapping(target = "status", ignore = true)
	MenuInfo menuBasicToMenuInfo(MenuBasic menu);

	@Mapping(target = "children", ignore = true)
	MenuRecord menuInfoToMenuRecord(MenuInfo menuInfo);

	@Mapping(target = "appCode", ignore = true)
	@Mapping(target = "appName", ignore = true)
	@Mapping(target = "serviceName", ignore = true)
	MenuDetails menuInfoToMenuDetails(MenuInfo menuInfo);

	@Mapping(target = "deleteStatus", ignore = true)
	RoleInfo roleBasicToRoleInfo(RoleBasic roleBasic);

	@Mapping(target = "menus", ignore = true)
	@Mapping(target = "tenantName", ignore = true)
	RoleDetails roleInfoToRoleDetails(RoleInfo roleInfo);

}