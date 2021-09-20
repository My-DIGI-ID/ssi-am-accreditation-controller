package com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.GuestAccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties.GuestMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations.GuestInvitationDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CorrelationMongoDbDocument;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuestAccreditationMongoDbMapper {

    @Autowired
    private Logger logger;

    @Autowired
    private GuestMongoDbFacade guestMongoDbFacade;

    public GuestAccreditationMongoDbDocument guestAccreditationToGuestAccreditationMongoDBDocument(
        GuestAccreditation guestAccreditation) {
        logger.debug("Mapping GuestAccreditation to MongoDb document");
        if (guestAccreditation == null) {
            return null;
        } else {
            GuestAccreditationMongoDbDocument document = new GuestAccreditationMongoDbDocument();

            // Rename to GuestInvitationMongoDbValue
            GuestInvitationDbValue invitationDbValue = new GuestInvitationDbValue();
            invitationDbValue.setInvitationEmail(guestAccreditation.getInvitationEmail());
            invitationDbValue.setInvitationLink(guestAccreditation.getInvitationLink());
            invitationDbValue.setConnectionQrCode(guestAccreditation.getConnectionQrCode());

            document.setPartyId(guestAccreditation.getParty().getId());
            document.setId(guestAccreditation.getId());
            document.setGuestInvitation(invitationDbValue);
            document.setAccreditationStatus(guestAccreditation.getStatus());

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
            }

            document
                .setBasisIdVerificationCorrelation(basisIdVerificationCorrelationMongoDbDocument);
            document.setGuestCredentialIssuanceCorrelation(
                guestCredentialIssuanceCorrelationMongoDbDocument);

            return document;
        }
    }

    public GuestAccreditation guestAccreditationMongoDBDocumentToGuestAccreditation(
        GuestAccreditationMongoDbDocument guestAccreditationMongoDbDocument) {
        logger.debug("Mapping GuestAccreditationMongoDbDocument to domain entity");

        if (guestAccreditationMongoDbDocument == null) {
            return null;
        } else {
            String guestId = guestAccreditationMongoDbDocument.getPartyId();
            //!! ToDo: WARNING Tech Debt: Move DB fetch out of mapper, should be done in the
            //  service (custom method in facade)
            Guest guest = guestMongoDbFacade.findById(guestId).get();

            Correlation basisIdVerification;

            if (guestAccreditationMongoDbDocument.getBasisIdVerificationCorrelation() != null) {
                basisIdVerification = new Correlation(
                    guestAccreditationMongoDbDocument.getBasisIdVerificationCorrelation()
                        .getConnectionId(),
                    guestAccreditationMongoDbDocument.getBasisIdVerificationCorrelation()
                        .getThreadId(),
                    guestAccreditationMongoDbDocument.getBasisIdVerificationCorrelation()
                        .getPresentationExchangeId()
                );
            } else {
                basisIdVerification = new Correlation();
            }

            Correlation guestCredentialIssuance;

            if (guestAccreditationMongoDbDocument.getGuestCredentialIssuanceCorrelation() != null) {
                guestCredentialIssuance = new Correlation(
                    guestAccreditationMongoDbDocument.getGuestCredentialIssuanceCorrelation()
                        .getConnectionId(),
                    guestAccreditationMongoDbDocument.getGuestCredentialIssuanceCorrelation()
                        .getThreadId(),
                    guestAccreditationMongoDbDocument.getGuestCredentialIssuanceCorrelation()
                        .getPresentationExchangeId()
                );
            } else {
                guestCredentialIssuance = new Correlation();
            }

            GuestAccreditation accreditation = new GuestAccreditation(
                guestAccreditationMongoDbDocument.getId(),
                guest,
                guestAccreditationMongoDbDocument.getAccreditationStatus(),
                basisIdVerification,
                guestCredentialIssuance,
                guestAccreditationMongoDbDocument.getGuestInvitation().getInvitationEmail(),
                guestAccreditationMongoDbDocument.getGuestInvitation().getInvitationLink());

            accreditation.associateConnectionQrCodeWithAccreditation(
                guestAccreditationMongoDbDocument.getGuestInvitation().getConnectionQrCode());
            return accreditation;
        }
    }
}
