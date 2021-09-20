package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties;

import com.bka.ssi.controller.accreditation.company.application.repositories.parties.GuestRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.GuestMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties.GuestMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.parties.GuestMongoDbRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuestMongoDbFacade implements GuestRepository {

    private final GuestMongoDbRepository guestMongoDbRepository;
    private final GuestMongoDbMapper guestMongoDbMapper;

    public GuestMongoDbFacade(GuestMongoDbRepository guestMongoDbRepository,
        GuestMongoDbMapper guestMongoDbMapper) {
        this.guestMongoDbRepository = guestMongoDbRepository;
        this.guestMongoDbMapper = guestMongoDbMapper;
    }


    @Override
    public Guest save(Guest guest) {
        GuestMongoDbDocument guestMongoDBDocument =
            guestMongoDbMapper.guestToGuestMongoDBDocument(guest);
        GuestMongoDbDocument savedGuestMongoDBDocument =
            guestMongoDbRepository.save(guestMongoDBDocument);

        Guest savedGuest =
            guestMongoDbMapper.guestMongoDBDocumentToGuest(savedGuestMongoDBDocument);

        return savedGuest;
    }

    @Override
    public <S extends Guest> Iterable<S> saveAll(Iterable<S> iterable) {
        List<GuestMongoDbDocument> guestMongoDbDocuments = new ArrayList<>();
        iterable.forEach(guest -> guestMongoDbDocuments
            .add(this.guestMongoDbMapper
                .guestToGuestMongoDBDocument(guest)));

        Iterable<GuestMongoDbDocument> savedGuestMongoDbDocuments =
            this.guestMongoDbRepository.saveAll(guestMongoDbDocuments);

        /* ToDo - List<S> looks weird, should actually be List<Guest> */
        List<S> savedGuests = new ArrayList<>();
        savedGuestMongoDbDocuments
            .forEach(
                savedEmployeeMongoDbDocument -> savedGuests.add((S) this.guestMongoDbMapper
                    .guestMongoDBDocumentToGuest(savedEmployeeMongoDbDocument)));

        return savedGuests;
    }

    @Override
    public Optional<Guest> findById(String id) {
        Optional<GuestMongoDbDocument> guestMongoDbDocument =
            this.guestMongoDbRepository.findById(id);

        Guest guest =
            this.guestMongoDbMapper
                .guestMongoDBDocumentToGuest(guestMongoDbDocument.get());
        
        return Optional.of(guest);
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException(
            "Operation existsById is not yet implemented");
    }

    @Override
    public Iterable<Guest> findAll() {
        Iterable<GuestMongoDbDocument> guestMongoDbDocuments =
            this.guestMongoDbRepository.findAll();
        List<Guest> guests = new ArrayList<>();

        guestMongoDbDocuments.forEach(guestMongoDbDocument -> guests
            .add(this.guestMongoDbMapper
                .guestMongoDBDocumentToGuest(guestMongoDbDocument)));

        return guests;
    }

    @Override
    public Iterable<Guest> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException(
            "Operation findAllById is not yet implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException(
            "Operation count is not yet implemented");
    }

    @Override
    public void deleteById(String id) {
        this.guestMongoDbRepository.deleteById(id);
    }

    @Override
    public void delete(Guest guest) {
        throw new UnsupportedOperationException(
            "Operation delete is not yet implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> iterable) {
        throw new UnsupportedOperationException(
            "Operation deleteAllById is not yet implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Guest> iterable) {
        throw new UnsupportedOperationException(
            "Operation deleteAll is not yet implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException(
            "Operation deleteAll is not yet implemented");
    }


}
