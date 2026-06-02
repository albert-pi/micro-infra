package com.microinfra.sysiam.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 服务项目
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_service_item")
public class ServiceItem implements Serializable {

	private static final long serialVersionUID = -1287360858440117681L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 系统应用编码
	 */
	@TableField("app_code")
	private String appCode;

	/**
	 * 服务名称
	 */
	@TableField("service_name")
	private String serviceName;

	/**
	 * 服务访问URL
	 */
	@TableField("service_url")
	private String serviceUrl;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态。0-正常；1-禁用；
	 */
	private Integer status;

}