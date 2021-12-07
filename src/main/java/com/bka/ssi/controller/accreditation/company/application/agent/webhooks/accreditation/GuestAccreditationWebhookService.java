package com.bka.ssi.controller.accreditation.company.application.agent.webhooks.accreditation;

import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.WebhookService;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.GuestAccreditationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GuestAccreditationWebhookService implements WebhookService {

    private final Logger logger;
    private final GuestAccreditationService guestAccreditationService;

    public GuestAccreditationWebhookService(Logger logger,
        GuestAccreditationService guestAccreditationService) {
        this.logger = logger;
        this.guestAccreditationService = guestAccreditationService;
    }

    public void handleOnConnection(
        ACAPYConnectionDto inputDto) throws Exception {

        switch (inputDto.getState()) {
            case "init":
            case "invitation":
            case "request":
            case "active":
            case "inactive":
            case "error":
                logger.debug("Ignoring Connection state: " + inputDto.getState());
                break;
            case "response":
                logger.debug("Connection state: " + inputDto.getState());
                guestAccreditationService
                    .verifyBasisId(inputDto.getAlias(), inputDto.getConnectionId());
                break;
            default:
                logger.debug("Unknown Connection state: " + inputDto.getState());
                break;
        }
    }

    public void handleOnIssueCredential(
        ACAPYIssueCredentialDto inputDto) throws Exception {

        switch (inputDto.getState()) {
            case "proposal_sent":
            case "proposal_received":
            case "offer_sent":
            case "offer_received":
            case "request_sent":
            case "request_received":
            case "credential_received":
            case "credential_acked":
                logger
                    .debug("Ignoring IssueCredential state: " + inputDto.getState());
                break;
            case "credential_issued":
                logger.debug("IssueCredential state: " + inputDto.getState());
                guestAccreditationService.completeAccreditation(inputDto.getConnectionId(),
                    inputDto.getCredentialExchangeId(), inputDto.getInitiator());
                break;
            default:
                logger
                    .debug("Unknown IssueCredential state: " + inputDto.getState());
                break;
        }
    }

    public void handleOnPresentProof(
        ACAPYPresentProofDto inputDto) throws Exception {

        switch (inputDto.getState()) {
            case "proposal_sent":
            case "proposal_received":
            case "request_sent":
            case "request_received":
            case "presentation_sent":
            case "presentation_received":
                logger.debug("Ignoring Proof Request state: " + inputDto.getState());
                break;
            case "verified":
                logger.debug("Proof Request state: " + inputDto.getState());
                guestAccreditationService.completeVerificationOfBasisId(inputDto.getConnectionId(),
                    inputDto.getThreadId(), inputDto.getPresentationExchangeId(),
                    inputDto.getVerified());
                break;
            default:
                logger.debug("Unknown Proof Request state: " + inputDto.getState());
                logger.debug(inputDto.toString());
                break;
        }
    }
}
