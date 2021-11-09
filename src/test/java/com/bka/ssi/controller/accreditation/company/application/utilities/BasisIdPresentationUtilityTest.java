package com.bka.ssi.controller.accreditation.company.application.utilities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidGuestValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.BasisIdPresentation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

public class BasisIdPresentationUtilityTest {

    private final static Logger logger = LoggerFactory.getLogger(FuzzyMatcherTest.class);
    private static FuzzyMatcher matcher;
    private static BasisIdPresentationUtility basisIdPresentationUtility;

    @BeforeAll
    static void init() {
        matcher = new FuzzyMatcher(100, 10, logger);
        basisIdPresentationUtility = new BasisIdPresentationUtility(logger, matcher);
    }

    @Test
    void isMatch() throws InvalidGuestValidityTimeframeException {
        Persona persona = new Persona(
            "Firstname",
            "Lastname"
        );

        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(ZonedDateTime.now(), ZonedDateTime.now());

        GuestCredential guestCredential = new GuestCredential(
            validityTimeframe,
            persona,
            null,
            null,
            null,
            null,
            null
        );

        CredentialOffer<GuestCredential> guestCredentialOffer = new CredentialOffer(
            null,
            guestCredential
        );

        Guest guest = new Guest(guestCredentialOffer, null, ZonedDateTime.now());

        BasisIdPresentation basisIdPresentation = new BasisIdPresentation("Firstname",
            "Lastname", null);

        assertTrue(basisIdPresentationUtility.isPresentationAndGuestMatch(basisIdPresentation,
            guest));
    }

    @Test
    void isNotMatch() throws InvalidGuestValidityTimeframeException {
        Persona persona = new Persona(
            "firstname",
            "lastname"
        );

        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(ZonedDateTime.now(), ZonedDateTime.now());

        GuestCredential guestCredential = new GuestCredential(
            validityTimeframe,
            persona,
            null,
            null,
            null,
            null,
            null
        );

        CredentialOffer<GuestCredential> guestCredentialOffer = new CredentialOffer(
            null,
            guestCredential
        );

        Guest guest = new Guest(guestCredentialOffer, null, ZonedDateTime.now());

        BasisIdPresentation basisIdPresentation = new BasisIdPresentation("Firstname",
            "Lastname", null);

        assertFalse(basisIdPresentationUtility.isPresentationAndGuestMatch(basisIdPresentation,
            guest));
    }

    @Test
    void isMatchLoosely() throws InvalidGuestValidityTimeframeException {
        Persona persona = new Persona(
            "firstnäme ",
            " last  nameee"
        );

        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(ZonedDateTime.now(), ZonedDateTime.now());

        GuestCredential guestCredential = new GuestCredential(
            validityTimeframe,
            persona,
            null,
            null,
            null,
            null,
            null
        );

        CredentialOffer<GuestCredential> guestCredentialOffer = new CredentialOffer(
            null,
            guestCredential
        );

        Guest guest = new Guest(guestCredentialOffer, null, ZonedDateTime.now());

        BasisIdPresentation basisIdPresentation = new BasisIdPresentation("Firstname",
            "Lastname", null);

        assertTrue(
            basisIdPresentationUtility.isPresentationAndGuestMatchLoosely(basisIdPresentation,
                guest));
    }

    @Test
    void isNotMatchLoosely() throws InvalidGuestValidityTimeframeException {
        Persona persona = new Persona(
            "ä ö ü i asdfqwertz",
            "lastname"
        );

        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(ZonedDateTime.now(), ZonedDateTime.now());

        GuestCredential guestCredential = new GuestCredential(
            validityTimeframe,
            persona,
            null,
            null,
            null,
            null,
            null
        );

        CredentialOffer<GuestCredential> guestCredentialOffer = new CredentialOffer(
            null,
            guestCredential
        );

        Guest guest = new Guest(guestCredentialOffer, null, ZonedDateTime.now());

        BasisIdPresentation basisIdPresentation = new BasisIdPresentation("Firstname",
            "Lastname", null);

        assertFalse(
            basisIdPresentationUtility.isPresentationAndGuestMatchLoosely(basisIdPresentation,
                guest));
    }
}
