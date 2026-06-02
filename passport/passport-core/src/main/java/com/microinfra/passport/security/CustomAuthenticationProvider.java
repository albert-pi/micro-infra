package com.microinfra.passport.security;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alibaba.fastjson2.JSONObject;
import com.microinfra.commons.bean.IdentityType;
import com.microinfra.commons.bean.UserProfile;
import com.microinfra.framework.ApplicationContextHelper;
import com.microinfra.passport.params.PassportCredentials;

@SuppressWarnings("unchecked")
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Map<IdentityType, Authenticator<? extends PassportCredentials>> AUTHENTICATORS = new HashMap<>();

	private static final Map<IdentityType, Type> AUTHENTICATOR_PARAM_TYPES = new HashMap<>();

	@SuppressWarnings("rawtypes")
	public CustomAuthenticationProvider() {
		Map<String, Authenticator> authenticators = ApplicationContextHelper.getBeans(Authenticator.class);

		authenticators.entrySet().forEach(entry -> {
			Authenticator authenticator = entry.getValue();
			Type[] types = authenticator.getClass().getGenericInterfaces();
			Type[] typeArgs = ((ParameterizedType) types[0]).getActualTypeArguments();

			AUTHENTICATORS.put(authenticator.getIdentityType(), authenticator);
			AUTHENTICATOR_PARAM_TYPES.put(authenticator.getIdentityType(), typeArgs[0]);
		});
	}

	public Authenticator<? extends PassportCredentials> getAuthenticator(IdentityType identityType) {
		return AUTHENTICATORS.get(identityType);
	}

	public Type getAuthenticatorGenericType(IdentityType identityType) {
		return AUTHENTICATOR_PARAM_TYPES.get(identityType);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		PassportCredentials credentials = (PassportCredentials) authentication.getCredentials();
		IdentityType identityType = IdentityType.of(credentials.getType());
		Authenticator<? extends PassportCredentials> authenticator = this.getAuthenticator(identityType);
		Type authenticatorGenericType = this.getAuthenticatorGenericType(identityType);

		UserProfile userProfile = authenticator.authenticate(JSONObject.from(credentials).to(authenticatorGenericType));
		CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(userProfile, null);
		authenticationToken.setDetails(authentication.getDetails());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (CustomAuthenticationToken.class.isAssignableFrom(authentication));
	}

}