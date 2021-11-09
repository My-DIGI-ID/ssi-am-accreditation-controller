package com.bka.ssi.controller.accreditation.company.application.utilities;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.values.BasisIdPresentation;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class BasisIdPresentationUtility {

    private final Logger logger;
    private final FuzzyMatcher matcher;

    public BasisIdPresentationUtility(Logger logger, FuzzyMatcher matcher) {
        this.logger = logger;
        this.matcher = matcher;
    }

    public Boolean isPresentationAndGuestMatch(BasisIdPresentation presentation,
        Guest guest) {
        logger.debug(
            "Matching expected firstname and lastname with actual firstname and lastname of " +
                "guest with id {}", guest.getId());

        String firstNameExpected =
            guest.getCredentialOffer().getCredential().getPersona().getFirstName();
        String lastNameExpected =
            guest.getCredentialOffer().getCredential().getPersona().getLastName();

        String firstNameActual = presentation.getFirstName();
        String lastNameActual = presentation.getFamilyName();

        if (firstNameExpected == null || lastNameExpected == null ||
            firstNameActual == null || lastNameActual == null) {
            return false;
        }

        return firstNameActual.equals(firstNameExpected) && lastNameActual.equals(lastNameExpected);
    }

    public Boolean isPresentationAndGuestMatchLoosely(BasisIdPresentation presentation,
        Guest guest) {
        logger.debug(
            "Loosely matching expected firstname and lastname with actual firstname and lastname " +
                "of guest with id {}", guest.getId());

        String firstNameExpected =
            guest.getCredentialOffer().getCredential().getPersona().getFirstName();
        String lastNameExpected =
            guest.getCredentialOffer().getCredential().getPersona().getLastName();

        String firstNameActual = presentation.getFirstName();
        String lastNameActual = presentation.getFamilyName();

        if (firstNameExpected == null || lastNameExpected == null ||
            firstNameActual == null || lastNameActual == null) {
            return false;
        }

        String fullExpected = new String(
            (firstNameExpected + lastNameExpected)
                .toLowerCase()
                .strip()
                .replaceAll("\\s+", "")
                .getBytes(), StandardCharsets.UTF_8);
        String fullActual = new String(
            (firstNameActual + lastNameActual)
                .toLowerCase()
                .strip()
                .replaceAll("\\s+", "")
                .getBytes(), StandardCharsets.UTF_8);

        return this.matcher.isMatch(fullActual, fullExpected);
    }
}
