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

package com.bka.ssi.controller.accreditation.company.domain.entities.credentials;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.ValidityTimeframeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class GuestCredentialTest {

    // Persona
    public String personaTitle;
    public String personaFirstName;
    public String personaLastName;

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
    public Date createdAt;

    // CredentialMetadata
    public String issuedBy;
    public Date issuedAt;
    public Date credentialMetadataPersonalDataDeleted;
    private Persona persona;
    private ValidityTimeframeBuilder validityTimeframeBuilder;
    private ValidityTimeframe validityTimeframe;
    private ContactInformation contactInformation;

    @BeforeEach
    void setup() {
        persona = new Persona(this.personaTitle, this.personaFirstName,
            this.personaLastName);

        validityTimeframeBuilder = new ValidityTimeframeBuilder();

        validityTimeframe = validityTimeframeBuilder.build();

        contactInformation =
            new ContactInformation(Arrays.asList(this.contactInformationEmail),
                Arrays.asList(this.contactInformationPrimaryPhoneNumber,
                    this.contactInformationSecondaryPhoneNumber));

    }


    @Test
    public void shouldCreateEmptyCredentialForDataCleanup() {

        GuestCredential guestCredential = new GuestCredential(validityTimeframe, persona,
            contactInformation, companyName, typeOfVisit, location, invitedBy);

        guestCredential.createEmptyCredentialForDataCleanup();

        assertEquals("deleted", guestCredential.getPersona().getTitle());
        assertEquals("deleted", guestCredential.getPersona().getFirstName());
        assertEquals("deleted", guestCredential.getPersona().getLastName());
        assertEquals(new ArrayList<>(), guestCredential.getContactInformation().getPhoneNumbers());
        assertEquals(new ArrayList<>(), guestCredential.getContactInformation().getEmails());
        assertEquals("deleted", guestCredential.getCompanyName());
        assertEquals("deleted", guestCredential.getTypeOfVisit());
        assertEquals("deleted", guestCredential.getLocation());
        assertEquals("deleted", guestCredential.getInvitedBy());
        assertEquals("deleted", guestCredential.getReferenceBasisId());
        assertEquals("deleted", guestCredential.getGuestPrivateInformation().getDateOfBirth());
        assertEquals("deleted",
            guestCredential.getGuestPrivateInformation().getLicencePlateNumber());
        assertEquals("deleted", guestCredential.getGuestPrivateInformation().getCompanyStreet());
        assertEquals("deleted", guestCredential.getGuestPrivateInformation().getCompanyCity());
        assertEquals("deleted", guestCredential.getGuestPrivateInformation().getCompanyPostCode());
        assertEquals("deleted", guestCredential.getGuestPrivateInformation().getAcceptedDocument());

    }


    @Test
    public void shouldAddGuestPrivateInformationToCredential() {
        // Given
        GuestCredential guestCredential = new GuestCredential(validityTimeframe, persona,
            contactInformation, companyName, typeOfVisit, location, invitedBy);

        GuestPrivateInformation guestPrivateInformation = new GuestPrivateInformation("license",
            "companyStreet", "companyCity", "companyPostCode", "acceptedDocuments");


        // When
        guestCredential.addGuestPrivateInformationToCredential(guestPrivateInformation,
            null, null);

        //Then
        assertEquals("companyStreet",
            guestCredential.getGuestPrivateInformation().getCompanyStreet());
        assertEquals("companyCity", guestCredential.getGuestPrivateInformation().getCompanyCity());
        assertEquals("companyPostCode",
            guestCredential.getGuestPrivateInformation().getCompanyPostCode());
        assertEquals("acceptedDocuments",
            guestCredential.getGuestPrivateInformation().getAcceptedDocument());
        assertEquals(Arrays.asList(this.contactInformationPrimaryPhoneNumber,
            this.contactInformationSecondaryPhoneNumber),
            guestCredential.getContactInformation().getPhoneNumbers());
    }


    @Test
    public void shouldAddGuestBirthdateOnBasisIdVerification() {
        // Given
        GuestCredential guestCredential = new GuestCredential(validityTimeframe, persona,
            contactInformation, companyName, typeOfVisit, location, invitedBy);
        GuestPrivateInformation guestPrivateInformation = new GuestPrivateInformation("license",
            "companyStreet", "companyCity", "companyPostCode", "acceptedDocuments");
        guestCredential.addGuestPrivateInformationToCredential(guestPrivateInformation,
            null, null);

        //When
        guestCredential.addGuestBirthDateOnBasisIdVerification("01.01.1970");

        //Then
        assertEquals("01.01.1970", guestCredential.getGuestPrivateInformation().getDateOfBirth());

    }

    @Test
    public void shouldAddBasisIdUniqueIdentifier() {
        // Given
        GuestCredential guestCredential = new GuestCredential(validityTimeframe, persona,
            contactInformation, companyName, typeOfVisit, location, invitedBy);
        GuestPrivateInformation guestPrivateInformation = new GuestPrivateInformation("license",
            "companyStreet", "companyCity", "companyPostCode", "acceptedDocuments");

        guestCredential.addGuestPrivateInformationToCredential(guestPrivateInformation,
            null, null);
        guestCredential.addGuestBirthDateOnBasisIdVerification("01.01.1970");

        //When
        guestCredential.addBasisIdUniqueIdentifier();

        //Then
        assertEquals(guestCredential.getPersona().getFirstName() + "_" +
                guestCredential.getPersona().getLastName() + "_" +
                guestCredential.getGuestPrivateInformation().getDateOfBirth(),
            guestCredential.getReferenceBasisId());
    }

    @Test
    public void shouldAddInvitingPersonInformationToCredential() {
        // Given
        GuestCredential guestCredential = new GuestCredential(validityTimeframe, persona,
            contactInformation, companyName, typeOfVisit, location, invitedBy);

        //When
        guestCredential.addInvitingPersonInformationToCredential("inviter");

        //Then
        assertEquals("inviter", guestCredential.getInvitedBy());

    }
}
