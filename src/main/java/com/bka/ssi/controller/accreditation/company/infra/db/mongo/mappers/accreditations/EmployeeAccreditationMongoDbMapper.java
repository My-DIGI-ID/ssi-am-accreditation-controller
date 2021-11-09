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

@Component
public class EmployeeAccreditationMongoDbMapper {

    private final Logger logger;
    private final EmployeeMongoDbFacade employeeMongoDbFacade;

    public EmployeeAccreditationMongoDbMapper(Logger logger,
        EmployeeMongoDbFacade employeeMongoDbFacade) {
        this.logger = logger;
        this.employeeMongoDbFacade = employeeMongoDbFacade;
    }

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
