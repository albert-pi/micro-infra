package com.microinfra.commons.bean;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum DeleteStatus {
	NOT_DELETED(0), DELETED(1);

	@Accessors(fluent = true)
	@Getter
	private int value;

	DeleteStatus(int value) {
		this.value = value;
	}

	public static DeleteStatus of(int value) {
		switch (value) {
		case 0:
			return NOT_DELETED;
		case 1:
			return DELETED;
		default:
			throw new IllegalArgumentException("Invalid Delete Status: " + value);
		}
	}

};