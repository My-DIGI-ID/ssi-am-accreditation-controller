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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.AccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CorrelationMongoDbDocument;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Guest accreditation mongo db document.
 */
@Document(collection = "guestAccreditations")
public class GuestAccreditationMongoDbDocument extends AccreditationMongoDbDocument {

    @Field("basisIdVerificationCorrelation")
    private CorrelationMongoDbDocument basisIdVerificationCorrelation;

    @Field("guestCredentialIssuanceCorrelation")
    private CorrelationMongoDbDocument guestCredentialIssuanceCorrelation;

    /**
     * Instantiates a new Guest accreditation mongo db document.
     */
    public GuestAccreditationMongoDbDocument() {
    }

    /**
     * Gets basis id verification correlation.
     *
     * @return the basis id verification correlation
     */
    public CorrelationMongoDbDocument getBasisIdVerificationCorrelation() {
        return basisIdVerificationCorrelation;
    }

    /**
     * Sets basis id verification correlation.
     *
     * @param basisIdVerificationCorrelation the basis id verification correlation
     */
    public void setBasisIdVerificationCorrelation(
        CorrelationMongoDbDocument basisIdVerificationCorrelation) {
        this.basisIdVerificationCorrelation = basisIdVerificationCorrelation;
    }

    /**
     * Gets guest credential issuance correlation.
     *
     * @return the guest credential issuance correlation
     */
    public CorrelationMongoDbDocument getGuestCredentialIssuanceCorrelation() {
        return guestCredentialIssuanceCorrelation;
    }

    /**
     * Sets guest credential issuance correlation.
     *
     * @param guestCredentialIssuanceCorrelation the guest credential issuance correlation
     */
    public void setGuestCredentialIssuanceCorrelation(
        CorrelationMongoDbDocument guestCredentialIssuanceCorrelation) {
        this.guestCredentialIssuanceCorrelation = guestCredentialIssuanceCorrelation;
    }
}
