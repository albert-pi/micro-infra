package com.microinfra.commons.bean;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum IdentityType {
	NAME_PASSWORD(1), SMS(2), WECHAT(3);

	@Accessors(fluent = true)
	@Getter
	private final int type;

	IdentityType(int type) {
		this.type = type;
	}

	public static IdentityType of(int type) {
		switch (type) {
		case 1:
			return NAME_PASSWORD;
		case 2:
			return SMS;
		case 3:
			return WECHAT;
		default:
			return null;
		}
	}
}