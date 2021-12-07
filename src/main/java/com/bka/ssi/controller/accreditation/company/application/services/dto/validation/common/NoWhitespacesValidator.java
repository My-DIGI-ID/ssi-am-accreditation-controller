package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common;

import com.bka.ssi.controller.accreditation.company.aop.utilities.I18nMessageBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoWhitespacesValidator implements ConstraintValidator<NoWhitespaces, String> {

    private final static String PATTERN = "(?i).*[\\s].*";

    private final I18nMessageBuilder i18nMessageBuilder;

    public NoWhitespacesValidator(I18nMessageBuilder i18nMessageBuilder) {
        this.i18nMessageBuilder = i18nMessageBuilder;
    }

    @Override
    public void initialize(NoWhitespaces constraint) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        String errorMessage = this.i18nMessageBuilder
            .create("message.common.validations.error.no_whitespaces");
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(value);
        return !matcher.lookingAt();
    }
}
