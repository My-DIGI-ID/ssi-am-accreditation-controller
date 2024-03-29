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

package com.bka.ssi.controller.accreditation.company.infra.agent.acapy;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.CredentialsConfiguration;
import com.bka.ssi.controller.accreditation.company.application.agent.ACAPYClient;
import com.bka.ssi.controller.accreditation.company.application.utilities.NonceGenerator;
import com.bka.ssi.controller.accreditation.company.domain.values.BasisIdPresentation;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions.ACAPYConnectionInvitationException;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions.ACAPYIssueCredentialException;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions.ACAPYRevocationCorrelationException;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions.ACAPYRevokeCredentialException;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions.ACAPYVerificationException;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions.ACAPYVerificationPresentationException;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.ACAPYCredentialFactory;
import io.github.my_digi_id.acapy_client.api.ConnectionApi;
import io.github.my_digi_id.acapy_client.api.IssueCredentialV10Api;
import io.github.my_digi_id.acapy_client.api.PresentProofV10Api;
import io.github.my_digi_id.acapy_client.api.RevocationApi;
import io.github.my_digi_id.acapy_client.invoker.ApiException;
import io.github.my_digi_id.acapy_client.model.CredentialPreview;
import io.github.my_digi_id.acapy_client.model.IndyProofReqAttrSpec;
import io.github.my_digi_id.acapy_client.model.IndyProofRequest;
import io.github.my_digi_id.acapy_client.model.IndyProofRequestNonRevoked;
import io.github.my_digi_id.acapy_client.model.InvitationResult;
import io.github.my_digi_id.acapy_client.model.RawEncoded;
import io.github.my_digi_id.acapy_client.model.RevokeRequest;
import io.github.my_digi_id.acapy_client.model.V10CredentialExchange;
import io.github.my_digi_id.acapy_client.model.V10CredentialFreeOfferRequest;
import io.github.my_digi_id.acapy_client.model.V10PresentationExchange;
import io.github.my_digi_id.acapy_client.model.V10PresentationSendRequestRequest;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The type Acapy client v 6.
 */
@Service
public class ACAPYClientV6 implements ACAPYClient {

    private final Logger logger;

    private final ACAPYConfiguration acapyConfiguration;
    private final CredentialsConfiguration credentialsConfiguration;

    private final ConnectionApi connectionApi;
    private final IssueCredentialV10Api issueCredentialApi;
    private final PresentProofV10Api presentProofV10Api;
    private final RevocationApi revocationApi;

    private final ACAPYCredentialFactory acapyCredentialFactory;

    /**
     * Instantiates a new Acapy client v 6.
     *
     * @param logger                   the logger
     * @param acapyConfiguration       the acapy configuration
     * @param credentialsConfiguration the credentials configuration
     * @param acapyCredentialFactory   the acapy credential factory
     */
    public ACAPYClientV6(Logger logger, ACAPYConfiguration acapyConfiguration,
        CredentialsConfiguration credentialsConfiguration,
        ACAPYCredentialFactory acapyCredentialFactory) {
        this.logger = logger;
        this.credentialsConfiguration = credentialsConfiguration;
        this.acapyConfiguration = acapyConfiguration;

        this.presentProofV10Api = new PresentProofV10Api(this.acapyConfiguration.getApiClient());
        this.connectionApi = new ConnectionApi(this.acapyConfiguration.getApiClient());
        this.issueCredentialApi = new IssueCredentialV10Api(this.acapyConfiguration.getApiClient());
        this.revocationApi = new RevocationApi(this.acapyConfiguration.getApiClient());

        this.acapyCredentialFactory = acapyCredentialFactory;
    }

    @Override
    public ConnectionInvitation createConnectionInvitation(String accreditationId)
        throws ACAPYConnectionInvitationException {
        try {
            InvitationResult invitationResult = connectionApi.connectionsCreateInvitationPost(
                accreditationId,
                null,
                null,
                null,
                null
            );

            return new ConnectionInvitation(
                invitationResult.getInvitationUrl(),
                invitationResult.getConnectionId()
            );
        } catch (ApiException e) {
            logger.error("ACAPY createConnectionsInvitationUrl native message: " + e.getMessage());
            throw new ACAPYConnectionInvitationException();
        }
    }

    @Override
    public Correlation verifyBasisId(String connectionId) throws ACAPYVerificationException {
        String nonce = String.valueOf(NonceGenerator.nextPositiveInt());

        logger.debug("Building basisId proof request attributes");
        Map<String, String> restrictionItem = new HashMap<>();
        restrictionItem
            .put("cred_def_id", this.credentialsConfiguration
                .getBasisIdCredentialDefinitionId());
        // restriction implicitly includes restriction on schema_id and issuer_did
        //restrictionItem.put("schema_id",this.credentialsConfiguration.getBasisIdCredentialSchemaId());

        IndyProofReqAttrSpec indyProofReqAttrSpec = new IndyProofReqAttrSpec();

        // TODO - configurable specification?
        indyProofReqAttrSpec.addNamesItem("firstName");
        indyProofReqAttrSpec.addNamesItem("familyName");
        indyProofReqAttrSpec.addNamesItem("dateOfBirth");
        indyProofReqAttrSpec.addRestrictionsItem(restrictionItem);

        Map<String, IndyProofReqAttrSpec> requestedAttributes = new HashMap<>();
        requestedAttributes.put("basisid", indyProofReqAttrSpec);

        IndyProofRequestNonRevoked indyProofRequestNonRevoked = new IndyProofRequestNonRevoked();
        indyProofRequestNonRevoked.from(0);
        indyProofRequestNonRevoked.to((int) Instant.now().getEpochSecond());

        // TODO - factory?
        logger.debug("Building basisId proof request");
        IndyProofRequest indyProofRequest = new IndyProofRequest();
        indyProofRequest.setName("Basis-Id proof request");
        indyProofRequest.setVersion("0.1");
        indyProofRequest.setRequestedAttributes(requestedAttributes);
        indyProofRequest.setRequestedPredicates(new HashMap<>());
        indyProofRequest.setNonRevoked(indyProofRequestNonRevoked);
        indyProofRequest.setNonce(nonce);

        V10PresentationSendRequestRequest v10PresentationSendRequestRequest =
            new V10PresentationSendRequestRequest();
        v10PresentationSendRequestRequest.setComment(
            "Connection-oriented proof request for Basis-Id");
        UUID connectionIdUUID = UUID.fromString(connectionId);
        v10PresentationSendRequestRequest.setConnectionId(connectionIdUUID);
        v10PresentationSendRequestRequest.setProofRequest(indyProofRequest);

        try {
            logger.debug("Sending basisId proof request");
            V10PresentationExchange v10PresentationExchange = this.presentProofV10Api
                .presentProofSendRequestPost(v10PresentationSendRequestRequest);

            return new Correlation(
                connectionId,
                v10PresentationExchange.getThreadId(),
                v10PresentationExchange.getPresentationExchangeId()
            );
        } catch (ApiException e) {
            logger.error("ACAPY verifyBasisId native message: " + e.getMessage());
            throw new ACAPYVerificationException();
        }
    }

    @Override
    public BasisIdPresentation getBasisIdPresentation(String presentationExchangeId)
        throws ACAPYVerificationPresentationException {
        try {
            logger.debug("Getting basisId proof presentation");
            V10PresentationExchange v10PresentationExchange = this.presentProofV10Api
                .presentProofRecordsPresExIdGet(presentationExchangeId);

            logger.debug("Parsing basisId presentation values");
            LinkedHashMap<String, RawEncoded>
                revealedAttrGroupValues =
                (LinkedHashMap<String, RawEncoded>)
                    v10PresentationExchange.getPresentation().getRequestedProof()
                        .getRevealedAttrGroups()
                        .get("basisid").getValues();


            logger.debug("Parsing basisId presentation attributes");
            String firstNameActual =
                revealedAttrGroupValues.get("firstName").getRaw();
            String lastNameActual =
                revealedAttrGroupValues.get("familyName").getRaw();
            String dateOfBirthActual =
                revealedAttrGroupValues.get("dateOfBirth").getRaw();

            return new BasisIdPresentation(
                firstNameActual,
                lastNameActual,
                dateOfBirthActual
            );
        } catch (ApiException e) {
            logger.error("ACAPY getBasisIdPresentation native message: " + e.getMessage());
            throw new ACAPYVerificationPresentationException();
        }
    }

    @Override
    public Correlation issueCredential(
        CredentialOffer credentialOffer,
        String connectionId)
        throws ACAPYIssueCredentialException {
        V10CredentialFreeOfferRequest credentialOfferRequest = new V10CredentialFreeOfferRequest();

        credentialOfferRequest.autoIssue(true);
        credentialOfferRequest.connectionId(UUID.fromString(connectionId));

        credentialOfferRequest.credDefId(
            this.acapyCredentialFactory.getCredentialDefinitionId(credentialOffer));

        CredentialPreview credPreview = acapyCredentialFactory.buildCredPreview(credentialOffer);
        credentialOfferRequest.credentialPreview(credPreview);

        try {
            logger.debug("Issuing guest credential");
            V10CredentialExchange record = issueCredentialApi
                .issueCredentialSendOfferPost(credentialOfferRequest);

            return new Correlation(
                record.getConnectionId(),
                record.getThreadId(),
                record.getCredentialExchangeId()
            );
        } catch (ApiException e) {
            logger.error("ACAPY issueCredential native message: " + e.getMessage());
            throw new ACAPYIssueCredentialException();
        }
    }

    @Override
    public Correlation getRevocationCorrelation(String credentialExchangeId) throws Exception {
        try {
            V10CredentialExchange record =
                this.issueCredentialApi.issueCredentialRecordsCredExIdGet(credentialExchangeId);

            return new Correlation(
                record.getConnectionId(),
                record.getThreadId(),
                record.getCredentialExchangeId(),
                record.getRevocRegId(),
                record.getRevocationId()
            );
        } catch (ApiException e) {
            logger.error("ACAPY getRevocationCorrelation native message: " + e.getMessage());
            throw new ACAPYRevocationCorrelationException();
        }
    }

    @Override
    public void revokeCredential(String credentialRevocationRegistryId,
        String credentialRevocationId) throws Exception {
        RevokeRequest revokeRequest = new RevokeRequest();
        revokeRequest.setRevRegId(credentialRevocationRegistryId);
        revokeRequest.setCredRevId(credentialRevocationId);
        revokeRequest.setPublish(true); // might be Boolean.TRUE but true should suffice

        try {
            this.revocationApi.revocationRevokePost(revokeRequest);
        } catch (ApiException e) {
            logger.error("ACAPY revokeCredential native message: " + e.getMessage());
            throw new ACAPYRevokeCredentialException();
        }

    }
}
