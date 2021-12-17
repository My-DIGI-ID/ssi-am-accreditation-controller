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

package com.bka.ssi.controller.accreditation.company.aop.configuration.agents;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The type Credentials configuration.
 */
@Configuration
public class CredentialsConfiguration {

    @Value("${bdr.credentials.basis_id.credential_definition_id}")
    private String basisIdCredentialDefinitionId;

    @Value("${bdr.credentials.basis_id.employee.schema_id}")
    private String basisIdCredentialSchemaId;

    @Value("${accreditation.credentials.employee.credential_definition_id}")
    private String employeeCredentialDefinitionId;

    @Value("${accreditation.credentials.employee.schema_id}")
    private String employeeCredentialSchemaId;

    @Value("${accreditation.credentials.guest.credential_definition_id}")
    private String guestCredentialDefinitionId;

    @Value("${accreditation.credentials.guest.schema_id}")
    private String guestCredentialSchemaId;

    /**
     * Gets basis id credential definition id.
     *
     * @return the basis id credential definition id
     */
    public String getBasisIdCredentialDefinitionId() {
        return basisIdCredentialDefinitionId;
    }

    /**
     * Gets basis id credential schema id.
     *
     * @return the basis id credential schema id
     */
    public String getBasisIdCredentialSchemaId() {
        return basisIdCredentialSchemaId;
    }

    /**
     * Gets employee credential definition id.
     *
     * @return the employee credential definition id
     */
    public String getEmployeeCredentialDefinitionId() {
        return employeeCredentialDefinitionId;
    }

    /**
     * Gets employee credential schema id.
     *
     * @return the employee credential schema id
     */
    public String getEmployeeCredentialSchemaId() {
        return employeeCredentialSchemaId;
    }

    /**
     * Gets guest credential definition id.
     *
     * @return the guest credential definition id
     */
    public String getGuestCredentialDefinitionId() {
        return guestCredentialDefinitionId;
    }

    /**
     * Gets guest credential schema id.
     *
     * @return the guest credential schema id
     */
    public String getGuestCredentialSchemaId() {
        return guestCredentialSchemaId;
    }
}
