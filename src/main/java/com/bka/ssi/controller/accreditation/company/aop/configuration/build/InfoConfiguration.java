package com.bka.ssi.controller.accreditation.company.aop.configuration.build;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfoConfiguration {

    @Value("${accreditation.info.title}")
    private String title;

    @Value("${accreditation.info.description}")
    private String description;

    @Value("${accreditation.info.version}")
    private String version;

    @Value("${accreditation.info.contact.name}")
    private String name;

    @Value("${accreditation.info.contact.url}")
    private String url;

    @Value("${accreditation.info.contact.email}")
    private String email;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getEmail() {
        return email;
    }
}
