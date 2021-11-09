package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationQrCodeOutputDto;
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

    public GuestAccreditationOpenOutputDto entityToOpenDto(
        GuestAccreditation guestAccreditation) {
        logger.debug("Mapping GuestAccreditation to GuestAccreditationOpenOutputDto");

        if (guestAccreditation == null) {
            // throw instead
            return null;
        } else {
            GuestAccreditationOpenOutputDto dto = new GuestAccreditationOpenOutputDto();

            GuestOpenOutputDto guest = guestOutputDtoMapper
                .entityToOpenDto(guestAccreditation.getParty());

            dto.setId(guestAccreditation.getId());
            dto.setGuest(guest);
            dto.setStatus(guestAccreditation.getStatus().getName());
            dto.setInvitedBy(guestAccreditation.getInvitedBy());
            dto.setInvitedAt(guestAccreditation.getInvitedAt());
            dto.setInvitationUrl(guestAccreditation.getInvitationUrl());
            dto.setInvitationEmail(guestAccreditation.getInvitationEmail());
            dto.setInvitationQrCode(guestAccreditation.getInvitationQrCode());

            return dto;
        }
    }

    public GuestAccreditationPrivateOutputDto entityToPrivateDto(
        GuestAccreditation guestAccreditation) {
        logger.debug("Mapping GuestAccreditation to GuestAccreditationPrivateOutputDto");

        if (guestAccreditation == null) {
            // throw instead
            return null;
        } else {
            GuestAccreditationPrivateOutputDto dto = new GuestAccreditationPrivateOutputDto();

            GuestPrivateOutputDto guest = guestOutputDtoMapper
                .entityToPrivateDto(guestAccreditation.getParty());

            dto.setId(guestAccreditation.getId());
            dto.setStatus(guestAccreditation.getStatus().getName());
            dto.setGuest(guest);
            dto.setInvitationUrl(guestAccreditation.getInvitationUrl());
            dto.setInvitationEmail(guestAccreditation.getInvitationEmail());
            dto.setInvitationQrCode(guestAccreditation.getInvitationQrCode());

            return dto;
        }
    }

    public GuestAccreditationQrCodeOutputDto entityToQrCodeDto(GuestAccreditation accreditation) {
        logger.debug("Mapping GuestAccreditation to GuestAccreditationQrCodeOutputDto");

        if (accreditation == null) {
            // throw instead
            return null;
        } else {
            GuestAccreditationQrCodeOutputDto dto = new GuestAccreditationQrCodeOutputDto();

            dto.setInvitationQrCode(accreditation.getInvitationQrCode());

            return dto;
        }
    }
}
