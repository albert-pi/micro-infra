package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.UserTenant;

/**
 * <p>
 * 授权给用户管理的运营商 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface UserTenantMapper extends BaseMapper<UserTenant> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name") //
	})
	@Select(" SELECT t.id, t.name FROM iam_user_tenant ut JOIN iam_tenant_info t ON ut.tenant_id = t.id WHERE ut.user_id = #{userId} AND ut.tenant_id != 0 " //
			+ " UNION " //
			+ "SELECT ut.tenant_id AS id, '全部' AS name FROM iam_user_tenant ut WHERE ut.user_id = #{userId} AND ut.tenant_id = 0")
	List<DataOption> selectTenantOptionsByUserId(@Param("userId") Long userId);

}
