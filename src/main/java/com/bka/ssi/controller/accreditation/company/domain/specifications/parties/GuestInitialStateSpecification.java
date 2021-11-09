package com.bka.ssi.controller.accreditation.company.domain.specifications.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;

public class GuestInitialStateSpecification implements Specification<Guest> {

    public Boolean isSatisfiedBy(Guest guest) {
        return (
            guest.getCredentialOffer() != null &&
                !guest.getCredentialOffer().getCredential().getPersona().getFirstName().isEmpty() &&
                !guest.getCredentialOffer().getCredential().getPersona().getLastName().isEmpty()
        );
    }
}
