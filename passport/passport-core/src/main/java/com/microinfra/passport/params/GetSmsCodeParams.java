package com.microinfra.passport.params;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "GetSmsCodeParams", description = "获取短信验证码参数")
public class GetSmsCodeParams {

	@NotBlank(message = "手机号码不能为空")
	@ApiModelProperty("手机号码")
	private String mobile;

}