package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

public class CredentialMetadataMongoDbValue {

    @Field("issuedBy")
    private String issuedBy;

    @Field("issuedAt")
    private ZonedDateTime issuedAt;

    @Field("partyPersonalDataDeleted")
    private ZonedDateTime partyPersonalDataDeleted;

    @Field("credentialType")
    private String credentialType;

    public CredentialMetadataMongoDbValue() {
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public ZonedDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(ZonedDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public ZonedDateTime getPartyPersonalDataDeleted() {
        return partyPersonalDataDeleted;
    }

    public void setPartyPersonalDataDeleted(ZonedDateTime partyPersonalDataDeleted) {
        this.partyPersonalDataDeleted = partyPersonalDataDeleted;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }
}
