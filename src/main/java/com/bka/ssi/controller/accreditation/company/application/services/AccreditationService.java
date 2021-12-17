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

import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.AccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.AccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.PartyRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.enums.AccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Accreditation service.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 * @param <U> the type parameter
 */
public abstract class AccreditationService<T extends Accreditation<R, U>, R extends Party,
    U extends AccreditationStatus> {

    /**
     * The Logger.
     */
    protected Logger logger;
    /**
     * The Accreditation repository.
     */
    protected AccreditationRepository<T> accreditationRepository;
    /**
     * The Factory.
     */
    protected AccreditationFactory<R, T> factory;
    /**
     * The Party repository.
     */
    protected PartyRepository<R> partyRepository;

    /**
     * Instantiates a new Accreditation service.
     *
     * @param logger                  the logger
     * @param accreditationRepository the accreditation repository
     * @param factory                 the factory
     * @param partyRepository         the party repository
     */
    public AccreditationService(Logger logger,
        AccreditationRepository<T> accreditationRepository,
        AccreditationFactory<R, T> factory,
        PartyRepository<R> partyRepository) {
        this.logger = logger;
        this.accreditationRepository = accreditationRepository;
        this.factory = factory;
        this.partyRepository = partyRepository;
    }

    /**
     * Gets all accreditations by party id.
     *
     * @param id the id
     * @return the all accreditations by party id
     * @throws Exception the exception
     */
    public List<T> getAllAccreditationsByPartyId(String id) throws Exception {
        return this.accreditationRepository.findAllByPartyId(id);
    }

    /**
     * Gets accreditation by id.
     *
     * @param id the id
     * @return the accreditation by id
     * @throws Exception the exception
     */
    public T getAccreditationById(String id) throws Exception {
        T output = this.accreditationRepository.findById(id).orElseThrow(NotFoundException::new);
        return output;
    }

    /**
     * Gets accreditation by id.
     *
     * @param id       the id
     * @param userName the user name
     * @return the accreditation by id
     * @throws Exception the exception
     */
    public T getAccreditationById(String id, String userName) throws Exception {
        T output =
            this.accreditationRepository.findByIdAndInvitedBy(id, userName)
                .orElseThrow(NotFoundException::new);
        return output;
    }

    /**
     * Gets all accreditations.
     *
     * @return the all accreditations
     * @throws Exception the exception
     */
    public List<T> getAllAccreditations() throws Exception {
        List<T> output = new ArrayList<>();
        this.accreditationRepository.findAll().forEach(output::add);
        return output;
    }

    /**
     * Gets all accreditations.
     *
     * @param userName the user name
     * @return the all accreditations
     * @throws Exception the exception
     */
    public List<T> getAllAccreditations(String userName) throws Exception {
        List<T> output = this.accreditationRepository.findAllByInvitedBy(userName);
        return output;
    }

    /**
     * Gets all accreditations by status.
     *
     * @param status the status
     * @return the all accreditations by status
     * @throws Exception the exception
     */
    public List<T> getAllAccreditationsByStatus(U status)
        throws Exception {
        return this.accreditationRepository.findAllByStatus(status);
    }

    /**
     * Count accreditations by status long.
     *
     * @param status the status
     * @return the long
     * @throws Exception the exception
     */
    public long countAccreditationsByStatus(U status)
        throws Exception {
        return this.accreditationRepository.countByStatus(status);
    }

    /**
     * Initiate accreditation t.
     *
     * @param partyId the party id
     * @return the t
     * @throws Exception the exception
     */
    public T initiateAccreditation(String partyId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation initiateAccreditation is not yet implemented");
    }

    /**
     * Initiate accreditation t.
     *
     * @param partyId  the party id
     * @param userName the user name
     * @return the t
     * @throws Exception the exception
     */
    public T initiateAccreditation(String partyId, String userName)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation initiateAccreditation is not yet implemented");
    }

    /**
     * Generate accreditation with email as message byte [ ].
     *
     * @param accreditationId the accreditation id
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public byte[] generateAccreditationWithEmailAsMessage(String accreditationId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation initiateAccreditationWithEmailAsMessage is not yet implemented");
    }

    /**
     * Proceed with accreditation t.
     *
     * @param accreditationId the accreditation id
     * @return the t
     * @throws Exception the exception
     */
    public T proceedWithAccreditation(String accreditationId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation proceedWithAccreditation is not yet implemented");
    }

    /**
     * Offer accreditation t.
     *
     * @param accreditationId the accreditation id
     * @return the t
     * @throws Exception the exception
     */
    public T offerAccreditation(String accreditationId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation offerAccreditation is not yet implemented");
    }

    /**
     * Complete accreditation t.
     *
     * @param connectionId         the connection id
     * @param credentialExchangeId the credential exchange id
     * @param issuedBy             the issued by
     * @return the t
     * @throws Exception the exception
     */
    public T completeAccreditation(String connectionId, String credentialExchangeId,
        String issuedBy)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation completeAccreditationProcess is not yet implemented");
    }

    /**
     * Is accreditation completed boolean.
     *
     * @param accreditationId the accreditation id
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean isAccreditationCompleted(String accreditationId) throws Exception {
        throw new UnsupportedOperationException(
            "Operation isAccreditationCompleted is not yet implemented");
    }

    /**
     * Revoke accreditation t.
     *
     * @param accreditationId the accreditation id
     * @return the t
     * @throws Exception the exception
     */
    public T revokeAccreditation(String accreditationId) throws Exception {
        throw new UnsupportedOperationException(
            "Operation revokeAccreditation is not yet implemented");
    }

    /**
     * Update accreditation status t.
     *
     * @param accreditationId the accreditation id
     * @param status          the status
     * @return the t
     * @throws Exception the exception
     */
    public T updateAccreditationStatus(String accreditationId, GuestAccreditationStatus status)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation updateAccreditationStatus is not yet implemented");
    }
}
