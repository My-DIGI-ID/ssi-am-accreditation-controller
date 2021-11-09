package com.bka.ssi.controller.accreditation.company.application.agent;

import com.bka.ssi.controller.accreditation.company.domain.values.BasisIdPresentation;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

public interface ACAPYClient {

    ConnectionInvitation createConnectionInvitation(String accreditationId) throws Exception;

    Correlation verifyBasisId(String connectionId) throws Exception;

    BasisIdPresentation getBasisIdPresentation(String presentationExchangeId) throws Exception;

    Correlation issueCredential(CredentialOffer credentialOffer, String connectionId)
        throws Exception;

    Correlation getRevocationCorrelation(String credentialExchangeId) throws Exception;

    void revokeCredential(String credentialRevocationRegistryId, String credentialRevocationId)
        throws Exception;
}
