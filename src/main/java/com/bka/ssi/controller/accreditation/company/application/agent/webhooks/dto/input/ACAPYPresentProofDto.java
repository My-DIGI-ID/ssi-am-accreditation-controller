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

/**
 * The type Acapy present proof dto.
 */
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

    /**
     * Gets presentation exchange id.
     *
     * @return the presentation exchange id
     */
    public String getPresentationExchangeId() {
        return presentationExchangeId;
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
     * Gets presentation proposal dict.
     *
     * @return the presentation proposal dict
     */
    public String getPresentationProposalDict() {
        return presentationProposalDict;
    }

    /**
     * Gets presentation request.
     *
     * @return the presentation request
     */
    public Object getPresentationRequest() {
        return presentationRequest;
    }

    /**
     * Gets presentation.
     *
     * @return the presentation
     */
    public Object getPresentation() {
        return presentation;
    }

    /**
     * Gets verified.
     *
     * @return the verified
     */
    public String getVerified() {
        return verified;
    }

    /**
     * Gets auto present.
     *
     * @return the auto present
     */
    public Boolean getAutoPresent() {
        return autoPresent;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
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
