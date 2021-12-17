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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties;

import java.time.ZonedDateTime;

/**
 * The type Employee output dto.
 */
public class EmployeeOutputDto {

    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String primaryPhoneNumber;
    private String secondaryPhoneNumber;
    private String employeeId;
    private String employeeState;
    private String position;
    private String companyName;
    private String companyStreet;
    private String companyPostalCode;
    private String companyCity;
    private String createdBy;
    private ZonedDateTime createdAt;

    /**
     * Instantiates a new Employee output dto.
     */
    public EmployeeOutputDto() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Sets primary phone number.
     *
     * @param primaryPhoneNumber the primary phone number
     */
    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
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
     * Sets secondary phone number.
     *
     * @param secondaryPhoneNumber the secondary phone number
     */
    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
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
     * Sets employee id.
     *
     * @param employeeId the employee id
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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
     * Sets employee state.
     *
     * @param employeeState the employee state
     */
    public void setEmployeeState(String employeeState) {
        this.employeeState = employeeState;
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
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(String position) {
        this.position = position;
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
     * Sets company name.
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
     * Sets company street.
     *
     * @param companyStreet the company street
     */
    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
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
     * Sets company postal code.
     *
     * @param companyPostalCode the company postal code
     */
    public void setCompanyPostalCode(String companyPostalCode) {
        this.companyPostalCode = companyPostalCode;
    }

    /**
     * Gets company city.
     *
     * @return the company city
     */
    public String getCompanyCity() {
        return companyCity;
    }

    /**
     * Sets company city.
     *
     * @param companyCity the company city
     */
    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
