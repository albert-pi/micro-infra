package com.microinfra.passport.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.microinfra.commons.bean.Result;
import com.microinfra.framework.ServletHelper;

@Component("authenticationEntryPoint")
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		ServletHelper.outputJSON(response, Result.<Void>builder().code(HttpStatus.UNAUTHORIZED.value()).msg(e.getMessage()).build());
	}

}
