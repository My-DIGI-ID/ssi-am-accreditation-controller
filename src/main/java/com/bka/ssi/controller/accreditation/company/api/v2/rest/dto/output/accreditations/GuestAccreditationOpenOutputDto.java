package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.enums.AccreditationStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GuestAccreditationOpenOutputDto {

    @NotEmpty
    @NotNull
    private String id;

    @NotEmpty
    @NotNull
    private String invitationEmail;

    @NotEmpty
    @NotNull
    private String invitationLink;

    @NotEmpty
    @NotNull
    private GuestOpenOutputDto guest;

    @NotEmpty
    @NotNull
    private AccreditationStatus status;

    private String connectionQrCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public GuestOpenOutputDto getGuest() {
        return guest;
    }

    public void setGuest(
        GuestOpenOutputDto guest) {
        this.guest = guest;
    }

    public AccreditationStatus getStatus() {
        return status;
    }

    public void setStatus(
        AccreditationStatus status) {
        this.status = status;
    }
    
	public String getConnectionQrCode() {
		return connectionQrCode;
	}

	public void setConnectionQrCode(String connectionQrCode) {
		this.connectionQrCode = connectionQrCode;
	}
}
