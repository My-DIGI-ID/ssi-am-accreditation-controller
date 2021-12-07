package com.bka.ssi.controller.accreditation.company.application.agent.webhooks;

import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYPresentProofDto;

public interface WebhookService {
    public void handleOnConnection(ACAPYConnectionDto inputDto) throws Exception;

    public void handleOnIssueCredential(ACAPYIssueCredentialDto inputDto) throws Exception;

    public void handleOnPresentProof(ACAPYPresentProofDto inputDto) throws Exception;
}
