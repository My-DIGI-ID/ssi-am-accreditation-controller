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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address("postalCode", "country", "city", "street",
            "houseNumber", "doorNumber");
    }

    @Test
    void testConstructors() {

        address = new Address("postalCode", "country", "city", "street",
            "houseNumber", "doorNumber");
        assertEquals("houseNumber", address.getHouseNumber());

        address = new Address("postalCode", "city", "street");
        assertEquals(null, address.getHouseNumber());

        address = new Address("postalCode", "street");
        assertEquals(null, address.getCity());
    }

    @Test
    void testGetPostalCode() {

        assertEquals("postalCode", address.getPostalCode());
    }

    @Test
    void testGetCountry() {
        assertEquals("country", address.getCountry());
    }

    @Test
    void testGetCity() {
        assertEquals("city", address.getCity());
    }

    @Test
    void testGetStreet() {
        assertEquals("street", address.getStreet());
    }

    @Test
    void testGetHouseNumber() {
        assertEquals("houseNumber", address.getHouseNumber());
    }

    @Test
    void testGetDoorNumber() {
        assertEquals("doorNumber", address.getDoorNumber());
    }
}