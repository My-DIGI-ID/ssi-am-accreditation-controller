package com.bka.ssi.controller.accreditation.company.domain.values;

public class Company {

    private String name;
    private Address address;
    private String subject;

    /* TODO - Values invariants checks to be added as part of functional stories */
    public Company(String name, Address address, String subject) {
        this.name = name;
        this.address = address;
        this.subject = subject;
    }

    public Company(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getSubject() {
        return subject;
    }
}
