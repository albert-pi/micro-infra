package com.microinfra.commons.bean;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum AdminFlag {
	YES(1), NO(0);

	@Accessors(fluent = true)
	@Getter
	private int value;

	AdminFlag(int value) {
		this.value = value;
	}

	public static AdminFlag of(int value) {
		switch (value) {
		case 0:
			return NO;
		case 1:
			return YES;
		default:
			throw new IllegalArgumentException("Invalid Admin Flag: " + value);
		}
	}

};