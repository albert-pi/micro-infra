package com.microinfra.sysiam.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 本地缓存的IP地址信息
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
@TableName(value = "iam_ip_info")
public class IpInfo implements Serializable {

	private static final long serialVersionUID = -5058070323273471568L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 所在位置
	 */
	private String location;

}