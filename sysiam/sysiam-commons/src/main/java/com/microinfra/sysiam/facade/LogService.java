package com.microinfra.sysiam.facade;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.LoginData;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.dto.QueryLoginConditions;
import com.microinfra.sysiam.vo.LoginRecord;

public interface LogService {

	public PageInfo<LoginRecord> queryLoginRecords(QueryLoginConditions queryLoginConditions);

	public void saveLoginLog(LoginData loginData) throws ServiceException;

}