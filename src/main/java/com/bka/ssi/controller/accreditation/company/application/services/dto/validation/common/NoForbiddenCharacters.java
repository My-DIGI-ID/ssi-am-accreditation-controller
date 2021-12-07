package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoForbiddenCharactersValidator.class)
@Documented
public @interface NoForbiddenCharacters {

    String message() default "forbidden characters in string";

    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};
}
