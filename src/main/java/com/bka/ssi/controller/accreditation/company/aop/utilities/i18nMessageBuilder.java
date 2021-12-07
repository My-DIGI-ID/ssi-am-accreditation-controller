package com.bka.ssi.controller.accreditation.company.aop.utilities;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class I18nMessageBuilder {

    private final MessageSource messageSource;

    public I18nMessageBuilder(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String create(String messageKey) {
        String errorMessage;
        Locale locale = LocaleContextHolder.getLocale();

        try {
            errorMessage =
                messageSource.getMessage(messageKey, null, locale);
        } catch (Exception e) {
            errorMessage = "No message available";
        }

        return errorMessage;
    }
}
