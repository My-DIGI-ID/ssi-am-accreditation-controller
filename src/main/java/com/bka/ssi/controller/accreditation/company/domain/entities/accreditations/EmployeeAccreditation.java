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

package com.bka.ssi.controller.accreditation.company.domain.entities.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidAccreditationInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations.AccreditationInitialStateSpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;

import java.time.ZonedDateTime;

/**
 * The type Employee accreditation.
 */
public class EmployeeAccreditation extends Accreditation<Employee, EmployeeAccreditationStatus> {

    private Correlation employeeCredentialIssuanceCorrelation;

    /**
     * Instantiates a new Employee accreditation.
     *
     * @param party     the party
     * @param status    the status
     * @param invitedBy the invited by
     * @param invitedAt the invited at
     */
    public EmployeeAccreditation(Employee party, EmployeeAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt) {
        super(party, status, invitedBy, invitedAt);
    }

    /**
     * Instantiates a new Employee accreditation.
     *
     * @param id        the id
     * @param party     the party
     * @param status    the status
     * @param invitedBy the invited by
     * @param invitedAt the invited at
     */
    public EmployeeAccreditation(String id, Employee party, EmployeeAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt) {
        super(id, party, status, invitedBy, invitedAt);
    }

    /**
     * Instantiates a new Employee accreditation.
     *
     * @param id               the id
     * @param party            the party
     * @param status           the status
     * @param invitedBy        the invited by
     * @param invitedAt        the invited at
     * @param invitationUrl    the invitation url
     * @param invitationEmail  the invitation email
     * @param invitationQrCode the invitation qr code
     */
    public EmployeeAccreditation(String id, Employee party, EmployeeAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt, String invitationUrl, String invitationEmail,
        String invitationQrCode) {
        super(id, party, status, invitedBy, invitedAt, invitationUrl, invitationEmail,
            invitationQrCode);
    }

    /**
     * Instantiates a new Employee accreditation.
     *
     * @param id                                    the id
     * @param party                                 the party
     * @param status                                the status
     * @param invitedBy                             the invited by
     * @param invitedAt                             the invited at
     * @param invitationUrl                         the invitation url
     * @param invitationEmail                       the invitation email
     * @param invitationQrCode                      the invitation qr code
     * @param employeeCredentialIssuanceCorrelation the employee credential issuance correlation
     */
    public EmployeeAccreditation(String id, Employee party, EmployeeAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt, String invitationUrl, String invitationEmail,
        String invitationQrCode, Correlation employeeCredentialIssuanceCorrelation) {
        super(id, party, status, invitedBy, invitedAt, invitationUrl, invitationEmail,
            invitationQrCode);
        this.employeeCredentialIssuanceCorrelation = employeeCredentialIssuanceCorrelation;
    }

    /**
     * Initiate accreditation employee accreditation.
     *
     * @param invitationUrl    the invitation url
     * @param invitationEmail  the invitation email
     * @param invitationQrCode the invitation qr code
     * @param connectionId     the connection id
     * @return the employee accreditation
     * @throws InvalidAccreditationInitialStateException the invalid accreditation initial state
     * exception
     */
    public EmployeeAccreditation initiateAccreditation(String invitationUrl,
        String invitationEmail, String invitationQrCode, String connectionId)
        throws InvalidAccreditationInitialStateException {
        if (!new AccreditationInitialStateSpecification().isSatisfiedBy(this)) {
            throw new InvalidAccreditationInitialStateException();
        }

        this.invitationUrl = invitationUrl;
        this.invitationEmail = invitationEmail;
        this.invitationQrCode = invitationQrCode;

        this.employeeCredentialIssuanceCorrelation = new Correlation(connectionId);

        return this;
    }

    /**
     * Offer accreditation employee accreditation.
     *
     * @param correlation the correlation
     * @return the employee accreditation
     */
    public EmployeeAccreditation offerAccreditation(Correlation correlation) {
        this.employeeCredentialIssuanceCorrelation = correlation;
        this.status = EmployeeAccreditationStatus.PENDING;

        return this;
    }

    /**
     * Complete accreditation employee accreditation.
     *
     * @param correlation the correlation
     * @param issuedBy    the issued by
     * @return the employee accreditation
     */
    public EmployeeAccreditation completeAccreditation(Correlation correlation, String issuedBy) {
        this.employeeCredentialIssuanceCorrelation = correlation;
        this.status = EmployeeAccreditationStatus.ACCEPTED;
        this.getParty().addIssuedByAndIssuedAtToCredentialMetadata(issuedBy);

        return this;
    }

    /**
     * Delete personal data employee accreditation.
     *
     * @return the employee accreditation
     */
    public EmployeeAccreditation deletePersonalData() {
        this.invitationEmail = "deleted";
        this.getParty().removeCredentialFromCredentialOffer();

        return this;
    }

    /**
     * Revoke accreditation employee accreditation.
     *
     * @return the employee accreditation
     */
    public EmployeeAccreditation revokeAccreditation() {
        this.status = EmployeeAccreditationStatus.REVOKED;

        return this;
    }

    /**
     * Gets employee credential issuance correlation.
     *
     * @return the employee credential issuance correlation
     */
    public Correlation getEmployeeCredentialIssuanceCorrelation() {
        return employeeCredentialIssuanceCorrelation;
    }
}
