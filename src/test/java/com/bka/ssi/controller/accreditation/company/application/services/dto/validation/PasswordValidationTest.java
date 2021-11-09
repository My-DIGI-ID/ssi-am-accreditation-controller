package com.bka.ssi.controller.accreditation.company.application.services.dto.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import com.bka.ssi.controller.accreditation.company.aop.utilities.i18nMessageBuilder;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.password.MatchPatternPassword;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.password.PasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

public class PasswordValidationTest {

    private static final String VALID_PASSWORD = "test123!";
    private static final String INVALID_PASSWORD = "äáâàăçéëêèïíììñóöôòøöșțüúüûùß";
    private final MatchPatternPassword matchPatternPassword =
        Mockito.mock(MatchPatternPassword.class);
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
        // given valid password:
        PasswordValidator passwordValidator = new PasswordValidator(i18nMessageBuilder);
        passwordValidator.initialize(matchPatternPassword);
        TestPassword testObject = new TestPassword(VALID_PASSWORD);

        // when:
        boolean result = passwordValidator.isValid(testObject.field, constraintValidatorContext);

        // then:
        assertTrue(result);
    }

    @Test
    public void shouldReturnValidationError() {
        // given invalid password:
        PasswordValidator passwordValidator = new PasswordValidator(i18nMessageBuilder);
        passwordValidator.initialize(matchPatternPassword);
        TestPassword testObject = new TestPassword(INVALID_PASSWORD);

        // when:
        boolean result = passwordValidator.isValid(testObject.field, constraintValidatorContext);

        // then:
        assertFalse(result);

    }

    static class TestPassword {

        @MatchPatternPassword
        String field;

        TestPassword(String field) {
            this.field = field;
        }

    }
}
