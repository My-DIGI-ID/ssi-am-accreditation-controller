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

package com.bka.ssi.controller.accreditation.company.application.services.dto.validation;

import com.bka.ssi.controller.accreditation.company.application.exceptions.BundleConstraintViolationExceptionsException;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.testutilities.TestConstraintValidatorFactory;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeInputDtoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(ValidationServiceTest.class);

    private static Validator validator;
    private static ValidationService validationService;

    private static EmployeeInputDtoBuilder employeeInputDtoBuilder;

    @BeforeAll
    public static void init() {
        TestConstraintValidatorFactory testConstraintValidatorFactory =
            new TestConstraintValidatorFactory();
        testConstraintValidatorFactory.init();

        validator = Validation.byDefaultProvider().configure()
            .constraintValidatorFactory(testConstraintValidatorFactory)
            .buildValidatorFactory()
            .getValidator();

        validationService = new ValidationService(validator, logger);

        employeeInputDtoBuilder = new EmployeeInputDtoBuilder();
    }

    @BeforeEach
    public void setup() {
        this.employeeInputDtoBuilder.reset();
    }

    @Test
    void validEmployeeInputDto() {
        // given:
        EmployeeInputDto dto = employeeInputDtoBuilder.buildEmployeeInputDto();

        // when:
        boolean isValid = this.validationService.validate(dto);

        // then:
        Assertions.assertTrue(isValid);
    }

    @Test
    void invalidEmployeeInputDto() {
        // given:
        employeeInputDtoBuilder.firstName = "$%&";
        EmployeeInputDto dto = employeeInputDtoBuilder.buildEmployeeInputDto();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            // when:
            boolean isValid = this.validationService.validate(dto);

            // then:
            Assertions.assertFalse(isValid);
        });

        Assertions.assertEquals("firstName: " + TestConstraintValidatorFactory.i18n_ERROR_MESSAGE,
            exception.getMessage());
    }

    @Test
    void validEmployeeInputDtos() throws BundleConstraintViolationExceptionsException {
        // given:
        EmployeeInputDto dto = employeeInputDtoBuilder.buildEmployeeInputDto();
        List<EmployeeInputDto> dtos = Arrays.asList(dto);

        // when:
        boolean isValid = this.validationService.validate(dtos);

        // then:
        Assertions.assertTrue(isValid);
    }

    @Test
    void invalidEmployeeInputDtos() {
        // given:
        employeeInputDtoBuilder.firstName = "$%&";
        EmployeeInputDto invalidDto = employeeInputDtoBuilder.buildEmployeeInputDto();
        employeeInputDtoBuilder.reset();
        EmployeeInputDto validDto = employeeInputDtoBuilder.buildEmployeeInputDto();

        List<EmployeeInputDto> dtos = Arrays.asList(validDto, invalidDto);

        BundleConstraintViolationExceptionsException exception =
            Assertions.assertThrows(BundleConstraintViolationExceptionsException.class, () -> {
                // when:
                boolean isValid = this.validationService.validate(dtos);

                // then:
                Assertions.assertFalse(isValid);
            });

        Assertions.assertEquals(1, exception.getExceptions().size());
        Assertions.assertEquals(
            "index: 1, firstName: " + TestConstraintValidatorFactory.i18n_ERROR_MESSAGE
            , exception.getExceptions().get(0).getMessage());
        Assertions.assertEquals(
            "index: 1, firstName: " + TestConstraintValidatorFactory.i18n_ERROR_MESSAGE,
            exception.getMessage());
    }
}
