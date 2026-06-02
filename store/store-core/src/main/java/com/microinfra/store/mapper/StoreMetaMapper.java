package com.microinfra.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.microinfra.store.domain.StoreMeta;
import com.microinfra.store.vo.StoreRecord;

/**
 * <p>
 * 存储元数据 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface StoreMetaMapper extends BaseMapper<StoreMeta> {

	@Select(" SELECT * FROM sto_store_meta ${ew.customSqlSegment} ")
	List<StoreRecord> selectStoreRecordsByParams(@Param(Constants.WRAPPER) QueryWrapper<StoreRecord> wrapper);

}
