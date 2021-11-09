package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

public class ValidityTimeframeDbValue {

    @Field("validFrom")
    private ZonedDateTime validFrom;

    @Field("validUntil")
    private ZonedDateTime validUntil;

    public ValidityTimeframeDbValue() {
    }

    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(ZonedDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(ZonedDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
