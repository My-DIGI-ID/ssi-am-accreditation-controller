package com.bka.ssi.controller.accreditation.company.domain.exceptions;

public class UpdatingPartyWithoutIdentityException extends Exception {

    public UpdatingPartyWithoutIdentityException() {
        super("You are trying to update a party that has no identity (id == null)");
    }
}
