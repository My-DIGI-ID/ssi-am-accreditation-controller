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

public class Guest extends Party<GuestCredential> {

    public Guest(String id, CredentialOffer<GuestCredential> credentialOffer, String createdBy,
        ZonedDateTime createAt) throws InvalidValidityTimeframeException {
        super(id, credentialOffer, createdBy, createAt);

        if (!new GuestValidityTimeframeSpecification().isSatisfiedBy(this)) {
            throw new InvalidValidityTimeframeException(
                "Invalid ValidityTimeframe for guest with id " + this.getId());
        }
    }

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

    public Guest addInformationAboutInvitingPerson(String invitedBy)
        throws InvalidGuestInitialStateException {
        if (!new GuestInitialStateSpecification().isSatisfiedBy(this)) {
            throw new InvalidGuestInitialStateException();
        }

        this.getCredentialOffer().getCredential()
            .addInvitingPersonInformationToCredential(invitedBy);

        return this;
    }

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
