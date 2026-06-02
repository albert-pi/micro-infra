package com.microinfra.store.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 存储项目的元数据
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "sto_store_meta")
public class StoreMeta implements Serializable {

	private static final long serialVersionUID = -6588870259786221217L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * bucket名称
	 */
	private String bucket;

	/**
	 * 存储Key
	 */
	private String skey;

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
	@TableField("public_status")
	private Integer publicStatus;

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

}