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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.abstractions.CredentialMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ContactInformationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.EmployerMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.IdentityManagementMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PersonaMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PositionMongoDbValue;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Employee credential mongo db value.
 */
public class EmployeeCredentialMongoDbValue extends CredentialMongoDbValue {

    @Field("employeeId")
    private String employeeId;

    @Field("employeeState")
    private String employeeState;

    @Field("persona")
    private PersonaMongoDbValue persona;

    @Field("contactInformation")
    private ContactInformationMongoDbValue contactInformation;

    @Field("identityManagement")
    private IdentityManagementMongoDbValue identityManagement;

    @Field("employer")
    private EmployerMongoDbValue employer;

    @Field("position")
    private PositionMongoDbValue position;

    /**
     * Gets employee id.
     *
     * @return the employee id
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets employee id.
     *
     * @param employeeId the employee id
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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
     * Sets employee state.
     *
     * @param employeeState the employee state
     */
    public void setEmployeeState(String employeeState) {
        this.employeeState = employeeState;
    }

    /**
     * Gets persona.
     *
     * @return the persona
     */
    public PersonaMongoDbValue getPersona() {
        return persona;
    }

    /**
     * Sets persona.
     *
     * @param persona the persona
     */
    public void setPersona(PersonaMongoDbValue persona) {
        this.persona = persona;
    }

    /**
     * Gets contact information.
     *
     * @return the contact information
     */
    public ContactInformationMongoDbValue getContactInformation() {
        return contactInformation;
    }

    /**
     * Sets contact information.
     *
     * @param contactInformation the contact information
     */
    public void setContactInformation(
        ContactInformationMongoDbValue contactInformation) {
        this.contactInformation = contactInformation;
    }

    /**
     * Gets identity management.
     *
     * @return the identity management
     */
    public IdentityManagementMongoDbValue getIdentityManagement() {
        return identityManagement;
    }

    /**
     * Sets identity management.
     *
     * @param identityManagement the identity management
     */
    public void setIdentityManagement(
        IdentityManagementMongoDbValue identityManagement) {
        this.identityManagement = identityManagement;
    }

    /**
     * Gets employer.
     *
     * @return the employer
     */
    public EmployerMongoDbValue getEmployer() {
        return employer;
    }

    /**
     * Sets employer.
     *
     * @param employer the employer
     */
    public void setEmployer(
        EmployerMongoDbValue employer) {
        this.employer = employer;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public PositionMongoDbValue getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(
        PositionMongoDbValue position) {
        this.position = position;
    }
}
