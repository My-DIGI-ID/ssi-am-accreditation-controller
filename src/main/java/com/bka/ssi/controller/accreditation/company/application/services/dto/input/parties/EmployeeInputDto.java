package com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties;

import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.general.NoForbiddenCharacters;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeInputDto {

    /* TODO - Implement characters escape annotation */
    /* TODO - Validation of PostalCode and PhoneNumbers */
    /* TODO - companySubject? */

    @Size(max = 50)
    @NoForbiddenCharacters
    @CsvBindByName(column = "title")
    @CsvBindByPosition(position = 0)
    private String title;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "firstName")
    @CsvBindByPosition(position = 1)
    private String firstName;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
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

    @Size(max = 50)
    @NoForbiddenCharacters
    @CsvBindByName(column = "employeeId")
    @CsvBindByPosition(position = 6)
    private String employeeId;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "employeeState")
    @CsvBindByPosition(position = 7)
    private String employeeState;

    @Size(max = 100)
    @NoForbiddenCharacters
    @CsvBindByName(column = "position")
    @CsvBindByPosition(position = 8)
    private String position;

    @Size(max = 200)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "companyName")
    @CsvBindByPosition(position = 9)
    private String companyName;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "companyStreet")
    @CsvBindByPosition(position = 10)
    private String companyStreet;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "companyPostalCode")
    @CsvBindByPosition(position = 11)
    private String companyPostalCode;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "companyCity")
    @CsvBindByPosition(position = 12)
    private String companyCity;

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeState() {
        return employeeState;
    }

    public String getPosition() {
        return position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public String getCompanyPostalCode() {
        return companyPostalCode;
    }

    public String getCompanyCity() {
        return companyCity;
    }
}
