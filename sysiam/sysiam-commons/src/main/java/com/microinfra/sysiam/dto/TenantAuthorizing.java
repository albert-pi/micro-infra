package com.microinfra.sysiam.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TenantAuthorizing {

	@ApiModelProperty("用户ID")
	private Long userId;

	@ApiModelProperty("授权给用户管理的运营商ID列表")
	private List<Long> tenantIds;

	@ApiModelProperty("授权者")
	private String authorizer;

}