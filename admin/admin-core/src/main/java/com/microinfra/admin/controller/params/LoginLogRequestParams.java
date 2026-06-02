package com.microinfra.admin.controller.params;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONField;
import com.microinfra.commons.bean.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class LoginLogRequestParams {

	@Data
	@EqualsAndHashCode(callSuper = false)
	@ApiModel(value = "QueryLoginParam", description = "登录日志查询参数")
	public static class QueryLoginParams extends PageParams {

		@ApiModelProperty("开始时间。格式：yyyy-MM-dd HH:mm:ss")
		@JSONField(format = "yyyy-MM-dd HH:mm:ss")
		private Date loginStart;

		@ApiModelProperty("结束时间。格式：yyyy-MM-dd HH:mm:ss")
		@JSONField(format = "yyyy-MM-dd HH:mm:ss")
		private String loginEnd;

		@ApiModelProperty("账号名称")
		private String userName;

		@ApiModelProperty("账号登录IP地址")
		private String clientIp;

	}

}