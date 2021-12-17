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

package com.bka.ssi.controller.accreditation.company.domain.values;

/**
 * The type Employer.
 */
public class Employer extends Company {

    private String proofOfOwnership;

    /**
     * Instantiates a new Employer.
     *
     * @param name             the name
     * @param address          the address
     * @param subject          the subject
     * @param proofOfOwnership the proof of ownership
     */
    public Employer(String name, Address address, String subject, String proofOfOwnership) {
        super(name, address, subject);

        this.proofOfOwnership = proofOfOwnership;
    }

    /**
     * Instantiates a new Employer.
     *
     * @param name    the name
     * @param address the address
     * @param subject the subject
     */
    public Employer(String name, Address address, String subject) {
        super(name, address, subject);
    }

    /**
     * Instantiates a new Employer.
     *
     * @param name    the name
     * @param address the address
     */
    public Employer(String name, Address address) {
        super(name, address);
    }

    /**
     * Gets proof of ownership.
     *
     * @return the proof of ownership
     */
    public String getProofOfOwnership() {
        return proofOfOwnership;
    }
}
