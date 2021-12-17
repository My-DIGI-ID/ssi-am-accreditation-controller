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

package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employee;

import com.bka.ssi.controller.accreditation.company.aop.utilities.I18nMessageBuilder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Employee state validator.
 */
public class EmployeeStateValidator
    implements ConstraintValidator<EmployeeState, String> {

    private final I18nMessageBuilder i18nMessageBuilder;

    /**
     * Instantiates a new Employee state validator.
     *
     * @param i18nMessageBuilder the 18 n message builder
     */
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
