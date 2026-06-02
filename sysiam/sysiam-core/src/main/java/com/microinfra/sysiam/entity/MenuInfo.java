package com.microinfra.sysiam.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 平台服务中定义的菜单信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_menu_info")
public class MenuInfo implements Serializable {

	private static final long serialVersionUID = 8712329169136804677L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 路由地址
	 */
	private String path;

	/**
	 * 服务项目ID
	 */
	@TableField("service_item_id")
	private Long serviceItemId;

	/**
	 * 上级菜单ID
	 */
	@TableField("parent_id")
	private Long parentId;

	/**
	 * 类型。1-菜单目录；2-菜单项；3-页面跳转按钮；4-功能按钮（请求后台接口）；9-其它；
	 */
	private Integer type;

	/**
	 * 图标样式或图标地址
	 */
	private String icon;

	/**
	 * 权限标识。如果需要对功能进行权限访问控制，值不为空
	 */
	@TableField("authority_id")
	private String authorityId;

	/**
	 * 关联的平台服务接口。当类型为4，需要请求后台接口时，值不为空
	 */
	@TableField("action_id")
	private Long actionId;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 状态。0-正常；1-禁用；
	 */
	private Integer status;

	/**
	 * 备注。功能描述或说明
	 */
	private String remark;

	/**
	 * 删除状态。0-未删除；1-已删除；
	 */
	@TableField("delete_status")
	private Integer deleteStatus;

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