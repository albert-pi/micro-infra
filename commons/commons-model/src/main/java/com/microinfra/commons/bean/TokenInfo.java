package com.microinfra.commons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * token的HASH值
	 */
	private String tokenHash;

	/**
	 * token有效期
	 */
	private Integer validity;

}