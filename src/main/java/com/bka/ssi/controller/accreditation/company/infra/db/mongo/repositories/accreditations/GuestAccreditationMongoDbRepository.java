package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.GuestAccreditationMongoDbDocument;
import org.springframework.data.repository.CrudRepository;

public interface GuestAccreditationMongoDbRepository
    extends CrudRepository<GuestAccreditationMongoDbDocument, String> {
}
