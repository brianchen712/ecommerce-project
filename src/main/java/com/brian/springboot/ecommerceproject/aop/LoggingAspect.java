package com.brian.springboot.ecommerceproject.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	// 設定紀錄器
	private Logger myLogeer = Logger.getLogger(getClass().getName());

	// 設定切點
	@Pointcut("execution(* com.brian.springboot.ecommerceproject.controller.*.*(..))")
	private void forControllerPackage() {
	}

	@Pointcut("execution(* com.brian.springboot.ecommerceproject.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* com.brian.springboot.ecommerceproject.dao.*.*(..))")
	private void forDaoPackage() {
	}
	
	@Pointcut("execution(* com.brian.springboot.ecommerceproject.utils.*.*(..))")
	private void forUtilsPackage() {
	}
	
	@Pointcut("execution(* com.brian.springboot.ecommerceproject.validator.*.*(..))")
	private void forValidatorPackage() {
	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage() || forUtilsPackage() || forValidatorPackage()")
	private void forAppFlow() {
	}

	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {

		String theMethod = joinPoint.getSignature().toShortString();
		myLogeer.info("=======> in @Before: calling method: " + theMethod);
		
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			myLogeer.info("=====> argument: " + arg);
		}
		myLogeer.info("\n");
	}
	
	@Around("forAppFlow()")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {

		String theMethod = joinPoint.getSignature().toShortString();
		myLogeer.info("=======> Executing: @Around on method: " + theMethod);
		
		long begin = System.currentTimeMillis();
		
		Object proceed = joinPoint.proceed();
		
		long end = System.currentTimeMillis();
		
		long duration = end - begin;
		
		System.out.println("\n======> Duration: " + duration /1000.0 + "seconds");
	}

	@After("forAppFlow()")
	public void after(JoinPoint joinPoint) {
		String theMethod = joinPoint.getSignature().toShortString();
		myLogeer.info("=======> in @After: calling method: " + theMethod);

		myLogeer.info("\n");
	}

}
