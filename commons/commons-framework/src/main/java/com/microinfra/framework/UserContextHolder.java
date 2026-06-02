package com.microinfra.framework;

import org.springframework.stereotype.Component;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.microinfra.commons.bean.UserProfile;

@Component
public final class UserContextHolder {

	private static TransmittableThreadLocal<UserProfile> CURRENT_USER_CONTEXT = new TransmittableThreadLocal<>();

	public static void setCurrentUser(UserProfile currentUser) {
		if (currentUser != null) {
			CURRENT_USER_CONTEXT.set(currentUser);
		}
	}

	public static UserProfile getCurrentUser() {
		return CURRENT_USER_CONTEXT.get();
	}

	public static void clean() {
		CURRENT_USER_CONTEXT.remove();
	}

}