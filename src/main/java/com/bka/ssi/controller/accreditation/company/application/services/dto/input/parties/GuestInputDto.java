package com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GuestInputDto {

    @Size(max = 50)
    @CsvBindByName(column = "title")
    @CsvBindByPosition(position = 0)
    private String title;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "firstName")
    @CsvBindByPosition(position = 1)
    private String firstName;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "lastName")
    @CsvBindByPosition(position = 2)
    private String lastName;

    @Size(max = 100)
    @Email
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "email")
    @CsvBindByPosition(position = 3)
    private String email;

    @Size(max = 50)
    @CsvBindByName(column = "primaryPhoneNumber")
    @CsvBindByPosition(position = 4)
    private String primaryPhoneNumber;

    @Size(max = 50)
    @CsvBindByName(column = "secondaryPhoneNumber")
    @CsvBindByPosition(position = 5)
    private String secondaryPhoneNumber;

    @Size(max = 100)
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "companyName")
    @CsvBindByPosition(position = 6)
    private String companyName;

    @Size(max = 100)
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "typeOfVisit")
    @CsvBindByPosition(position = 7)
    private String typeOfVisit;

    @Size(max = 100)
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "location")
    @CsvBindByPosition(position = 8)
    private String location;

    @NotNull
    @CsvBindByName(column = "validFromDate")
    @CsvBindByPosition(position = 9)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date validFromDate;

    @NotNull
    @CsvBindByName(column = "validFromTime")
    @CsvBindByPosition(position = 10)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date validFromTime;

    @NotNull
    @CsvBindByName(column = "validUntilDate")
    @CsvBindByPosition(position = 11)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date validUntilDate;

    @NotNull
    @CsvBindByName(column = "validUntilTime")
    @CsvBindByPosition(position = 12)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date validUntilTime;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTypeOfVisit() {
        return typeOfVisit;
    }

    public String getLocation() {
        return location;
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