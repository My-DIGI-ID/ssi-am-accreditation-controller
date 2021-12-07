package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employee;

import com.bka.ssi.controller.accreditation.company.aop.utilities.I18nMessageBuilder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployeeStateValidator
    implements ConstraintValidator<EmployeeState, String> {

    private final I18nMessageBuilder i18nMessageBuilder;

    public EmployeeStateValidator(I18nMessageBuilder i18nMessageBuilder) {
        this.i18nMessageBuilder = i18nMessageBuilder;
    }

    @Override
    public void initialize(EmployeeState constraint) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        String errorMessage = this.i18nMessageBuilder
            .create("message.common.validations.error.employee_state");
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();

        return value.equalsIgnoreCase(
            com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeState.INTERNAL
                .getName())
            || value.equalsIgnoreCase(
            com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeState.EXTERNAL
                .getName())
            || value.equalsIgnoreCase(
            com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeState.SERVICE_PROVIDER
                .getName());
    }
}
