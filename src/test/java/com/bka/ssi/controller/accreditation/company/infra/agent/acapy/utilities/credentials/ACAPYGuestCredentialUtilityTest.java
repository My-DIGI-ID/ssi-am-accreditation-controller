/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.credentials;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import io.github.my_digi_id.acapy_client.model.CredentialPreview;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

class ACAPYGuestCredentialUtilityTest {

    private final Logger logger = LoggerFactory.getLogger(ACAPYGuestCredentialUtilityTest.class);

    private ACAPYGuestCredentialUtility acapyGuestCredentialUtility;

    @BeforeEach
    void setUp() {
        acapyGuestCredentialUtility = new ACAPYGuestCredentialUtility(logger);
    }

    @Test
    void createCredentialPreview() throws InvalidValidityTimeframeException {
        GuestBuilder guestBuilder = new GuestBuilder();
        Guest guest = guestBuilder.buildGuest();

        GuestCredential guestCredential = guest.getCredentialOffer().getCredential();

        CredentialPreview credentialPreview =
            acapyGuestCredentialUtility.createCredentialPreview(guestCredential);

        Assertions.assertNotNull(credentialPreview.getAttributes());
        Assertions.assertEquals(21, credentialPreview.getAttributes().size());

        Map<String, String> attributes = new HashMap<>();

        credentialPreview.getAttributes().stream().forEach((attr) -> {
            attributes.put(attr.getName(), attr.getValue());
        });

        Assertions.assertEquals(guestCredential.getPersona().getFirstName(),
            attributes.get("firstName"));
        Assertions.assertEquals(guestCredential.getPersona().getLastName(),
            attributes.get("lastName"));
        Assertions.assertEquals(guestCredential.getPersona().getTitle(), attributes.get("title"));
        Assertions.assertEquals(guestCredential.getContactInformation().getEmails().get(0),
            attributes.get("email"));
        Assertions.assertEquals(guestCredential.getContactInformation().getPhoneNumbers().get(0),
            attributes.get("primaryPhoneNumber"));
        Assertions.assertEquals(guestCredential.getContactInformation().getPhoneNumbers().get(1),
            attributes.get("secondaryPhoneNumber"));
        Assertions.assertEquals(guestCredential.getCompanyName(), attributes.get("companyName"));
        Assertions.assertEquals(guestCredential.getTypeOfVisit(), attributes.get("typeOfVisit"));
        Assertions.assertEquals(guestCredential.getLocation(), attributes.get("location"));
        Assertions.assertEquals(guestCredential.getValidityTimeframe().getValidFrom().format(
            DateTimeFormatter.ISO_OFFSET_DATE_TIME), attributes.get("validFrom"));
        Assertions.assertEquals(guestCredential.getValidityTimeframe().getValidUntil()
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), attributes.get("validUntil"));
        Assertions.assertEquals(guestCredential.getInvitedBy(), attributes.get("invitedBy"));
        Assertions.assertEquals(guestCredential.getGuestPrivateInformation().getDateOfBirth(),
            attributes.get("dateOfBirth"));
        Assertions.assertEquals(
            guestCredential.getGuestPrivateInformation().getLicencePlateNumber(),
            attributes.get("licensePlateNumber"));
        Assertions.assertEquals(guestCredential.getGuestPrivateInformation().getCompanyCity(),
            attributes.get("companyCity"));
        Assertions.assertEquals(guestCredential.getGuestPrivateInformation().getCompanyStreet(),
            attributes.get("companyStreet"));
        Assertions.assertEquals(guestCredential.getGuestPrivateInformation().getCompanyPostCode(),
            attributes.get("companyPostCode"));
        Assertions.assertEquals(guestCredential.getReferenceBasisId(),
            attributes.get("referenceBasisId"));
        Assertions.assertEquals("", attributes.get("referenceEmployeeId"));
        Assertions.assertEquals("", attributes.get("companyProofOfOwnership"));
        Assertions.assertEquals("", attributes.get("dataEncryptionAlgorithm"));
    }
}