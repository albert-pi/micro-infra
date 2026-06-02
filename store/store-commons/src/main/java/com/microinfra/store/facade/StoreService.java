package com.microinfra.store.facade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.store.dto.QueryStoreConditions;
import com.microinfra.store.dto.StoreStrategy;
import com.microinfra.store.vo.StoreRecord;

public interface StoreService {

	public List<DataOption> getProviderOptions();

	public PageInfo<StoreRecord> queryStoreRecords(QueryStoreConditions queryStoreConditions);

	public StoreRecord getStoreRecord(Long storeId);

	public void saveStoreStrategy(StoreStrategy storeStrategy) throws ServiceException;

	public Long upload(Long tenantId, String biz, MultipartFile file) throws ServiceException;

	public String getAccessUrl(Long storeId) throws ServiceException;

//	@ApiOperation(value = "获取存储数据的访问地址")
//	@GetMapping("/internal/store/content")
//	public InputStream getContent(Long storeId) throws ServiceException;

	public void remove(LongId storeId) throws ServiceException;

}