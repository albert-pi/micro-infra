package com.microinfra.commons.bean;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Data;

/**
 * <p>
 * 当前用户的个人资料
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Data
public class UserProfile implements Principal, Serializable {

	private static final long serialVersionUID = 7379283825534048172L;

	private Long id;

	/**
	 * 账号名称
	 */
	private String name;

	/**
	 * 姓名或昵称
	 */
	private String nick;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 头像地址
	 */
	private String avatar;

	/**
	 * 账号类型。1-平台用户；2-运营商用户
	 */
	private Integer type;

	/**
	 * 状态。0-正常；1-禁用；
	 */
	private Integer status;

	/**
	 * 所属运营商ID
	 */
	private Long tenantId;

	/**
	 * 所属运营商名称
	 */
	private String tenantName;

	/**
	 * 超级管理员标记。0-否；1-是；
	 */
	private Integer adminFlag;

	/**
	 * 所属组织机构
	 */
	private List<DataOption> orgs;

	/**
	 * 被授权管理的运营商列表
	 */
	private List<DataOption> tenants;

	/**
	 * 被授权使用的服务项目
	 */
	private List<ServiceItemOption> serviceItems;

	/**
	 * 有操作权限的菜单选项
	 */
	private List<MenuOption> menus;

	/**
	 * 被授权使用的平台应用系统、服务项目ID及服务项目名称
	 */
	@JSONField(serialize = false)
	private final Map<String, Map<Long, String>> appServicesMappings = new HashMap<>();

	public void setServiceItems(List<ServiceItemOption> serviceItemOptions) {
		this.serviceItems = serviceItemOptions;

		if (serviceItemOptions != null) {
			serviceItemOptions.forEach(opt -> {
				Map<Long, String> serviceIdNameMap = appServicesMappings.computeIfAbsent(opt.getAppCode(), appCode -> new HashMap<>());
				serviceIdNameMap.put(opt.getServiceItemId(), opt.getServiceItemName());
			});
		}
	}

	/**
	 * 被授权使用的平台服务ID、操作权限ID及操作名称
	 */
	@JSONField(serialize = false)
	private final Map<Long, Map<String, String>> serviceAuthoritiesMappings = new HashMap<>();

	public void setMenus(List<MenuOption> menuOptions) {
		this.menus = menuOptions;

		if (menuOptions != null) {
			menuOptions.forEach(opt -> {
				Map<String, String> authorityMap = serviceAuthoritiesMappings.computeIfAbsent(opt.getServiceItemId(), serviceItemId -> new HashMap<>());
				authorityMap.put(opt.getAuthorityId(), opt.getAuthorityId());
			});
		}
	}

}
