package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestOpenOutputDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GuestAccreditationOpenOutputDto {

    @NotEmpty
    @NotNull
    private String id;

    @NotEmpty
    @NotNull
    private GuestOpenOutputDto guest;

    @NotEmpty
    @NotNull
    private String status;

    @NotEmpty
    @NotNull
    private String invitedBy;

    @NotEmpty
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime invitedAt;

    // ToDo - JsonProperty only for compatibility reasons, should be invitationUrl and invitationQrCode
    @NotEmpty
    @NotNull
    @JsonProperty("invitationLink")
    private String invitationUrl;

    @NotEmpty
    @NotNull
    private String invitationEmail;

    @JsonProperty("connectionQrCode")
    private String invitationQrCode;

    public GuestAccreditationOpenOutputDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GuestOpenOutputDto getGuest() {
        return guest;
    }

    public void setGuest(
        GuestOpenOutputDto guest) {
        this.guest = guest;
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
