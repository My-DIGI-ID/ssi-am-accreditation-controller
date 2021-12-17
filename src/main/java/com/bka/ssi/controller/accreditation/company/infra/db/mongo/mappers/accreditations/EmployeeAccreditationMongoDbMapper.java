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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.EmployeeAccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties.EmployeeMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations.InvitationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CorrelationMongoDbDocument;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The type Employee accreditation mongo db mapper.
 */
@Component
public class EmployeeAccreditationMongoDbMapper {

    private final Logger logger;
    private final EmployeeMongoDbFacade employeeMongoDbFacade;

    /**
     * Instantiates a new Employee accreditation mongo db mapper.
     *
     * @param logger                the logger
     * @param employeeMongoDbFacade the employee mongo db facade
     */
    public EmployeeAccreditationMongoDbMapper(Logger logger,
        EmployeeMongoDbFacade employeeMongoDbFacade) {
        this.logger = logger;
        this.employeeMongoDbFacade = employeeMongoDbFacade;
    }

    /**
     * Entity to document employee accreditation mongo db document.
     *
     * @param employeeAccreditation the employee accreditation
     * @return the employee accreditation mongo db document
     */
    public EmployeeAccreditationMongoDbDocument entityToDocument(
        EmployeeAccreditation employeeAccreditation) {
        logger.debug("Mapping EmployeeAccreditation to MongoDb document");

        if (employeeAccreditation == null) {
            return null;
        } else {
            EmployeeAccreditationMongoDbDocument document =
                new EmployeeAccreditationMongoDbDocument();

            InvitationMongoDbValue invitationMongoDbValue = new InvitationMongoDbValue();
            invitationMongoDbValue.setInvitationUrl(employeeAccreditation.getInvitationUrl());
            invitationMongoDbValue.setInvitationEmail(employeeAccreditation.getInvitationEmail());
            invitationMongoDbValue.setInvitationQrCode(employeeAccreditation.getInvitationQrCode());

            document.setPartyId(employeeAccreditation.getParty().getId());
            document.setId(employeeAccreditation.getId());
            document.setInvitation(invitationMongoDbValue);
            document.setStatus(employeeAccreditation.getStatus().getName());
            document.setInvitedBy(employeeAccreditation.getInvitedBy());
            document.setInvitedAt(employeeAccreditation.getInvitedAt());

            CorrelationMongoDbDocument employeeCredentialIssuanceCorrelationMongoDbDocument =
                new CorrelationMongoDbDocument();

            if (employeeAccreditation.getEmployeeCredentialIssuanceCorrelation() != null) {
                employeeCredentialIssuanceCorrelationMongoDbDocument.setConnectionId(
                    employeeAccreditation.getEmployeeCredentialIssuanceCorrelation()
                        .getConnectionId());
                employeeCredentialIssuanceCorrelationMongoDbDocument
                    .setThreadId(
                        employeeAccreditation.getEmployeeCredentialIssuanceCorrelation()
                            .getThreadId());
                employeeCredentialIssuanceCorrelationMongoDbDocument.setPresentationExchangeId(
                    employeeAccreditation.getEmployeeCredentialIssuanceCorrelation()
                        .getPresentationExchangeId());
                employeeCredentialIssuanceCorrelationMongoDbDocument
                    .setCredentialRevocationRegistryId(
                        employeeAccreditation.getEmployeeCredentialIssuanceCorrelation()
                            .getCredentialRevocationRegistryId());
                employeeCredentialIssuanceCorrelationMongoDbDocument.setCredentialRevocationId(
                    employeeAccreditation.getEmployeeCredentialIssuanceCorrelation()
                        .getCredentialRevocationId());
            }

            document.setEmployeeCredentialIssuanceCorrelation(
                employeeCredentialIssuanceCorrelationMongoDbDocument);

            return document;
        }
    }

    /**
     * Document to entity employee accreditation.
     *
     * @param document the document
     * @return the employee accreditation
     */
    public EmployeeAccreditation documentToEntity(EmployeeAccreditationMongoDbDocument document) {
        logger.debug("Mapping EmployeeAccreditationMongoDbDocument to domain entity");

        if (document == null) {
            return null;
        } else {
            String employeeId = document.getPartyId();
            /**
             * Technical Debt.: ToDo: WARNING Tech Debt: Move DB fetch out of mapper, should be
             *                      done in the service (custom method in facade).
             *                      Further handle Optional not present.
             */
            Employee employee = employeeMongoDbFacade.findById(employeeId).get();

            Correlation employeeCredentialIssuanceCorrelation;

            if (document.getEmployeeCredentialIssuanceCorrelation() != null) {
                employeeCredentialIssuanceCorrelation = new Correlation(
                    document.getEmployeeCredentialIssuanceCorrelation()
                        .getConnectionId(),
                    document.getEmployeeCredentialIssuanceCorrelation()
                        .getThreadId(),
                    document.getEmployeeCredentialIssuanceCorrelation()
                        .getPresentationExchangeId(),
                    document.getEmployeeCredentialIssuanceCorrelation()
                        .getCredentialRevocationRegistryId(),
                    document.getEmployeeCredentialIssuanceCorrelation()
                        .getCredentialRevocationId()
                );
            } else {
                employeeCredentialIssuanceCorrelation = new Correlation();
            }

            EmployeeAccreditation accreditation = new EmployeeAccreditation(
                document.getId(),
                employee,
                EmployeeAccreditationStatus.valueOf(document.getStatus()),
                document.getInvitedBy(),
                document.getInvitedAt(),
                document.getInvitation().getInvitationUrl(),
                document.getInvitation().getInvitationEmail(),
                document.getInvitation().getInvitationQrCode(),
                employeeCredentialIssuanceCorrelation);

            return accreditation;
        }
    }
}
