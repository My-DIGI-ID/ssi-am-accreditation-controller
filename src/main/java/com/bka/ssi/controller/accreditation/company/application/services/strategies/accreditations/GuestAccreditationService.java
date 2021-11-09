package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;

import com.bka.ssi.controller.accreditation.company.application.agent.ACAPYClient;
import com.bka.ssi.controller.accreditation.company.application.exceptions.AlreadyExistsException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidAccreditationStateChangeException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
import com.bka.ssi.controller.accreditation.company.application.factories.accreditations.GuestAccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.GuestAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.services.AccreditationService;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations.GuestAccreditationPrivateInfoInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.parties.GuestPartyService;
import com.bka.ssi.controller.accreditation.company.application.utilities.BasisIdPresentationUtility;
import com.bka.ssi.controller.accreditation.company.application.utilities.EmailBuilder;
import com.bka.ssi.controller.accreditation.company.application.utilities.QrCodeGenerator;
import com.bka.ssi.controller.accreditation.company.application.utilities.UrlBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidAccreditationInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidGuestInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations.AccreditationBasisIdValiditySpecification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations.AccreditationCompletedSpecification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.parties.GuestValidityTimeframeTodaySpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.BasisIdPresentation;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GuestAccreditationService extends AccreditationService<GuestAccreditation> {

    /* Enable testing of mock BasisId which will always result to false validity by design */
    @Value("${guest.basisIdVerificationDevMode:false}")
    private boolean basisIdVerificationDevMode;

    @Value("${accreditation.guest.connection.qr.size}")
    private int qrSize;

    private final GuestPartyService guestPartyService;
    private final AuthenticationService authenticationService;
    private final ACAPYClient acapyClient;
    private final EmailBuilder emailBuilder;
    private final UrlBuilder urlBuilder;
    private final QrCodeGenerator qrCodeGenerator;
    private final BasisIdPresentationUtility basisIdPresentationUtility;

    public GuestAccreditationService(
        @Qualifier("guestAccreditationMongoDbFacade")
            GuestAccreditationRepository guestAccreditationRepository,
        GuestAccreditationFactory guestAccreditationFactory, GuestPartyService guestPartyService,
        Logger logger, EmailBuilder emailBuilder, QrCodeGenerator qrCodeGenerator,
        UrlBuilder urlBuilder, ACAPYClient acapyClient,
        AuthenticationService authenticationService,
        BasisIdPresentationUtility basisIdPresentationUtility) {
        super(logger, guestAccreditationRepository, guestAccreditationFactory);
        this.emailBuilder = emailBuilder;
        this.acapyClient = acapyClient;
        this.qrCodeGenerator = qrCodeGenerator;
        this.urlBuilder = urlBuilder;
        this.guestPartyService = guestPartyService;
        this.authenticationService = authenticationService;
        this.basisIdPresentationUtility = basisIdPresentationUtility;
    }

    @Override
    public GuestAccreditation initiateAccreditation(String partyId,
        String userName) throws NotFoundException, InvalidGuestInitialStateException,
        InvalidAccreditationInitialStateException, InvalidValidityTimeframeException, Exception {
        logger.info("Initiating accreditation with invitation email for guest with id {}", partyId);

        // Check if an accreditation has been created previously, if so return it
        List<GuestAccreditation> existingAccreditation =
            this.repository.findAllByPartyId(partyId);
        if (!existingAccreditation.isEmpty()) {
            logger.warn("Accreditation already exists, returning existing one at index 0");
            return existingAccreditation.get(0);
        }

        Guest guest = guestPartyService.getPartyById(partyId, userName);

        if (!new GuestValidityTimeframeTodaySpecification().isSatisfiedBy(guest)) {
            throw new InvalidValidityTimeframeException(
                "ValidityTimeframe < today for guest with id " + guest.getId());
        }

        guest.addInformationAboutInvitingPerson(userName);

        GuestAccreditation newAccreditation =
            ((GuestAccreditationFactory) factory).create(guest, userName);

        // Interim save in order to generate accreditationId
        GuestAccreditation accreditation = this.repository.save(newAccreditation);

        String redirectUrl = urlBuilder.buildGuestInvitationRedirectUrl(accreditation.getId());

        String invitationEmail = this.emailBuilder.buildGuestInvitationEmail(guest, redirectUrl);

        accreditation.initiateAccreditationWithInvitationUrlAndInvitationEmail(
            redirectUrl, invitationEmail);

        GuestAccreditation updatedAccreditation = this.repository.save(accreditation);

        logger.debug(
            "Accreditation with id {} is in status {} with redirectUrl {} and invitation email {}",
            updatedAccreditation.getId(), updatedAccreditation.getStatus(),
            updatedAccreditation.getInvitationUrl(), updatedAccreditation.getInvitationEmail());
        return updatedAccreditation;
    }

    @Override
    public GuestAccreditation proceedWithAccreditation(String accreditationId)
        throws NotFoundException, Exception {
        logger.info("Proceeding accreditation with qr code for accreditation with id {}",
            accreditationId);

        GuestAccreditation accreditation = this.repository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        ConnectionInvitation connectionInvitation =
            acapyClient.createConnectionInvitation(accreditationId);
        String connectionUrl = connectionInvitation.getInvitationUrl();

        String connectionQrCode = qrCodeGenerator.generateQrCodeSvg(connectionUrl, qrSize, qrSize);

        accreditation = accreditation
            .associateInvitationQrCodeWithAccreditation(connectionQrCode);

        GuestAccreditation savedAccreditation = this.repository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {} with invitation url {}",
            savedAccreditation.getId(), savedAccreditation.getStatus(), connectionUrl);
        return savedAccreditation;
    }

    public void verifyBasisId(String accreditationId, String connectionId)
        throws NotFoundException, Exception {
        logger.info("Verify basis id for accreditation with id {}", accreditationId);

        GuestAccreditation accreditation = this.repository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        Correlation correlation = acapyClient.verifyBasisId(connectionId);

        accreditation.startBasisIdVerification(correlation);

        logger.debug("Accreditation with id {} is in status {}",
            accreditation.getId(), accreditation.getStatus());
        this.repository.save(accreditation);
    }

    public void completeVerificationOfBasisId(String connectionId, String threadId,
        String presentationExchangeId, String verified) throws NotFoundException, Exception {

        boolean basisIdVerified = verified != null && Boolean.parseBoolean(verified);

        GuestAccreditation accreditation = ((GuestAccreditationRepository) this.repository)
            .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                connectionId,
                threadId)
            .orElseThrow(NotFoundException::new);

        logger.info("Getting basisId verification status for accreditation with id {}",
            accreditation.getId());
        if (basisIdVerificationDevMode) {
            logger.warn("Fallback to basisId verification dev mode");
            basisIdVerified = true;
        } else {
            logger.debug("Proceeding with basisId verification in production mode");
        }

        if (basisIdVerified) {
            BasisIdPresentation presentation =
                acapyClient.getBasisIdPresentation(presentationExchangeId);

            Guest guest = accreditation.getParty();

            if (this.basisIdPresentationUtility
                .isPresentationAndGuestMatchLoosely(presentation, guest)) {
                logger.debug("Updating accreditation with BasisId data");
                Correlation correlation = new Correlation(
                    connectionId,
                    threadId,
                    presentationExchangeId
                );

                String dateOfBirth = presentation.getDateOfBirth();

                accreditation.completeBasisIdVerification(correlation, dateOfBirth);

                logger
                    .info("BasisId valid for accreditation with id {} and status {}",
                        accreditation.getId(), accreditation.getStatus());
            } else {
                accreditation.deferAccreditationDueToInvalidBasisId();
                logger.warn("BasisId invalid for accreditation with id {} and status {}",
                    accreditation.getId(), accreditation.getStatus());
            }
        } else {
            accreditation.deferAccreditationDueToInvalidBasisId();
            logger.warn("BasisId invalid for accreditation with id {} and status {}",
                accreditation.getId(), accreditation.getStatus());
        }

        this.repository.save(accreditation);
    }

    public GuestAccreditation appendWithProprietaryInformationFromGuest(String accreditationId,
        GuestAccreditationPrivateInfoInputDto inputDto) throws Exception {

        GuestAccreditation accreditation =
            this.repository.findById(accreditationId).orElseThrow(NotFoundException::new);

        logger.info("Appending guest accreditation with id {} with private information",
            accreditation.getId());
        accreditation.addPrivateInformationByTheGuest(
            inputDto.getLicencePlateNumber(),
            inputDto.getCompanyStreet(),
            inputDto.getCompanyCity(),
            inputDto.getCompanyPostCode(),
            inputDto.getAcceptedDocument(),
            inputDto.getPrimaryPhoneNumber(),
            inputDto.getSecondaryPhoneNumber()
        );

        GuestAccreditation savedAccreditation = this.repository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {}", savedAccreditation.getId(),
            savedAccreditation.getStatus());
        return savedAccreditation;
    }

    @Override
    public GuestAccreditation offerAccreditation(String accreditationId)
        throws NotFoundException, Exception {
        GuestAccreditation accreditation = this.repository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);
        logger.info("Offer accreditation with id {}", accreditationId);

        switch (accreditation.getStatus()) {
            case REVOKED:
            case ACCEPTED:
            case PENDING:
                throw new AlreadyExistsException(
                    "Accreditation either pending, revoked or already accepted");
            case OPEN:
            case BASIS_ID_INVALID:
            case BASIS_ID_VERIFICATION_PENDING:
                throw new InvalidAccreditationStateChangeException(
                    "Cannot offer accreditation when basis id verification is open, pending or failed.");
            case CANCELLED:
            case BASIS_ID_VALID:
                break;
        }

        CredentialOffer guestCredentialOffer =
            accreditation.getParty().getCredentialOffer();

        String connectionId = accreditation.getBasisIdVerificationCorrelation().getConnectionId();

        Correlation correlation = acapyClient.issueCredential(guestCredentialOffer, connectionId);

        accreditation.offerAccreditation(correlation);
        accreditation = this.repository.save(accreditation);

        logger.debug("Accreditation with id {} is in status {}", accreditation.getId(),
            accreditation.getStatus());
        return accreditation;
    }

    @Override
    public GuestAccreditation completeAccreditation(String connectionId,
        String credentialExchangeId, String issuedBy) throws NotFoundException, Exception {
        logger.info("Complete accreditation for connectionId {} and credentialExchangeId {}",
            connectionId, credentialExchangeId);

        Correlation correlation = acapyClient.getRevocationCorrelation(credentialExchangeId);

        GuestAccreditation accreditation = ((GuestAccreditationRepository) this.repository)
            .findByGuestCredentialIssuanceCorrelationConnectionId(connectionId)
            .orElseThrow(NotFoundException::new);

        accreditation.completeAccreditation(correlation, issuedBy);

        accreditation = this.repository.save(accreditation);
        this.authenticationService.invalidateGuestAccessToken(accreditation.getId());

        logger.debug("Accreditation with id {} is in status {}", accreditation.getId(),
            accreditation.getStatus());
        return accreditation;
    }


    public boolean isGuestBasisIdValidationCompleted(String accreditationId) throws Exception {
        GuestAccreditation accreditation = this.repository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        if (accreditation.getStatus() == GuestAccreditationStatus.BASIS_ID_INVALID) {
            throw new UnauthorizedException();
        }

        return new AccreditationBasisIdValiditySpecification().isSatisfiedBy(accreditation);
    }

    @Override
    public boolean isAccreditationCompleted(String accreditationId) throws Exception {
        GuestAccreditation accreditation = this.repository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        return new AccreditationCompletedSpecification().isSatisfiedBy(accreditation);
    }

    public GuestAccreditation getUniqueAccreditationByPartyParams(
        String referenceBasisId, String firstName, String lastName, String dateOfBirth,
        String companyName, ZonedDateTime validFrom, ZonedDateTime validUntil, String invitedBy)
        throws Exception {

        GuestAccreditation accreditation =
            ((GuestAccreditationRepository) this.repository).findByPartyParams(
                referenceBasisId,
                firstName,
                lastName,
                dateOfBirth,
                companyName,
                validFrom,
                validUntil,
                invitedBy
            ).orElseThrow(NotFoundException::new);

        return accreditation;
    }

    public GuestAccreditation cleanGuestInformationOnCheckout(String accreditationId)
        throws Exception {
        logger.info("Clean information from accreditation with id {}", accreditationId);

        GuestAccreditation accreditation = this.repository.findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        GuestAccreditation cleanedAccreditation = accreditation.cleanGuestInformationOnCheckout();
        GuestAccreditation savedAccreditation = this.repository.save(cleanedAccreditation);

        logger.debug("Accreditation with id {} is in status {}", savedAccreditation.getId(),
            savedAccreditation.getStatus());
        return savedAccreditation;
    }

    public Map<GuestAccreditationStatus, List<GuestAccreditation>> getAllAccreditationsGroupedByStatus()
        throws Exception {
        Map<GuestAccreditationStatus, List<GuestAccreditation>>
            accreditationsGroupedByStatus = new HashMap<>();

        for (GuestAccreditationStatus status : GuestAccreditationStatus.values()) {
            accreditationsGroupedByStatus.put(status, this.repository.findAllByStatus(status));
        }

        return accreditationsGroupedByStatus;
    }

    public Map<GuestAccreditationStatus, Long> countOfAccreditationsGroupedByStatus() {
        Map<GuestAccreditationStatus, Long>
            countOfAccreditationsGroupedByStatus = new HashMap<>();

        for (GuestAccreditationStatus status : GuestAccreditationStatus.values()) {
            countOfAccreditationsGroupedByStatus.put(status, this.repository.countByStatus(status));
        }

        return countOfAccreditationsGroupedByStatus;
    }

    @Override
    public GuestAccreditation revokeAccreditation(String accreditationId)
        throws NotFoundException, Exception {
        logger.info("Revoke accreditation with id {}", accreditationId);

        GuestAccreditation accreditation = this.repository
            .findById(accreditationId)
            .orElseThrow(NotFoundException::new);

        String credentialRevocationRegistryId =
            accreditation.getGuestCredentialIssuanceCorrelation()
                .getCredentialRevocationRegistryId();
        String credentialRevocationId =
            accreditation.getGuestCredentialIssuanceCorrelation().getCredentialRevocationId();

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
    public List<GuestAccreditation> getAllAccreditations(String userName) throws Exception {
        /* ToDo - clarify if CANCELLED or BASIS_ID_INVALID should be excluded */
        return this.repository.findAllByInvitedByAndValidStatus(userName,
            Arrays.asList(GuestAccreditationStatus.REVOKED));
    }
}
