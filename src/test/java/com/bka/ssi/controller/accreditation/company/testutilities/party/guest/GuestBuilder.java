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
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.ValidityTimeframeBuilder;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;

public class GuestBuilder {

    private ValidityTimeframeBuilder builder;

    public String id;

    // Persona
    public String personaTitle;
    public String personaFirstName;
    public String personaLastName;

    public GuestPrivateInformation guestPrivateInformation;

    public ValidityTimeframe validityTimeframe;

    // ContactInformation
    public String contactInformationEmail;
    public String contactInformationPrimaryPhoneNumber;
    public String contactInformationSecondaryPhoneNumber;

    public String companyName;
    public String typeOfVisit;
    public String location;
    public String referenceBasisId;

    public String invitedBy;

    // Creator
    public String createdBy;
    public ZonedDateTime createdAt;

    // CredentialMetadata
    public String issuedBy;
    public ZonedDateTime issuedAt;
    public ZonedDateTime credentialMetadataPersonalDataDeleted;

    public GuestBuilder() {
        this.builder = new ValidityTimeframeBuilder();
    }

    public Guest build() throws InvalidValidityTimeframeException {
        Persona persona = new Persona(this.personaTitle, this.personaFirstName,
            this.personaLastName);

        ContactInformation contactInformation =
            new ContactInformation(Arrays.asList(this.contactInformationEmail),
                Arrays.asList(this.contactInformationPrimaryPhoneNumber,
                    this.contactInformationSecondaryPhoneNumber));

        GuestCredential guestCredential = new GuestCredential(this.validityTimeframe,
            persona, contactInformation, this.companyName, this.typeOfVisit, this.location,
            this.invitedBy, this.referenceBasisId, this.guestPrivateInformation);

        CredentialMetadata credentialMetadata = new CredentialMetadata(this.issuedBy, this.issuedAt,
            this.credentialMetadataPersonalDataDeleted, CredentialType.GUEST);

        CredentialOffer<GuestCredential> credentialOffer =
            new CredentialOffer<>(credentialMetadata, guestCredential);
        return new Guest(this.id, credentialOffer, this.createdBy, this.createdAt);
    }

    public void reset() {
        this.id = null;

        // Persona
        this.personaTitle = null;
        this.personaFirstName = null;
        this.personaLastName = null;

        this.guestPrivateInformation = null;

        this.validityTimeframe = null;

        // ContactInformation
        this.contactInformationEmail = null;
        this.contactInformationPrimaryPhoneNumber = null;
        this.contactInformationSecondaryPhoneNumber = null;

        this.companyName = null;
        this.typeOfVisit = null;
        this.location = null;
        this.referenceBasisId = null;

        this.invitedBy = null;

        this.createdBy = null;
        this.createdAt = null;

        // CredentialMetadata
        this.issuedBy = null;
        this.issuedAt = null;
        this.credentialMetadataPersonalDataDeleted = null;
    }

    public Guest buildGuest() throws InvalidValidityTimeframeException {
        this.id = this.id != null ? this.id : "123456789";

        // ValidityTimeFrame
        this.validityTimeframe =
            this.validityTimeframe != null ? this.validityTimeframe :
                this.builder.buildDynamicValidityTimeframe();

        // Persona
        this.personaTitle = this.personaTitle != null ? this.personaTitle : "Mrs.";
        this.personaFirstName = this.personaFirstName != null ? this.personaFirstName : "Erika";
        this.personaLastName = this.personaLastName != null ? this.personaLastName : "Mustermann";

        this.guestPrivateInformation =
            this.guestPrivateInformation != null ? this.guestPrivateInformation :
                new GuestPrivateInformation(
                    "1970-01-01",
                    "licencePlateNumber",
                    "companyStreet",
                    "companyCity",
                    "companyPostCode",
                    "acceptedDocument");

        // ContactInformation
        this.contactInformationEmail =
            this.contactInformationEmail != null ? this.contactInformationEmail :
                "mustermann@test.tld";
        this.contactInformationPrimaryPhoneNumber =
            this.contactInformationPrimaryPhoneNumber != null ?
                this.contactInformationPrimaryPhoneNumber : "0123456789";
        this.contactInformationSecondaryPhoneNumber =
            this.contactInformationSecondaryPhoneNumber != null ?
                this.contactInformationSecondaryPhoneNumber : "9876543210";

        this.companyName = this.companyName != null ? this.companyName : "Test Company";
        this.typeOfVisit = this.typeOfVisit != null ? this.typeOfVisit : "Test Visit";
        this.location = this.location != null ? this.location : "Test location";
        this.referenceBasisId = this.referenceBasisId != null ? this.referenceBasisId : "Reference";

        this.createdBy = this.createdBy != null ? this.createdBy : "unittest";
        this.createdAt = this.createdAt != null ? this.createdAt : ZonedDateTime.now();

        // CredentialMetadata
        this.issuedAt = this.issuedAt != null ? this.issuedAt : ZonedDateTime.now();
        this.issuedBy = this.issuedBy != null ? this.issuedBy : "unittest";
        this.credentialMetadataPersonalDataDeleted =
            this.credentialMetadataPersonalDataDeleted != null ?
                this.credentialMetadataPersonalDataDeleted : ZonedDateTime.now();

        return this.build();
    }

    @Test
    void buildGuestTest() throws InvalidValidityTimeframeException {
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

        assertEquals(this.validityTimeframe.getValidFrom(),
            guestDto.getCredentialOffer().getCredential().getValidityTimeframe().getValidFrom());
        assertEquals(this.validityTimeframe.getValidUntil(),
            guestDto.getCredentialOffer().getCredential().getValidityTimeframe().getValidUntil());

        this.reset();
    }
}
