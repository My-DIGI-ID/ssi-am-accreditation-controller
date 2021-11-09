package com.bka.ssi.controller.accreditation.company.application.services.dto.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import com.bka.ssi.controller.accreditation.company.aop.utilities.i18nMessageBuilder;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.general.ForbiddenCharactersValidator;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.general.NoForbiddenCharacters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

public class ForbiddenCharacterValidationTest {

    private static final String VALID_STRING = "&äáâàăçéëêèïíììñóöôòøöșțüúüûùß-_.123";
    private static final String INVALID_STRING = "{&äáâàăçéëêèïíììñóöôòøöșțüúüûùß-_.123}";

    private final NoForbiddenCharacters noForbiddenCharacters =
        Mockito.mock(NoForbiddenCharacters.class);
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
        // given valid string
        ForbiddenCharactersValidator forbiddenCharactersValidator =
            new ForbiddenCharactersValidator(i18nMessageBuilder);
        forbiddenCharactersValidator.initialize(noForbiddenCharacters);
        TestForbiddenCharacter testObject = new TestForbiddenCharacter(VALID_STRING);

        //when:
        boolean result = forbiddenCharactersValidator.isValid(testObject.field,
            constraintValidatorContext);

        //then:
        assertTrue(result);
    }

    @Test
    public void shouldReturnValidationError() {
        // given invalid string
        ForbiddenCharactersValidator forbiddenCharactersValidator =
            new ForbiddenCharactersValidator(i18nMessageBuilder);
        forbiddenCharactersValidator.initialize(noForbiddenCharacters);
        TestForbiddenCharacter testObject = new TestForbiddenCharacter(INVALID_STRING);

        //when:
        boolean result = forbiddenCharactersValidator.isValid(testObject.field,
            constraintValidatorContext);

        //then:
        assertFalse(result);
    }


    static class TestForbiddenCharacter {

        @NoForbiddenCharacters
        String field;

        TestForbiddenCharacter(String field) {
            this.field = field;
        }
    }
}

