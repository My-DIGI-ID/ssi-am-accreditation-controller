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

import com.bka.ssi.controller.accreditation.company.application.repositories.parties.GuestRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.GuestMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties.GuestMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties.GuestMongoDbRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Guest mongo db facade.
 */
@Service
public class GuestMongoDbFacade implements GuestRepository {

    private final GuestMongoDbRepository repository;
    private final GuestMongoDbMapper mapper;
    private final Logger logger;

    /**
     * Instantiates a new Guest mongo db facade.
     *
     * @param guestMongoDbRepository the guest mongo db repository
     * @param guestMongoDbMapper     the guest mongo db mapper
     * @param logger                 the logger
     */
    public GuestMongoDbFacade(GuestMongoDbRepository guestMongoDbRepository,
        GuestMongoDbMapper guestMongoDbMapper, Logger logger) {
        this.repository = guestMongoDbRepository;
        this.mapper = guestMongoDbMapper;
        this.logger = logger;
    }

    @Override
    public Guest save(Guest guest)
        throws InvalidValidityTimeframeException {
        logger.debug("Saving guest");

        GuestMongoDbDocument document = mapper.entityToDocument(guest);
        GuestMongoDbDocument savedDocument = repository.save(document);

        Guest savedGuest = mapper.documentToEntity(savedDocument);

        return savedGuest;
    }

    @Override
    public <S extends Guest> Iterable<S> saveAll(Iterable<S> iterable)
        throws InvalidValidityTimeframeException {
        logger.debug("Saving guests");

        List<GuestMongoDbDocument> documents = new ArrayList<>();
        iterable.forEach(guest -> documents.add(this.mapper.entityToDocument(guest)));

        Iterable<GuestMongoDbDocument> savedDocuments = this.repository.saveAll(documents);

        /* ToDo - List<S> looks weird, should actually be List<Guest> */
        List<S> savedGuests = new ArrayList<>();

        for (GuestMongoDbDocument savedDocument : savedDocuments) {
            savedGuests.add((S) this.mapper.documentToEntity(savedDocument));
        }

        return savedGuests;
    }

    @Override
    public Optional<Guest> findById(String id)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest with id " + id);

        Optional<GuestMongoDbDocument> document = this.repository.findById(id);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        Guest guest = this.mapper.documentToEntity(document.get());

        return Optional.of(guest);
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Operation existsById is not yet implemented");
    }

    @Override
    public Iterable<Guest> findAll()
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching all guests");

        List<GuestMongoDbDocument> documents = this.repository.findAll();

        List<Guest> guests = new ArrayList<>();
        for (GuestMongoDbDocument document : documents) {
            guests.add(this.mapper.documentToEntity(document));
        }

        return guests;
    }

    @Override
    public Iterable<Guest> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Operation findAllById is not yet implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Operation count is not yet implemented");
    }

    @Override
    public void deleteById(String id) {
        logger.debug("Deleting guest with id " + id);

        this.repository.deleteById(id);
    }

    @Override
    public void delete(Guest guest) {
        throw new UnsupportedOperationException("Operation delete is not yet implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> iterable) {
        throw new UnsupportedOperationException("Operation deleteAllById is not yet implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Guest> iterable) {
        throw new UnsupportedOperationException("Operation deleteAll is not yet implemented");
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    public Optional<Guest> findByPartyParams(String referenceBasisId, String firstName,
        String lastName, String dateOfBirth, String companyName, ZonedDateTime validFrom,
        ZonedDateTime validUntil, String invitedBy)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest by various party params");

        Optional<GuestMongoDbDocument> document =
            this.repository.findByPartyParams(referenceBasisId, firstName, lastName,
                dateOfBirth, companyName, validFrom, validUntil, invitedBy);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        Guest guest = this.mapper.documentToEntity(document.get());

        return Optional.of(guest);
    }

    @Override
    public List<Guest> findAllByCredentialInvitedBy(String invitedBy)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching all guests by invitedBy " + invitedBy);

        List<GuestMongoDbDocument> documents =
            this.repository.findAllByCredentialOfferCredentialInvitedBy(invitedBy);

        List<Guest> guests = new ArrayList<>();
        for (GuestMongoDbDocument document : documents) {
            guests.add(this.mapper.documentToEntity(document));
        }

        return guests;
    }

    @Override
    public Optional<Guest> findByIdAndCreatedBy(String id, String createdBy)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest with id " + id + " and creator " + createdBy);

        Optional<GuestMongoDbDocument> document =
            this.repository.findByIdAndCreatedBy(id, createdBy);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        Guest guest = this.mapper.documentToEntity(document.get());

        return Optional.of(guest);
    }

    @Override
    public List<Guest> findAllByCreatedBy(String createdBy)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching all guests by creator " + createdBy);

        List<GuestMongoDbDocument> documents = this.repository.findAllByCreatedBy(createdBy);

        List<Guest> guests = new ArrayList<>();
        for (GuestMongoDbDocument document : documents) {
            guests.add(this.mapper.documentToEntity(document));
        }

        return guests;
    }
}
