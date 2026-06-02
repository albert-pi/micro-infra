package com.microinfra.sysiam.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.microinfra.commons.bean.AvailableStatus;
import com.microinfra.commons.bean.DeleteStatus;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.commons.lang.TreeUtils;
import com.microinfra.sysiam.dto.MenuBasic;
import com.microinfra.sysiam.entity.MenuInfo;
import com.microinfra.sysiam.entity.ServiceItem;
import com.microinfra.sysiam.exception.MenuException;
import com.microinfra.sysiam.facade.MenuService;
import com.microinfra.sysiam.service.MenuInfoService;
import com.microinfra.sysiam.service.ServiceItemService;
import com.microinfra.sysiam.vo.MenuDetails;
import com.microinfra.sysiam.vo.MenuRecord;
import com.microinfra.uid.facade.UidService;

import cn.hutool.core.date.DateUtil;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class MenuManager implements MenuService {

	private static final Predicate<MenuRecord> rootChecker = menu -> menu.getParentId() == 0L;

	private static final BiFunction<MenuRecord, MenuRecord, Boolean> parentChecker = (parent, child) -> parent.getId().equals(child.getParentId());

	private static final BiConsumer<MenuRecord, List<MenuRecord>> childrenSetter = (parent, children) -> parent.setChildren(children);

	@Resource
	private ServiceItemService serviceItemService;

	@Resource
	private MenuInfoService menuInfoService;

	@Resource
	private UidService uidService;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	public List<MenuRecord> getMenuRecordTree(Long serviceId) {
		List<MenuInfo> menusInService = menuInfoService.getMenusInServiceItem(serviceId);
		List<MenuRecord> menus = new ArrayList<>();
		if (!CollectionUtils.isEmpty(menusInService)) {
			menusInService.forEach(m -> {
				menus.add(sysiamBeanConverter.menuInfoToMenuRecord(m));
			});
		}

		return TreeUtils.buildTree(menus, rootChecker, parentChecker, childrenSetter);
	}

	@Override
	public MenuDetails getMenuDetails(Long menuId) {
		MenuInfo menuInfo = menuInfoService.getById(menuId);
		MenuDetails menuDetails = sysiamBeanConverter.menuInfoToMenuDetails(menuInfo);

		ServiceItem serviceItem = serviceItemService.getById(menuInfo.getServiceItemId());
		menuDetails.setServiceName(serviceItem.getServiceName());

		return menuDetails;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public long createMenu(MenuInfo menuInfo) throws ServiceException {
		MenuInfo theMenu = menuInfoService.getMenuInServiceItemByName(menuInfo.getServiceItemId(), menuInfo.getName());
		if (theMenu != null) {
			throw new MenuException("菜单名称已存在");
		}

		long menuId = uidService.generate();
		menuInfo.setId(menuId);
		menuInfo.setStatus(AvailableStatus.NORMAL.value());
		menuInfo.setDeleteStatus(DeleteStatus.NOT_DELETED.value());
		menuInfo.setCreateTime(DateUtil.date());
		menuInfoService.save(menuInfo);

		return menuId;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void updateMenu(MenuInfo menuInfo) throws MenuException {
		MenuInfo theMenu = menuInfoService.getMenuInServiceItemByName(menuInfo.getServiceItemId(), menuInfo.getName());
		if (theMenu != null && !theMenu.getId().equals(menuInfo.getId())) {
			throw new MenuException("菜单名称已存在");
		}

		menuInfoService.updateById(menuInfo);
	}

	public void removeMenu(Long menuId) {
		menuInfoService.removeMenuInfo(menuId);
	}

	@Override
	public List<MenuRecord> listMenusInTree(Long serviceItemId) {
		return this.getMenuRecordTree(serviceItemId);
	}

	@Override
	public Long saveMenu(MenuBasic menuBasic) throws ServiceException {
		Long menuId = menuBasic.getId();
		MenuInfo menuInfo = sysiamBeanConverter.menuBasicToMenuInfo(menuBasic);
		if (menuId == null) {
			menuId = this.createMenu(menuInfo);
		} else {
			this.updateMenu(menuInfo);
		}

		return menuId;
	}

	@Override
	public void removeMenu(LongId menuId) {
		this.removeMenu(menuId.getId());
	}

}