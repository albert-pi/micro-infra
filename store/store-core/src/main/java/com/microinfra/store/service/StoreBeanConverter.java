package com.microinfra.store.service;

import org.mapstruct.Mapper;

import com.microinfra.store.domain.StoreMeta;
import com.microinfra.store.vo.StoreRecord;

@Mapper(componentModel = "spring")
public interface StoreBeanConverter {

	StoreRecord storeMetaToStoreRecord(StoreMeta storeMeta);

}