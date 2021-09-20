package com.bka.ssi.controller.accreditation.company.domain.values;

import java.util.Date;

public class ValidityTimeframe {

    private Date validFromDate;
    private Date validFromTime;
    private Date validUntilDate;
    private Date validUntilTime;

    public ValidityTimeframe(Date validFromDate, Date validFromTime, Date validUntilDate,
        Date validUntilTime) {
        this.validFromDate = validFromDate;
        this.validFromTime = validFromTime;
        this.validUntilDate = validUntilDate;
        this.validUntilTime = validUntilTime;
    }

    public Date getValidFromDate() {
        return validFromDate;
    }

    public Date getValidFromTime() {
        return validFromTime;
    }

    public Date getValidUntilDate() {
        return validUntilDate;
    }

    public Date getValidUntilTime() {
        return validUntilTime;
    }
}
