package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties;

import org.springframework.data.mongodb.core.mapping.Field;

public class GuestPrivateInformationMongoDbValue {

    @Field("dateOfBirth")
    private String dateOfBirth;

    @Field("licencePlateNumber")
    private String licencePlateNumber;

    @Field("companyStreet")
    private String companyStreet;

    @Field("companyCity")
    private String companyCity;

    @Field("companyPostCode")
    private String companyPostCode;

    @Field("acceptedDocument")
    private String acceptedDocument;

    public GuestPrivateInformationMongoDbValue() {
    }

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
