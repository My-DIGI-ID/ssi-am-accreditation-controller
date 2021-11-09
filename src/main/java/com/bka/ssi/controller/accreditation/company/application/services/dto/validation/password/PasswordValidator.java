package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.password;

import com.bka.ssi.controller.accreditation.company.aop.utilities.i18nMessageBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<MatchPatternPassword, String> {
    private static final String PASSWORD_PATTERN = "(?i).*[^a-z0-9_!@#$%^&*()-.?].*";

    private final i18nMessageBuilder i18nMessageBuilder;

    public PasswordValidator(i18nMessageBuilder i18nMessageBuilder) {
        this.i18nMessageBuilder = i18nMessageBuilder;
    }

    @Override
    public void initialize(MatchPatternPassword constraint) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        String errorMessage = this.i18nMessageBuilder
            .create("message.common.validations.error.forbidden_characters_password");
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(value);
        return !matcher.lookingAt();
    }

}
