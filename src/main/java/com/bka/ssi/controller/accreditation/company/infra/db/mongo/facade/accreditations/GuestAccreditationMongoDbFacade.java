package com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.accreditations;

import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.GuestAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.accreditations.GuestAccreditationMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.parties.GuestMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.accreditations.GuestAccreditationMongoDbMapper;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.repositories.accreditations.GuestAccreditationMongoDbRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Repository("guestAccreditationMongoDbFacade")
public class GuestAccreditationMongoDbFacade implements GuestAccreditationRepository {

    private final GuestAccreditationMongoDbRepository guestAccreditationMongoDbRepository;
    private final GuestAccreditationMongoDbMapper guestAccreditationMongoDbMapper;
    private final GuestMongoDbFacade guestMongoDbFacade;
    private Logger logger;

    public GuestAccreditationMongoDbFacade(
        GuestAccreditationMongoDbRepository guestAccreditationMongoDbRepository,
        GuestAccreditationMongoDbMapper guestAccreditationMongoDbMapper,
        GuestMongoDbFacade guestMongoDbFacade, Logger logger) {
        this.guestAccreditationMongoDbRepository = guestAccreditationMongoDbRepository;
        this.guestAccreditationMongoDbMapper = guestAccreditationMongoDbMapper;
        this.guestMongoDbFacade = guestMongoDbFacade;
        this.logger = logger;
    }

    @Override
    public GuestAccreditation save(GuestAccreditation guestAccreditation) {
        GuestAccreditationMongoDbDocument guestAccreditationMongoDBDocument =
            guestAccreditationMongoDbMapper
                .guestAccreditationToGuestAccreditationMongoDBDocument(guestAccreditation);
        GuestAccreditationMongoDbDocument savedGuestAccreditationMongoDBDocument =
            guestAccreditationMongoDbRepository.save(guestAccreditationMongoDBDocument);

        GuestAccreditation savedGuestAccreditation =
            guestAccreditationMongoDbMapper
                .guestAccreditationMongoDBDocumentToGuestAccreditation(
                    savedGuestAccreditationMongoDBDocument);

        /* TODO - add proper error and transaction handling */
        Guest guest = guestMongoDbFacade.save(guestAccreditation.getParty());

        GuestAccreditation updatedGuestAccreditation = new GuestAccreditation(
            savedGuestAccreditation.getId(),
            guest,
            savedGuestAccreditation.getStatus(),
            savedGuestAccreditation.getInvitationLink(),
            savedGuestAccreditation.getInvitationEmail()
        );

        return updatedGuestAccreditation;
    }

    //WARNING Tech Debt!! ToDo: saveWithParty(accreditation, party){}

    @Override
    public <S extends GuestAccreditation> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException(
            "Operation saveAll is not yet implemented");
    }

    @Override
    public Optional<GuestAccreditation> findById(String id) {
        logger.debug("Fetching Guest Accreditation with id " + id + " from MongoDB");
        Optional<GuestAccreditationMongoDbDocument> guestAccreditationMongoDbDocument =
            this.guestAccreditationMongoDbRepository.findById(id);

        GuestAccreditation guestAccreditation =
            this.guestAccreditationMongoDbMapper
                .guestAccreditationMongoDBDocumentToGuestAccreditation(
                    guestAccreditationMongoDbDocument.get());

        return Optional.of(guestAccreditation);
    }

    public Optional<GuestAccreditation> findByConnectionIdAndThreadId(String correlationId,
        String threadId) {

        Iterator<GuestAccreditation> iterator = this.findAll().iterator();
        while (iterator.hasNext()) {
            GuestAccreditation accreditation = iterator.next();

            if (accreditation.getBasisIdVerificationCorrelation().getConnectionId()
                .equals(correlationId) &&
                accreditation.getBasisIdVerificationCorrelation().getThreadId()
                    .equals(threadId)) {

                return Optional.of(accreditation);
            }
        }
        return null;
    }

    @Override
    public Optional<GuestAccreditation> findByIssuanceConnectionIdAndThreadId(String correlationId,
        String threadId) {
        Iterator<GuestAccreditation> iterator = this.findAll().iterator();
        while (iterator.hasNext()) {
            GuestAccreditation accreditation = iterator.next();

            if (correlationId.equals(accreditation.getGuestCredentialIssuanceCorrelation().getConnectionId())) {

                return Optional.of(accreditation);
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<GuestAccreditation> findByVerificationQueryParameters(
        String referenceBasisId, String firstName, String lastName, String dateOfBirth,
        String companyName, Date validFromDate, Date validUntilDate, String invitedBy)
        throws NotFoundException {

        Iterator<GuestAccreditation> iterator = this.findAll().iterator();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (!iterator.hasNext()) {
            throw new NotFoundException();
        }

        while (iterator.hasNext()) {
            GuestAccreditation accreditation = iterator.next();

            boolean isReferenceEqual =
                referenceBasisId.equals(
                    accreditation.getParty().getCredentialOffer().getCredential()
                        .getReferenceBasisId());

            boolean isFirstNameEqual =
                firstName.equals(
                    accreditation.getParty().getCredentialOffer().getCredential().getPersona()
                        .getFirstName());

            boolean isLastNameEqual =
                lastName.equals(
                    accreditation.getParty().getCredentialOffer().getCredential().getPersona()
                        .getLastName());

            boolean isDateOfBirthEqual =
                dateOfBirth.equals(accreditation.getParty().getCredentialOffer().getCredential()
                    .getGuestPrivateInformation()
                    .getDateOfBirth());

            boolean isCompanyNameEqual =
                companyName.equals(
                    accreditation.getParty().getCredentialOffer().getCredential().getCompanyName());

            boolean isValidFromDateEqual =
                format.format(validFromDate).equals(format.format(accreditation.getParty().getCredentialOffer().getCredential()
                    .getValidityTimeframe()
                    .getValidFromDate()));

            boolean isValidUntilDateEqual =
                format.format(validUntilDate).equals(format.format(accreditation.getParty().getCredentialOffer().getCredential()
                    .getValidityTimeframe()
                    .getValidUntilDate()));

            boolean isInvitedByEqual =
                invitedBy.equals(
                    accreditation.getParty().getCredentialOffer().getCredential().getInvitedBy());

            if (
                isReferenceEqual
                    && isFirstNameEqual
                    && isLastNameEqual
                    && isDateOfBirthEqual
                    && isCompanyNameEqual
                    && isValidFromDateEqual
                    && isValidUntilDateEqual
                    && isInvitedByEqual
            ) {
                return Optional.of(accreditation);
            }
        }

        return null;
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException(
            "Operation existsById is not yet implemented");
    }

    @Override
    public Iterable<GuestAccreditation> findAll() {
        Iterable<GuestAccreditationMongoDbDocument> guestAccreditationMongoDbDocuments =
            this.guestAccreditationMongoDbRepository.findAll();
        List<GuestAccreditation> guestAccreditations = new ArrayList<>();

        guestAccreditationMongoDbDocuments.forEach(document -> guestAccreditations
            .add(this.guestAccreditationMongoDbMapper
                .guestAccreditationMongoDBDocumentToGuestAccreditation(document)));

        return guestAccreditations;
    }

    @Override
    public Iterable<GuestAccreditation> findAllById(Iterable<String> iterable) {
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
        throw new UnsupportedOperationException(
            "Operation deleteById is not yet implemented");
    }

    @Override
    public void delete(GuestAccreditation guestAccreditation) {
        throw new UnsupportedOperationException(
            "Operation delete is not yet implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> iterable) {
        throw new UnsupportedOperationException(
            "Operation deleteAllById is not yet implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends GuestAccreditation> iterable) {
        throw new UnsupportedOperationException(
            "Operation deleteAll is not yet implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException(
            "Operation deleteAll is not yet implemented");
    }
}
