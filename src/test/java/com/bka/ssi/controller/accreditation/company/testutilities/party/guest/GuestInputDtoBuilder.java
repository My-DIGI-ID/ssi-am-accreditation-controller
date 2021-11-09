package com.bka.ssi.controller.accreditation.company.testutilities.party.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.GuestInputDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;

public class GuestInputDtoBuilder {

    private final String json;
    public String title;
    public String firstName;
    public String lastName;
    public String email;
    public String primaryPhoneNumber;
    public String secondaryPhoneNumber;
    public String companyName;
    public String typeOfVisit;
    public String location;
    public String validFrom;
    public String validUntil;

    public GuestInputDtoBuilder() {
        this.json =
            "{\"title\":\"%1$s\", \"firstName\":\"%2$s\",\"lastName\":\"%3$s\"," +
                "\"email\":\"%4$s\",\"primaryPhoneNumber\":\"%5$s\", " +
                "\"secondaryPhoneNumber\":\"%6$s\"," +
                "\"companyName\":\"%7$s\", \"typeOfVisit\":\"%8$s\", \"location\":\"%9$s\", " +
                "\"validFrom\":\"%10$s\", \"validUntil\":\"%11$s\"}";
    }

    public GuestInputDto build() {
        String concatenated = String.format(this.json, this.title, this.firstName, this.lastName,
            this.email, this.primaryPhoneNumber, this.secondaryPhoneNumber, this.companyName,
            this.typeOfVisit, this.location, this.validFrom, this.validUntil);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        objectMapper.registerModule(new JavaTimeModule());
        GuestInputDto dto = new GuestInputDto();

        try {
            dto = objectMapper.readValue(concatenated, GuestInputDto.class);
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
        this.companyName = "";
        this.typeOfVisit = "";
        this.location = "";
        this.validFrom = "";
        this.validUntil = "";
    }

    @Test
    void buildGuestInputDto() {
        this.reset();

        this.title = "title";
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.email = "email@email.xyz";
        this.primaryPhoneNumber = "0123456789";
        this.secondaryPhoneNumber = "9876543210";
        this.companyName = "companyName";
        this.typeOfVisit = "typeOfVisit";
        this.location = "location";
        this.validFrom = "2022-12-31T01:00:00+01:00";
        this.validUntil = "2022-12-31T04:59:59+01:00";

        GuestInputDto dto = this.build();

        assertEquals(this.title, dto.getTitle());
        assertEquals(this.firstName, dto.getFirstName());
        assertEquals(this.lastName, dto.getLastName());
        assertEquals(this.email, dto.getEmail());
        assertEquals(this.primaryPhoneNumber, dto.getPrimaryPhoneNumber());
        assertEquals(this.secondaryPhoneNumber, dto.getSecondaryPhoneNumber());
        assertEquals(this.companyName, dto.getCompanyName());
        assertEquals(this.typeOfVisit, dto.getTypeOfVisit());
        assertEquals(this.location, dto.getLocation());
        assertEquals(this.validFrom,
            dto.getValidFrom().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(this.validUntil,
            dto.getValidUntil().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        this.reset();
    }
}
