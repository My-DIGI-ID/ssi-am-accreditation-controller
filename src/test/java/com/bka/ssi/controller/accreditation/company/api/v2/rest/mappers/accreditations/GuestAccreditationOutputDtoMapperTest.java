package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationQrCodeOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.GuestOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.GuestAccreditationBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuestAccreditationOutputDtoMapperTest {

    private static final Logger logger =
        LoggerFactory.getLogger(GuestAccreditationOutputDtoMapperTest.class);
    private static GuestAccreditationOutputDtoMapper guestAccreditationOutputDtoMapper;
    private static GuestOutputDtoMapper guestOutputDtoMapper;

    private static GuestAccreditationBuilder guestAccreditationBuilder;

    private GuestAccreditation guestAccreditation;

    @BeforeAll
    static void init() throws InvalidValidityTimeframeException {
        guestOutputDtoMapper = new GuestOutputDtoMapper(logger);
        guestAccreditationOutputDtoMapper =
            new GuestAccreditationOutputDtoMapper(guestOutputDtoMapper, logger);
        guestAccreditationBuilder = new GuestAccreditationBuilder();
    }

    @BeforeEach
    void setup() throws InvalidValidityTimeframeException {
        guestAccreditation = guestAccreditationBuilder.buildGuestAccreditation();
    }

    @Test
    public void shouldMapEntityToOpenDto() {
        // when
        GuestAccreditationOpenOutputDto guestAccreditationOpenOutputDto =
            guestAccreditationOutputDtoMapper.entityToOpenDto(guestAccreditation);

        // then
        assertEquals(guestAccreditation.getId(), guestAccreditationOpenOutputDto.getId());
        assertEquals(guestAccreditation.getStatus().getName(),
            guestAccreditationOpenOutputDto.getStatus());
        assertEquals(guestAccreditation.getInvitedBy(),
            guestAccreditationOpenOutputDto.getInvitedBy());
        assertEquals(guestAccreditation.getInvitedAt(),
            guestAccreditationOpenOutputDto.getInvitedAt());
        assertEquals(guestAccreditation.getInvitationUrl(),
            guestAccreditationOpenOutputDto.getInvitationUrl());
        assertEquals(guestAccreditation.getInvitationEmail(),
            guestAccreditationOpenOutputDto.getInvitationEmail());
        assertEquals(guestAccreditation.getInvitationQrCode(),
            guestAccreditationOpenOutputDto.getInvitationQrCode());

        // implicitly testing EmployeeOutputDtoMapper
        assertEquals(guestAccreditation.getParty().getId(),
            guestAccreditationOpenOutputDto.getGuest().getId());
        assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getTitle(),
            guestAccreditationOpenOutputDto.getGuest().getTitle());
        assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getFirstName(),
            guestAccreditationOpenOutputDto.getGuest().getFirstName());
        assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getLastName(),
            guestAccreditationOpenOutputDto.getGuest().getLastName());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getEmails().get(0),
            guestAccreditationOpenOutputDto.getGuest().getEmail());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getPhoneNumbers().get(0),
            guestAccreditationOpenOutputDto.getGuest().getPrimaryPhoneNumber());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getPhoneNumbers().get(1),
            guestAccreditationOpenOutputDto.getGuest().getSecondaryPhoneNumber());
        assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getCompanyName(),
            guestAccreditationOpenOutputDto.getGuest().getCompanyName());
        assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getTypeOfVisit(),
            guestAccreditationOpenOutputDto.getGuest().getTypeOfVisit());
        assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getLocation(),
            guestAccreditationOpenOutputDto.getGuest().getLocation());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getValidityTimeframe().getValidFrom(),
            guestAccreditationOpenOutputDto.getGuest().getValidFrom());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getValidityTimeframe().getValidUntil(),
            guestAccreditationOpenOutputDto.getGuest().getValidUntil());
        assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getInvitedBy(),
            guestAccreditationOpenOutputDto.getGuest().getInvitedBy());
        assertEquals(guestAccreditation.getParty().getCreatedBy(),
            guestAccreditationOpenOutputDto.getGuest().getCreatedBy());
        assertEquals(guestAccreditation.getParty().getCreatedAt(),
            guestAccreditationOpenOutputDto.getGuest().getCreatedAt());
    }

    @Test
    public void shouldMapEntityToPrivateDto() {
        // when
        GuestAccreditationPrivateOutputDto guestAccreditationPrivateOutputDto =
            guestAccreditationOutputDtoMapper.entityToPrivateDto(guestAccreditation);

        // then
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getDateOfBirth(),
            guestAccreditationPrivateOutputDto.getGuest().getDateOfBirth());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getLicencePlateNumber(),
            guestAccreditationPrivateOutputDto.getGuest().getLicencePlateNumber());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyStreet(),
            guestAccreditationPrivateOutputDto.getGuest().getCompanyStreet());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyCity(),
            guestAccreditationPrivateOutputDto.getGuest().getCompanyCity());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyPostCode(),
            guestAccreditationPrivateOutputDto.getGuest().getCompanyPostCode());
        assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getAcceptedDocument(),
            guestAccreditationPrivateOutputDto.getGuest().getAcceptedDocument());
    }

    @Test
    public void shouldMapEntityToQrCodeDto() {
        // when
        GuestAccreditationQrCodeOutputDto guestAccreditationQrCodeOutputDto =
            guestAccreditationOutputDtoMapper.entityToQrCodeDto(guestAccreditation);

        // then
        assertEquals(guestAccreditation.getInvitationQrCode(),
            guestAccreditationQrCodeOutputDto.getInvitationQrCode());
    }
}
