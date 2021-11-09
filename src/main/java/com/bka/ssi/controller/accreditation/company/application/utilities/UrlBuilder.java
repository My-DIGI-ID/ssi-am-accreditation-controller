package com.bka.ssi.controller.accreditation.company.application.utilities;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {

    private final Logger logger;
    @Value("${guest.invitation.redirectUrl}")
    private String redirectUrl;

    public UrlBuilder(Logger logger) {
        this.logger = logger;
    }

    public String buildGuestInvitationRedirectUrl(String accreditationId) {
        logger.debug("Building redirect Url for guest invitation for id: " + accreditationId);
        return redirectUrl.replace("{id}", accreditationId);
    }
}
