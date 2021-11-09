package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.EmployeeAccreditationMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeAccreditationMongoDbRepository
    extends MongoRepository<EmployeeAccreditationMongoDbDocument, String> {

    List<EmployeeAccreditationMongoDbDocument> findAllByPartyId(String partyId);

    Optional<EmployeeAccreditationMongoDbDocument> findByPartyId(String partyId);

    Optional<EmployeeAccreditationMongoDbDocument> findByIdAndInvitedBy(String id,
        String invitedBy);

    List<EmployeeAccreditationMongoDbDocument> findAllByInvitedBy(String invitedBy);

    Optional<EmployeeAccreditationMongoDbDocument> findByEmployeeCredentialIssuanceCorrelationConnectionId(
        String connectionId);
}
