package com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ACAPYPresentProofDto {

    /*
    presentation_exchange_id: the unique identifier of the presentation exchange
    connection_id: the identifier of the related pairwise connection
    thread_id: the thread ID of the previously received presentation proposal or offer
    initiator: present-proof exchange initiator: self / external
    state: proposal_sent / proposal_received / request_sent / request_received / presentation_sent / presentation_received / verified
    (proposal_sent/proposal_received/request_sent/request_received/presentation_sent/presentation_received/presentation_acked/verified)
    presentation_proposal_dict: the presentation proposal message
    presentation_request: (Indy) presentation request (also known as proof request)
    presentation: (Indy) presentation (also known as proof)
    verified: (string) whether the presentation is verified: true or false
    auto_present: (boolean) prover choice to auto-present proof as verifier requests
    error_msg: the previous error message
     */

    @JsonProperty("presentation_exchange_id")
    private String presentationExchangeId;

    @JsonProperty("connection_id")
    private String connectionId;

    @JsonProperty("thread_id")
    private String threadId;

    @JsonProperty("initiator")
    private String initiator;

    @JsonProperty("state")
    private String state;

    @JsonProperty("presentation_proposal_dict")
    private String presentationProposalDict; // ToDo - some other datatype might be more suitable

    @JsonProperty("presentation_request")
    private Object presentationRequest; // ToDo - some other datatype might be more suitable

    @JsonProperty("presentation")
    private Object presentation; // ToDo - some other datatype might be more suitable

    @JsonProperty("verified")
    private String verified;

    @JsonProperty("auto_present")
    private Boolean autoPresent;

    @JsonProperty("error_msg")
    private String errorMessage;

    public String getPresentationExchangeId() {
        return presentationExchangeId;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public String getThreadId() {
        return threadId;
    }

    public String getInitiator() {
        return initiator;
    }

    public String getState() {
        return state;
    }

    public String getPresentationProposalDict() {
        return presentationProposalDict;
    }

    public Object getPresentationRequest() {
        return presentationRequest;
    }

    public Object getPresentation() {
        return presentation;
    }

    public String getVerified() {
        return verified;
    }

    public Boolean getAutoPresent() {
        return autoPresent;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ACAPYPresentProofDto{" +
            "presentationExchangeId='" + presentationExchangeId + '\'' +
            ", connectionId='" + connectionId + '\'' +
            ", threadId='" + threadId + '\'' +
            ", initiator='" + initiator + '\'' +
            ", state='" + state + '\'' +
            ", presentationProposalDict='" + presentationProposalDict + '\'' +
            ", presentationRequest=" + presentationRequest +
            ", presentation=" + presentation +
            ", verified='" + verified + '\'' +
            ", autoPresent=" + autoPresent +
            ", errorMessage='" + errorMessage + '\'' +
            '}';
    }
}
