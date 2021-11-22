package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.application.agent.ACAPYClient;
import com.bka.ssi.controller.accreditation.company.application.exceptions.AlreadyExistsException;
import com.bka.ssi.controller.accreditation.company.application.factories.accreditations.EmployeeAccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.EmployeeAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.parties.EmployeeRepository;
import com.bka.ssi.controller.accreditation.company.application.utilities.EmailBuilder;
import com.bka.ssi.controller.accreditation.company.application.utilities.QrCodeGenerator;
import com.bka.ssi.controller.accreditation.company.application.utilities.UrlBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee.EmployeeAccreditationBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


class EmployeeAccreditationServiceTest {

    @Value("${template.email.invitation.employee.path}")
    String emailTemplatePath;

    private Employee employee;

    private final Logger logger =
        LoggerFactory.getLogger(EmployeeAccreditationServiceTest.class);

    @Mock
    private EmployeeAccreditationRepository employeeAccreditationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeAccreditationFactory factory;

    @InjectMocks
    private EmployeeAccreditationService accreditationService;

    @Mock
    private EmailBuilder emailBuilder;

    @InjectMocks
    private UrlBuilder urlBuilder;

    @Mock
    private ACAPYClient acapyClient;

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
        builder.employeeCredentialIssuanceCorrelation = correlation;
        accreditation = builder.buildEmployeeAccreditation();
        accreditationService =
            new EmployeeAccreditationService(logger, employeeAccreditationRepository, factory,
                acapyClient, emailBuilder, urlBuilder, employeeRepository);
    }

    @Test
    void offerAccreditation() throws Exception {
        // Given
        Mockito.when(employeeAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(
            acapyClient.issueCredential(Mockito.any(), Mockito.eq(UUID.randomUUID().toString())))
            .thenReturn(new Correlation(expectedUUID));

        Mockito.when(employeeAccreditationRepository.save(Mockito.any()))
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
        builder.employeeCredentialIssuanceCorrelation = correlation;
        builder.status = EmployeeAccreditationStatus.REVOKED;

        EmployeeAccreditation revokedAccreditation = builder.build();

        Mockito.when(employeeAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(revokedAccreditation));

        EmployeeAccreditationService accreditationService =
            new EmployeeAccreditationService(logger, employeeAccreditationRepository, factory,
                acapyClient, emailBuilder, urlBuilder, employeeRepository);

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
        builder.employeeCredentialIssuanceCorrelation = correlation;
        builder.status = EmployeeAccreditationStatus.ACCEPTED;

        EmployeeAccreditation acceptedAccreditation = builder.build();

        Mockito.when(employeeAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(acceptedAccreditation));


        // Then
        assertThrows(AlreadyExistsException.class, () -> {
            // When
            accreditationService.offerAccreditation(UUID.randomUUID().toString());
        });
    }


    @Test
//    @Disabled
    void initiateAccreditation() throws Exception {
        // Given
        Mockito.when(employeeRepository.findByIdAndCreatedBy(Mockito.anyString(),
            Mockito.anyString())).thenReturn(Optional.of(employee));

        List<EmployeeAccreditation> accreditations = new ArrayList<EmployeeAccreditation>();

        Mockito.when(employeeAccreditationRepository.findAllByPartyId(Mockito.anyString()))
            .thenReturn(accreditations);

        Mockito.when(factory.create(Mockito.any(), Mockito.anyString()))
            .thenReturn(accreditation);

        Mockito.when(employeeAccreditationRepository.save(Mockito.any()))
            .thenReturn(accreditation);

        ConnectionInvitation connectionInvitation = new ConnectionInvitation("invitationUrl",
            "connectionId");

        Mockito.when(acapyClient.createConnectionInvitation(Mockito.any()))
            .thenReturn(connectionInvitation);

        Mockito.when(emailBuilder.buildEmployeeInvitationEmail(Mockito.any(),
            Mockito.anyString())).thenReturn("invitationEmail");

        try (MockedStatic<QrCodeGenerator> qrCodeGeneratorMockedStatic = Mockito
            .mockStatic(QrCodeGenerator.class)) {
            qrCodeGeneratorMockedStatic
                .when(() -> QrCodeGenerator.generateQrCodeSvg(Mockito.anyString(), Mockito.anyInt(),
                    Mockito.anyInt()))
                .thenReturn("qrCode");

            // When
            EmployeeAccreditation returnedAccreditation =
                accreditationService.initiateAccreditation(
                    "partyId", "userName");

            // Then
            assertEquals("qrCode", returnedAccreditation.getInvitationQrCode());
        }
    }

    @Test
    void shouldReturnExistingAccreditationIfExists() throws Exception {
        // Given
        Mockito.when(employeeRepository.findByIdAndCreatedBy(Mockito.anyString(),
            Mockito.anyString())).thenReturn(Optional.of(employee));
        EmployeeAccreditationBuilder accreditationBuilder = new EmployeeAccreditationBuilder();
        accreditationBuilder.status = EmployeeAccreditationStatus.PENDING;

        EmployeeAccreditation existingAccreditation = accreditationBuilder.build();

        List<EmployeeAccreditation> existingAccreditations = new ArrayList<EmployeeAccreditation>();
        existingAccreditations.add(existingAccreditation);
        existingAccreditations.add(accreditation);

        Mockito.when(employeeAccreditationRepository.findAllByPartyId(Mockito.anyString()))
            .thenReturn(existingAccreditations);

        // When
        EmployeeAccreditation returnedAccreditation = accreditationService.initiateAccreditation(
            "123", "username");

        // Then
        assertNotEquals(accreditation, returnedAccreditation);
        assertEquals(existingAccreditation, returnedAccreditation);
    }

    @Test
    void completeAccreditation() throws Exception {
        // Given
        Correlation correlation = new Correlation(
            "connectionId");
        Mockito.when(acapyClient.getRevocationCorrelation(Mockito.anyString()))
            .thenReturn(correlation);

        Mockito.when(
            employeeAccreditationRepository
                .findByEmployeeCredentialIssuanceCorrelationConnectionId(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(employeeAccreditationRepository.save(Mockito.any()))
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
        Mockito.when(employeeAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));

        Mockito.when(employeeAccreditationRepository.save(Mockito.any()))
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
        builder.id = "accreditationId";
        builder.employeeCredentialIssuanceCorrelation = correlation;
        builder.status = EmployeeAccreditationStatus.ACCEPTED;

        EmployeeAccreditation acceptedAccreditation = builder.buildEmployeeAccreditation();

        Mockito.when(employeeAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(acceptedAccreditation));

        // When
        Boolean result = accreditationService.isAccreditationCompleted(
            "accreditationId");

        //Then
        assertTrue(result);
    }

    @Test
    void generateAccreditationWithEmailAsMessage() throws Exception {
        // Given
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        EmployeeAccreditationBuilder employeeAccreditationBuilder =
            new EmployeeAccreditationBuilder();

        Employee employee = employeeBuilder.buildEmployee();
        employeeAccreditationBuilder.employee = employee;
        EmployeeAccreditation accreditation = employeeAccreditationBuilder.build();

        String email = "Email content";
        byte[] emailAsByteArray = email.getBytes(StandardCharsets.UTF_8);

        Mockito.when(employeeAccreditationRepository.findById(Mockito.anyString()))
            .thenReturn(Optional.of(accreditation));
        Mockito.when(emailBuilder.buildInvitationEmailAsMessage(
            Mockito.eq(employee.getCredentialOffer().getCredential().getContactInformation()
                .getEmails()),
            Mockito.eq("Invitation for Employee Credential"),
            Mockito.eq(accreditation.getInvitationEmail()))).thenReturn(emailAsByteArray);

        // When
        byte[] result =
            accreditationService.generateAccreditationWithEmailAsMessage("accreditationId");

        //Then
        Assertions.assertEquals(emailAsByteArray, result);
    }
}