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

package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.application.agent.ACAPYClient;
import com.bka.ssi.controller.accreditation.company.application.exceptions.AlreadyExistsException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidAccreditationStateChangeException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
import com.bka.ssi.controller.accreditation.company.application.factories.accreditations.GuestAccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.GuestAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.parties.GuestRepository;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations.GuestAccreditationPrivateInfoInputDto;
import com.bka.ssi.controller.accreditation.company.application.utilities.BasisIdPresentationUtility;
import com.bka.ssi.controller.accreditation.company.application.utilities.EmailBuilder;
import com.bka.ssi.controller.accreditation.company.application.utilities.QrCodeGenerator;
import com.bka.ssi.controller.accreditation.company.application.utilities.UrlBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.BasisIdPresentation;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.GuestAccreditationBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GuestAccreditationServiceTest {

    @InjectMocks
    private GuestAccreditationService accreditationService;

    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private ACAPYClient acapyClient;
    @Mock
    private EmailBuilder emailBuilder;
    @Mock
    private UrlBuilder urlBuilder;

    @Mock
    private BasisIdPresentationUtility basisIdPresentationUtility;

    @Mock
    private GuestAccreditationRepository guestAccreditationRepository;

    @Mock
    private GuestRepository guestPartyRepository;

    @Mock
    private GuestAccreditationFactory factory;

    private Guest guest;

    private final Logger logger =
        LoggerFactory.getLogger(EmployeeAccreditationServiceTest.class);

    private String expectedUUID;
    private GuestAccreditation accreditation;

    @BeforeEach
    void setUp() throws InvalidValidityTimeframeException {
        MockitoAnnotations.openMocks(this);
        expectedUUID = UUID.randomUUID().toString();

        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        accreditation = builder.buildGuestAccreditation();
        GuestBuilder guestBuilder = new GuestBuilder();
        guest = guestBuilder.buildGuest();

        accreditationService =
            new GuestAccreditationService(
                guestAccreditationRepository,
                factory,
                logger,
                emailBuilder,
                urlBuilder,
                acapyClient,
                authenticationService,
                basisIdPresentationUtility,
                guestPartyRepository
            );
    }


    @Test
    void initiateAccreditation() throws Throwable {
        // Given
        Mockito.when(factory.create(Mockito.eq(guest), Mockito.anyString()))
            .thenReturn(accreditation);

        Mockito.when(guestAccreditationRepository.findAllByPartyId(Mockito.anyString()))
            .thenReturn(new ArrayList<>());

        Mockito.when(guestPartyRepository.findByIdAndCreatedBy(Mockito.anyString(),
            Mockito.anyString())).thenReturn(Optional.ofNullable(guest));

        Mockito.when(urlBuilder.buildGuestInvitationRedirectUrl(Mockito.anyString())).thenReturn(
            "url");

        Mockito.when(emailBuilder.buildGuestInvitationEmail(Mockito.any(Guest.class),
            Mockito.anyString())).thenReturn("email");

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        GuestAccreditation returnedAccreditation = accreditationService.initiateAccreditation(
            "partyId", "unittest");

        // Then
        assertEquals("unittest", returnedAccreditation.getInvitedBy());
        assertEquals("url", returnedAccreditation.getInvitationUrl());
        assertEquals("email", returnedAccreditation.getInvitationEmail());

        Mockito.verify(guestAccreditationRepository, Mockito.times(2)).save(Mockito.any());
    }

    @Test
    void initiateAccreditationExistingAccreditation() throws Throwable {
        // Given
        Mockito.when(factory.create(Mockito.eq(guest), Mockito.anyString()))
            .thenReturn(accreditation);

        Mockito.when(guestAccreditationRepository.findAllByPartyId(Mockito.anyString()))
            .thenReturn(List.of(accreditation));

        Mockito.when(guestPartyRepository.findByIdAndCreatedBy(Mockito.anyString(),
            Mockito.anyString())).thenReturn(Optional.ofNullable(guest));

        // When
        GuestAccreditation returnedAccreditation = accreditationService.initiateAccreditation(
            "partyId", "unittest");

        // Then
        assertEquals("unittest", returnedAccreditation.getInvitedBy());
        assertEquals("url", returnedAccreditation.getInvitationUrl());
        assertEquals("email", returnedAccreditation.getInvitationEmail());
    }

    @Test
    void generateAccreditationWithEmailAsMessage() throws Exception {
        // Given
        Mockito.when(guestAccreditationRepository.findById("accreditationId"))
            .thenReturn(Optional.of(accreditation));

        byte[] email = "Test".getBytes();
        Mockito.when(emailBuilder.buildInvitationEmailAsMessage(
            Mockito.anyList(),
            Mockito.eq("Invitation for Guest Credential"),
            Mockito.eq(accreditation.getInvitationEmail())))
            .thenReturn(email);

        // When
        byte[] returnedEmail = accreditationService.generateAccreditationWithEmailAsMessage(
            "accreditationId");

        Assertions.assertEquals(email, returnedEmail);

        Mockito.verify(emailBuilder, Mockito.times(1))
            .buildInvitationEmailAsMessage(
                Mockito.anyList(),
                Mockito.eq("Invitation for Guest Credential"),
                Mockito.eq(accreditation.getInvitationEmail()));
    }

    @Test
    void proceedWithAccreditation() throws Exception {
        // Given
        Mockito.when(
            guestAccreditationRepository
                .findById(Mockito.anyString())).thenReturn(Optional.of(accreditation));

        ConnectionInvitation connectionInvitation = new ConnectionInvitation("invitationUrl",
            "connectionId");

        Mockito.when(acapyClient.createConnectionInvitation(Mockito.anyString()))
            .thenReturn(connectionInvitation);

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        try (MockedStatic<QrCodeGenerator> qrCodeGeneratorMockedStatic = Mockito
            .mockStatic(QrCodeGenerator.class)) {
            qrCodeGeneratorMockedStatic
                .when(() -> QrCodeGenerator.generateQrCodeSvg(Mockito.anyString(), Mockito.anyInt(),
                    Mockito.anyInt()))
                .thenReturn("qrCode");

            // When
            GuestAccreditation returnedAccreditation =
                accreditationService.proceedWithAccreditation(
                    "accreditationId");

            // Then
            assertEquals("qrCode", returnedAccreditation.getInvitationQrCode());
        }
    }

    @Test
    void verifyBasisId() throws Exception {
        // Given
        Mockito.when(
            guestAccreditationRepository
                .findById(Mockito.anyString())).thenReturn(Optional.of(accreditation));

        Correlation guestCorrelation = new Correlation(expectedUUID);
        Mockito.when(acapyClient.verifyBasisId(Mockito.anyString())).thenReturn(guestCorrelation);

        // When
        accreditationService.verifyBasisId(
            "accreditationId", "connectionId");

        // Then
        assertEquals(GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING,
            accreditation.getStatus());
        assertEquals(guestCorrelation, accreditation.getBasisIdVerificationCorrelation());
    }

    @Test
    void completeVerificationOfBasisId() throws Exception {
        // Given
        Mockito.when(
            guestAccreditationRepository
                .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                    Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        BasisIdPresentation basisIdPresentation = Mockito.mock(BasisIdPresentation.class);

        Mockito.when(acapyClient.getBasisIdPresentation(Mockito.anyString()))
            .thenReturn(basisIdPresentation);

        Mockito.when(basisIdPresentationUtility.isPresentationAndGuestMatchLoosely(Mockito.any(),
            Mockito.any())).thenReturn(true);
        Correlation guestCorrelation = new Correlation(expectedUUID);

        Mockito.when(basisIdPresentation.getDateOfBirth()).thenReturn("dateOfBirth");

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        accreditationService.completeVerificationOfBasisId("connectionId", "threadId",
            "presentationExchangeId", "true");

        //Then
        assertEquals(GuestAccreditationStatus.BASIS_ID_VALID, accreditation.getStatus());
        assertEquals("connectionId",
            accreditation.getBasisIdVerificationCorrelation().getConnectionId());
        assertEquals("threadId",
            accreditation.getBasisIdVerificationCorrelation().getThreadId());
        assertEquals("presentationExchangeId",
            accreditation.getBasisIdVerificationCorrelation().getPresentationExchangeId());

        assertEquals("dateOfBirth",
            accreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getDateOfBirth());
        assertNotNull(
            accreditation.getParty().getCredentialOffer().getCredential().getReferenceBasisId());
    }

    @Test
    void shouldDeferOnInvalidBasisId() throws Exception {
        // Given
        Mockito.when(
            guestAccreditationRepository
                .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                    Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        accreditationService.completeVerificationOfBasisId("connectionId", "threadId",
            "presentationExchangeId", "false");

        // Then
        assertEquals(GuestAccreditationStatus.BASIS_ID_INVALID, accreditation.getStatus());
    }

    @Test
    void shouldDeferIfPresentationMismatch() throws Exception {
        // Given
        Mockito.when(
            guestAccreditationRepository
                .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                    Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(basisIdPresentationUtility.isPresentationAndGuestMatchLoosely(Mockito.any(),
            Mockito.any())).thenReturn(false);

        // When
        accreditationService.completeVerificationOfBasisId("connectionId", "threadId",
            "presentationExchangeId", "true");

        // Then
        assertEquals(GuestAccreditationStatus.BASIS_ID_INVALID, accreditation.getStatus());
    }

    @Test
    void appendWithProprietaryInformationFromGuest() throws Exception {
        // Given
        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        GuestAccreditationPrivateInfoInputDto inputDto =
            new GuestAccreditationPrivateInfoInputDto();

        String licensePlate = "ABC-123";
        String street = "Street";
        String city = "City";
        String postCode = "012345";
        String documents = "test.txt";
        String secondaryPhone = "+49-321";
        String primaryPhone = "+49-123";


        ReflectionTestUtils.setField(inputDto, "licencePlateNumber", licensePlate);
        ReflectionTestUtils.setField(inputDto, "companyStreet", street);
        ReflectionTestUtils.setField(inputDto, "companyCity", city);
        ReflectionTestUtils.setField(inputDto, "companyPostCode", postCode);
        ReflectionTestUtils.setField(inputDto, "acceptedDocument", documents);
        ReflectionTestUtils.setField(inputDto, "primaryPhoneNumber", primaryPhone);
        ReflectionTestUtils.setField(inputDto, "secondaryPhoneNumber", secondaryPhone);

        // When
        GuestAccreditation returnedAccreditation =
            accreditationService.appendWithProprietaryInformationFromGuest(
                "accreditationId", inputDto);

        // Then
        assertEquals(licensePlate,
            returnedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getLicencePlateNumber());
        assertEquals(street,
            returnedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyStreet());
        assertEquals(city,
            returnedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyCity());
        assertEquals(postCode,
            returnedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyPostCode());
        assertEquals(documents,
            returnedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getAcceptedDocument());
        assertEquals(primaryPhone,
            returnedAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getPhoneNumbers()
                .get(returnedAccreditation.getParty().getCredentialOffer().getCredential()
                    .getContactInformation().getPhoneNumbers().size() - 2));
        assertEquals(secondaryPhone,
            returnedAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getPhoneNumbers()
                .get(returnedAccreditation.getParty().getCredentialOffer().getCredential()
                    .getContactInformation().getPhoneNumbers().size() - 1));

    }

    @Test
    void offerAccreditation() throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.BASIS_ID_VALID;
        GuestAccreditation verifiedBasisIdAccreditation = builder.buildGuestAccreditation();

        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(verifiedBasisIdAccreditation));


        Correlation guestCorrelation = new Correlation(expectedUUID);
        Mockito.when(
            acapyClient.issueCredential(Mockito.any(), Mockito.anyString()))
            .thenReturn(guestCorrelation);

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(verifiedBasisIdAccreditation);

        // When
        GuestAccreditation returnedAccreditation =
            accreditationService.offerAccreditation(expectedUUID);

        // Then
        assertEquals(GuestAccreditationStatus.PENDING, returnedAccreditation.getStatus());
        assertEquals(guestCorrelation,
            returnedAccreditation.getGuestCredentialIssuanceCorrelation());
    }


    @Test
    void shouldThrowAlreadyExistsException() throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.REVOKED;
        GuestAccreditation revokedAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.ACCEPTED;
        GuestAccreditation acceptedAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.PENDING;
        GuestAccreditation pendingAccreditation = builder.buildGuestAccreditation();

        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(acceptedAccreditation));

        // Then
        assertThrows(AlreadyExistsException.class, () -> {
            // When
            accreditationService.offerAccreditation("accreditationId");
        });

        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(revokedAccreditation));

        assertThrows(AlreadyExistsException.class, () -> {
            // When
            accreditationService.offerAccreditation("accreditationId");
        });

        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(pendingAccreditation));

        assertThrows(AlreadyExistsException.class, () -> {
            // When
            accreditationService.offerAccreditation("accreditationId");
        });
    }

    @Test
    void shouldThrowInvalidAccreditationStateChangeExceptionWhenOfferedInvalidAccreditations()
        throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.OPEN;
        GuestAccreditation openAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.BASIS_ID_INVALID;
        GuestAccreditation invalidAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING;
        GuestAccreditation pendingAccreditation = builder.buildGuestAccreditation();

        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(openAccreditation));

        // Then
        assertThrows(InvalidAccreditationStateChangeException.class, () -> {
            // When
            accreditationService.offerAccreditation("accreditationId");
        });

        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(invalidAccreditation));

        assertThrows(InvalidAccreditationStateChangeException.class, () -> {
            // When
            accreditationService.offerAccreditation("accreditationId");
        });

        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(pendingAccreditation));

        assertThrows(InvalidAccreditationStateChangeException.class, () -> {
            // When
            accreditationService.offerAccreditation("accreditationId");
        });
    }

    @Test
    void completeAccreditation() throws Exception {
        // Given
        Correlation correlation = new Correlation(
            "connectionId");
        Mockito.when(acapyClient.getRevocationCorrelation(Mockito.anyString()))
            .thenReturn(correlation);

        Mockito.when(
            guestAccreditationRepository
                .findByGuestCredentialIssuanceCorrelationConnectionId(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        GuestAccreditation guestAccreditation = accreditationService.completeAccreditation(
            "connectionId", "exchangeId", "issuer");

        //Then
        assertEquals(GuestAccreditationStatus.ACCEPTED, guestAccreditation.getStatus());
    }

    @Test
    void isGuestBasisIdValidationCompleted() throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.id = "accreditationId";
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.BASIS_ID_VALID;
        GuestAccreditation accreditationWithValidBasisId = builder.buildGuestAccreditation();

        Mockito.when(
            guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditationWithValidBasisId));

        // When
        Boolean status =
            accreditationService.isGuestBasisIdValidationCompleted(
                "accreditationId");

        // Then
        assertTrue(status);
    }

    @Test
    void shouldThrowUnauthorizedOnInvalidBasisId() throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.BASIS_ID_INVALID;

        GuestAccreditation accreditationWithInValidBasisId = builder.buildGuestAccreditation();

        Mockito.when(
            guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditationWithInValidBasisId));

        // Then
        assertThrows(UnauthorizedException.class, () -> {
            // When
            accreditationService.isGuestBasisIdValidationCompleted("accreditationId");
        });

    }

    @Test
    void isAccreditationCompleted() throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.id = "accreditationId";
        builder.status = GuestAccreditationStatus.ACCEPTED;
        builder.invitedBy = "unittest";
        GuestAccreditation acceptedAccreditation = builder.buildGuestAccreditation();

        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(acceptedAccreditation));

        // When
        Boolean result = accreditationService.isAccreditationCompleted(
            "accreditationId");

        //Then
        assertTrue(result);
    }

    @Test
    void getUniqueAccreditationByPartyParams() throws Exception {
        // Given
        Mockito.when(
            guestAccreditationRepository.findByPartyParams(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(ZonedDateTime.class),
                Mockito.any(ZonedDateTime.class),
                Mockito.anyString()
            ))
            .thenReturn(Optional.of(accreditation));

        // When
        GuestAccreditation returnedAccreditation =
            accreditationService.getUniqueAccreditationByPartyParams(
                "referenceBasisId",
                "firstName",
                "lastName",
                "dateOfBirth",
                "companyName",
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                "unittest");

        // Then
        assertEquals(accreditation, returnedAccreditation);
    }

    @Test
    void shouldThrowNotFoundIfNoUniqueAccreditation() throws Exception {
        // Given
        Mockito.when(
            guestAccreditationRepository.findByPartyParams(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(ZonedDateTime.class),
                Mockito.any(ZonedDateTime.class),
                Mockito.anyString()
            ))
            .thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> {
            // When
            GuestAccreditation returnedAccreditation =
                accreditationService.getUniqueAccreditationByPartyParams(
                    "referenceBasisId",
                    "firstName",
                    "lastName",
                    "dateOfBirth",
                    "companyName",
                    ZonedDateTime.now(),
                    ZonedDateTime.now(),
                    "unittest");
        });
    }

    @Test
    void cleanGuestInformationOnCheckout() throws Exception {
        // Given
        Mockito.when(
            guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        GuestAccreditation cleanedAccreditation =
            accreditationService.cleanGuestInformationOnCheckout("accreditationid");


        // Then
        assertEquals("deleted", cleanedAccreditation.getInvitationEmail());

        // Persona
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getFirstName());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getLastName());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getTitle());

        // ContactInfo
        assertEquals(new ArrayList<>(),
            cleanedAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getPhoneNumbers());
        assertEquals(new ArrayList<>(),
            cleanedAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getEmails());

        // Credential
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential().getCompanyName());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential().getTypeOfVisit());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential().getLocation());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential().getInvitedBy());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential()
                .getReferenceBasisId());

        // Private Info
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getDateOfBirth());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyPostCode());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getLicencePlateNumber());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyStreet());
        assertEquals("deleted",
            cleanedAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation().getCompanyCity());

    }

    @Test
    void getAllAccreditationsGroupedByStatus() throws Exception {
        //Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.CANCELLED;
        GuestAccreditation cancelledAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.ACCEPTED;
        GuestAccreditation acceptedAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING;
        GuestAccreditation verificationPendingAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.BASIS_ID_VALID;
        GuestAccreditation validBasisIdAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.PENDING;
        GuestAccreditation pendingAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.BASIS_ID_INVALID;
        GuestAccreditation invalidBasisIdAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.REVOKED;
        GuestAccreditation revokedBasisIdAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.CHECK_OUT;
        GuestAccreditation checkedOutAccreditation = builder.buildGuestAccreditation();

        builder.status = GuestAccreditationStatus.CHECK_IN;
        GuestAccreditation checkedInAccreditation = builder.buildGuestAccreditation();

        List<GuestAccreditation> openList =
            Arrays.asList(accreditation);

        List<GuestAccreditation> cancelledList =
            Arrays.asList(cancelledAccreditation);

        List<GuestAccreditation> acceptedList =
            Arrays.asList(acceptedAccreditation);

        List<GuestAccreditation> verificationPendingList =
            Arrays.asList(verificationPendingAccreditation);

        List<GuestAccreditation> validBasisIdList =
            Arrays.asList(validBasisIdAccreditation);

        List<GuestAccreditation> pendingList =
            Arrays.asList(pendingAccreditation);

        List<GuestAccreditation> invalidBasisIdList =
            Arrays.asList(invalidBasisIdAccreditation);

        List<GuestAccreditation> checkedInList =
            Arrays.asList(checkedInAccreditation);

        List<GuestAccreditation> checkedOutList =
            Arrays.asList(checkedOutAccreditation);

        List<GuestAccreditation> revokedList =
            Arrays.asList(revokedBasisIdAccreditation);

        Map<GuestAccreditationStatus, List<GuestAccreditation>> expectedMap =
            new HashMap<GuestAccreditationStatus, List<GuestAccreditation>>() {{
                put(GuestAccreditationStatus.CANCELLED, cancelledList);
                put(GuestAccreditationStatus.ACCEPTED, acceptedList);
                put(GuestAccreditationStatus.OPEN, openList);
                put(GuestAccreditationStatus.REVOKED, revokedList);
                put(GuestAccreditationStatus.BASIS_ID_INVALID, invalidBasisIdList);
                put(GuestAccreditationStatus.PENDING, pendingList);
                put(GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING,
                    verificationPendingList);
                put(GuestAccreditationStatus.BASIS_ID_VALID, validBasisIdList);
                put(GuestAccreditationStatus.CHECK_IN, checkedInList);
                put(GuestAccreditationStatus.CHECK_OUT, checkedOutList);
            }};

        Mockito.when(guestAccreditationRepository.findAllByStatus(GuestAccreditationStatus.OPEN))
            .thenReturn(openList);

        Mockito
            .when(guestAccreditationRepository.findAllByStatus(GuestAccreditationStatus.ACCEPTED))
            .thenReturn(acceptedList);

        Mockito
            .when(guestAccreditationRepository.findAllByStatus(GuestAccreditationStatus.CANCELLED))
            .thenReturn(cancelledList);

        Mockito
            .when(guestAccreditationRepository.findAllByStatus(GuestAccreditationStatus.REVOKED))
            .thenReturn(revokedList);

        Mockito
            .when(guestAccreditationRepository
                .findAllByStatus(GuestAccreditationStatus.BASIS_ID_INVALID))
            .thenReturn(invalidBasisIdList);

        Mockito
            .when(guestAccreditationRepository.findAllByStatus(GuestAccreditationStatus.PENDING))
            .thenReturn(pendingList);

        Mockito
            .when(guestAccreditationRepository
                .findAllByStatus(GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING))
            .thenReturn(verificationPendingList);

        Mockito
            .when(guestAccreditationRepository
                .findAllByStatus(GuestAccreditationStatus.BASIS_ID_VALID))
            .thenReturn(validBasisIdList);

        Mockito
            .when(guestAccreditationRepository
                .findAllByStatus(GuestAccreditationStatus.CHECK_OUT))
            .thenReturn(checkedOutList);

        Mockito
            .when(guestAccreditationRepository
                .findAllByStatus(GuestAccreditationStatus.CHECK_IN))
            .thenReturn(checkedInList);

        // When
        Map<GuestAccreditationStatus, List<GuestAccreditation>> returnedMap =
            accreditationService.getAllAccreditationsGroupedByStatus();

        // Then
        assertEquals(expectedMap, returnedMap);
    }

    @Test
    void countOfAccreditationsGroupedByStatus() {
        //Given
        Mockito.when(
            guestAccreditationRepository.countByStatus(Mockito.any(GuestAccreditationStatus.class)))
            .thenReturn(
                (long) 3);


        Map<GuestAccreditationStatus, Long> expectedMap = new HashMap<GuestAccreditationStatus,
            Long>() {{
            put(GuestAccreditationStatus.CANCELLED, (long) 3);
            put(GuestAccreditationStatus.ACCEPTED, (long) 3);
            put(GuestAccreditationStatus.OPEN, (long) 3);
            put(GuestAccreditationStatus.REVOKED, (long) 3);
            put(GuestAccreditationStatus.BASIS_ID_INVALID, (long) 3);
            put(GuestAccreditationStatus.PENDING, (long) 3);
            put(GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING,
                (long) 3);
            put(GuestAccreditationStatus.BASIS_ID_VALID, (long) 3);
            put(GuestAccreditationStatus.CHECK_IN, (long) 3);
            put(GuestAccreditationStatus.CHECK_OUT, (long) 3);
        }};

        // When
        Map<GuestAccreditationStatus, Long> returnedMap =
            accreditationService.countOfAccreditationsGroupedByStatus();

        // Then
        assertEquals(returnedMap, expectedMap);
    }

    @Test
    void revokeAccreditation() throws Exception {
        // Given
        Mockito.when(guestAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        GuestAccreditation revokedAccreditation = accreditationService.revokeAccreditation(
            "accreditationId");

        // Then
        assertEquals(GuestAccreditationStatus.REVOKED, revokedAccreditation.getStatus());
    }

    @Test
    void getAllAccreditations() throws Exception {
        // Given
        List<GuestAccreditation> guestAccreditationList = new ArrayList<GuestAccreditation>();
        guestAccreditationList.add(accreditation);

        Mockito.when(
            guestAccreditationRepository
                .findAllByInvitedByAndValidStatus(Mockito.anyString(), Mockito.anyList()))
            .thenReturn(guestAccreditationList);

        // When
        List<GuestAccreditation> returnedAccreditationList =
            accreditationService.getAllAccreditations("userName");

        // Then
        assertEquals(guestAccreditationList, returnedAccreditationList);
    }

    @Test
    void shouldUpdateAcceptedAccreditation() throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.ACCEPTED;
        GuestAccreditation accreditation = builder.buildGuestAccreditation();

        Mockito.when(
            guestAccreditationRepository
                .findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        GuestAccreditation returnedAccreditation =
            accreditationService.updateAccreditationStatus("accreditationId",
                GuestAccreditationStatus.CHECK_IN);

        // Then
        assertEquals(GuestAccreditationStatus.CHECK_IN, returnedAccreditation.getStatus());
    }

    @Test
    void shouldUpdateCheckedInAccreditation() throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.CHECK_IN;
        GuestAccreditation accreditation = builder.buildGuestAccreditation();

        Mockito.when(
            guestAccreditationRepository
                .findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        GuestAccreditation returnedAccreditation =
            accreditationService.updateAccreditationStatus("accreditationId",
                GuestAccreditationStatus.CHECK_OUT);

        // Then
        assertEquals(GuestAccreditationStatus.CHECK_OUT, returnedAccreditation.getStatus());
    }

    @Test
    void shouldThrowInvalidAccreditationStateChangeExceptionOnInvalidUpdates() throws Exception {
        // Given
        GuestAccreditationBuilder builder = new GuestAccreditationBuilder();
        builder.invitedBy = "unittest";
        builder.status = GuestAccreditationStatus.OPEN;
        GuestAccreditation accreditation = builder.buildGuestAccreditation();

        Mockito.when(
            guestAccreditationRepository
                .findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(guestAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        // Then
        assertThrows(InvalidAccreditationStateChangeException.class, () -> {
            // When
            accreditationService.updateAccreditationStatus("accreditationId",
                GuestAccreditationStatus.CHECK_OUT);
        });
    }
}
