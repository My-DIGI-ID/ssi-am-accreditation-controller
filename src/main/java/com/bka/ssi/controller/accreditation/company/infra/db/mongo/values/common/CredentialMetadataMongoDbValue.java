package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class CredentialMetadataMongoDbValue {

    @Field("type")
    private String type;

    @Field("id")
    private String id;

    @Field("did")
    private String did;

    @Field("partyCreated")
    private Date partyCreated;

    @Field("partyPersonalDataDeleted")
    private Date partyPersonalDataDeleted;

    public CredentialMetadataMongoDbValue() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Date getPartyCreated() {
        return partyCreated;
    }

    public void setPartyCreated(Date partyCreated) {
        this.partyCreated = partyCreated;
    }

    public Date getPartyPersonalDataDeleted() {
        return partyPersonalDataDeleted;
    }

    public void setPartyPersonalDataDeleted(Date partyPersonalDataDeleted) {
        this.partyPersonalDataDeleted = partyPersonalDataDeleted;
    }
}
