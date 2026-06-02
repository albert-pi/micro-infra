package com.microinfra.sysiam.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.LoginData;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.client.LogFeignClient;
import com.microinfra.sysiam.dto.QueryLoginConditions;
import com.microinfra.sysiam.facade.LogManager;
import com.microinfra.sysiam.vo.LoginRecord;

@RestController
public class LogFeignController implements LogFeignClient {

	@Resource
	private LogManager logManager;

	public PageInfo<LoginRecord> queryLoginRecords(QueryLoginConditions queryLoginConditions) {
		return logManager.queryLoginRecords(queryLoginConditions);
	}

	public void saveLoginLog(LoginData loginData) throws ServiceException {
		logManager.saveLoginLog(loginData);
	}

}