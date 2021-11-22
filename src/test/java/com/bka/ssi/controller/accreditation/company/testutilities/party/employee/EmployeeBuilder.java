package com.bka.ssi.controller.accreditation.company.testutilities.party.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.values.Address;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.Employer;
import com.bka.ssi.controller.accreditation.company.domain.values.IdentityManagement;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.Position;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;

public class EmployeeBuilder {

    // PartyId
    public String id;

    // Creator
    public String createdBy;
    public ZonedDateTime createdAt;

    // EmployeeCredential
    // Persona
    public String personaTitle;
    public String personaFirstName;
    public String personaLastName;

    // ContactInformation
    public String contactInformationEmail;
    public String contactInformationPrimaryPhoneNumber;
    public String contactInformationSecondaryPhoneNumber;

    public String employeeId;
    public String employeeState;

    // Position
    public String position;

    // Employer
    public String employerName;
    public String employerSubject;
    public String employerProofOfOwnership;
    public String employerStreet;
    public String employerPostalCode;
    public String employerCity;
    public String employerCountry;
    public String employerHouseNumber;
    public String employerDoorNumber;

    // IdentityManagement
    public String identityManagementReference;
    public String identityManagementUsername;
    public String identityManagementEmail;

    // CredentialMetadata
    public String issuedBy;
    public ZonedDateTime issuedAt;
    public ZonedDateTime credentialMetadataPersonalDataDeleted;

    public EmployeeBuilder() {
    }

    public Employee build() {
        Persona persona = new Persona(this.personaTitle, this.personaFirstName,
            this.personaLastName);

        ContactInformation contactInformation =
            new ContactInformation(Arrays.asList(this.contactInformationEmail),
                Arrays.asList(this.contactInformationPrimaryPhoneNumber,
                    this.contactInformationSecondaryPhoneNumber));

        Position position = new Position(this.position);

        Address employerAddress = new Address(this.employerPostalCode, this.employerCountry,
            this.employerCity, this.employerStreet, this.employerHouseNumber,
            this.employerDoorNumber);

        Employer employer = new Employer(this.employerName, employerAddress, this.employerSubject,
            this.employerProofOfOwnership);

        IdentityManagement identityManagement =
            new IdentityManagement(this.identityManagementReference,
                this.identityManagementUsername, this.identityManagementEmail);

        CredentialMetadata credentialMetadata = new CredentialMetadata(this.issuedBy, this.issuedAt,
            this.credentialMetadataPersonalDataDeleted, CredentialType.EMPLOYEE);

        EmployeeCredential employeeCredential = new EmployeeCredential(this.employeeId,
            this.employeeState, persona, contactInformation, identityManagement, employer,
            position);

        CredentialOffer<EmployeeCredential> credentialOffer =
            new CredentialOffer<>(credentialMetadata, employeeCredential);

        return new Employee(this.id, credentialOffer, this.createdBy, this.createdAt);

    }

    public void reset() {
        // PartyId
        this.id = null;

        // Creator
        this.createdBy = null;
        this.createdAt = null;

        // EmployeeCredential
        // Persona
        this.personaTitle = null;
        this.personaFirstName = null;
        this.personaLastName = null;

        // ContactInformation
        this.contactInformationEmail = null;
        this.contactInformationPrimaryPhoneNumber = null;
        this.contactInformationSecondaryPhoneNumber = null;

        this.employeeId = null;
        this.employeeState = null;

        // Position
        this.position = null;

        // Employer
        this.employerName = null;
        this.employerSubject = null;
        this.employerProofOfOwnership = null;
        this.employerStreet = null;
        this.employerPostalCode = null;
        this.employerCity = null;
        this.employerCountry = null;
        this.employerHouseNumber = null;
        this.employerDoorNumber = null;

        // IdentityManagement
        this.identityManagementReference = null;
        this.identityManagementUsername = null;
        this.identityManagementEmail = null;

        // CredentialMetadata
        this.issuedBy = null;
        this.issuedAt = null;
        this.credentialMetadataPersonalDataDeleted = null;
    }

    public Employee buildEmployee() {
        // PartyId
        this.id = this.id != null ? this.id : "12345";

        // Creator
        this.createdBy = this.createdBy != null ? this.createdBy : "hr-admin-01";
        this.createdAt = this.createdAt != null ? this.createdAt : ZonedDateTime.now();

        // EmployeeCredential
        // Persona
        this.personaTitle = this.personaTitle != null ? this.personaTitle : "Mr.";
        this.personaFirstName = this.personaFirstName != null ? this.personaFirstName : "Erika";
        this.personaLastName = this.personaLastName != null ? this.personaLastName : "Mustermann";

        // ContactInformation
        this.contactInformationEmail =
            this.contactInformationEmail != null ? this.contactInformationEmail :
                "mustermann@test.de";
        this.contactInformationPrimaryPhoneNumber =
            this.contactInformationPrimaryPhoneNumber != null ?
                this.contactInformationPrimaryPhoneNumber : "1234567890";
        this.contactInformationSecondaryPhoneNumber =
            this.contactInformationSecondaryPhoneNumber != null ?
                this.contactInformationSecondaryPhoneNumber : "1234567890";

        this.employeeId = this.employeeId != null ? this.employeeId : "12345";
        this.employeeState = this.employeeState != null ? this.employeeState : "employeeState";

        // Position
        this.position = this.position != null ? this.position : "Employed";

        // Employer
        this.employerName = this.employerName != null ? this.employerName : "IBM";
        this.employerSubject = this.employerSubject != null ? this.employerSubject : "This Subject";
        this.employerProofOfOwnership =
            this.employerProofOfOwnership != null ? this.employerProofOfOwnership :
                "employerProofOfOwnership";
        this.employerStreet = this.employerStreet != null ? this.employerStreet : "Bakerstreet";
        this.employerPostalCode =
            this.employerPostalCode != null ? this.employerPostalCode : "10001";
        this.employerCity = this.employerCity != null ? this.employerCity : "London";
        this.employerCountry =
            this.employerCountry != null ? this.employerCountry : "United Kingdom";
        this.employerHouseNumber =
            this.employerHouseNumber != null ? this.employerHouseNumber : "1";
        this.employerDoorNumber = this.employerDoorNumber != null ? this.employerDoorNumber : "12";

        // IdentityManagement
        this.identityManagementReference =
            this.identityManagementReference != null ? this.identityManagementReference :
                "This Reference";
        this.identityManagementUsername =
            this.identityManagementUsername != null ? this.identityManagementUsername :
                "this@reference.test";
        this.identityManagementEmail =
            this.identityManagementEmail != null ? this.identityManagementEmail :
                "managament@reference.test";

        // CredentialMetadata
        this.issuedBy = this.issuedBy != null ? this.issuedBy : "TBD";
        this.issuedAt = this.issuedAt != null ? this.issuedAt : ZonedDateTime.now();
        this.credentialMetadataPersonalDataDeleted =
            this.credentialMetadataPersonalDataDeleted != null ?
                this.credentialMetadataPersonalDataDeleted : ZonedDateTime.now();

        return this.build();
    }

    @Test
    void buildEmployeeTest() {
        Employee employeeDto = this.buildEmployee();
        // PartyId
        assertEquals(this.id, employeeDto.getCredentialOffer().getCredential().getEmployeeId());

        // Creator
        assertEquals(this.credentialMetadataPersonalDataDeleted,
            employeeDto.getCredentialOffer().getCredentialMetadata().getPartyPersonalDataDeleted());
        assertEquals(this.createdBy, employeeDto.getCreatedBy());
        assertEquals(this.createdAt.toString(), employeeDto.getCreatedAt().toString());

        // EmployeeCredential
        // Persona
        assertEquals(this.personaTitle,
            employeeDto.getCredentialOffer().getCredential().getPersona().getTitle());
        assertEquals(this.personaFirstName,
            employeeDto.getCredentialOffer().getCredential().getPersona().getFirstName());
        assertEquals(this.personaLastName,
            employeeDto.getCredentialOffer().getCredential().getPersona().getLastName());

        // ContactInformation
        assertTrue(
            employeeDto.getCredentialOffer().getCredential().getContactInformation().getEmails()
                .contains(this.contactInformationEmail));
        assertTrue(employeeDto.getCredentialOffer().getCredential().getContactInformation()
            .getPhoneNumbers().contains(this.contactInformationPrimaryPhoneNumber));
        assertTrue(employeeDto.getCredentialOffer().getCredential().getContactInformation()
            .getPhoneNumbers().contains(this.contactInformationSecondaryPhoneNumber));
        assertEquals(this.employeeId,
            employeeDto.getCredentialOffer().getCredential().getEmployeeId());
        assertEquals(this.employeeState,
            employeeDto.getCredentialOffer().getCredential().getEmployeeState());

        // Position
        assertEquals(this.position,
            employeeDto.getCredentialOffer().getCredential().getPosition().getName());

        // Employer
        assertEquals(this.employerName,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getName());
        assertEquals(this.employerSubject,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getSubject());
        assertEquals(this.employerProofOfOwnership,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getProofOfOwnership());
        assertEquals(this.employerStreet,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getAddress()
                .getStreet());
        assertEquals(this.employerPostalCode,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getAddress()
                .getPostalCode());
        assertEquals(this.employerCity,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getAddress().getCity());
        assertEquals(this.employerCountry,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getAddress()
                .getCountry());
        assertEquals(this.employerHouseNumber,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getAddress()
                .getHouseNumber());
        assertEquals(this.employerDoorNumber,
            employeeDto.getCredentialOffer().getCredential().getEmployer().getAddress()
                .getDoorNumber());

        // IdentityManagement
        assertEquals(this.identityManagementReference,
            employeeDto.getCredentialOffer().getCredential().getIdentityManagement()
                .getReference());
        assertEquals(this.identityManagementUsername,
            employeeDto.getCredentialOffer().getCredential().getIdentityManagement().getUsername());
        assertEquals(this.identityManagementEmail,
            employeeDto.getCredentialOffer().getCredential().getIdentityManagement().getEmail());

        // CredentialMetadata
        assertEquals(this.issuedBy,
            employeeDto.getCredentialOffer().getCredentialMetadata().getIssuedBy());
        assertEquals(this.issuedAt,
            employeeDto.getCredentialOffer().getCredentialMetadata().getIssuedAt());

        this.reset();
    }
}
