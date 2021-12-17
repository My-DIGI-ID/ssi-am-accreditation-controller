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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

/**
 * The type Credential metadata mongo db value.
 */
public class CredentialMetadataMongoDbValue {

    @Field("issuedBy")
    private String issuedBy;

    @Field("issuedAt")
    private ZonedDateTime issuedAt;

    @Field("partyPersonalDataDeleted")
    private ZonedDateTime partyPersonalDataDeleted;

    @Field("credentialType")
    private String credentialType;

    /**
     * Instantiates a new Credential metadata mongo db value.
     */
    public CredentialMetadataMongoDbValue() {
    }

    /**
     * Gets issued by.
     *
     * @return the issued by
     */
    public String getIssuedBy() {
        return issuedBy;
    }

    /**
     * Sets issued by.
     *
     * @param issuedBy the issued by
     */
    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    /**
     * Gets issued at.
     *
     * @return the issued at
     */
    public ZonedDateTime getIssuedAt() {
        return issuedAt;
    }

    /**
     * Sets issued at.
     *
     * @param issuedAt the issued at
     */
    public void setIssuedAt(ZonedDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    /**
     * Gets party personal data deleted.
     *
     * @return the party personal data deleted
     */
    public ZonedDateTime getPartyPersonalDataDeleted() {
        return partyPersonalDataDeleted;
    }

    /**
     * Sets party personal data deleted.
     *
     * @param partyPersonalDataDeleted the party personal data deleted
     */
    public void setPartyPersonalDataDeleted(ZonedDateTime partyPersonalDataDeleted) {
        this.partyPersonalDataDeleted = partyPersonalDataDeleted;
    }

    /**
     * Gets credential type.
     *
     * @return the credential type
     */
    public String getCredentialType() {
        return credentialType;
    }

    /**
     * Sets credential type.
     *
     * @param credentialType the credential type
     */
    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }
}
