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

package com.bka.ssi.controller.accreditation.company.domain.enums;

/**
 * The enum Default accreditation status.
 */
public enum DefaultAccreditationStatus implements AccreditationStatus {
    /**
     * Open default accreditation status.
     */
    OPEN("OPEN"),
    /**
     * Pending default accreditation status.
     */
    PENDING("PENDING"),
    /**
     * Accepted default accreditation status.
     */
    ACCEPTED("ACCEPTED"),
    /**
     * Cancelled default accreditation status.
     */
    CANCELLED("CANCELLED"),
    /**
     * Revoked default accreditation status.
     */
    REVOKED("REVOKED");

    private final String name;

    DefaultAccreditationStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
