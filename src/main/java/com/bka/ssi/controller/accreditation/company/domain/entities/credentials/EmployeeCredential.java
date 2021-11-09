package com.bka.ssi.controller.accreditation.company.domain.entities.credentials;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.values.Address;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Employer;
import com.bka.ssi.controller.accreditation.company.domain.values.IdentityManagement;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.Position;

import java.util.ArrayList;

public class EmployeeCredential extends Credential {

    private String employeeId;
    private String employeeState;
    private Persona persona;
    private ContactInformation contactInformation;
    private IdentityManagement identityManagement;
    private Employer employer;
    private Position position;

    public EmployeeCredential(String employeeId, String employeeState, Persona persona,
        ContactInformation contactInformation,
        IdentityManagement identityManagement,
        Employer employer, Position position) {
        this.employeeId = employeeId;
        this.employeeState = employeeState;
        this.persona = persona;
        this.contactInformation = contactInformation;
        this.identityManagement = identityManagement;
        this.employer = employer;
        this.position = position;
    }

    public EmployeeCredential(String employeeState,
        Persona persona,
        ContactInformation contactInformation,
        IdentityManagement identityManagement,
        Employer employer, Position position) {
        this.employeeState = employeeState;
        this.persona = persona;
        this.contactInformation = contactInformation;
        this.identityManagement = identityManagement;
        this.employer = employer;
        this.position = position;
    }

    @Override
    public Credential createEmptyCredential() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Credential createEmptyCredentialForDataCleanup() {
        this.employeeId = "deleted";
        this.employeeState = "deleted";

        this.persona = new Persona(
            "deleted",
            "deleted",
            "deleted"
        );

        this.contactInformation = new ContactInformation(
            new ArrayList<>(),
            new ArrayList<>()
        );

        this.identityManagement = new IdentityManagement(
            "deleted",
            "deleted",
            "deleted"
        );

        this.employer = new Employer(
            "deleted",
            new Address(
                "deleted",
                "deleted",
                "deleted",
                "deleted",
                "deleted",
                "deleted"
            ),
            "deleted",
            "deleted"
        );

        this.position = new Position("deleted");

        return this;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeState() {
        return employeeState;
    }

    public Persona getPersona() {
        return persona;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public IdentityManagement getIdentityManagement() {
        return identityManagement;
    }

    public Employer getEmployer() {
        return employer;
    }

    public Position getPosition() {
        return position;
    }
}
