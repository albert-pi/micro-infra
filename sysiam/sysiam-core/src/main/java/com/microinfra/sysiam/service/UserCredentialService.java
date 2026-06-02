package com.microinfra.sysiam.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microinfra.sysiam.entity.UserCredential;
import com.microinfra.sysiam.mapper.UserCredentialMapper;

/**
 * <p>
 * 用户身份凭证信息 服务实现类
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class UserCredentialService extends ServiceImpl<UserCredentialMapper, UserCredential> {

	public void updateUserCredential(Long userId, Integer identityType, String credential) {
		LambdaUpdateWrapper<UserCredential> updateWrapper = Wrappers.<UserCredential>lambdaUpdate();
		updateWrapper.set(UserCredential::getCredential, credential);
		updateWrapper.eq(UserCredential::getUserId, userId);
		updateWrapper.eq(UserCredential::getIdentityType, identityType);

		baseMapper.update(null, updateWrapper);
	}

	public String getUserCredential(Long userId, Integer identityType) {
		LambdaQueryWrapper<UserCredential> queryWrapper = Wrappers.<UserCredential>lambdaQuery();
		queryWrapper.eq(UserCredential::getUserId, userId);
		queryWrapper.eq(UserCredential::getIdentityType, identityType);
		UserCredential staffCredential = baseMapper.selectOne(queryWrapper);

		return staffCredential != null ? staffCredential.getCredential() : null;
	}

	public void removeUserCredentials(Long userId) {
		LambdaQueryWrapper<UserCredential> queryWrapper = Wrappers.<UserCredential>lambdaQuery();
		queryWrapper.eq(UserCredential::getUserId, userId);

		baseMapper.delete(queryWrapper);
	}

}