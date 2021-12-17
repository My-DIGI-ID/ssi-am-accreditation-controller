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

package com.bka.ssi.controller.accreditation.company.domain.entities.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

import java.time.ZonedDateTime;

/**
 * The type Employee.
 */
public class Employee extends Party<EmployeeCredential> {

    /**
     * Instantiates a new Employee.
     *
     * @param id              the id
     * @param credentialOffer the credential offer
     * @param createdBy       the created by
     * @param createAt        the create at
     */
    public Employee(String id,
        CredentialOffer<EmployeeCredential> credentialOffer, String createdBy,
        ZonedDateTime createAt) {
        super(id, credentialOffer, createdBy, createAt);
    }

    /**
     * Instantiates a new Employee.
     *
     * @param credentialOffer the credential offer
     * @param createdBy       the created by
     * @param createAt        the create at
     */
    public Employee(CredentialOffer<EmployeeCredential> credentialOffer, String createdBy,
        ZonedDateTime createAt) {
        super(credentialOffer, createdBy, createAt);
    }

    @Override
    public Party<EmployeeCredential> removeCredentialFromCredentialOffer() {
        CredentialMetadata newCredentialMetadata = new CredentialMetadata(
            this.credentialOffer.getCredentialMetadata().getIssuedBy(),
            this.credentialOffer.getCredentialMetadata().getIssuedAt(),
            ZonedDateTime.now(),
            CredentialType.EMPLOYEE
        );

        EmployeeCredential cleanedCredential = (EmployeeCredential) this.credentialOffer
            .getCredential().createEmptyCredentialForDataCleanup();

        this.credentialOffer = new CredentialOffer<>(newCredentialMetadata, cleanedCredential);

        return this;
    }

    /**
     * Add issued by and issued at to credential metadata employee.
     *
     * @param issuedBy the issued by
     * @return the employee
     */
    public Employee addIssuedByAndIssuedAtToCredentialMetadata(String issuedBy) {
        CredentialMetadata credentialMetadata = new CredentialMetadata(
            issuedBy,
            ZonedDateTime.now(),
            CredentialType.EMPLOYEE
        );

        this.credentialOffer = new CredentialOffer<>(credentialMetadata,
            this.credentialOffer.getCredential());

        return this;
    }
}
