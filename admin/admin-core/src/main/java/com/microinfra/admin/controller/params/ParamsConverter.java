package com.microinfra.admin.controller.params;

import org.mapstruct.Mapper;

import com.microinfra.admin.controller.params.AccountRequestParams.QueryUserParams;
import com.microinfra.admin.controller.params.AccountRequestParams.UserFormParams;
import com.microinfra.admin.controller.params.DeptRequestParams.OrgFormParams;
import com.microinfra.admin.controller.params.DeptRequestParams.QueryOrgParams;
import com.microinfra.admin.controller.params.LoginLogRequestParams.QueryLoginParams;
import com.microinfra.admin.controller.params.MenuRequestParams.MenuFormParams;
import com.microinfra.admin.controller.params.RoleRequestParams.QueryRoleParams;
import com.microinfra.admin.controller.params.RoleRequestParams.RoleFormParams;
import com.microinfra.admin.controller.params.TenantRequestParams.QueryTenantParams;
import com.microinfra.admin.controller.params.TenantRequestParams.TenantFormParams;
import com.microinfra.sysiam.dto.MenuBasic;
import com.microinfra.sysiam.dto.OrgBasic;
import com.microinfra.sysiam.dto.QueryLoginConditions;
import com.microinfra.sysiam.dto.QueryOrgConditions;
import com.microinfra.sysiam.dto.QueryRoleConditions;
import com.microinfra.sysiam.dto.QueryTenantConditions;
import com.microinfra.sysiam.dto.QueryUserConditions;
import com.microinfra.sysiam.dto.RoleBasic;
import com.microinfra.sysiam.dto.TenantBasic;
import com.microinfra.sysiam.dto.UserBasic;

@Mapper(componentModel = "spring")
public interface ParamsConverter {

	QueryTenantConditions queryTenantParamsToQueryTenantConditions(QueryTenantParams params);

	TenantBasic tenantFormParamsToTenantBasic(TenantFormParams params);

	QueryOrgConditions queryOrgParamsToQueryOrgConditions(QueryOrgParams params);

	OrgBasic orgFormParamsToOrgBasic(OrgFormParams params);

	MenuBasic menuFormParamsToMenuBasic(MenuFormParams params);

	QueryRoleConditions queryRoleParamsToQueryRoleConditions(QueryRoleParams params);

	RoleBasic roleFormParamsToRoleBasic(RoleFormParams params);

	QueryUserConditions queryUserParamsToQueryUserConditions(QueryUserParams params);

	UserBasic userFormParamsToUserBasic(UserFormParams params);

	QueryLoginConditions queryLoginParamsToQueryLoginConditions(QueryLoginParams params);

}