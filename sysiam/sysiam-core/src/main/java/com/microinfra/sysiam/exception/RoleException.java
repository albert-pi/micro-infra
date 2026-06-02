package com.microinfra.sysiam.exception;

import com.microinfra.commons.lang.ServiceException;

public class RoleException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public RoleException(String msg) {
		super(msg);
	}

	public RoleException(Exception cause) {
		super(cause);
	}

	public RoleException(String msg, Exception cause) {
		super(msg, cause);
	}

}