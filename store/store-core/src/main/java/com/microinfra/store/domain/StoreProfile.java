package com.microinfra.store.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 存储配置
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "sto_store_profile")
public class StoreProfile implements Serializable {

	private static final long serialVersionUID = -6588870259786221217L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 运营商ID
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 存储服务提供商名称
	 */
	private String provider;

	/**
	 * bucket名称
	 */
	private String bucket;

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