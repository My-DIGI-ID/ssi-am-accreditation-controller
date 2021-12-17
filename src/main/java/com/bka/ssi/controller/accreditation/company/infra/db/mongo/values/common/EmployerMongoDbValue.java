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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Employer mongo db value.
 */
public class EmployerMongoDbValue {

    @Field("name")
    private String name;

    @Field("address")
    private AddressMongoDbValue address;

    @Field("subject")
    private String subject;

    @Field("proofOfOwnership")
    private String proofOfOwnership;

    /**
     * Instantiates a new Employer mongo db value.
     */
    public EmployerMongoDbValue() {
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public AddressMongoDbValue getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(
        AddressMongoDbValue address) {
        this.address = address;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets proof of ownership.
     *
     * @return the proof of ownership
     */
    public String getProofOfOwnership() {
        return proofOfOwnership;
    }

    /**
     * Sets proof of ownership.
     *
     * @param proofOfOwnership the proof of ownership
     */
    public void setProofOfOwnership(String proofOfOwnership) {
        this.proofOfOwnership = proofOfOwnership;
    }
}
