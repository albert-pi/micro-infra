package com.microinfra.passport.security;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.microinfra.commons.bean.IdentityType;

@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticateWith {

	IdentityType value() default IdentityType.NAME_PASSWORD;
}