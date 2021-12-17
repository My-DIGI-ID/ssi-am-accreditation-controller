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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.abstractions.CredentialMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialOfferMongoDbValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

/**
 * The type Party mongo db document.
 *
 * @param <T> the type parameter
 */
public abstract class PartyMongoDbDocument<T extends CredentialMongoDbValue> {

    @Id
    private String id;

    @Field("credentialOffer")
    private CredentialOfferMongoDbValue<T> credentialOffer;

    @Field("createdBy")
    private String createdBy;

    @Field("createdAt")
    private ZonedDateTime createdAt;

    /**
     * Instantiates a new Party mongo db document.
     */
    public PartyMongoDbDocument() {
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
     * Gets credential offer.
     *
     * @return the credential offer
     */
    public CredentialOfferMongoDbValue<T> getCredentialOffer() {
        return credentialOffer;
    }

    /**
     * Sets credential offer.
     *
     * @param credentialOffer the credential offer
     */
    public void setCredentialOffer(
        CredentialOfferMongoDbValue<T> credentialOffer) {
        this.credentialOffer = credentialOffer;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
