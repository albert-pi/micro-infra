package com.microinfra.passport.security;

import org.springframework.security.core.AuthenticationException;

import com.microinfra.commons.bean.IdentityType;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.passport.params.PassportCredentials;

public interface Authenticator<C extends PassportCredentials> {

	public IdentityType getIdentityType();

	public void validate(C credentials) throws AuthenticationException;

	public UserProfile authenticate(C credentials) throws AuthenticationException;

}