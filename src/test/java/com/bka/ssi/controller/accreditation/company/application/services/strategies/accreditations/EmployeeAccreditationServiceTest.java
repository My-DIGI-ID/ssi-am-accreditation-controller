package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import com.bka.ssi.controller.accreditation.company.application.exceptions.AlreadyExistsException;
import com.bka.ssi.controller.accreditation.company.application.factories.accreditations.EmployeeAccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.EmployeeAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.parties.EmployeePartyService;
import com.bka.ssi.controller.accreditation.company.application.utilities.EmailBuilder;
import com.bka.ssi.controller.accreditation.company.application.utilities.QrCodeGenerator;
import com.bka.ssi.controller.accreditation.company.application.utilities.UrlBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.ACAPYClientV6;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee.EmployeeAccreditationBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


class EmployeeAccreditationServiceTest {

    @Value("${template.email.invitation.employee.path}")
    String emailTemplatePath;

    @Mock
    private EmployeePartyService partyService;

    private Employee employee;

    private final Logger logger =
        LoggerFactory.getLogger(EmployeeAccreditationServiceTest.class);

    @Mock
    private EmployeeAccreditationRepository repository;

    @Mock
    private EmployeeAccreditationFactory factory;

    @InjectMocks
    private EmployeeAccreditationService accreditationService;

    @Mock
    private EmailBuilder emailBuilder;

    @InjectMocks
    private UrlBuilder urlBuilder;

    @Mock
    private ACAPYConfiguration configuration;

    @Mock
    private ACAPYClientV6 acapyClient;


    @Mock
    private QrCodeGenerator qrCodeGenerator;


    private EmployeeAccreditation accreditation;
    private String expectedUUID;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        employee = new EmployeeBuilder().buildEmployee();

        ReflectionTestUtils
            .setField(emailBuilder, "employeeEmailTemplatePath", emailTemplatePath);

        expectedUUID = UUID.randomUUID().toString();

        Correlation correlation = new Correlation(expectedUUID);

        EmployeeAccreditationBuilder builder = new EmployeeAccreditationBuilder();
        builder.correlation = correlation;
        accreditation = builder.build();
        accreditationService =
            new EmployeeAccreditationService(logger, repository, factory, acapyClient, emailBuilder,
                urlBuilder, qrCodeGenerator, partyService);
    }

    @Test
    void offerAccreditation() throws Exception {
        // Given


        Mockito.when(repository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(
            acapyClient.issueCredential(Mockito.any(), Mockito.eq(UUID.randomUUID().toString())))
            .thenReturn(new Correlation(expectedUUID));

        Mockito.when(repository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        EmployeeAccreditation returnedAccreditation =
            accreditationService.offerAccreditation(UUID.randomUUID().toString());

        // Then
        assertEquals(EmployeeAccreditationStatus.PENDING, returnedAccreditation.getStatus());
    }

    @Test
    void shouldThrowAlreadyExistsIfCredentialRevoked() throws Exception {
        // Given
        EmployeeAccreditationBuilder builder = new EmployeeAccreditationBuilder();

        Correlation correlation = new Correlation(expectedUUID);
        builder.correlation = correlation;
        builder.status = EmployeeAccreditationStatus.REVOKED;

        EmployeeAccreditation revokedAccreditation = builder.build();

        Mockito.when(repository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(revokedAccreditation));

        EmployeeAccreditationService accreditationService =
            new EmployeeAccreditationService(logger, repository, factory, acapyClient, emailBuilder,
                urlBuilder, qrCodeGenerator, partyService);

        // Then
        assertThrows(AlreadyExistsException.class, () -> {
            // When
            accreditationService.offerAccreditation(UUID.randomUUID().toString());
        });
    }

    @Test
    void shouldThrowAlreadyExistsIfCredentialAccepted() throws Exception {
        // Given
        EmployeeAccreditationBuilder builder = new EmployeeAccreditationBuilder();

        Correlation correlation = new Correlation(expectedUUID);
        builder.correlation = correlation;
        builder.status = EmployeeAccreditationStatus.ACCEPTED;

        EmployeeAccreditation acceptedAccreditation = builder.build();

        Mockito.when(repository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(acceptedAccreditation));


        // Then
        assertThrows(AlreadyExistsException.class, () -> {
            // When
            accreditationService.offerAccreditation(UUID.randomUUID().toString());
        });
    }


    @Test
    @Disabled
        //ToDo figure out why connectioninvitation is always null
    void testInitiateAccreditation() throws Exception {
        // Given
        List<EmployeeAccreditation> accreditations = new ArrayList<EmployeeAccreditation>();

        Mockito.when(repository.findAllByPartyId(Mockito.anyString()))
            .thenReturn(accreditations);

        Mockito.when(partyService.getPartyById(Mockito.anyString())).thenReturn(employee);

        Mockito.when(factory.create(Mockito.any(Employee.class), Mockito.anyString()))
            .thenReturn(accreditation);

        Mockito.when(repository.save(Mockito.any()))
            .thenReturn(accreditation);


        ConnectionInvitation connectionInvitation = Mockito.mock(ConnectionInvitation.class);

        Mockito.when(acapyClient.createConnectionInvitation(Mockito.anyString()))
            .thenReturn(connectionInvitation);

        Mockito.when(connectionInvitation.getInvitationUrl()).thenReturn("url");

        Mockito.when(qrCodeGenerator.generateQrCodeSvg(Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyInt())).thenReturn("qrCode");

        Mockito.when(emailBuilder.buildEmployeeInvitationEmail(Mockito.any(),
            Mockito.anyString())).thenReturn("invitationEmail");

        // When
        EmployeeAccreditation returnedAccreditation =
            accreditationService.initiateAccreditation("123", "username");

        //Then
        assertEquals("qrCode", returnedAccreditation.getInvitationQrCode());
    }

    @Test
    void shouldReturnExistingAccreditationIfExists() throws Exception {
        // Given
        EmployeeAccreditation accreditation =
            new EmployeeAccreditationBuilder().build();

        List<EmployeeAccreditation> existingAccreditations = new ArrayList<EmployeeAccreditation>();
        existingAccreditations.add(accreditation);

        Mockito.when(repository.findAllByPartyId(Mockito.anyString()))
            .thenReturn(existingAccreditations);

        // When
        EmployeeAccreditation employeeAccreditation = accreditationService.initiateAccreditation(
            "123", "username");

        // Then
        assertEquals(accreditation, employeeAccreditation);
    }

    @Test
    void completeAccreditation() throws Exception {
        // Given
        Correlation correlation = new Correlation(
            "connectionId");
        Mockito.when(acapyClient.getRevocationCorrelation(Mockito.anyString()))
            .thenReturn(correlation);

        Mockito.when(
            repository.findByEmployeeCredentialIssuanceCorrelationConnectionId(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(repository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When

        EmployeeAccreditation employeeAccreditation = accreditationService.completeAccreditation(
            "connectionId", "exchangeId", "issuer");

        //Then
        assertEquals(EmployeeAccreditationStatus.ACCEPTED, employeeAccreditation.getStatus());

        assertEquals("deleted", employeeAccreditation.getInvitationEmail());

        assertEquals("deleted",
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getEmployeeId());
        assertEquals("deleted",
            employeeAccreditation.getParty().getCredentialOffer().getCredential()
                .getEmployeeState());
        assertEquals("deleted",
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getEmployer()
                .getName());
    }

    @Test
    void revokeAccreditation() throws Exception {
        // Given
        Mockito.when(repository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(repository.save(Mockito.any()))
            .thenReturn(accreditation);

        // When
        EmployeeAccreditation employeeAccreditation = accreditationService.revokeAccreditation(
            "accreditationId");

        //Then
        assertEquals(EmployeeAccreditationStatus.REVOKED, employeeAccreditation.getStatus());
    }

    @Test
    void isAccreditationCompleted() throws Exception {
        // Given
        EmployeeAccreditationBuilder builder = new EmployeeAccreditationBuilder();

        Correlation correlation = new Correlation(expectedUUID);
        builder.correlation = correlation;
        builder.status = EmployeeAccreditationStatus.ACCEPTED;

        EmployeeAccreditation acceptedAccreditation = builder.build();

        Mockito.when(repository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(acceptedAccreditation));

        // When
        Boolean result = accreditationService.isAccreditationCompleted(
            "accreditationId");

        //Then
        assertTrue(result);
    }
}