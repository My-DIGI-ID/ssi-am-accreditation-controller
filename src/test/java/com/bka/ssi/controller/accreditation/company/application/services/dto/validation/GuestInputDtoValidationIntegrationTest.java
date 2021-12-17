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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.GuestInputDto;
import com.bka.ssi.controller.accreditation.company.testutilities.TestConstraintValidatorFactory;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestInputDtoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class GuestInputDtoValidationIntegrationTest {

    private static Validator validator;

    private static GuestInputDtoBuilder guestInputDtoBuilder;

    @BeforeAll
    public static void init() {
        TestConstraintValidatorFactory testConstraintValidatorFactory =
            new TestConstraintValidatorFactory();
        testConstraintValidatorFactory.init();

        validator = Validation.byDefaultProvider().configure()
            .constraintValidatorFactory(testConstraintValidatorFactory)
            .buildValidatorFactory()
            .getValidator();

        guestInputDtoBuilder = new GuestInputDtoBuilder();
    }

    @BeforeEach
    public void setup() {
        guestInputDtoBuilder.reset();
    }

    @Test
    void shouldHaveNoViolations() {
        // given:
        GuestInputDto dto = guestInputDtoBuilder.buildGuestInputDto();

        // when:
        Set<ConstraintViolation<GuestInputDto>> violations = validator.validate(dto);

        // then:
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void shouldDetectEmptyEmail() {
        // given:
        guestInputDtoBuilder.email = "";
        GuestInputDto dto = guestInputDtoBuilder.buildGuestInputDto();

        // when:
        Set<ConstraintViolation<GuestInputDto>> violations = validator.validate(dto);

        // then:
        assertEquals(1, violations.size());

        ConstraintViolation<GuestInputDto> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("", violation.getInvalidValue());
    }

    @Test
    void shouldDetectInvalidEmail() {
        // given:
        guestInputDtoBuilder.email = "example.com";
        GuestInputDto dto = guestInputDtoBuilder.buildGuestInputDto();

        // when:
        Set<ConstraintViolation<GuestInputDto>> violations = validator.validate(dto);

        // then:
        assertEquals(1, violations.size());

        ConstraintViolation<GuestInputDto> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals(dto.getEmail(), violation.getInvalidValue());
    }

    @Test
    void shouldDetectInvalidCharacters() {
        // given:
        guestInputDtoBuilder.firstName = "Test!";
        GuestInputDto dto = guestInputDtoBuilder.buildGuestInputDto();

        // when:
        Set<ConstraintViolation<GuestInputDto>> violations = validator.validate(dto);

        // then:
        assertEquals(1, violations.size());

        ConstraintViolation<GuestInputDto> violation = violations.iterator().next();
        assertEquals("firstName", violation.getPropertyPath().toString());
        assertEquals(dto.getFirstName(), violation.getInvalidValue());
    }
}
