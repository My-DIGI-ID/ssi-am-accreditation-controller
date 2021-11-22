package com.bka.ssi.controller.accreditation.company.application.services;

import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.AccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.AccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.PartyRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.enums.AccreditationStatus;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AccreditationService<T extends Accreditation<R, U>, R extends Party,
    U extends AccreditationStatus> {

    protected Logger logger;
    protected AccreditationRepository<T> accreditationRepository;
    protected AccreditationFactory<R, T> factory;
    protected PartyRepository<R> partyRepository;

    public AccreditationService(Logger logger,
        AccreditationRepository<T> accreditationRepository,
        AccreditationFactory<R, T> factory,
        PartyRepository<R> partyRepository) {
        this.logger = logger;
        this.accreditationRepository = accreditationRepository;
        this.factory = factory;
        this.partyRepository = partyRepository;
    }

    public List<T> getAllAccreditationsByPartyId(String id) throws Exception {
        return this.accreditationRepository.findAllByPartyId(id);
    }

    public T getAccreditationById(String id) throws Exception {
        T output = this.accreditationRepository.findById(id).orElseThrow(NotFoundException::new);
        return output;
    }

    public T getAccreditationById(String id, String userName) throws Exception {
        T output =
            this.accreditationRepository.findByIdAndInvitedBy(id, userName).orElseThrow(NotFoundException::new);
        return output;
    }

    public List<T> getAllAccreditations() throws Exception {
        List<T> output = new ArrayList<>();
        this.accreditationRepository.findAll().forEach(output::add);
        return output;
    }

    public List<T> getAllAccreditations(String userName) throws Exception {
        List<T> output = this.accreditationRepository.findAllByInvitedBy(userName);
        return output;
    }

    public List<T> getAllAccreditationsByStatus(U status)
        throws Exception {
        return this.accreditationRepository.findAllByStatus(status);
    }

    public long countAccreditationsByStatus(U status)
        throws Exception {
        return this.accreditationRepository.countByStatus(status);
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

    public byte[] generateAccreditationWithEmailAsMessage(String accreditationId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation initiateAccreditationWithEmailAsMessage is not yet implemented");
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
