package com.microinfra.sysiam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryRoleConditions {

	@ApiModelProperty("角色名称")
	private String name;

	@ApiModelProperty("类型。1-平台角色；2-运营商角色；...")
	private Integer type;

	@ApiModelProperty("运营商ID")
	private Long tenantId;

	@ApiModelProperty("分页号")
	private Integer page = 1;

	@ApiModelProperty("返回记录数量")
	private Integer size = 10;

}