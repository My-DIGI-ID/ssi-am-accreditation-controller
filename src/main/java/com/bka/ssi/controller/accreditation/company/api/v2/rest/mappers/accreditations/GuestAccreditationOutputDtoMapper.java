/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * The type Guest accreditation output dto mapper.
 */
@Service
public class GuestAccreditationOutputDtoMapper {

    private final GuestOutputDtoMapper guestOutputDtoMapper;
    private final Logger logger;

    /**
     * Instantiates a new Guest accreditation output dto mapper.
     *
     * @param guestOutputDtoMapper the guest output dto mapper
     * @param logger               the logger
     */
    public GuestAccreditationOutputDtoMapper(
        GuestOutputDtoMapper guestOutputDtoMapper, Logger logger) {
        this.guestOutputDtoMapper = guestOutputDtoMapper;
        this.logger = logger;
    }

    /**
     * Entity to open dto guest accreditation open output dto.
     *
     * @param guestAccreditation the guest accreditation
     * @return the guest accreditation open output dto
     */
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

    /**
     * Entity to private dto guest accreditation private output dto.
     *
     * @param guestAccreditation the guest accreditation
     * @return the guest accreditation private output dto
     */
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

    /**
     * Entity to qr code dto guest accreditation qr code output dto.
     *
     * @param accreditation the accreditation
     * @return the guest accreditation qr code output dto
     */
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
