package com.bka.ssi.controller.accreditation.company.aop.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class i18nMessageBuilder {

    private final MessageSource messageSource;

    public i18nMessageBuilder(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public String create(String messageKey) {
        String errorMessage;
        Locale loc = LocaleContextHolder.getLocale();
        try {
            errorMessage =
                messageSource.getMessage(messageKey, null, loc);
        } catch (Exception e) {
            errorMessage = "No message available";
        }
        return errorMessage;
    }
}
