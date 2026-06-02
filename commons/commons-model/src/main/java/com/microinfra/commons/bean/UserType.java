package com.microinfra.commons.bean;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum UserType {
	PLATFORM(1), TENANT(2), UNKNOWN(9);

	@Accessors(fluent = true)
	@Getter
	private int value;

	UserType(int value) {
		this.value = value;
	}

	public static UserType of(int value) {
		switch (value) {
		case 1:
			return PLATFORM;
		case 2:
			return TENANT;
		case 9:
			return UNKNOWN;
		default:
			throw new IllegalArgumentException("Invalid User Type: " + value);
		}
	}

};