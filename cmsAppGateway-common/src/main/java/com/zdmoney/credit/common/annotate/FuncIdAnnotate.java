package com.zdmoney.credit.common.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 业务功能号 注解
 * 
 * @author Ivan
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FuncIdAnnotate {
	/** 功能号 **/
	String value();

	/** 功能号描述 **/
	String desc() default "";

	/** 业务参数类型 **/
	Class<?> voCls();

	/** 是否依赖登陆 **/
	boolean isDependLogin() default true;
}
