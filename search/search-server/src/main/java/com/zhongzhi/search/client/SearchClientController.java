package com.zhongzhi.search.client;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhongzhi.commons.lang.LongId;
import com.zhongzhi.commons.lang.ServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("搜索服务接口")
@RestController
@RequestMapping("/internal/search")
@Validated
public class SearchClientController {

	@ApiOperation(value = "删除存储对象", notes = "根据ID删除存储元数据及相应的存储数据")
	@PostMapping("/remove")
	void remove(@RequestBody LongId storeId) throws ServiceException {

	}

}