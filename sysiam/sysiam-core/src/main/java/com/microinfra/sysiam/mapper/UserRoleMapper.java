package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.UserRole;

/**
 * <p>
 * 用户角色分配 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
	})
	@Select(" SELECT r.id, r.name FROM iam_user_role ur JOIN iam_role_info r ON ur.role_id = r.id WHERE ur.user_id = #{userId} ")
	List<DataOption> selectRoleOptionsByUser(@Param("userId") Long userId);
}
