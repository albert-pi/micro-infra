package com.microinfra.sysiam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TenantAdministrator {

	@ApiModelProperty("运营商ID")
	private Long tenantId;

	@ApiModelProperty("登录账号名称")
	private String name;

	@ApiModelProperty("账号密码")
	private String password;

	@ApiModelProperty("手机号码")
	private String mobile;

	@ApiModelProperty("创建者账号名称")
	private String createBy;

}