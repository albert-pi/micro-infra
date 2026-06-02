package com.microinfra.passport.controller;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microinfra.passport.model.CaptchaInfo;
import com.microinfra.passport.model.LoginResult;
import com.microinfra.passport.params.AccountCredentials;
import com.microinfra.passport.params.GetSmsCodeParams;
import com.microinfra.passport.service.CaptchaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "登录接口")
@RestController
@Validated
public class LoginController {

	@Resource
	private CaptchaService captchaService;

	@ApiOperation(produces = "application/json", value = "获取登录验证码", notes = "获取登录验证码")
	@ApiResponses({ //
			@ApiResponse(code = 200, message = "请求成功", response = CaptchaInfo.class), //
	})
	@RequestMapping("/captcha")
	public CaptchaInfo getCaptcha() {
		return captchaService.generateCaptcha();
	}

	@ApiOperation(produces = "application/json", value = "获取登录验证码", notes = "获取登录验证码")
	@ApiResponses({ //
			@ApiResponse(code = 200, message = "请求成功", response = CaptchaInfo.class), //
	})
	@PostMapping("/sms/login-code")
	public void sendSmsCode(@Validated @RequestBody GetSmsCodeParams params) {
		// TODO 在消息推送服务中处理
//		smsService.sendLoginVerifyCode(params.getMobile());
	}

	@ApiOperation(produces = "application/json", value = "获得换取微信code的地址", notes = "获得换取微信code的地址")
	@ApiResponses({ //
			@ApiResponse(code = 200, message = "请求成功", response = String.class), //
	})
	@GetMapping("/wechat-code-url")
	public String getWechatCodeUrl() {
		return "";// TODO
	}

	/**
	 * 注意：此处代码不处理登录逻辑，只是用来生成Swagger文档
	 * 
	 * @return
	 */
	@ApiOperation(produces = "application/json", value = "登录系统", notes = "登录系统")
	@ApiResponses({ //
			@ApiResponse(code = 200, message = "请求成功", response = LoginResult.class), //
	})
	@PostMapping("/login")
	public LoginResult login(@Validated @RequestBody AccountCredentials params) {
		return null;
	}

	/**
	 * 注意：此处代码不处理退出登录逻辑，只是用来生成Swagger文档
	 * 
	 * @return
	 */
	@ApiOperation(produces = "application/json", value = "退出登录", notes = "退出登录")
	@ApiResponses({ //
			@ApiResponse(code = 200, message = "请求成功", response = Void.class), //
	})
	@PostMapping("/logout")
	public void logout() {
	}

}
