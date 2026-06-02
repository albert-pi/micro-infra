package com.microinfra.passport.authenticator;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.microinfra.commons.bean.IdentityType;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.passport.params.AccountCredentials;
import com.microinfra.passport.security.Authenticator;
import com.microinfra.passport.service.CaptchaService;
import com.microinfra.sysiam.dto.UserIdentity;
import com.microinfra.sysiam.facade.UserService;

@Component
public class DefaultAuthenticator implements Authenticator<AccountCredentials> {

	@Resource
	private UserService userService;

	@Resource
	private CaptchaService captchaService;

	@Override
	public IdentityType getIdentityType() {
		return IdentityType.NAME_PASSWORD;
	}

	@Override
	public void validate(AccountCredentials credentials) throws AuthenticationException {
		if (Strings.isNullOrEmpty(credentials.getName())) {
			throw new AuthenticationServiceException("账号名称不能为空");
		}
		if (Strings.isNullOrEmpty(credentials.getPassword())) {
			throw new AuthenticationServiceException("账号密码不能为空");
		}
		if (Strings.isNullOrEmpty(credentials.getCaptchaKey())) {
			throw new AuthenticationServiceException("验证码Key不能为空");
		}
		if (Strings.isNullOrEmpty(credentials.getCaptchaCode())) {
			throw new AuthenticationServiceException("验证码不能为空");
		}

		String cachedCaptchaCode = captchaService.pollCaptcha(credentials.getCaptchaKey());
		if (cachedCaptchaCode == null) {
			throw new AuthenticationServiceException("验证码过期");
		}
		if (!credentials.getCaptchaCode().equalsIgnoreCase(cachedCaptchaCode)) {
			throw new AuthenticationServiceException("验证码错误");
		}
	}

	@Override
	public UserProfile authenticate(AccountCredentials credential) throws AuthenticationException {
		UserProfile userProfile = userService.getUserProfileByName(credential.getName());
		if (userProfile == null) {
			throw new UsernameNotFoundException("账号或密码错误");
		} else if (1 == userProfile.getStatus()) {
			throw new DisabledException("账号被禁用");
		}

		// TODO "输错密码次数达到上限，请在" + lockDuration + "分钟后再试");
		// TODO "IP被限制登录，请在" + lockDuration + "分钟后再试");

		try {
			UserIdentity userIdentity = new UserIdentity();
			userIdentity.setUserId(userProfile.getId());
			userIdentity.setIdentityType(IdentityType.NAME_PASSWORD);
			userIdentity.setCredential(credential.getPassword());

			if (!userService.checkUserIdentity(userIdentity)) {
				throw new BadCredentialsException("账号或密码错误");
			}
		} catch (ServiceException e) {
			throw new BadCredentialsException(e.getMessage());
		}

		return userProfile;
	}

}
