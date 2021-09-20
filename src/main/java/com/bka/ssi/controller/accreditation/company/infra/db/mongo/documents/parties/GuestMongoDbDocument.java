package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialOfferMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.GuestCredentialMongoDbValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "guests")
public class GuestMongoDbDocument {

    @Id
    private String id;

    @Field("credentialOffer")
    private CredentialOfferMongoDbValue<GuestCredentialMongoDbValue> credentialOffer;

    public GuestMongoDbDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CredentialOfferMongoDbValue<GuestCredentialMongoDbValue> getCredentialOffer() {
        return credentialOffer;
    }

    public void setCredentialOffer(
        CredentialOfferMongoDbValue<GuestCredentialMongoDbValue> credentialOffer) {
        this.credentialOffer = credentialOffer;
    }
}
