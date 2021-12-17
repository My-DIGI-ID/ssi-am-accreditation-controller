/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties;

import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.NoForbiddenCharacters;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.PhoneNumber;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.PostalCode;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employee.EmployeeId;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employee.EmployeeState;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Employee input dto.
 */
public class EmployeeInputDto {

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
    @PhoneNumber
    @CsvBindByName(column = "primaryPhoneNumber")
    @CsvBindByPosition(position = 4)
    private String primaryPhoneNumber;

    @Size(max = 50)
    @PhoneNumber
    @CsvBindByName(column = "secondaryPhoneNumber")
    @CsvBindByPosition(position = 5)
    private String secondaryPhoneNumber;

    @Size(max = 50)
    @EmployeeId
    @CsvBindByName(column = "employeeId")
    @CsvBindByPosition(position = 6)
    private String employeeId;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @EmployeeState
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
    @NoForbiddenCharacters
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

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets primary phone number.
     *
     * @return the primary phone number
     */
    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    /**
     * Gets secondary phone number.
     *
     * @return the secondary phone number
     */
    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    /**
     * Gets employee id.
     *
     * @return the employee id
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets employee state.
     *
     * @return the employee state
     */
    public String getEmployeeState() {
        return employeeState;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets company street.
     *
     * @return the company street
     */
    public String getCompanyStreet() {
        return companyStreet;
    }

    /**
     * Gets company postal code.
     *
     * @return the company postal code
     */
    public String getCompanyPostalCode() {
        return companyPostalCode;
    }

    /**
     * Gets company city.
     *
     * @return the company city
     */
    public String getCompanyCity() {
        return companyCity;
    }
}
