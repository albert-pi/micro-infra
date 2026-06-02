package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.ServiceItemOption;
import com.microinfra.sysiam.entity.UserServiceItem;

/**
 * <p>
 * 授权给用户使用的服务项目 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface UserServiceItemMapper extends BaseMapper<UserServiceItem> {

	@Results({ //
			@Result(property = "appCode", column = "app_code"), //
			@Result(property = "serviceItemId", column = "service_item_id"), //
			@Result(property = "serviceItemName", column = "service_name") //
	})
	@Select(" SELECT s.app_code, s.id AS service_item_id, s.service_name FROM iam_user_service us JOIN iam_service_item s ON us.service_item_id = s.id WHERE us.user_id = #{userId} AND us.service_item_id != '0' " //
			+ " UNION " //
			+ "SELECT '0' AS app_code, 0 AS service_item_id, '全部' AS service_name FROM iam_user_service us WHERE us.user_id = #{userId}  AND us.service_item_id = 0 ")
	List<ServiceItemOption> selectServiceItemOptionsByUserId(@Param("userId") Long userId);

}
