package com.bka.ssi.controller.accreditation.company.application.security.utilities;

import com.bka.ssi.controller.accreditation.company.application.utilities.http.HttpHeaderUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class BearerTokenParserTest {

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2M" +
        "zY1NjA3NTAsImV4cCI6MTYzNjU2MDY1NiwiYXVkIjoiIiwic3ViIjoiIiwicHJlZmVycmVkX3VzZXJuYW1lIjoid" +
        "GVzdCJ9.tvzD1HhMllyEFhzujKkRcKr1lBsqRACLbXm-b6-UOEk";

    private String invalidToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.e3Rlc3QiaXNzIjoiIi" +
        "wiaWF0IjoxNjM2NTYwNzUwLCJleHAiOjE2MzY1NjA2NTYsImF1ZCI6IiIsInN1YiI6IiIsInByZWZlcnJlZF91c2" +
        "VybmFtZSI6InRlc3QifQ==";

    private BearerTokenParser bearerTokenParser;

    @BeforeEach
    void init() {
        bearerTokenParser = new BearerTokenParser();
        ReflectionTestUtils.setField(bearerTokenParser, "userIdentifierEntry",
            "preferred_username");
    }

    @Test
    public void parseUserFromJWTToken() {
        try (MockedStatic<HttpHeaderUtility> httpHeaderUtilityMockedStatic =
                 Mockito.mockStatic(HttpHeaderUtility.class)) {
            httpHeaderUtilityMockedStatic.when(
                    () -> HttpHeaderUtility.getHttpHeader("authorization"))
                .thenReturn(token);

            Assertions.assertEquals("test", bearerTokenParser.parseUserFromJWTToken());
        }
    }

    @Test
    void parseUserFromInvalidToken() {
        try (MockedStatic<HttpHeaderUtility> httpHeaderUtilityMockedStatic =
                 Mockito.mockStatic(HttpHeaderUtility.class)) {
            httpHeaderUtilityMockedStatic.when(
                    () -> HttpHeaderUtility.getHttpHeader("authorization"))
                .thenReturn(invalidToken);

            Assertions.assertEquals("undefined", bearerTokenParser.parseUserFromJWTToken());
        }
    }
}
