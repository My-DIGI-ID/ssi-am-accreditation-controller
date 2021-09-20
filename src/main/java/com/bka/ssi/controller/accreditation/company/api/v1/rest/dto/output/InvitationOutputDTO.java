package com.bka.ssi.controller.accreditation.company.api.v1.rest.dto.output;

import javax.validation.constraints.NotNull;

public class InvitationOutputDTO {

    @NotNull
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
