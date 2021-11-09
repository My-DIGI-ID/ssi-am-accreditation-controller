package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.AccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CorrelationMongoDbDocument;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "employeeAccreditations")
public class EmployeeAccreditationMongoDbDocument extends AccreditationMongoDbDocument {

    @Field("employeeCredentialIssuanceCorrelation")
    private CorrelationMongoDbDocument employeeCredentialIssuanceCorrelation;

    public EmployeeAccreditationMongoDbDocument() {
    }

    public CorrelationMongoDbDocument getEmployeeCredentialIssuanceCorrelation() {
        return employeeCredentialIssuanceCorrelation;
    }

    public void setEmployeeCredentialIssuanceCorrelation(
        CorrelationMongoDbDocument employeeCredentialIssuanceCorrelation) {
        this.employeeCredentialIssuanceCorrelation = employeeCredentialIssuanceCorrelation;
    }
}
