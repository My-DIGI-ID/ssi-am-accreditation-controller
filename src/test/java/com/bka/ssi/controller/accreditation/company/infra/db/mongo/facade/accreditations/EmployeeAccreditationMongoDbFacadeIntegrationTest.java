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
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties.EmployeeMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.accreditations.EmployeeAccreditationMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties.EmployeeMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations.EmployeeAccreditationMongoDbRepository;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties.EmployeeMongoDbRepository;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbTestConversionsConfiguration;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbUtility;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee.EmployeeAccreditationBuilder;
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

import java.util.List;
import java.util.Optional;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = {EmbeddedMongoAutoConfiguration.class,
    MongoDbConfiguration.class})
@Import(MongoDbTestConversionsConfiguration.class)
public class EmployeeAccreditationMongoDbFacadeIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer =
        new MongoDBContainer(MongoDbUtility.MONGODB_DOCKER_IMAGE);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private final Logger logger =
        LoggerFactory.getLogger(EmployeeAccreditationMongoDbFacadeIntegrationTest.class);
    private EmployeeAccreditationMongoDbMapper mapper;
    @Autowired
    private EmployeeAccreditationMongoDbRepository repository;
    private EmployeeAccreditationMongoDbFacade facade;
    private EmployeeMongoDbMapper employeeMongoDbMapper;
    @Autowired
    private EmployeeMongoDbRepository employeeMongoDbRepository;
    private EmployeeMongoDbFacade employeeMongoDbFacade;

    private EmployeeAccreditationBuilder builder;

    EmployeeAccreditation employeeAccreditation;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() {
        employeeMongoDbMapper = new EmployeeMongoDbMapper(logger);
        employeeMongoDbFacade =
            new EmployeeMongoDbFacade(employeeMongoDbRepository, employeeMongoDbMapper, logger);
        mapper = new EmployeeAccreditationMongoDbMapper(logger, employeeMongoDbFacade);
        facade = new EmployeeAccreditationMongoDbFacade(repository, mapper, employeeMongoDbFacade,
            logger);
        builder = new EmployeeAccreditationBuilder();

        employeeAccreditation = builder.buildEmployeeAccreditation();
        employeeMongoDbFacade.save(employeeAccreditation.getParty());
        facade.save(employeeAccreditation);
    }

    @AfterAll
    static void clean() {
    }

    @AfterEach
    void teardown() {
        facade.deleteAll();
    }

    @Test
    public void save() {
        EmployeeAccreditation result = facade.save(employeeAccreditation);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(employeeAccreditation.getId(), result.getId());
    }

    @Test
    public void findById() {
        Optional<EmployeeAccreditation> result = facade.findById(employeeAccreditation.getId());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(employeeAccreditation.getId(), result.get().getId());
    }

    @Test
    public void findAll() {
        List<EmployeeAccreditation> result = (List<EmployeeAccreditation>) facade.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void findByEmployeeCredentialIssuanceCorrelationConnectionId() {
        Optional<EmployeeAccreditation> result =
            facade.findByEmployeeCredentialIssuanceCorrelationConnectionId(
                employeeAccreditation.getEmployeeCredentialIssuanceCorrelation().getConnectionId());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(
            employeeAccreditation.getEmployeeCredentialIssuanceCorrelation().getConnectionId(),
            result.get().getEmployeeCredentialIssuanceCorrelation().getConnectionId());
    }

    @Test
    public void findAllByPartyId() {
        List<EmployeeAccreditation> result =
            facade.findAllByPartyId(employeeAccreditation.getParty().getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void deleteAll() {
        facade.deleteAll();
        Assertions.assertEquals(0, ((List<EmployeeAccreditation>) facade.findAll()).size());
    }

    @Test
    public void findByIdAndInvitedBy() {
        Optional<EmployeeAccreditation> result =
            facade.findByIdAndInvitedBy(employeeAccreditation.getId(),
                employeeAccreditation.getInvitedBy());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(employeeAccreditation.getId(), result.get().getId());
        Assertions.assertEquals(employeeAccreditation.getInvitedBy(), result.get().getInvitedBy());
    }

    @Test
    public void findAllByInvitedBy() {
        List<EmployeeAccreditation> result =
            facade.findAllByInvitedBy(employeeAccreditation.getInvitedBy());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }
}
