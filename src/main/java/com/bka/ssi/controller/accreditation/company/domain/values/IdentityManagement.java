package com.bka.ssi.controller.accreditation.company.domain.values;

public class IdentityManagement {

    private String reference;
    private String username;
    private String email;

    /* TODO - Entities invariants checks to be added as part of functional stories */
    public IdentityManagement(String reference, String username, String email) {
        this.reference = reference;
        this.username = username;
        this.email = email;
    }

    public IdentityManagement(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
