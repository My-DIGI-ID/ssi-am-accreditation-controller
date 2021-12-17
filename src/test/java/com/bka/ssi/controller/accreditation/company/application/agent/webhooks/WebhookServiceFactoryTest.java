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
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee.EmployeeAccreditationBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.GuestAccreditationBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

public class WebhookServiceFactoryTest {

    private final Logger logger =
        LoggerFactory.getLogger(WebhookServiceFactoryTest.class);

    @Mock
    GuestAccreditationRepository guestAccreditationRepository;

    @Mock
    EmployeeAccreditationRepository employeeAccreditationRepository;

    @Mock
    GuestAccreditationWebhookService guestAccreditationWebhookService;

    @Mock
    EmployeeAccreditationWebhookService employeeAccreditationWebhookService;

    @InjectMocks
    private WebhookServiceFactory webhookServiceFactory;

    private EmployeeAccreditation employeeAccreditation;
    private GuestAccreditation guestAccreditation;

    private ACAPYConnectionDto acapyConnectionDto;
    private ACAPYIssueCredentialDto acapyIssueCredentialDto;
    private ACAPYPresentProofDto acapyPresentProofDto;

    @BeforeEach
    void setup() throws InvalidValidityTimeframeException {
        MockitoAnnotations.openMocks(this);

        webhookServiceFactory = new WebhookServiceFactory(logger,
            guestAccreditationWebhookService, employeeAccreditationWebhookService,
            guestAccreditationRepository, employeeAccreditationRepository);

        EmployeeAccreditationBuilder employeeAccreditationBuilder =
            new EmployeeAccreditationBuilder();
        employeeAccreditation = employeeAccreditationBuilder.buildEmployeeAccreditation();

        GuestBuilder guestBuilder = new GuestBuilder();
        guestBuilder.invitedBy = "unittest";
        guestBuilder.createdBy = "unittest";
        Guest guest = guestBuilder.buildGuest();

        GuestAccreditationBuilder guestAccreditationBuilder = new GuestAccreditationBuilder();
        guestAccreditationBuilder.invitedBy = "unittest";
        guestAccreditationBuilder.guest = guest;
        guestAccreditation = guestAccreditationBuilder.buildGuestAccreditation();

        acapyIssueCredentialDto = new ACAPYIssueCredentialDto();
        ReflectionTestUtils.setField(acapyIssueCredentialDto, "connectionId", "12345");

        acapyConnectionDto = new ACAPYConnectionDto();
        ReflectionTestUtils.setField(acapyConnectionDto, "alias", "23456");

        acapyPresentProofDto = new ACAPYPresentProofDto();
    }

    @Test
    void handleGuestOnConnection() throws Exception {
        Mockito.when(guestAccreditationRepository.findById(acapyConnectionDto.getAlias()))
            .thenReturn(Optional.of(guestAccreditation));

        webhookServiceFactory.handleOnConnection(acapyConnectionDto);

        Mockito.verify(guestAccreditationWebhookService, Mockito.times(1))
            .handleOnConnection(acapyConnectionDto);
    }

    @Test
    void handleEmployeeOnConnection() throws Exception {
        Mockito.when(guestAccreditationRepository.findById(acapyConnectionDto.getAlias()))
            .thenReturn(Optional.empty());

        Mockito.when(employeeAccreditationRepository.findById(acapyConnectionDto.getAlias()))
            .thenReturn(Optional.of(employeeAccreditation));

        webhookServiceFactory.handleOnConnection(acapyConnectionDto);

        Mockito.verify(employeeAccreditationWebhookService, Mockito.times(1))
            .handleOnConnection(acapyConnectionDto);
    }

    @Test
    void handleOnConnectionAccreditationNotFound() throws Exception {
        Mockito.when(guestAccreditationRepository.findById(acapyConnectionDto.getAlias()))
            .thenReturn(Optional.empty());

        Mockito.when(employeeAccreditationRepository.findById(acapyConnectionDto.getAlias()))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            webhookServiceFactory.handleOnConnection(acapyConnectionDto);
        });
    }

    @Test
    void handleGuestOnIssueCredential() throws Exception {
        Mockito.when(
            guestAccreditationRepository.findByGuestCredentialIssuanceCorrelationConnectionId(
                acapyIssueCredentialDto.getConnectionId()))
            .thenReturn(Optional.of(guestAccreditation));

        webhookServiceFactory.handleOnIssueCredential(acapyIssueCredentialDto);

        Mockito.verify(guestAccreditationWebhookService, Mockito.times(1))
            .handleOnIssueCredential(acapyIssueCredentialDto);
    }

    @Test
    void handleEmployeeOnIssueCredential() throws Exception {
        Mockito.when(
            guestAccreditationRepository.findByGuestCredentialIssuanceCorrelationConnectionId(
                acapyIssueCredentialDto.getConnectionId()))
            .thenReturn(Optional.empty());

        Mockito.when(
            employeeAccreditationRepository.findByEmployeeCredentialIssuanceCorrelationConnectionId(
                acapyIssueCredentialDto.getConnectionId()))
            .thenReturn(Optional.of(employeeAccreditation));

        webhookServiceFactory.handleOnIssueCredential(acapyIssueCredentialDto);

        Mockito.verify(employeeAccreditationWebhookService, Mockito.times(1))
            .handleOnIssueCredential(acapyIssueCredentialDto);
    }

    @Test
    void handleOnIssueCredentialAccreditationNotFound() throws Exception {
        Mockito.when(
            guestAccreditationRepository.findByGuestCredentialIssuanceCorrelationConnectionId(
                acapyIssueCredentialDto.getConnectionId()))
            .thenReturn(Optional.empty());

        Mockito.when(
            employeeAccreditationRepository.findByEmployeeCredentialIssuanceCorrelationConnectionId(
                acapyIssueCredentialDto.getConnectionId()))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            webhookServiceFactory.handleOnIssueCredential(acapyIssueCredentialDto);
        });
    }

    @Test
    void handleOnPresentProof() throws Exception {
        webhookServiceFactory.handleOnPresentProof(acapyPresentProofDto);

        Mockito.verify(guestAccreditationWebhookService, Mockito.times(1))
            .handleOnPresentProof(acapyPresentProofDto);
    }
}
