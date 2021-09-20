package com.bka.ssi.controller.accreditation.company.api.v1.rest.dto.output;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeOutputDTO {

    @NotNull
    @Size(max = 50)
    private String employeeId;

    @Size(max = 50)
    @NotNull
    private String firstName;

    @Size(max = 50)
    @NotNull
    private String lastName;

    @Email
    @NotNull
    @NotEmpty
    private String email;

    @Size(max = 100)
    @NotNull
    private String firmName;

    @Size(max = 50)
    private String firmSubject;

    @Size(max = 50)
    @NotNull
    private String firmStreet;

    @Size(max = 50)
    @NotNull
    private String firmPostalCode;

    @Size(max = 50)
    @NotNull
    private String firmCity;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getFirmSubject() {
        return firmSubject;
    }

    public void setFirmSubject(String firmSubject) {
        this.firmSubject = firmSubject;
    }

    public String getFirmStreet() {
        return firmStreet;
    }

    public void setFirmStreet(String firmStreet) {
        this.firmStreet = firmStreet;
    }

    public String getFirmPostalCode() {
        return firmPostalCode;
    }

    public void setFirmPostalCode(String firmPostalCode) {
        this.firmPostalCode = firmPostalCode;
    }

    public String getFirmCity() {
        return firmCity;
    }

    public void setFirmCity(String firmCity) {
        this.firmCity = firmCity;
    }
}
