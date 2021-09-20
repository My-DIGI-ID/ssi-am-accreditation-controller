package com.bka.ssi.controller.accreditation.company.api.common.exceptions.response;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class RestErrorResponse {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public RestErrorResponse(HttpStatus httpStatus, String message, String path) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now().toString();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
