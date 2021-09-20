package com.bka.ssi.controller.accreditation.company.domain.entities.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;

public class GuestAccreditation extends Accreditation<Guest, GuestAccreditationStatus> {

    private Correlation basisIdVerificationCorrelation;
    private Correlation guestCredentialIssuanceCorrelation;

    // reconsider - maybe this is Accreditation abstract class props
    private String invitationLink;
    private String invitationEmail;
    private String connectionQrCode;

    public GuestAccreditation(String id,
        Guest party,
        GuestAccreditationStatus status,
        Correlation basisIdVerification,
        Correlation guestCredentialIssuance,
        String invitationEmail,
        String invitationLink) {
        super(id, party, status);
        this.basisIdVerificationCorrelation = basisIdVerification;
        this.guestCredentialIssuanceCorrelation = guestCredentialIssuance;
        setInvitationLink(invitationLink);
        setInvitationEmail(invitationEmail);
    }

    public GuestAccreditation(String id,
        Guest party,
        GuestAccreditationStatus status) {
        super(id, party, status);
    }

    public GuestAccreditation(
        Guest party,
        GuestAccreditationStatus status) {
        super(party, status);
    }

    public GuestAccreditation(String id,
        Guest party,
        GuestAccreditationStatus status, String invitationLink, String invitationEmail) {
        super(id, party, status);
        this.invitationEmail = invitationEmail;
        this.invitationLink = invitationLink;
    }


    public GuestAccreditation initiateAccreditationWithInvitationLinkAndEmail(String invitationLink,
        String invitationEmail) {
        setInvitationLink(invitationLink);
        setInvitationEmail(invitationEmail);

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

    public GuestAccreditation associateConnectionQrCodeWithAccreditation(String connectionQrCode) {
        this.connectionQrCode = connectionQrCode;

        return this;
    }

    public GuestAccreditation cleanGuestInformationOnCheckout() {
        this.invitationEmail = "deleted";
        this.getParty().removeCredentialFromCredentialOffer();

        return this;
    }

    public Correlation getBasisIdVerificationCorrelation() {
        return basisIdVerificationCorrelation;
    }

    public void setBasisIdVerificationCorrelation(
        Correlation basisIdVerificationCorrelation) {
        this.basisIdVerificationCorrelation = basisIdVerificationCorrelation;
    }

    public Correlation getGuestCredentialIssuanceCorrelation() {
        return guestCredentialIssuanceCorrelation;
    }

    public void setGuestCredentialIssuanceCorrelation(
        Correlation guestCredentialIssuanceCorrelation) {
        this.guestCredentialIssuanceCorrelation = guestCredentialIssuanceCorrelation;
    }

    public String getInvitationLink() {
        return invitationLink;
    }

    public String getInvitationEmail() {
        return invitationEmail;
    }

    public void setInvitationLink(String invitationLink) {
        this.invitationLink = invitationLink;
    }

    public void setInvitationEmail(String invitationEmail) {
        this.invitationEmail = invitationEmail;
    }

    public String getConnectionQrCode() {
        return connectionQrCode;
    }
}
