# Ensure jar compiled before build this image
FROM openjdk:8-jdk-alpine
COPY /target/hello-world-maven-0.1.0.jar /hello-world-maven.jar
ENTRYPOINT ["java","-jar","/hello-world-maven.jar"]