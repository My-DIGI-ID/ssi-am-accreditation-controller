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