# Build
FROM openjdk:8-jdk-alpine as build
ENV WORK_SPACE=moebius/
WORKDIR $WORK_SPACE

COPY build.gradle settings.gradle gradlew $WORK_SPACE
COPY gradle $WORK_SPACE/gradle
RUN $WORK_SPACE/gradlew build

# Run
FROM openjdk:8-jre-alpine
ENV WORK_SPACE=moebius/
ENV ARTIFACT=frontend-0.0.1-RELEASE.jar
WORKDIR $WORK_SPACE
COPY --from=build $WORK_SPACE/build/libs/$ARTIFACT app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
