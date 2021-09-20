package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import javax.validation.constraints.NotNull;

public class CompletionStatusOutputDto {

    @NotNull
    private String transactionName;

    @NotNull
    private boolean status;

    private @NotNull String actionToken;

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

    public boolean getStatus() {
        return status;
    }

    public String getActionToken() {
        return actionToken;
    }
}
