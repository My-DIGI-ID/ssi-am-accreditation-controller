package com.bka.ssi.controller.accreditation.company.testutilities.party.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class EmployeeInputDtoBuilder {

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

    public EmployeeInputDtoBuilder() {
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
        EmployeeInputDto dto = new EmployeeInputDto();

        try {
            dto = objectMapper.readValue(concatenated, EmployeeInputDto.class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            fail(e.getMessage());
        }

        return dto;
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

    @Test
    void buildEmployeeInputDto() {
        this.reset();

        this.title = "title";
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.email = "email@email.xyz";
        this.primaryPhoneNumber = "0123456789";
        this.secondaryPhoneNumber = "9876543210";
        this.employeeId = "employeeId";
        this.employeeState = "employeeState";
        this.position = "position";
        this.companyName = "companyName";
        this.companyStreet = "companyStreet";
        this.companyPostalCode = "companyPostalCode";
        this.companyCity = "companyCity";

        EmployeeInputDto dto = this.build();

        assertEquals(this.title, dto.getTitle());
        assertEquals(this.firstName, dto.getFirstName());
        assertEquals(this.lastName, dto.getLastName());
        assertEquals(this.email, dto.getEmail());
        assertEquals(this.primaryPhoneNumber, dto.getPrimaryPhoneNumber());
        assertEquals(this.secondaryPhoneNumber, dto.getSecondaryPhoneNumber());
        assertEquals(this.employeeId, dto.getEmployeeId());
        assertEquals(this.employeeState, dto.getEmployeeState());
        assertEquals(this.position, dto.getPosition());
        assertEquals(this.companyName, dto.getCompanyName());
        assertEquals(this.companyStreet, dto.getCompanyStreet());
        assertEquals(this.companyPostalCode, dto.getCompanyPostalCode());
        assertEquals(this.companyCity, dto.getCompanyCity());

        this.reset();
    }
}
