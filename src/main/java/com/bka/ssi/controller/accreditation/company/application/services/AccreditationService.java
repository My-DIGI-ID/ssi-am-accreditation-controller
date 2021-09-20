package com.bka.ssi.controller.accreditation.company.application.services;

import com.bka.ssi.controller.accreditation.company.application.factories.Factory;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.common.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class AccreditationService<T extends Accreditation> {

    @Autowired
    protected Logger logger;

    protected CrudRepository<T, String> crudRepository;
    protected Factory factory;

    public void setCrudRepository(
        CrudRepository<T, String> crudRepository) {
        this.crudRepository = crudRepository;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public Factory getFactory() {
        return this.factory;
    }

    public CrudRepository<T, String> getCrudRepository() {
        return this.crudRepository;
    }


    public Optional<T> getAccreditationById(String id) throws Exception {
        Optional<T> output = this.crudRepository.findById(id);
        return output;
    }

    public T initiateAccreditationWithPlainUrl(String partyId)
        throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
            "initiateAccreditation not implemented");
    }

    public T proceedAccreditationWithQrCode(String accreditationId)
        throws UnsupportedOperationException, Exception {
        // maybe rename to initiateAccreditationWithQR since in guest accreditation it is after
        // email initiation but in general it can be just a plain QR code
        throw new UnsupportedOperationException("proceedAccreditationWithQrCode not implemented");
    }

    public void offerAccreditation(String accreditationId)
        throws UnsupportedOperationException, Exception {
        throw new UnsupportedOperationException("offerAccreditation not implemented");
    }

    public void completeAccreditationProcess(ACAPYIssueCredentialDto acapyIssueCredentialDto)
        throws UnsupportedOperationException, Exception {
        throw new UnsupportedOperationException("completeAccreditationProcess not implemented");
    }
}
