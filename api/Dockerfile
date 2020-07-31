# Build
FROM openjdk:11-jdk-slim as build
COPY /build.gradle settings.gradle gradlew ./
COPY /gradle gradle
COPY api api
COPY data data
RUN chmod +x ./gradlew
RUN ./gradlew clean :api:bootJar

# Run
FROM openjdk:11-jre-slim
ENV ARTIFACT_PATH=api/build/libs/*.jar
COPY --from=build $ARTIFACT_PATH app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=production","-jar","app.jar"]
