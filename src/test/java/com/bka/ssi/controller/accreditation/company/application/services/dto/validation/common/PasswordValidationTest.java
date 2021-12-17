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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import com.bka.ssi.controller.accreditation.company.aop.utilities.I18nMessageBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.TestValidatorClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

public class PasswordValidationTest {

    private static final String VALID_STRING = "test123!";
    private static final String INVALID_STRING = "äáâàăçéëêèïíììñóöôòøöșțüúüûùß";

    private static Password password;
    private static ConstraintValidatorContext constraintValidatorContext;
    private static I18nMessageBuilder i18nMessageBuilder;
    private static ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;
    private static PasswordValidator passwordValidator;

    @BeforeAll
    static void init() {
        password = Mockito.mock(Password.class);
        constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
        constraintViolationBuilder =
            Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        i18nMessageBuilder = Mockito.mock(I18nMessageBuilder.class);
        passwordValidator = new PasswordValidator(i18nMessageBuilder);

        passwordValidator.initialize(password);
        Mockito.when(i18nMessageBuilder.create(anyString())).thenReturn("Public error message");
        Mockito.when(
            constraintValidatorContext.buildConstraintViolationWithTemplate(Mockito.anyString()))
            .thenReturn(constraintViolationBuilder);
    }

    @BeforeEach
    void setup() {
    }

    @Test
    public void shouldHaveNoViolations() {
        // given:
        TestValidatorClass testObject = new TestValidatorClass(VALID_STRING);

        // when:
        boolean result =
            passwordValidator.isValid(testObject.field, constraintValidatorContext);

        // then:
        assertTrue(result);
    }

    @Test
    public void shouldHaveViolations() {
        // given:
        TestValidatorClass testObject = new TestValidatorClass(INVALID_STRING);

        // when:
        boolean result =
            passwordValidator.isValid(testObject.field, constraintValidatorContext);

        // then:
        assertFalse(result);
    }
}
