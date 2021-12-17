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
 * The enum Guest accreditation status.
 */
public enum GuestAccreditationStatus implements AccreditationStatus {
    /**
     * Open guest accreditation status.
     */
    OPEN(DefaultAccreditationStatus.OPEN),
    /**
     * Pending guest accreditation status.
     */
    PENDING(DefaultAccreditationStatus.PENDING),
    /**
     * Accepted guest accreditation status.
     */
    ACCEPTED(DefaultAccreditationStatus.ACCEPTED),
    /**
     * Cancelled guest accreditation status.
     */
    CANCELLED(DefaultAccreditationStatus.CANCELLED),
    /**
     * Revoked guest accreditation status.
     */
    REVOKED(DefaultAccreditationStatus.REVOKED),
    /**
     * Basis id verification pending guest accreditation status.
     */
    BASIS_ID_VERIFICATION_PENDING("BASIS_ID_VERIFICATION_PENDING"),
    /**
     * Basis id valid guest accreditation status.
     */
    BASIS_ID_VALID("BASIS_ID_VALID"),
    /**
     * Basis id invalid guest accreditation status.
     */
    BASIS_ID_INVALID("BASIS_ID_INVALID"),
    /**
     * Check in guest accreditation status.
     */
    CHECK_IN("CHECK_IN"),
    /**
     * Check out guest accreditation status.
     */
    CHECK_OUT("CHECK_OUT");

    private final String name;

    GuestAccreditationStatus(DefaultAccreditationStatus defaultAccreditationStatus) {
        this.name = defaultAccreditationStatus.getName();
    }

    GuestAccreditationStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
