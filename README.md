# SSI AM Accreditation Controller

## ACAPY client library

Current generated ACAPY client library is based on `accreditation-acapy-openapi.v2.json`.

### Troubleshoot: ACAPY client library .jar files are missing in build process

Manually install the .jar file into your local Maven repository.

   ```sh
   mvn install:install-file \
      -Dfile=./lib/com/bka/ssi/controller/accreditation.acapy-client/0.6.0/accreditation.acapy-client-0.6.0.jar \
      -DgroupId=com.bka.ssi.controller \
      -DartifactId=accreditation.acapy-client \
      -Dversion=0.6.0 \
      -Dpackaging=jar \
      -DgeneratePom=true
   ```

In case installing fails, copy ACAPY client library .jar files to local Maven registry manually by
running the command:

   ```sh
   chmod u+x ./scripts/resolve-acapy-client-dependency.sh
   ./scripts/resolve-acapy-client-dependency.sh
   ```

In case installing fails, copy ACAPY client library .jar files to local Maven registry manually.
Make sure the path ~/.m2/repository/com/bka/ssi/controller/accreditation.acapy-client/ exists.

   ```sh
   cp -r ./lib/com/bka/ssi/controller/accreditation.acapy-client/ ~/.m2/repository/com/bka/ssi/controller/accreditation.acapy-client/
   ```

### Generate the ACAPY client library given an OpenAPI specification

1. Make sure Maven `mvn` and NPM `npm` are available on your system. E.g. you can install them via
   Homebrew.
    ```sh
    brew install node
    brew install maven  
    ```

2. Make sure `openapi-generator-cli` is available on your system. E.g. you can install it as a
   global NodeJS dependency.
    ```sh
    npm install @openapitools/openapi-generator-cli -g
    ```

3. OpenAPI specification (v2 or v3) of Aries Cloud Agent Python is the input for the OpenAPI
   generator. E.g. when running an instance of ACAPY v0.6.0 locally on your system with admin API
   available on port `11080`, generate the client library with the following command. Otherwise,
   use `./accreditation-acapy-openapi.v2.json`.
    ```sh
    openapi-generator-cli generate -i http://127.0.0.1:11080/api/docs/swagger.json -o accreditation.acapy-client --api-package com.bka.ssi.controller.accreditation.acapy-client.api --model-package com.bka.ssi.controller.accreditation.acapy-client.model --invoker-package com.bka.ssi.controller.accreditation.acapy-client.invoker --group-id com.bka.ssi.controller --artifact-id accreditation.acapy-client --artifact-version 0.6.0 -g java --skip-validate-spec -p dateLibrary=java8 --library=jersey2
   ```

   Swagger UI and JSON are usually accessible via
    - [http://localhost:11080/api/doc](http://localhost:11080/api/doc)
    - [http://localhost:11080/api/docs/swagger.json](http://localhost:11080/api/docs/swagger.json)

4. Register Maven project of client library to local Maven repository.
    ```sh
    cd accreditation.acapy-client && mvn clean install
    ```

5. Copy entry from local Maven repository into this project. Make sure the
   path `<PATH-TO-THIS-PROJECT>/lib/com/bka/ssi/controller/accreditation.acapy-client/` exists.
    ```sh
    cd ~/.m2/repository &&
    cp -r ./com/bka/ssi/controller/accreditation.acapy-client/ <PATH-TO-THIS-PROJECT>/lib/com/bka/ssi/controller/accreditation.acapy-client/
    ```

## Generate mock accreditation controller

<!-- TODO - BKAACMGT-168 integrate mock controller in local docker-compose setup -->

For a list of API mock tools, see [here](https://openapi.tools/#mock).

### Recommended: Use openapi-mock to mock accreditation controller by OpenAPI specification

See [here](https://github.com/muonsoft/openapi-mock).

```sh
docker run --init --rm -v $(pwd)/target/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json:/tmp/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json -p 8080:8080 -e "OPENAPI_MOCK_SPECIFICATION_URL=/tmp/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json" muonsoft/openapi-mock:latest
```

### Optional: Use prism to mock accreditation controller by OpenAPI specification

See [here](https://github.com/stoplightio/prism).

```sh
docker run --init --rm -v $(pwd)/target/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json:/tmp/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json -p 4010:4010 stoplight/prism:latest mock -h 0.0.0.0 "/tmp/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json"
```

### Optional: Use open-api-mocker to mock accreditation controller by OpenAPI specification

See [here](https://www.npmjs.com/package/open-api-mocker).

```sh
docker run --init --rm -v $(pwd)/target/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json:/app/schema.json -p 5000:5000 jormaechea/open-api-mocker:latest
```

### Not recommended: Boilerplate with openapi-generator responding with 501 not implemented on any request

Might be useful if mock implementation is a dedicated separate component.

1. Make sure Maven `mvn` and NPM `npm` is available on your system. E.g. you can install them via
   Homebrew.
    ```sh
    brew install node
    brew install maven
    ```

2. Make sure `openapi-generator-cli` is available on your system. E.g. you can install it as a
   global NodeJS dependency.
    ```sh
    npm install @openapitools/openapi-generator-cli -g
    ```

3. OpenAPI specification (v2 or v3) of `ssi-am-accreditation-controller` is the input for the
   OpenAPI generator.
    ```sh
    mvn verify -DskipTests
    cd ../ssi-am-accreditation-controller
    openapi-generator-cli generate -i ./ssi-am-accreditation-controller/target/accreditation.company-0.0.1-SNAPSHOT-openapi.v3.json -o ssi-am-accreditation-controller-mock --api-package com.bka.ssi.controller.accreditation.company.mock.api --model-package com.bka.ssi.controller.accreditation.company.mock.model --group-id com.bka.ssi.controller --artifact-id accreditation.company.mock --artifact-version 0.0.1-SNAPSHOT -g java --skip-validate-spec -p dateLibrary=java8 --library=spring-boot
    ```

4. Package and run generated mock accreditation controller.
   ```sh
   cd ./ssi-am-accreditation-controller-mock
   mvn package
   java -jar target/accreditation.company.mock-0.0.1-SNAPSHOT.jar
   ```

## Access Swagger UI and API specified via OpenAPI 3 for local environment

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

Run `mvn verify -DskipTests` in order to generate OpenAPI 3 json
in `./target/accreditation. company-0.0.1-SNAPSHOT-openapi.v3.json`.
