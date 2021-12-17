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

import com.bka.ssi.controller.accreditation.company.application.factories.accreditations.GuestAccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.GuestAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.utilities.EmailBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.GuestAccreditationBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("integrationtest")
@TestPropertySource("classpath:application-integrationtest.properties")
@ExtendWith(SpringExtension.class)
public class GuestAccreditationServiceIntegrationTest {

    @Value("${template.email.invitation.guest.path}")
    String emailTemplatePath;

    @Value("${guest.invitation.redirectUrl}")
    String redirectUrl;

    @Mock
    private GuestAccreditationRepository repository;

    @Mock
    private GuestAccreditationFactory factory;

    @InjectMocks
    private GuestAccreditationService accreditationService;

    @InjectMocks
    private EmailBuilder emailBuilder;

    private Guest testGuest;

    private CredentialOffer<GuestCredential> credentialOffer;

    private static final String partyId = "abc123";

    @BeforeEach
    public void setup() throws InvalidValidityTimeframeException {
        MockitoAnnotations.openMocks(this);
        CredentialMetadata metadata = new CredentialMetadata(CredentialType.GUEST);
        Persona persona = new Persona("Test", "User");
        GuestCredential credential =
            new GuestCredential(null, persona, null, null, null, null, null);
        credentialOffer = new CredentialOffer<GuestCredential>(metadata, credential);
        testGuest = new Guest("123", credentialOffer, "user1", ZonedDateTime.now());
        ReflectionTestUtils
            .setField(emailBuilder, "emailTemplatePath", emailTemplatePath);
        ReflectionTestUtils
            .setField(accreditationService, "redirectUrl", redirectUrl);
    }

    @Disabled
    @Test
    public void shouldInitiateAccreditationWithInvitationEmail() throws Exception {
        //ToDo: Implement test
        GuestAccreditation accreditation =
            accreditationService.initiateAccreditation(partyId, "employee-01");

        assertEquals(null, accreditation);

    }

    @Disabled
    @Test
    public void shouldCreateEmailFromTemplate() {
        // Given
        String invitation =
            emailBuilder.buildGuestInvitationEmail(testGuest, redirectUrl);
        // Then
        assertEquals("Unit test\nTest User", invitation);
    }

    @Disabled
    @Test
    void generateAccreditationWithEmailAsMessage() throws Exception {
        // Given
        GuestBuilder guestBuilder = new GuestBuilder();
        GuestAccreditationBuilder guestAccreditationBuilder =
            new GuestAccreditationBuilder();

        Guest guest = guestBuilder.buildGuest();
        guestAccreditationBuilder.guest = guest;
        GuestAccreditation accreditation = guestAccreditationBuilder.build();

        String email = "Email content";
        byte[] emailAsByteArray = email.getBytes(StandardCharsets.UTF_8);

        Mockito.when(repository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));
        Mockito.when(emailBuilder.buildInvitationEmailAsMessage(
            Mockito.eq(guest.getCredentialOffer().getCredential().getContactInformation()
                .getEmails()),
            Mockito.eq("Invitation for Guest Credential"),
            Mockito.eq(accreditation.getInvitationEmail()))).thenReturn(emailAsByteArray);

        // When
        byte[] result =
            accreditationService.generateAccreditationWithEmailAsMessage("accreditationId");

        //Then
        Assertions.assertEquals(emailAsByteArray, result);
    }
}
