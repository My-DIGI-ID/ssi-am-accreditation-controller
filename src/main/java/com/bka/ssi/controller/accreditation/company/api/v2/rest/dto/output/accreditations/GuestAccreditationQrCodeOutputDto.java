package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GuestAccreditationQrCodeOutputDto {

    @NotEmpty
    @NotNull
    @JsonProperty("connectionQrCode")
    private String invitationQrCode;

    public GuestAccreditationQrCodeOutputDto() {
    }

    public String getInvitationQrCode() {
        return invitationQrCode;
    }

    public void setInvitationQrCode(String invitationQrCode) {
        this.invitationQrCode = invitationQrCode;
    }
}
