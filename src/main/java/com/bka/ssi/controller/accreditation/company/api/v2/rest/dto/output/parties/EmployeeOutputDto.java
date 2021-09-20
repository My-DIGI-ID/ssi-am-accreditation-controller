package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeOutputDto {

    @Size(max = 50)
    @NotEmpty
    @NotNull
    private String id;

    @Size(max = 50)
    @NotNull
    private String title;

    @Size(max = 50)
    @NotNull
    private String firstName;

    @Size(max = 50)
    @NotNull
    private String lastName;

    @Size(max = 100)
    @Email
    @NotNull
    @NotEmpty
    private String email;

    @Size(max = 50)
    private String primaryPhoneNumber;

    @Size(max = 50)
    private String secondaryPhoneNumber;

    @Size(max = 50)
    private String employeeId;

    @Size(max = 50)
    @NotNull
    private String employeeState;

    @Size(max = 100)
    private String position;

    @Size(max = 100)
    @NotNull
    private String companyName;

    @Size(max = 50)
    @NotNull
    private String companyStreet;

    @Size(max = 50)
    @NotNull
    private String companyPostalCode;

    @Size(max = 50)
    private String companyCity;

    public EmployeeOutputDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeState() {
        return employeeState;
    }

    public void setEmployeeState(String employeeState) {
        this.employeeState = employeeState;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    public String getCompanyPostalCode() {
        return companyPostalCode;
    }

    public void setCompanyPostalCode(String companyPostalCode) {
        this.companyPostalCode = companyPostalCode;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }
}
