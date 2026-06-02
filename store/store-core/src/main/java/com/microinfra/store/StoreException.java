package com.microinfra.store;

import com.microinfra.commons.lang.ServiceException;

public class StoreException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public StoreException(String msg) {
		super(msg);
	}

	public StoreException(Exception cause) {
		super(cause);
	}

	public StoreException(String msg, Exception cause) {
		super(msg, cause);
	}

}