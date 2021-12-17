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
 * The type Company.
 */
public class Company {

    private String name;
    private Address address;
    private String subject;

    /**
     * Instantiates a new Company.
     *
     * @param name    the name
     * @param address the address
     * @param subject the subject
     */
    public Company(String name, Address address, String subject) {
        this.name = name;
        this.address = address;
        this.subject = subject;
    }

    /**
     * Instantiates a new Company.
     *
     * @param name    the name
     * @param address the address
     */
    public Company(String name, Address address) {
        this.name = name;
        this.address = address;
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
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
}
