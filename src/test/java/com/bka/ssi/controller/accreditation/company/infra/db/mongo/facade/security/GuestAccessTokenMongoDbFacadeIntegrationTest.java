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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.security;

import com.bka.ssi.controller.accreditation.company.aop.configuration.db.mongodb.MongoDbConfiguration;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.security.GuestAccessTokenMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.security.GuestAccessTokenMongoDbRepository;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbTestConversionsConfiguration;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbUtility;
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = {EmbeddedMongoAutoConfiguration.class,
    MongoDbConfiguration.class})
@Import(MongoDbTestConversionsConfiguration.class)
class GuestAccessTokenMongoDbFacadeIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer =
        new MongoDBContainer(MongoDbUtility.MONGODB_DOCKER_IMAGE);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private final Logger logger =
        LoggerFactory.getLogger(GuestAccessTokenMongoDbFacadeIntegrationTest.class);
    private GuestAccessTokenMongoDbMapper mapper;
    @Autowired
    private GuestAccessTokenMongoDbRepository repository;
    private GuestAccessTokenMongoDbFacade facade;

    GuestToken token;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() {
        mapper = new GuestAccessTokenMongoDbMapper(logger);
        facade = new GuestAccessTokenMongoDbFacade(repository, mapper, logger);

        // ToDo - Improvement: implement GuestToken builder
        token = new GuestToken("id", "accreditationId",
            ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC")));

        facade.save(token);
    }

    @AfterAll
    static void clean() {
    }

    @AfterEach
    void teardown() {
        facade.deleteAll();
    }

    @Test
    void save() {
        GuestToken result = facade.save(token);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(token.getId(), result.getId());
        Assertions.assertEquals(token.getAccreditationId(), result.getAccreditationId());
        Assertions.assertEquals(token.getExpiring(), result.getExpiring());
    }

    @Test
    public void deleteByAccreditationId() {
        facade.deleteByAccreditationId("accreditationId");
        Assertions.assertTrue(facade.findById("id").isEmpty());
    }

    @Test
    public void findById() {
        Optional<GuestToken> result = facade.findById("id");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(token.getId(), result.get().getId());
        Assertions.assertEquals(token.getAccreditationId(), result.get().getAccreditationId());
        Assertions.assertTrue(token.getExpiring().isEqual(result.get().getExpiring()));
    }

    @Test
    public void findAll() {
        List<GuestToken> result = (List<GuestToken>) facade.findAll();
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void deleteById() {
        facade.deleteById("id");
        Assertions.assertTrue(facade.findById("id").isEmpty());
    }

    @Test
    public void deleteAll() {
        facade.deleteAll();
        Assertions.assertEquals(0, ((List<GuestToken>) facade.findAll()).size());
    }
}