package com.bka.ssi.controller.accreditation.company.domain.enums;

public enum GuestAccreditationStatus implements AccreditationStatus {
    OPEN(DefaultAccreditationStatus.OPEN),
    PENDING(DefaultAccreditationStatus.PENDING),
    ACCEPTED(DefaultAccreditationStatus.ACCEPTED),
    CANCELLED(DefaultAccreditationStatus.CANCELLED),
    REVOKED(DefaultAccreditationStatus.REVOKED),
    BASIS_ID_VERIFICATION_PENDING("BASIS_ID_VERIFICATION_PENDING"),
    BASIS_ID_VALID("BASIS_ID_VALID"),
    BASIS_ID_INVALID("BASIS_ID_INVALID");

    private final String name;

    GuestAccreditationStatus(DefaultAccreditationStatus defaultAccreditationStatus) {
        this.name = defaultAccreditationStatus.getName();
    }

    GuestAccreditationStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
