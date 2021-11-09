package com.bka.ssi.controller.accreditation.company.domain.entities.credentials;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

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

    public GuestCredential addBasisIdUniqueIdentifier() {
        this.referenceBasisId =
            this.persona.getFirstName() + "_" + this.persona.getLastName() + "_"
                + this.guestPrivateInformation.getDateOfBirth();

        return this;
    }

    public GuestCredential addInvitingPersonInformationToCredential(String invitedBy) {
        this.invitedBy = invitedBy;

        return this;
    }

    public ValidityTimeframe getValidityTimeframe() {
        return validityTimeframe;
    }

    public Persona getPersona() {
        return persona;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTypeOfVisit() {
        return typeOfVisit;
    }

    public String getLocation() {
        return location;
    }

    public String getReferenceBasisId() {
        return referenceBasisId;
    }

    public GuestPrivateInformation getGuestPrivateInformation() {
        return guestPrivateInformation;
    }

    public String getInvitedBy() {
        return invitedBy;
    }
}
