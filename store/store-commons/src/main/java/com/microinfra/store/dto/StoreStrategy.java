package com.microinfra.store.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 运营商存储策略
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
public class StoreStrategy implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 运营商ID
	 */
	private Long tenantId;

	/**
	 * 存储服务提供商名称
	 */
	private String provider;

}