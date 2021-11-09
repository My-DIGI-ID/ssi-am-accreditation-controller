package com.bka.ssi.controller.accreditation.company.application.repositories.accreditations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.application.repositories.parties.GuestRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Technical Debt.: @DataMongoTest gives NoSuchBeanDefinitionException due to
 * guestAccreditationMongoDbFacade and
 * guestMongoDbFacade not in test context. @Import does not resolve all dependencies in test
 * context. Therefore, @SpringBootTest for integration tests is used which loads application
 * context for testing. Note that this is not necessary, but by far simplest approach.
 */
@SpringBootTest
@ActiveProfiles("integrationtest")
@TestPropertySource("classpath:application-integrationtest.properties")
public class GuestAccreditationRepositoryIntegrationTest {

    private final static ZonedDateTime dateTime = ZonedDateTime.parse("2018-05-30T12:00:00+00:00");

    @Autowired
    @Qualifier("guestAccreditationMongoDbFacade")
    private GuestAccreditationRepository guestAccreditationRepository;

    @Autowired
    @Qualifier("guestMongoDbFacade")
    private GuestRepository guestRepository;

    @BeforeEach
    public void setUp() throws Exception {
        // ToDo - Outsource to testutils

        Persona persona = new Persona(
            "title",
            "firstName",
            "lastName"
        );

        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(
                dateTime,
                dateTime);
        ContactInformation contactInformation = new ContactInformation(null, null);

        GuestPrivateInformation guestPrivateInformation = new GuestPrivateInformation(
            "dateOfBirth",
            "licencePlateNumber",
            "companyStreet",
            "companyCity",
            "companyPostCode",
            "acceptedDocument");

        GuestCredential credential = new GuestCredential(
            validityTimeframe,
            persona,
            contactInformation,
            "companyName",
            "typeOfVisit",
            "location",
            "invitedBy",
            "referenceBasisId",
            guestPrivateInformation
        );

        CredentialMetadata credentialMetadata = new CredentialMetadata(CredentialType.GUEST);

        CredentialOffer<GuestCredential> credentialOffer = new CredentialOffer(
            credentialMetadata,
            credential
        );

        Guest guest = new Guest("123456", credentialOffer, "user1", ZonedDateTime.now());
        Guest savedGuest = this.guestRepository.save(guest);

        Correlation basisIdVerificationCorrelation = new Correlation("1", "1", null);
        Correlation guestCredentialIssuance = new Correlation("2", "2", null);

        GuestAccreditation guestAccreditation =
            new GuestAccreditation("123", savedGuest, GuestAccreditationStatus.ACCEPTED, "user1",
                null, null, null, null,
                basisIdVerificationCorrelation, guestCredentialIssuance);

        GuestAccreditation savedGuestAccreditation =
            this.guestAccreditationRepository.save(guestAccreditation);

        assertEquals(guestAccreditation.getId(), savedGuestAccreditation.getId());
    }

    @Test
    public void shouldFindGuestAccreditationByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId()
        throws Exception {
        Optional<GuestAccreditation>
            guestAccreditationFoundByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId =
            this.guestAccreditationRepository
                .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                    "1", "1");

        assertTrue(
            guestAccreditationFoundByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId
                .isPresent());
    }

    @Test
    public void shouldNotFindGuestAccreditationByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId() {
        assertThrows(NoSuchElementException.class, () ->
        {
            Optional<GuestAccreditation>
                guestAccreditationFoundByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId =
                this.guestAccreditationRepository
                    .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                        "1", "2");
        });
    }

    @Test
    public void shouldFindGuestAccreditationByGuestCredentialIssuanceCorrelationConnectionId()
        throws Exception {
        Optional<GuestAccreditation>
            guestAccreditationFoundByGuestCredentialIssuanceCorrelationConnectionId =
            this.guestAccreditationRepository
                .findByGuestCredentialIssuanceCorrelationConnectionId("2");

        assertTrue(guestAccreditationFoundByGuestCredentialIssuanceCorrelationConnectionId
            .isPresent());
    }

    @Test
    public void shouldNotFindGuestAccreditationByGuestCredentialIssuanceCorrelationConnectionId() {
        assertThrows(NoSuchElementException.class, () ->
        {
            Optional<GuestAccreditation>
                guestAccreditationFoundByGuestCredentialIssuanceCorrelationConnectionId =
                this.guestAccreditationRepository
                    .findByGuestCredentialIssuanceCorrelationConnectionId("3");
        });
    }

    @Test
    public void shouldFindGuestAccreditationByPartyParams() throws Exception {
        Optional<GuestAccreditation>
            guestAccreditationFoundByPartyParams =
            this.guestAccreditationRepository
                .findByPartyParams("referenceBasisId",
                    "firstName",
                    "lastName",
                    "dateOfBirth",
                    "companyName",
                    dateTime,
                    dateTime,
                    "invitedBy"
                );

        assertTrue(guestAccreditationFoundByPartyParams.isPresent());
    }

    @Test
    public void shouldNotFindGuestAccreditationByPartyParams() {
        assertThrows(NoSuchElementException.class, () ->
        {
            Optional<GuestAccreditation>
                guestAccreditationFoundByPartyParams =
                this.guestAccreditationRepository
                    .findByPartyParams("referenceBasisId",
                        "someOtherFirstName",
                        "lastName",
                        "dateOfBirth",
                        "companyName",
                        dateTime,
                        dateTime,
                        "invitedBy"
                    );
        });
    }

    @Test
    public void shouldCountGuestAccreditationByStatus() {
        assertEquals(1, this.guestAccreditationRepository
            .countByStatus(GuestAccreditationStatus.ACCEPTED));
    }

    @Test
    public void shouldFindAllGuestAccreditationsByStatus() throws Exception {
        assertEquals(1, this.guestAccreditationRepository
            .findAllByStatus(GuestAccreditationStatus.ACCEPTED).size());
    }

    @Test
    public void shouldCountGuestAccreditationByStatusNegated() {
        assertEquals(0, this.guestAccreditationRepository
            .countByStatusIsNot(GuestAccreditationStatus.ACCEPTED));
    }

    @Test
    public void shouldFindAllGuestAccreditationByStatusNegated() throws Exception {
        assertEquals(0, this.guestAccreditationRepository
            .findAllByStatusIsNot(GuestAccreditationStatus.ACCEPTED).size());
    }
}
