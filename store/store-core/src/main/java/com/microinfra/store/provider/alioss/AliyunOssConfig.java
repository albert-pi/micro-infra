package com.microinfra.store.provider.alioss;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Data;

@Data
public class AliyunOssConfig {

	private String domain;

	private String endpoint;

	@JSONField(name = "default-bucket")
	private String defaultBucket;

	@JSONField(name = "access-id")
	private String accessId;

	@JSONField(name = "access-secret")
	private String accessSecret;

	@JSONField(name = "policy-expire")
	private Long policyExpire;

	@JSONField(name = "callback-url")
	private String callbackUrl;

	@JSONField(name = "default-provider")
	private boolean defaultProvider = true;

}