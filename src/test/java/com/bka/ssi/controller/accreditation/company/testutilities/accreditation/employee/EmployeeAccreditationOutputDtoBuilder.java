package com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;

import java.util.Date;

public class EmployeeAccreditationOutputDtoBuilder {

    private final String json;

    private String id;
    private EmployeeOutputDto employee;
    private String status;
    private String invitedBy;
    private Date invitedAt;
    private String invitationUrl;
    private String invitationEmail;
    private String invitationQrCode;

    public EmployeeAccreditationOutputDtoBuilder() {
        this.json = "";
    }
}
