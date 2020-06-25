package com.cy.pj.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义注解
 * @author qilei
 */
@Retention(RetentionPolicy.RUNTIME)//定义我们的注解何时有效(例如运行时)
@Target({ElementType.METHOD}) //定义我们的注解可以描述的对象
public @interface RequiredCache {//RequiredCache.class
	String key() default "";
}
