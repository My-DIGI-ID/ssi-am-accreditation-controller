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

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.EmployeeAccreditationMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Employee accreditation mongo db repository.
 */
@Repository
public interface EmployeeAccreditationMongoDbRepository
    extends MongoRepository<EmployeeAccreditationMongoDbDocument, String> {

    /**
     * Find all by party id list.
     *
     * @param partyId the party id
     * @return the list
     */
    List<EmployeeAccreditationMongoDbDocument> findAllByPartyId(String partyId);

    /**
     * Find by party id optional.
     *
     * @param partyId the party id
     * @return the optional
     */
    Optional<EmployeeAccreditationMongoDbDocument> findByPartyId(String partyId);

    /**
     * Find by id and invited by optional.
     *
     * @param id        the id
     * @param invitedBy the invited by
     * @return the optional
     */
    Optional<EmployeeAccreditationMongoDbDocument> findByIdAndInvitedBy(String id,
        String invitedBy);

    /**
     * Find all by invited by list.
     *
     * @param invitedBy the invited by
     * @return the list
     */
    List<EmployeeAccreditationMongoDbDocument> findAllByInvitedBy(String invitedBy);

    /**
     * Find by employee credential issuance correlation connection id optional.
     *
     * @param connectionId the connection id
     * @return the optional
     */
    Optional<EmployeeAccreditationMongoDbDocument> findByEmployeeCredentialIssuanceCorrelationConnectionId(
        String connectionId);
}
