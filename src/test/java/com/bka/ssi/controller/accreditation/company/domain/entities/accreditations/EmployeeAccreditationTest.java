package com.bka.ssi.controller.accreditation.company.domain.entities.accreditations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidAccreditationInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

public class EmployeeAccreditationTest {

    private static final String URL = "url";
    private static final String QRCODE = "qrcode";
    private static final String EMAIL = "email";
    private static final String INVITEDBY = "unittest";
    private ZonedDateTime invitedAt;
    private Employee party;

    @BeforeEach
    void setup() {
        EmployeeBuilder builder = new EmployeeBuilder();
        invitedAt = ZonedDateTime.now();
        this.party = builder.buildEmployee();
    }


    @Test
    public void shouldCreateAccreditationWithId() {
        EmployeeAccreditation employeeAccreditation = new EmployeeAccreditation("id", party,
            EmployeeAccreditationStatus.OPEN, INVITEDBY, invitedAt);
        assertNotEquals(null, employeeAccreditation);
    }

    @Test
    public void shouldCreateAccreditationWithOutId() {
        EmployeeAccreditation employeeAccreditation = new EmployeeAccreditation(party,
            EmployeeAccreditationStatus.OPEN, INVITEDBY, invitedAt);
        assertNotEquals(null, employeeAccreditation);
    }

    @Test
    public void shouldCreateAccreditationWithIdQrEmailUrl() {
        EmployeeAccreditation employeeAccreditation = new EmployeeAccreditation("id", party,
            EmployeeAccreditationStatus.OPEN, INVITEDBY, invitedAt, URL, EMAIL, QRCODE);
        assertNotEquals(null, employeeAccreditation);
    }

    @Test
    public void shouldCreateAccreditationWithCorrelation() {
        Correlation correlation = new Correlation();
        EmployeeAccreditation employeeAccreditation = new EmployeeAccreditation("id", party,
            EmployeeAccreditationStatus.OPEN, INVITEDBY, invitedAt, URL, EMAIL, QRCODE,
            correlation);
        assertNotEquals(null, employeeAccreditation);
    }


    @Test
    public void shouldInitiateAccreditation() throws InvalidAccreditationInitialStateException {
        // Given accreditation
        EmployeeAccreditation employeeAccreditation = new EmployeeAccreditation(party,
            EmployeeAccreditationStatus.OPEN, INVITEDBY, invitedAt);

        //when
        employeeAccreditation
            .initiateAccreditation(URL, EMAIL, QRCODE, UUID.randomUUID().toString());

        //then
        assertNotEquals(null, employeeAccreditation);
        assertEquals(employeeAccreditation.getInvitationEmail(), EMAIL);
        assertEquals(employeeAccreditation.getInvitationQrCode(), QRCODE);
        assertEquals(employeeAccreditation.getInvitationUrl(), URL);
        assertEquals(employeeAccreditation.getInvitedAt(), invitedAt);
        assertEquals(employeeAccreditation.getInvitedBy(), INVITEDBY);
    }

    @Test
    public void shouldUpdateStatusOnCredentialOffer() {
        // Given accreditation with status OPEN
        EmployeeAccreditation employeeAccreditation = new EmployeeAccreditation(party,
            EmployeeAccreditationStatus.OPEN, INVITEDBY, invitedAt);

        // when offered credential
        employeeAccreditation.offerAccreditation(new Correlation());

        // then
        assertEquals(EmployeeAccreditationStatus.PENDING, employeeAccreditation.getStatus());
    }

    @Test
    public void shouldThrowInvalidAccreditationStateExceptionWhenPartyIsNull() {
        EmployeeAccreditation employeeAccreditation = new EmployeeAccreditation(null,
            EmployeeAccreditationStatus.OPEN, INVITEDBY, invitedAt);

        assertThrows(InvalidAccreditationInitialStateException.class, () -> {
            employeeAccreditation
                .initiateAccreditation(URL, EMAIL, QRCODE, UUID.randomUUID().toString());
        });
    }

}
