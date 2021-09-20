package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.EmployeeMongoDbDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMongoDbRepository extends CrudRepository<EmployeeMongoDbDocument, String> {
}
