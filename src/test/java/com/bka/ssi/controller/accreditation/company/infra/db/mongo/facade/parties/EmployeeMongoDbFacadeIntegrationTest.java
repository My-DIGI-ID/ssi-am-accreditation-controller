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
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties.EmployeeMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties.EmployeeMongoDbRepository;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbTestConversionsConfiguration;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbUtility;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = {EmbeddedMongoAutoConfiguration.class,
    MongoDbConfiguration.class})
@Import(MongoDbTestConversionsConfiguration.class)
class EmployeeMongoDbFacadeIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer =
        new MongoDBContainer(MongoDbUtility.MONGODB_DOCKER_IMAGE);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private final Logger logger =
        LoggerFactory.getLogger(EmployeeMongoDbFacadeIntegrationTest.class);
    private EmployeeMongoDbMapper mapper;
    @Autowired
    private EmployeeMongoDbRepository repository;
    private EmployeeMongoDbFacade facade;

    private EmployeeBuilder builder;

    Employee employee;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() {
        mapper = new EmployeeMongoDbMapper(logger);
        facade = new EmployeeMongoDbFacade(repository, mapper, logger);
        builder = new EmployeeBuilder();

        employee = builder.buildEmployee();
        facade.save(employee);
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
        Employee result = facade.save(employee);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(employee.getId(), result.getId());
    }

    @Test
    public void saveAll() {
        List<Employee> result = (List<Employee>) facade.saveAll(Arrays.asList(employee));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void findById() {
        Optional<Employee> result = facade.findById(employee.getId());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(employee.getId(), result.get().getId());
    }

    @Test
    public void findAll() {
        List<Employee> result = (List<Employee>) facade.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void deleteById() {
        facade.deleteById(employee.getId());
        Assertions.assertTrue(facade.findById(employee.getId()).isEmpty());
    }

    @Test
    public void deleteAll() {
        facade.deleteAll();
        Assertions.assertEquals(0, ((List<Employee>) facade.findAll()).size());
    }

    @Test
    public void findByIdAndCreatedBy() {
        Optional<Employee> result = facade.findByIdAndCreatedBy(employee.getId(),
            employee.getCreatedBy());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals(employee.getId(), result.get().getId());
        Assertions.assertEquals(employee.getCreatedBy(), result.get().getCreatedBy());
    }

    @Test
    public void findAllByCreatedBy() {
        List<Employee> result = facade.findAllByCreatedBy(employee.getCreatedBy());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(employee.getId(), result.get(0).getId());
        Assertions.assertEquals(employee.getCreatedBy(), result.get(0).getCreatedBy());
    }
}