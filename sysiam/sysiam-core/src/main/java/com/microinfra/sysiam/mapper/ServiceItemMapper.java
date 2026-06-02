package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.ServiceItem;

/**
 * <p>
 * 服务项目 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface ServiceItemMapper extends BaseMapper<ServiceItem> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "service_name") //
	})
	@Select(" SELECT id, service_name FROM iam_service_item ")
	List<DataOption> selectServiceItemOptions();

}
