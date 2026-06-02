package com.microinfra.admin.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microinfra.admin.controller.params.ParamsConverter;
import com.microinfra.admin.controller.params.MenuRequestParams.MenuFormParams;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.framework.CurrentUser;
import com.microinfra.framework.HasAuthority;
import com.microinfra.framework.LongIdParam;
import com.microinfra.sysiam.dto.MenuBasic;
import com.microinfra.sysiam.facade.MenuService;
import com.microinfra.sysiam.vo.MenuDetails;
import com.microinfra.sysiam.vo.MenuRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "菜单管理")
@RestController
@Validated
public class MenuController {

	@Resource
	private MenuService menuService;

	@Resource
	private ParamsConverter paramsConverter;

	@ApiOperation(produces = "application/json", value = "获取菜单及功能列表", notes = "获取平台服务的菜单及功能列表。按树状结构显示")
	@HasAuthority("menu.list")
	@PostMapping("/menu/list")
	public List<MenuRecord> listMenusInTree(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody LongIdParam serviceItemId) {
		return menuService.listMenusInTree(serviceItemId.getId());
	}

	@ApiOperation(produces = "application/json", value = "获取菜单详细信息")
	@HasAuthority("menu.details")
	@PostMapping("/menu/details")
	public MenuDetails getMenuDetails(@Validated @RequestBody LongIdParam menuId) {
		return menuService.getMenuDetails(menuId.getId());
	}

	@ApiOperation(produces = "application/json", value = "保存菜单信息", notes = "保存新增菜单或修改菜单信息。返回菜单ID")
	@HasAuthority("menu.save")
	@PostMapping("/menu/save")
	public LongId saveMenu(@ApiIgnore @CurrentUser UserProfile userProfile, @Validated @RequestBody MenuFormParams params) throws ServiceException {
		MenuBasic menuBasic = paramsConverter.menuFormParamsToMenuBasic(params);
		menuBasic.setCreateBy(userProfile.getName());
		menuBasic.setCreateTime(new Date());
		Long menuId = menuService.saveMenu(menuBasic);

		return new LongId(menuId);
	}

	@ApiOperation(produces = "application/json", value = "删除菜单", notes = "删除菜单")
	@HasAuthority("menu.remove")
	@PostMapping("/menu/remove")
	public void removeMenu(@Validated @RequestBody LongIdParam menuId) {
		menuService.removeMenu(new LongId(menuId.getId()));
	}

}