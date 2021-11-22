package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.GuestAccreditationMongoDbDocument;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class GuestAccreditationMongoDbRepositoryTest {

    @Autowired
    private GuestAccreditationMongoDbRepository repository;

    GuestAccreditationMongoDbDocument document;
    GuestAccreditationMongoDbDocument otherDocument;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() {
        // ToDo - Improvement: implement GuestAccreditationMongoDbDocument builder
        document = new GuestAccreditationMongoDbDocument();

        CorrelationMongoDbDocument correlation = new CorrelationMongoDbDocument();
        correlation.setConnectionId("connectionId");
        correlation.setThreadId("threadId");

        document.setId("id");
        document.setPartyId("partyId");
        document.setInvitedBy("employee-01");
        document.setBasisIdVerificationCorrelation(correlation);
        document.setStatus("OPEN");
        repository.save(document);

        otherDocument = new GuestAccreditationMongoDbDocument();
        otherDocument.setId("otherId");
        otherDocument.setPartyId("partyId");
        otherDocument.setInvitedBy("employee-02");
        otherDocument.setGuestCredentialIssuanceCorrelation(correlation);
        otherDocument.setStatus("PENDING");
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
        List<GuestAccreditationMongoDbDocument> result = repository.findAllByPartyId("partyId");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void findByPartyId() {
        // ToDo - findByPartyId might be obsolete, reconsider to remove when not used
        repository.deleteById("otherId");

        Optional<GuestAccreditationMongoDbDocument> result = repository.findByPartyId("partyId");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("partyId", result.get().getPartyId());
    }

    @Test
    void findByIdAndInvitedBy() {
        Optional<GuestAccreditationMongoDbDocument> result = repository.findByIdAndInvitedBy(
            "id", "employee-01");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("employee-01", result.get().getInvitedBy());
    }

    @Test
    void findAllByInvitedBy() {
        List<GuestAccreditationMongoDbDocument> result =
            repository.findAllByInvitedBy("employee-01");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void findAllByInvitedByAndStatusIsNot() {
        List<GuestAccreditationMongoDbDocument> result =
            repository.findAllByInvitedByAndStatusIsNot("employee-01", Arrays
                .asList("OPEN", "PENDING"));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void findAllByStatus() {
        List<GuestAccreditationMongoDbDocument> result = repository.findAllByStatus("OPEN");
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void findAllByStatusIsNot() {
        List<GuestAccreditationMongoDbDocument> result =
            repository.findAllByStatusIsNot("ACCEPTED");
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void countByStatus() {
        long result = repository.countByStatus("OPEN");
        Assertions.assertEquals(1, result);
    }

    @Test
    void countByStatusIsNot() {
        long result = repository.countByStatusIsNot("ACCEPTED");
        Assertions.assertEquals(2, result);
    }

    @Test
    void findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId() {
        Optional<GuestAccreditationMongoDbDocument> result =
            repository
                .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                    "connectionId", "threadId");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("connectionId",
            result.get().getBasisIdVerificationCorrelation().getConnectionId());
        Assertions.assertEquals("threadId",
            result.get().getBasisIdVerificationCorrelation().getThreadId());
    }

    @Test
    void findByGuestCredentialIssuanceCorrelationConnectionId() {
        Optional<GuestAccreditationMongoDbDocument> result =
            repository.findByGuestCredentialIssuanceCorrelationConnectionId("connectionId");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("otherId", result.get().getId());
        Assertions.assertEquals("connectionId",
            result.get().getGuestCredentialIssuanceCorrelation().getConnectionId());
    }

    @Test
    void findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId() {
        Optional<GuestAccreditationMongoDbDocument> result = repository
            .findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId(
                "connectionId", "threadId");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("otherId", result.get().getId());
        Assertions.assertEquals("connectionId",
            result.get().getGuestCredentialIssuanceCorrelation().getConnectionId());
        Assertions.assertEquals("threadId",
            result.get().getGuestCredentialIssuanceCorrelation().getThreadId());
    }
}
