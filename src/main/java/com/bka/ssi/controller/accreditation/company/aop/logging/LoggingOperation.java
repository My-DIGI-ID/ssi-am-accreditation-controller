package com.bka.ssi.controller.accreditation.company.aop.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggingOperation {

    LoggingUtility.Level level() default LoggingUtility.Level.INFO;
}
