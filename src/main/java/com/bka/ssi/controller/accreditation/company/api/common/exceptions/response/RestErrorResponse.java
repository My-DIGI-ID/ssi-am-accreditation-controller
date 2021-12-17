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

import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * The type Rest error response.
 */
public class RestErrorResponse {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    /**
     * Instantiates a new Rest error response.
     *
     * @param httpStatus the http status
     * @param message    the message
     * @param path       the path
     */
    public RestErrorResponse(HttpStatus httpStatus, String message, String path) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now().toString();
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "status: " + this.status + "; message: " + this.message
            + "; path: " + this.path + ";";
    }
}
