package com.microinfra.sysiam.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleAuthorizing {

	@ApiModelProperty("用户ID")
	private Long userId;

	@ApiModelProperty("角色ID列表")
	private List<Long> roleIds;

	@ApiModelProperty("授权者")
	private String authorizer;

}