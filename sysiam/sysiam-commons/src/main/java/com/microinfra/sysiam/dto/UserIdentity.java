package com.microinfra.sysiam.dto;

import com.microinfra.commons.bean.IdentityType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserIdentity {

	@ApiModelProperty("用户ID")
	private Long userId;

	@ApiModelProperty("身份验证类别")
	private IdentityType identityType;

	@ApiModelProperty("凭证")
	private String credential;

}