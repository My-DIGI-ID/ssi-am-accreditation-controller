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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import javax.validation.constraints.NotNull;

/**
 * The type Completion status output dto.
 */
public class CompletionStatusOutputDto {

    private String transactionName;
    private boolean status;
    private String actionToken;

    /**
     * Instantiates a new Completion status output dto.
     *
     * @param transactionName the transaction name
     * @param status          the status
     * @param actionToken     the action token
     */
    public CompletionStatusOutputDto(String transactionName, boolean status,
        @NotNull String actionToken) {
        this.transactionName = transactionName;
        this.status = status;
        this.actionToken = actionToken;
    }

    /**
     * Instantiates a new Completion status output dto.
     *
     * @param transactionName the transaction name
     * @param status          the status
     */
    public CompletionStatusOutputDto(String transactionName, boolean status) {
        this.transactionName = transactionName;
        this.status = status;
    }

    /**
     * Gets transaction name.
     *
     * @return the transaction name
     */
    public String getTransactionName() {
        return transactionName;
    }

    /**
     * Sets transaction name.
     *
     * @param transactionName the transaction name
     */
    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    /**
     * Is status boolean.
     *
     * @return the boolean
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Gets action token.
     *
     * @return the action token
     */
    public String getActionToken() {
        return actionToken;
    }

    /**
     * Sets action token.
     *
     * @param actionToken the action token
     */
    public void setActionToken(String actionToken) {
        this.actionToken = actionToken;
    }
}
