package com.bka.ssi.controller.accreditation.company.testutilities.party.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    public ZonedDateTime createdAt;

    public EmployeeOutputDtoBuilder() {
        this.json =
            "{\"id\":\"%1$s\", \"title\":\"%2$s\", \"firstName\":\"%3$s\",\"lastName\":\"%4$s\"," +
                "\"email\":\"%5$s\",\"primaryPhoneNumber\":\"%6$s\", " +
                "\"secondaryPhoneNumber\":\"%7$s\"," +
                "\"employeeId\":\"%8$s\", \"employeeState\":\"%9$s\", \"position\":\"%10$s\", " +
                "\"companyName\":\"%11$s\", \"companyStreet\":\"%12$s\", " +
                "\"companyPostalCode\":\"%13$s\", \"companyCity\":\"%14$s\", " +
                "\"createdBy\":\"%15$s\", \"createdAt\":\"%16$s\"}";
    }

    public EmployeeOutputDto build() {
        String concatenated = String.format(this.json, this.id, this.title, this.firstName,
            this.lastName, this.email, this.primaryPhoneNumber, this.secondaryPhoneNumber,
            this.employeeId,
            this.employeeState, this.position, this.companyName, this.companyStreet,
            this.companyPostalCode, this.companyCity, this.createdBy, this.createdAt);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        objectMapper.registerModule(new JavaTimeModule());
        EmployeeOutputDto dto = new EmployeeOutputDto();

        try {
            dto = objectMapper.readValue(concatenated, EmployeeOutputDto.class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            fail(e.getMessage());
        }

        return dto;
    }

    public void reset() {
        this.id = null;
        this.title = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.primaryPhoneNumber = null;
        this.secondaryPhoneNumber = null;
        this.employeeId = null;
        this.employeeState = null;
        this.position = null;
        this.companyName = null;
        this.companyStreet = null;
        this.companyPostalCode = null;
        this.companyCity = null;
        this.createdBy = null;
        this.createdAt = null;
    }

    public EmployeeOutputDto buildEmployeeOutputDto() {
        this.id = this.id != null ? this.id : "id";
        this.title = this.title != null ? this.title : "title";
        this.firstName = this.firstName != null ? this.firstName : "firstName";
        this.lastName = this.lastName != null ? this.lastName : "lastName";
        this.email = this.email != null ? this.email : "email@email.xyz";
        this.primaryPhoneNumber =
            this.primaryPhoneNumber != null ? this.primaryPhoneNumber : "0123456789";
        this.secondaryPhoneNumber =
            this.secondaryPhoneNumber != null ? this.secondaryPhoneNumber : "9876543210";
        this.employeeId = this.employeeId != null ? this.employeeId : "employeeId";
        this.employeeState = this.employeeState != null ? this.employeeState : "employeeState";
        this.position = this.position != null ? this.position : "position";
        this.companyName = this.companyName != null ? this.companyName : "companyName";
        this.companyStreet = this.companyStreet != null ? this.companyStreet : "companyStreet";
        this.companyPostalCode =
            this.companyPostalCode != null ? this.companyPostalCode : "companyPostalCode";
        this.companyCity = this.companyCity != null ? this.companyCity : "companyCity";
        this.createdBy = this.createdBy != null ? this.createdBy : "createdBy";
        this.createdAt = this.createdAt != null ? this.createdAt :
            ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"));

        return this.build();
    }

    @Test
    void buildEmployeeOutputDtoTest() {
        EmployeeOutputDto dto = this.buildEmployeeOutputDto();

        assertEquals(this.id, dto.getId());
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
        assertEquals(this.createdBy, dto.getCreatedBy());
        assertEquals(this.createdAt, dto.getCreatedAt());

        this.reset();
    }
}
