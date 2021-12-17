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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.GuestAccreditationMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Guest accreditation mongo db repository.
 */
@Repository
public interface GuestAccreditationMongoDbRepository
    extends MongoRepository<GuestAccreditationMongoDbDocument, String> {

    /**
     * Find by party id optional.
     *
     * @param partyId the party id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByPartyId(String partyId);

    /**
     * Find by basis id verification correlation connection id optional.
     *
     * @param connectionId the connection id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationConnectionId(
        String connectionId);

    /**
     * Find by basis id verification correlation thread id optional.
     *
     * @param threadId the thread id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationThreadId(
        String threadId);

    /**
     * Find by basis id verification correlation presentation exchange id optional.
     *
     * @param presentationExchangeId the presentation exchange id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationPresentationExchangeId(
        String presentationExchangeId);

    /**
     * Find by basis id verification correlation connection id and basis id verification
     * correlation thread id optional.
     *
     * @param connectionId the connection id
     * @param threadId     the thread id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
        String connectionId,
        String threadId);

    /**
     * Find by basis id verification correlation connection id and basis id verification
     * correlation thread id and basis id verification correlation presentation exchange id
     * optional.
     *
     * @param connectionId           the connection id
     * @param threadId               the thread id
     * @param presentationExchangeId the presentation exchange id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadIdAndBasisIdVerificationCorrelationPresentationExchangeId(
        String connectionId,
        String threadId,
        String presentationExchangeId);

    /**
     * Find by guest credential issuance correlation connection id optional.
     *
     * @param connectionId the connection id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationConnectionId(
        String connectionId);

    /**
     * Find by guest credential issuance correlation thread id optional.
     *
     * @param threadId the thread id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationThreadId(
        String threadId);

    /**
     * Find by guest credential issuance correlation presentation exchange id optional.
     *
     * @param presentationExchangeId the presentation exchange id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String presentationExchangeId);

    /**
     * Find by guest credential issuance correlation connection id and guest credential issuance
     * correlation thread id optional.
     *
     * @param connectionId the connection id
     * @param threadId     the thread id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId(
        String connectionId,
        String threadId);

    /**
     * Find by guest credential issuance correlation connection id and guest credential issuance
     * correlation thread id and guest credential issuance correlation presentation exchange id
     * optional.
     *
     * @param connectionId           the connection id
     * @param threadId               the thread id
     * @param presentationExchangeId the presentation exchange id
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadIdAndGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String connectionId,
        String threadId,
        String presentationExchangeId);

    /**
     * Find all by status list.
     *
     * @param status the status
     * @return the list
     */
    List<GuestAccreditationMongoDbDocument> findAllByStatus(
        String status);

    /**
     * Find all by status is not list.
     *
     * @param status the status
     * @return the list
     */
    List<GuestAccreditationMongoDbDocument> findAllByStatusIsNot(
        String status);

    /**
     * Count by status long.
     *
     * @param status the status
     * @return the long
     */
    long countByStatus(String status);

    /**
     * Count by status is not long.
     *
     * @param status the status
     * @return the long
     */
    long countByStatusIsNot(String status);

    /**
     * Find all by party id list.
     *
     * @param partyId the party id
     * @return the list
     */
    List<GuestAccreditationMongoDbDocument> findAllByPartyId(String partyId);

    /**
     * Find by id and invited by optional.
     *
     * @param id        the id
     * @param invitedBy the invited by
     * @return the optional
     */
    Optional<GuestAccreditationMongoDbDocument> findByIdAndInvitedBy(String id, String invitedBy);

    /**
     * Find all by invited by list.
     *
     * @param invitedBy the invited by
     * @return the list
     */
    List<GuestAccreditationMongoDbDocument> findAllByInvitedBy(String invitedBy);

    /**
     * Find all by invited by and status is not list.
     *
     * @param invitedBy the invited by
     * @param stats     the stats
     * @return the list
     */
    List<GuestAccreditationMongoDbDocument> findAllByInvitedByAndStatusIsNot(
        String invitedBy, List<String> stats);
}
