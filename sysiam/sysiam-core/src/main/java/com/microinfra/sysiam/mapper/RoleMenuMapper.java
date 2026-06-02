package com.microinfra.sysiam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microinfra.commons.lang.TreeNode;
import com.microinfra.sysiam.entity.RoleMenu;

/**
 * <p>
 * 角色的菜单或功能分配 Mapper接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
			@Result(property = "parentId", column = "parent_id"), //
			@Result(property = "sort", column = "sort"), //
	})
	@Select(" SELECT m.id, m.name, m.parent_id, m.sort FROM iam_role_menu rm JOIN iam_menu_info m ON rm.menu_id = m.id WHERE rm.role_id = #{roleId} ")
	List<TreeNode> selectMenuAsTreeNodeByRole(@Param("roleId") Long roleId);

}
