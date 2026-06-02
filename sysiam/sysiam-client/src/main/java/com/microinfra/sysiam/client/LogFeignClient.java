package com.microinfra.sysiam.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.LoginData;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.rpc.feign.FeignConfiguration;
import com.microinfra.sysiam.dto.QueryLoginConditions;
import com.microinfra.sysiam.facade.LogService;
import com.microinfra.sysiam.vo.LoginRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("系统日志数据接口")
@FeignClient(name = Sysiam.SERVICE_NAME, contextId = "logService", configuration = FeignConfiguration.class)
public interface LogFeignClient extends LogService {

	@Override
	@ApiOperation(produces = "application/json", value = "查询登录日志")
	@PostMapping("/internal/log/login/query")
	public PageInfo<LoginRecord> queryLoginRecords(QueryLoginConditions queryLoginConditions);

	@Override
	@ApiOperation(produces = "application/json", value = "保存用户登录日志")
	@PostMapping("/internal/log/login/save")
	public void saveLoginLog(@RequestBody LoginData loginData) throws ServiceException;

}