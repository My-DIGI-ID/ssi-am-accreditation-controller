package com.bka.ssi.controller.accreditation.company.application.security.facade;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SSOProtectedTransaction {

    String scope() default "bypassed";

    String resource() default "bypassed";
}
