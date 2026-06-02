package com.microinfra.sysiam.exception;

import com.microinfra.commons.lang.ServiceException;

public class MenuException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public MenuException(String msg) {
		super(msg);
	}

	public MenuException(Exception cause) {
		super(cause);
	}

	public MenuException(String msg, Exception cause) {
		super(msg, cause);
	}

}