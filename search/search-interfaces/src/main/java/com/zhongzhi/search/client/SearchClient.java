package com.zhongzhi.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zhongzhi.commons.lang.LongId;
import com.zhongzhi.commons.rpc.feign.FeignConfiguration;

@FeignClient(name = Search.SERVICE_NAME, contextId = "searchEngineClient", path = "/internal/search", configuration = FeignConfiguration.class)
public interface SearchClient {

	@PostMapping("/remove")
	void remove(@RequestBody LongId storeId);

}