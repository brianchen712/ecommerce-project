package com.brian.springboot.ecommerceproject.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
	public void beforeForAll(JoinPoint joinPoint) {

		String theMethod = joinPoint.getSignature().toShortString();
		myLogeer.info("=======> in @Before: calling method: " + theMethod);
		
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			myLogeer.info("=====> argument: " + arg);
		}
		myLogeer.info("\n");
	}

	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String theMethod = joinPoint.getSignature().toShortString();
		myLogeer.info("=======> in @AfterReturning: calling method: " + theMethod);

		myLogeer.info("=======> result: " + result);
		myLogeer.info("\n");
	}

}
