package com.jperezmota.wsellfiliates.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Aspect
@Configuration
public class AfterThrowingAspect {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Pointcut("execution(* com.jperezmota.wsellfiliates.views..*(..))")
	public void presentationLayout() {}
	
	@Pointcut("execution(* com.jperezmota.wsellfiliates.services..*(..))")
	public void businessLayout() {}
	
	@Pointcut("execution(* com.jperezmota.wsellfiliates.dao..*(..))")
	public void dataLayout() {}

	@AfterThrowing(
			pointcut = "presentationLayout() || businessLayout() || dataLayout()",
			throwing = "ex")
	public void before(JoinPoint joinPoint, Throwable ex) {
		String methodName = joinPoint.getSignature().toString();
		logger.error("EXCEPTION THROW RUNNING METHOD: " + methodName);
		logger.error("EXCEPTION DETAIL: " + ex);
	}
}
