package com.bka.ssi.controller.accreditation.company.application.agent.webwooks.accreditation;

import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.WebhookService;
import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.EmployeeAccreditationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAccreditationWebhookService implements WebhookService {

    private final Logger logger;
    private final EmployeeAccreditationService employeeAccreditationService;

    public EmployeeAccreditationWebhookService(Logger logger,
        EmployeeAccreditationService employeeAccreditationService) {
        this.logger = logger;
        this.employeeAccreditationService = employeeAccreditationService;
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
                logger.debug("Connection state: " + inputDto.getState());
                break;
            case "response":
                logger.debug("Connection state: " + inputDto.getState());
                employeeAccreditationService.offerAccreditation(inputDto.getAlias());
                break;
            default:
                logger.debug("Connection state: " + inputDto.getState());
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
                employeeAccreditationService.completeAccreditation(inputDto.getConnectionId(),
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
        throw new UnsupportedOperationException("PresentProof Webhook not supported for Employee "
            + "Accreditation");
    }
}
