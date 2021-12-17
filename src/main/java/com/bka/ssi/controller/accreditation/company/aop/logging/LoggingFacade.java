/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The type Logging facade.
 * ToDo: Currently unused
 */
@Aspect
@Component
public class LoggingFacade {

    private final Logger logger;

    /**
     * Instantiates a new Logging facade.
     *
     * @param logger the logger
     */
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

    /**
     * Log before.
     *
     * @param joinPoint the join point
     */
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

    /**
     * Log after.
     *
     * @param joinPoint the join point
     */
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
