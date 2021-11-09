package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.PartyMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.EmployeeCredentialMongoDbValue;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
public class EmployeeMongoDbDocument extends PartyMongoDbDocument<EmployeeCredentialMongoDbValue> {

    public EmployeeMongoDbDocument() {
    }
}
