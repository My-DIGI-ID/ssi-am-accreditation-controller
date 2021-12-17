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

package com.bka.ssi.controller.accreditation.company.application.repositories.parties;

import com.bka.ssi.controller.accreditation.company.application.repositories.PartyRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface Guest repository.
 */
@Repository
public interface GuestRepository extends PartyRepository<Guest> {

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
    default Optional<Guest> findByPartyParams(
        String referenceBasisId,
        String firstName, String lastName, String dateOfBirth, String companyName,
        ZonedDateTime validFrom, ZonedDateTime validUntil, String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Find all by credential invited by list.
     *
     * @param invitedBy the invited by
     * @return the list
     * @throws Exception the exception
     */
    default List<Guest> findAllByCredentialInvitedBy(String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }
}
