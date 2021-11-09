package com.bka.ssi.controller.accreditation.company.api.common.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class LogOutputTest {

    @Test
    public void shouldCreateLogOutputFromRestErrorResponse() {
        // Given RestErrorResponse
        RestErrorResponse response = new RestErrorResponse(HttpStatus.I_AM_A_TEAPOT, "error",
            "failed/path/");
        
        // when converting to logoutput
        String output = new LogOutput(response).toString();

        // then
        assertEquals("status: 418; message: error; path: failed/path/;", output);
    }
}