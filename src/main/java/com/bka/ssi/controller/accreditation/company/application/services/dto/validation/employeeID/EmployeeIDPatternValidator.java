package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employeeID;

import com.bka.ssi.controller.accreditation.company.aop.utilities.i18nMessageBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployeeIDPatternValidator
    implements ConstraintValidator<MatchPatternEmployeeID, String> {
    private final String EMPLOYEE_ID_PATTERN = "(?i).*[^a-z0-9_.äöüß-].*";

    private final i18nMessageBuilder i18nMessageBuilder;


    public EmployeeIDPatternValidator(i18nMessageBuilder i18nMessageBuilder) {
        this.i18nMessageBuilder = i18nMessageBuilder;
    }

    @Override
    public void initialize(MatchPatternEmployeeID constraint) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        String errorMessage = this.i18nMessageBuilder
            .create("message.common.validations.error.forbidden_characters_employeeID");
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
        Pattern pattern = Pattern.compile(EMPLOYEE_ID_PATTERN);
        Matcher matcher = pattern.matcher(value);
        return !matcher.lookingAt();
    }

}
