package com.bka.ssi.controller.accreditation.company.application.security.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.NoSuchElementException;

@SpringBootTest
@ActiveProfiles("integrationtest")
@TestPropertySource("classpath:application-integrationtest.properties")
public class GuestAccessTokenRepositoryIntegrationTest {

    @Autowired
    @Qualifier("guestAccessTokenMongoDbFacade")
    private GuestAccessTokenRepository guestAccessTokenRepository;

    @BeforeEach
    public void setUp() {
        GuestToken guestToken = new GuestToken("123", "456", null);

        GuestToken savedGuestToken = this.guestAccessTokenRepository.save(guestToken);

        assertEquals(guestToken.getId(), savedGuestToken.getId());
    }

    @Test
    public void shouldDeleteGuestTokenByAccreditationId() {
        this.guestAccessTokenRepository.deleteByAccreditationId("456");

        assertThrows(NoSuchElementException.class, () ->
        {
            this.guestAccessTokenRepository.findById("123");
        });
    }
}
