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

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations.GuestAccreditationPrivateInfoInputDto;
import com.bka.ssi.controller.accreditation.company.testutilities.TestConstraintValidatorFactory;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.GuestAccreditationPrivateInfoInputDtoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class GuestAccreditationPrivateInfoInputDtoValidationIntegrationTest {

    private static Validator validator;

    private static GuestAccreditationPrivateInfoInputDtoBuilder
        guestAccreditationPrivateInfoInputDtoBuilder;

    @BeforeAll
    public static void init() {
        TestConstraintValidatorFactory testConstraintValidatorFactory =
            new TestConstraintValidatorFactory();
        testConstraintValidatorFactory.init();

        validator = Validation.byDefaultProvider().configure()
            .constraintValidatorFactory(testConstraintValidatorFactory)
            .buildValidatorFactory()
            .getValidator();

        guestAccreditationPrivateInfoInputDtoBuilder =
            new GuestAccreditationPrivateInfoInputDtoBuilder();
    }

    @BeforeEach
    public void setup() {
        guestAccreditationPrivateInfoInputDtoBuilder.reset();
    }

    @Test
    void shouldHaveNoViolations() {
        // given:
        GuestAccreditationPrivateInfoInputDto dto = guestAccreditationPrivateInfoInputDtoBuilder
            .buildGuestAccreditationPrivateInfoInputDto();

        // when:
        Set<ConstraintViolation<GuestAccreditationPrivateInfoInputDto>> violations =
            validator.validate(dto);

        // then:
        Assertions.assertTrue(violations.isEmpty());
    }
}
