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

package com.bka.ssi.controller.accreditation.company.domain.exceptions;

/**
 * The type Invalid validity timeframe exception.
 */
public class InvalidValidityTimeframeException extends Exception {

    /**
     * Instantiates a new Invalid validity timeframe exception.
     */
    public InvalidValidityTimeframeException() {
        super();
    }

    /**
     * Instantiates a new Invalid validity timeframe exception.
     *
     * @param message the message
     */
    public InvalidValidityTimeframeException(String message) {
        super(message);
    }
}
