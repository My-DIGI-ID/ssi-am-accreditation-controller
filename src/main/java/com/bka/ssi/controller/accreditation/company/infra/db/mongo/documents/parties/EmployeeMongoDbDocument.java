package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialOfferMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.EmployeeCredentialMongoDbValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "employees")
public class EmployeeMongoDbDocument {

    @Id
    private String id;

    @Field("credentialOffer")
    private CredentialOfferMongoDbValue<EmployeeCredentialMongoDbValue> credentialOffer;

    public EmployeeMongoDbDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CredentialOfferMongoDbValue<EmployeeCredentialMongoDbValue> getCredentialOffer() {
        return credentialOffer;
    }

    public void setCredentialOffer(
        CredentialOfferMongoDbValue<EmployeeCredentialMongoDbValue> credentialOffer) {
        this.credentialOffer = credentialOffer;
    }
}
