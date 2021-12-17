FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD

WORKDIR /build/

COPY pom.xml mvnw mvnw.cmd /build/

RUN mvn -N io.takari:maven:wrapper

COPY src /build/src/

RUN mvn package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/accreditation.company-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-Dlog4j2.formatMsgNoLookups=true","-jar", "accreditation.company-0.0.1-SNAPSHOT.jar"]

