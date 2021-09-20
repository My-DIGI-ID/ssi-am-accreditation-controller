package com.bka.ssi.controller.accreditation.company.api.common.exceptions.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RestErrorResponseTest {

    RestErrorResponse response;

    @BeforeEach
    void setUp() {
        response =
            new RestErrorResponse(HttpStatus.BAD_REQUEST, "Bad request message",
                "/test/api/endpoint");
    }

    @Test
    void created() {
        assertEquals(response.getStatus(), 400);
        assertEquals(response.getMessage(), "Bad request message");
        assertEquals(response.getPath(), "/test/api/endpoint");
        assertEquals(response.getError(), "Bad Request");
        assertNotEquals(response.getTimestamp(), null);
    }

    @Test
    void getTimestamp() {
        assertNotEquals(response.getTimestamp(), null);
    }

    @Test
    void getStatus() {
        assertEquals(response.getStatus(), 400);
    }

    @Test
    void getError() {
        assertEquals(response.getError(), "Bad Request");
    }

    @Test
    void getMessage() {
        assertEquals(response.getMessage(), "Bad request message");
    }

    @Test
    void getPath() {
        assertEquals(response.getPath(), "/test/api/endpoint");
    }
}
