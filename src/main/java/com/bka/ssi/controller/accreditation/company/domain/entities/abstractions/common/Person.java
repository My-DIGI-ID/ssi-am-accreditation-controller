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

package com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common;

/**
 * The type Person.
 */
public abstract class Person extends Entity {

    private String title;
    private String firstName;
    private String lastName;

    /**
     * Instantiates a new Person.
     *
     * @param id        the id
     * @param title     the title
     * @param firstName the first name
     * @param lastName  the last name
     */
    /* ToDo - How to use in inheritance chain? Remove if Persona class suffice */
    public Person(String id, String title, String firstName, String lastName) {
        super(id);

        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Instantiates a new Person.
     *
     * @param id        the id
     * @param firstName the first name
     * @param lastName  the last name
     */
    public Person(String id, String firstName, String lastName) {
        super(id);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Instantiates a new Person.
     *
     * @param firstName the first name
     * @param lastName  the last name
     */
    public Person(String firstName, String lastName) {
        super(null);

        this.firstName = firstName;
        this.lastName = lastName;
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
}
