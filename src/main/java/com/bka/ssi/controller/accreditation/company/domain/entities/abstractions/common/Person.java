package com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common;

public abstract class Person extends Entity {

    private String title;
    private String firstName;
    private String lastName;

    /* ToDo - How to use in inheritance chain? Remove if Persona class suffice */
    public Person(String id, String title, String firstName, String lastName) {
        super(id);

        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String id, String firstName, String lastName) {
        super(id);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName) {
        super(null);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
