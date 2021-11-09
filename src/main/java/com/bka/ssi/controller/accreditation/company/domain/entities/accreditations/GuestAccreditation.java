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

public class GuestAccreditation extends Accreditation<Guest, GuestAccreditationStatus> {

    private Correlation basisIdVerificationCorrelation;
    private Correlation guestCredentialIssuanceCorrelation;

    /*
        ToDo - Have initiateAccreditation, proceedWithAccreditation in accreditation abstract
          class and use method overloading to cover guest/employee requirements
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

    public GuestAccreditation(String id, Guest party, GuestAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt) {
        super(id, party, status, invitedBy, invitedAt);

        if (!new GuestAccreditationCreatedByAndInvitedBySpecification()
            .isSatisfiedBy(this)) {
            throw new IllegalArgumentException(
                "createdBy != invitedBy for guest accreditation with id " + this.getId());
        }
    }

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

    public GuestAccreditation associateInvitationQrCodeWithAccreditation(String
        invitationQrCode) {
        this.invitationQrCode = invitationQrCode;

        return this;
    }

    public GuestAccreditation startBasisIdVerification(Correlation correlation) {
        this.status = GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING;
        this.basisIdVerificationCorrelation = correlation;

        return this;
    }

    public GuestAccreditation completeBasisIdVerification(Correlation correlation,
        String dateOfBirth) {
        this.status = GuestAccreditationStatus.BASIS_ID_VALID;
        this.basisIdVerificationCorrelation = correlation;
        this.getParty().getCredentialOffer().getCredential()
            .addGuestBirthDateOnBasisIdVerification(dateOfBirth);
        this.getParty().getCredentialOffer().getCredential().addBasisIdUniqueIdentifier();

        return this;
    }

    public GuestAccreditation deferAccreditationDueToInvalidBasisId() {
        this.status = GuestAccreditationStatus.BASIS_ID_INVALID;

        return this;
    }

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

    public GuestAccreditation offerAccreditation(Correlation correlation) {
        this.guestCredentialIssuanceCorrelation = correlation;
        this.status = GuestAccreditationStatus.PENDING;

        return this;
    }

    public GuestAccreditation completeAccreditation(Correlation correlation, String issuedBy) {
        this.guestCredentialIssuanceCorrelation = correlation;
        this.status = GuestAccreditationStatus.ACCEPTED;
        this.getParty().addIssuedByAndIssuedAtToCredentialMetadata(issuedBy);

        return this;
    }

    public GuestAccreditation cleanGuestInformationOnCheckout() {
        this.invitationEmail = "deleted";
        this.getParty().removeCredentialFromCredentialOffer();

        return this;
    }

    public GuestAccreditation revokeAccreditation() {
        this.status = GuestAccreditationStatus.REVOKED;

        return this;
    }

    public Correlation getBasisIdVerificationCorrelation() {
        return basisIdVerificationCorrelation;
    }

    public Correlation getGuestCredentialIssuanceCorrelation() {
        return guestCredentialIssuanceCorrelation;
    }
}
