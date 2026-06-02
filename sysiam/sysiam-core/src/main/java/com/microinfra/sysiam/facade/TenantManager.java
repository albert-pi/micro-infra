package com.microinfra.sysiam.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.commons.lang.GeoUtils;
import com.microinfra.commons.lang.LongId;
import com.microinfra.commons.lang.Regions;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.store.facade.StoreService;
import com.microinfra.sysiam.dto.QueryTenantConditions;
import com.microinfra.sysiam.dto.ServiceItemAuthorizing;
import com.microinfra.sysiam.dto.TenantAdministrator;
import com.microinfra.sysiam.dto.TenantBasic;
import com.microinfra.sysiam.dto.UserBasic;
import com.microinfra.sysiam.entity.TenantInfo;
import com.microinfra.sysiam.entity.TenantServiceItem;
import com.microinfra.sysiam.exception.TenantException;
import com.microinfra.sysiam.facade.TenantService;
import com.microinfra.sysiam.service.TenantInfoService;
import com.microinfra.sysiam.service.TenantServiceItemService;
import com.microinfra.sysiam.vo.TenantDetails;
import com.microinfra.sysiam.vo.TenantRecord;
import com.microinfra.uid.facade.UidService;

import cn.hutool.core.date.DateUtil;

/**
 * <p>
 * 运营商信息及服务项目授权管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class TenantManager implements TenantService {

	@Resource
	private TenantInfoService tenantInfoService;

	@Resource
	private TenantServiceItemService tenantServiceItemService;

	@Resource
	private UidService uidService;

	@Resource
	private StoreService storeService;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	@Override
	public List<DataOption> getTenantOptions() {
		return tenantInfoService.getTenantOptions();
	}

	@Override
	public TenantDetails getTenantDetails(Long tenantId) {
		TenantInfo tenantInfo = tenantInfoService.getTenantInfo(tenantId);
		TenantDetails tenantDetails = sysiamBeanConverter.tenantInfoToTenantDetails(tenantInfo);

		List<DataOption> services = tenantServiceItemService.getAuthorizedServiceOptions(tenantId);
		tenantDetails.setServices(services);

		return tenantDetails;
	}

	@Override
	public PageInfo<TenantRecord> queryTenantRecords(QueryTenantConditions queryConditions) {
		Page<TenantRecord> thePage = PageHelper.startPage(queryConditions.getPage(), queryConditions.getSize());
		List<TenantRecord> tenants = tenantInfoService.queryTenantRecords(queryConditions);
		PageInfo<TenantRecord> pageInfo = PageInfo.of(thePage);
		pageInfo.setList(tenants);

		return pageInfo;
	}

	public Long createTenant(TenantInfo tenant) throws ServiceException {
		TenantInfo theTenant = tenantInfoService.getTenantInfo(tenant.getName());
		if (theTenant != null) {
			throw new TenantException("运营商名称已存在");
		}

		long tenantId = uidService.generate();
		tenant.setId(tenantId);
		tenant.setCreateTime(DateUtil.date());

		if (tenant.getLat() != null && tenant.getLng() != null) {
			tenant.setGeoHash(GeoUtils.getGeoHash(tenant.getLat(), tenant.getLng()));
		}
		if (tenant.getProvinceCode() != null) {
			tenant.setProvinceName(Regions.getRegionName(tenant.getProvinceCode()));
		}
		if (tenant.getCityCode() != null) {
			tenant.setCityName(Regions.getRegionName(tenant.getCityCode()));
		}
		if (tenant.getDistrictCode() != null) {
			tenant.setDistrictName(Regions.getRegionName(tenant.getDistrictCode()));
		}
		tenantInfoService.save(tenant);

		return tenantId;
	}

	public void updateTenant(TenantInfo tenant) throws TenantException {
		TenantInfo theTenant = tenantInfoService.getTenantInfo(tenant.getName());
		if (theTenant != null && !theTenant.getId().equals(tenant.getId())) {
			throw new TenantException("运营商名称已存在");
		}

		if (theTenant == null) {
			theTenant = tenantInfoService.getById(tenant.getId());
		}

		if (tenant.getLat() != null && tenant.getLng() != null) {
			tenant.setGeoHash(GeoUtils.getGeoHash(tenant.getLat(), tenant.getLng()));
		}
		if (tenant.getProvinceCode() != null) {
			tenant.setProvinceName(Regions.getRegionName(tenant.getProvinceCode()));
		}
		if (tenant.getCityCode() != null) {
			tenant.setCityName(Regions.getRegionName(tenant.getCityCode()));
		}
		if (tenant.getDistrictCode() != null) {
			tenant.setDistrictName(Regions.getRegionName(tenant.getDistrictCode()));
		}

		tenantInfoService.updateById(tenant);
	}

	public void authorizeServiceItemsToTenant(Long tenantId, List<Long> serviceIds, String authorizer) throws ServiceException {
		List<TenantServiceItem> serviceItems = new ArrayList<>();
		Date now = DateUtil.date();

		tenantServiceItemService.removeAllAuthorizedServices(tenantId);

		for (Long serviceId : serviceIds) {
			long tsId = uidService.generate();

			TenantServiceItem serviceItem = new TenantServiceItem();
			serviceItem.setId(tsId);
			serviceItem.setTenantId(tenantId);
			serviceItem.setServiceId(serviceId);
			serviceItem.setCreateTime(now);
			serviceItem.setCreateBy(authorizer);
			serviceItems.add(serviceItem);
		}

		tenantServiceItemService.saveBatch(serviceItems);
	}

	public List<DataOption> getAuthorizedServices(Long tenantId) {
		return tenantServiceItemService.getAuthorizedServiceOptions(tenantId);
	}

	public void removeAuthorizedService(Long tenantId, String serviceId) {
		tenantServiceItemService.removeAuthorizedService(tenantId, serviceId);
	}

	public void saveStoreProfile(Long tenantId, String storeProvider) {

	}

	public void removeTenant(Long tenantId) {
		tenantInfoService.removeTenant(tenantId);
	}

	@Override
	public Long saveTenant(TenantBasic tenantBasic) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBasic getAdministrator(Long tenantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAdministrator(TenantAdministrator administrator) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DataOption> getAuthorizedServiceItems(Long tenantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void authorizeServiceItems(ServiceItemAuthorizing authorizing) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTenant(LongId tenantId) {
		// TODO Auto-generated method stub

	}

}