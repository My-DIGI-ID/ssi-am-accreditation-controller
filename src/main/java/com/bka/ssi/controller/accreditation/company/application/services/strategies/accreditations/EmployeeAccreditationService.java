package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;

import com.bka.ssi.controller.accreditation.company.application.agent.ACAPYClient;
import com.bka.ssi.controller.accreditation.company.application.exceptions.AlreadyExistsException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.accreditations.EmployeeAccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.EmployeeAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.services.AccreditationService;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.parties.EmployeePartyService;
import com.bka.ssi.controller.accreditation.company.application.utilities.EmailBuilder;
import com.bka.ssi.controller.accreditation.company.application.utilities.QrCodeGenerator;
import com.bka.ssi.controller.accreditation.company.application.utilities.UrlBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations.AccreditationCompletedSpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeAccreditationService
    extends AccreditationService<EmployeeAccreditation> {

    @Value("${accreditation.employee.connection.qr.size}")
    private int qrSize;

    private final ACAPYClient acapyClient;
    private final EmailBuilder emailBuilder;
    private final UrlBuilder urlBuilder;
    private final QrCodeGenerator qrCodeGenerator;
    private final EmployeePartyService employeePartyService;

    public EmployeeAccreditationService(Logger logger,
        @Qualifier("employeeAccreditationMongoDbFacade")
            EmployeeAccreditationRepository employeeAccreditationRepository,
        EmployeeAccreditationFactory employeeAccreditationFactory,
        ACAPYClient acapyClient,
        EmailBuilder emailBuilder,
        UrlBuilder urlBuilder,
        QrCodeGenerator qrCodeGenerator,
        EmployeePartyService employeePartyService) {
        super(logger, employeeAccreditationRepository, employeeAccreditationFactory);
        this.acapyClient = acapyClient;
        this.emailBuilder = emailBuilder;
        this.urlBuilder = urlBuilder;
        this.qrCodeGenerator = qrCodeGenerator;
        this.employeePartyService = employeePartyService;
    }

    @Override
    public EmployeeAccreditation initiateAccreditation(String partyId, String userName)
        throws Exception {
        logger.info(
            "Initiating accreditation with invitation email including qr code for employee with id {}",
            partyId);

        /* Check if an accreditation has been created previously, if so return it */
        List<EmployeeAccreditation> existingAccreditation =
            this.repository.findAllByPartyId(partyId);
        if (!existingAccreditation.isEmpty()) {
            logger.warn("Accreditation already exists, returning existing one at index 0");
            return existingAccreditation.get(0);
        }

        Employee employee = this.employeePartyService.getPartyById(partyId);
        EmployeeAccreditation accreditation =
            ((EmployeeAccreditationFactory) this.factory).create(employee, userName);
        accreditation = this.repository.save(accreditation);

        ConnectionInvitation connectionInvitation =
            this.acapyClient.createConnectionInvitation(accreditation.getId());
        String connectionUrl = connectionInvitation.getInvitationUrl();

        String connectionQrCode =
            this.qrCodeGenerator.generateQrCodeSvg(connectionUrl, qrSize, qrSize);
        String invitationEmail =
            this.emailBuilder.buildEmployeeInvitationEmail(accreditation.getParty(),
                connectionQrCode);

        accreditation.initiateAccreditation(connectionUrl, invitationEmail, connectionQrCode,
            connectionInvitation.getConnectionId());
        accreditation = this.repository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {} with invitation url {}, invitation"
                + " email {} and invitation QR code {}",
            accreditation.getId(), accreditation.getStatus(), accreditation.getInvitationUrl(),
            accreditation.getInvitationEmail(), accreditation.getInvitationQrCode());
        return accreditation;
    }

    @Override
    public EmployeeAccreditation offerAccreditation(String accreditationId) throws Exception {
        EmployeeAccreditation accreditation = this.repository.findById(accreditationId)
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
        accreditation = this.repository.save(accreditation);

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

        EmployeeAccreditation accreditation = ((EmployeeAccreditationRepository) this.repository)
            .findByEmployeeCredentialIssuanceCorrelationConnectionId(
                connectionId).orElseThrow(NotFoundException::new);

        accreditation.completeAccreditation(correlation, issuedBy);
        accreditation.deletePersonalData();
        accreditation = this.repository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {}", accreditation.getId(),
            accreditation.getStatus());
        return accreditation;
    }

    @Override
    public EmployeeAccreditation revokeAccreditation(String accreditationId)
        throws NotFoundException, Exception {
        logger.info("Revoke accreditation with id {}", accreditationId);

        EmployeeAccreditation accreditation = this.repository
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
        accreditation = this.repository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {}", accreditation.getId(),
            accreditation.getStatus());
        return accreditation;
    }

    @Override
    public boolean isAccreditationCompleted(String accreditationId) throws Exception {
        EmployeeAccreditation accreditation = this.repository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        return new AccreditationCompletedSpecification().isSatisfiedBy(accreditation);
    }
}
