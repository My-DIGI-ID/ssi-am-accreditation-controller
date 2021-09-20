package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GuestPrivateOutputDto extends GuestOpenOutputDto {

    @Size(max = 50)
    @NotNull
    private String dateOfBirth;

    @Size(max = 50)
    private String licencePlateNumber;

    @Size(max = 50)
    @NotNull
    private String companyStreet;

    @Size(max = 50)
    @NotNull
    private String companyCity;

    @Size(max = 50)
    @NotNull
    private String companyPostCode;

    @Size(max = 50)
    @NotNull
    private String acceptedDocument;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public void setLicencePlateNumber(String licencePlateNumber) {
        this.licencePlateNumber = licencePlateNumber;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyPostCode() {
        return companyPostCode;
    }

    public void setCompanyPostCode(String companyPostCode) {
        this.companyPostCode = companyPostCode;
    }

    public String getAcceptedDocument() {
        return acceptedDocument;
    }

    public void setAcceptedDocument(String acceptedDocument) {
        this.acceptedDocument = acceptedDocument;
    }
}
