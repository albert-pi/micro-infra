package com.microinfra.sysiam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PasswordChanging {

	@ApiModelProperty("用户ID")
	private Long userId;

	@ApiModelProperty("原密码")
	private String oldPwd;

	@ApiModelProperty("新密码")
	private String newPwd;

}