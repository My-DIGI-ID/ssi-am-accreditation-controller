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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;

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
    //ToDo: Implement test
    public void shouldInitiateAccreditationWithInvitationEmail() throws Exception {
        GuestAccreditation accreditation =
            accreditationService.initiateAccreditation(partyId, "employee-01");

        assertEquals(null, accreditation);

    }

    @Test
    public void shouldCreateEmailFromTemplate() {
        // Given
        String invitation =
            emailBuilder.buildGuestInvitationEmail(testGuest, redirectUrl);
        // Then
        assertEquals("Unit test\nTest User", invitation);
    }
}
