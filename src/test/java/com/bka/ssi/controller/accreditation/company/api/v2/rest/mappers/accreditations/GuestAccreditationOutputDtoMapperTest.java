package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationQrCodeOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.GuestOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.GuestAccreditationBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuestAccreditationOutputDtoMapperTest {
    private static final Logger logger =
        LoggerFactory.getLogger(GuestAccreditationOutputDtoMapperTest.class);
    private static Guest guest;
    private static GuestAccreditationOutputDtoMapper guestAccreditationOutputDtoMapper;
    private GuestAccreditation guestAccreditation;
    private static final String INVITEE = "unittest";

    @BeforeAll
    static void init() {
        // Mapper
        GuestOutputDtoMapper guestOutputDtoMapper = new GuestOutputDtoMapper(logger);
        guestAccreditationOutputDtoMapper =
            new GuestAccreditationOutputDtoMapper(guestOutputDtoMapper, logger);
    }

    @BeforeEach
    void setUp() throws InvalidValidityTimeframeException {
        // Initiate guest accreditation
        GuestBuilder guestBuilder = new GuestBuilder();
        guestBuilder.createdBy = INVITEE;
        guestBuilder.invitedBy = INVITEE;
        guest = guestBuilder.buildGuest();

        GuestAccreditationBuilder guestAccreditationBuilder = new GuestAccreditationBuilder();
        GuestAccreditationBuilder.guest = guest;
        guestAccreditationBuilder.invitee = INVITEE;
        guestAccreditation = guestAccreditationBuilder.build();
    }

    @Test
    public void shouldMapEntityToOpenDto() {
        // when
        GuestAccreditationOpenOutputDto guestAccreditationOpenOutputDto =
            guestAccreditationOutputDtoMapper.entityToOpenDto(guestAccreditation);

        // then
        assertEquals(guestAccreditationOpenOutputDto.getGuest().getCompanyName(),
            guest.getCredentialOffer().getCredential().getCompanyName());
        assertEquals(guestAccreditationOpenOutputDto.getGuest().getTypeOfVisit(),
            guest.getCredentialOffer().getCredential().getTypeOfVisit());
        assertEquals(guestAccreditationOpenOutputDto.getGuest().getFirstName(),
            guest.getCredentialOffer().getCredential().getPersona().getFirstName());
        assertEquals(guestAccreditationOpenOutputDto.getGuest().getLastName(),
            guest.getCredentialOffer().getCredential().getPersona().getLastName());
        assertEquals(guestAccreditationOpenOutputDto.getGuest().getId(), guest.getId());
        assertEquals(guestAccreditationOpenOutputDto.getInvitedBy(), INVITEE);
        assertEquals(guestAccreditationOpenOutputDto.getStatus(), "OPEN");

    }

    @Test
    public void shouldMapEntityToPrivateDto() {
        // when
        GuestAccreditationPrivateOutputDto guestAccreditationPrivateOutputDto =
            guestAccreditationOutputDtoMapper.entityToPrivateDto(guestAccreditation);

        // then
        assertEquals("1970-01-01", guestAccreditationPrivateOutputDto.getGuest().getDateOfBirth());
        assertNull(guestAccreditationPrivateOutputDto.getInvitationQrCode());
    }

    @Test
    public void shouldMapEntityToQrCodeDto() {
        // when
        GuestAccreditationQrCodeOutputDto guestAccreditationQrCodeOutputDto =
            guestAccreditationOutputDtoMapper.entityToQrCodeDto(guestAccreditation);

        guestAccreditationQrCodeOutputDto.setInvitationQrCode("GuestQrCode");

        // then
        assertEquals("GuestQrCode", guestAccreditationQrCodeOutputDto.getInvitationQrCode());
    }
}
