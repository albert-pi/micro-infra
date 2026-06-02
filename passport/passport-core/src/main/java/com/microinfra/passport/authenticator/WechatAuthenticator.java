package com.microinfra.passport.authenticator;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.microinfra.commons.bean.IdentityType;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.passport.params.WechatCredentials;
import com.microinfra.passport.security.Authenticator;

@Component
public class WechatAuthenticator implements Authenticator<WechatCredentials> {

	@Override
	public IdentityType getIdentityType() {
		return IdentityType.WECHAT;
	}

	@Override
	public void validate(WechatCredentials credentials) throws AuthenticationException {
		// TODO
		if (Strings.isNullOrEmpty(credentials.getCode())) {
			throw new AuthenticationServiceException("微信openid不能为空");
		}
//		if (Strings.isNullOrEmpty(token)) {
//			throw new AuthenticationServiceException("微信token不能为空");
//		}
	}

	@Override
	public UserProfile authenticate(WechatCredentials credentials) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

}
