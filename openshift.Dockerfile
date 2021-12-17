FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD

WORKDIR /build/

COPY pom.xml mvnw mvnw.cmd /build/

RUN mvn -N io.takari:maven:wrapper

COPY src /build/src/

RUN mvn package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

RUN mkdir -p /data/templates && mkdir -p /app/resources/i18n/ui

VOLUME /data

COPY --from=MAVEN_BUILD /build/target/accreditation.company-0.0.1-SNAPSHOT.jar /app/
COPY --from=MAVEN_BUILD /build/src/main/resources/templates/email/invitation/*.html /data/templates/
COPY --from=MAVEN_BUILD /build/src/main/resources/i18n/ui/* /app/resources/i18n/ui/

# For OpenShift - nothing runs with ROOT
#RUN chmod -R 777 /app && \
# chmod -R 777 /app/*  && \
# chmod -R 777 /data && \
# chmod -R 777 /data/*
RUN chgrp -R 0 /app && chmod -R g=u /app && \
    chgrp -R 0 /data && chmod -R g=u /data && \
    chgrp -R 0 /app/* && chmod -R g=u /app/* && \
    chgrp -R 0 /data/* && chmod -R g=u /data/*

EXPOSE 8080

# For debugging
#RUN apk --no-cache add curl

ENTRYPOINT ["java", "-Dlog4j2.formatMsgNoLookups=true","-jar", "accreditation.company-0.0.1-SNAPSHOT.jar"]
