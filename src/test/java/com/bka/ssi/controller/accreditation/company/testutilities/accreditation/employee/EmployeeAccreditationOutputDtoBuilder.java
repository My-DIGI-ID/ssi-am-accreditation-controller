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
        this.json =
            "{\"title\":\"%1$s\", \"firstName\":\"%2$s\",\"lastName\":\"%3$s\"," +
                "\"email\":\"%4$s\",\"primaryPhoneNumber\":\"%5$s\", " +
                "\"secondaryPhoneNumber\":\"%6$s\"," +
                "\"employeeId\":\"%7$s\", \"employeeState\":\"%8$s\", \"position\":\"%9$s\", " +
                "\"companyName\":\"%10$s\", \"companyStreet\":\"%11$s\", " +
                "\"companyPostalCode\":\"%12$s\", \"companyCity\":\"%13$s\"}";
    }
}
