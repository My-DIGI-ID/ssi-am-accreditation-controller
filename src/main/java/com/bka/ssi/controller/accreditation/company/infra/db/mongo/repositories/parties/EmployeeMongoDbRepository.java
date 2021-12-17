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

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.EmployeeMongoDbDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Employee mongo db repository.
 */
@Repository
public interface EmployeeMongoDbRepository extends
    MongoRepository<EmployeeMongoDbDocument, String> {

    /**
     * Find by id and created by optional.
     *
     * @param id        the id
     * @param createdBy the created by
     * @return the optional
     */
    Optional<EmployeeMongoDbDocument> findByIdAndCreatedBy(String id, String createdBy);

    /**
     * Find all by created by list.
     *
     * @param createdBy the created by
     * @return the list
     */
    List<EmployeeMongoDbDocument> findAllByCreatedBy(String createdBy);
}
