FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/sct-0.0.1-SNAPSHOT.jar sct-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/sct-0.0.1-SNAPSHOT.jar"]
