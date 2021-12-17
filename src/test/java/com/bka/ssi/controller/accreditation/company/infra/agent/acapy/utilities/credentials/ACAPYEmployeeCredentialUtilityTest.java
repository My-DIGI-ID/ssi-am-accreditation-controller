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

package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.credentials;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
import io.github.my_digi_id.acapy_client.model.CredentialPreview;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

class ACAPYEmployeeCredentialUtilityTest {

    private final Logger logger =
        LoggerFactory.getLogger(ACAPYEmployeeCredentialUtilityTest.class);

    private ACAPYEmployeeCredentialUtility acapyEmployeeCredentialUtility;

    @BeforeEach
    void setUp() {
        acapyEmployeeCredentialUtility = new ACAPYEmployeeCredentialUtility(logger);
    }

    @Test
    void createCredentialPreview() {
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        Employee employee = employeeBuilder.buildEmployee();

        EmployeeCredential employeeCredential = employee.getCredentialOffer().getCredential();

        CredentialPreview credentialPreview =
            acapyEmployeeCredentialUtility.createCredentialPreview(employeeCredential);

        Assertions.assertNotNull(credentialPreview.getAttributes());
        Assertions.assertEquals(15, credentialPreview.getAttributes().size());

        Map<String, String> attributes = new HashMap<>();

        credentialPreview.getAttributes().stream().forEach((attr) -> {
            attributes.put(attr.getName(), attr.getValue());
        });

        Assertions.assertEquals(employeeCredential.getPersona().getFirstName(),
            attributes.get("firstName"));
        Assertions.assertEquals(employeeCredential.getPersona().getLastName(),
            attributes.get("lastName"));
        Assertions.assertEquals(employeeCredential.getPersona().getTitle(),
            attributes.get("title"));
        Assertions.assertEquals(employeeCredential.getContactInformation().getPhoneNumbers().get(0),
            attributes.get(
                "primaryPhoneNumber"));
        Assertions.assertEquals(employeeCredential.getContactInformation().getPhoneNumbers().get(1),
            attributes.get(
                "secondaryPhoneNumber"));
        Assertions.assertEquals(employeeCredential.getContactInformation().getEmails().get(0),
            attributes.get(
                "email"));
        Assertions.assertEquals(employeeCredential.getEmployeeState(),
            attributes.get("employeeState"));
        Assertions.assertEquals(employeeCredential.getEmployeeId(), attributes.get("employeeId"));
        Assertions.assertEquals(employeeCredential.getPosition().getName(),
            attributes.get("position"));
        Assertions.assertEquals(employeeCredential.getEmployer().getName(),
            attributes.get("companyName"));
        Assertions.assertEquals(employeeCredential.getEmployer().getAddress().getStreet(),
            attributes.get(
                "companyStreet"));
        Assertions.assertEquals(employeeCredential.getEmployer().getAddress().getCity(),
            attributes.get(
                "companyCity"));
        Assertions.assertEquals(employeeCredential.getEmployer().getAddress().getPostalCode(),
            attributes.get(
                "companyPostCode"));
        Assertions.assertEquals(employeeCredential.getEmployer().getProofOfOwnership(),
            attributes.get(
                "companyProofOfOwnership"));
        Assertions.assertEquals(employeeCredential.getIdentityManagement().getReference(),
            attributes.get(
                "ssoReference"));
    }
}