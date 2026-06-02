package com.microinfra.passport.params;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class PassportCredentials {

	@NotNull(message = "登录方式不能为空")
	@ApiModelProperty("登录方式。1-用户名和密码登录；2-短信验证码登录；3-微信登录；")
	protected Integer type;

	public abstract String getUserName();

}