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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.security;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security.GuestAccessTokenMongoDbDocument;
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

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class GuestAccessTokenMongoDbRepositoryTest {

    @Autowired
    private GuestAccessTokenMongoDbRepository repository;

    GuestAccessTokenMongoDbDocument document;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() {
        // ToDo - Improvement: implement GuestAccessTokenMongoDbDocument builder
        document = new GuestAccessTokenMongoDbDocument();

        document.setId("id");
        document.setAccreditationId("accreditationId");
        repository.save(document);
    }

    @AfterAll
    static void clean() {
    }

    @AfterEach
    void teardown() {
        repository.deleteAll();
    }

    @Test
    void deleteByAccreditationId() {
        repository.deleteByAccreditationId("accreditationId");
        Assertions.assertTrue(repository.findById("id").isEmpty());
    }
}
