package com.microinfra.framework;

import org.springframework.context.ApplicationEvent;

import com.microinfra.commons.bean.UserProfile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserProfileEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public static enum EventType {

		CREATED,

		UPDATED,

		REMOVED

	};

	private UserProfile userProfile;

	private EventType eventType;

	public UserProfileEvent(Object source, UserProfile userProfile, EventType eventType) {
		super(source);
		this.userProfile = userProfile;
		this.eventType = eventType;
	}

}