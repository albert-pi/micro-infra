package com.microinfra.parking;

import com.microinfra.commons.lang.ServiceException;

public class ParkingException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public ParkingException(String msg) {
		super(msg);
	}

	public ParkingException(Exception cause) {
		super(cause);
	}

	public ParkingException(String msg, Exception cause) {
		super(msg, cause);
	}

}