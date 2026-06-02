package com.microinfra.passport.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "CaptchaInfo", description = "登录验证码信息")
public class CaptchaInfo {

	@ApiModelProperty("验证码唯一标识")
	private String captchaKey;

	@ApiModelProperty("验证码图片的BASE64编码")
	private String captchaImg;

}