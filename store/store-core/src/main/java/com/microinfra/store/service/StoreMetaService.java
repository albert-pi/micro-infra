package com.microinfra.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.store.domain.StoreMeta;
import com.microinfra.store.dto.QueryStoreConditions;
import com.microinfra.store.mapper.StoreMetaMapper;
import com.microinfra.store.vo.StoreRecord;

/**
 * <p>
 * 存储元数据 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */

@Service
public class StoreMetaService extends ServiceImpl<StoreMetaMapper, StoreMeta> {

	public List<StoreRecord> queryStoreRecords(QueryStoreConditions queryCondtions) {
		QueryWrapper<StoreRecord> queryWrapper = Wrappers.<StoreRecord>query();
		queryWrapper.orderByDesc("create_time");

		Optional.ofNullable(queryCondtions.getProvider()).ifPresent(provider -> queryWrapper.ge("provider", provider));
		Optional.ofNullable(queryCondtions.getStoreStart()).ifPresent(storeStart -> queryWrapper.ge("create_time", storeStart));
		Optional.ofNullable(queryCondtions.getStoreEnd()).ifPresent(storeEnd -> queryWrapper.le("create_time", storeEnd));
		Optional.ofNullable(queryCondtions.getName()).ifPresent(name -> queryWrapper.eq("name", name));

		return baseMapper.selectStoreRecordsByParams(queryWrapper);
	}

	public StoreMeta getStoreMeta(String key) {
		LambdaQueryWrapper<StoreMeta> queryWrapper = Wrappers.<StoreMeta>lambdaQuery();
		queryWrapper.eq(StoreMeta::getSkey, key);
		queryWrapper.eq(StoreMeta::getDeleteStatus, DeleteStatus.NOT_DELETED.value());

		return baseMapper.selectOne(queryWrapper);
	}

	public void removeStoreMeta(Long storeId) {
		LambdaUpdateWrapper<StoreMeta> updateWrapper = Wrappers.<StoreMeta>lambdaUpdate();
		updateWrapper.set(StoreMeta::getDeleteStatus, DeleteStatus.DELETED.value());
		updateWrapper.eq(StoreMeta::getId, storeId);

		baseMapper.update(null, updateWrapper);
	}

}