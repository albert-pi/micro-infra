package com.microinfra.sysiam.facade;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microinfra.commons.bean.LoginData;
import com.microinfra.commons.lang.LocationUtils;
import com.microinfra.commons.lang.ServiceException;
import com.microinfra.sysiam.dto.QueryLoginConditions;
import com.microinfra.sysiam.entity.AuditLog;
import com.microinfra.sysiam.entity.IpInfo;
import com.microinfra.sysiam.entity.LoginLog;
import com.microinfra.sysiam.facade.LogService;
import com.microinfra.sysiam.service.AuditLogService;
import com.microinfra.sysiam.service.IpInfoService;
import com.microinfra.sysiam.service.LoginLogService;
import com.microinfra.sysiam.service.UserInfoService;
import com.microinfra.sysiam.vo.LoginRecord;
import com.microinfra.uid.facade.UidService;

import cn.hutool.core.date.DateUtil;

/**
 * <p>
 * 系统日志管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class LogManager implements LogService {

	@Resource
	private UserInfoService userInfoService;

	@Resource
	private IpInfoService ipInfoService;

	@Resource
	private LoginLogService loginLogService;

	@Resource
	private AuditLogService auditLogService;

	@Resource
	private UidService uidService;

	@Resource
	private SysiamBeanConverter sysiamBeanConverter;

	@Override
	public void saveLoginLog(LoginData loginData) throws ServiceException {
		LoginLog theLog = sysiamBeanConverter.loginDataToLoginLog(loginData);
		theLog.setId(uidService.generate());

		if (loginData.getStatus() == 0) {
			if (loginData.getType() == 1) {
				theLog.setResult("登录成功");
			} else if (loginData.getType() == 2) {
				theLog.setResult("退出登录成功");
			}
		} else {
			theLog.setResult(loginData.getError());
		}
		theLog.setLogTime(DateUtil.date());

		if (loginData.getClientIp() != null) {
			IpInfo ipInfo = ipInfoService.getIpInfo(loginData.getClientIp());
			if (ipInfo == null) {
				ipInfo = new IpInfo();
				ipInfo.setIp(loginData.getClientIp());

				String location = LocationUtils.getRealAddressByIP(loginData.getClientIp());
				if (!LocationUtils.UNKNOWN.equals(location)) {
					theLog.setLocation(location);
					ipInfo.setLocation(location);
				}

				ipInfoService.save(ipInfo);
			} else if (ipInfo.getLocation() != null) {
				theLog.setLocation(ipInfo.getLocation());
			}
		}

		loginLogService.save(theLog);
		userInfoService.updateLastLoginState(theLog.getName(), theLog.getClientIp(), theLog.getLogTime());
	}

	@Override
	public PageInfo<LoginRecord> queryLoginRecords(QueryLoginConditions queryLoginConditions) {
		Page<LoginRecord> thePage = PageHelper.startPage(queryLoginConditions.getPage(), queryLoginConditions.getSize());
		List<LoginRecord> loginRecords = loginLogService.queryLoginRecords(queryLoginConditions);
		PageInfo<LoginRecord> pageInfo = PageInfo.of(thePage);
		pageInfo.setList(loginRecords);

		return pageInfo;
	}

	public PageInfo<String> getIpsWithoutLocation(int page, int size) {
		Page<String> thePage = PageHelper.startPage(page, size);

		List<IpInfo> ipInfos = ipInfoService.getIpsWithoutLocation();

		PageInfo<String> pageInfo = PageInfo.of(thePage);
		if (ipInfos != null) {
			List<String> ips = new ArrayList<>();
			ipInfos.forEach(item -> {
				ips.add(item.getIp());
			});

			pageInfo.setList(ips);
		}

		return pageInfo;
	}

	public void updateLoginLocation(String ip, String location) {
		loginLogService.updateClientLocation(ip, location);
		ipInfoService.updateLocation(ip, location);
	}

	public void saveAuditLog(AuditLog auditLog) {

	}

}