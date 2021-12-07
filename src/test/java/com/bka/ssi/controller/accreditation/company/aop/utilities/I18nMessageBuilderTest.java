package com.bka.ssi.controller.accreditation.company.aop.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

public class I18nMessageBuilderTest {

    private MessageSource messageSource;
    private static final String existingMessageKey = "test.message.found";
    private static final String missingMessageKey = "message.not.found";
    private I18nMessageBuilder messageBuilder;

    @BeforeEach
    void setUp() {
        messageSource = Mockito.mock(MessageSource.class);
        Mockito
            .when(messageSource.getMessage(
                Mockito.eq(existingMessageKey),
                Mockito.any(), Mockito.any()))
            .thenReturn("found the message");
        Mockito
            .when(messageSource.getMessage(
                Mockito.eq(missingMessageKey),
                Mockito.any(), Mockito.any()))
            .thenThrow(new NoSuchMessageException(""));

        messageBuilder = new I18nMessageBuilder(messageSource);
    }

    @Test
    public void shouldCreateMessageFromMessageSource() {
        // Given existing messageKey
        String messageKey = existingMessageKey;

        // When creating error message
        String errorMessage = messageBuilder.create(messageKey);

        //Then return found message
        assertEquals("found the message", errorMessage);
    }

    @Test
    public void shouldDefaultIfNoMessageFound() {
        // Given missing message key
        String messageKey = missingMessageKey;

        // When creating error message
        String errorMessage = messageBuilder.create(messageKey);

        // Then return default
        assertEquals("No message available", errorMessage);
    }
}
