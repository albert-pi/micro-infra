package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.sysiam.dto.QueryOrgConditions;
import com.microinfra.sysiam.entity.OrgInfo;
import com.microinfra.sysiam.vo.OrgRecord;

/**
 * <p>
 * 组织机构信息 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface OrgInfoMapper extends BaseMapper<OrgInfo> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
			@Result(property = "parentId", column = "parent_id"), //
			@Result(property = "sort", column = "sort"), //
	})
	@Select(" SELECT id, name, parent_id, sort FROM iam_org_info WHERE type = 1 AND delete_status = 0 ")
	List<TreeNode> selectOrgAsTreeNodeInPlatform();

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
			@Result(property = "parentId", column = "parent_id"), //
			@Result(property = "sort", column = "sort"), //
	})
	@Select(" SELECT id, name, parent_id, sort FROM iam_org_info WHERE type = 2 AND tenant_id = #{tenantId} AND delete_status = 0 ")
	List<TreeNode> selectOrgAsTreeNodeInTenant(@Param("tenantId") Long tenantId);

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
			@Result(property = "parentName", column = "parent_id", one = @One(select = "selectOrgNameByDeptId", fetchType = FetchType.EAGER)),
			@Result(property = "tenantName", column = "tenant_id", one = @One(select = "selectTenantNameByTenantId", fetchType = FetchType.EAGER)), //
			@Result(property = "remark", column = "remark"), //
			@Result(property = "createTime", column = "create_time"), //

	})
	@Select({ "<script>", //
			"SELECT id, name, parent_id, tenant_id, remark, create_time ", //
			"FROM iam_org_info ", //
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
	List<OrgRecord> selectOrgRecordsByParams(@Param("params") QueryOrgConditions params);

	@Select(" SELECT name FROM iam_org_info WHERE id = #{deptId} ")
	String selectOrgNameByDeptId(@Param("orgId") Long orgId);

	@Select(" SELECT name FROM iam_tenant_info WHERE id = #{tenantId} ")
	String selectTenantNameByTenantId(@Param("tenantId") Long tenantId);

}
