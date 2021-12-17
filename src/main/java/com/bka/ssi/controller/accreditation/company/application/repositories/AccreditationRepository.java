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

package com.bka.ssi.controller.accreditation.company.application.repositories;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.AccreditationStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Accreditation repository.
 *
 * @param <T> the type parameter
 */
@Repository
public interface AccreditationRepository<T extends Accreditation>
    extends BaseRepository<T, String> {

    /**
     * Find by party id optional.
     *
     * @param partyId the party id
     * @return the optional
     * @throws Exception the exception
     */
    default Optional<T> findByPartyId(String partyId)
        throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Find all by party id list.
     *
     * @param partyId the party id
     * @return the list
     * @throws Exception the exception
     */
    default List<T> findAllByPartyId(String partyId) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Find all by status list.
     *
     * @param <R>    the type parameter
     * @param status the status
     * @return the list
     * @throws Exception the exception
     */
    default <R extends AccreditationStatus> List<T> findAllByStatus(R status) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Find all by status is not list.
     *
     * @param <R>    the type parameter
     * @param status the status
     * @return the list
     * @throws Exception the exception
     */
    default <R extends AccreditationStatus> List<T> findAllByStatusIsNot(R status)
        throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Count by status long.
     *
     * @param <R>    the type parameter
     * @param status the status
     * @return the long
     */
    default <R extends AccreditationStatus> long countByStatus(R status) {
        throw new UnsupportedOperationException();
    }

    /**
     * Count by status is not long.
     *
     * @param <R>    the type parameter
     * @param status the status
     * @return the long
     */
    default <R extends AccreditationStatus> long countByStatusIsNot(R status) {
        throw new UnsupportedOperationException();
    }

    /**
     * Find by id and invited by optional.
     *
     * @param id        the id
     * @param invitedBy the invited by
     * @return the optional
     * @throws Exception the exception
     */
    default Optional<T> findByIdAndInvitedBy(String id, String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Find all by invited by list.
     *
     * @param invitedBy the invited by
     * @return the list
     * @throws Exception the exception
     */
    default List<T> findAllByInvitedBy(String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Find all by invited by and valid status list.
     *
     * @param <R>       the type parameter
     * @param invitedBy the invited by
     * @param status    the status
     * @return the list
     * @throws Exception the exception
     */
    default <R extends AccreditationStatus> List<T> findAllByInvitedByAndValidStatus(
        String invitedBy, List<R> status) throws Exception {
        throw new UnsupportedOperationException();
    }
}
