package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public abstract class AccreditationMongoDbDocument {
    @Id
    private String _id;

    @Field("partyId")
    private String partyId;

    public AccreditationMongoDbDocument() {
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

}
