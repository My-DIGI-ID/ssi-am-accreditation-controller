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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.accreditations;

import com.bka.ssi.controller.accreditation.company.aop.configuration.db.mongodb.MongoDbConfiguration;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties.GuestMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.accreditations.GuestAccreditationMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties.GuestMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations.GuestAccreditationMongoDbRepository;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties.GuestMongoDbRepository;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbTestConversionsConfiguration;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbUtility;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.GuestAccreditationBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = {EmbeddedMongoAutoConfiguration.class,
    MongoDbConfiguration.class})
@Import(MongoDbTestConversionsConfiguration.class)
class GuestAccreditationMongoDbFacadeIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer =
        new MongoDBContainer(MongoDbUtility.MONGODB_DOCKER_IMAGE);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private final Logger logger =
        LoggerFactory.getLogger(GuestAccreditationMongoDbFacadeIntegrationTest.class);
    private GuestAccreditationMongoDbMapper mapper;
    @Autowired
    private GuestAccreditationMongoDbRepository repository;
    private GuestAccreditationMongoDbFacade facade;
    private GuestMongoDbMapper guestMongoDbMapper;
    @Autowired
    private GuestMongoDbRepository guestMongoDbRepository;
    private GuestMongoDbFacade guestMongoDbFacade;

    private GuestAccreditationBuilder builder;

    GuestAccreditation guestAccreditation;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() throws Exception {
        guestMongoDbMapper = new GuestMongoDbMapper(logger);
        guestMongoDbFacade = new GuestMongoDbFacade(guestMongoDbRepository, guestMongoDbMapper,
            logger);
        mapper = new GuestAccreditationMongoDbMapper(logger, guestMongoDbFacade);
        facade = new GuestAccreditationMongoDbFacade(repository, mapper, guestMongoDbFacade,
            logger);
        builder = new GuestAccreditationBuilder();

        guestAccreditation = builder.buildGuestAccreditation();
        guestMongoDbFacade.save(guestAccreditation.getParty());
        facade.save(guestAccreditation);
    }

    @AfterAll
    static void clean() {
    }

    @AfterEach
    void teardown() {
        facade.deleteAll();
    }

    @Test
    public void save() throws Exception {
        GuestAccreditation result = facade.save(guestAccreditation);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(guestAccreditation.getId(), result.getId());
    }

    @Test
    public void findById() throws Exception {
        Optional<GuestAccreditation> result = facade.findById(guestAccreditation.getId());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(guestAccreditation.getId(), result.get().getId());
    }

    @Test
    public void findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId()
        throws Exception {
        Optional<GuestAccreditation> result =
            facade
                .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                    guestAccreditation.getBasisIdVerificationCorrelation().getConnectionId(),
                    guestAccreditation.getBasisIdVerificationCorrelation().getThreadId());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions
            .assertEquals(guestAccreditation.getBasisIdVerificationCorrelation().getConnectionId(),
                result.get().getBasisIdVerificationCorrelation().getConnectionId());
        Assertions
            .assertEquals(guestAccreditation.getBasisIdVerificationCorrelation().getThreadId(),
                result.get().getBasisIdVerificationCorrelation().getThreadId());
    }

    @Test
    public void findByPartyId() throws Exception {
        Optional<GuestAccreditation> result =
            facade.findByPartyId(guestAccreditation.getParty().getId());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions
            .assertEquals(guestAccreditation.getParty().getId(), result.get().getParty().getId());
    }

    @Test
    public void findByGuestCredentialIssuanceCorrelationConnectionId() throws Exception {
        Optional<GuestAccreditation> result =
            facade.findByGuestCredentialIssuanceCorrelationConnectionId(
                guestAccreditation.getGuestCredentialIssuanceCorrelation().getConnectionId());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions
            .assertEquals(
                guestAccreditation.getGuestCredentialIssuanceCorrelation().getConnectionId(),
                result.get().getGuestCredentialIssuanceCorrelation().getConnectionId());
    }

    @Test
    public void findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId()
        throws Exception {
        Optional<GuestAccreditation> result =
            facade
                .findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId(
                    guestAccreditation.getGuestCredentialIssuanceCorrelation().getConnectionId(),
                    guestAccreditation.getGuestCredentialIssuanceCorrelation().getThreadId());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions
            .assertEquals(
                guestAccreditation.getGuestCredentialIssuanceCorrelation().getConnectionId(),
                result.get().getGuestCredentialIssuanceCorrelation().getConnectionId());
        Assertions
            .assertEquals(
                guestAccreditation.getGuestCredentialIssuanceCorrelation().getThreadId(),
                result.get().getGuestCredentialIssuanceCorrelation().getThreadId());
    }

    @Test
    public void findAllByStatus() throws Exception {
        List<GuestAccreditation> result = facade.findAllByStatus(guestAccreditation.getStatus());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void findAllByStatusIsNot() throws Exception {
        List<GuestAccreditation> result =
            facade.findAllByStatusIsNot(guestAccreditation.getStatus());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void countByStatus() throws Exception {
        long result = facade.countByStatus(guestAccreditation.getStatus());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void countByStatusIsNot() throws Exception {
        long result = facade.countByStatusIsNot(guestAccreditation.getStatus());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void findByPartyParams() throws Exception {
        Optional<GuestAccreditation> result =
            facade.findByPartyParams(
                guestAccreditation.getParty().getCredentialOffer().getCredential()
                    .getReferenceBasisId(),
                guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                    .getFirstName(),
                guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                    .getLastName(),
                guestAccreditation.getParty().getCredentialOffer().getCredential()
                    .getGuestPrivateInformation()
                    .getDateOfBirth(),
                guestAccreditation.getParty().getCredentialOffer().getCredential().getCompanyName(),
                guestAccreditation.getParty().getCredentialOffer().getCredential()
                    .getValidityTimeframe().getValidFrom(),
                guestAccreditation.getParty().getCredentialOffer().getCredential()
                    .getValidityTimeframe().getValidUntil(),
                guestAccreditation.getParty().getCredentialOffer().getCredential().getInvitedBy());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getReferenceBasisId(),
            result.get().getParty().getCredentialOffer().getCredential().getReferenceBasisId());
        Assertions
            .assertEquals(
                guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                    .getFirstName(),
                result.get().getParty().getCredentialOffer().getCredential().getPersona()
                    .getFirstName());
        Assertions
            .assertEquals(
                guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                    .getLastName(),
                result.get().getParty().getCredentialOffer().getCredential().getPersona()
                    .getLastName());
        Assertions.assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation()
                .getDateOfBirth(),
            result.get().getParty().getCredentialOffer().getCredential()
                .getGuestPrivateInformation()
                .getDateOfBirth());
        Assertions.assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getCompanyName(),
            result.get().getParty().getCredentialOffer().getCredential().getCompanyName());
        Assertions.assertTrue(
            guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getValidityTimeframe().getValidFrom()
                .truncatedTo(ChronoUnit.MILLIS).isEqual(
                result.get().getParty().getCredentialOffer().getCredential().getValidityTimeframe()
                    .getValidFrom()));
        Assertions.assertTrue(
            guestAccreditation.getParty().getCredentialOffer().getCredential()
                .getValidityTimeframe().getValidUntil()
                .truncatedTo(ChronoUnit.MILLIS).isEqual(
                result.get().getParty().getCredentialOffer().getCredential().getValidityTimeframe()
                    .getValidUntil()));
        Assertions.assertEquals(
            guestAccreditation.getParty().getCredentialOffer().getCredential().getInvitedBy(),
            result.get().getParty().getCredentialOffer().getCredential().getInvitedBy());
    }

    @Test
    public void findAllByCredentialInvitedBy() throws Exception {
        List<GuestAccreditation> result =
            facade.findAllByCredentialInvitedBy(
                guestAccreditation.getParty().getCredentialOffer().getCredential().getInvitedBy());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void findAllByPartyId() throws Exception {
        List<GuestAccreditation> result =
            facade.findAllByPartyId(guestAccreditation.getParty().getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void findAll() throws Exception {
        List<GuestAccreditation> result = (List<GuestAccreditation>) facade.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void count() throws Exception {
        long result = facade.count();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void deleteAll() throws Exception {
        facade.deleteAll();
        Assertions.assertEquals(0, ((List<GuestAccreditation>) facade.findAll()).size());
    }

    @Test
    public void findByIdAndInvitedBy() throws Exception {
        Optional<GuestAccreditation> result =
            facade.findByIdAndInvitedBy(guestAccreditation.getId(),
                guestAccreditation.getInvitedBy());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions
            .assertEquals(guestAccreditation.getId(), result.get().getId());
        Assertions
            .assertEquals(guestAccreditation.getInvitedBy(), result.get().getInvitedBy());
    }

    @Test
    public void findAllByInvitedBy() throws Exception {
        List<GuestAccreditation> result =
            facade.findAllByInvitedBy(guestAccreditation.getInvitedBy());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void findAllByInvitedByAndValidStatus() throws Exception {
        List<GuestAccreditation> result =
            facade.findAllByInvitedByAndValidStatus(guestAccreditation.getInvitedBy(),
                Arrays.asList(GuestAccreditationStatus.REVOKED));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }
}