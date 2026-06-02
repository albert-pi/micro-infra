package com.microinfra.gateway.route;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Component;

import com.microinfra.gateway.GatewayConfig.BaseConfig;

import reactor.core.publisher.Flux;

@Component
public class DynamicRouteLocator implements RouteDefinitionLocator {

	@Resource
	private BaseConfig baseConfig;

	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		List<DynamicRoute> routes = baseConfig.getRoutes();
		return Flux.fromIterable(routes.stream().map(r -> {
			RouteDefinition rd = new RouteDefinition();
			rd.setId(r.getId());
			rd.setUri(URI.create(r.getUri()));
			rd.setPredicates(r.getPredicates().stream().map(p -> new PredicateDefinition(p)).toList());
//			rd.setFilters(null);

			return rd;
		}).toList());
	}

}
