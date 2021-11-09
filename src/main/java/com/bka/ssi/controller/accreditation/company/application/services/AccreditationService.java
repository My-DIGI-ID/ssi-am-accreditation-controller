package com.bka.ssi.controller.accreditation.company.application.services;

import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.AccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.AccreditationRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.AccreditationStatus;
import org.slf4j.Logger;

import java.util.List;

public abstract class AccreditationService<T extends Accreditation> {

    protected Logger logger;
    protected AccreditationRepository<T> repository;
    protected AccreditationFactory factory;

    public AccreditationService(Logger logger,
        AccreditationRepository<T> repository,
        AccreditationFactory factory) {
        this.logger = logger;
        this.repository = repository;
        this.factory = factory;
    }

    public T getAccreditationById(String id) throws Exception {
        T output = this.repository.findById(id).orElseThrow(NotFoundException::new);
        return output;
    }

    public T getAccreditationById(String id, String userName) throws Exception {
        T output =
            this.repository.findByIdAndInvitedBy(id, userName).orElseThrow(NotFoundException::new);
        return output;
    }

    public List<T> getAllAccreditations() throws Exception {
        List<T> output = (List<T>) this.repository.findAll();
        return output;
    }

    public List<T> getAllAccreditations(String userName) throws Exception {
        List<T> output = (List<T>) this.repository.findAllByInvitedBy(userName);
        return output;
    }

    public <R extends AccreditationStatus> List<T> getAllAccreditationsByStatus(R status)
        throws Exception {
        return this.repository.findAllByStatus(status);
    }

    public <R extends AccreditationStatus> long countAccreditationsByStatus(R status)
        throws Exception {
        return this.repository.countByStatus(status);
    }

    public T initiateAccreditation(String partyId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation initiateAccreditation is not yet implemented");
    }

    public T initiateAccreditation(String partyId, String userName)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation initiateAccreditation is not yet implemented");
    }

    public T proceedWithAccreditation(String accreditationId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation proceedWithAccreditation is not yet implemented");
    }

    public T offerAccreditation(String accreditationId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation offerAccreditation is not yet implemented");
    }

    public T completeAccreditation(String connectionId, String credentialExchangeId,
        String issuedBy)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation completeAccreditationProcess is not yet implemented");
    }

    public boolean isAccreditationCompleted(String accreditationId) throws Exception {
        throw new UnsupportedOperationException(
            "Operation isAccreditationCompleted is not yet implemented");
    }

    public T revokeAccreditation(String accreditationId) throws Exception {
        throw new UnsupportedOperationException(
            "Operation revokeAccreditation is not yet implemented");
    }
}
