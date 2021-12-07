package com.bka.ssi.controller.accreditation.company.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingFacade {

    private final Logger logger;

    public LoggingFacade(Logger logger) {
        this.logger = logger;
    }

    private LoggingUtility.Level getLoggingLevel(MethodSignature methodSignature) {
        return methodSignature.getMethod()
            .getAnnotation(LoggingOperation.class)
            .level();
    }

    private String getMethodName(MethodSignature methodSignature) {
        return methodSignature.getMethod().getName();
    }

    private String getClassName(MethodSignature methodSignature) {
        return methodSignature.getMethod().getDeclaringClass().getName();
    }

    @Before("@annotation(com.bka.ssi.controller.accreditation.company.aop.logging.LoggingOperation)")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LoggingUtility.Level level = this.getLoggingLevel(methodSignature);
        String methodName = this.getMethodName(methodSignature);
        String className = this.getClassName(methodSignature);

        // ToDo - enrich generic data to be logged
        LoggingUtility.log(logger, level, "start: method {} in class {}",
            new Object[] {methodName, className});
    }

    @After("@annotation(com.bka.ssi.controller.accreditation.company.aop.logging.LoggingOperation)")
    public void logAfter(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LoggingUtility.Level level = this.getLoggingLevel(methodSignature);
        String methodName = this.getMethodName(methodSignature);
        String className = this.getClassName(methodSignature);

        // ToDo - enrich generic data to be logged
        LoggingUtility.log(logger, level, "end: method {} in class {}",
            new Object[] {methodName, className});
    }
}
