package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.EmployeeMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeMongoDbRepository extends
    MongoRepository<EmployeeMongoDbDocument, String> {

    Optional<EmployeeMongoDbDocument> findByIdAndCreatedBy(String id, String createdBy);

    List<EmployeeMongoDbDocument> findAllByCreatedBy(String createdBy);
}
