# Build
FROM openjdk:8-jdk-alpine as build
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
COPY backend backend
COPY frontend frontend
RUN chmod +x ./gradlew
RUN ./gradlew clean build --exclude-task batch:build

# Run
FROM openjdk:8-jre-alpine
ENV ARTIFACT_PATH=frontend/build/libs/frontend-0.0.1-RELEASE.jar
COPY --from=build $ARTIFACT_PATH app.jar
RUN mkdir /var/log/moebius/tomcat
RUN mkdir /var/log/moebius/app
ENTRYPOINT ["java","-Dspring.profiles.active=develop","-jar","app.jar"]
