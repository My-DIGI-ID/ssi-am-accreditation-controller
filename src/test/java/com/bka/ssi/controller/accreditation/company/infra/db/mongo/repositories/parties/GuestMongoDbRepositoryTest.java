package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.GuestMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialOfferMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PersonaMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ValidityTimeframeDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.GuestCredentialMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.GuestPrivateInformationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.testutilities.MongoDbTestConversionsConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoDbTestConversionsConfiguration.class)
public class GuestMongoDbRepositoryTest {

    @Autowired
    private GuestMongoDbRepository repository;

    GuestMongoDbDocument document;

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setup() {
        // ToDo - Improvement: implement GuestMongoDbDocument builder
        document = new GuestMongoDbDocument();

        PersonaMongoDbValue persona = new PersonaMongoDbValue();
        persona.setFirstName("firstName");
        persona.setLastName("lastName");

        GuestPrivateInformationMongoDbValue privateInformation =
            new GuestPrivateInformationMongoDbValue();
        privateInformation.setDateOfBirth("dateOfBirth");

        ValidityTimeframeDbValue validityTimeframe = new ValidityTimeframeDbValue();
        validityTimeframe
            .setValidFrom(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC")));
        validityTimeframe
            .setValidUntil(ZonedDateTime.of(2023, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC")));

        GuestCredentialMongoDbValue credential = new GuestCredentialMongoDbValue();
        credential.setReferenceBasisId("referenceBasisId");
        credential.setPersona(persona);
        credential.setGuestPrivateInformation(privateInformation);
        credential.setCompanyName("companyName");
        credential.setValidityTimeframe(validityTimeframe);
        credential.setInvitedBy("invitedBy");
        CredentialOfferMongoDbValue<GuestCredentialMongoDbValue> credentialOffer =
            new CredentialOfferMongoDbValue<>();
        credentialOffer.setCredential(credential);

        document.setId("id");
        document.setCreatedBy("createdBy");
        document.setCredentialOffer(credentialOffer);
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
    void findByPartyParams() {
        GuestMongoDbDocument doc = repository.findById("id").get();
        Optional<GuestMongoDbDocument> result = repository.findByPartyParams("referenceBasisId",
            "firstName", "lastName", "dateOfBirth", "companyName",
            ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC")),
            "invitedBy");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("referenceBasisId",
            result.get().getCredentialOffer().getCredential().getReferenceBasisId());
        Assertions.assertEquals("firstName",
            result.get().getCredentialOffer().getCredential().getPersona().getFirstName());
        Assertions.assertEquals("lastName",
            result.get().getCredentialOffer().getCredential().getPersona().getLastName());
        Assertions.assertEquals("dateOfBirth",
            result.get().getCredentialOffer().getCredential().getGuestPrivateInformation()
                .getDateOfBirth());
        Assertions.assertEquals("companyName",
            result.get().getCredentialOffer().getCredential().getCompanyName());
        Assertions.assertTrue(
            result.get().getCredentialOffer().getCredential().getValidityTimeframe().getValidFrom()
                .isEqual(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"))
                ));
        Assertions.assertTrue(
            result.get().getCredentialOffer().getCredential().getValidityTimeframe().getValidUntil()
                .isEqual(ZonedDateTime.of(2023, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"))
                ));
        Assertions.assertEquals("invitedBy",
            result.get().getCredentialOffer().getCredential().getInvitedBy());
    }

    @Test
    void findByIdAndCreatedBy() {
        Optional<GuestMongoDbDocument> result = repository.findByIdAndCreatedBy(
            "id", "createdBy");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertEquals("id", result.get().getId());
        Assertions.assertEquals("createdBy", result.get().getCreatedBy());
    }

    @Test
    void findAllByCreatedBy() {
        List<GuestMongoDbDocument> result = repository.findAllByCreatedBy("createdBy");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void findAllByCredentialOfferCredentialInvitedBy() {
        List<GuestMongoDbDocument> result =
            repository.findAllByCredentialOfferCredentialInvitedBy("invitedBy");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }
}
