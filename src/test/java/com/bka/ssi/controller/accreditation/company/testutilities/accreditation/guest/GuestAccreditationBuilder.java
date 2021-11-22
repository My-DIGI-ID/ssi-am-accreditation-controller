package com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class GuestAccreditationBuilder {

    private GuestBuilder builder;

    public String id;
    public Guest guest;
    public GuestAccreditationStatus status;
    public String invitedBy;
    public ZonedDateTime invitedAt;
    public String invitationUrl;
    public String invitationEmail;
    public String invitationQrCode;
    public Correlation basisIdVerificationCorrelation;
    public Correlation guestCredentialIssuanceCorrelation;

    public GuestAccreditationBuilder() throws InvalidValidityTimeframeException {
        this.builder = new GuestBuilder();
    }

    public GuestAccreditation build() {
        return new GuestAccreditation(this.id, this.guest, this.status, this.invitedBy,
            this.invitedAt, this.invitationUrl, this.invitationEmail, this.invitationQrCode,
            this.basisIdVerificationCorrelation, this.guestCredentialIssuanceCorrelation);
    }

    public void reset() {
        this.id = null;
        this.guest = null;
        this.status = null;
        this.invitedBy = null;
        this.invitedAt = null;
        this.invitationUrl = null;
        this.invitationEmail = null;
        this.invitationQrCode = null;
        this.basisIdVerificationCorrelation = null;
        this.guestCredentialIssuanceCorrelation = null;
    }

    public GuestAccreditation buildGuestAccreditation() throws InvalidValidityTimeframeException {
        this.id = this.id != null ? this.id : "id";
        this.guest = this.guest != null ? this.guest : this.builder.buildGuest();
        this.status = this.status != null ? this.status : GuestAccreditationStatus.OPEN;
        this.invitedBy = this.invitedBy != null ? this.invitedBy : "unittest";
        this.invitedAt = this.invitedAt != null ? this.invitedAt : ZonedDateTime.now();
        this.invitationUrl = this.invitationUrl != null ? this.invitationUrl : "url";
        this.invitationEmail = this.invitationEmail != null ? this.invitationEmail : "email";
        this.invitationQrCode = this.invitationQrCode != null ? this.invitationQrCode : "qrCode";
        this.basisIdVerificationCorrelation =
            this.basisIdVerificationCorrelation != null ?
                this.basisIdVerificationCorrelation : new Correlation(
                "basisConnectionId", "threadId",
                "presentationExchangeId");
        this.guestCredentialIssuanceCorrelation =
            this.guestCredentialIssuanceCorrelation != null ?
                this.guestCredentialIssuanceCorrelation : new Correlation(
                "guestConnectionId", "threadId",
                "presentationExchangeId");

        return this.build();
    }

    @Test
    private void buildGuestAccreditationTest() throws InvalidValidityTimeframeException {
        GuestAccreditation accreditation = this.buildGuestAccreditation();

        assertEquals(this.id, accreditation.getId());
        assertEquals(this.guest, accreditation.getParty());
        assertEquals(this.status, accreditation.getStatus());
        assertEquals(this.invitedBy, accreditation.getInvitedBy());
        assertEquals(this.invitedAt, accreditation.getInvitedAt());
        assertEquals(this.invitationUrl, accreditation.getInvitationUrl());
        assertEquals(this.invitationEmail, accreditation.getInvitationEmail());
        assertEquals(this.invitationQrCode, accreditation.getInvitationQrCode());
        assertEquals(this.basisIdVerificationCorrelation,
            accreditation.getBasisIdVerificationCorrelation());
        assertEquals(this.guestCredentialIssuanceCorrelation,
            accreditation.getGuestCredentialIssuanceCorrelation());

        this.reset();
    }
}
