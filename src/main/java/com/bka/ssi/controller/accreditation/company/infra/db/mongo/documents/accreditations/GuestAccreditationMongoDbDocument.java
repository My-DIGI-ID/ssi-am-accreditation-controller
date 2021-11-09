package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.AccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CorrelationMongoDbDocument;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "guestAccreditations")
public class GuestAccreditationMongoDbDocument extends AccreditationMongoDbDocument {

    @Field("basisIdVerificationCorrelation")
    private CorrelationMongoDbDocument basisIdVerificationCorrelation;

    @Field("guestCredentialIssuanceCorrelation")
    private CorrelationMongoDbDocument guestCredentialIssuanceCorrelation;

    public GuestAccreditationMongoDbDocument() {
    }

    public CorrelationMongoDbDocument getBasisIdVerificationCorrelation() {
        return basisIdVerificationCorrelation;
    }

    public void setBasisIdVerificationCorrelation(
        CorrelationMongoDbDocument basisIdVerificationCorrelation) {
        this.basisIdVerificationCorrelation = basisIdVerificationCorrelation;
    }

    public CorrelationMongoDbDocument getGuestCredentialIssuanceCorrelation() {
        return guestCredentialIssuanceCorrelation;
    }

    public void setGuestCredentialIssuanceCorrelation(
        CorrelationMongoDbDocument guestCredentialIssuanceCorrelation) {
        this.guestCredentialIssuanceCorrelation = guestCredentialIssuanceCorrelation;
    }
}
