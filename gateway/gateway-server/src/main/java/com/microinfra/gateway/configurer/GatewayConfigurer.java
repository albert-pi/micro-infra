package com.microinfra.gateway.configurer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 配置Gateway应用
 *
 * @author albert pi
 * @Since 1.0.0
 */
@Configuration
public class GatewayConfigurer {

	@Slf4j
	public static class CustomBlockingLoadBalancerClient extends BlockingLoadBalancerClient {

		private final ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerClientFactory;

		private final Executor serviceExecutor;

		public CustomBlockingLoadBalancerClient(LoadBalancerClientFactory loadBalancerClientFactory, Executor serviceExecutor) {
			super(loadBalancerClientFactory);

			this.loadBalancerClientFactory = loadBalancerClientFactory;
			this.serviceExecutor = serviceExecutor;
		}

		@Override
		public <T> ServiceInstance choose(String serviceId, Request<T> request) {
			ReactiveLoadBalancer<ServiceInstance> loadBalancer = loadBalancerClientFactory.getInstance(serviceId);
			if (loadBalancer == null) {
				return null;
			}

			CompletableFuture<Response<ServiceInstance>> future = CompletableFuture.supplyAsync(() -> {
				Response<ServiceInstance> loadBalancerResponse = Mono.from(loadBalancer.choose(request)).block();

				return loadBalancerResponse;
			}, serviceExecutor);

			try {
				return future.get().getServer();
			} catch (Exception e) {
				log.error("", e);

				return null;
			}
		}

	}

	@Bean(name = "serviceExecutor")
	public Executor serviceExecutor() {
		ThreadPoolTaskExecutor serviceExecutor = new ThreadPoolTaskExecutor();
		serviceExecutor.setCorePoolSize(20);
		serviceExecutor.setMaxPoolSize(100);
		serviceExecutor.setQueueCapacity(300);
		serviceExecutor.initialize();

		return serviceExecutor;
	}

	@Bean
	public LoadBalancerClient customBlockingLoadBalancerclient(LoadBalancerClientFactory loadBalancerClientFactory,
			@Qualifier("serviceExecutor") Executor serviceExecutor) {
		return new CustomBlockingLoadBalancerClient(loadBalancerClientFactory, serviceExecutor);
	}

	@Bean
	@ConditionalOnMissingBean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
		return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
	}

}
