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

package com.bka.ssi.controller.accreditation.company.application.security.authentication.dto;

import java.time.ZonedDateTime;

/**
 * The type Guest token.
 */
public class GuestToken {

    // ToDo - separate dto from domain and infra object even though they look the same

    /**
     * The Id.
     */
    String id;
    /**
     * The Accreditation id.
     */
    String accreditationId;
    /**
     * The Expiring.
     */
    ZonedDateTime expiring;

    /**
     * Instantiates a new Guest token.
     *
     * @param id              the id
     * @param accreditationId the accreditation id
     * @param expiring        the expiring
     */
    public GuestToken(String id, String accreditationId, ZonedDateTime expiring) {
        this.id = id;
        this.accreditationId = accreditationId;
        this.expiring = expiring;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets accreditation id.
     *
     * @return the accreditation id
     */
    public String getAccreditationId() {
        return accreditationId;
    }

    /**
     * Gets expiring.
     *
     * @return the expiring
     */
    public ZonedDateTime getExpiring() {
        return expiring;
    }
}
