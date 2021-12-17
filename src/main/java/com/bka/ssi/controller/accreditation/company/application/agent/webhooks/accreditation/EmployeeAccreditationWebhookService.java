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

package com.bka.ssi.controller.accreditation.company.application.agent.webhooks.accreditation;

import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.WebhookService;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.EmployeeAccreditationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * The type Employee accreditation webhook service.
 */
@Service
public class EmployeeAccreditationWebhookService implements WebhookService {

    private final Logger logger;
    private final EmployeeAccreditationService employeeAccreditationService;

    /**
     * Instantiates a new Employee accreditation webhook service.
     *
     * @param logger                       the logger
     * @param employeeAccreditationService the employee accreditation service
     */
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
                logger.debug("Ignoring Connection state: " + inputDto.getState());
                break;
            case "response":
                logger.debug("Connection state: " + inputDto.getState());
                employeeAccreditationService.offerAccreditation(inputDto.getAlias());
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
