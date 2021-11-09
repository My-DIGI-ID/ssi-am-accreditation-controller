package com.bka.ssi.controller.accreditation.company.domain.entities.accreditations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidAccreditationInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class GuestAccreditationTest {

    private static final String URL = "url";
    private static final String QRCODE = "qrcode";
    private static final String EMAIL = "email";
    private static final String INVITEE = "unittest";
    private static final String NOTINVITEE = "anothertest";
    private static final String BIRTHDATE = "1970-01-01";
    private ZonedDateTime invitedAt;
    private Guest party;

    @BeforeEach
    void init() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        GuestBuilder builder = new GuestBuilder();
        builder.invitedBy = INVITEE;
        builder.createdBy = INVITEE;
        try {
            this.party = builder.buildGuest();
        } catch (InvalidValidityTimeframeException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldCreateAccreditationWithoutId() {
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        assertNotEquals(null, guestAccreditation);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfSpecificationUnsatisfied() {
        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // When
            GuestAccreditation guestAccreditation = new GuestAccreditation("id", party,
                GuestAccreditationStatus.OPEN, NOTINVITEE, invitedAt);
        });

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // When
            GuestAccreditation guestAccreditation = new GuestAccreditation(party,
                GuestAccreditationStatus.OPEN, NOTINVITEE, invitedAt);
        });
    }

    @Test
    public void shouldCreateAccreditationWithId() {
        GuestAccreditation guestAccreditation = new GuestAccreditation("id", party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        assertNotEquals(null, guestAccreditation);
    }

    @Test
    public void shouldCreateAccreditationWithIdIUrlEmailQrCorrelations() {
        Correlation basisIdCorrelation = new Correlation();
        Correlation guestIssuanceCorrelation = new Correlation();
        GuestAccreditation guestAccreditation = new GuestAccreditation("id", party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt, URL, EMAIL, QRCODE,
            basisIdCorrelation, guestIssuanceCorrelation);

        assertNotEquals(null, guestAccreditation);
    }

    @Test
    public void shouldInitiateAccreditationWithUrlAndEmail()
        throws InvalidAccreditationInitialStateException {
        // Given
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        // When
        guestAccreditation.initiateAccreditationWithInvitationUrlAndInvitationEmail(URL, EMAIL);

        // Then
        assertEquals(EMAIL, guestAccreditation.getInvitationEmail());
        assertEquals(URL, guestAccreditation.getInvitationUrl());

    }

    @Test
    @Disabled // not testable, can't create accr. with party 0 due to check for invitedby
    public void shouldThrowInvalidAccredittionStateExceptionWhenPartyNull()
        throws InvalidAccreditationInitialStateException {
        // Given
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        // Then
        assertThrows(InvalidAccreditationInitialStateException.class, () -> {
            // When
            guestAccreditation
                .initiateAccreditationWithInvitationUrlAndInvitationEmail(URL, EMAIL);
        });

    }


    @Test
    public void shouldUpdateStatusOnStartBasisVerification() {
        // Given accreditation with status OPEN
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);
        Correlation correlation = new Correlation();

        // when started basis id verification

        guestAccreditation.startBasisIdVerification(correlation);

        // then
        assertEquals(GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING,
            guestAccreditation.getStatus());
        assertEquals(guestAccreditation.getBasisIdVerificationCorrelation(), correlation);
    }

    @Test
    public void shouldUpdateStatusOnDeferDueToInvalidBasisId() {
        // Given accreditation with status OPEN
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        // when deferred credential
        guestAccreditation.deferAccreditationDueToInvalidBasisId();

        // then
        assertEquals(GuestAccreditationStatus.BASIS_ID_INVALID, guestAccreditation.getStatus());
    }

    @Test
    public void shouldAddPrivateInformation() {
        // Given accreditation with status OPEN
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);
        String licencePlateNumber = "license";
        String companyStreet = "street";
        String companyCity = "city";
        String companyPostCode = "postcode";
        String acceptedDocument = "document";
        String primaryPhoneNumber = "primePhone";
        String secondaryPhoneNumber = "secondPhone";


        // when adding private info
        this.party.getCredentialOffer().getCredential()
            .addGuestBirthDateOnBasisIdVerification(BIRTHDATE);

        guestAccreditation.addPrivateInformationByTheGuest(
            licencePlateNumber,
            companyStreet,
            companyCity,
            companyPostCode,
            acceptedDocument,
            primaryPhoneNumber,
            secondaryPhoneNumber);

        // then
        List<String> expectedPhoneNumbers = Arrays.asList("0123456789", "9876543210",
            "primePhone", "secondPhone");

        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
            .getContactInformation().getPhoneNumbers(), expectedPhoneNumbers);

        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
            .getGuestPrivateInformation().getLicencePlateNumber(), licencePlateNumber);

        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
            .getGuestPrivateInformation().getCompanyCity(), companyCity);

        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
            .getGuestPrivateInformation().getCompanyPostCode(), companyPostCode);

        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
            .getGuestPrivateInformation().getCompanyStreet(), companyStreet);
    }

    @Test
    public void shouldAddCorrelation() {
    }

    @Test
    public void shouldAssociateQrCodeWithAccreditation() {
        // Given accreditation with status OPEN
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        // when
        guestAccreditation.associateInvitationQrCodeWithAccreditation(QRCODE);

        // then
        assertEquals(QRCODE, guestAccreditation.getInvitationQrCode());
    }

    @Test
    public void shouldUpdateStatusAndPartyOnAccreditationCompletion() {
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);
        Correlation correlation = new Correlation("connectionId");

        // When
        guestAccreditation.completeAccreditation(correlation, BIRTHDATE);

        // Then
        assertEquals(GuestAccreditationStatus.ACCEPTED, guestAccreditation.getStatus());
        assertEquals(BIRTHDATE,
            guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getDateOfBirth());

        assertEquals(correlation, guestAccreditation.getGuestCredentialIssuanceCorrelation());
    }

    @Test
    public void shouldUpdateStatusSetCorrelationAndDateOfBirthOnCompletingBasisIdVerification() {
        // Given accreditation with status OPEN
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        // When
        guestAccreditation
            .completeBasisIdVerification(new Correlation("connectionId"), BIRTHDATE);

        // Then
        assertEquals(GuestAccreditationStatus.BASIS_ID_VALID, guestAccreditation.getStatus());
        assertEquals(BIRTHDATE,
            guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getDateOfBirth());

        String expectedReferenceId =
            guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getFirstName() + "_" +
                guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                    .getLastName() + "_" + BIRTHDATE;

        assertEquals(expectedReferenceId,
            guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getReferenceBasisId());
    }

    @Test
    public void shouldUpdateOnRevocation() {
        // Given accreditation with status OPEN
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        // when revoked credential
        guestAccreditation.revokeAccreditation();

        // then
        assertEquals(GuestAccreditationStatus.REVOKED, guestAccreditation.getStatus());
    }

    @Test
    public void shouldDeleteEmailOnCleanUp() {
        // Given accreditation with status OPEN
        GuestAccreditation guestAccreditation = new GuestAccreditation(party,
            GuestAccreditationStatus.OPEN, INVITEE, invitedAt);

        // When
        guestAccreditation.cleanGuestInformationOnCheckout();

        // Then
        assertEquals("deleted", guestAccreditation.getInvitationEmail());
        assertEquals("deleted",
            guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getFirstName());
        assertEquals("deleted",
            guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getDateOfBirth());
    }
}
