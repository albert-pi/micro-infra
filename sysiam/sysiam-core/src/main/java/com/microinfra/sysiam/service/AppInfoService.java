package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.commons.bean.DataOption;
import com.microinfra.sysiam.entity.AppInfo;
import com.microinfra.sysiam.mapper.AppInfoMapper;

/**
 * <p>
 * 系统应用 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class AppInfoService extends ServiceImpl<AppInfoMapper, AppInfo> {

	public List<DataOption> getAppOptions() {
		return baseMapper.selectAppOptions();
	}

}