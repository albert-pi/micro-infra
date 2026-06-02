package com.microinfra.sysiam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.sysiam.entity.IpInfo;
import com.microinfra.sysiam.mapper.IpInfoMapper;

/**
 * <p>
 * 本地缓存的IP地址信息 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class IpInfoService extends ServiceImpl<IpInfoMapper, IpInfo> {

	public IpInfo getIpInfo(String ip) {
		LambdaQueryWrapper<IpInfo> queryWrapper = Wrappers.<IpInfo>lambdaQuery();
		queryWrapper.eq(IpInfo::getIp, ip).last("limit 1");

		return baseMapper.selectOne(queryWrapper);
	}

	public List<IpInfo> getIpsWithoutLocation() {
		LambdaQueryWrapper<IpInfo> queryWrapper = Wrappers.<IpInfo>lambdaQuery();
		queryWrapper.isNull(IpInfo::getLocation);

		return baseMapper.selectList(queryWrapper);
	}

	public void updateLocation(String ip, String location) {
		LambdaUpdateWrapper<IpInfo> updateWrapper = Wrappers.<IpInfo>lambdaUpdate();
		updateWrapper.set(IpInfo::getLocation, location);
		updateWrapper.eq(IpInfo::getIp, ip);

		baseMapper.update(null, updateWrapper);
	}

}