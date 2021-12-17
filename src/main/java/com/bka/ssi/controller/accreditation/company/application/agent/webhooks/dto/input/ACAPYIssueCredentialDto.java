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

package com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.my_digi_id.acapy_client.model.CredentialOffer;
import io.github.my_digi_id.acapy_client.model.IndyCredRequest;

import java.util.LinkedHashMap;

/**
 * The type Acapy issue credential dto.
 */
public class ACAPYIssueCredentialDto {

    /*
    credential_exchange_id: the unique identifier of the credential exchange
    connection_id: the identifier of the related pairwise connection
    thread_id: the thread ID of the previously received credential proposal or offer
    parent_thread_id: the parent thread ID of the previously received credential proposal or offer
    initiator: issue-credential exchange initiator self / external
    state: proposal_sent / proposal_received / offer_sent / offer_received / request_sent / request_received / issued / credential_received / credential_acked
    (offer_sent/offer_received/request_sent/request_received/credential_issued/credential_received/credential_acked)
    credential_definition_id: the ledger identifier of the related credential definition
    schema_id: the ledger identifier of the related credential schema
    credential_proposal_dict: the credential proposal message
    credential_offer: (Indy) credential offer
    credential_request: (Indy) credential request
    credential_request_metadata: (Indy) credential request metadata
    credential_id: the wallet identifier of the stored credential
    raw_credential: the credential record as received
    credential: the credential record as stored in the wallet
    auto_offer: (boolean) whether to automatically offer the credential
    auto_issue: (boolean) whether to automatically issue the credential
    error_msg: the previous error message
     */

    @JsonProperty("credential_exchange_id")
    private String credentialExchangeId;

    @JsonProperty("connection_id")
    private String connectionId;

    @JsonProperty("thread_id")
    private String threadId;

    @JsonProperty("parent_thread_id")
    private String parentThreadId;

    @JsonProperty("initiator")
    private String initiator;

    @JsonProperty("state")
    private String state;

    @JsonProperty("credential_definition_id")
    private String credentialDefinitionId;

    @JsonProperty("schema_id")
    private String schemaId;

    @JsonProperty("credential_proposal_dict")
    private LinkedHashMap<String, Object> credentialProposalDict;
    // ToDo - some other datatype might be more suitable

    @JsonProperty("credential_offer")
    private CredentialOffer credentialOffer;

    @JsonProperty("credential_request")
    private IndyCredRequest credentialRequest;

    @JsonProperty("credential_request_metadata")
    private String credentialRequestMetadata;

    @JsonProperty("credential_id")
    private String credentialId;

    @JsonProperty("raw_credential")
    private String rawCredential; // ToDo - some other datatype might be more suitable

    @JsonProperty("credential")
    private Object credential; // ToDo - some other datatype might be more suitable

    @JsonProperty("auto_offer")
    private Boolean autoOffer;

    @JsonProperty("auto_issue")
    private Boolean auto_Issue;

    @JsonProperty("error_msg")
    private String errorMessage;

    /**
     * Gets credential exchange id.
     *
     * @return the credential exchange id
     */
    public String getCredentialExchangeId() {
        return credentialExchangeId;
    }

    /**
     * Gets connection id.
     *
     * @return the connection id
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Gets thread id.
     *
     * @return the thread id
     */
    public String getThreadId() {
        return threadId;
    }

    /**
     * Gets parent thread id.
     *
     * @return the parent thread id
     */
    public String getParentThreadId() {
        return parentThreadId;
    }

    /**
     * Gets initiator.
     *
     * @return the initiator
     */
    public String getInitiator() {
        return initiator;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Gets credential definition id.
     *
     * @return the credential definition id
     */
    public String getCredentialDefinitionId() {
        return credentialDefinitionId;
    }

    /**
     * Gets schema id.
     *
     * @return the schema id
     */
    public String getSchemaId() {
        return schemaId;
    }

    /**
     * Gets credential proposal dict.
     *
     * @return the credential proposal dict
     */
    public LinkedHashMap<String, Object> getCredentialProposalDict() {
        return credentialProposalDict;
    }

    /**
     * Gets credential offer.
     *
     * @return the credential offer
     */
    public CredentialOffer getCredentialOffer() {
        return credentialOffer;
    }

    /**
     * Gets credential request.
     *
     * @return the credential request
     */
    public IndyCredRequest getCredentialRequest() {
        return credentialRequest;
    }

    /**
     * Gets credential request metadata.
     *
     * @return the credential request metadata
     */
    public String getCredentialRequestMetadata() {
        return credentialRequestMetadata;
    }

    /**
     * Gets credential id.
     *
     * @return the credential id
     */
    public String getCredentialId() {
        return credentialId;
    }

    /**
     * Gets raw credential.
     *
     * @return the raw credential
     */
    public String getRawCredential() {
        return rawCredential;
    }

    /**
     * Gets credential.
     *
     * @return the credential
     */
    public Object getCredential() {
        return credential;
    }

    /**
     * Gets auto offer.
     *
     * @return the auto offer
     */
    public Boolean getAutoOffer() {
        return autoOffer;
    }

    /**
     * Gets auto issue.
     *
     * @return the auto issue
     */
    public Boolean getAuto_Issue() {
        return auto_Issue;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
