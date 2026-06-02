package com.microinfra.commons.bean;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum AvailableStatus {
	NORMAL(0), DISABLED(1);

	@Accessors(fluent = true)
	@Getter
	private int value;

	AvailableStatus(int value) {
		this.value = value;
	}

	public static AvailableStatus of(int value) {
		switch (value) {
		case 0:
			return NORMAL;
		case 1:
			return DISABLED;
		default:
			throw new IllegalArgumentException("Invalid Status: " + value);
		}
	}

}