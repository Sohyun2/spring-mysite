package com.douzone.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.METHOD, ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	public enum Role { admin, user }
	Role value() default Role.user;
}
