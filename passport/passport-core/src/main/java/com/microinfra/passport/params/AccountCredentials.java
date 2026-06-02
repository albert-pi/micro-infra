package com.microinfra.passport.params;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountCredentials", description = "系统账号登录凭证")
public class AccountCredentials extends PassportCredentials {

	@NotBlank(message = "账号名称不能为空")
	@ApiModelProperty("账号名称")
	private String name;

	@NotBlank(message = "密码不能为空")
	@ApiModelProperty("账号密码。密码以密文形式上传，密码密文通过对原始密码取MD5哈希值获得")
	private String password;

	@NotBlank(message = "验证码不能为空")
	@ApiModelProperty("验证码Key")
	private String captchaKey;

	@NotBlank(message = "验证码不能为空")
	@ApiModelProperty("验证码")
	private String captchaCode;

	@ApiModelProperty("记住我。可能的值：'true', 'on', 'yes', '1'")
	private String rememberMe;

	@Override
	public String getUserName() {
		return this.name;
	}

}