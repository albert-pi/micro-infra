package com.microinfra.passport.params;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SmsCredentials", description = "短信登录凭证")
public class SmsCredentials extends PassportCredentials {

	@NotBlank(message = "手机号码不能为空")
	@ApiModelProperty("手机号码")
	private String mobile;

	@NotBlank(message = "验证码不能为空")
	@ApiModelProperty("验证码")
	private String code;

	@ApiModelProperty("记住我。可能的值：'true', 'on', 'yes', '1'")
	private String rememberMe;

	@Override
	public String getUserName() {
		return this.mobile;
	}

}