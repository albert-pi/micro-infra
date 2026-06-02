package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.AppInfo;

/**
 * <p>
 * 系统应用 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface AppInfoMapper extends BaseMapper<AppInfo> {

	@Results({ //
			@Result(property = "id", column = "app_code"), //
			@Result(property = "name", column = "app_name") //
	})
	@Select(" SELECT app_code, app_name FROM iam_app_info ")
	List<DataOption> selectAppOptions();

}
