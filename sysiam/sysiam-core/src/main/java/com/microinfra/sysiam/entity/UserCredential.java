package com.microinfra.sysiam.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 用户身份凭证信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_user_credential")
public class UserCredential implements Serializable {

	private static final long serialVersionUID = 3417013506783973278L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	@TableField("user_id")
	private Long userId;

	/**
	 * 认证类型。1-系统自建账号+密码；...
	 */
	@TableField("identity_type")
	private Integer identityType;

	/**
	 * 账号标识
	 */
	private String identifier;

	/**
	 * 密码凭证
	 */
	private String credential;

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

	/**
	 * 最近修改时间
	 */
	@TableField("update_time")
	private Date updateTime;

	/**
	 * 最近修改者账号名称
	 */
	@TableField("update_by")
	private String updateBy;

}