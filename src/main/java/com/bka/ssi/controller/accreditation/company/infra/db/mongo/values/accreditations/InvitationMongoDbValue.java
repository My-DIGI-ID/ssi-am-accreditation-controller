package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations;

import org.springframework.data.mongodb.core.mapping.Field;

public class InvitationMongoDbValue {

    @Field("invitationUrl")
    private String invitationUrl;

    @Field("invitationEmail")
    private String invitationEmail;

    @Field("invitationQrCode")
    private String invitationQrCode;

    public InvitationMongoDbValue() {
    }

    public String getInvitationUrl() {
        return invitationUrl;
    }

    public void setInvitationUrl(String invitationUrl) {
        this.invitationUrl = invitationUrl;
    }

    public String getInvitationEmail() {
        return invitationEmail;
    }

    public void setInvitationEmail(String invitationEmail) {
        this.invitationEmail = invitationEmail;
    }

    public String getInvitationQrCode() {
        return invitationQrCode;
    }

    public void setInvitationQrCode(String invitationQrCode) {
        this.invitationQrCode = invitationQrCode;
    }
}
