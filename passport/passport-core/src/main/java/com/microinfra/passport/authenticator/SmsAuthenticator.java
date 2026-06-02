package com.microinfra.passport.authenticator;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.microinfra.commons.bean.IdentityType;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.passport.params.SmsCredentials;
import com.microinfra.passport.security.Authenticator;
import com.microinfra.sysiam.facade.UserService;

@Component
public class SmsAuthenticator implements Authenticator<SmsCredentials> {

	@Resource
	private UserService userService;

	@Override
	public IdentityType getIdentityType() {
		return IdentityType.SMS;
	}

	@Override
	public void validate(SmsCredentials credentials) throws AuthenticationException {
		if (Strings.isNullOrEmpty(credentials.getMobile())) {
			throw new AuthenticationServiceException("手机号码不能为空");
		}
		if (Strings.isNullOrEmpty(credentials.getCode())) {
			throw new AuthenticationServiceException("验证码不能为空");
		}
	}

	@Override
	public UserProfile authenticate(SmsCredentials credentials) throws AuthenticationException {
		UserProfile userProfile = userService.getUserProfileByMobile(credentials.getMobile());
		if (userProfile == null) {
			throw new UsernameNotFoundException("无效的手机号码");
		} else if (1 == userProfile.getStatus()) {
			throw new DisabledException("账号被禁用");
		}

		// TODO 使用消息推送服务
//		String code = smsService.getLoginVerifyCode(params.getMobile());
//		if (StrUtil.isBlank(code)) {
//			throw new BadCredentialsException("验证码错误");
//		}
//
//		if (!code.equals(verifyCode)) {
//			throw new BadCredentialsException("验证码错误");
//		}

		return userProfile;
	}

}
