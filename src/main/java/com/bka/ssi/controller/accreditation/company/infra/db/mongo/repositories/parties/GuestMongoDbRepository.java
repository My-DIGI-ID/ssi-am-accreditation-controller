package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.GuestMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestMongoDbRepository extends MongoRepository<GuestMongoDbDocument, String> {

    @Query(value =
        "{'credentialOffer.credential.referenceBasisId': ?0, " +
            "'credentialOffer.credential.persona.firstName': ?1, " +
            "'credentialOffer.credential.persona.lastName': ?2, " +
            "'credentialOffer.credential.guestPrivateInformation.dateOfBirth': ?3, " +
            "'credentialOffer.credential.companyName': ?4, " +
            "'credentialOffer.credential.validityTimeframe.validFrom': ?5, " +
            "'credentialOffer.credential.validityTimeframe.validUntil.': ?6, " +
            "'credentialOffer.credential.invitedBy': ?7}")
    Optional<GuestMongoDbDocument> findByPartyParams(
        String referenceBasisId,
        String firstName, String lastName, String dateOfBirth, String companyName,
        ZonedDateTime validFrom, ZonedDateTime validUntil, String invitedBy);

    Optional<GuestMongoDbDocument> findByIdAndCreatedBy(String id, String createdBy);

    List<GuestMongoDbDocument> findAllByCreatedBy(String createdBy);

    List<GuestMongoDbDocument> findAllByCredentialOfferCredentialInvitedBy(String invitedBy);
}
