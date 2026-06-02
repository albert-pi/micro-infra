package com.microinfra.commons.rpc.feign;

import static com.microinfra.commons.rpc.ServiceConstants.SERVICE_UNAVAILABLE_PROMPTS;

import java.io.IOException;

import org.springframework.context.annotation.Bean;

import com.alibaba.fastjson2.JSON;
import com.microinfra.commons.lang.BizException;
import com.microinfra.commons.lang.SysException;

import feign.Logger;
import feign.Response;
import feign.Retryer;
import feign.Util;
import feign.codec.ErrorDecoder;

public class FeignConfiguration {

	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public Retryer feignRetryer() {
		return new Retryer.Default(100, 1000, 3);
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}

	public class CustomErrorDecoder implements ErrorDecoder {

		@Override
		public Exception decode(String methodKey, Response response) {
			switch (response.status()) {
			case 404:
				return new SysException("resource not found");
			case 400:
			case 500:
				byte[] body = null;
				try {
					if (response.body() != null) {
						body = Util.toByteArray(response.body().asInputStream());
					}
				} catch (IOException ioe) { // NOPMD
					return new SysException(SERVICE_UNAVAILABLE_PROMPTS);
				}

				return new BizException(JSON.parseObject(body).getString("msg"));
			default:
				return new SysException(SERVICE_UNAVAILABLE_PROMPTS);
			}
		}
	}
}