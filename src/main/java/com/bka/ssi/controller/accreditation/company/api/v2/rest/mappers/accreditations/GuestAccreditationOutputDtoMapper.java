package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.GuestOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GuestAccreditationOutputDtoMapper {

    private final GuestOutputDtoMapper guestOutputDtoMapper;
    private final Logger logger;

    public GuestAccreditationOutputDtoMapper(
        GuestOutputDtoMapper guestOutputDtoMapper, Logger logger) {
        this.guestOutputDtoMapper = guestOutputDtoMapper;
        this.logger = logger;
    }

    public GuestAccreditationOpenOutputDto toGuestAccreditationOpenOutputDto(
        GuestAccreditation guestAccreditation) {
        logger.debug("Mapping GuestAccreditation to OpenOutputDto");
        if (guestAccreditation == null) {
            // throw instead
            return null;
        } else {
            GuestAccreditationOpenOutputDto output = new GuestAccreditationOpenOutputDto();

            GuestOpenOutputDto guest = guestOutputDtoMapper
                .guestToGuestOpenOutputDto(guestAccreditation.getParty());

            output.setId(guestAccreditation.getId());
            output.setInvitationLink(guestAccreditation.getInvitationLink());
            output.setInvitationEmail(guestAccreditation.getInvitationEmail());
            output.setStatus(guestAccreditation.getStatus());
            output.setGuest(guest);
            output.setConnectionQrCode(guestAccreditation.getConnectionQrCode());
            
            return output;
        }
    }

    public GuestAccreditationPrivateOutputDto toGuestAccreditationPrivateOutputDto(
        GuestAccreditation guestAccreditation) {
        if (guestAccreditation == null) {
            // throw instead
            return null;
        } else {
            GuestAccreditationPrivateOutputDto output = new GuestAccreditationPrivateOutputDto();

            GuestPrivateOutputDto guest = guestOutputDtoMapper
                .guestToGuestPrivateOutputDto(guestAccreditation.getParty());

            output.setId(guestAccreditation.getId());
            output.setInvitationLink(guestAccreditation.getInvitationLink());
            output.setInvitationEmail(guestAccreditation.getInvitationEmail());
            output.setStatus(guestAccreditation.getStatus());
            output.setGuest(guest);

            return output;
        }
    }

}
