package com.bka.ssi.controller.accreditation.company.application.services.dto.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.aop.utilities.i18nMessageBuilder;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.general.NoForbiddenCharacters;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeInputDtoBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class EmployeeValidationIntegrationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private static EmployeeInputDtoBuilder employeeInputDtoBuilder;
    private final NoForbiddenCharacters noForbiddenCharacters =
        Mockito.mock(NoForbiddenCharacters.class);
    private ConstraintValidatorContext constraintValidatorContext;
    private i18nMessageBuilder i18nMessageBuilder;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        employeeInputDtoBuilder = new EmployeeInputDtoBuilder();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @BeforeEach
    public void resetStrings() {
        employeeInputDtoBuilder.reset();
    }

    @Test
    @Disabled
    /*
    ToDo: Enable Integration Test again once Mocking is solved in BKAACMGT-173 and BKAACMGT-174
    */
    public void shouldHaveNoViolations() {
        // given:
        employeeInputDtoBuilder.firstName = "Test";
        employeeInputDtoBuilder.lastName = "Test";
        employeeInputDtoBuilder.email = "test@example.com";

        EmployeeInputDto employee = employeeInputDtoBuilder.build();
        // when:
        Set<ConstraintViolation<EmployeeInputDto>> violations =
            validator.validate(employee);
        // then:
        assertTrue(violations.isEmpty());
    }

    @Test
    @Disabled
    /*
    ToDo: Enable Integration Test again once Mocking is solved in BKAACMGT-173
    */
    public void shouldDetectEmptyEmail() {
        // given empty email:
        employeeInputDtoBuilder.firstName = "Test";
        employeeInputDtoBuilder.lastName = "Test";
        employeeInputDtoBuilder.email = "";

        EmployeeInputDto employee = employeeInputDtoBuilder.build();
        // when:
        Set<ConstraintViolation<EmployeeInputDto>> violations =
            validator.validate(employee);
        // then:
        assertEquals(violations.size(), 1);
        ConstraintViolation<EmployeeInputDto> violation = violations.iterator().next();
        assertEquals("must not be empty", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("", violation.getInvalidValue());
    }

    @Test
    @Disabled
    /*
    ToDo: Enable Integration Test again once Mocking is solved in BKAACMGT-173
    */
    public void shouldDetectInvalidCharacters() {
        // given invalid firstName:
        employeeInputDtoBuilder.firstName = "Test!";
        employeeInputDtoBuilder.lastName = "Test";
        employeeInputDtoBuilder.email = "test@example.com";

        EmployeeInputDto employee = employeeInputDtoBuilder.build();
        // when:
        Set<ConstraintViolation<EmployeeInputDto>> violations =
            validator.validate(employee);
        // then:
        assertEquals(1, violations.size());
        ConstraintViolation<EmployeeInputDto> violation = violations.iterator().next();
        assertEquals("invalid characters in string", violation.getMessage());
        assertEquals("firstName", violation.getPropertyPath().toString());
        assertEquals(employeeInputDtoBuilder.firstName, violation.getInvalidValue());
    }

    @Test
    @Disabled
    /*
    ToDo: Enable Integration Test again once Mocking is solved in BKAACMGT-173
    */
    public void shouldDetectInvalidEmail() {
        // given invalid email:
        employeeInputDtoBuilder.firstName = "Test";
        employeeInputDtoBuilder.lastName = "Test";
        employeeInputDtoBuilder.email = "example.com";

        EmployeeInputDto employee = employeeInputDtoBuilder.build();
        // when:
        Set<ConstraintViolation<EmployeeInputDto>> violations =
            validator.validate(employee);
        // then:
        assertEquals(violations.size(), 1);
        ConstraintViolation<EmployeeInputDto> violation = violations.iterator().next();
        assertEquals("must be a well-formed email address", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals(employeeInputDtoBuilder.email, violation.getInvalidValue());
    }
}
