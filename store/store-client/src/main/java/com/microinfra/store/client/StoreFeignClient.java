package com.microinfra.store.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.rpc.feign.FeignConfiguration;
import com.microinfra.store.dto.QueryStoreConditions;
import com.microinfra.store.dto.StoreStrategy;
import com.microinfra.store.facade.StoreService;
import com.microinfra.store.vo.StoreRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("存储服务接口")
@FeignClient(name = Store.SERVICE_NAME, contextId = "storeService", configuration = FeignConfiguration.class)
public interface StoreFeignClient extends StoreService {

	@Override
	@ApiOperation(value = "获取存储服务提供商列表选项")
	@GetMapping("/internal/store/provider/options")
	public List<DataOption> getProviderOptions();

	@Override
	@ApiOperation(produces = "application/json", value = "查询存储记录")
	@PostMapping("/internal/store/query")
	public PageInfo<StoreRecord> queryStoreRecords(QueryStoreConditions queryStoreConditions);

	@Override
	@ApiOperation(value = "获取存储数据的基本信息")
	@GetMapping("/internal/store/record")
	public StoreRecord getStoreRecord(@RequestParam("id") Long storeId);

	@Override
	@ApiOperation(produces = "application/json", value = "保存存储配置")
	@PostMapping("/internal/store/profile/save")
	public void saveStoreStrategy(@RequestBody StoreStrategy profileData) throws ServiceException;

	@Override
	@ApiOperation(value = "上传文件", notes = "上传文件到存储服务，返回文件的存储ID")
	@PostMapping(value = "/internal/store/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Long upload(@RequestParam(name = "tenantId", required = false) Long tenantId, @RequestParam(name = "biz") String biz,
			@RequestPart(value = "file") MultipartFile file) throws ServiceException;

	@Override
	@ApiOperation(value = "获取存储数据的访问地址")
	@GetMapping("/internal/store/access-url")
	public String getAccessUrl(@RequestParam(name = "id") Long storeId) throws ServiceException;

//	@ApiOperation(value = "获取存储数据的访问地址")
//	@GetMapping("/internal/store/content")
//	public InputStream getContent(@RequestParam(name = "storeId") Long storeId) throws ServiceException;

	@Override
	@ApiOperation(value = "删除存储对象", notes = "根据ID删除存储元数据及相应的存储数据")
	@PostMapping("/internal/store/remove")
	public void remove(@RequestBody LongId storeId) throws ServiceException;

}