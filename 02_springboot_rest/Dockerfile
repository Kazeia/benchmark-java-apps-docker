# Ensure jar compiled before build this image
FROM openjdk:8-jdk-alpine
COPY /rest/target/rest-0.0.1-SNAPSHOT.jar /hello-world-rest.jar
COPY /rest/elastic-apm-agent-1.13.0.jar /elastic-apm-agent-1.13.0.jar
ENTRYPOINT ["java","-javaagent:/elastic-apm-agent-1.13.0.jar","-Delastic.apm.service_name=hello-world","-Delastic.apm.server_url=http://localhost:8200","-Delastic.apm.secret_token= ","-Delastic.apm.application_packages=com.demo.rest","-jar","/hello-world-rest.jar"]
EXPOSE 8080

# java -javaagent:/elastic-apm-agent-1.13.0.jar -Delastic.apm.service_name=hello-world -Delastic.apm.server_url=http://localhost:8200 -Delastic.apm.secret_token=  -Delastic.apm.application_packages=com.demo.rest -jar /hello-world-rest.jar

# java -javaagent:./elastic-apm-agent-1.13.0.jar -Delastic.apm.service_name=hello-world -Delastic.apm.server_url=http://localhost:8200 -Delastic.apm.secret_token=  -Delastic.apm.application_packages=com.demo.rest -jar target/rest-0.0.1-SNAPSHOT.jar