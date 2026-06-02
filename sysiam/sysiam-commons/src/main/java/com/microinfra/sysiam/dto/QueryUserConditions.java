package com.microinfra.sysiam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryUserConditions {

	@ApiModelProperty("账号名称")
	private String name;

	@ApiModelProperty("手机号码")
	private String mobile;

	@ApiModelProperty("姓名或昵称")
	private String nick;

	@ApiModelProperty("状态。0-正常；1-禁用；")
	private Integer status;

	@ApiModelProperty("类型。1-平台用户；2-运营商用户；...")
	private Integer type;

	@ApiModelProperty("运营商ID")
	private Long tenantId;

	@ApiModelProperty("分页号")
	private Integer page = 1;

	@ApiModelProperty("返回记录数量")
	private Integer size = 10;

}