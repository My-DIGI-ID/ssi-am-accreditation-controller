package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations.InvitationMongoDbValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

public abstract class AccreditationMongoDbDocument {

    @Id
    private String id;

    @Field("partyId")
    private String partyId;

    @Field("status")
    private String status;

    @Field("invitedBy")
    private String invitedBy;

    @Field("invitedAt")
    private ZonedDateTime invitedAt;

    @Field("invitation")
    private InvitationMongoDbValue invitation;

    public AccreditationMongoDbDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(String invitedBy) {
        this.invitedBy = invitedBy;
    }

    public ZonedDateTime getInvitedAt() {
        return invitedAt;
    }

    public void setInvitedAt(ZonedDateTime invitedAt) {
        this.invitedAt = invitedAt;
    }

    public InvitationMongoDbValue getInvitation() {
        return invitation;
    }

    public void setInvitation(
        InvitationMongoDbValue invitation) {
        this.invitation = invitation;
    }
}
