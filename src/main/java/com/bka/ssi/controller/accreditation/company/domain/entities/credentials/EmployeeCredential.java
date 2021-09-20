package com.bka.ssi.controller.accreditation.company.domain.entities.credentials;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Employer;
import com.bka.ssi.controller.accreditation.company.domain.values.IdentityManagement;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.Position;

public class EmployeeCredential extends Credential {

    private String employeeId;
    /* ToDo - Use enum EmployeeStatus? */
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
