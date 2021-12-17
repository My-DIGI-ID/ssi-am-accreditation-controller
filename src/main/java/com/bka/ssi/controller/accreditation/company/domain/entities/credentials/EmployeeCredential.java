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

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.values.Address;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Employer;
import com.bka.ssi.controller.accreditation.company.domain.values.IdentityManagement;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.Position;

import java.util.ArrayList;

/**
 * The type Employee credential.
 */
public class EmployeeCredential extends Credential {

    private String employeeId;
    private String employeeState;
    private Persona persona;
    private ContactInformation contactInformation;
    private IdentityManagement identityManagement;
    private Employer employer;
    private Position position;

    /**
     * Instantiates a new Employee credential.
     *
     * @param employeeId         the employee id
     * @param employeeState      the employee state
     * @param persona            the persona
     * @param contactInformation the contact information
     * @param identityManagement the identity management
     * @param employer           the employer
     * @param position           the position
     */
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

    /**
     * Instantiates a new Employee credential.
     *
     * @param employeeState      the employee state
     * @param persona            the persona
     * @param contactInformation the contact information
     * @param identityManagement the identity management
     * @param employer           the employer
     * @param position           the position
     */
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

    /**
     * Gets employee id.
     *
     * @return the employee id
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets employee state.
     *
     * @return the employee state
     */
    public String getEmployeeState() {
        return employeeState;
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
     * Gets identity management.
     *
     * @return the identity management
     */
    public IdentityManagement getIdentityManagement() {
        return identityManagement;
    }

    /**
     * Gets employer.
     *
     * @return the employer
     */
    public Employer getEmployer() {
        return employer;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }
}
