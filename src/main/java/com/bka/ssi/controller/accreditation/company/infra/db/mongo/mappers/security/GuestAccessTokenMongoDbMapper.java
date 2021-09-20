package com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.security;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.security.GuestAccessTokenMongoDbDocument;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GuestAccessTokenMongoDbMapper {

    private Logger logger;

    public GuestAccessTokenMongoDbMapper(Logger logger) {
        this.logger = logger;
    }

    public GuestToken documentToEntity(GuestAccessTokenMongoDbDocument document) {
        GuestToken entity = new GuestToken(document.getId(), document.getAccreditationId(),
            document.getExpiring());

        return entity;
    }

    public GuestAccessTokenMongoDbDocument entityToDocument(GuestToken token) {
        GuestAccessTokenMongoDbDocument document = new GuestAccessTokenMongoDbDocument();

        document.setId(token.getId());
        document.setAccreditationId(token.getAccreditationId());
        document.setExpiring(token.getExpiring());

        return document;
    }
}
