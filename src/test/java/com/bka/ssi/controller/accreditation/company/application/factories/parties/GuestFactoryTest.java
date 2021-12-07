package com.bka.ssi.controller.accreditation.company.application.factories.parties;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.GuestInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.ValidationService;
import com.bka.ssi.controller.accreditation.company.application.utilities.CsvBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestInputDtoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class GuestFactoryTest {

    @Mock
    private CsvBuilder csvBuilder;

    @Mock
    private ValidationService validationService;

    @Mock
    private MultipartFile multipartFile;

    private GuestFactory guestFactory;

    private GuestInputDtoBuilder guestInputDtoBuilder;

    private Logger logger = LoggerFactory.getLogger(GuestFactoryTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        guestFactory = new GuestFactory(csvBuilder, validationService, logger);
        guestInputDtoBuilder = new GuestInputDtoBuilder();
    }

    @Test
    void createFromDto() throws Exception {
        GuestInputDto dto = guestInputDtoBuilder.buildGuestInputDto();

        Guest guest = guestFactory.create(dto, "username");

        Assertions.assertEquals(dto.getTitle(),
            guest.getCredentialOffer().getCredential().getPersona().getTitle());
        Assertions.assertEquals(dto.getFirstName(),
            guest.getCredentialOffer().getCredential().getPersona().getFirstName());
        Assertions.assertEquals(dto.getLastName(),
            guest.getCredentialOffer().getCredential().getPersona().getLastName());
        Assertions.assertEquals(dto.getEmail(),
            guest.getCredentialOffer().getCredential().getContactInformation().getEmails().get(0));
        Assertions.assertEquals(dto.getPrimaryPhoneNumber(),
            guest.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .get(0));
        Assertions.assertEquals(dto.getSecondaryPhoneNumber(),
            guest.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .get(1));
        Assertions.assertTrue(dto.getValidFrom()
            .isEqual(guest.getCredentialOffer().getCredential().getValidityTimeframe()
                .getValidFrom()));
        Assertions.assertTrue(dto.getValidUntil().isEqual(
            guest.getCredentialOffer().getCredential().getValidityTimeframe().getValidUntil()));
        Assertions.assertEquals(dto.getCompanyName(),
            guest.getCredentialOffer().getCredential().getCompanyName());
        Assertions.assertEquals(dto.getTypeOfVisit(),
            guest.getCredentialOffer().getCredential().getTypeOfVisit());
        Assertions.assertEquals(dto.getLocation(),
            guest.getCredentialOffer().getCredential().getLocation());
        Assertions.assertEquals("username", guest.getCreatedBy());
    }

    @Test
    void createFromFile() throws Exception {
        GuestInputDto dto = guestInputDtoBuilder.buildGuestInputDto();

        List<String> header = List.of("header");

        Mockito.when(csvBuilder.getHeaderFromCsv(Mockito.any())).thenReturn(header);

        Mockito.when(csvBuilder.validateHeader(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(csvBuilder.readCsvToListByColumnName(null, GuestInputDto.class,
            ',')).thenReturn(List.of(dto));

        Mockito.when(validationService.validate(Mockito.anyList())).thenReturn(true);

        List<Guest> guests = guestFactory.create(multipartFile, "username");

        Assertions.assertEquals(1, guests.size());

        Guest guest = guests.get(0);
        Assertions.assertEquals(dto.getTitle(),
            guest.getCredentialOffer().getCredential().getPersona().getTitle());
        Assertions.assertEquals(dto.getFirstName(),
            guest.getCredentialOffer().getCredential().getPersona().getFirstName());
        Assertions.assertEquals(dto.getLastName(),
            guest.getCredentialOffer().getCredential().getPersona().getLastName());
        Assertions.assertEquals(dto.getEmail(),
            guest.getCredentialOffer().getCredential().getContactInformation().getEmails().get(0));
        Assertions.assertEquals(dto.getPrimaryPhoneNumber(),
            guest.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .get(0));
        Assertions.assertEquals(dto.getSecondaryPhoneNumber(),
            guest.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .get(1));
        Assertions.assertTrue(dto.getValidFrom()
            .isEqual(guest.getCredentialOffer().getCredential().getValidityTimeframe()
                .getValidFrom()));
        Assertions.assertTrue(dto.getValidUntil().isEqual(
            guest.getCredentialOffer().getCredential().getValidityTimeframe().getValidUntil()));
        Assertions.assertEquals(dto.getCompanyName(),
            guest.getCredentialOffer().getCredential().getCompanyName());
        Assertions.assertEquals(dto.getTypeOfVisit(),
            guest.getCredentialOffer().getCredential().getTypeOfVisit());
        Assertions.assertEquals(dto.getLocation(),
            guest.getCredentialOffer().getCredential().getLocation());
        Assertions.assertEquals("username", guest.getCreatedBy());
    }

    @Test
    void createFromNullFile() throws Exception {
        List<Guest> guests = guestFactory.create((MultipartFile) null, "username");

        Assertions.assertNull(guests);
    }
}
