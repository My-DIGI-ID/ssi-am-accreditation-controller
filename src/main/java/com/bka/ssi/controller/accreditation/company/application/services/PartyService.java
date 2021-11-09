package com.bka.ssi.controller.accreditation.company.application.services;

import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.PartyFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.PartyRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class PartyService<T extends Party> {

    protected Logger logger;
    protected PartyRepository<T> repository;
    protected PartyFactory factory;

    public PartyService(Logger logger,
        PartyRepository<T> repository,
        PartyFactory factory) {
        this.logger = logger;
        this.repository = repository;
        this.factory = factory;
    }

    public <R> T createParty(R inputDto) throws Exception {
        T output = (T) this.factory.create(inputDto);
        T savedOutput = this.repository.save(output);
        return savedOutput;
    }

    public <R> T createParty(R inputDto, String userName) throws Exception {
        T output = (T) this.factory.create(inputDto, userName);
        T savedOutput = this.repository.save(output);
        return savedOutput;
    }

    public List<T> createParty(MultipartFile file) throws Exception {
        List<T> output = this.factory.create(file);
        List<T> savedOutput = (List<T>) this.repository.saveAll(output);
        return savedOutput;
    }

    public List<T> createParty(MultipartFile file, String userName) throws Exception {
        List<T> output = this.factory.create(file, userName);
        List<T> savedOutput = (List<T>) this.repository.saveAll(output);
        return savedOutput;
    }

    public List<T> getAllParties() throws Exception {
        List<T> output = (List<T>) this.repository.findAll();
        return output;
    }

    public List<T> getAllParties(String userName) throws Exception {
        List<T> output = (List<T>) this.repository.findAllByCreatedBy(userName);
        return output;
    }

    public T getPartyById(String id) throws Exception {
        T output =
            this.repository.findById(id).orElseThrow(NotFoundException::new);
        return output;
    }

    public T getPartyById(String id, String userName) throws Exception {
        // ToDo - specify what kind of NotFoundException
        T output =
            this.repository.findByIdAndCreatedBy(id, userName).orElseThrow(NotFoundException::new);
        return output;
    }

    public <R> T updateParty(R inputDto, String partyId, String userName) throws Exception {
        /* TODO - after MVP, restrict free party updates in relation to accreditation status */

        T existingParty = this.repository.findById(partyId)
            .orElseThrow(NotFoundException::new);

        T newPartyData = (T) this.factory.create(inputDto, userName);
        existingParty.updateWithNewPartyData(newPartyData);
        T savedOutput = this.repository.save(existingParty);

        return savedOutput;
    }

    public void deleteParty(String id) throws Exception {
        this.repository.deleteById(id);
    }
}
