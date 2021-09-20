package com.bka.ssi.controller.accreditation.company.application.services.dto.validation.general;

import com.bka.ssi.controller.accreditation.company.aop.utils.i18nMessageBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ForbiddenCharactersValidator
    implements ConstraintValidator<NoForbiddenCharacters, String> {
    /* ToDo - ForbiddenCharactersValidator does not allow Whitespaces */

    private final String PASSWORD_PATTERN =
        "(?i).*[^\\\\sa-z0-9_.&äáâàăçéëêèïíììñóöôòøöșțüúüûùß-].*";


    private final i18nMessageBuilder i18nMessageBuilder;

    public ForbiddenCharactersValidator(i18nMessageBuilder i18nMessageBuilder) {
        this.i18nMessageBuilder = i18nMessageBuilder;
    }

    @Override
    public void initialize(NoForbiddenCharacters constraint) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        String errorMessage = this.i18nMessageBuilder
            .create("message.common.validations.error.forbidden_characters");
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(value);
        return !matcher.lookingAt();
    }


}
