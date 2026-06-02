package com.microinfra.uid.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.rpc.feign.FeignConfiguration;
import com.microinfra.uid.facade.UidService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("ID生成服务接口")
@FeignClient(name = Uid.SERVICE_NAME, contextId = "uidService", configuration = FeignConfiguration.class)
public interface UidFeignClient extends UidService {

	@Override
	@ApiOperation(value = "生成ID", notes = "生成一个全局唯一的ID")
	@PostMapping("/internal/uid/generate")
	public Long generate() throws ServiceException;

	@Override
	@ApiOperation(value = "解析ID", notes = "解ID。解析结果包含生成ID组成的时间戳、worker id和序列号")
	@PostMapping("/internal/uid/parse")
	public String parse(@RequestBody LongId uid) throws ServiceException;

}