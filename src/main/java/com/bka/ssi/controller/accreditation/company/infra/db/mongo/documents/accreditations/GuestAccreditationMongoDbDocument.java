package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations.GuestInvitationDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CorrelationMongoDbDocument;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "guestAccreditations")
public class GuestAccreditationMongoDbDocument extends AccreditationMongoDbDocument {

    @Field("basisIdVerificationCorrelation")
    private CorrelationMongoDbDocument basisIdVerificationCorrelation;

    @Field("guestCredentialIssuanceCorrelation")
    private CorrelationMongoDbDocument guestCredentialIssuanceCorrelation;

    @Field("guestInvitation")
    private GuestInvitationDbValue guestInvitation;

    @Field("accreditationStatus")
    private GuestAccreditationStatus accreditationStatus;

    public GuestAccreditationMongoDbDocument() {
        super();
    }

    public GuestInvitationDbValue getGuestInvitation() {
        return guestInvitation;
    }

    public void setGuestInvitation(
        GuestInvitationDbValue guestInvitation) {
        this.guestInvitation = guestInvitation;
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

    public GuestAccreditationStatus getAccreditationStatus() {
        return accreditationStatus;
    }

    public void setAccreditationStatus(
        GuestAccreditationStatus accreditationStatus) {
        this.accreditationStatus = accreditationStatus;
    }
}
