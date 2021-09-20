package com.bka.ssi.controller.accreditation.company.domain.entities.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.entities.enums.DefaultAccreditationStatus;

public class DefaultAccreditation<T extends Party>
    extends Accreditation<T, DefaultAccreditationStatus> {

    public DefaultAccreditation(String id,
        T party,
        DefaultAccreditationStatus status) {
        super(id, party, status);
    }

    public DefaultAccreditation(T party,
        DefaultAccreditationStatus status) {
        super(party, status);
    }
}
