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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.PartyMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.GuestCredentialMongoDbValue;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Guest mongo db document.
 */
@Document(collection = "guests")
public class GuestMongoDbDocument extends PartyMongoDbDocument<GuestCredentialMongoDbValue> {

    /**
     * Instantiates a new Guest mongo db document.
     */
    public GuestMongoDbDocument() {
    }
}
