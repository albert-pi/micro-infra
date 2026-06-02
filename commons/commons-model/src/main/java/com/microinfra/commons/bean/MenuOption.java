package com.microinfra.commons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuOption {

	/**
	 * 服务项目ID
	 */
	private Long serviceItemId;

	/**
	 * 菜单ID
	 */
	protected Long menuId;

	/**
	 * 菜单名称
	 */
	protected String menuName;

	/**
	 * 菜单对应的权限标识
	 */
	private String authorityId;

}