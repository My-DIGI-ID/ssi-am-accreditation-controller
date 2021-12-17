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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.GuestMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface Guest mongo db repository.
 */
@Repository
public interface GuestMongoDbRepository extends MongoRepository<GuestMongoDbDocument, String> {

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
     */
    @Query(value =
        "{'credentialOffer.credential.referenceBasisId': ?0, " +
            "'credentialOffer.credential.persona.firstName': ?1, " +
            "'credentialOffer.credential.persona.lastName': ?2, " +
            "'credentialOffer.credential.guestPrivateInformation.dateOfBirth': ?3, " +
            "'credentialOffer.credential.companyName': ?4, " +
            "'credentialOffer.credential.validityTimeframe.validFrom': ?5, " +
            "'credentialOffer.credential.validityTimeframe.validUntil.': ?6, " +
            "'credentialOffer.credential.invitedBy': ?7}")
    Optional<GuestMongoDbDocument> findByPartyParams(
        String referenceBasisId,
        String firstName, String lastName, String dateOfBirth, String companyName,
        ZonedDateTime validFrom, ZonedDateTime validUntil, String invitedBy);

    /**
     * Find by id and created by optional.
     *
     * @param id        the id
     * @param createdBy the created by
     * @return the optional
     */
    Optional<GuestMongoDbDocument> findByIdAndCreatedBy(String id, String createdBy);

    /**
     * Find all by created by list.
     *
     * @param createdBy the created by
     * @return the list
     */
    List<GuestMongoDbDocument> findAllByCreatedBy(String createdBy);

    /**
     * Find all by credential offer credential invited by list.
     *
     * @param invitedBy the invited by
     * @return the list
     */
    List<GuestMongoDbDocument> findAllByCredentialOfferCredentialInvitedBy(String invitedBy);
}
