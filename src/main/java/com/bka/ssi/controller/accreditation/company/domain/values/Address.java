package com.bka.ssi.controller.accreditation.company.domain.values;

public class Address {

    private String postalCode;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String doorNumber;

    public Address(String postalCode, String country, String city, String street,
        String houseNumber, String doorNumber) {
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.doorNumber = doorNumber;
    }

    public Address(String postalCode, String city, String street) {
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
    }

    public Address(String postalCode, String street) {
        this.postalCode = postalCode;
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getDoorNumber() {
        return doorNumber;
    }
}
