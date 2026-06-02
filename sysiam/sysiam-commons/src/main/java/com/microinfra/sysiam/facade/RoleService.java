package com.microinfra.sysiam.facade;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.dto.QueryRoleConditions;
import com.microinfra.sysiam.dto.RoleBasic;
import com.microinfra.sysiam.vo.RoleDetails;
import com.microinfra.sysiam.vo.RoleRecord;

public interface RoleService {

	public List<DataOption> getAssignableRoleOptions(Integer type, Long tenantId);

	public PageInfo<RoleRecord> queryRole(QueryRoleConditions conditions);

	public RoleDetails getRoleDetails(Long roleId);

	public Long saveRole(RoleBasic roleBasic) throws ServiceException;

	public void removeRole(LongId roleId) throws ServiceException;

}