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

package com.bka.ssi.controller.accreditation.company.domain.entities;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common.Entity;
import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.UpdatingPartyWithoutIdentityException;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

import java.time.ZonedDateTime;

/**
 * The type Party.
 *
 * @param <T> the type parameter
 */
public abstract class Party<T extends Credential> extends Entity {

    /**
     * The Credential offer.
     */
    protected CredentialOffer<T> credentialOffer;
    /**
     * The Created by.
     */
    protected String createdBy;
    /**
     * The Created at.
     */
    protected ZonedDateTime createdAt;

    /**
     * Remove credential from credential offer party.
     *
     * @return the party
     */
    abstract public Party<T> removeCredentialFromCredentialOffer();

    /**
     * Instantiates a new Party.
     *
     * @param id              the id
     * @param credentialOffer the credential offer
     * @param createdBy       the created by
     * @param createdAt       the created at
     */
    public Party(String id, CredentialOffer<T> credentialOffer, String createdBy,
        ZonedDateTime createdAt) {
        super(id);

        this.credentialOffer = credentialOffer;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    /**
     * Instantiates a new Party.
     *
     * @param credentialOffer the credential offer
     * @param createdBy       the created by
     * @param createdAt       the created at
     */
    public Party(CredentialOffer<T> credentialOffer, String createdBy, ZonedDateTime createdAt) {
        super(null);

        this.credentialOffer = credentialOffer;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    /**
     * Update with new party data party.
     *
     * @param newParty the new party
     * @return the party
     * @throws UpdatingPartyWithoutIdentityException the updating party without identity exception
     */
    public Party<T> updateWithNewPartyData(Party<T> newParty)
        throws UpdatingPartyWithoutIdentityException {
        if (this.id == null) {
            throw new UpdatingPartyWithoutIdentityException();
        }
        this.credentialOffer = newParty.credentialOffer;
        this.createdBy = newParty.createdBy;
        this.createdAt = newParty.createdAt;

        return this;
    }

    /**
     * Gets credential offer.
     *
     * @return the credential offer
     */
    public CredentialOffer<T> getCredentialOffer() {
        return credentialOffer;
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
     * Gets created at.
     *
     * @return the created at
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
