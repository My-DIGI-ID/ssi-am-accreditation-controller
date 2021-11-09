package com.bka.ssi.controller.accreditation.company.domain.enums;

public enum CredentialType {
    EMPLOYEE("EMPLOYEE"),
    GUEST("GUEST");

    private final String name;

    CredentialType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
