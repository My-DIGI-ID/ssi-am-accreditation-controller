package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;

import com.bka.ssi.controller.accreditation.acapy_client.api.ConnectionApi;
import com.bka.ssi.controller.accreditation.acapy_client.api.IssueCredentialV10Api;
import com.bka.ssi.controller.accreditation.acapy_client.api.PresentProofV10Api;
import com.bka.ssi.controller.accreditation.acapy_client.invoker.ApiException;
import com.bka.ssi.controller.accreditation.acapy_client.model.CredAttrSpec;
import com.bka.ssi.controller.accreditation.acapy_client.model.CredentialPreview;
import com.bka.ssi.controller.accreditation.acapy_client.model.IndyProofReqAttrSpec;
import com.bka.ssi.controller.accreditation.acapy_client.model.IndyProofRequest;
import com.bka.ssi.controller.accreditation.acapy_client.model.InvitationResult;
import com.bka.ssi.controller.accreditation.acapy_client.model.RawEncoded;
import com.bka.ssi.controller.accreditation.acapy_client.model.V10CredentialExchange;
import com.bka.ssi.controller.accreditation.acapy_client.model.V10CredentialFreeOfferRequest;
import com.bka.ssi.controller.accreditation.acapy_client.model.V10PresentationExchange;
import com.bka.ssi.controller.accreditation.acapy_client.model.V10PresentationSendRequestRequest;
import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.CredentialsConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.utils.ResourceReader;
import com.bka.ssi.controller.accreditation.company.application.exceptions.ACAPYException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
import com.bka.ssi.controller.accreditation.company.application.factories.accreditations.GuestAccreditationFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.GuestAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.services.AccreditationService;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations.GuestAccreditationPrivateInfoInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.common.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.common.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.common.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.parties.GuestPartyService;
import com.bka.ssi.controller.accreditation.company.application.utilities.QRCodeGenerator;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestAccreditationService extends AccreditationService<GuestAccreditation> {

    //    public final static String PERMISSION_RESOURCE_IDENTIFIER = "guest";
    private final GuestPartyService guestPartyService;

    @Value("${template.email.invitation.guest.path}")
    String emailTemplatePath;

    @Value("${guest.invitation.redirectUrl}")
    String redirectUrl;

    @Value("${guest.basisIdVerificationDevMode:false}")
    Boolean basisIdVerificationDevMode;

    private final Logger logger;
    private final SecureRandom secureRandom;

    private GuestAccreditationRepository guestAccreditationRepository;
    private GuestAccreditationFactory guestAccreditationFactory;
    private ACAPYConfiguration acapyConfiguration;
    private CredentialsConfiguration credentialsConfiguration;
    private PresentProofV10Api presentProofV10Api;
    private AuthenticationService authenticationService;

    @Value("${accreditation.guest.connection.qr.size}")
    private int qrSize;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @Autowired
    private ConnectionApi connectionApi;
    @Autowired
    private IssueCredentialV10Api issueCredentialApi;

    public GuestAccreditationService(
        @Qualifier("guestAccreditationMongoDbFacade")
            GuestAccreditationRepository guestAccreditationRepository,
        GuestAccreditationFactory guestAccreditationFactory, GuestPartyService guestPartyService,
        Logger logger, ACAPYConfiguration acapyConfiguration,
        AuthenticationService authenticationService,
        CredentialsConfiguration credentialsConfiguration) {
        this.logger = logger;
        this.acapyConfiguration = acapyConfiguration;
        this.credentialsConfiguration = credentialsConfiguration;
        this.guestPartyService = guestPartyService;
        this.guestAccreditationRepository = guestAccreditationRepository;
        this.guestAccreditationFactory = guestAccreditationFactory;
        this.authenticationService = authenticationService;

        this.setCrudRepository(guestAccreditationRepository);
        this.setFactory(guestAccreditationFactory);

        this.secureRandom = new SecureRandom();
        this.presentProofV10Api = new PresentProofV10Api(this.acapyConfiguration.getApiClient());
    }

    public GuestAccreditation initiateAccreditationWithInvitationEmail(String partyId,
        String userName)
        throws Exception {
        // fetch Party from repository by partyId
        Guest guest;
        try {
            guest = guestPartyService.getPartyById(partyId).get();
        } catch (Exception e) {
            throw new NotFoundException();
        }

        guest.addInformationAboutInvitingPerson(userName);

        // create new Accreditation entity for Party
        GuestAccreditation guestAccreditation = new GuestAccreditation(guest,
            GuestAccreditationStatus.OPEN);

        // save Accreditation to repository
        GuestAccreditation savedAccreditation = this.getCrudRepository().save(guestAccreditation);

        // create proper redirect link with accreditationId
        String redirectLink = redirectUrl.replace("{id}", savedAccreditation.getId());

        // create email body
        String invitationEmail =
            this.buildInvitationEmailFromTemplate(guest, redirectLink);

        // append email body to saved Accreditation
        savedAccreditation.initiateAccreditationWithInvitationLinkAndEmail(
            redirectLink, invitationEmail);
        // update accreditation
        GuestAccreditation updatedAccreditation =
            this.getCrudRepository().save(savedAccreditation);

        // return Accreditation entity
        return updatedAccreditation;
    }

    public GuestAccreditation proceedAccreditationWithQrCode(String accreditationId)
        throws Exception {
        // maybe rename to initiateAccreditationWithQR since in guest accreditation it is after
        // email initiation but in general it can be just a plain QR code

        Optional<GuestAccreditation> result = this.getCrudRepository().findById(accreditationId);

        if (result.isEmpty()) {
            throw new NotFoundException();
        }

        GuestAccreditation accreditation = result.get();
        logger.debug(accreditation.toString());
        try {

            InvitationResult invitationResult =
                connectionApi
                    .connectionsCreateInvitationPost(accreditationId, null, null, null, null);
            String invitationUrl = invitationResult.getInvitationUrl();
            logger.debug(invitationUrl);
            String connectionQrCode =
                qrCodeGenerator.generateQRCodeSvg(invitationUrl, qrSize, qrSize);
            accreditation =
                accreditation.associateConnectionQrCodeWithAccreditation(connectionQrCode);
            this.getCrudRepository().save(accreditation);

        } catch (Exception e) {
            logger.debug(e.getStackTrace().toString());
        }

        return accreditation;
    }

    public void offerAccreditation(String accreditationId)
        throws Exception {

        GuestAccreditation accreditation = this.crudRepository.findById(accreditationId)
            .orElseThrow(() -> new NotFoundException());

        GuestCredential guestCredential =
            accreditation.getParty().getCredentialOffer().getCredential();

        String connectionId = accreditation.getBasisIdVerificationCorrelation().getConnectionId();

        V10CredentialFreeOfferRequest credentialOffer = new V10CredentialFreeOfferRequest();

        credentialOffer.autoIssue(true);

        credentialOffer.connectionId(UUID.fromString(connectionId));

        credentialOffer.credDefId(this.credentialsConfiguration.getGuestCredentialDefinitionId());

        CredentialPreview credPreview = buildCredPreview(guestCredential);
        credentialOffer.credentialPreview(credPreview);
        try {
            V10CredentialExchange record =
                issueCredentialApi.issueCredentialSendOfferPost(credentialOffer);
            Correlation credentialIssuanceCorrelation =
                new Correlation(record.getConnectionId(), record.getThreadId(),
                    record.getCredentialExchangeId());
            accreditation.setGuestCredentialIssuanceCorrelation(credentialIssuanceCorrelation);

        } catch (Exception e) {
            return;
        }

        this.crudRepository.save(accreditation);
    }

    public void completeAccreditationProcess(ACAPYIssueCredentialDto acapyIssueCredentialDto)
        throws Exception {
        String connectionId = acapyIssueCredentialDto.getConnectionId();
        String threadId = acapyIssueCredentialDto.getThreadId();

        GuestAccreditation accreditation = this.guestAccreditationRepository
            .findByIssuanceConnectionIdAndThreadId(connectionId, threadId)
            .orElseThrow(() -> new NotFoundException());

        accreditation.setStatus(GuestAccreditationStatus.ACCEPTED);

        this.guestAccreditationRepository.save(accreditation);
        this.authenticationService.invalidateGuestAccessToken(accreditation.getId());
    }

    public void verifyBasisId(ACAPYConnectionDto acapyConnectionDto)
        throws ACAPYException, NotFoundException {
        // Might only want to pass connectionId and alias=accreditationId to verifyBasisId()
        // Assume acapyConnectionDto.alias is accreditationId; need to sync with fabio

        // Get existing accreditation from db
        String accreditationId = acapyConnectionDto.getAlias();
        GuestAccreditation guestAccreditation =
            this.guestAccreditationRepository.findById(accreditationId)
                .orElseThrow(NotFoundException::new);

        // Create nonce
        String nonce = String.valueOf(secureRandom.nextInt());

        // Requested attributes
        Map<String, String> restrictionItem = new HashMap<>();
        restrictionItem
            .put("cred_def_id", this.credentialsConfiguration
                .getBasisIdCredentialDefinitionId());
        // restriction implicitly includes restriction on schema_id and issuer_did
        //restrictionItem.put("schema_id",this.credentialsConfiguration.getBasisIdCredentialSchemaId());

        IndyProofReqAttrSpec indyProofReqAttrSpec = new IndyProofReqAttrSpec();
        indyProofReqAttrSpec.addNamesItem("firstName");
        indyProofReqAttrSpec.addNamesItem("familyName");
        indyProofReqAttrSpec.addNamesItem("dateOfBirth");
        indyProofReqAttrSpec.addRestrictionsItem(restrictionItem);

        Map<String, IndyProofReqAttrSpec> requestedAttributes = new HashMap<>();
        requestedAttributes.put("basisid", indyProofReqAttrSpec);

        // Proof request
        IndyProofRequest indyProofRequest = new IndyProofRequest();
        indyProofRequest.setName("Basis-Id proof request"); // Set via env?
        indyProofRequest.setVersion("0.1"); // Set via env?
        indyProofRequest.setRequestedAttributes(requestedAttributes);
        indyProofRequest.setRequestedPredicates(new HashMap<>());
        indyProofRequest.setNonce(nonce);

        V10PresentationSendRequestRequest v10PresentationSendRequestRequest =
            new V10PresentationSendRequestRequest();
        v10PresentationSendRequestRequest.setComment(
            "Connection-oriented proof request for Basis-Id"); // Set via env?
        UUID connectionId = UUID.fromString(acapyConnectionDto.getConnectionId());
        v10PresentationSendRequestRequest.setConnectionId(connectionId);
        v10PresentationSendRequestRequest.setProofRequest(indyProofRequest);

        try {
            V10PresentationExchange v10PresentationExchange = this.presentProofV10Api
                .presentProofSendRequestPost(v10PresentationSendRequestRequest);

            // Initiate correlation
            Correlation basisIdVerificationCorrelation =
                new Correlation(acapyConnectionDto.getConnectionId(),
                    v10PresentationExchange.getThreadId(),
                    v10PresentationExchange.getPresentationExchangeId());

            guestAccreditation.setStatus(GuestAccreditationStatus.BASIS_ID_VERIFICATION_PENDING);
            guestAccreditation.setBasisIdVerificationCorrelation(basisIdVerificationCorrelation);

            this.guestAccreditationRepository.save(guestAccreditation);
        } catch (ApiException e) {
            logger.debug(e.getMessage());
            throw new ACAPYException(
                "GuestAccreditationService.verifyBasisId: presentProofV10Api.presentProofSendRequestPost");
        }
    }

    public void completeVerificationOfBasisId(ACAPYPresentProofDto acapyPresentProofDto)
        throws ACAPYException, NotFoundException {
        String connectionId = acapyPresentProofDto.getConnectionId();
        String threadId = acapyPresentProofDto.getThreadId();
        String presentationExchangeId = acapyPresentProofDto.getPresentationExchangeId();

        logger.debug("Getting basisId verification status");
        boolean basisIdVerified = acapyPresentProofDto.getVerified() != null &&
            Boolean.parseBoolean(acapyPresentProofDto.getVerified());

        // Get accreditation by basisIdVerificationCorrelation on (connectionId) and threadId
        // Implement findByConnectionIdAndThreadId, use MongoRepository if possible otherwise filter
        GuestAccreditation guestAccreditation =
            this.guestAccreditationRepository.findByConnectionIdAndThreadId(connectionId,
                threadId).orElseThrow(NotFoundException::new);

        if (basisIdVerificationDevMode) {
            logger.warn("Warning! Fallback to basisId verification dev mode");
            basisIdVerified = true;
        } else {
            logger.debug("Proceeding with basisId verification in production mode");
        }

        if (basisIdVerified) {
            try {
                logger.debug("Getting basisId presentation proof");
                V10PresentationExchange v10PresentationExchange = this.presentProofV10Api
                    .presentProofRecordsPresExIdGet(presentationExchangeId);

                logger.debug("Parsing basisId presentation values");
                LinkedHashMap<String, RawEncoded>
                    revealedAttrGroupValues =
                    (LinkedHashMap<String, RawEncoded>)
                        v10PresentationExchange.getPresentation().getRequestedProof()
                            .getRevealedAttrGroups()
                            .get("basisid").getValues();


                logger.debug("Parsing basisId presentation attributes");
                // Actual basis-id attributes
                // Need to get more attributes?
                String firstNameActual =
                    revealedAttrGroupValues.get("firstName").getRaw();
                String lastNameActual =
                    revealedAttrGroupValues.get("familyName").getRaw();
                String dateOfBirthActual =
                    revealedAttrGroupValues.get("dateOfBirth").getRaw();

                // Expected db attributes
                // Need to get more attributes?
                String firstNameExpected =
                    guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                        .getFirstName();
                String lastNameExpected =
                    guestAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                        .getLastName();

                // Check if basis-id attributes match attributes in db
                // Need to check more attributes?
                logger.debug("Firstname BasisID: " + firstNameActual + " | mongoDB guest first " +
                    "name" + firstNameExpected);
                logger.debug("Familyname BasisID: " + lastNameActual + " | mongoDB guest last " +
                    "name" + lastNameExpected);

                if (firstNameActual.equals(firstNameExpected) &&
                    lastNameActual.equals(lastNameExpected)) {
                    logger.debug("Updating accreditation with BasisId data");
                    // Update accreditation party
                    Correlation basisIdVerificationCorrelation =
                        new Correlation(connectionId, threadId, presentationExchangeId);

                    guestAccreditation.setStatus(GuestAccreditationStatus.BASIS_ID_VALID);
                    guestAccreditation
                        .setBasisIdVerificationCorrelation(basisIdVerificationCorrelation);

                    guestAccreditation
                        .getParty()
                        .getCredentialOffer()
                        .getCredential()
                        .addGuestBirthDateOnBasisIdVerification(dateOfBirthActual)
                        .addBasisIdUniqueIdentifier();

                } else {
                    logger.warn("BasisId invalid!");
                    guestAccreditation.setStatus(GuestAccreditationStatus.BASIS_ID_INVALID);
                }
            } catch (ApiException e) {
                logger.debug(e.getMessage());
                throw new ACAPYException(
                    "GuestAccreditationService.getBasisIdProofAttributesAndValidateGuestAccreditationAgainstBasisId: presentProofV10Api.presentProofRecordsPresExIdGet");
            }
        } else {
            logger.warn("BasisId invalid!");
            guestAccreditation.setStatus(GuestAccreditationStatus.BASIS_ID_INVALID);
        }

        this.guestAccreditationRepository.save(guestAccreditation);
    }

    public boolean isGuestBasisIdValidationCompleted(String accreditationId) throws Exception {

        GuestAccreditation accreditation =
            this.guestAccreditationRepository.findById(accreditationId)
                .orElseThrow(NotFoundException::new);

        if (accreditation.getStatus() == GuestAccreditationStatus.BASIS_ID_VALID) {
            return true;
        }

        if (accreditation.getStatus() == GuestAccreditationStatus.BASIS_ID_INVALID) {
            throw new UnauthorizedException();
        }

        return false;

    }

    public boolean isGuestAccreditationProcessCompleted(String accreditationId)
        throws Exception {

        GuestAccreditation accreditation =
            this.guestAccreditationRepository.findById(accreditationId)
                .orElseThrow(NotFoundException::new);

        if (accreditation.getStatus() == GuestAccreditationStatus.ACCEPTED) {
            return true;
        }

        return false;
    }

    public GuestAccreditation appendWithProprietaryInformationFromGuest(String accreditationId,
        GuestAccreditationPrivateInfoInputDto guestAccreditationPrivateInfoInputDto)
        throws Exception {

        GuestAccreditation accreditation =
            this.crudRepository.findById(accreditationId).orElseThrow(NotFoundException::new);

        logger.debug("Appending Guest Accreditation entity " + accreditation.getId() + " with " +
            "guest private information");
        accreditation.addPrivateInformationByTheGuest(
            guestAccreditationPrivateInfoInputDto.getLicencePlateNumber(),
            guestAccreditationPrivateInfoInputDto.getCompanyStreet(),
            guestAccreditationPrivateInfoInputDto.getCompanyCity(),
            guestAccreditationPrivateInfoInputDto.getCompanyPostCode(),
            guestAccreditationPrivateInfoInputDto.getAcceptedDocument(),
            guestAccreditationPrivateInfoInputDto.getPrimaryPhoneNumber(),
            guestAccreditationPrivateInfoInputDto.getSecondaryPhoneNumber()
        );

        logger.debug("Saving updated Guest Accreditation in database");
        GuestAccreditation savedAccreditation = this.crudRepository.save(accreditation);

        return savedAccreditation;

    }

    public String buildInvitationEmailFromTemplate(Guest guest, String redirectLink) {
        Resource emailTemplateResource = new FileSystemResource(this.emailTemplatePath);
        // ToDo default template as fallback?
        String emailTemplate = ResourceReader.asString(emailTemplateResource);
        emailTemplate = emailTemplate.replace("\r\n", " ").replace("\n", " ");
        String firstName =
            guest.getCredentialOffer().getCredential().getPersona().getFirstName();
        String lastName = guest.getCredentialOffer().getCredential().getPersona().getLastName();
        String invitation = emailTemplate
            .replace("{{EMPLOYEE_FIRSTNAME}}", firstName)
            .replace("{{EMPLOYEE_LASTNAME}}", lastName)
            .replace("{{REDIRECT_URL}}", redirectLink);
        return invitation;
    }

    public Optional<GuestAccreditation> getUniqueAccreditationByPartyParams(
        String referenceBasisId, String firstName, String lastName, String dateOfBirth,
        String companyName, Date validFromDate, Date validUntilDate, String invitedBy)
        throws NotFoundException {

        Optional<GuestAccreditation> guestAccreditation =
            this.guestAccreditationRepository.findByVerificationQueryParameters(
                referenceBasisId,
                firstName,
                lastName,
                dateOfBirth,
                companyName,
                validFromDate,
                validUntilDate,
                invitedBy
            );

        if (guestAccreditation == null) {
            throw new NotFoundException();
        }

        return guestAccreditation;
    }

    private CredentialPreview buildCredPreview(GuestCredential guestCredential) {

        CredentialPreview credentialPreview = new CredentialPreview();

        CredAttrSpec attributesItem = null;

        Persona persona = guestCredential.getPersona();

        attributesItem = new CredAttrSpec();
        attributesItem.name("firstName");
        attributesItem.value(persona.getFirstName());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("lastName");
        attributesItem.value(persona.getLastName());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("titel");
        attributesItem.value(StringUtils.defaultString(persona.getTitle()));
        credentialPreview.addAttributesItem(attributesItem);

        ContactInformation contactInformation = guestCredential.getContactInformation();

        List<String> emails = contactInformation.getEmails();

        attributesItem = new CredAttrSpec();
        attributesItem.name("email");
        attributesItem.value(emails.get(0));
        credentialPreview.addAttributesItem(attributesItem);

        List<String> phoneNumbers = contactInformation.getPhoneNumbers();

        attributesItem = new CredAttrSpec();
        attributesItem.name("primaryPhoneNumber");
        if (phoneNumbers != null && phoneNumbers.size() > 0) {
            attributesItem.value(phoneNumbers.get(0));
        } else {
            attributesItem.value("");
        }
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("secondaryPhoneNumber");
        if (phoneNumbers != null && phoneNumbers.size() > 1) {
            attributesItem.value(phoneNumbers.get(1));
        } else {
            attributesItem.value("");
        }
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyName");
        attributesItem.value(guestCredential.getCompanyName());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("typeOfVisit");
        attributesItem.value(guestCredential.getTypeOfVisit());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("location");
        attributesItem.value(guestCredential.getLocation());
        credentialPreview.addAttributesItem(attributesItem);

        ValidityTimeframe validityTimeframe = guestCredential.getValidityTimeframe();

        attributesItem = new CredAttrSpec();
        attributesItem.name("validFromDate");
        attributesItem.value(validityTimeframe.getValidFromDate().toString());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("validFromTime");
        attributesItem.value(validityTimeframe.getValidFromTime().toString());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("validUntilDate");
        attributesItem.value(validityTimeframe.getValidUntilDate().toString());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("validUntilTime");
        attributesItem.value(validityTimeframe.getValidUntilTime().toString());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("invitedBy");
        attributesItem.value(guestCredential.getInvitedBy());
        credentialPreview.addAttributesItem(attributesItem);

        GuestPrivateInformation privateInformation = guestCredential.getGuestPrivateInformation();

        attributesItem = new CredAttrSpec();
        attributesItem.name("dateOfBirth");
        attributesItem.value(privateInformation.getDateOfBirth());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("licensePlateNumber");
        attributesItem.value(StringUtils.defaultString(privateInformation.getLicencePlateNumber()));
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyCity");
        attributesItem.value(privateInformation.getCompanyCity());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyStreet");
        attributesItem.value(privateInformation.getCompanyStreet());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyPostCode");
        attributesItem.value(privateInformation.getCompanyPostCode());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("referenceBasisId");
        attributesItem.value(StringUtils.defaultString(guestCredential.getReferenceBasisId()));
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("referenceEmployeeId");
        attributesItem.value("");
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyProofOfOwnership");
        attributesItem.value("");
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("dataEncryptionAlgorithm");
        attributesItem.value("");
        credentialPreview.addAttributesItem(attributesItem);

        return credentialPreview;
    }

    public GuestAccreditation cleanGuestInformationOnCheckout(String accreditationId)
        throws NotFoundException {

        GuestAccreditation accreditation =
            this.guestAccreditationRepository.findById(accreditationId)
                .orElseThrow(NotFoundException::new);

        GuestAccreditation cleanedAccreditation = accreditation.cleanGuestInformationOnCheckout();

        GuestAccreditation savedAccreditation =
            this.guestAccreditationRepository.save(cleanedAccreditation);

        return savedAccreditation;
    }
}
