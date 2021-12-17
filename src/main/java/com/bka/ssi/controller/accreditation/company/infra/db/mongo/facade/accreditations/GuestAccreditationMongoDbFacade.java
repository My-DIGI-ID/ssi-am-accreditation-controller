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

import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.GuestAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.AccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.GuestAccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties.GuestMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.accreditations.GuestAccreditationMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations.GuestAccreditationMongoDbRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Guest accreditation mongo db facade.
 */
@Service
public class GuestAccreditationMongoDbFacade implements GuestAccreditationRepository {

    private final GuestAccreditationMongoDbRepository repository;
    private final GuestAccreditationMongoDbMapper mapper;
    private final GuestMongoDbFacade guestMongoDbFacade;
    private final Logger logger;

    /**
     * Instantiates a new Guest accreditation mongo db facade.
     *
     * @param guestAccreditationMongoDbRepository the guest accreditation mongo db repository
     * @param guestAccreditationMongoDbMapper     the guest accreditation mongo db mapper
     * @param guestMongoDbFacade                  the guest mongo db facade
     * @param logger                              the logger
     */
    public GuestAccreditationMongoDbFacade(
        GuestAccreditationMongoDbRepository guestAccreditationMongoDbRepository,
        GuestAccreditationMongoDbMapper guestAccreditationMongoDbMapper,
        GuestMongoDbFacade guestMongoDbFacade, Logger logger) {
        this.repository = guestAccreditationMongoDbRepository;
        this.mapper = guestAccreditationMongoDbMapper;
        this.guestMongoDbFacade = guestMongoDbFacade;
        this.logger = logger;
    }

    @Override
    public GuestAccreditation save(GuestAccreditation guestAccreditation)
        throws InvalidValidityTimeframeException {
        logger.debug("Saving guest accreditation");

        GuestAccreditationMongoDbDocument document = mapper.entityToDocument(guestAccreditation);
        GuestAccreditationMongoDbDocument savedDocument = repository.save(document);

        GuestAccreditation savedGuestAccreditation = mapper.documentToEntity(savedDocument);

        /* WARNING Tech Debt!! ToDo: saveWithParty(accreditation, party){} */
        /* TODO - add proper error and transaction handling */
        Guest guest = guestMongoDbFacade.save(guestAccreditation.getParty());

        GuestAccreditation updatedGuestAccreditation = new GuestAccreditation(
            savedGuestAccreditation.getId(),
            guest,
            savedGuestAccreditation.getStatus(),
            savedGuestAccreditation.getInvitedBy(),
            savedGuestAccreditation.getInvitedAt(),
            savedGuestAccreditation.getInvitationUrl(),
            savedGuestAccreditation.getInvitationEmail(),
            savedGuestAccreditation.getInvitationQrCode(),
            savedGuestAccreditation.getBasisIdVerificationCorrelation(),
            savedGuestAccreditation.getGuestCredentialIssuanceCorrelation()
        );

        return updatedGuestAccreditation;
    }

    @Override
    public <S extends GuestAccreditation> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Operation saveAll is not yet implemented");
    }

    @Override
    public Optional<GuestAccreditation> findById(String id)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditation with accreditationId " + id);

        Optional<GuestAccreditationMongoDbDocument> document = this.repository.findById(id);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        GuestAccreditation guestAccreditation = this.mapper.documentToEntity(document.get());

        return Optional.of(guestAccreditation);
    }

    @Override
    public Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
        String connectionId, String threadId)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditation with " +
            "BasisIdVerificationCorrelationConnectionId " + connectionId +
            " and BasisIdVerificationCorrelationThreadId " + threadId);

        Optional<GuestAccreditationMongoDbDocument> document =
            this.repository
                .findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
                    connectionId, threadId);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        GuestAccreditation guestAccreditation =
            this.mapper.documentToEntity(document.get());

        return Optional.of(guestAccreditation);
    }

    @Override
    public Optional<GuestAccreditation> findByPartyId(String partyId)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditation by partyId " + partyId);

        Optional<GuestAccreditationMongoDbDocument> document =
            this.repository.findByPartyId(partyId);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        GuestAccreditation guestAccreditation =
            this.mapper.documentToEntity(document.get());

        return Optional.of(guestAccreditation);
    }

    @Override
    public Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionId(
        String connectionId) {
        throw new UnsupportedOperationException(
            "Operation findByBasisIdVerificationCorrelationConnectionId is not yet implemented");
    }

    @Override
    public Optional<GuestAccreditation> findByBasisIdVerificationCorrelationThreadId(
        String threadId) {
        throw new UnsupportedOperationException(
            "Operation findByBasisIdVerificationCorrelationThreadId is not yet implemented");
    }

    @Override
    public Optional<GuestAccreditation> findByBasisIdVerificationCorrelationPresentationExchangeId(
        String presentationExchangeId) {
        throw new UnsupportedOperationException(
            "Operation findByBasisIdVerificationCorrelationPresentationExchangeId is not yet implemented");
    }

    @Override
    public Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadIdAndBasisIdVerificationCorrelationPresentationExchangeId(
        String connectionId, String threadId, String presentationExchangeId) {
        throw new UnsupportedOperationException(
            "Operation findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadIdAndBasisIdVerificationCorrelationPresentationExchangeId is not yet implemented");
    }

    @Override
    public Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionId(
        String connectionId)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditation with " +
            "GuestCredentialIssuanceCorrelationConnectionId " + connectionId);

        Optional<GuestAccreditationMongoDbDocument> document =
            this.repository.findByGuestCredentialIssuanceCorrelationConnectionId(connectionId);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        GuestAccreditation guestAccreditation =
            this.mapper.documentToEntity(document.get());

        return Optional.of(guestAccreditation);
    }

    @Override
    public Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationThreadId(
        String threadId) {
        throw new UnsupportedOperationException(
            "Operation findByGuestCredentialIssuanceCorrelationThreadId is not yet implemented");
    }

    @Override
    public Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String presentationExchangeId) {
        throw new UnsupportedOperationException(
            "Operation findByGuestCredentialIssuanceCorrelationPresentationExchangeId is not yet implemented");
    }

    @Override
    public Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId(
        String connectionId, String threadId)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditation with " +
            "GuestCredentialIssuanceCorrelationConnectionId " + connectionId +
            " and GuestCredentialIssuanceCorrelationThreadId " + threadId);

        Optional<GuestAccreditationMongoDbDocument> document =
            this.repository
                .findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId(
                    connectionId, threadId);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        GuestAccreditation guestAccreditation =
            this.mapper.documentToEntity(document.get());

        return Optional.of(guestAccreditation);
    }

    @Override
    public Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadIdAndGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String connectionId, String threadId, String presentationExchangeId) {
        throw new UnsupportedOperationException(
            "Operation findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadIdAndGuestCredentialIssuanceCorrelationPresentationExchangeId is not yet implemented");
    }

    @Override
    public <R extends AccreditationStatus> List<GuestAccreditation> findAllByStatus(
        R status) throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditations with accreditationStatus: " +
            status.getName());

        List<GuestAccreditationMongoDbDocument> documents =
            this.repository.findAllByStatus(status.getName());

        List<GuestAccreditation> guestAccreditations = new ArrayList<>();
        for (GuestAccreditationMongoDbDocument document : documents) {
            guestAccreditations.add(this.mapper.documentToEntity(document));
        }

        return guestAccreditations;
    }

    @Override
    public <R extends AccreditationStatus> List<GuestAccreditation> findAllByStatusIsNot(
        R status) throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditations which do not have accreditationStatus: " +
            status.getName());

        List<GuestAccreditationMongoDbDocument> documents =
            this.repository.findAllByStatusIsNot(status.getName());

        List<GuestAccreditation> guestAccreditations = new ArrayList<>();
        for (GuestAccreditationMongoDbDocument document : documents) {
            guestAccreditations.add(this.mapper
                .documentToEntity(document));
        }

        return guestAccreditations;
    }

    @Override
    public <R extends AccreditationStatus> long countByStatus(R status) {
        logger.debug("Counting guest accreditations with accreditationStatus: " +
            status.getName());

        return this.repository.countByStatus(status.getName());
    }

    @Override
    public <R extends AccreditationStatus> long countByStatusIsNot(R status) {
        logger.debug("Counting guest accreditations which do not have accreditationStatus: " +
            status.getName());

        return this.repository.countByStatusIsNot(status.getName());
    }

    @Override
    public Optional<GuestAccreditation> findByPartyParams(String referenceBasisId,
        String firstName, String lastName, String dateOfBirth, String companyName,
        ZonedDateTime validFrom, ZonedDateTime validUntil, String invitedBy)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditation by various party params");

        Optional<Guest> guest = guestMongoDbFacade.findByPartyParams(referenceBasisId, firstName,
            lastName, dateOfBirth, companyName, validFrom, validUntil, invitedBy);

        if (guest.isEmpty()) {
            return Optional.empty();
        }

        String partyId = guest.get().getId();
        /* ToDo - take care of accreditations for exact same partyId, needs sanity check */
        Optional<GuestAccreditation> guestAccreditation = this.findByPartyId(partyId);

        return guestAccreditation;
    }

    @Override
    public List<GuestAccreditation> findAllByCredentialInvitedBy(String invitedBy)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching all guest accreditations by credential invitedBy " + invitedBy);
        List<Guest> guests =
            guestMongoDbFacade.findAllByCredentialInvitedBy(invitedBy);

        List<GuestAccreditation> accreditations = new ArrayList<>();
        for (Guest guest : guests) {
            accreditations.addAll(this.findAllByPartyId(guest.getId()));
        }

        return accreditations;
    }

    @Override
    public List<GuestAccreditation> findAllByPartyId(String partyId)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching all guest accreditations with partyId: " + partyId);

        List<GuestAccreditationMongoDbDocument> documents =
            this.repository.findAllByPartyId(partyId);

        List<GuestAccreditation> guestAccreditations = new ArrayList<>();
        for (GuestAccreditationMongoDbDocument document :
            documents) {
            guestAccreditations.add(this.mapper
                .documentToEntity(document));
        }

        return guestAccreditations;
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Operation existsById is not yet implemented");
    }

    @Override
    public Iterable<GuestAccreditation> findAll() throws InvalidValidityTimeframeException {
        logger.debug("Fetching all guest accreditations");

        Iterable<GuestAccreditationMongoDbDocument> documents = this.repository.findAll();

        List<GuestAccreditation> guestAccreditations = new ArrayList<>();
        for (GuestAccreditationMongoDbDocument document : documents) {
            guestAccreditations.add(this.mapper
                .documentToEntity(document));
        }

        return guestAccreditations;
    }

    @Override
    public Iterable<GuestAccreditation> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Operation findAllById is not yet implemented");
    }

    @Override
    public long count() {
        logger.debug("Counting guest accreditations");

        return this.repository.count();
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Operation deleteById is not yet implemented");
    }

    @Override
    public void delete(GuestAccreditation guestAccreditation) {
        throw new UnsupportedOperationException("Operation delete is not yet implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> iterable) {
        throw new UnsupportedOperationException("Operation deleteAllById is not yet implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends GuestAccreditation> iterable) {
        throw new UnsupportedOperationException("Operation deleteAll is not yet implemented");
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    public Optional<GuestAccreditation> findByIdAndInvitedBy(String id, String invitedBy)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching guest accreditation by id {} and invitedBy {}", id, invitedBy);

        Optional<GuestAccreditationMongoDbDocument> document =
            this.repository.findByIdAndInvitedBy(id, invitedBy);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        GuestAccreditation guestAccreditation =
            this.mapper.documentToEntity(document.get());

        return Optional.of(guestAccreditation);
    }

    @Override
    public List<GuestAccreditation> findAllByInvitedBy(String invitedBy)
        throws InvalidValidityTimeframeException {
        logger.debug("Fetching all guest accreditations by invitedBy " + invitedBy);

        List<GuestAccreditationMongoDbDocument> documents =
            this.repository.findAllByInvitedBy(invitedBy);

        List<GuestAccreditation> guestAccreditations = new ArrayList<>();
        for (GuestAccreditationMongoDbDocument document : documents) {
            guestAccreditations.add(this.mapper
                .documentToEntity(document));
        }

        return guestAccreditations;
    }

    @Override
    public <R extends AccreditationStatus> List<GuestAccreditation> findAllByInvitedByAndValidStatus(
        String invitedBy, List<R> status) throws InvalidValidityTimeframeException {
        logger.debug("Fetching all valid guest accreditations by invitedBy {}", invitedBy);

        List<String> stats = status.stream().map(s -> s.getName()).collect(Collectors.toList());

        List<GuestAccreditationMongoDbDocument> documents =
            this.repository.findAllByInvitedByAndStatusIsNot(invitedBy, stats);

        List<GuestAccreditation> guestAccreditations = new ArrayList<>();
        for (GuestAccreditationMongoDbDocument document : documents) {
            guestAccreditations.add(this.mapper
                .documentToEntity(document));
        }

        return guestAccreditations;
    }
}
