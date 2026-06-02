package com.microinfra.sysiam.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 角色的菜单或功能分配
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_role_menu")
public class RoleMenu implements Serializable {

	private static final long serialVersionUID = -4297378178227687306L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 角色ID
	 */
	@TableField("role_id")
	private Long roleId;

	/**
	 * 服务项目ID。0表示所有服务
	 */
	@TableField("service_item_id")
	private Long serviceItemId;

	/**
	 * 菜单ID。0表示服务中的所有菜单
	 */
	@TableField("menu_id")
	private Long menuId;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;

	/**
	 * 创建者账号名称
	 */
	@TableField("create_by")
	private String createBy;

}