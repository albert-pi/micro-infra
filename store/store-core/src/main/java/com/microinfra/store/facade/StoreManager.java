package com.microinfra.store.facade;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.store.StoreException;
import com.microinfra.store.domain.StoreMeta;
import com.microinfra.store.domain.StoreProfile;
import com.microinfra.store.dto.QueryStoreConditions;
import com.microinfra.store.dto.StoreStrategy;
import com.microinfra.store.facade.StoreService;
import com.microinfra.store.provider.StoreProvider;
import com.microinfra.store.provider.StoreProviderHelper;
import com.microinfra.store.service.StoreBeanConverter;
import com.microinfra.store.service.StoreMetaService;
import com.microinfra.store.service.StoreProfileService;
import com.microinfra.store.vo.StoreRecord;
import com.microinfra.uid.facade.UidService;

/**
 * <p>
 * 存储管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */

@Service
public class StoreManager implements StoreService {

	@Resource
	private StoreMetaService storeMetaService;

	@Resource
	private StoreProfileService storeProfileService;

	@Resource
	private UidService uidService;

	@Resource
	private StoreBeanConverter storeBeanConverter;

	@Override
	public List<DataOption> getProviderOptions() {
		return StoreProviderHelper.getProviderOptions();
	}

	public Long saveStoreProfile(Long tenantId, String providerName, String bucket, String operator) throws ServiceException {
		Long profileId = uidService.generate();

		StoreProfile storeProfile = new StoreProfile();
		storeProfile.setId(profileId);
//		storeProfile.setName(profileName);
		storeProfile.setTenantId(tenantId);
		storeProfile.setProvider(providerName);
		storeProfile.setBucket(bucket);
		storeProfile.setCreateBy(operator);
		storeProfile.setCreateTime(new Date());
		storeProfileService.save(storeProfile);

		return profileId;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Long upload(Long tenantId, String biz, String name, long size, InputStream ins) throws ServiceException {
		StoreProvider theStoreProvider = null;
		String bucket = null;
		if (tenantId != null) {
			StoreProfile storeProfile = storeProfileService.getStoreProfile(tenantId);
			if (storeProfile == null) {
				throw new StoreException("没有存储配置信息");
			}

			bucket = storeProfile.getBucket();
			theStoreProvider = StoreProviderHelper.getProvider(storeProfile.getProvider());
		} else {
			theStoreProvider = StoreProviderHelper.getDefaultProvider();
			bucket = theStoreProvider.getDefaultBucket();
		}

		int index = name.lastIndexOf('.');
		String suffix = index > -1 ? name.substring(index) : "";
		String newFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400) + suffix;
		String storeKey = biz.concat("/").concat(newFileName);

		Long storeId = uidService.generate();

		StoreMeta storeItem = new StoreMeta();
		storeItem.setId(storeId);
		storeItem.setBucket(bucket);
		storeItem.setSkey(storeKey);
		storeItem.setName(name);
		storeItem.setSize(size);
		storeItem.setBiz(biz);
		storeItem.setProvider(theStoreProvider.getName());
		storeItem.setPublicStatus(0); // TODO
		storeItem.setDeleteStatus(DeleteStatus.NOT_DELETED.value());
		storeItem.setCreateTime(new Date());
		storeMetaService.save(storeItem);

		theStoreProvider.upload(bucket, storeKey, ins);

		return storeId;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Long upload(String biz, String name, long size, InputStream ins) throws ServiceException {
		return upload(null, biz, name, size, ins);
	}

	@Override
	public PageInfo<StoreRecord> queryStoreRecords(QueryStoreConditions queryCondtions) {
		Page<StoreRecord> thePage = PageHelper.startPage(queryCondtions.getPage(), queryCondtions.getSize());
		List<StoreRecord> storeRecords = storeMetaService.queryStoreRecords(queryCondtions);
		PageInfo<StoreRecord> pageInfo = PageInfo.of(thePage);
		pageInfo.setList(storeRecords);

		return pageInfo;
	}

	@Override
	public StoreRecord getStoreRecord(Long storeId) {
		return storeBeanConverter.storeMetaToStoreRecord(storeMetaService.getById(storeId));
	}

	@Override
	public String getAccessUrl(Long storeId) throws ServiceException {
		String accessUrl = null;

		StoreMeta storeMeta = storeMetaService.getById(storeId);
		if (storeMeta.getPublicStatus().intValue() == 0) {
			accessUrl = StoreProviderHelper.getProvider(storeMeta.getProvider()).getSignedUrl(storeMeta.getBucket(), storeMeta.getSkey());
		} else {
			accessUrl = StoreProviderHelper.getProvider(storeMeta.getProvider()).getPublicUrl(storeMeta.getBucket(), storeMeta.getSkey());
		}

		return accessUrl;
	}

//	public InputStream getContent(Long storeId) throws ServiceException {
//		StoreMeta storeMeta = storeMetaService.getById(storeId);
//		InputStream ins = StoreProviderHelper.getProvider(storeMeta.getProvider()).getInputStream(storeMeta.getBucket(), storeMeta.getSkey());
//
//		return ins;
//	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void removeStore(Long storeId) throws ServiceException {
		StoreMeta storeMeta = storeMetaService.getById(storeId);
		storeMetaService.removeStoreMeta(storeId);

		StoreProvider theStoreProvider = StoreProviderHelper.getProvider(storeMeta.getProvider());
		theStoreProvider.deleteStore(storeMeta.getBucket(), storeMeta.getSkey());
	}

	@Override
	public void saveStoreStrategy(StoreStrategy storeStrategy) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Long upload(Long tenantId, String biz, MultipartFile file) throws ServiceException {
		String fileName = file.getOriginalFilename();
		long fileSize = file.getSize();

		try (InputStream ins = file.getInputStream()) {
			return this.upload(tenantId, biz, fileName, fileSize, ins);
		} catch (IOException e) {
			throw new StoreException("获取MultipartFile输入流错误：", e);
		}
	}

	@Override
	public void remove(LongId storeId) throws ServiceException {
		this.removeStore(storeId.getId());
	}

}