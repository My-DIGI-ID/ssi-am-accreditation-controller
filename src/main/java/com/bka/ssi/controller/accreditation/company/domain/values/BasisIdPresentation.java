package com.bka.ssi.controller.accreditation.company.domain.values;

public class BasisIdPresentation {

    private String firstName;
    private String familyName;
    private String dateOfBirth;

    public BasisIdPresentation(String firstName, String familyName, String dateOfBirth) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
