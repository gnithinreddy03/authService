package com.nithin.authService.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("within(com.nithin.authService.controller..*)")
    public Object logServiceMethod(ProceedingJoinPoint point) throws Throwable{
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        String fullMethodName = className + "." + methodName;
        long startTime = System.currentTimeMillis();
        log.info("[METHOD_START] {}", fullMethodName);
        try {
            Object result = point.proceed();
            long endTime = System.currentTimeMillis() - startTime;
            log.info("[METHOD_SUCCESS] {} executed in {}ms.", fullMethodName, endTime);
            return result;
        }catch (Throwable e){
            long endTime = System.currentTimeMillis() - startTime;
            log.error("[METHOD_ERROR] {} executed in {}ms", fullMethodName, endTime, e);
            throw e;
        }
    }
}
