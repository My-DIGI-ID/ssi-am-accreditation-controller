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

public class NoWhitespacesValidationTest {

    private final static String VALID_STRING = "";
    private final static String INVALID_STRING = " a b c ";

    private static NoWhitespaces noWhitespaces;
    private static ConstraintValidatorContext constraintValidatorContext;
    private static I18nMessageBuilder i18nMessageBuilder;
    private static ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;
    private static NoWhitespacesValidator noWhitespacesValidator;

    @BeforeAll
    static void init() {
        noWhitespaces = Mockito.mock(NoWhitespaces.class);
        constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
        constraintViolationBuilder =
            Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        i18nMessageBuilder = Mockito.mock(I18nMessageBuilder.class);
        noWhitespacesValidator = new NoWhitespacesValidator(i18nMessageBuilder);

        noWhitespacesValidator.initialize(noWhitespaces);
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
            noWhitespacesValidator.isValid(testObject.field, constraintValidatorContext);

        // then:
        assertTrue(result);
    }

    @Test
    public void shouldHaveViolations() {
        // given:
        TestValidatorClass testObject = new TestValidatorClass(INVALID_STRING);

        // when:
        boolean result =
            noWhitespacesValidator.isValid(testObject.field, constraintValidatorContext);

        // then:
        assertFalse(result);
    }
}
