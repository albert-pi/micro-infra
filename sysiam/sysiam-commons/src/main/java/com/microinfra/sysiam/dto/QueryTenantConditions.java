package com.microinfra.sysiam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryTenantConditions {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("联系人电话")
	private String phone;

	@ApiModelProperty("运营商ID")
	private Long tenantId;

	@ApiModelProperty("分页号")
	private Integer page = 1;

	@ApiModelProperty("返回记录数量")
	private Integer size = 10;

}