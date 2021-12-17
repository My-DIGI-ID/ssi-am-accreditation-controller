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

package com.bka.ssi.controller.accreditation.company.domain.entities.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidGuestInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidGuestValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.specifications.parties.GuestInitialStateSpecification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.parties.GuestValidityTimeframeSpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

import java.time.ZonedDateTime;

/**
 * The type Guest.
 */
public class Guest extends Party<GuestCredential> {

    /**
     * Instantiates a new Guest.
     *
     * @param id              the id
     * @param credentialOffer the credential offer
     * @param createdBy       the created by
     * @param createAt        the create at
     * @throws InvalidValidityTimeframeException the invalid validity timeframe exception
     */
    public Guest(String id, CredentialOffer<GuestCredential> credentialOffer, String createdBy,
        ZonedDateTime createAt) throws InvalidValidityTimeframeException {
        super(id, credentialOffer, createdBy, createAt);

        if (!new GuestValidityTimeframeSpecification().isSatisfiedBy(this)) {
            throw new InvalidValidityTimeframeException(
                "Invalid ValidityTimeframe for guest with id " + this.getId());
        }
    }

    /**
     * Instantiates a new Guest.
     *
     * @param credentialOffer the credential offer
     * @param createdBy       the created by
     * @param createAt        the create at
     * @throws InvalidGuestValidityTimeframeException the invalid guest validity timeframe exception
     */
    public Guest(CredentialOffer<GuestCredential> credentialOffer, String createdBy,
        ZonedDateTime createAt) throws InvalidGuestValidityTimeframeException {
        super(credentialOffer, createdBy, createAt);

        if (!new GuestValidityTimeframeSpecification().isSatisfiedBy(this)) {
            throw new InvalidGuestValidityTimeframeException(
                "Invalid ValidityTimeframe for guest with id " + this.getId());
        }
    }

    @Override
    public Guest removeCredentialFromCredentialOffer() {
        CredentialMetadata newCredentialMetadata = new CredentialMetadata(
            this.credentialOffer.getCredentialMetadata().getIssuedBy(),
            this.credentialOffer.getCredentialMetadata().getIssuedAt(),
            ZonedDateTime.now(),
            CredentialType.GUEST
        );

        GuestCredential cleanedCredential = (GuestCredential)
            this.credentialOffer.getCredential().createEmptyCredentialForDataCleanup();

        this.credentialOffer =
            new CredentialOffer<>(newCredentialMetadata, cleanedCredential);

        return this;
    }

    /**
     * Add information about inviting person guest.
     *
     * @param invitedBy the invited by
     * @return the guest
     * @throws InvalidGuestInitialStateException the invalid guest initial state exception
     */
    public Guest addInformationAboutInvitingPerson(String invitedBy)
        throws InvalidGuestInitialStateException {
        if (!new GuestInitialStateSpecification().isSatisfiedBy(this)) {
            throw new InvalidGuestInitialStateException();
        }

        this.getCredentialOffer().getCredential()
            .addInvitingPersonInformationToCredential(invitedBy);

        return this;
    }

    /**
     * Add issued by and issued at to credential metadata guest.
     *
     * @param issuedBy the issued by
     * @return the guest
     */
    public Guest addIssuedByAndIssuedAtToCredentialMetadata(String issuedBy) {
        CredentialMetadata credentialMetadata = new CredentialMetadata(
            issuedBy,
            ZonedDateTime.now(),
            CredentialType.GUEST
        );

        this.credentialOffer =
            new CredentialOffer<>(credentialMetadata, this.credentialOffer.getCredential());

        return this;
    }
}
