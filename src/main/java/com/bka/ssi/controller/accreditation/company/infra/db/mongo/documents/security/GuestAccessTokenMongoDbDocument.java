package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "guestTokens")
public class GuestAccessTokenMongoDbDocument {

    @Id
    private String id;

    @Field("accreditationId")
    String accreditationId;

    @Field("expiring")
    Date expiring;

    public GuestAccessTokenMongoDbDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccreditationId() {
        return accreditationId;
    }

    public void setAccreditationId(String accreditationId) {
        this.accreditationId = accreditationId;
    }

    public Date getExpiring() {
        return expiring;
    }

    public void setExpiring(Date expiring) {
        this.expiring = expiring;
    }
}
