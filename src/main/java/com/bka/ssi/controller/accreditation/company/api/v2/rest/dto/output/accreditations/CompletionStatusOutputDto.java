package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import javax.validation.constraints.NotNull;

public class CompletionStatusOutputDto {

    @NotNull
    private String transactionName;

    @NotNull
    private boolean status;

    @NotNull
    private String actionToken;

    public CompletionStatusOutputDto(String transactionName, boolean status,
        @NotNull String actionToken) {
        this.transactionName = transactionName;
        this.status = status;
        this.actionToken = actionToken;
    }

    public CompletionStatusOutputDto(String transactionName, boolean status) {
        this.transactionName = transactionName;
        this.status = status;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getActionToken() {
        return actionToken;
    }

    public void setActionToken(String actionToken) {
        this.actionToken = actionToken;
    }
}
