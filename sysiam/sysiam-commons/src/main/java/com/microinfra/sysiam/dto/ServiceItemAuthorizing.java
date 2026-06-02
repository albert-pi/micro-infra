package com.microinfra.sysiam.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceItemAuthorizing {

	@ApiModelProperty("运营商ID")
	private Long tenantId;

	@ApiModelProperty("授权给运营商使用的服务项目ID列表")
	private List<Long> serviceItemIds;

	@ApiModelProperty("授权者")
	private String authorizer;

}