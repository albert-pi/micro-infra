package com.microinfra.sysiam.exception;

import com.microinfra.commons.lang.ServiceException;

public class UserException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public UserException(String msg) {
		super(msg);
	}

	public UserException(Exception cause) {
		super(cause);
	}

	public UserException(String msg, Exception cause) {
		super(msg, cause);
	}

}