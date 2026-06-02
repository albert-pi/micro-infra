package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.UserOrg;

/**
 * <p>
 * 用户和组织机构关系 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface UserOrgMapper extends BaseMapper<UserOrg> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
	})
	@Select(" SELECT o.id, o.name FROM iam_user_org uo JOIN iam_org_info o ON uo.org_id = o.id WHERE uo.user_id = #{userId} ")
	List<DataOption> selectOrgOptionsByUserId(@Param("userId") Long userId);

}
