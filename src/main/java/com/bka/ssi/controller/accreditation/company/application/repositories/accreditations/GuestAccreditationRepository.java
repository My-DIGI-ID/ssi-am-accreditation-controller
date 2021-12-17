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

package com.bka.ssi.controller.accreditation.company.application.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.application.repositories.AccreditationRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface Guest accreditation repository.
 */
@Repository
public interface GuestAccreditationRepository extends AccreditationRepository<GuestAccreditation> {

    /**
     * Find by basis id verification correlation connection id optional.
     *
     * @param connectionId the connection id
     * @return the optional
     */
    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionId(
        String connectionId);

    /**
     * Find by basis id verification correlation thread id optional.
     *
     * @param threadId the thread id
     * @return the optional
     */
    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationThreadId(
        String threadId);

    /**
     * Find by basis id verification correlation presentation exchange id optional.
     *
     * @param presentationExchangeId the presentation exchange id
     * @return the optional
     */
    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationPresentationExchangeId(
        String presentationExchangeId);

    /**
     * Find by basis id verification correlation connection id and basis id verification
     * correlation thread id optional.
     *
     * @param connectionId the connection id
     * @param threadId     the thread id
     * @return the optional
     * @throws Exception the exception
     */
    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
        String connectionId,
        String threadId) throws Exception;

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
    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadIdAndBasisIdVerificationCorrelationPresentationExchangeId(
        String connectionId,
        String threadId,
        String presentationExchangeId);

    /**
     * Find by guest credential issuance correlation connection id optional.
     *
     * @param connectionId the connection id
     * @return the optional
     * @throws Exception the exception
     */
    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionId(
        String connectionId) throws Exception;

    /**
     * Find by guest credential issuance correlation thread id optional.
     *
     * @param threadId the thread id
     * @return the optional
     */
    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationThreadId(
        String threadId);

    /**
     * Find by guest credential issuance correlation presentation exchange id optional.
     *
     * @param presentationExchangeId the presentation exchange id
     * @return the optional
     */
    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String presentationExchangeId);

    /**
     * Find by guest credential issuance correlation connection id and guest credential issuance
     * correlation thread id optional.
     *
     * @param connectionId the connection id
     * @param threadId     the thread id
     * @return the optional
     * @throws Exception the exception
     */
    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId(
        String connectionId,
        String threadId) throws Exception;

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
    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadIdAndGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String connectionId,
        String threadId,
        String presentationExchangeId);

    /**
     * Find by party params optional.
     *
     * @param referenceBasisId the reference basis id
     * @param firstName        the first name
     * @param lastName         the last name
     * @param dateOfBirth      the date of birth
     * @param companyName      the company name
     * @param validFrom        the valid from
     * @param validUntil       the valid until
     * @param invitedBy        the invited by
     * @return the optional
     * @throws Exception the exception
     */
    default Optional<GuestAccreditation> findByPartyParams(String referenceBasisId,
        String firstName,
        String lastName, String dateOfBirth,
        String companyName, ZonedDateTime validFrom, ZonedDateTime validUntil,
        String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Find all by credential invited by list.
     *
     * @param invitedBy the invited by
     * @return the list
     * @throws Exception the exception
     */
    default List<GuestAccreditation> findAllByCredentialInvitedBy(String invitedBy)
        throws Exception {
        throw new UnsupportedOperationException();
    }
}
