package com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.my_digi_id.acapy_client.model.CredentialOffer;
import io.github.my_digi_id.acapy_client.model.IndyCredRequest;

import java.util.LinkedHashMap;

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

    public String getCredentialExchangeId() {
        return credentialExchangeId;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public String getThreadId() {
        return threadId;
    }

    public String getParentThreadId() {
        return parentThreadId;
    }

    public String getInitiator() {
        return initiator;
    }

    public String getState() {
        return state;
    }

    public String getCredentialDefinitionId() {
        return credentialDefinitionId;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public LinkedHashMap<String, Object> getCredentialProposalDict() {
        return credentialProposalDict;
    }

    public CredentialOffer getCredentialOffer() {
        return credentialOffer;
    }

    public IndyCredRequest getCredentialRequest() {
        return credentialRequest;
    }

    public String getCredentialRequestMetadata() {
        return credentialRequestMetadata;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public String getRawCredential() {
        return rawCredential;
    }

    public Object getCredential() {
        return credential;
    }

    public Boolean getAutoOffer() {
        return autoOffer;
    }

    public Boolean getAuto_Issue() {
        return auto_Issue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
