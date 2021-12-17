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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties;

import com.bka.ssi.controller.accreditation.company.aop.configuration.db.mongodb.MongoDbConfiguration;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties.GuestMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties.GuestMongoDbRepository;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbTestConversionsConfiguration;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbUtility;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
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
class GuestMongoDbFacadeIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer =
        new MongoDBContainer(MongoDbUtility.MONGODB_DOCKER_IMAGE);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private final Logger logger =
        LoggerFactory.getLogger(GuestMongoDbFacadeIntegrationTest.class);
    private GuestMongoDbMapper mapper;
    @Autowired
    private GuestMongoDbRepository repository;
    private GuestMongoDbFacade facade;

    private GuestBuilder builder;

    Guest guest;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() throws Exception {
        mapper = new GuestMongoDbMapper(logger);
        facade = new GuestMongoDbFacade(repository, mapper, logger);
        builder = new GuestBuilder();

        guest = builder.buildGuest();
        facade.save(guest);
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
        Guest result = facade.save(guest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(guest.getId(), result.getId());
    }

    @Test
    public void saveAll() throws Exception {
        List<Guest> result = (List<Guest>) facade.saveAll(Arrays.asList(guest));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void findById() throws Exception {
        Optional<Guest> result = facade.findById(guest.getId());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(guest.getId(), result.get().getId());
    }

    @Test
    public void findAll() throws Exception {
        List<Guest> result = (List<Guest>) facade.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void deleteById() throws Exception {
        facade.deleteById(guest.getId());
        Assertions.assertTrue(facade.findById(guest.getId()).isEmpty());
    }

    @Test
    public void deleteAll() throws Exception {
        facade.deleteAll();
        Assertions.assertEquals(0, ((List<Guest>) facade.findAll()).size());
    }

    @Test
    public void findByIdAndCreatedBy() throws Exception {
        Optional<Guest> result = facade.findByIdAndCreatedBy(guest.getId(),
            guest.getCreatedBy());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(guest.getId(), result.get().getId());
        Assertions.assertEquals(guest.getCreatedBy(), result.get().getCreatedBy());
    }

    @Test
    public void findAllByCreatedBy() throws Exception {
        List<Guest> result = facade.findAllByCreatedBy(guest.getCreatedBy());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(guest.getId(), result.get(0).getId());
        Assertions.assertEquals(guest.getCreatedBy(), result.get(0).getCreatedBy());
    }

    @Test
    public void findByPartyParams() throws Exception {
        Optional<Guest> result =
            facade.findByPartyParams(
                guest.getCredentialOffer().getCredential().getReferenceBasisId(),
                guest.getCredentialOffer().getCredential().getPersona().getFirstName(),
                guest.getCredentialOffer().getCredential().getPersona().getLastName(),
                guest.getCredentialOffer().getCredential().getGuestPrivateInformation()
                    .getDateOfBirth(),
                guest.getCredentialOffer().getCredential().getCompanyName(),
                guest.getCredentialOffer().getCredential().getValidityTimeframe().getValidFrom(),
                guest.getCredentialOffer().getCredential().getValidityTimeframe().getValidUntil(),
                guest.getCredentialOffer().getCredential().getInvitedBy());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(guest.getCredentialOffer().getCredential().getReferenceBasisId(),
            result.get().getCredentialOffer().getCredential().getReferenceBasisId());
        Assertions
            .assertEquals(guest.getCredentialOffer().getCredential().getPersona().getFirstName(),
                result.get().getCredentialOffer().getCredential().getPersona().getFirstName());
        Assertions
            .assertEquals(guest.getCredentialOffer().getCredential().getPersona().getLastName(),
                result.get().getCredentialOffer().getCredential().getPersona().getLastName());
        Assertions.assertEquals(
            guest.getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getDateOfBirth(),
            result.get().getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getDateOfBirth());
        Assertions.assertEquals(guest.getCredentialOffer().getCredential().getCompanyName(),
            result.get().getCredentialOffer().getCredential().getCompanyName());
        Assertions.assertTrue(
            guest.getCredentialOffer().getCredential().getValidityTimeframe().getValidFrom()
                .truncatedTo(ChronoUnit.MILLIS).isEqual(
                result.get().getCredentialOffer().getCredential().getValidityTimeframe()
                    .getValidFrom()));
        Assertions.assertTrue(
            guest.getCredentialOffer().getCredential().getValidityTimeframe().getValidUntil()
                .truncatedTo(ChronoUnit.MILLIS).isEqual(
                result.get().getCredentialOffer().getCredential().getValidityTimeframe()
                    .getValidUntil()));
        Assertions.assertEquals(guest.getCredentialOffer().getCredential().getInvitedBy(),
            result.get().getCredentialOffer().getCredential().getInvitedBy());
    }
}