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

package com.bka.ssi.controller.accreditation.company.application.services;

import com.bka.ssi.controller.accreditation.company.application.exceptions.EmptyFileException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidAccreditationStatusForPartyException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidPartyOperationException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.PartyFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.AccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.PartyRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.enums.DefaultAccreditationStatus;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Party service.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 * @param <U> the type parameter
 */
public abstract class PartyService<T extends Party, R, U extends Accreditation<T, ?>> {

    /**
     * The Logger.
     */
    protected Logger logger;
    /**
     * The Party repository.
     */
    protected PartyRepository<T> partyRepository;
    /**
     * The Factory.
     */
    protected PartyFactory<R, T> factory;
    /**
     * The Accreditation repository.
     */
    protected AccreditationRepository<U> accreditationRepository;

    /**
     * Instantiates a new Party service.
     *
     * @param logger                  the logger
     * @param partyRepository         the party repository
     * @param factory                 the factory
     * @param accreditationRepository the accreditation repository
     */
    public PartyService(Logger logger,
        PartyRepository<T> partyRepository,
        PartyFactory<R, T> factory,
        AccreditationRepository<U> accreditationRepository) {
        this.logger = logger;
        this.partyRepository = partyRepository;
        this.factory = factory;
        this.accreditationRepository = accreditationRepository;
    }

    /**
     * Create party t.
     *
     * @param inputDto the input dto
     * @return the t
     * @throws Exception the exception
     */
    public T createParty(R inputDto) throws Exception {
        T output = this.factory.create(inputDto);
        T savedOutput = this.partyRepository.save(output);
        return savedOutput;
    }

    /**
     * Create party t.
     *
     * @param inputDto the input dto
     * @param userName the user name
     * @return the t
     * @throws Exception the exception
     */
    public T createParty(R inputDto, String userName) throws Exception {
        T output = this.factory.create(inputDto, userName);
        T savedOutput = this.partyRepository.save(output);
        return savedOutput;
    }

    /**
     * Create party list.
     *
     * @param file the file
     * @return the list
     * @throws Exception the exception
     */
    public List<T> createParty(MultipartFile file) throws Exception {
        List<T> output = this.factory.create(file);

        if (output.isEmpty()) {
            throw new EmptyFileException();
        }

        List<T> savedOutput = new ArrayList<>();
        this.partyRepository.saveAll(output).forEach(savedOutput::add);
        return savedOutput;
    }

    /**
     * Create party list.
     *
     * @param file     the file
     * @param userName the user name
     * @return the list
     * @throws Exception the exception
     */
    public List<T> createParty(MultipartFile file, String userName) throws Exception {
        List<T> output = this.factory.create(file, userName);

        if (output.isEmpty()) {
            throw new EmptyFileException();
        }

        List<T> savedOutput = new ArrayList<>();
        this.partyRepository.saveAll(output).forEach(savedOutput::add);
        return savedOutput;
    }

    /**
     * Gets all parties.
     *
     * @return the all parties
     * @throws Exception the exception
     */
    public List<T> getAllParties() throws Exception {
        List<T> output = new ArrayList<>();
        this.partyRepository.findAll().forEach(output::add);
        return output;
    }

    /**
     * Gets all parties.
     *
     * @param userName the user name
     * @return the all parties
     * @throws Exception the exception
     */
    public List<T> getAllParties(String userName) throws Exception {
        List<T> output = this.partyRepository.findAllByCreatedBy(userName);
        return output;
    }

    /**
     * Gets party by id.
     *
     * @param id the id
     * @return the party by id
     * @throws Exception the exception
     */
    public T getPartyById(String id) throws Exception {
        T output =
            this.partyRepository.findById(id).orElseThrow(NotFoundException::new);
        return output;
    }

    /**
     * Gets party by id.
     *
     * @param id       the id
     * @param userName the user name
     * @return the party by id
     * @throws Exception the exception
     */
    public T getPartyById(String id, String userName) throws Exception {
        T output =
            this.partyRepository.findByIdAndCreatedBy(id, userName)
                .orElseThrow(NotFoundException::new);
        return output;
    }

    /**
     * Delete party.
     *
     * @param id the id
     * @throws Exception the exception
     */
    public void deleteParty(String id) throws Exception {
        T party = this.partyRepository.findById(id).orElseThrow(NotFoundException::new);

        List<U> accreditations = this.accreditationRepository.findAllByPartyId(id);

        if (!accreditations.isEmpty()) {
            throw new InvalidPartyOperationException();
        }

        this.partyRepository.deleteById(id);
    }

    /**
     * Update party t.
     *
     * @param inputDto the input dto
     * @param partyId  the party id
     * @param userName the user name
     * @return the t
     * @throws Exception the exception
     */
    public T updateParty(R inputDto, String partyId, String userName) throws Exception {
        T existingParty = this.partyRepository.findById(partyId)
            .orElseThrow(NotFoundException::new);

        List<U> accreditations = this.accreditationRepository.findAllByPartyId(partyId);
        for (Accreditation<T, ?> accreditation : accreditations) {
            if (accreditation.getStatus().getName()
                .equals(DefaultAccreditationStatus.ACCEPTED.getName())) {
                throw new InvalidAccreditationStatusForPartyException(partyId);
            }
        }

        T newPartyData = (T) this.factory.create(inputDto, userName);
        existingParty.updateWithNewPartyData(newPartyData);
        T savedOutput = this.partyRepository.save(existingParty);

        return savedOutput;
    }
}
