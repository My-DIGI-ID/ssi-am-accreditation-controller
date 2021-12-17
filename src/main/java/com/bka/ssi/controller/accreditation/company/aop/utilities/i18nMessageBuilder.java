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

package com.bka.ssi.controller.accreditation.company.aop.utilities;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * The type 18 n message builder.
 */
@Component
public class I18nMessageBuilder {

    private final MessageSource messageSource;

    /**
     * Instantiates a new 18 n message builder.
     *
     * @param messageSource the message source
     */
    public I18nMessageBuilder(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Create string.
     *
     * @param messageKey the message key
     * @return the string
     */
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
