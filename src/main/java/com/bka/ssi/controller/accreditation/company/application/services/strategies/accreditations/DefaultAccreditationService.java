package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;

import com.bka.ssi.controller.accreditation.company.application.services.AccreditationService;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.DefaultAccreditation;
import org.springframework.stereotype.Service;

@Service
public class DefaultAccreditationService<T extends Party>
    extends AccreditationService<DefaultAccreditation<T>> {

    /* Remove if Employee AccreditationService and GuestAccreditationService are in-place */
    /*
    public DefaultAccreditationService(
        CrudRepository<DefaultAccreditation<T>, String> crudRepository) {
        this.setCrudRepository(crudRepository);
    }*/


}

