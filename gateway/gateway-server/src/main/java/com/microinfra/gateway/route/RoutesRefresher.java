package com.microinfra.gateway.route;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;

//@Component
public class RoutesRefresher
//implements ApplicationEventPublisherAware 
{

	private ApplicationEventPublisher publisher;

//	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void refreshRoutes() {
		publisher.publishEvent(new RefreshRoutesEvent(this));
	}

}
