package com.microinfra.commons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceItemOption {

	/**
	 * 系统应用编码
	 */
	private String appCode;

	/**
	 * 服务项目ID
	 */
	private Long serviceItemId;

	/**
	 * 服务项目名称
	 */
	private String serviceItemName;

	public DataOption toDataOption() {
		return new DataOption(serviceItemId.toString(), serviceItemName);
	}

}