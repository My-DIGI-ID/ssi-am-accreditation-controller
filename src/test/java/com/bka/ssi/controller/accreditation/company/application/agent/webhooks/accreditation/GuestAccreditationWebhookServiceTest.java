package com.bka.ssi.controller.accreditation.company.application.agent.webhooks.accreditation;

import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.accreditation.GuestAccreditationWebhookService;
import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.GuestAccreditationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

public class GuestAccreditationWebhookServiceTest {

    @Mock
    private Logger logger;

    @Mock
    private GuestAccreditationService guestAccreditationService;

    @InjectMocks
    private GuestAccreditationWebhookService guestAccreditationWebhookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        guestAccreditationWebhookService =
            new GuestAccreditationWebhookService(logger, guestAccreditationService);
    }

    @Test
    void handleOnConnectionResponseState() throws Exception {
        ACAPYConnectionDto dto = new ACAPYConnectionDto();
        ReflectionTestUtils.setField(dto, "state", "response");
        ReflectionTestUtils.setField(dto, "alias", "12345");
        ReflectionTestUtils.setField(dto, "connectionId", "23456");

        guestAccreditationWebhookService.handleOnConnection(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Connection state: " + dto.getState());
        Mockito.verify(guestAccreditationService, Mockito.times(1))
            .verifyBasisId(dto.getAlias(), dto.getConnectionId());
    }

    @Test
    void handleOnConnectionIgnoredState() throws Exception {
        ACAPYConnectionDto dto = new ACAPYConnectionDto();
        ReflectionTestUtils.setField(dto, "state", "error");

        guestAccreditationWebhookService.handleOnConnection(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Ignoring Connection state: " + dto.getState());
        Mockito.verifyNoInteractions(guestAccreditationService);
    }

    @Test
    void handleOnConnectionUnknownState() throws Exception {
        ACAPYConnectionDto dto = new ACAPYConnectionDto();
        ReflectionTestUtils.setField(dto, "state", "state");

        guestAccreditationWebhookService.handleOnConnection(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Unknown Connection state: " + dto.getState());
        Mockito.verifyNoInteractions(guestAccreditationService);
    }

    @Test
    void handleOnIssueCredentialCredentialIssuedState() throws Exception {
        ACAPYIssueCredentialDto dto = new ACAPYIssueCredentialDto();
        ReflectionTestUtils.setField(dto, "state", "credential_issued");
        ReflectionTestUtils.setField(dto, "connectionId", "12345");
        ReflectionTestUtils.setField(dto, "credentialExchangeId", "23456");
        ReflectionTestUtils.setField(dto, "initiator", "initiator");

        guestAccreditationWebhookService.handleOnIssueCredential(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("IssueCredential state: " + dto.getState());
        Mockito.verify(guestAccreditationService, Mockito.times(1))
            .completeAccreditation(dto.getConnectionId(), dto.getCredentialExchangeId(),
                dto.getInitiator());
    }

    @Test
    void handleOnIssueCredentialIgnoredState() throws Exception {
        ACAPYIssueCredentialDto dto = new ACAPYIssueCredentialDto();
        ReflectionTestUtils.setField(dto, "state", "credential_acked");

        guestAccreditationWebhookService.handleOnIssueCredential(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Ignoring IssueCredential state: " + dto.getState());
        Mockito.verifyNoInteractions(guestAccreditationService);
    }

    @Test
    void handleOnIssueCredentialUnknownState() throws Exception {
        ACAPYIssueCredentialDto dto = new ACAPYIssueCredentialDto();
        ReflectionTestUtils.setField(dto, "state", "state");

        guestAccreditationWebhookService.handleOnIssueCredential(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Unknown IssueCredential state: " + dto.getState());
        Mockito.verifyNoInteractions(guestAccreditationService);
    }

    @Test
    void handleOnPresentProofVerifiedState() throws Exception {
        ACAPYPresentProofDto dto = new ACAPYPresentProofDto();
        ReflectionTestUtils.setField(dto, "state", "verified");
        ReflectionTestUtils.setField(dto, "connectionId", "12345");
        ReflectionTestUtils.setField(dto, "threadId", "23456");
        ReflectionTestUtils.setField(dto, "presentationExchangeId", "34567");
        ReflectionTestUtils.setField(dto, "verified", "true");

        guestAccreditationWebhookService.handleOnPresentProof(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Proof Request state: " + dto.getState());
        Mockito.verify(guestAccreditationService, Mockito.times(1))
            .completeVerificationOfBasisId(dto.getConnectionId(), dto.getThreadId(),
                dto.getPresentationExchangeId(), dto.getVerified());
    }

    @Test
    void handleOnPresentProofIgnoredState() throws Exception {
        ACAPYPresentProofDto dto = new ACAPYPresentProofDto();
        ReflectionTestUtils.setField(dto, "state", "presentation_received");

        guestAccreditationWebhookService.handleOnPresentProof(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Ignoring Proof Request state: " + dto.getState());
        Mockito.verifyNoInteractions(guestAccreditationService);
    }

    @Test
    void handleOnPresentProofUnknownState() throws Exception {
        ACAPYPresentProofDto dto = new ACAPYPresentProofDto();
        ReflectionTestUtils.setField(dto, "state", "state");

        guestAccreditationWebhookService.handleOnPresentProof(dto);

        Mockito.verify(logger, Mockito.times(1))
            .debug("Unknown Proof Request state: " + dto.getState());
        Mockito.verifyNoInteractions(guestAccreditationService);
    }
}
