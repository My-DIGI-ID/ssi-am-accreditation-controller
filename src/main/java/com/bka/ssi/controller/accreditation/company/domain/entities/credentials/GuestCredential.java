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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

/**
 * The type Guest credential.
 */
public class GuestCredential extends Credential {


    private ValidityTimeframe validityTimeframe;
    private Persona persona;
    private ContactInformation contactInformation;
    private String companyName;
    private String typeOfVisit;
    private String location;
    private String invitedBy;
    private String referenceBasisId;

    private GuestPrivateInformation guestPrivateInformation;

    /**
     * Instantiates a new Guest credential.
     *
     * @param validityTimeframe       the validity timeframe
     * @param persona                 the persona
     * @param contactInformation      the contact information
     * @param companyName             the company name
     * @param typeOfVisit             the type of visit
     * @param location                the location
     * @param invitedBy               the invited by
     * @param referenceBasisId        the reference basis id
     * @param guestPrivateInformation the guest private information
     */
    public GuestCredential(
        ValidityTimeframe validityTimeframe,
        Persona persona,
        ContactInformation contactInformation, String companyName, String typeOfVisit,
        String location, String invitedBy, String referenceBasisId,
        GuestPrivateInformation guestPrivateInformation) {
        this.validityTimeframe = validityTimeframe;
        this.persona = persona;
        this.contactInformation = contactInformation;
        this.companyName = companyName;
        this.typeOfVisit = typeOfVisit;
        this.location = location;
        this.invitedBy = invitedBy;
        this.referenceBasisId = referenceBasisId;
        this.guestPrivateInformation = guestPrivateInformation;
    }

    /**
     * Instantiates a new Guest credential.
     *
     * @param validityTimeframe  the validity timeframe
     * @param persona            the persona
     * @param contactInformation the contact information
     * @param companyName        the company name
     * @param typeOfVisit        the type of visit
     * @param location           the location
     * @param invitedBy          the invited by
     */
    public GuestCredential(
        ValidityTimeframe validityTimeframe,
        Persona persona,
        ContactInformation contactInformation, String companyName, String typeOfVisit,
        String location, String invitedBy) {
        this.validityTimeframe = validityTimeframe;
        this.persona = persona;
        this.contactInformation = contactInformation;
        this.companyName = companyName;
        this.typeOfVisit = typeOfVisit;
        this.location = location;
        this.invitedBy = invitedBy;
    }

    @Override
    public Credential createEmptyCredential() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Credential createEmptyCredentialForDataCleanup() {
        this.validityTimeframe = new ValidityTimeframe(
            ZonedDateTime.now(),
            ZonedDateTime.now()
        );

        this.persona = new Persona(
            "deleted",
            "deleted",
            "deleted"
        );

        this.contactInformation = new ContactInformation(
            new ArrayList<>(),
            new ArrayList<>()
        );

        this.companyName = "deleted";
        this.typeOfVisit = "deleted";
        this.location = "deleted";
        this.invitedBy = "deleted";
        this.referenceBasisId = "deleted";

        this.guestPrivateInformation = new GuestPrivateInformation(
            "deleted",
            "deleted",
            "deleted",
            "deleted",
            "deleted",
            "deleted"
        );

        return this;
    }

    /**
     * Add guest private information to credential guest credential.
     *
     * @param guestPrivateInformation the guest private information
     * @param primaryPhoneNumber      the primary phone number
     * @param secondaryPhoneNumber    the secondary phone number
     * @return the guest credential
     */
    public GuestCredential addGuestPrivateInformationToCredential(
        GuestPrivateInformation guestPrivateInformation,
        String primaryPhoneNumber, String secondaryPhoneNumber) {
        this.guestPrivateInformation = guestPrivateInformation;

        List<String> emails = this.contactInformation.getEmails();
        ArrayList<String> phoneNumbers =
            new ArrayList<String>(this.contactInformation.getPhoneNumbers());

        if (primaryPhoneNumber != null) {
            phoneNumbers.add(primaryPhoneNumber);
        }

        if (secondaryPhoneNumber != null) {
            phoneNumbers.add(secondaryPhoneNumber);
        }

        ContactInformation newContactInformation = new ContactInformation(emails, phoneNumbers);

        this.contactInformation = newContactInformation;

        return this;
    }

    /**
     * Add guest birth date on basis id verification guest credential.
     *
     * @param dateOfBirth the date of birth
     * @return the guest credential
     */
    public GuestCredential addGuestBirthDateOnBasisIdVerification(String dateOfBirth) {
        this.guestPrivateInformation = new GuestPrivateInformation(
            dateOfBirth,
            this.guestPrivateInformation.getLicencePlateNumber(),
            this.guestPrivateInformation.getCompanyStreet(),
            this.guestPrivateInformation.getCompanyCity(),
            this.guestPrivateInformation.getCompanyPostCode(),
            this.guestPrivateInformation.getAcceptedDocument()
        );

        return this;
    }

    /**
     * Add basis id unique identifier guest credential.
     *
     * @return the guest credential
     */
    public GuestCredential addBasisIdUniqueIdentifier() {
        this.referenceBasisId =
            this.persona.getFirstName() + "_" + this.persona.getLastName() + "_"
                + this.guestPrivateInformation.getDateOfBirth();

        return this;
    }

    /**
     * Add inviting person information to credential guest credential.
     *
     * @param invitedBy the invited by
     * @return the guest credential
     */
    public GuestCredential addInvitingPersonInformationToCredential(String invitedBy) {
        this.invitedBy = invitedBy;

        return this;
    }

    /**
     * Gets validity timeframe.
     *
     * @return the validity timeframe
     */
    public ValidityTimeframe getValidityTimeframe() {
        return validityTimeframe;
    }

    /**
     * Gets persona.
     *
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Gets contact information.
     *
     * @return the contact information
     */
    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets type of visit.
     *
     * @return the type of visit
     */
    public String getTypeOfVisit() {
        return typeOfVisit;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets reference basis id.
     *
     * @return the reference basis id
     */
    public String getReferenceBasisId() {
        return referenceBasisId;
    }

    /**
     * Gets guest private information.
     *
     * @return the guest private information
     */
    public GuestPrivateInformation getGuestPrivateInformation() {
        return guestPrivateInformation;
    }

    /**
     * Gets invited by.
     *
     * @return the invited by
     */
    public String getInvitedBy() {
        return invitedBy;
    }
}
