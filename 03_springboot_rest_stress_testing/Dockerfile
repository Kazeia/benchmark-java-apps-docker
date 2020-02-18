# Ensure jar compiled before build this image
FROM openjdk:8-jdk-alpine
COPY /jmeter/target/jmeter-0.0.1-SNAPSHOT.jar /jmeter-test.jar
ENTRYPOINT ["java","-jar","/jmeter-test.jar"]
EXPOSE 8080