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
