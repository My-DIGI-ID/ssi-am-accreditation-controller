package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class ValidityTimeframeDbValue {

    @Field("validFromDate")
    private Date validFromDate;

    @Field("validFromTime")
    private Date validFromTime;

    @Field("validUntilDate")
    private Date validUntilDate;

    @Field("validUntilTime")
    private Date validUntilTime;

    public ValidityTimeframeDbValue() {
    }

    public Date getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(Date validFromDate) {
        this.validFromDate = validFromDate;
    }

    public Date getValidFromTime() {
        return validFromTime;
    }

    public void setValidFromTime(Date validFromTime) {
        this.validFromTime = validFromTime;
    }

    public Date getValidUntilDate() {
        return validUntilDate;
    }

    public void setValidUntilDate(Date validUntilDate) {
        this.validUntilDate = validUntilDate;
    }

    public Date getValidUntilTime() {
        return validUntilTime;
    }

    public void setValidUntilTime(Date validUntilTime) {
        this.validUntilTime = validUntilTime;
    }
}
