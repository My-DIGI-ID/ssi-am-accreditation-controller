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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.security;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security.GuestAccessTokenMongoDbDocument;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The type Guest access token mongo db mapper.
 */
@Component
public class GuestAccessTokenMongoDbMapper {

    private Logger logger;

    /**
     * Instantiates a new Guest access token mongo db mapper.
     *
     * @param logger the logger
     */
    public GuestAccessTokenMongoDbMapper(Logger logger) {
        this.logger = logger;
    }

    /**
     * Document to entity guest token.
     *
     * @param document the document
     * @return the guest token
     */
    public GuestToken documentToEntity(GuestAccessTokenMongoDbDocument document) {
        GuestToken entity = new GuestToken(document.getId(), document.getAccreditationId(),
            document.getExpiring());

        return entity;
    }

    /**
     * Entity to document guest access token mongo db document.
     *
     * @param token the token
     * @return the guest access token mongo db document
     */
    public GuestAccessTokenMongoDbDocument entityToDocument(GuestToken token) {
        GuestAccessTokenMongoDbDocument document = new GuestAccessTokenMongoDbDocument();

        document.setId(token.getId());
        document.setAccreditationId(token.getAccreditationId());
        document.setExpiring(token.getExpiring());

        return document;
    }
}
