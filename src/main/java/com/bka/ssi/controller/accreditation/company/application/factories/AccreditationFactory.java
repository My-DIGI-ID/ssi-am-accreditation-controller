package com.bka.ssi.controller.accreditation.company.application.factories;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;

public interface AccreditationFactory<T extends Party, R extends Accreditation<T, ?>> {

    default public R create(T input) throws Exception {
        throw new UnsupportedOperationException();
    }

    default public R create(T input, String userName) throws Exception {
        throw new UnsupportedOperationException();
    }
}
