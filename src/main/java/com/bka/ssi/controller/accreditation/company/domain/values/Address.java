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
 * The type Address.
 */
public class Address {

    private String postalCode;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String doorNumber;

    /**
     * Instantiates a new Address.
     *
     * @param postalCode  the postal code
     * @param country     the country
     * @param city        the city
     * @param street      the street
     * @param houseNumber the house number
     * @param doorNumber  the door number
     */
    public Address(String postalCode, String country, String city, String street,
        String houseNumber, String doorNumber) {
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.doorNumber = doorNumber;
    }

    /**
     * Instantiates a new Address.
     *
     * @param postalCode the postal code
     * @param city       the city
     * @param street     the street
     */
    public Address(String postalCode, String city, String street) {
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
    }

    /**
     * Instantiates a new Address.
     *
     * @param postalCode the postal code
     * @param street     the street
     */
    public Address(String postalCode, String street) {
        this.postalCode = postalCode;
        this.street = street;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Gets house number.
     *
     * @return the house number
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Gets door number.
     *
     * @return the door number
     */
    public String getDoorNumber() {
        return doorNumber;
    }
}
