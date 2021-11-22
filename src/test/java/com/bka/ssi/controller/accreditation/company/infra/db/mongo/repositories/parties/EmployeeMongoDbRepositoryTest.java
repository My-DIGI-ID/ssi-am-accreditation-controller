package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.EmployeeMongoDbDocument;
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
public class EmployeeMongoDbRepositoryTest {

    @Autowired
    private EmployeeMongoDbRepository repository;

    EmployeeMongoDbDocument document;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() {
        // ToDo - Improvement: implement EmployeeMongoDbDocument builder
        document = new EmployeeMongoDbDocument();

        document.setId("id");
        document.setCreatedBy("createdBy");
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
    void findByIdAndCreatedBy() {
        Optional<EmployeeMongoDbDocument> result = repository.findByIdAndCreatedBy(
            "id", "createdBy");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("createdBy", result.get().getCreatedBy());
    }

    @Test
    void findAllByCreatedBy() {
        List<EmployeeMongoDbDocument> result = repository.findAllByCreatedBy("createdBy");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }
}
