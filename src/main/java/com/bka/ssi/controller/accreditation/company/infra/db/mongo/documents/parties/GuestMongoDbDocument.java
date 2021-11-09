package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.PartyMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.GuestCredentialMongoDbValue;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "guests")
public class GuestMongoDbDocument extends PartyMongoDbDocument<GuestCredentialMongoDbValue> {

    public GuestMongoDbDocument() {
    }
}
