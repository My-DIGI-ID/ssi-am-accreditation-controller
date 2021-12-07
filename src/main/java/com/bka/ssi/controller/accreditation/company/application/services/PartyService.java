package com.bka.ssi.controller.accreditation.company.application.services;

import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidAccreditationStatusForPartyException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidPartyOperationException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.PartyFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.AccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.PartyRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.enums.DefaultAccreditationStatus;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public abstract class PartyService<T extends Party, R, U extends Accreditation<T, ?>> {

    protected Logger logger;
    protected PartyRepository<T> partyRepository;
    protected PartyFactory<R, T> factory;
    protected AccreditationRepository<U> accreditationRepository;

    public PartyService(Logger logger,
        PartyRepository<T> partyRepository,
        PartyFactory<R, T> factory,
        AccreditationRepository<U> accreditationRepository) {
        this.logger = logger;
        this.partyRepository = partyRepository;
        this.factory = factory;
        this.accreditationRepository = accreditationRepository;
    }

    public T createParty(R inputDto) throws Exception {
        T output = this.factory.create(inputDto);
        T savedOutput = this.partyRepository.save(output);
        return savedOutput;
    }

    public T createParty(R inputDto, String userName) throws Exception {
        T output = this.factory.create(inputDto, userName);
        T savedOutput = this.partyRepository.save(output);
        return savedOutput;
    }

    public List<T> createParty(MultipartFile file) throws Exception {
        List<T> output = this.factory.create(file);
        List<T> savedOutput = new ArrayList<>();
        this.partyRepository.saveAll(output).forEach(savedOutput::add);
        return savedOutput;
    }

    public List<T> createParty(MultipartFile file, String userName) throws Exception {
        List<T> output = this.factory.create(file, userName);
        List<T> savedOutput = new ArrayList<>();
        this.partyRepository.saveAll(output).forEach(savedOutput::add);
        return savedOutput;
    }

    public List<T> getAllParties() throws Exception {
        List<T> output = new ArrayList<>();
        this.partyRepository.findAll().forEach(output::add);
        return output;
    }

    public List<T> getAllParties(String userName) throws Exception {
        List<T> output = this.partyRepository.findAllByCreatedBy(userName);
        return output;
    }

    public T getPartyById(String id) throws Exception {
        T output =
            this.partyRepository.findById(id).orElseThrow(NotFoundException::new);
        return output;
    }

    public T getPartyById(String id, String userName) throws Exception {
        T output =
            this.partyRepository.findByIdAndCreatedBy(id, userName)
                .orElseThrow(NotFoundException::new);
        return output;
    }

    public void deleteParty(String id) throws Exception {
        T party = this.partyRepository.findById(id).orElseThrow(NotFoundException::new);

        List<U> accreditations = this.accreditationRepository.findAllByPartyId(id);

        if (!accreditations.isEmpty()) {
            throw new InvalidPartyOperationException();
        }

        this.partyRepository.deleteById(id);
    }

    public T updateParty(R inputDto, String partyId, String userName) throws Exception {
        T existingParty = this.partyRepository.findById(partyId)
            .orElseThrow(NotFoundException::new);

        List<U> accreditations = this.accreditationRepository.findAllByPartyId(partyId);
        for (Accreditation<T, ?> accreditation : accreditations) {
            if (accreditation.getStatus().getName()
                .equals(DefaultAccreditationStatus.ACCEPTED.getName())) {
                throw new InvalidAccreditationStatusForPartyException(partyId);
            }
        }

        T newPartyData = (T) this.factory.create(inputDto, userName);
        existingParty.updateWithNewPartyData(newPartyData);
        T savedOutput = this.partyRepository.save(existingParty);

        return savedOutput;
    }
}
