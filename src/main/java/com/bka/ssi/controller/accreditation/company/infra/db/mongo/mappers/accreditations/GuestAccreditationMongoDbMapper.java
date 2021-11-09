package com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.GuestAccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties.GuestMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations.InvitationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CorrelationMongoDbDocument;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GuestAccreditationMongoDbMapper {

    private final Logger logger;
    private final GuestMongoDbFacade guestMongoDbFacade;

    public GuestAccreditationMongoDbMapper(Logger logger,
        GuestMongoDbFacade guestMongoDbFacade) {
        this.logger = logger;
        this.guestMongoDbFacade = guestMongoDbFacade;
    }

    public GuestAccreditationMongoDbDocument entityToDocument(
        GuestAccreditation guestAccreditation) {
        logger.debug("Mapping GuestAccreditation to MongoDb document");

        if (guestAccreditation == null) {
            return null;
        } else {
            GuestAccreditationMongoDbDocument document = new GuestAccreditationMongoDbDocument();

            InvitationMongoDbValue invitationMongoDbValue = new InvitationMongoDbValue();
            invitationMongoDbValue.setInvitationUrl(guestAccreditation.getInvitationUrl());
            invitationMongoDbValue.setInvitationEmail(guestAccreditation.getInvitationEmail());
            invitationMongoDbValue.setInvitationQrCode(guestAccreditation.getInvitationQrCode());

            document.setPartyId(guestAccreditation.getParty().getId());
            document.setId(guestAccreditation.getId());
            document.setInvitation(invitationMongoDbValue);
            document.setStatus(guestAccreditation.getStatus().getName());
            document.setInvitedBy(guestAccreditation.getInvitedBy());
            document.setInvitedAt(guestAccreditation.getInvitedAt());

            CorrelationMongoDbDocument basisIdVerificationCorrelationMongoDbDocument =
                new CorrelationMongoDbDocument();

            if (guestAccreditation.getBasisIdVerificationCorrelation() != null) {
                basisIdVerificationCorrelationMongoDbDocument.setConnectionId(
                    guestAccreditation.getBasisIdVerificationCorrelation().getConnectionId());
                basisIdVerificationCorrelationMongoDbDocument
                    .setThreadId(
                        guestAccreditation.getBasisIdVerificationCorrelation().getThreadId());
                basisIdVerificationCorrelationMongoDbDocument.setPresentationExchangeId(
                    guestAccreditation.getBasisIdVerificationCorrelation()
                        .getPresentationExchangeId());
                basisIdVerificationCorrelationMongoDbDocument.setCredentialRevocationRegistryId(
                    guestAccreditation.getBasisIdVerificationCorrelation()
                        .getCredentialRevocationRegistryId());
                basisIdVerificationCorrelationMongoDbDocument.setCredentialRevocationId(
                    guestAccreditation.getBasisIdVerificationCorrelation()
                        .getCredentialRevocationId());
            }

            CorrelationMongoDbDocument guestCredentialIssuanceCorrelationMongoDbDocument =
                new CorrelationMongoDbDocument();

            if (guestAccreditation.getGuestCredentialIssuanceCorrelation() != null) {
                guestCredentialIssuanceCorrelationMongoDbDocument.setConnectionId(
                    guestAccreditation.getGuestCredentialIssuanceCorrelation().getConnectionId());
                guestCredentialIssuanceCorrelationMongoDbDocument
                    .setThreadId(
                        guestAccreditation.getGuestCredentialIssuanceCorrelation().getThreadId());
                guestCredentialIssuanceCorrelationMongoDbDocument.setPresentationExchangeId(
                    guestAccreditation.getGuestCredentialIssuanceCorrelation()
                        .getPresentationExchangeId());
                guestCredentialIssuanceCorrelationMongoDbDocument
                    .setCredentialRevocationRegistryId(
                        guestAccreditation.getGuestCredentialIssuanceCorrelation()
                            .getCredentialRevocationRegistryId());
                guestCredentialIssuanceCorrelationMongoDbDocument.setCredentialRevocationId(
                    guestAccreditation.getGuestCredentialIssuanceCorrelation()
                        .getCredentialRevocationId());
            }

            document
                .setBasisIdVerificationCorrelation(basisIdVerificationCorrelationMongoDbDocument);
            document.setGuestCredentialIssuanceCorrelation(
                guestCredentialIssuanceCorrelationMongoDbDocument);

            return document;
        }
    }

    public GuestAccreditation documentToEntity(
        GuestAccreditationMongoDbDocument document)
        throws InvalidValidityTimeframeException {
        logger.debug("Mapping GuestAccreditationMongoDbDocument to domain entity");

        if (document == null) {
            return null;
        } else {
            String guestId = document.getPartyId();
            /**
             * Technical Debt.: ToDo: WARNING Tech Debt: Move DB fetch out of mapper, should be
             *                      done in the service (custom method in facade).
             *                      Further handle Optional not present.
             */
            Guest guest = guestMongoDbFacade.findById(guestId).get();

            Correlation basisIdVerification;

            if (document.getBasisIdVerificationCorrelation() != null) {
                basisIdVerification = new Correlation(
                    document.getBasisIdVerificationCorrelation()
                        .getConnectionId(),
                    document.getBasisIdVerificationCorrelation()
                        .getThreadId(),
                    document.getBasisIdVerificationCorrelation()
                        .getPresentationExchangeId(),
                    document.getBasisIdVerificationCorrelation()
                        .getCredentialRevocationRegistryId(),
                    document.getBasisIdVerificationCorrelation()
                        .getCredentialRevocationId()
                );
            } else {
                basisIdVerification = new Correlation();
            }

            Correlation guestCredentialIssuance;

            if (document.getGuestCredentialIssuanceCorrelation() != null) {
                guestCredentialIssuance = new Correlation(
                    document.getGuestCredentialIssuanceCorrelation()
                        .getConnectionId(),
                    document.getGuestCredentialIssuanceCorrelation()
                        .getThreadId(),
                    document.getGuestCredentialIssuanceCorrelation()
                        .getPresentationExchangeId(),
                    document.getGuestCredentialIssuanceCorrelation()
                        .getCredentialRevocationRegistryId(),
                    document.getGuestCredentialIssuanceCorrelation()
                        .getCredentialRevocationId()
                );
            } else {
                guestCredentialIssuance = new Correlation();
            }

            GuestAccreditation accreditation = new GuestAccreditation(
                document.getId(),
                guest,
                GuestAccreditationStatus.valueOf(document.getStatus()),
                document.getInvitedBy(),
                document.getInvitedAt(),
                document.getInvitation().getInvitationUrl(),
                document.getInvitation().getInvitationEmail(),
                document.getInvitation().getInvitationQrCode(),
                basisIdVerification,
                guestCredentialIssuance);

            return accreditation;
        }
    }
}
