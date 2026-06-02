package com.microinfra.admin.controller;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.microinfra.admin.controller.params.ParamsConverter;
import com.microinfra.admin.controller.params.LoginLogRequestParams.QueryLoginParams;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.framework.CurrentUser;
import com.microinfra.sysiam.dto.QueryLoginConditions;
import com.microinfra.sysiam.facade.LogService;
import com.microinfra.sysiam.vo.LoginRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "日志管理接口")
@RestController
@Validated
public class LogController {

	@Resource
	private LogService logService;

	@Resource
	private ParamsConverter paramsConverter;

	@ApiOperation(produces = "application/json", value = "查询登录日志", notes = "查询登录日志")
	@PostMapping("/log/logins/query")
	public PageInfo<LoginRecord> queryLoginRecord(@ApiIgnore @CurrentUser UserProfile userProfile, @RequestBody QueryLoginParams params) {
		QueryLoginConditions queryConditions = paramsConverter.queryLoginParamsToQueryLoginConditions(params);
		queryConditions.setUserType(userProfile.getType());
		queryConditions.setTenantId(userProfile.getTenantId());

		return logService.queryLoginRecords(queryConditions);
	}

}