package com.microinfra.sysiam.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 用户登录日志
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_login_log")
public class LoginLog implements Serializable {

	private static final long serialVersionUID = -784690251058070761L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 账号名称
	 */
	private String name;

	/**
	 * 用户姓名或昵称
	 */
	private String nick;

	/**
	 * 登录用户类型。1-平台用户；2-运营商用户；9-未知；
	 */
	@TableField("user_type")
	private Integer userType;

	/**
	 * 运营商ID。如果是运营商用户，值不为空
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 地点
	 */
	private String location;

	/**
	 * IP地址
	 */
	@TableField("client_ip")
	private String clientIp;

	/**
	 * 客户端类型。1-浏览器；2-小程序；3-app；
	 */
	@TableField("client_type")
	private Integer clientType;

	/**
	 * 客户端名称。比如：Chrome浏览器、微信H5...
	 */
	@TableField("client_name")
	private String clientName;

	/**
	 * 操作系统
	 */
	private String os;

	/**
	 * 类型。1-登录；2-登出；
	 */
	private Integer type;

	/**
	 * 状态。0-成功；1-失败；
	 */
	private Integer status;

	/**
	 * 登录登出的结果。如果失败，记录失败详细信息
	 */
	private String result;

	/**
	 * 登录或登出时间
	 */
	@TableField("log_time")
	private Date logTime;

}