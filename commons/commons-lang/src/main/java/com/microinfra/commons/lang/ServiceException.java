package com.microinfra.commons.lang;

public class ServiceException extends BizException {

	private static final long serialVersionUID = 1L;

	public ServiceException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public ServiceException(Exception cause) {
		super(cause);
	}

	public ServiceException(String msg, Exception cause) {
		super(msg, cause);
	}

}