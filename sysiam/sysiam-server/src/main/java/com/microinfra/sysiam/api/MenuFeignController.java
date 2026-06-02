package com.microinfra.sysiam.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.client.MenuFeignClient;
import com.microinfra.sysiam.dto.MenuBasic;
import com.microinfra.sysiam.entity.MenuInfo;
import com.microinfra.sysiam.facade.SysiamBeanConverter;
import com.microinfra.sysiam.facade.MenuManager;
import com.microinfra.sysiam.vo.MenuDetails;
import com.microinfra.sysiam.vo.MenuRecord;

@RestController
public class MenuFeignController implements MenuFeignClient {

	@Resource
	private MenuManager menuManager;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	@Override
	public List<MenuRecord> listMenusInTree(Long serviceItemId) {
		return menuManager.getMenuRecordTree(serviceItemId);
	}

	@Override
	public MenuDetails getMenuDetails(Long menuId) {
		return menuManager.getMenuDetails(menuId);
	}

	@Override
	public Long saveMenu(MenuBasic menuBasic) throws ServiceException {
		Long menuId = menuBasic.getId();
		MenuInfo menuInfo = sysiamBeanConverter.menuBasicToMenuInfo(menuBasic);
		if (menuId == null) {
			menuId = menuManager.createMenu(menuInfo);
		} else {
			menuManager.updateMenu(menuInfo);
		}

		return menuId;
	}

	@Override
	public void removeMenu(@RequestBody LongId menuId) {
		menuManager.removeMenu(menuId.getId());
	}

}