package com.microinfra.sysiam.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryLoginConditions {

	@ApiModelProperty("登录用户类型。1-平台用户；2-运营商用户；...")
	private Integer userType;

	@ApiModelProperty("登录用户所属运营商ID")
	private Long tenantId;

	@ApiModelProperty("开始时间")
	private Date loginStart;

	@ApiModelProperty("结束时间")
	private Date loginEnd;

	@ApiModelProperty("账号名称")
	private String name;

	@ApiModelProperty("账号登录IP地址")
	private String clientIp;

	@ApiModelProperty("分页号")
	private Integer page = 1;

	@ApiModelProperty("返回记录数量")
	private Integer size = 10;

}