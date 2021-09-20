package com.bka.ssi.controller.accreditation.company.application.testutils;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class BuildEmployeeCompositeInputDTO {

    private final String json;
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

    public BuildEmployeeCompositeInputDTO() {
        this.json =
            "{\"title\":\"%1$s\", \"firstName\":\"%2$s\",\"lastName\":\"%3$s\"," +
                "\"email\":\"%4$s\",\"primaryPhoneNumber\":\"%5$s\", " +
                "\"secondaryPhoneNumber\":\"%6$s\"," +
                "\"employeeId\":\"%7$s\", \"employeeState\":\"%8$s\", \"position\":\"%9$s\", " +
                "\"companyName\":\"%10$s\", \"companyStreet\":\"%11$s\", " +
                "\"companyPostalCode\":\"%12$s\", \"companyCity\":\"%13$s\"}";
    }

    public EmployeeInputDto build() {
        String concatenated = String.format(this.json, this.title, this.firstName, this.lastName,
            this.email, this.primaryPhoneNumber, this.secondaryPhoneNumber, this.employeeId,
            this.employeeState, this.position, this.companyName, this.companyStreet,
            this.companyPostalCode, this.companyCity);
        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeInputDto employee = new EmployeeInputDto();
        try {
            employee = objectMapper.readValue(concatenated, EmployeeInputDto.class);
        } catch (IOException e) {
            System.err.println(e);
        }
        return employee;
    }

    public void reset() {
        this.title = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.primaryPhoneNumber = "";
        this.secondaryPhoneNumber = "";
        this.employeeId = "";
        this.employeeState = "";
        this.position = "";
        this.companyName = "";
        this.companyStreet = "";
        this.companyPostalCode = "";
        this.companyCity = "";
    }
}
