package com.chamago.pcrm.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME) 
public @interface MemCached {
	
	String expiredmins() default "30";
}
