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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Guest access token mongo db document.
 */
@Document(collection = "guestTokens")
public class GuestAccessTokenMongoDbDocument {

    @Id
    private String id;

    @Field("accreditationId")
    private String accreditationId;

    /**
     * The Expiring.
     */
    @Field("expiring")
    ZonedDateTime expiring;

    /**
     * Instantiates a new Guest access token mongo db document.
     */
    public GuestAccessTokenMongoDbDocument() {
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
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
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
     * Sets accreditation id.
     *
     * @param accreditationId the accreditation id
     */
    public void setAccreditationId(String accreditationId) {
        this.accreditationId = accreditationId;
    }

    /**
     * Gets expiring.
     *
     * @return the expiring
     */
    public ZonedDateTime getExpiring() {
        return expiring;
    }

    /**
     * Sets expiring.
     *
     * @param expiring the expiring
     */
    public void setExpiring(ZonedDateTime expiring) {
        this.expiring = expiring;
    }
}
