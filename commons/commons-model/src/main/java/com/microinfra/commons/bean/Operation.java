package com.microinfra.commons.bean;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum Operation {
	CREATE(1), DELETE(2), UPDATE(3), QUERY(4), OTHER(9);

	@Accessors(fluent = true)
	@Getter
	private int value;

	Operation(int value) {
		this.value = value;
	}

	public static Operation of(int value) {
		switch (value) {
		case 1:
			return CREATE;
		case 2:
			return DELETE;
		case 3:
			return UPDATE;
		case 4:
			return QUERY;
		case 9:
			return OTHER;
		default:
			throw new IllegalArgumentException("Invalid Operation: " + value);
		}
	}

}