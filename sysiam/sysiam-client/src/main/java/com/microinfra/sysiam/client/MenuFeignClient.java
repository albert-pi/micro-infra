package com.microinfra.sysiam.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.rpc.feign.FeignConfiguration;
import com.microinfra.sysiam.dto.MenuBasic;
import com.microinfra.sysiam.facade.MenuService;
import com.microinfra.sysiam.vo.MenuDetails;
import com.microinfra.sysiam.vo.MenuRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("菜单管理接口")
@FeignClient(name = Sysiam.SERVICE_NAME, contextId = "menuService", configuration = FeignConfiguration.class)
public interface MenuFeignClient extends MenuService {

	@ApiOperation(produces = "application/json", value = "获取平台服务的菜单及功能列表，以树状结构进行组织")
	@GetMapping("/internal/menu/list")
	public List<MenuRecord> listMenusInTree(@RequestParam(name = "serviceItemId") Long serviceItemId);

	@ApiOperation(produces = "application/json", value = "获取菜单详细信息")
	@GetMapping("/internal/menu/details")
	public MenuDetails getMenuDetails(@RequestParam(name = "menuId") Long menuId);

	@ApiOperation(produces = "application/json", value = "保存菜单信息")
	@PostMapping("/internal/menu/save")
	public Long saveMenu(@RequestBody MenuBasic menuBasic) throws ServiceException;

	@ApiOperation(produces = "application/json", value = "删除菜单")
	@PostMapping("/internal/menu/remove")
	public void removeMenu(@RequestBody LongId menuId);

}