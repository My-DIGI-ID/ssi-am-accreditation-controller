package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.abstractions.CredentialMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialOfferMongoDbValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

public abstract class PartyMongoDbDocument<T extends CredentialMongoDbValue> {

    @Id
    private String id;

    @Field("credentialOffer")
    private CredentialOfferMongoDbValue<T> credentialOffer;

    @Field("createdBy")
    private String createdBy;

    @Field("createdAt")
    private ZonedDateTime createdAt;

    public PartyMongoDbDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CredentialOfferMongoDbValue<T> getCredentialOffer() {
        return credentialOffer;
    }

    public void setCredentialOffer(
        CredentialOfferMongoDbValue<T> credentialOffer) {
        this.credentialOffer = credentialOffer;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
