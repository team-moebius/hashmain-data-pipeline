# Build
FROM openjdk:8-jdk-alpine as build
COPY /build.gradle settings.gradle gradlew ./
COPY /gradle gradle
COPY streams streams
RUN chmod +x ./gradlew
RUN ./gradlew clean :streams:bootJar

# Run
FROM openjdk:8-jre-alpine
ENV ARTIFACT_PATH=streams/build/libs/streams-0.0.1-RELEASE.jar
COPY --from=build $ARTIFACT_PATH streams.jar
ENTRYPOINT ["java","-Dspring.profiles.active=production","-jar","streams.jar"]
