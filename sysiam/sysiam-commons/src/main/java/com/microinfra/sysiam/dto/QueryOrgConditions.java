package com.microinfra.sysiam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryOrgConditions {

	@ApiModelProperty("组织机构名称")
	private String name;

	@ApiModelProperty("类型。1-平台组织机构；2-运营商组织机构；")
	private Integer type;

	@ApiModelProperty("运营商ID")
	private Long tenantId;

	@ApiModelProperty("分页号")
	private Integer page = 1;

	@ApiModelProperty("返回记录数量")
	private Integer size = 10;

}