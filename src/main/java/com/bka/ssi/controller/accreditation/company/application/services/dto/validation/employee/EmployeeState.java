package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employee;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeIdValidator.class)
@Documented
public @interface EmployeeState {

    String message() default "invalid enumerate for employee state";

    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};
}
