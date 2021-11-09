package com.bka.ssi.controller.accreditation.company.testutilities.party.employee;

import java.util.Date;

public class EmployeeOutputDtoBuilder {
    private final String json;
    public String id;

    public String title;

    public String firstName;

    public String lastName;

    public String email;

    public String primaryPhoneNumber;

    public String secondaryPhoneNumber;

    public String employeeId;

    public String employeeState;

    public String position;

    public String companyName;

    public String companyStreet;

    public String companyPostalCode;

    public String companyCity;

    public String createdBy;

    public Date createdAt;

    public EmployeeOutputDtoBuilder() {
        this.json =
            "{\"title\":\"%1$s\", \"firstName\":\"%2$s\",\"lastName\":\"%3$s\"," +
                "\"email\":\"%4$s\",\"primaryPhoneNumber\":\"%5$s\", " +
                "\"secondaryPhoneNumber\":\"%6$s\"," +
                "\"employeeId\":\"%7$s\", \"employeeState\":\"%8$s\", \"position\":\"%9$s\", " +
                "\"companyName\":\"%10$s\", \"companyStreet\":\"%11$s\", " +
                "\"companyPostalCode\":\"%12$s\", \"companyCity\":\"%13$s\"}";
    }
}
