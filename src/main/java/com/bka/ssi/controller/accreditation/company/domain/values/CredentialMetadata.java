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

package com.bka.ssi.controller.accreditation.company.domain.values;

import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;

import java.time.ZonedDateTime;

/**
 * The type Credential metadata.
 */
/* ToDo - needs refactoring with respect to used properties */
public class CredentialMetadata {

    private String issuedBy;
    private ZonedDateTime issuedAt;
    private ZonedDateTime partyPersonalDataDeleted;
    private CredentialType credentialType;

    /**
     * Instantiates a new Credential metadata.
     *
     * @param issuedBy                 the issued by
     * @param issuedAt                 the issued at
     * @param partyPersonalDataDeleted the party personal data deleted
     * @param credentialType           the credential type
     */
    public CredentialMetadata(String issuedBy, ZonedDateTime issuedAt,
        ZonedDateTime partyPersonalDataDeleted,
        CredentialType credentialType) {
        this.issuedBy = issuedBy;
        this.issuedAt = issuedAt;
        this.partyPersonalDataDeleted = partyPersonalDataDeleted;
        this.credentialType = credentialType;
    }

    /**
     * Instantiates a new Credential metadata.
     *
     * @param issuedBy       the issued by
     * @param issuedAt       the issued at
     * @param credentialType the credential type
     */
    public CredentialMetadata(String issuedBy, ZonedDateTime issuedAt,
        CredentialType credentialType) {
        this.issuedBy = issuedBy;
        this.issuedAt = issuedAt;
        this.credentialType = credentialType;
    }

    /**
     * Instantiates a new Credential metadata.
     *
     * @param issuedBy       the issued by
     * @param credentialType the credential type
     */
    public CredentialMetadata(String issuedBy, CredentialType credentialType) {
        this.issuedBy = issuedBy;
        this.credentialType = credentialType;
    }

    /**
     * Instantiates a new Credential metadata.
     *
     * @param credentialType the credential type
     */
    public CredentialMetadata(CredentialType credentialType) {
        this.credentialType = credentialType;
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
     * Gets issued at.
     *
     * @return the issued at
     */
    public ZonedDateTime getIssuedAt() {
        return issuedAt;
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
     * Gets credential type.
     *
     * @return the credential type
     */
    public CredentialType getCredentialType() {
        return credentialType;
    }
}
