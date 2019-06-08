FROM openjdk:8-jdk-alpine
ADD frontend/build/libs/frontend-0.0.1-RELEASE.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
