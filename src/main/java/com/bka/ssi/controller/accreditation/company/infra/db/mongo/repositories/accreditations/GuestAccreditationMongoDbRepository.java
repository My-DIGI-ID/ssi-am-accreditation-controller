package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.GuestAccreditationMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestAccreditationMongoDbRepository
    extends MongoRepository<GuestAccreditationMongoDbDocument, String> {

    Optional<GuestAccreditationMongoDbDocument> findByPartyId(String partyId);

    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationConnectionId(
        String connectionId);

    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationThreadId(
        String threadId);

    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationPresentationExchangeId(
        String presentationExchangeId);

    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
        String connectionId,
        String threadId);

    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadIdAndBasisIdVerificationCorrelationPresentationExchangeId(
        String connectionId,
        String threadId,
        String presentationExchangeId);

    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationConnectionId(
        String connectionId);

    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationThreadId(
        String threadId);

    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String presentationExchangeId);

    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId(
        String connectionId,
        String threadId);

    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadIdAndGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String connectionId,
        String threadId,
        String presentationExchangeId);

    List<GuestAccreditationMongoDbDocument> findAllByStatus(
        String status);

    List<GuestAccreditationMongoDbDocument> findAllByStatusIsNot(
        String status);

    long countByStatus(String status);

    long countByStatusIsNot(String status);

    List<GuestAccreditationMongoDbDocument> findAllByPartyId(String partyId);

    Optional<GuestAccreditationMongoDbDocument> findByIdAndInvitedBy(String id, String invitedBy);

    List<GuestAccreditationMongoDbDocument> findAllByInvitedBy(String invitedBy);

    List<GuestAccreditationMongoDbDocument> findAllByInvitedByAndStatusIsNot(
        String invitedBy, List<String> firstStatus);
}
