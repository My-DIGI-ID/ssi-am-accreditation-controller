package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.abstractions.CredentialMongoDbValue;
import org.springframework.data.mongodb.core.mapping.Field;

public class CredentialOfferMongoDbValue<T extends CredentialMongoDbValue> {

    @Field("credentialMetadata")
    private CredentialMetadataMongoDbValue credentialMetadata;

    @Field("credential")
    private T credential;

    public CredentialOfferMongoDbValue() {
    }

    public CredentialMetadataMongoDbValue getCredentialMetadata() {
        return credentialMetadata;
    }

    public void setCredentialMetadata(
        CredentialMetadataMongoDbValue credentialMetadata) {
        this.credentialMetadata = credentialMetadata;
    }

    public T getCredential() {
        return credential;
    }

    public void setCredential(T credential) {
        this.credential = credential;
    }
}
