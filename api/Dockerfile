# Build
FROM openjdk:11-jdk-slim as build
COPY /build.gradle settings.gradle gradlew ./
COPY /gradle gradle
COPY api api
COPY data data
RUN chmod +x ./gradlew
RUN ./gradlew clean build --exclude-task batch:build

# Run
FROM openjdk:8-jre-alpine
ENV ARTIFACT_PATH=tracker/build/libs/tracker-0.0.1-RELEASE.jar
COPY --from=build $ARTIFACT_PATH tracker.jar
ENV TZ Asia/Seoul
ENTRYPOINT ["java","-Dspring.profiles.active=production","-Dreactor.netty.http.server.accessLogEnabled=true","-jar","tracker.jar"]
