package com.microinfra.sysiam.facade;

import java.util.List;

import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.dto.MenuBasic;
import com.microinfra.sysiam.vo.MenuDetails;
import com.microinfra.sysiam.vo.MenuRecord;

public interface MenuService {

	public List<MenuRecord> listMenusInTree(Long serviceItemId);

	public MenuDetails getMenuDetails(Long menuId);

	public Long saveMenu(MenuBasic menuBasic) throws ServiceException;

	public void removeMenu(LongId menuId);

}