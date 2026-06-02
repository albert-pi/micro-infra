package com.microinfra.passport.model;

import com.microinfra.commons.bean.UserProfile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "LoginResult", description = "登录返回结果")
public class LoginResult {

	@ApiModelProperty("访问接口所需的token，后续接口需要在请求头中携带此token，请求头格式：Authorization: Bearer {token}。只在没有rememberMe参数的情况下返回")
	private String token;

	@ApiModelProperty("用户个人资料")
	private UserProfile user;

}