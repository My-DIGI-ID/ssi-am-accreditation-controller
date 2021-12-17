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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.security;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import com.bka.ssi.controller.accreditation.company.application.security.repositories.GuestAccessTokenRepository;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security.GuestAccessTokenMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.security.GuestAccessTokenMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.security.GuestAccessTokenMongoDbRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Guest access token mongo db facade.
 */
@Service
public class GuestAccessTokenMongoDbFacade implements GuestAccessTokenRepository {

    private final GuestAccessTokenMongoDbRepository repository;
    private final GuestAccessTokenMongoDbMapper mapper;
    private final Logger logger;

    /**
     * Instantiates a new Guest access token mongo db facade.
     *
     * @param repository the repository
     * @param mapper     the mapper
     * @param logger     the logger
     */
    public GuestAccessTokenMongoDbFacade(
        GuestAccessTokenMongoDbRepository repository,
        GuestAccessTokenMongoDbMapper mapper,
        Logger logger) {
        this.repository = repository;
        this.mapper = mapper;
        this.logger = logger;
    }

    @Override
    public GuestToken save(GuestToken token) {
        logger.debug("Saving guest token");

        GuestAccessTokenMongoDbDocument document = mapper.entityToDocument(token);

        GuestAccessTokenMongoDbDocument savedDocument = this.repository.save(document);

        GuestToken savedToken = mapper.documentToEntity(savedDocument);

        return savedToken;
    }

    @Override
    public <S extends GuestToken> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Operation saveAll is not yet implemented");
    }

    @Override
    public Optional<GuestToken> findById(String id) {
        logger.debug("Fetching guest token with id " + id);

        Optional<GuestAccessTokenMongoDbDocument> document =
            this.repository.findById(id);

        if (document.isEmpty()) {
            return Optional.empty();
        }

        GuestToken token = this.mapper.documentToEntity(document.get());

        return Optional.of(token);
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Operation existsById is not yet implemented");
    }

    @Override
    public Iterable<GuestToken> findAll() {
        logger.debug("Fetching all guest tokens");

        Iterable<GuestAccessTokenMongoDbDocument> documents =
            this.repository.findAll();

        List<GuestToken> tokens = new ArrayList<>();
        documents.forEach(document -> tokens
            .add(this.mapper
                .documentToEntity(document)));

        return tokens;
    }

    @Override
    public Iterable<GuestToken> findAllById(Iterable<String> strings) {
        throw new UnsupportedOperationException("Operation findAllById is not yet implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Operation count is not yet implemented");
    }

    @Override
    public void deleteById(String id) {
        logger.debug("Deleting guest token with id " + id);

        this.repository.deleteById(id);
    }

    @Override
    public void deleteByAccreditationId(String accreditationId) {
        logger.debug("Deleting guest token with id " + accreditationId);

        this.repository.deleteByAccreditationId(accreditationId);
    }

    @Override
    public void delete(GuestToken entity) {
        throw new UnsupportedOperationException("Operation delete is not yet implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        throw new UnsupportedOperationException("Operation deleteAllById is not yet implemented");
    }

    @Override
    public void deleteAll(
        Iterable<? extends GuestToken> entities) {
        throw new UnsupportedOperationException("Operation deleteAll is not yet implemented");
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }
}
