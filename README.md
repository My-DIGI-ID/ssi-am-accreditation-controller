# SSI AM Accreditation Controller

## Table of contents

* [Set Up](#set-up)
    * [Required Dependencies](#required-dependencies)
    * [Required Environment Variables](#required-environment-variables)
    * [Running the application locally](#running-the-application-locally)
    * [Building the application](#building-the-application)
    * [Running the application locally](#running-the-application-locally)
    * [Access Swagger UI and API specified via OpenAPI 3 for local environment](#access-swagger-ui-and-api-specified-via-openapi-3-for-local-environment)
* [Testing](#testing)
    * [Running the Unit Tests](#running-the-unit-tests)
* [Code Quality](#code-quality)
    * [Checkstyle](#checkstyle)
* [Documentation](#documentation)
    * [JavaDoc](#javadoc)

## Set Up

### Required Dependencies

* Hyperledger Aries Cloud Agent Python
* Keycloak
* MongoDB

### Required Environment Variables

| Variable | Description |
|----------|-------------|
| ACCR_CONTROLLER_PORT | Server port |
| ACCR_CONTROLLER_SSL_ENABLED | Whether to enable SSL support |
| ACCR_CONTROLLER_SSL_KEYSTORE | Path to the key store that holds the certificates |
| ACCR_CONTROLLER_SSL_KEYSTORE_PASSWORD | Password used to access the key store |
| ACCR_CONTROLLER_SSL_KEYSTORE_TYPE | Type of the keystore |
| ACCR_CONTROLLER_SSL_KEY_ALIAS | Alias that identifies the key in the key store |
| ACCR_MONGODB_HOST | Mongo server host |
| ACCR_MONGODB_PORT | Mongo server port |
| ACCR_MONGODB_USERNAME | Login user of the mongo server |
| ACCR_MONGODB_PASSWORD | Login password of the mongo server |
| ACCR_MONGODB_AUTH_DB_NAME | Authentication database name |
| ACCR_MONGODB_DB_NAME | Database name |
| ACCR_MONGODB_SSL_ENABLED | Whether to enable SSL support for mongo server connection |
| ACCR_MONGODB_SSL_KEYSTORE | Path to the key store that holds the CA-signed client certificate |
| ACCR_MONGODB_SSL_KEYSTORE_PASSWORD | Password used to access the key store |
| ACCR_MONGODB_SSL_TRUSTSTORE | Path to the trust store that holds the CA key |
| ACCR_MONGODB_SSL_TRUSTSTORE_PASSWORD | Password used to access the trust store |
| ACCR_MONGODB_SSL_INVALID_HOSTNAME_ALLOWED | Whether to skip hostname verification for mongo server certificate |
| ACCR_ID_PROVIDER_HOST | ID Provider host URL |
| ACCR_ID_PROVIDER_PERMISSIONS_PATH | Path to check permissions |
| ACCR_ID_PROVIDER_TOKEN_PATH | Path to verify token |
| ACCR_ID_PROVIDER_PORT | ID Provider port |
| ACCR_ID_PROVIDER_REALM | Realm in which the permissions are stored |
| ACCR_ID_PROVIDER_ACCR_CLIENT_ID | Client ID for the controller |
| ACCR_ID_PROVIDER_ACCR_CLIENT_SECRET | Client secret for the controller |
| ACCR_ID_PROVIDER_SSL_TRUST_ALL | Whether to always trust the ID Provider certificate. Should be false for prod |
| ACCR_ID_PROVIDER_VERIFY_HOSTNAME | Whether to verify hostname for the ID Provider certificate.Should be true for prod |
| ACCR_ALLOWED_ORIGIN_HOST_A | Frontend URL |
| ACCR_ALLOWED_ORIGIN_HOST_B | Additional allowed URL |
| ACCR_ALLOWED_ORIGIN_HOST_C | Additional allowed URL |
| ACCR_EMAIL_TEMPLATE_GUEST_INVITATION | Path to guest invitation email template |
| ACCR_EMAIL_GUEST_INVITATION_REDIRECT | Guest redirect URL |
| ACCR_BASIS_ID_VERIFICATION_DEV_MODE | Whether to disable BasisID verification. Should be false for prod |
| ACCR_AGENT_API_URL | Acapy agent URL |
| ACCR_AGENT_PORT_ADMIN | Acapy agent admin API port |
| ACCR_AGENT_API_KEY | Acapy agent admin API key |
| ACCR_AGENT_WEBHOOK_API_KEY | Acapy webhook API key |
| AGENT_API_KEY_HEADER_NAME | Name of the API key header parameter |
| EMPLOYEE_CREDENTIAL_DEFINITION | Employee credential definition ID |
| EMPLOYEE_CREDENTIAL_SCHEMA | Employee credential schema ID |
| GUEST_CREDENTIAL_DEFINITION | Guest credential definition ID |
| GUEST_CREDENTIAL_SCHEMA | Guest credential schema ID |
| BDR_BASIS_ID_CREDENTIAL_DEFINITION | BasisID credential definition ID |
| BDR_BASIS_ID_SCHEMA | BasisID credential schema ID |
| ACCR_INFO_TITLE | Swagger application title |
| ACCR_INFO_DESCRIPTION | Swagger application description |
| ACCR_INFO_VERSION | Swagger application version |
| ACCR_INFO_CONTACT_NAME | Swagger application contact name |
| ACCR_INFO_CONTACT_URL | Swagger application contact URL |
| ACCR_INFO_CONTACT_EMAIL | Swagger application contact email |
| ACCR_GUEST_TOKEN_LIFETIME_MS | Custom guest access token validity in milliseconds |
| ACCR_JWT_USER_IDENTIFIER_ENTRY_NAME | JWT user identifier claim name |
| ACCR_SWAGGER_UI_ID_PROVIDER_HOST | URL for Swagger UI authentication |
| ACCR_EMAIL_TEMPLATE_EMPLOYEE_INVITATION | Path to employee invitation email template |
| ACCR_EMPLOYEE_CONNECTION_QR_SIZE | Employee connection QR code size in pixels |
| ACCR_GUEST_CONNECTION_QR_SIZE | Guest connection QR code size in pixels |
| ACCR_I18N_PATH | Path to i18n json file |
| ACCR_FUZZY_LD_THRESHOLD | Levenshtein distance maximum to mach Basis ID to invitation |
| ACCR_GUEST_BASIS_ID_FUZZY_LIMIT | Limit of difference for LD |
| ACCR_API_KEY_HEADER_NAME | Controller API key header parameter |
| ACCR_API_KEY | Controller API key |
| ACCR_MGMT_DISCOVERY_ENABLED | Whether to enable the actuators discovery page |
| ACCR_MGMT_EXPOSURE_INCLUDE | List of endpoints to expose |
| ACCR_MGMT_ENDPOINT_HEALTH_ENABLED | Whether to enable the health endpoint |
| ACCR_MGMT_ENDPOINT_HEALTH_SHOW_DETAILS | Whether to expose details on the health endpoint |
| ACCR_MGMT_ENDPOINT_INFO_ENABLED | Whether to enable the info endpoint |
| ACCR_MGMT_HEALTH_LIVENESS_STATE_ENABLED | Exposes the “Liveness” application availability state |
| ACCR_MGMT_HEALTH_READINESS_STATE_ENABLED | Exposes the “Readiness” application availability state |

### Building the application

To compile and package the application use the following command:

```sh
mvn package
```

### Running the application locally

Run the `main` method in the `com.bka.ssi.controller.accreditation.company.Application` from your
IDE.

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```sh
mvn spring-boot:run
```

### Access Swagger UI and API specified via OpenAPI 3 for local environment

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

Run `mvn verify -DskipTests` in order to generate OpenAPI 3 json
in `./target/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json`.

## Testing

### Running the Unit Tests

The unit test can be run through your preferred IDE.

IntelliJ: https://www.jetbrains.com/help/idea/performing-tests.html

Alternatively the unit test can also be run using the following command:

```sh
mvn test
```

### Coverage

The coverage report can be generated through your preferred IDE.

IntelliJ: https://www.jetbrains.com/help/idea/running-test-with-coverage.html

## Code Quality

### Checkstyle

The code analysis can be run with the following command:

```sh
mvn validate
```

## Documentation

### JavaDoc

To generate the JavaDoc run the following command:

```sh
mvn javadoc:javadoc
```

The JavaDoc will be generated in `target/site/apidocs`, open `index.html` for an overview of the
documentation.