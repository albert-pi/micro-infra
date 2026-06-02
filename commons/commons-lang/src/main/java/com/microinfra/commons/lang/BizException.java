package com.microinfra.commons.lang;

public class BizException extends Exception {

	private static final long serialVersionUID = 1L;

	protected Integer code = null;

	protected String msg = null;

	public BizException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BizException(String msg, int code) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public BizException(String msg, Throwable cause, int code) {
		super(msg, cause);
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}