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

package com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class EmployeeAccreditationBuilder {

    private EmployeeBuilder builder;

    public String id;
    public Employee employee;
    public EmployeeAccreditationStatus status;
    public String invitedBy;
    public ZonedDateTime invitedAt;
    public String invitationUrl;
    public String invitationEmail;
    public String invitationQrCode;
    public Correlation employeeCredentialIssuanceCorrelation;

    public EmployeeAccreditationBuilder() {
        this.builder = new EmployeeBuilder();
    }

    public EmployeeAccreditation build() {
        return new EmployeeAccreditation(this.id, this.employee, this.status,
            this.invitedBy, this.invitedAt, this.invitationUrl, this.invitationEmail,
            this.invitationQrCode, this.employeeCredentialIssuanceCorrelation);
    }

    public void reset() {
        this.id = null;
        this.employee = null;
        this.status = null;
        this.invitedBy = null;
        this.invitedAt = null;
        this.invitationUrl = null;
        this.invitationEmail = null;
        this.invitationQrCode = null;
        this.employeeCredentialIssuanceCorrelation = null;
    }

    public EmployeeAccreditation buildEmployeeAccreditation() {
        this.id = this.id != null ? this.id : "id";
        this.employee = this.employee != null ? this.employee : this.builder.buildEmployee();
        this.status = this.status != null ? this.status : EmployeeAccreditationStatus.OPEN;
        this.invitedBy = this.invitedBy != null ? this.invitedBy : "unit test";
        this.invitedAt = this.invitedAt != null ? this.invitedAt : ZonedDateTime.now();
        this.invitationUrl = this.invitationUrl != null ? this.invitationUrl : "url";
        this.invitationEmail = this.invitationEmail != null ? this.invitationEmail : "email";
        this.invitationQrCode = this.invitationQrCode != null ? this.invitationQrCode : "qrCode";
        this.employeeCredentialIssuanceCorrelation =
            this.employeeCredentialIssuanceCorrelation != null ?
                this.employeeCredentialIssuanceCorrelation : new Correlation(
                "connectionId", "threadId",
                "presentationExchangeId");

        return this.build();
    }

    @Test
    void buildEmployeeAccreditationTest() {
        EmployeeAccreditation accreditation = this.buildEmployeeAccreditation();

        assertEquals(this.id, accreditation.getId());
        assertEquals(this.employee, accreditation.getParty());
        assertEquals(this.status, accreditation.getStatus());
        assertEquals(this.invitedBy, accreditation.getInvitedBy());
        assertEquals(this.invitedAt, accreditation.getInvitedAt());
        assertEquals(this.invitationUrl, accreditation.getInvitationUrl());
        assertEquals(this.invitationEmail, accreditation.getInvitationEmail());
        assertEquals(this.invitationQrCode, accreditation.getInvitationQrCode());
        assertEquals(this.employeeCredentialIssuanceCorrelation,
            accreditation.getEmployeeCredentialIssuanceCorrelation());

        this.reset();
    }
}
