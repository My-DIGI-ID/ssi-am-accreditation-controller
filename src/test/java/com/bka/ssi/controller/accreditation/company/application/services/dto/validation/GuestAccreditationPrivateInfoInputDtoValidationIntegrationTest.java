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
