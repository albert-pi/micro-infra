package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.TenantServiceItem;

/**
 * <p>
 * 授权给运营商使用的服务项目 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface TenantServiceItemMapper extends BaseMapper<TenantServiceItem> {

	@Results({ //
			@Result(property = "id", column = "service_id"), //
			@Result(property = "name", column = "service_name") //
	})
	@Select(" SELECT s.id AS service_id, s.service_name FROM iam_tenant_service ts JOIN iam_service_item s ON ts.service_id = s.id WHERE ts.tenant_id = #{tenantId} ")
	List<DataOption> selectServiceOptionsByTenant(@Param("tenantId") Long tenantId);

}
