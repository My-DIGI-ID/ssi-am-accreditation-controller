package com.bka.ssi.controller.accreditation.company.application.agent.webwooks;

import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webwooks.dto.input.ACAPYPresentProofDto;

public interface WebhookService {
    public void handleOnConnection(ACAPYConnectionDto inputDto) throws Exception;

    public void handleOnIssueCredential(ACAPYIssueCredentialDto inputDto) throws Exception;

    public void handleOnPresentProof(ACAPYPresentProofDto inputDto) throws Exception;
}
