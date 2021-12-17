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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.EmployeeAccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CorrelationMongoDbDocument;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class EmployeeAccreditationMongoDbRepositoryTest {

    @Autowired
    private EmployeeAccreditationMongoDbRepository repository;

    EmployeeAccreditationMongoDbDocument document;
    EmployeeAccreditationMongoDbDocument otherDocument;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() {
        // ToDo - Improvement: implement EmployeeAccreditationMongoDbDocument builder
        document = new EmployeeAccreditationMongoDbDocument();

        CorrelationMongoDbDocument correlation = new CorrelationMongoDbDocument();
        correlation.setConnectionId("connectionId");

        document.setId("id");
        document.setPartyId("partyId");
        document.setInvitedBy("hr-admin-01");
        document.setEmployeeCredentialIssuanceCorrelation(correlation);
        repository.save(document);

        otherDocument = new EmployeeAccreditationMongoDbDocument();
        otherDocument.setId("otherId");
        otherDocument.setPartyId("partyId");
        otherDocument.setInvitedBy("hr-admin-01");
        repository.save(otherDocument);
    }

    @AfterAll
    static void clean() {
    }

    @AfterEach
    void teardown() {
        repository.deleteAll();
    }

    @Test
    void findAllByPartyId() {
        List<EmployeeAccreditationMongoDbDocument> result = repository.findAllByPartyId("partyId");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void findByPartyId() {
        // ToDo - findByPartyId might be obsolete, reconsider to remove when not used
        repository.deleteById("otherId");

        Optional<EmployeeAccreditationMongoDbDocument> result = repository.findByPartyId("partyId");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("partyId", result.get().getPartyId());
    }

    @Test
    void findByIdAndInvitedBy() {
        Optional<EmployeeAccreditationMongoDbDocument> result = repository.findByIdAndInvitedBy(
            "id", "hr-admin-01");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("hr-admin-01", result.get().getInvitedBy());
    }

    @Test
    void findAllByInvitedBy() {
        List<EmployeeAccreditationMongoDbDocument> result =
            repository.findAllByInvitedBy("hr-admin-01");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void findByEmployeeCredentialIssuanceCorrelationConnectionId() {
        Optional<EmployeeAccreditationMongoDbDocument> result =
            repository.findByEmployeeCredentialIssuanceCorrelationConnectionId(
                "connectionId");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("connectionId",
            result.get().getEmployeeCredentialIssuanceCorrelation().getConnectionId());
    }
}