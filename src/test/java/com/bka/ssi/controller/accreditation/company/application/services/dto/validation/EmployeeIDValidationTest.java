package com.bka.ssi.controller.accreditation.company.application.services.dto.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import com.bka.ssi.controller.accreditation.company.aop.utilities.i18nMessageBuilder;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employeeID.EmployeeIDPatternValidator;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employeeID.MatchPatternEmployeeID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

public class EmployeeIDValidationTest {

    private static final String VALID_ID = "ID123_.äöüß-";
    private static final String INVALID_ID = "{ID123}";
    private final MatchPatternEmployeeID matchPatternEmployeeID =
        Mockito.mock(MatchPatternEmployeeID.class);
    private ConstraintValidatorContext constraintValidatorContext;
    private i18nMessageBuilder i18nMessageBuilder;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    /*
    ToDo: Add more extensive testing in BKAACMGT-174
    */

    @BeforeEach
    void setup() {
        i18nMessageBuilder = Mockito.mock(i18nMessageBuilder.class);
        constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
        builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        Mockito.when(i18nMessageBuilder.create(anyString())).thenReturn("Public error message");
        Mockito.when(
            constraintValidatorContext.buildConstraintViolationWithTemplate(Mockito.anyString()))
            .thenReturn(builder);
    }

    @Test
    public void shouldHaveNoViolations() {
        // given valid ID:
        EmployeeIDPatternValidator employeeIDPatternValidator =
            new EmployeeIDPatternValidator(i18nMessageBuilder);
        employeeIDPatternValidator.initialize(matchPatternEmployeeID);
        TestEmployeeID testObject = new TestEmployeeID(VALID_ID);

        //when:
        boolean result = employeeIDPatternValidator.isValid(testObject.field,
            constraintValidatorContext);

        // then:
        assertTrue(result);
    }

    @Test
    public void shouldReturnValidationError() {
        // given invalid ID:
        EmployeeIDPatternValidator employeeIDPatternValidator =
            new EmployeeIDPatternValidator(i18nMessageBuilder);
        employeeIDPatternValidator.initialize(matchPatternEmployeeID);
        TestEmployeeID testObject = new TestEmployeeID(INVALID_ID);

        // when:
        boolean result = employeeIDPatternValidator.isValid(testObject.field,
            constraintValidatorContext);

        // then:
        assertFalse(result);
    }

    static class TestEmployeeID {

        @MatchPatternEmployeeID
        String field;

        TestEmployeeID(String field) {
            this.field = field;
        }
    }
}
