package com.microinfra.store.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 存储记录
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
public class StoreRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 大小。单位：字节
	 */
	private Long size;

	/**
	 * 上传存储数据的业务线名称
	 */
	private String biz;

	/**
	 * 存储服务提供商名称
	 */
	private String provider;

	/**
	 * 公共访问状态。0-不能公共访问，生成临时地址供用户访问；1-可以公共访问，有永久访问地址供用户访问；
	 */
	private Integer publicStatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

}