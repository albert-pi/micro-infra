package com.microinfra.store.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.store.StoreException;
import com.microinfra.store.client.StoreFeignClient;
import com.microinfra.store.dto.QueryStoreConditions;
import com.microinfra.store.dto.StoreStrategy;
import com.microinfra.store.facade.StoreManager;
import com.microinfra.store.vo.StoreRecord;

@RestController
public class StoreFeignController implements StoreFeignClient {

	@Resource
	private StoreManager storeManager;

	@Override
	public List<DataOption> getProviderOptions() {
		return storeManager.getProviderOptions();
	}

	@Override
	public PageInfo<StoreRecord> queryStoreRecords(QueryStoreConditions queryStoreConditions) {
		return storeManager.queryStoreRecords(queryStoreConditions);
	}

	@Override
	public StoreRecord getStoreRecord(Long storeId) {
		return storeManager.getStoreRecord(storeId);
	}

	@Override
	public void saveStoreStrategy(StoreStrategy storeProfile) throws ServiceException {

	}

	@Override
	public Long upload(Long tenantId, String biz, MultipartFile file) throws ServiceException {
		String fileName = file.getOriginalFilename();
		long fileSize = file.getSize();

		try (InputStream ins = file.getInputStream()) {
			return storeManager.upload(tenantId, biz, fileName, fileSize, ins);
		} catch (IOException e) {
			throw new StoreException("获取MultipartFile输入流错误：", e);
		}
	}

	@Override
	public String getAccessUrl(Long storeId) throws ServiceException {
		return storeManager.getAccessUrl(storeId);
	}

//	public InputStream getContent(Long storeId) throws ServiceException {
//		return storeManager.getContent(storeId);
//	}

	@Override
	public void remove(LongId storeId) throws ServiceException {
		storeManager.removeStore(storeId.getId());
	}

}