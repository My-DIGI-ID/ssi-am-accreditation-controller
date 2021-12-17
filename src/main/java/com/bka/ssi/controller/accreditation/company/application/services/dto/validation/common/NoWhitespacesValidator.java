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

package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common;

import com.bka.ssi.controller.accreditation.company.aop.utilities.I18nMessageBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type No whitespaces validator.
 */
public class NoWhitespacesValidator implements ConstraintValidator<NoWhitespaces, String> {

    private final static String PATTERN = "(?i).*[\\s].*";

    private final I18nMessageBuilder i18nMessageBuilder;

    /**
     * Instantiates a new No whitespaces validator.
     *
     * @param i18nMessageBuilder the 18 n message builder
     */
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
