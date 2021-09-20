package com.bka.ssi.controller.accreditation.company.domain.values;

public class Employer extends Company {

    private String proofOfOwnership;

    /* TODO - Values invariants checks to be added as part of functional stories */
    public Employer(String name, Address address, String subject, String proofOfOwnership) {
        super(name, address, subject);

        this.proofOfOwnership = proofOfOwnership;
    }

    public Employer(String name, Address address, String subject) {
        super(name, address, subject);
    }

    public Employer(String name, Address address) {
        super(name, address);
    }

    public String getProofOfOwnership() {
        return proofOfOwnership;
    }
}
