package com.bka.ssi.controller.accreditation.company.domain.values;

public class GuestPrivateInformation {

    private String dateOfBirth;
    private String licencePlateNumber;
    private String companyStreet;
    private String companyCity;
    private String companyPostCode;
    private String acceptedDocument;

    public GuestPrivateInformation(String dateOfBirth, String licencePlateNumber,
        String companyStreet, String companyCity,
        String companyPostCode, String acceptedDocument) {
        this.dateOfBirth = dateOfBirth;
        this.licencePlateNumber = licencePlateNumber;
        this.companyStreet = companyStreet;
        this.companyCity = companyCity;
        this.companyPostCode = companyPostCode;
        this.acceptedDocument = acceptedDocument;
    }

    public GuestPrivateInformation(String licencePlateNumber, String companyStreet,
        String companyCity, String companyPostCode,
        String acceptedDocument) {
        this.licencePlateNumber = licencePlateNumber;
        this.companyStreet = companyStreet;
        this.companyCity = companyCity;
        this.companyPostCode = companyPostCode;
        this.acceptedDocument = acceptedDocument;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

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
}
