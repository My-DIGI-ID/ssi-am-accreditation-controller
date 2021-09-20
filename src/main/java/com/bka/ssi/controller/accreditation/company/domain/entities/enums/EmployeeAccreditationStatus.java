package com.bka.ssi.controller.accreditation.company.domain.entities.enums;

public enum EmployeeAccreditationStatus implements AccreditationStatus {
    OPEN(DefaultAccreditationStatus.OPEN),
    PENDING(DefaultAccreditationStatus.PENDING),
    ACCEPTED(DefaultAccreditationStatus.ACCEPTED),
    CANCELLED(DefaultAccreditationStatus.CANCELLED),
    REVOKED(DefaultAccreditationStatus.REVOKED),
    MIGHT_HAVE_ADDITIONAL_STATUS("MIGHT_HAVE_ADDITIONAL_STATUS");

    private final String name;

    EmployeeAccreditationStatus(DefaultAccreditationStatus defaultAccreditationStatus) {
        this.name = defaultAccreditationStatus.getName();
    }

    EmployeeAccreditationStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
