package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestPrivateOutputDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GuestAccreditationPrivateOutputDto extends GuestAccreditationOpenOutputDto {

    @NotEmpty
    @NotNull
    private GuestPrivateOutputDto guest;

    @Override
    public GuestPrivateOutputDto getGuest() {
        return guest;
    }

    public void setGuest(
        GuestPrivateOutputDto guest) {
        this.guest = guest;
    }
}
