package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.security;

import com.bka.ssi.controller.accreditation.company.application.security.GuestAccessTokenRepository;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security.GuestAccessTokenMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.security.GuestAccessTokenMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.security.GuestAccessTokenMongoDbRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Repository("guestAccessTokenMongoDbFacade")
public class GuestAccessTokenMongoDbFacade implements GuestAccessTokenRepository {

    private final GuestAccessTokenMongoDbRepository repository;
    private final GuestAccessTokenMongoDbMapper mapper;

    public GuestAccessTokenMongoDbFacade(
        GuestAccessTokenMongoDbRepository repository,
        GuestAccessTokenMongoDbMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public GuestToken save(GuestToken token) {
        GuestAccessTokenMongoDbDocument tokenMongoDbDocument = mapper.entityToDocument(token);

        GuestAccessTokenMongoDbDocument savedTokenMongoDbDocument =
            this.repository.save(tokenMongoDbDocument);

        GuestToken savedToken = mapper.documentToEntity(savedTokenMongoDbDocument);

        return savedToken;
    }

    @Override
    public <S extends GuestToken> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<GuestToken> findById(String id) {
        Optional<GuestAccessTokenMongoDbDocument> document =
            this.repository.findById(id);

        GuestToken token = this.mapper.documentToEntity(document.get());

        return Optional.of(token);
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<GuestToken> findAll() {
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
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteByAccreditationId(String accreditationId) {

        Iterator<GuestToken> iterator = this.findAll().iterator();
        while (iterator.hasNext()) {
            GuestToken token = iterator.next();

            if (token.getAccreditationId().equals(accreditationId)) {
                this.repository.deleteById(token.getId());
            }
        }
    }

    @Override
    public void delete(
        GuestToken entity) {
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(
        Iterable<? extends GuestToken> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
