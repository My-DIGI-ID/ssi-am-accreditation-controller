package com.bka.ssi.controller.accreditation.company.domain.entities.credentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bka.ssi.controller.accreditation.company.domain.values.Address;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Employer;
import com.bka.ssi.controller.accreditation.company.domain.values.IdentityManagement;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class EmployeeCredentialTest {

    private List<String> emailAddresses;
    private String employeeState;
    private Persona persona;
    private Address address;
    private ContactInformation contactInformation;
    private IdentityManagement identityManagement;
    private Employer employer;
    private Position position;

    @BeforeEach
    void init() {
        emailAddresses = Arrays.asList("email");
        employeeState = "";
        persona = new Persona("title", "firstname", "lastname");
        address = new Address("12345", "streetname");
        contactInformation = new ContactInformation(emailAddresses);
        identityManagement = new IdentityManagement("reference");
        employer = new Employer("employerName", address);
        position = new Position();

    }


    @Test
    public void shouldCreateCredentialWithoutID() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential(employeeState, persona,
            contactInformation, identityManagement, employer, position);

        // Then
        assertNotEquals(null, employeeCredential);
    }


    @Test
    public void shouldCreateCredentialWithID() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential("id",
            employeeState, persona, contactInformation, identityManagement, employer, position);

        // Then
        assertNotEquals(null, employeeCredential);
    }

    @Test
    public void getEmployeeId() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential("id",
            employeeState, persona, contactInformation, identityManagement, employer, position);

        // Then
        assertEquals("id", employeeCredential.getEmployeeId());
    }

    @Test
    public void getEmployeeState() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential(
            employeeState, persona, contactInformation, identityManagement, employer, position);

        // Then
        assertEquals(employeeState, employeeCredential.getEmployeeState());
    }

    @Test
    public void getPersona() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential(
            employeeState, persona, contactInformation, identityManagement, employer, position);

        // Then
        assertEquals(persona, employeeCredential.getPersona());
    }

    @Test
    public void getContactInformation() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential(
            employeeState, persona, contactInformation, identityManagement, employer, position);

        // Then
        assertEquals(contactInformation, employeeCredential.getContactInformation());
    }

    @Test
    public void getIdentityManagement() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential(
            employeeState, persona, contactInformation, identityManagement, employer, position);

        // Then
        assertEquals(identityManagement, employeeCredential.getIdentityManagement());
    }

    @Test
    public void getEmployer() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential(
            employeeState, persona, contactInformation, identityManagement, employer, position);

        // Then
        assertEquals(employer, employeeCredential.getEmployer());
    }

    @Test
    public void getPosition() {
        // Given
        EmployeeCredential employeeCredential = new EmployeeCredential(
            employeeState, persona, contactInformation, identityManagement, employer, position);

        // Then
        assertEquals(position, employeeCredential.getPosition());
    }

}
