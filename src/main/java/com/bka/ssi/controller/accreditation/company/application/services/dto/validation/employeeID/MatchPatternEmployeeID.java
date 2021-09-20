package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employeeID;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeIDPatternValidator.class)
@Documented
public @interface MatchPatternEmployeeID {

    String message() default "invalid characters in employee ID";

    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};

}
