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

package com.bka.ssi.controller.accreditation.company.domain.entities.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidAccreditationInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations.AccreditationInitialStateSpecification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations.GuestAccreditationCreatedByAndInvitedBySpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;

import java.time.ZonedDateTime;

/**
 * The type Guest accreditation.
 */
public class GuestAccreditation extends Accreditation<Guest, GuestAccreditationStatus> {

    private Correlation basisIdVerificationCorrelation;
    private Correlation guestCredentialIssuanceCorrelation;

    /*
        ToDo - Have initiateAccreditation, proceedWithAccreditation in accreditation abstract
          class and use method overloading to cover guest/employee requirements
     */

    /**
     * Instantiates a new Guest accreditation.
     *
     * @param party     the party
     * @param status    the status
     * @param invitedBy the invited by
     * @param invitedAt the invited at
     */
    public GuestAccreditation(Guest party, GuestAccreditationStatus status, String invitedBy,
        ZonedDateTime invitedAt) {
        super(party, status, invitedBy, invitedAt);

        if (!new GuestAccreditationCreatedByAndInvitedBySpecification()
            .isSatisfiedBy(this)) {
            throw new IllegalArgumentException(
                "createdBy != invitedBy for guest accreditation with id " + this.getId());
        }
    }

    /**
     * Instantiates a new Guest accreditation.
     *
     * @param id        the id
     * @param party     the party
     * @param status    the status
     * @param invitedBy the invited by
     * @param invitedAt the invited at
     */
    public GuestAccreditation(String id, Guest party, GuestAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt) {
        super(id, party, status, invitedBy, invitedAt);

        if (!new GuestAccreditationCreatedByAndInvitedBySpecification()
            .isSatisfiedBy(this)) {
            throw new IllegalArgumentException(
                "createdBy != invitedBy for guest accreditation with id " + this.getId());
        }
    }

    /**
     * Instantiates a new Guest accreditation.
     *
     * @param id                                 the id
     * @param party                              the party
     * @param status                             the status
     * @param invitedBy                          the invited by
     * @param invitedAt                          the invited at
     * @param invitationUrl                      the invitation url
     * @param invitationEmail                    the invitation email
     * @param invitationQrCode                   the invitation qr code
     * @param basisIdVerificationCorrelation     the basis id verification correlation
     * @param guestCredentialIssuanceCorrelation the guest credential issuance correlation
     */
    public GuestAccreditation(String id, Guest party, GuestAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt, String invitationUrl,
        String invitationEmail, String invitationQrCode,
        Correlation basisIdVerificationCorrelation,
        Correlation guestCredentialIssuanceCorrelation) {
        super(id, party, status, invitedBy, invitedAt, invitationUrl, invitationEmail,
            invitationQrCode);
        this.basisIdVerificationCorrelation = basisIdVerificationCorrelation;
        this.guestCredentialIssuanceCorrelation = guestCredentialIssuanceCorrelation;

        if (!new GuestAccreditationCreatedByAndInvitedBySpecification()
            .isSatisfiedBy(this)) {
            throw new IllegalArgumentException(
                "createdBy != invitedBy for guest accreditation with id " + this.getId());
        }
    }

    /**
     * Initiate accreditation with invitation url and invitation email guest accreditation.
     *
     * @param invitationUrl   the invitation url
     * @param invitationEmail the invitation email
     * @return the guest accreditation
     * @throws InvalidAccreditationInitialStateException the invalid accreditation initial state
     * exception
     */
    public GuestAccreditation initiateAccreditationWithInvitationUrlAndInvitationEmail(
        String invitationUrl, String invitationEmail)
        throws InvalidAccreditationInitialStateException {
        if (!new AccreditationInitialStateSpecification().isSatisfiedBy(this)) {
            throw new InvalidAccreditationInitialStateException();
        }

        this.invitationEmail = invitationEmail;
        this.invitationUrl = invitationUrl;

        return this;
    }

    /**
     * Associate invitation qr code with accreditation guest accreditation.
     *
     * @param invitationQrCode the invitation qr code
     * @return the guest accreditation
     */
    public GuestAccreditation associateInvitationQrCodeWithAccreditation(String
        invitationQrCode) {
        this.invitationQrCode = invitationQrCode;

        return this;
    }

    /**
     * Start basis id verification guest accreditation.
     *
     * @param correlation the correlation
     * @return the guest accreditation
     */
    public GuestAccreditation startBasisIdVerification(Correlation correlation) {
        this.status = GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING;
        this.basisIdVerificationCorrelation = correlation;

        return this;
    }

    /**
     * Complete basis id verification guest accreditation.
     *
     * @param correlation the correlation
     * @param dateOfBirth the date of birth
     * @return the guest accreditation
     */
    public GuestAccreditation completeBasisIdVerification(Correlation correlation,
        String dateOfBirth) {
        this.status = GuestAccreditationStatus.BASIS_ID_VALID;
        this.basisIdVerificationCorrelation = correlation;
        this.getParty().getCredentialOffer().getCredential()
            .addGuestBirthDateOnBasisIdVerification(dateOfBirth);
        this.getParty().getCredentialOffer().getCredential().addBasisIdUniqueIdentifier();

        return this;
    }

    /**
     * Defer accreditation due to invalid basis id guest accreditation.
     *
     * @return the guest accreditation
     */
    public GuestAccreditation deferAccreditationDueToInvalidBasisId() {
        this.status = GuestAccreditationStatus.BASIS_ID_INVALID;

        return this;
    }

    /**
     * Add private information by the guest guest accreditation.
     *
     * @param licencePlateNumber   the licence plate number
     * @param companyStreet        the company street
     * @param companyCity          the company city
     * @param companyPostCode      the company post code
     * @param acceptedDocument     the accepted document
     * @param primaryPhoneNumber   the primary phone number
     * @param secondaryPhoneNumber the secondary phone number
     * @return the guest accreditation
     */
    public GuestAccreditation addPrivateInformationByTheGuest(
        String licencePlateNumber,
        String companyStreet,
        String companyCity,
        String companyPostCode,
        String acceptedDocument,
        String primaryPhoneNumber,
        String secondaryPhoneNumber
    ) {
        String dateOfBirth =
            this.getParty().getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getDateOfBirth();

        GuestPrivateInformation privateInformation = new GuestPrivateInformation(
            dateOfBirth, licencePlateNumber, companyStreet, companyCity, companyPostCode,
            acceptedDocument
        );

        this.getParty().getCredentialOffer().getCredential()
            .addGuestPrivateInformationToCredential(privateInformation, primaryPhoneNumber,
                secondaryPhoneNumber);

        return this;
    }

    /**
     * Offer accreditation guest accreditation.
     *
     * @param correlation the correlation
     * @return the guest accreditation
     */
    public GuestAccreditation offerAccreditation(Correlation correlation) {
        this.guestCredentialIssuanceCorrelation = correlation;
        this.status = GuestAccreditationStatus.PENDING;

        return this;
    }

    /**
     * Complete accreditation guest accreditation.
     *
     * @param correlation the correlation
     * @param issuedBy    the issued by
     * @return the guest accreditation
     */
    public GuestAccreditation completeAccreditation(Correlation correlation, String issuedBy) {
        this.guestCredentialIssuanceCorrelation = correlation;
        this.status = GuestAccreditationStatus.ACCEPTED;
        this.getParty().addIssuedByAndIssuedAtToCredentialMetadata(issuedBy);

        return this;
    }

    /**
     * Clean guest information on checkout guest accreditation.
     *
     * @return the guest accreditation
     */
    public GuestAccreditation cleanGuestInformationOnCheckout() {
        this.invitationEmail = "deleted";
        this.getParty().removeCredentialFromCredentialOffer();

        return this;
    }

    /**
     * Revoke accreditation guest accreditation.
     *
     * @return the guest accreditation
     */
    public GuestAccreditation revokeAccreditation() {
        this.status = GuestAccreditationStatus.REVOKED;
        this.invitationEmail = "deleted";
        this.getParty().removeCredentialFromCredentialOffer();

        return this;
    }

    /**
     * Gets basis id verification correlation.
     *
     * @return the basis id verification correlation
     */
    public Correlation getBasisIdVerificationCorrelation() {
        return basisIdVerificationCorrelation;
    }

    /**
     * Gets guest credential issuance correlation.
     *
     * @return the guest credential issuance correlation
     */
    public Correlation getGuestCredentialIssuanceCorrelation() {
        return guestCredentialIssuanceCorrelation;
    }

    /**
     * Check in accreditation guest accreditation.
     *
     * @return the guest accreditation
     */
    public GuestAccreditation checkInAccreditation() {
        this.status = GuestAccreditationStatus.CHECK_IN;
        return this;
    }

    /**
     * Check out accreditation guest accreditation.
     *
     * @return the guest accreditation
     */
    public GuestAccreditation checkOutAccreditation() {
        this.status = GuestAccreditationStatus.CHECK_OUT;
        return this;
    }
}
