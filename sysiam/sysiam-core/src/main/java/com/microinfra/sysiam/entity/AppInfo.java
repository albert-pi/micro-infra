package com.microinfra.sysiam.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 系统应用
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_app_info")
public class AppInfo implements Serializable {

	private static final long serialVersionUID = -3103954913775194820L;

	/**
	 * 系统应用编码
	 */
	@TableField("app_code")
	private String appCode;

	/**
	 * 系统应用名称
	 */
	@TableField("app_name")
	private String appName;

	/**
	 * 系统应用访问地址
	 */
	@TableField("app_url")
	private String appUrl;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态。0-正常；1-禁用；
	 */
	private Integer status;

}