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

package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import io.github.my_digi_id.acapy_client.model.CredentialPreview;


/**
 * The interface Acapy credential utility.
 *
 * @param <T> the type parameter
 */
public interface ACAPYCredentialUtility<T extends Credential> {

    /**
     * Create credential preview credential preview.
     *
     * @param input the input
     * @return the credential preview
     */
    default public CredentialPreview createCredentialPreview(T input) {
        throw new UnsupportedOperationException();
    }
}
