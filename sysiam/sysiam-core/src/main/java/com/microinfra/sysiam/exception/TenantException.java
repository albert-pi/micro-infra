package com.microinfra.sysiam.exception;

import com.microinfra.commons.lang.ServiceException;

public class TenantException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public TenantException(String msg) {
		super(msg);
	}

	public TenantException(Exception cause) {
		super(cause);
	}

	public TenantException(String msg, Exception cause) {
		super(msg, cause);
	}

}