package com.bka.ssi.controller.accreditation.company.domain.values;

public class Persona {

    private String title;
    private String firstName;
    private String lastName;

    public Persona(String title, String firstName, String lastName) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Persona(String firstName, String lastName) {
        this.title = "No title";
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
