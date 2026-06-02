package com.microinfra.auth;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.microinfra.commons.bean.UserProfile;
import com.microinfra.commons.lang.BizException;
import com.microinfra.framework.ApplicationContextHelper;
import com.microinfra.framework.HasAuthority;
import com.microinfra.framework.UserContextHolder;

@Aspect
@Component
public class AuthorityAspect {

	@Pointcut("@annotation(com.microinfra.framework.HasAuthority)")
	public void checkAuthorityPointCut() {
	}

	@Around("checkAuthorityPointCut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		HasAuthority theAnnotation = method.getAnnotation(HasAuthority.class);
		if (theAnnotation == null) {
			return joinPoint.proceed();
		}

		UserProfile currentUser = UserContextHolder.getCurrentUser();
		if (currentUser == null || currentUser.getMenus() == null) {
			throw new BizException("没有操作权限");
		}

		String appCode = ApplicationContextHelper.getApplicationName();
		Map<Long, String> services = currentUser.getAppServicesMappings().get(appCode);
		if (CollectionUtils.isEmpty(services)) {
			throw new BizException("没有操作权限");
		}

//		Long currentServiceId = ApplicationContextHelper.getCurrentServiceId();//TODO
		String authorityId = theAnnotation.value();
		boolean hasAuthority = false;
		if (currentUser.getServiceAuthoritiesMappings().containsKey(0L)) {
			if (currentUser.getServiceAuthoritiesMappings().get(0L).containsKey("0") || currentUser.getServiceAuthoritiesMappings().get(0L).containsValue(authorityId)) {
				hasAuthority = true;
			}
		} else {
			for (Long sid : currentUser.getServiceAuthoritiesMappings().keySet()) {
				if (services.containsKey(sid)) {
					if (currentUser.getServiceAuthoritiesMappings().get(sid).containsKey("0") || currentUser.getServiceAuthoritiesMappings().get(sid).containsKey(authorityId)) {
						hasAuthority = true;
					}
				}
			}
		}

		if (!hasAuthority) {
			throw new BizException("没有操作权限");
		}

		return joinPoint.proceed();
	}

}