package com.microinfra.passport.security;

import org.springframework.security.core.AuthenticationException;

public class AuthenticatorNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = -5665489577365655751L;

	public AuthenticatorNotFoundException(String msg) {
		super(msg);
	}

	public AuthenticatorNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
