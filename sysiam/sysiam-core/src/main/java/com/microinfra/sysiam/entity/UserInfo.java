package com.microinfra.sysiam.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 平台应用的用户信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_user_info")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -2099237777367478481L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 系统自建账号名称
	 */
	private String name;

	/**
	 * 姓名或昵称
	 */
	private String nick;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 邮箱地址
	 */
	private String email;

	/**
	 * 头像地址
	 */
	private String avatar;

	/**
	 * 用户类别。1-平台用户；2-运营商用户；
	 */
	private Integer type;

	/**
	 * 状态。0-正常；1-停用；
	 */
	private Integer status;

	/**
	 * 删除状态。0-未删除；1-已删除；
	 */
	@TableField("delete_status")
	private Integer deleteStatus;

	/**
	 * 运营商ID。如果是运营商用户，值不为空
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 运营商名称
	 */
	@TableField("tenant_name")
	private String tenantName;

	/**
	 * 超级管理员标记。0-否；1-是；
	 */
	private Integer adminFlag;

	/**
	 * 最后登录地IP
	 */
	@TableField("login_ip")
	private String loginIp;

	/**
	 * 最后登录时间
	 */
	@TableField("login_time")
	private Date loginTime;

	/**
	 * 备注
	 */
	private String remark;

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