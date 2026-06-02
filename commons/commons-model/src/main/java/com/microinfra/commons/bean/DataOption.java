package com.microinfra.commons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataOption {

	/**
	 * 数据项ID
	 */
	protected String id;

	/**
	 * 数据项名称
	 */
	protected String name;
};