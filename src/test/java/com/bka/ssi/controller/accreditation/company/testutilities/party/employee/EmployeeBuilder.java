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

    // Creator
    public String createdBy;
    public ZonedDateTime createdAt;

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
        this.id = "";
        // EmployeeCredential
        // Persona
        this.personaTitle = "";
        this.personaFirstName = "";
        this.personaLastName = "";
        // ContactInformation
        this.contactInformationEmail = "";
        this.contactInformationPrimaryPhoneNumber = "";
        this.contactInformationSecondaryPhoneNumber = "";

        this.employeeId = "";
        this.employeeState = "";
        // Position
        this.position = "";
        // Employer
        this.employerName = "";
        this.employerSubject = "";
        this.employerProofOfOwnership = "";
        this.employerStreet = "";
        this.employerPostalCode = "";
        this.employerCity = "";
        this.employerCountry = "";
        this.employerHouseNumber = "";
        this.employerDoorNumber = "";
        // IdentityManagement
        this.identityManagementReference = "";
        this.identityManagementUsername = "";
        this.identityManagementEmail = "";

        // CredentialMetadata
        this.issuedBy = "";
        this.issuedAt = null;
        this.credentialMetadataPersonalDataDeleted = null;

        // Creator
        this.createdBy = "";
        this.createdAt = null;
    }

    public Employee buildEmployee() {
        this.reset();

        // PartyId
        this.id = "12345";
        // EmployeeCredential
        // Persona
        this.personaTitle = "Mr.";
        this.personaFirstName = "Erika";
        this.personaLastName = "Mustermann";
        // ContactInformation
        this.contactInformationEmail = "mustermann@test.de";
        this.contactInformationPrimaryPhoneNumber = "1234567890";
        this.contactInformationSecondaryPhoneNumber = "1234567890";

        this.employeeId = "12345";
        this.employeeState = "";
        // Position
        this.position = "Employed";
        // Employer
        this.employerName = "IBM";
        this.employerSubject = "This Subject";
        this.employerProofOfOwnership = "";
        this.employerStreet = "Bakerstreet";
        this.employerPostalCode = "10001";
        this.employerCity = "London";
        this.employerCountry = "United Kingdom";
        this.employerHouseNumber = "1";
        this.employerDoorNumber = "12";
        // IdentityManagement
        this.identityManagementReference = "This Reference";
        this.identityManagementUsername = "this@reference.test";
        this.identityManagementEmail = "managament@reference.test";

        // CredentialMetadata
        this.issuedBy = "TBD";
        this.issuedAt = ZonedDateTime.now();
        this.credentialMetadataPersonalDataDeleted = ZonedDateTime.now();

        // Creator
        this.createdBy = "hr-admin-01";
        this.createdAt = ZonedDateTime.now();

        return this.build();
    }

    @Test
    private void buildEmployeeTest() {
        Employee employeeDto = this.buildEmployee();
        // PartyId
        assertEquals(this.id, employeeDto.getCredentialOffer().getCredential().getEmployeeId());

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
        // Creator
        assertEquals(this.credentialMetadataPersonalDataDeleted,
            employeeDto.getCredentialOffer().getCredentialMetadata().getPartyPersonalDataDeleted());
        assertEquals(this.createdBy, employeeDto.getCreatedBy());
        assertEquals(this.createdAt.toString(), employeeDto.getCreatedAt().toString());

        this.reset();
    }
}
