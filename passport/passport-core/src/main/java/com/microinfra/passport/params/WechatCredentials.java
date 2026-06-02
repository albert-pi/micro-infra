package com.microinfra.passport.params;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WechatCredentials", description = "微信登录凭证")
public class WechatCredentials extends PassportCredentials {

	@NotBlank(message = "微信code不能为空")
	@ApiModelProperty("微信code")
	private String code;

	@Override
	public String getUserName() {
		return null;
	}

}