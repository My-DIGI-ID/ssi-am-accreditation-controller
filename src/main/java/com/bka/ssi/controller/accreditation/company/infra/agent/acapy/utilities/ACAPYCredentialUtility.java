package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities;

import com.bka.ssi.controller.accreditation.acapy_client.model.CredentialPreview;
import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;


public interface ACAPYCredentialUtility<T extends Credential> {

    default public CredentialPreview createCredentialPreview(T input) {
        throw new UnsupportedOperationException();
    }
}
