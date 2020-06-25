package com.cy.pj.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * 在此切面中讲解各种通知的执行顺序
 * @author qilei
 */
@Order(1)
@Aspect
@Component
public class SysTimeAspect {

	@Pointcut("bean(sysUserServiceImpl)")
	public void doTime() {}

	@Around("doTime()")
	public Object doAround(ProceedingJoinPoint pj) throws Throwable {
		try {
			System.out.println("SysTimeAspect.doAround.before");
			Object result = pj.proceed();
			System.out.println("doAround.after");
			return result;
		} catch (Throwable e) {
			System.out.println("throw exception");
			throw e;
		}
	}

	@Before("doTime()")
	public void dobefore() {
		System.out.println("time doBefore");
	}

	@After("doTime()")
	public void doAfter() {
		System.out.println("time doAfter");
	}

	@AfterReturning("doTime()")
	public void doAfterReturning() {
		System.out.println("time doAfterReturning");
	}

	@AfterThrowing("doTime()")
	public void doAfterThrowing() {
		System.out.println("time doAfterThrowing");
	}
}