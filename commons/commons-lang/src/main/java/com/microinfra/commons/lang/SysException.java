package com.microinfra.commons.lang;

public class SysException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SysException() {
		super();
	}

	public SysException(String msg) {
		super(msg);
	}

	public SysException(Throwable cause) {
		super(cause);
	}

	public SysException(String msg, Throwable cause) {
		super(msg, cause);
	}

}