# Ensure jar compiled before build this image
FROM openjdk:8-jdk-alpine
COPY /rest/target/rest-0.0.1-SNAPSHOT.jar /hello-world-rest.jar
ENTRYPOINT ["java","-jar","/hello-world-rest.jar"]