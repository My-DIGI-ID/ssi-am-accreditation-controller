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

package com.bka.ssi.controller.accreditation.company.application.factories;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;

/**
 * The interface Accreditation factory.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
public interface AccreditationFactory<T extends Party, R extends Accreditation<T, ?>> {

    /**
     * Create r.
     *
     * @param input the input
     * @return the r
     * @throws Exception the exception
     */
    default public R create(T input) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Create r.
     *
     * @param input    the input
     * @param userName the user name
     * @return the r
     * @throws Exception the exception
     */
    default public R create(T input, String userName) throws Exception {
        throw new UnsupportedOperationException();
    }
}
