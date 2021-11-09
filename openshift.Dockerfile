FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD

WORKDIR /build/

COPY pom.xml mvnw mvnw.cmd /build/

RUN mvn -N io.takari:maven:wrapper

COPY lib /build/lib/
COPY src /build/src/
COPY scripts /build/scripts

# ToDo - BKAACMGT-188
RUN chmod u+x ./scripts/resolve-acapy-client-dependency.sh
RUN ./scripts/resolve-acapy-client-dependency.sh

## PDB - Added embeddedMongodbMode
RUN mvn package -Dmaven.test.skip=true -DembeddedMongodbMode=test 

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

RUN mkdir -p /data/templates && mkdir -p /app/resources/i18n/ui

VOLUME /data

COPY --from=MAVEN_BUILD /build/target/accreditation.company-0.0.1-SNAPSHOT.jar /app/
COPY --from=MAVEN_BUILD /build/src/main/resources/templates/email/invitation/*.html /data/templates/
COPY --from=MAVEN_BUILD /build/src/main/resources/i18n/ui/* /app/resources/i18n/ui/

# PDB - For OpenShift - nothing runs with ROOT 
RUN chmod 777 -R /app && chmod 777 -R /app/*  && chmod -R 777 /data && chmod -R 777 /data/*
EXPOSE 8080

# For debugging
#RUN apk --no-cache add curl

ENTRYPOINT ["java", "-jar", "accreditation.company-0.0.1-SNAPSHOT.jar"]
