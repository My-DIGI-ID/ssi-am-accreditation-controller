package com.bka.ssi.controller.accreditation.company.application.repositories.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import org.springframework.data.repository.CrudRepository;

public interface GuestRepository extends CrudRepository<Guest, String> {
}
