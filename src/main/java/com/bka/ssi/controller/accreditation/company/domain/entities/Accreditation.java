package com.bka.ssi.controller.accreditation.company.domain.entities;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common.Entity;
import com.bka.ssi.controller.accreditation.company.domain.entities.enums.AccreditationStatus;

public abstract class Accreditation<T extends Party, R extends AccreditationStatus>
    extends Entity {

    private T party;
    private R status;

    public Accreditation(String id, T party, R status) {
        super(id);

        this.party = party;
        this.status = status;
    }

    public Accreditation(T party, R status) {
        super(null);

        this.party = party;
        this.status = status;
    }

    public T getParty() {
        return this.party;
    }

    public R getStatus() {
        return this.status;
    }

    public void setStatus(R status) {
        this.status = status;
    }
}
