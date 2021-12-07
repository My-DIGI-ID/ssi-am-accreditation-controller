package com.bka.ssi.controller.accreditation.company.domain.enums;

public enum EmployeeState {
    INTERNAL("INTERNAL"),
    EXTERNAL("EXTERNAL"),
    SERVICE_PROVIDER("SERVICE PROVIDER");

    private final String name;

    EmployeeState(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
