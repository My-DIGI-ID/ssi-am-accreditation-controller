package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.security;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security.GuestAccessTokenMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestAccessTokenMongoDbRepository extends
    MongoRepository<GuestAccessTokenMongoDbDocument, String> {

    void deleteByAccreditationId(String accreditationId);
}
