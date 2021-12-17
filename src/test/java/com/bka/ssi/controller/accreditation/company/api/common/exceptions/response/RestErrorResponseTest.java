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

    @Test
    void testToString() {
        assertEquals("status: 400; message: Bad request message; path: /test/api/endpoint;",
            response.toString());
    }
}
