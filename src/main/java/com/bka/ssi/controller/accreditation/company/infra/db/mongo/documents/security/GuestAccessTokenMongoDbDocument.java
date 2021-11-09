package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "guestTokens")
public class GuestAccessTokenMongoDbDocument {

    @Id
    private String id;

    @Field("accreditationId")
    private String accreditationId;

    @Field("expiring")
    ZonedDateTime expiring;

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

    public ZonedDateTime getExpiring() {
        return expiring;
    }

    public void setExpiring(ZonedDateTime expiring) {
        this.expiring = expiring;
    }
}
