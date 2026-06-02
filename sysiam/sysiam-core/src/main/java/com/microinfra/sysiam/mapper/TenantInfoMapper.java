package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.dto.QueryTenantConditions;
import com.microinfra.sysiam.entity.TenantInfo;
import com.microinfra.sysiam.vo.TenantRecord;

/**
 * <p>
 * 运营商信息 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface TenantInfoMapper extends BaseMapper<TenantInfo> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name") //
	})
	@Select(" SELECT id, name FROM iam_tenant_info WHERE delete_status = 0 ")
	List<DataOption> selectTenantOptions();

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
			@Result(property = "corp", column = "corp"), //
			@Result(property = "phone", column = "phone"), //
			@Result(property = "address", column = "address"), //
			@Result(property = "beginTime", column = "begin_time"), //
			@Result(property = "endTime", column = "end_time"), //
			@Result(property = "status", column = "status"), //
			@Result(property = "createTime", column = "create_time"), //
			@Result(property = "services", column = "id", many = @Many(select = "selectServiceNamesByTenantId", fetchType = FetchType.EAGER)) //
	})
	@Select({ "<script>", //
			"SELECT id, name, corp, contact, phone, address, begin_time, end_time, status, remark, create_time, create_by ", //
			"FROM iam_tenant_info ", //
			"<where> ", // 小写
			"	delete_status = 0 ", //
			"	<if test='params != null'>", //
			"		<if test='params.tenantId != null'>", //
			"			AND id = #{params.tenantId} ", //
			"		</if>", //
			"		<if test='params.name != null'>", //
			"			AND name like CONCAT(CONCAT('%', #{params.name}), '%') ", //
			"		</if>", //
			"		<if test='params.phone != null'>", //
			"			AND phone = #{params.phone} ", //
			"		</if>", //
			"	</if>", //
			"</where> ", //
			" ORDER BY create_time DESC ", //
			"</script>" })
	List<TenantRecord> selectTenantRecordsByParams(@Param("params") QueryTenantConditions params);

	@Select(" SELECT s.service_name FROM iam_tenant_service ts JOIN iam_service_item s ON ts.service_id = s.id WHERE ts.tenant_id = #{tenantId} ")
	List<String> selectServiceNamesByTenantId(@Param("tenantId") Long tenantId);

}
