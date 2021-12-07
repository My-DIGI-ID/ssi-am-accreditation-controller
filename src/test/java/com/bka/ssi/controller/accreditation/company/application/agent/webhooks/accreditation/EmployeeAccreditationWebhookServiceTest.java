package com.bka.ssi.controller.accreditation.company.application.agent.webhooks.accreditation;

import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.EmployeeAccreditationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

public class EmployeeAccreditationWebhookServiceTest {

    @Mock
    private Logger logger;

    @Mock
    private EmployeeAccreditationService employeeAccreditationService;

    @InjectMocks
    private EmployeeAccreditationWebhookService employeeAccreditationWebhookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        employeeAccreditationWebhookService = new EmployeeAccreditationWebhookService(logger,
            employeeAccreditationService);
    }

    @Test
    void handleOnConnectionResponseState() throws Exception {
        ACAPYConnectionDto dto = new ACAPYConnectionDto();
        ReflectionTestUtils.setField(dto, "state", "response");
        ReflectionTestUtils.setField(dto, "alias", "12345");

        employeeAccreditationWebhookService.handleOnConnection(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Connection state: " + dto.getState());
        Mockito.verify(employeeAccreditationService, Mockito.times(1))
            .offerAccreditation(dto.getAlias());
    }

    @Test
    void handleOnConnectionIgnoredState() throws Exception {
        ACAPYConnectionDto dto = new ACAPYConnectionDto();
        ReflectionTestUtils.setField(dto, "state", "error");

        employeeAccreditationWebhookService.handleOnConnection(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Ignoring Connection state: " + dto.getState());
        Mockito.verifyNoInteractions(employeeAccreditationService);
    }

    @Test
    void handleOnConnectionUnknownState() throws Exception {
        ACAPYConnectionDto dto = new ACAPYConnectionDto();
        ReflectionTestUtils.setField(dto, "state", "state");

        employeeAccreditationWebhookService.handleOnConnection(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Unknown Connection state: " + dto.getState());
        Mockito.verifyNoInteractions(employeeAccreditationService);
    }

    @Test
    void handleOnIssueCredentialCredentialIssuedState() throws Exception {
        ACAPYIssueCredentialDto dto = new ACAPYIssueCredentialDto();
        ReflectionTestUtils.setField(dto, "state", "credential_issued");
        ReflectionTestUtils.setField(dto, "connectionId", "12345");
        ReflectionTestUtils.setField(dto, "credentialExchangeId", "23456");
        ReflectionTestUtils.setField(dto, "initiator", "initiator");

        employeeAccreditationWebhookService.handleOnIssueCredential(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("IssueCredential state: " + dto.getState());
        Mockito.verify(employeeAccreditationService, Mockito.times(1))
            .completeAccreditation(dto.getConnectionId(), dto.getCredentialExchangeId(),
                dto.getInitiator());
    }

    @Test
    void handleOnIssueCredentialIgnoredState() throws Exception {
        ACAPYIssueCredentialDto dto = new ACAPYIssueCredentialDto();
        ReflectionTestUtils.setField(dto, "state", "credential_acked");

        employeeAccreditationWebhookService.handleOnIssueCredential(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Ignoring IssueCredential state: " + dto.getState());
        Mockito.verifyNoInteractions(employeeAccreditationService);
    }

    @Test
    void handleOnIssueCredentialUnknownState() throws Exception {
        ACAPYIssueCredentialDto dto = new ACAPYIssueCredentialDto();
        ReflectionTestUtils.setField(dto, "state", "state");

        employeeAccreditationWebhookService.handleOnIssueCredential(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Unknown IssueCredential state: " + dto.getState());
        Mockito.verifyNoInteractions(employeeAccreditationService);
    }

    @Test
    void handleOnPresentProof() throws Exception {
        ACAPYPresentProofDto dto = new ACAPYPresentProofDto();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            employeeAccreditationWebhookService.handleOnPresentProof(dto);
        });
    }
}
