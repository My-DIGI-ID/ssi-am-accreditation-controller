package com.bka.ssi.controller.accreditation.company.domain.values;

public class Position {

    private String name;

    /* TODO - Values invariants checks to be added as part of functional stories */
    public Position(String name) {
        this.name = name;
    }

    public Position() {
        this.name = "No position specified";
    }

    public String getName() {
        return name;
    }
}
