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

package com.bka.ssi.controller.accreditation.company.application.utilities;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Url builder.
 */
@Component
public class UrlBuilder {

    private final Logger logger;
    @Value("${guest.invitation.redirectUrl}")
    private String redirectUrl;

    /**
     * Instantiates a new Url builder.
     *
     * @param logger the logger
     */
    public UrlBuilder(Logger logger) {
        this.logger = logger;
    }

    /**
     * Build guest invitation redirect url string.
     *
     * @param accreditationId the accreditation id
     * @return the string
     */
    public String buildGuestInvitationRedirectUrl(String accreditationId) {
        logger.debug("Building redirect Url for guest invitation for id: " + accreditationId);
        return redirectUrl.replace("{id}", accreditationId);
    }
}
