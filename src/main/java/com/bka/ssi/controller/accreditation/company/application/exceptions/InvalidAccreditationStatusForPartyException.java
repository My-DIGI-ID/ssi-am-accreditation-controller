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

package com.bka.ssi.controller.accreditation.company.application.exceptions;

/**
 * The type Invalid accreditation status for party exception.
 */
public class InvalidAccreditationStatusForPartyException extends Exception {

    /**
     * Instantiates a new Invalid accreditation status for party exception.
     */
    public InvalidAccreditationStatusForPartyException() {
        super("Invalid accrediation status for party");
    }

    /**
     * Instantiates a new Invalid accreditation status for party exception.
     *
     * @param message the message
     */
    public InvalidAccreditationStatusForPartyException(String message) {
        super("Invalid accrediation status for party: " + message);
    }
}
