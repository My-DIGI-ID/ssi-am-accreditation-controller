package com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GuestAccreditationPrivateInfoInputDto {

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

    @Size(max = 50)
    private String primaryPhoneNumber;

    @Size(max = 50)
    private String secondaryPhoneNumber;

    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public String getCompanyPostCode() {
        return companyPostCode;
    }

    public String getAcceptedDocument() {
        return acceptedDocument;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }
}
