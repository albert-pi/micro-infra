package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.dto.QueryRoleConditions;
import com.microinfra.sysiam.entity.RoleInfo;
import com.microinfra.sysiam.vo.RoleRecord;

/**
 * <p>
 * 角色信息 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name") //
	})
	@Select(" SELECT id, name FROM iam_role_info WHERE type = 1 AND delete_status = 0")
	List<DataOption> selectRoleOptionsInPlatform();

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name") //
	})
	@Select(" SELECT id, name FROM iam_role_info WHERE type = 2 AND delete_status = 0 AND tenant_id = #{tenantId}")
	List<DataOption> selectRoleOptionsInTenant(@Param("tenantId") Long tenantId);

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
			@Result(property = "parentName", column = "parent_id", one = @One(select = "selectDeptNameByDeptId", fetchType = FetchType.EAGER)),
			@Result(property = "tenantName", column = "tenant_id", one = @One(select = "selectTenantNameByTenantId", fetchType = FetchType.EAGER)), //
			@Result(property = "remark", column = "remark"), //
			@Result(property = "createTime", column = "create_time"), //

	})
	@Select({ "<script>", //
			"SELECT id, name, status, remark, create_time ", //
			"FROM iam_role_info ", //
			"<where> ", // 小写
			"	delete_status = 0 ", //
			"	<if test='params != null'>", //
			"		<if test='params.type != null'>", //
			"			AND type = #{params.type} ", //
			"		</if>", //
			"		<if test='params.tenantId != null'>", //
			"			AND id = #{params.tenantId} ", //
			"		</if>", //
			"		<if test='params.name != null'>", //
			"			AND name like CONCAT(CONCAT('%', #{params.name}), '%') ", //
			"		</if>", //
			"	</if>", //
			"</where> ", //
			" ORDER BY create_time DESC ", //
			"</script>" })
	List<RoleRecord> selectRoleRecordsByParams(@Param("params") QueryRoleConditions params);

}
