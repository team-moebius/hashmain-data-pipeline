FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD backend/build/libs/backend-0.0.1-RELEASE.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
