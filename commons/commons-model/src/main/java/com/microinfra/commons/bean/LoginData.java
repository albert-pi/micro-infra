package com.microinfra.commons.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 用户登录、登出信息参数
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
public class LoginData implements Serializable {

	private static final long serialVersionUID = -7799668547968234908L;

	/**
	 * 账号名称
	 */
	private String name;

	/**
	 * 用户类型。1-平台用户；2-运营商用户；9-未知；
	 */
	private Integer userType;

	/**
	 * IP地址
	 */
	private String clientIp;

	/**
	 * 客户端类型。1-浏览器；2-小程序；3-app；...
	 */
	private Integer clientType;

	/**
	 * 客户端名称。比如：Chrome浏览器
	 */
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
	 * 登录状态。0-成功；1-失败；
	 */
	private Integer status = 0;

	/**
	 * 错误信息
	 */
	private String error;

}
