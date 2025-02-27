package com.cognizant.loan_application.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect 
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Before("execution(* com.cognizant.loan_application.service.*.*(..)) || execution(* com.loan_application.controller.*.*(..))")
	public void logBeforeMethodExecution(JoinPoint joinPoint) {
		logger.info("Executing method: {}", joinPoint.getSignature());
	}
	@AfterReturning(pointcut = "execution(* com.cognizant.loan_application.service.*.*(..)) || execution(* com.cognizant.loan_application.controller.*.*(..))", returning = "result")
	public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
		logger.info("Method executed: {} with result: {}", joinPoint.getSignature(), result);
	}
	@AfterThrowing(pointcut = "execution(* com.cognizant.loan_application.service.*.*(..)) || execution(* com.cognizant.loan_application.controller.*.*(..))", throwing = "exception")
	public void logAfterMethodThrowing(JoinPoint joinPoint, Throwable exception) {
		logger.error("Method executed: {} with exception: {}", joinPoint.getSignature(), exception.getMessage());
	}
}
