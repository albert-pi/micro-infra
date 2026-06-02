package com.microinfra.sysiam.facade;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.dto.QueryTenantConditions;
import com.microinfra.sysiam.dto.ServiceItemAuthorizing;
import com.microinfra.sysiam.dto.TenantAdministrator;
import com.microinfra.sysiam.dto.TenantBasic;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.vo.TenantDetails;
import com.microinfra.sysiam.vo.TenantRecord;

public interface TenantService {

	public List<DataOption> getTenantOptions();

	public PageInfo<TenantRecord> queryTenantRecords(QueryTenantConditions queryConditions);

	public TenantDetails getTenantDetails(Long tenantId);

	public Long saveTenant(TenantBasic tenantBasic) throws ServiceException;

	public UserBasic getAdministrator(Long tenantId);

	public void setAdministrator(TenantAdministrator administrator) throws ServiceException;

	public List<DataOption> getAuthorizedServiceItems(Long tenantId);

	public void authorizeServiceItems(ServiceItemAuthorizing authorizing) throws ServiceException;

	public void removeTenant(LongId tenantId);

}