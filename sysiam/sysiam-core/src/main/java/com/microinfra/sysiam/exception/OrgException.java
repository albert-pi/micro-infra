package com.microinfra.sysiam.exception;

import com.microinfra.commons.lang.ServiceException;

public class OrgException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public OrgException(String msg) {
		super(msg);
	}

	public OrgException(Exception cause) {
		super(cause);
	}

	public OrgException(String msg, Exception cause) {
		super(msg, cause);
	}

}