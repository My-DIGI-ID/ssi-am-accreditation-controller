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

import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.EmployeeAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.EmployeeAccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties.EmployeeMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.accreditations.EmployeeAccreditationMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations.EmployeeAccreditationMongoDbRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Employee accreditation mongo db facade.
 */
@Service
public class EmployeeAccreditationMongoDbFacade implements EmployeeAccreditationRepository {

    private final EmployeeAccreditationMongoDbRepository repository;
    private final EmployeeAccreditationMongoDbMapper mapper;
    private final EmployeeMongoDbFacade employeeMongoDbFacade;
    private final Logger logger;

    /**
     * Instantiates a new Employee accreditation mongo db facade.
     *
     * @param repository            the repository
     * @param mapper                the mapper
     * @param employeeMongoDbFacade the employee mongo db facade
     * @param logger                the logger
     */
    public EmployeeAccreditationMongoDbFacade(
        EmployeeAccreditationMongoDbRepository repository,
        EmployeeAccreditationMongoDbMapper mapper,
        EmployeeMongoDbFacade employeeMongoDbFacade, Logger logger) {
        this.repository = repository;
        this.mapper = mapper;
        this.employeeMongoDbFacade = employeeMongoDbFacade;
        this.logger = logger;
    }

    @Override
    public EmployeeAccreditation save(EmployeeAccreditation employeeAccreditation) {
        logger.debug("Saving employee accreditation");

        EmployeeAccreditationMongoDbDocument document =
            mapper.entityToDocument(employeeAccreditation);
        EmployeeAccreditationMongoDbDocument savedDocument = repository.save(document);

        EmployeeAccreditation savedEmployeeAccreditation = mapper.documentToEntity(savedDocument);

        /* WARNING Tech Debt!! ToDo: saveWithParty(accreditation, party){} */
        /* TODO - add proper error and transaction handling */
        Employee employee = employeeMongoDbFacade.save(employeeAccreditation.getParty());

        EmployeeAccreditation updatedEmployeeAccreditation = new EmployeeAccreditation(
            savedEmployeeAccreditation.getId(),
            employee,
            savedEmployeeAccreditation.getStatus(),
            savedEmployeeAccreditation.getInvitedBy(),
            savedEmployeeAccreditation.getInvitedAt(),
            savedEmployeeAccreditation.getInvitationUrl(),
            savedEmployeeAccreditation.getInvitationEmail(),
            savedEmployeeAccreditation.getInvitationQrCode(),
            savedEmployeeAccreditation.getEmployeeCredentialIssuanceCorrelation()
        );

        return updatedEmployeeAccreditation;
    }

    @Override
    public <S extends EmployeeAccreditation> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Operation saveAll is not yet implemented");
    }

    @Override
    public Optional<EmployeeAccreditation> findById(String id) {
        logger.debug("Fetching employee accreditation with accreditationId " + id);

        Optional<EmployeeAccreditationMongoDbDocument> document = this.repository.findById(id);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        EmployeeAccreditation employeeAccreditation = this.mapper.documentToEntity(document.get());

        return Optional.of(employeeAccreditation);
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Operation existsById is not yet implemented");
    }

    @Override
    public Iterable<EmployeeAccreditation> findAll() {
        logger.debug("Fetching all employee accreditations");

        Iterable<EmployeeAccreditationMongoDbDocument> documents = this.repository.findAll();

        List<EmployeeAccreditation> accreditations = new ArrayList<>();
        documents.forEach(document -> accreditations.add(this.mapper.documentToEntity(document)));

        return accreditations;
    }

    @Override
    public Iterable<EmployeeAccreditation> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Operation findAllById is not yet implemented");
    }

    @Override
    public Optional<EmployeeAccreditation> findByEmployeeCredentialIssuanceCorrelationConnectionId(
        String connectionId) {
        logger.debug(
            "Fetching employee accreditation with "
                + "EmployeeCredentialIssuanceCorrelationConnectionId {}", connectionId);

        Optional<EmployeeAccreditationMongoDbDocument> document =
            this.repository.findByEmployeeCredentialIssuanceCorrelationConnectionId(connectionId);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        EmployeeAccreditation employeeAccreditation = this.mapper.documentToEntity(document.get());

        return Optional.of(employeeAccreditation);
    }

    @Override
    public List<EmployeeAccreditation> findAllByPartyId(String partyId) {
        logger.debug("Fetching accreditations with party Id {}", partyId);

        Iterable<EmployeeAccreditationMongoDbDocument> documents =
            this.repository.findAllByPartyId(partyId);

        List<EmployeeAccreditation> accreditations = new ArrayList<>();
        documents.forEach(document -> accreditations.add(this.mapper.documentToEntity(document)));
        return accreditations;
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Operation count is not yet implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Operation deleteById is not yet implemented");
    }

    @Override
    public void delete(
        EmployeeAccreditation employeeAccreditation) {
        throw new UnsupportedOperationException("Operation delete is not yet implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> iterable) {
        throw new UnsupportedOperationException("Operation deleteAllById is not yet implemented");
    }

    @Override
    public void deleteAll(
        Iterable<? extends EmployeeAccreditation> iterable) {
        throw new UnsupportedOperationException("Operation deleteAll is not yet implemented");
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    public Optional<EmployeeAccreditation> findByIdAndInvitedBy(String id, String invitedBy) {
        logger.debug("Fetching employee accreditation by id {} and invitedBy {}", id, invitedBy);

        Optional<EmployeeAccreditationMongoDbDocument> document =
            this.repository.findByIdAndInvitedBy(id, invitedBy);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        EmployeeAccreditation accreditation = this.mapper.documentToEntity(document.get());

        return Optional.of(accreditation);
    }

    @Override
    public List<EmployeeAccreditation> findAllByInvitedBy(String invitedBy) {
        logger.debug("Fetching all employee accreditations by invitedBy " + invitedBy);

        List<EmployeeAccreditationMongoDbDocument> documents =
            this.repository.findAllByInvitedBy(invitedBy);

        List<EmployeeAccreditation> accreditations = new ArrayList<>();
        documents.forEach(document -> accreditations.add(this.mapper
            .documentToEntity(document)));

        return accreditations;
    }
}
