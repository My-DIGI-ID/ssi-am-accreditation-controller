package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations;

import org.springframework.data.mongodb.core.mapping.Field;

public class GuestInvitationDbValue {

    @Field("invitationLink")
    private String invitationLink;

    @Field("invitationEmail")
    private String invitationEmail;
    
    @Field("connectionQrCode")
    private String connectionQrCode;
    

    public GuestInvitationDbValue() {
    }

    public String getInvitationEmail() {
        return invitationEmail;
    }

    public void setInvitationEmail(String invitationEmail) {
        this.invitationEmail = invitationEmail;
    }

    public String getInvitationLink() {
        return invitationLink;
    }

    public void setInvitationLink(String invitationLink) {
        this.invitationLink = invitationLink;
    }

	public String getConnectionQrCode() {
		return connectionQrCode;
	}

	public void setConnectionQrCode(String connectionQrCode) {
		this.connectionQrCode = connectionQrCode;
	}
}
