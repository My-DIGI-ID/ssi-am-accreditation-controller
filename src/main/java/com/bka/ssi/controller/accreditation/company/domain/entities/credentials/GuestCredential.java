package com.bka.ssi.controller.accreditation.company.domain.entities.credentials;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

import java.util.List;

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
        String location, String invitedBy) {
        this.validityTimeframe = validityTimeframe;
        this.persona = persona;
        this.contactInformation = contactInformation;
        this.companyName = companyName;
        this.typeOfVisit = typeOfVisit;
        this.location = location;
        this.invitedBy = invitedBy;
    }

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

    public void addGuestPrivateInformationToCredential(
        GuestPrivateInformation guestPrivateInformation,
        String primaryPhoneNumber, String secondaryPhoneNumber) {
        this.guestPrivateInformation = guestPrivateInformation;

        List<String> emails = this.contactInformation.getEmails();
        List<String> phoneNumbers = this.contactInformation.getPhoneNumbers();

        if (primaryPhoneNumber != null) {
            phoneNumbers.add(primaryPhoneNumber);
        }

        if (secondaryPhoneNumber != null) {
            phoneNumbers.add(secondaryPhoneNumber);
        }

        ContactInformation newContactInformation = new ContactInformation(emails, phoneNumbers);

        this.contactInformation = newContactInformation;
    }

    /* Should not alter state of value object, method to be moved to entity */
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

    /* Should not alter state of value object, method to be moved to entity */
    public GuestCredential addBasisIdUniqueIdentifier() {
        this.referenceBasisId =
            this.persona.getFirstName() + "_" + this.persona.getLastName() + "_" +
                this.guestPrivateInformation.getDateOfBirth();

        return this;
    }

    /* Should not alter state of value object, method to be moved to entity */
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
