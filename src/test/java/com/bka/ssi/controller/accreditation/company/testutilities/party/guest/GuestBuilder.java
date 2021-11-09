package com.bka.ssi.controller.accreditation.company.testutilities.party.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.ValidTimeframeBuilder;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;

public class GuestBuilder {

    public String id;

    // Persona
    public String personaTitle;
    public String personaFirstName;
    public String personaLastName;

    public GuestPrivateInformation guestPrivateInformation;

    // ContactInformation
    public String contactInformationEmail;
    public String contactInformationPrimaryPhoneNumber;
    public String contactInformationSecondaryPhoneNumber;

    public String companyName;
    public String typeOfVisit;
    public String location;
    public String referenceBasisId;

    public String invitedBy;

    public String createdBy;
    public ZonedDateTime createdAt;

    // CredentialMetadata
    public String issuedBy;
    public ZonedDateTime issuedAt;
    public ZonedDateTime credentialMetadataPersonalDataDeleted;

    public GuestBuilder() {
    }

    public Guest build() throws InvalidValidityTimeframeException {
        Persona persona = new Persona(this.personaTitle, this.personaFirstName,
            this.personaLastName);

        ValidTimeframeBuilder validTimeframeBuilder = new ValidTimeframeBuilder();

        ValidityTimeframe validityTimeframe = validTimeframeBuilder.build();

        ContactInformation contactInformation =
            new ContactInformation(Arrays.asList(this.contactInformationEmail),
                Arrays.asList(this.contactInformationPrimaryPhoneNumber,
                    this.contactInformationSecondaryPhoneNumber));

        GuestCredential guestCredential = new GuestCredential(validityTimeframe,
            persona, contactInformation,
            companyName, typeOfVisit, location, invitedBy, referenceBasisId,
            guestPrivateInformation);

        CredentialMetadata credentialMetadata = new CredentialMetadata(this.issuedBy, this.issuedAt,
            this.credentialMetadataPersonalDataDeleted, CredentialType.EMPLOYEE);

        CredentialOffer<GuestCredential> credentialOffer =
            new CredentialOffer<>(credentialMetadata, guestCredential);
        return new Guest(id, credentialOffer, createdBy, createdAt);
    }

    public void reset() {
        this.id = "";


        // Persona
        this.personaTitle = "";
        this.personaFirstName = "";
        this.personaLastName = "";

        this.guestPrivateInformation = null;

        // ContactInformation
        this.contactInformationEmail = "";
        this.contactInformationPrimaryPhoneNumber = "";
        this.contactInformationSecondaryPhoneNumber = "";

        this.companyName = "";
        this.typeOfVisit = "";
        this.location = "";
        this.referenceBasisId = "";

        this.invitedBy = "";

        this.createdBy = "";
        this.createdAt = null;

        // CredentialMetadata
        this.issuedBy = "";
        this.issuedAt = null;
        this.credentialMetadataPersonalDataDeleted = null;
    }

    public Guest buildGuest() throws InvalidValidityTimeframeException {

        this.id = "123456789";

        // ValidityTimeFrame
        ValidityTimeframe validityTimeframe = new ValidTimeframeBuilder().build();

        // Persona
        this.personaTitle = "Mrs.";
        this.personaFirstName = "Erika";
        this.personaLastName = "Mustermann";

        guestPrivateInformation = new GuestPrivateInformation(
            "1970-01-01",
            "licencePlateNumber",
            "companyStreet",
            "companyCity",
            "companyPostCode",
            "acceptedDocument");

        // ContactInformation
        this.contactInformationEmail = "mustermann@test.tld";
        this.contactInformationPrimaryPhoneNumber = "0123456789";
        this.contactInformationSecondaryPhoneNumber = "9876543210";

        this.companyName = "Test Company";
        this.typeOfVisit = "Test Visit";
        this.location = "Test location";
        this.referenceBasisId = "Reference";


        this.createdAt = ZonedDateTime.now();

        // CredentialMetadata
        this.issuedAt = ZonedDateTime.now();
        this.credentialMetadataPersonalDataDeleted = ZonedDateTime.now();

        return this.build();
    }

    @Test
    private void buildGuestTest() throws InvalidValidityTimeframeException {
        Guest guestDto = this.buildGuest();

        // id
        assertEquals(this.id, guestDto.getId());

        // Persona
        assertEquals(this.personaTitle,
            guestDto.getCredentialOffer().getCredential().getPersona().getTitle());
        assertEquals(this.personaFirstName,
            guestDto.getCredentialOffer().getCredential().getPersona().getFirstName());
        assertEquals(this.personaLastName,
            guestDto.getCredentialOffer().getCredential().getPersona().getLastName());

        // Private information
        assertEquals(this.guestPrivateInformation.getDateOfBirth(),
            guestDto.getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getDateOfBirth());
        assertEquals(this.guestPrivateInformation.getLicencePlateNumber(),
            guestDto.getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getLicencePlateNumber());
        assertEquals(this.guestPrivateInformation.getCompanyStreet(),
            guestDto.getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getCompanyStreet());
        assertEquals(this.guestPrivateInformation.getCompanyCity(),
            guestDto.getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getCompanyCity());
        assertEquals(this.guestPrivateInformation.getCompanyPostCode(),
            guestDto.getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getCompanyPostCode());
        assertEquals(this.guestPrivateInformation.getAcceptedDocument(),
            guestDto.getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getAcceptedDocument());

        // ContactInformation
        assertTrue(guestDto.getCredentialOffer().getCredential().getContactInformation().getEmails()
            .contains(this.contactInformationEmail));
        assertTrue(
            guestDto.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .contains(this.contactInformationPrimaryPhoneNumber));
        assertTrue(
            guestDto.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .contains(this.contactInformationSecondaryPhoneNumber));

        assertEquals(this.companyName,
            guestDto.getCredentialOffer().getCredential().getCompanyName());
        assertEquals(this.typeOfVisit,
            guestDto.getCredentialOffer().getCredential().getTypeOfVisit());
        assertEquals(this.location, guestDto.getCredentialOffer().getCredential().getLocation());

        assertEquals(this.referenceBasisId,
            guestDto.getCredentialOffer().getCredential().getReferenceBasisId());
        assertEquals(this.invitedBy, guestDto.getCredentialOffer().getCredential().getInvitedBy());

        assertEquals(this.createdBy, guestDto.getCreatedBy());
        assertEquals(this.createdAt, guestDto.getCreatedAt());
        assertEquals(this.credentialMetadataPersonalDataDeleted,
            guestDto.getCredentialOffer().getCredentialMetadata().getPartyPersonalDataDeleted());

        this.reset();
    }
}
