package com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common;

public abstract class Entity {

    protected String id;

    public Entity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
