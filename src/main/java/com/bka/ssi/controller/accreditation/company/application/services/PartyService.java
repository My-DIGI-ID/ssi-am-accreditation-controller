package com.bka.ssi.controller.accreditation.company.application.services;

import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.factories.Factory;
import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public abstract class PartyService<T extends Party> {

    private CrudRepository<T, String> crudRepository;
    private Factory factory;

    public void setCrudRepository(
        CrudRepository<T, String> crudRepository) {
        this.crudRepository = crudRepository;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public <R> T createParty(R inputDto) throws Exception {
        T output = (T) this.factory.create(inputDto);
        T savedOutput = this.crudRepository.save(output);
        return savedOutput;
    }

    public List<T> createParty(MultipartFile file) throws Exception {
        List<T> output = this.factory.create(file);
        List<T> savedOutput = (List<T>) this.crudRepository.saveAll(output);
        return savedOutput;
    }

    public List<T> getAllParties() throws Exception {
        List<T> output = (List<T>) this.crudRepository.findAll();
        return output;
    }

    public Optional<T> getPartyById(String id) throws Exception {
        /* ToDo - current implementation finds party by mongodb _id. What are the criteria on
            which to find a party, e.g. for employee it might be employeeId */
        /* ToDo - throw NotFound exception here? */
        Optional<T> output = (Optional<T>) this.crudRepository.findById(id);
        return output;
    }

    public <R> T updateParty(R inputDto, String partyId) throws Exception {
        /* TODO - after MVP, restrict free party updates in relation to accreditation status */

        T existingParty = this.crudRepository.findById(partyId)
            .orElseThrow(() -> new NotFoundException());

        T newPartyData = (T) this.factory.create(inputDto);
        existingParty.updateWithNewPartyData(newPartyData);
        T savedOutput = this.crudRepository.save(existingParty);

        return savedOutput;
    }

    public void deleteParty(String id) throws Exception {
        this.crudRepository.deleteById(id);
    }
}
