package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.GuestMongoDbDocument;
import org.springframework.data.repository.CrudRepository;

public interface GuestMongoDbRepository extends CrudRepository<GuestMongoDbDocument, String> {
}
