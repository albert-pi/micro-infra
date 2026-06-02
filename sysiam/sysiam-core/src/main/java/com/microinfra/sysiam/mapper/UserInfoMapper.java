package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.bean.MenuOption;
import com.microinfra.sysiam.dto.QueryUserConditions;
import com.microinfra.sysiam.entity.UserInfo;
import com.microinfra.sysiam.vo.UserRecord;

/**
 * <p>
 * 用户信息 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
			@Result(property = "nick", column = "nick"), //
			@Result(property = "mobile", column = "mobile"), //
			@Result(property = "status", column = "status"), //
			@Result(property = "tenantId", column = "tenant_id"), //
			@Result(property = "tenantName", column = "tenant_name"), //
			@Result(property = "createTime", column = "create_time"), //
			@Result(property = "createBy", column = "create_by"), //
			@Result(property = "services", column = "id", many = @Many(select = "selectServiceNamesByUserId", fetchType = FetchType.EAGER)) //
	})
	@Select({ "<script>", //
			"SELECT id, name, nick, mobile, status, tenant_id, tenant_name, create_time, create_by FROM iam_user_info ", //
			"<where> ", // 小写
			"	delete_status = 0 ", //
			"	<if test='params != null'>", //
			"		<if test='params.tenantId != null'>", //
			"			AND tenant_id = #{params.tenantId} ", //
			"		</if>", //
			"		<if test='params.nick != null'>", //
			"			AND nick = #{params.nick} ", //
			"		</if>", //
			"		<if test='params.mobile != null'>", //
			"			AND mobile = #{params.mobile} ", //
			"		</if>", //
			"		<if test='params.status != null'>", //
			"			AND status = #{params.status} ", //
			"		</if>", //
			"	</if>", //
			"</where> ", //
			" ORDER BY create_time DESC ", //
			"</script>" })
	List<UserRecord> selectUserRecordsByParams(@Param("params") QueryUserConditions params);

	@Select(" SELECT s.service_name FROM iam_user_service us JOIN iam_service_item s ON us.service_item_id = s.id WHERE us.user_id = #{userId} " //
			+ " UNION " //
			+ "SELECT '全部' AS service_name FROM iam_user_service us WHERE us.user_id = #{userId} AND service_item_id = 0")
	List<String> selectServiceNamesByUserId(@Param("userId") Long userId);

	@Results({ //
			@Result(property = "serviceItemId", column = "service_item_id"), //
			@Result(property = "menuId", column = "menu_id"), //
			@Result(property = "menuName", column = "menu_name"), //
			@Result(property = "authorityId", column = "authority_id"), //
	})
	@Select("SELECT m.service_item_id, m.id AS menu_id, m.name AS menu_name, m.authority_id FROM iam_menu_info m JOIN ( " //
			+ "		SELECT rm.menu_id from iam_role_menu rm JOIN iam_user_role ur ON rm.role_id = ur.role_id where ur.user_id = #{userId}" //
			+ ") t ON m.id = t.menu_id " //
			+ "UNION " //
			+ "SELECT rm.service_item_id, rm.menu_id, '全部' AS menu_name, '0' AS authority_id from iam_role_menu rm JOIN iam_user_role ur ON rm.role_id = ur.role_id where ur.user_id = #{userId} AND rm.menu_id = 0")
	List<MenuOption> selectMenuOptionsByUserId(@Param("userId") Long userId);

}
