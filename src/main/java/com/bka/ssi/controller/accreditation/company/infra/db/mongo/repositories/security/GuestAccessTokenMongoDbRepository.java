package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.security;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security.GuestAccessTokenMongoDbDocument;
import org.springframework.data.repository.CrudRepository;

public interface GuestAccessTokenMongoDbRepository extends
    CrudRepository<GuestAccessTokenMongoDbDocument, String> {
}
