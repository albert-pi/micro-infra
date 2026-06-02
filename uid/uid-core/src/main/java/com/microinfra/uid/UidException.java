package com.microinfra.uid;

import com.microinfra.commons.lang.ServiceException;

public class UidException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public UidException(String msg) {
		super(msg);
	}

	public UidException(Exception cause) {
		super(cause);
	}

	public UidException(String msg, Exception cause) {
		super(msg, cause);
	}

}