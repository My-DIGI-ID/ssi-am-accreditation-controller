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

RUN mvn package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/accreditation.company-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "accreditation.company-0.0.1-SNAPSHOT.jar"]
