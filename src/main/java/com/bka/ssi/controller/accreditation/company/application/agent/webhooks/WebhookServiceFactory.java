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

package com.bka.ssi.controller.accreditation.company.application.agent.webhooks;

import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.accreditation.EmployeeAccreditationWebhookService;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.accreditation.GuestAccreditationWebhookService;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.EmployeeAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.GuestAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The type Webhook service factory.
 */
@Component
public class WebhookServiceFactory {

    private final Logger logger;

    private final GuestAccreditationWebhookService guestAccreditationWebhookService;
    private final EmployeeAccreditationWebhookService employeeAccreditationWebhookService;
    private final GuestAccreditationRepository guestAccreditationRepository;
    private final EmployeeAccreditationRepository employeeAccreditationRepository;

    /**
     * Instantiates a new Webhook service factory.
     *
     * @param logger                              the logger
     * @param guestAccreditationWebhookService    the guest accreditation webhook service
     * @param employeeAccreditationWebhookService the employee accreditation webhook service
     * @param guestAccreditationRepository        the guest accreditation repository
     * @param employeeAccreditationRepository     the employee accreditation repository
     */
    public WebhookServiceFactory(Logger logger,
        GuestAccreditationWebhookService guestAccreditationWebhookService,
        EmployeeAccreditationWebhookService employeeAccreditationWebhookService,
        @Qualifier("guestAccreditationMongoDbFacade")
            GuestAccreditationRepository guestAccreditationRepository,
        @Qualifier("employeeAccreditationMongoDbFacade")
            EmployeeAccreditationRepository employeeAccreditationRepository) {
        this.logger = logger;
        this.guestAccreditationWebhookService = guestAccreditationWebhookService;
        this.employeeAccreditationWebhookService = employeeAccreditationWebhookService;
        this.guestAccreditationRepository = guestAccreditationRepository;
        this.employeeAccreditationRepository = employeeAccreditationRepository;
    }

    private WebhookService getWebhookServiceByAccreditationId(String accreditationId)
        throws Exception {
        Optional<GuestAccreditation> guestAccreditation =
            guestAccreditationRepository.findById(accreditationId);

        if (guestAccreditation.isPresent()) {
            return guestAccreditationWebhookService;
        }

        Optional<EmployeeAccreditation> employeeAccreditation =
            employeeAccreditationRepository.findById(accreditationId);

        if (employeeAccreditation.isPresent()) {
            return employeeAccreditationWebhookService;
        }

        logger.error("Accreditation with id {} not found on either Guest or Employee "
            + "repositories", accreditationId);
        throw new NotFoundException();
    }

    private WebhookService getWebhookServiceByCredentialIssuanceCorrelationConnectionId(
        String connectionId
    ) throws Exception {
        Optional<GuestAccreditation> guestAccreditation =
            guestAccreditationRepository.findByGuestCredentialIssuanceCorrelationConnectionId(
                connectionId);

        if (guestAccreditation.isPresent()) {
            return guestAccreditationWebhookService;
        }

        Optional<EmployeeAccreditation> employeeAccreditation =
            employeeAccreditationRepository.findByEmployeeCredentialIssuanceCorrelationConnectionId(
                connectionId);

        if (employeeAccreditation.isPresent()) {
            return employeeAccreditationWebhookService;
        }

        logger.error("Accreditation with connectionId {} not found on either Guest or Employee "
            + "repositories", connectionId);
        throw new NotFoundException();
    }

    /**
     * Handle on connection.
     *
     * @param inputDto the input dto
     * @throws Exception the exception
     */
    public void handleOnConnection(ACAPYConnectionDto inputDto) throws Exception {
        WebhookService webhookService =
            getWebhookServiceByAccreditationId(inputDto.getAlias());

        webhookService.handleOnConnection(inputDto);
    }

    /**
     * Handle on issue credential.
     *
     * @param inputDto the input dto
     * @throws Exception the exception
     */
    public void handleOnIssueCredential(ACAPYIssueCredentialDto inputDto) throws Exception {
        WebhookService webhookService =
            getWebhookServiceByCredentialIssuanceCorrelationConnectionId(
                inputDto.getConnectionId());

        webhookService.handleOnIssueCredential(inputDto);
    }

    /**
     * Handle on present proof.
     *
     * @param inputDto the input dto
     * @throws Exception the exception
     */
    public void handleOnPresentProof(ACAPYPresentProofDto inputDto) throws Exception {
        guestAccreditationWebhookService.handleOnPresentProof(inputDto);
    }
}
