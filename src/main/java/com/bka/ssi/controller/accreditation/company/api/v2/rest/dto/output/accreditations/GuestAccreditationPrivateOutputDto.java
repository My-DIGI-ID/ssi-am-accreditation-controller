package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestPrivateOutputDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GuestAccreditationPrivateOutputDto extends GuestAccreditationOpenOutputDto {

    // ToDo - should be renamed and not overriden, so GuestAccreditationPrivateOutputDto returns
    //  open and private information
    @NotEmpty
    @NotNull
    private GuestPrivateOutputDto guest;

    public GuestAccreditationPrivateOutputDto() {
    }

    @Override
    public GuestPrivateOutputDto getGuest() {
        return guest;
    }

    public void setGuest(
        GuestPrivateOutputDto guest) {
        this.guest = guest;
    }
}
