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

package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import com.bka.ssi.controller.accreditation.company.application.agent.ACAPYClient;
import com.bka.ssi.controller.accreditation.company.application.exceptions.AlreadyExistsException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.accreditations.EmployeeAccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.EmployeeAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.parties.EmployeeRepository;
import com.bka.ssi.controller.accreditation.company.application.services.AccreditationService;
import com.bka.ssi.controller.accreditation.company.application.utilities.EmailBuilder;
import com.bka.ssi.controller.accreditation.company.application.utilities.QrCodeGenerator;
import com.bka.ssi.controller.accreditation.company.application.utilities.UrlBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations.AccreditationCompletedSpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Employee accreditation service.
 */
@Service
public class EmployeeAccreditationService
    extends AccreditationService<EmployeeAccreditation, Employee, EmployeeAccreditationStatus> {

    @Value("${accreditation.employee.connection.qr.size}")
    private int qrSize;

    private final ACAPYClient acapyClient;
    private final EmailBuilder emailBuilder;
    private final UrlBuilder urlBuilder;

    /**
     * Instantiates a new Employee accreditation service.
     *
     * @param logger                          the logger
     * @param employeeAccreditationRepository the employee accreditation repository
     * @param employeeAccreditationFactory    the employee accreditation factory
     * @param acapyClient                     the acapy client
     * @param emailBuilder                    the email builder
     * @param urlBuilder                      the url builder
     * @param employeeRepository              the employee repository
     */
    public EmployeeAccreditationService(Logger logger,
        @Qualifier("employeeAccreditationMongoDbFacade")
            EmployeeAccreditationRepository employeeAccreditationRepository,
        EmployeeAccreditationFactory employeeAccreditationFactory,
        ACAPYClient acapyClient,
        EmailBuilder emailBuilder,
        UrlBuilder urlBuilder,
        @Qualifier("employeeMongoDbFacade")
            EmployeeRepository employeeRepository) {
        super(logger, employeeAccreditationRepository, employeeAccreditationFactory,
            employeeRepository);
        this.acapyClient = acapyClient;
        this.emailBuilder = emailBuilder;
        this.urlBuilder = urlBuilder;
    }

    @Override
    public EmployeeAccreditation initiateAccreditation(String partyId, String userName)
        throws Exception {
        logger.info(
            "Initiating accreditation with invitation email including qr code for employee with id {}",
            partyId);

        Employee employee =
            partyRepository.findByIdAndCreatedBy(partyId, userName)
                .orElseThrow(NotFoundException::new);

        List<EmployeeAccreditation> existingAccreditation =
            this.accreditationRepository.findAllByPartyId(partyId);
        if (!existingAccreditation.isEmpty()) {
            logger.warn("Accreditation already exists, returning existing one at index 0");
            return existingAccreditation.get(0);
        }

        EmployeeAccreditation accreditation =
            ((EmployeeAccreditationFactory) this.factory).create(employee, userName);
        accreditation = this.accreditationRepository.save(accreditation);

        ConnectionInvitation connectionInvitation =
            this.acapyClient.createConnectionInvitation(accreditation.getId());
        String connectionUrl = connectionInvitation.getInvitationUrl();

        byte[] connectionQrCodePng = QrCodeGenerator.generateQrCodePng(connectionUrl, qrSize, qrSize);
        String connectionQrCodeSvg = QrCodeGenerator.generateQrCodeSvg(connectionUrl, qrSize, qrSize);

        String connectionQrCodeSvgBase64encoded = Base64.getEncoder().encodeToString(connectionQrCodeSvg.getBytes(StandardCharsets.UTF_8));
        String connectionQrCodePngBase64encoded = Base64.getEncoder().encodeToString(connectionQrCodePng);

        String invitationEmail =
            this.emailBuilder.buildEmployeeInvitationEmail(accreditation.getParty(),
                    connectionQrCodeSvgBase64encoded, connectionQrCodePngBase64encoded, qrSize);

        accreditation.initiateAccreditation(connectionUrl, invitationEmail, connectionQrCodeSvg,
            connectionInvitation.getConnectionId());
        accreditation = this.accreditationRepository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {} with invitation url {}, invitation"
                + " email {} and invitation QR code {}",
            accreditation.getId(), accreditation.getStatus(), accreditation.getInvitationUrl(),
            accreditation.getInvitationEmail(), accreditation.getInvitationQrCode());
        return accreditation;
    }

    @Override
    public byte[] generateAccreditationWithEmailAsMessage(String accreditationId) throws Exception {
        EmployeeAccreditation accreditation =
            this.accreditationRepository.findById(accreditationId)
                .orElseThrow(NotFoundException::new);

        List<String> emailAddresses =
            accreditation.getParty().getCredentialOffer().getCredential().getContactInformation()
                .getEmails();
        String email = accreditation.getInvitationEmail();
        return this.emailBuilder
            .buildInvitationEmailAsMessage(emailAddresses, "Invitation for Employee Credential",
                email);
    }

    @Override
    public EmployeeAccreditation offerAccreditation(String accreditationId) throws Exception {
        EmployeeAccreditation accreditation = this.accreditationRepository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);
        logger.info("Offer accreditation with id {}", accreditationId);

        switch (accreditation.getStatus()) {
            case REVOKED:
            case ACCEPTED:
            case PENDING:
                throw new AlreadyExistsException(
                    "Accreditation either pending, revoked or already accepted");
            case OPEN:
            case CANCELLED:
                break;
        }

        CredentialOffer employeeCredentialOffer =
            accreditation.getParty().getCredentialOffer();

        String connectionId =
            accreditation.getEmployeeCredentialIssuanceCorrelation().getConnectionId();

        Correlation correlation =
            acapyClient.issueCredential(employeeCredentialOffer, connectionId);

        accreditation.offerAccreditation(correlation);
        accreditation = this.accreditationRepository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {}", accreditation.getId(),
            accreditation.getStatus());
        return accreditation;
    }

    @Override
    public EmployeeAccreditation completeAccreditation(String connectionId,
        String credentialExchangeId, String issuedBy) throws NotFoundException, Exception {
        logger.info("Complete accreditation for connectionId {} and credentialExchangeId {}",
            connectionId, credentialExchangeId);

        Correlation correlation = acapyClient.getRevocationCorrelation(credentialExchangeId);

        EmployeeAccreditation accreditation =
            ((EmployeeAccreditationRepository) this.accreditationRepository)
                .findByEmployeeCredentialIssuanceCorrelationConnectionId(
                    connectionId).orElseThrow(NotFoundException::new);

        accreditation.completeAccreditation(correlation, issuedBy);
        accreditation.deletePersonalData();
        accreditation = this.accreditationRepository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {}", accreditation.getId(),
            accreditation.getStatus());
        return accreditation;
    }

    @Override
    public EmployeeAccreditation revokeAccreditation(String accreditationId)
        throws NotFoundException, Exception {
        logger.info("Revoke accreditation with id {}", accreditationId);

        EmployeeAccreditation accreditation = this.accreditationRepository
            .findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        String credentialRevocationRegistryId =
            accreditation.getEmployeeCredentialIssuanceCorrelation()
                .getCredentialRevocationRegistryId();
        String credentialRevocationId =
            accreditation.getEmployeeCredentialIssuanceCorrelation().getCredentialRevocationId();

        acapyClient.revokeCredential(credentialRevocationRegistryId, credentialRevocationId);

        /* ToDo - Discuss if accreditation totally or partly must be removed/ blackened, if so implement
            in domain revokeAccreditation() method */
        accreditation.revokeAccreditation();
        accreditation = this.accreditationRepository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {}", accreditation.getId(),
            accreditation.getStatus());
        return accreditation;
    }

    @Override
    public boolean isAccreditationCompleted(String accreditationId) throws Exception {
        EmployeeAccreditation accreditation = this.accreditationRepository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        return new AccreditationCompletedSpecification().isSatisfiedBy(accreditation);
    }
}
