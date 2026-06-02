package com.microinfra.gateway.route;

import java.util.List;

import lombok.Data;

@Data
public class DynamicRoute {

	private String id;

	private String uri;

	private List<String> predicates;

	private List<String> filters;
}
