FROM maven:3.6.3-jdk-11-slim AS build
COPY src /app/DS-playground/src
COPY pom.xml /app/DS-playground
COPY docs /app/DS-playground/docs
COPY img /app/DS-playground/img
WORKDIR /app/DS-playground
RUN mvn package


FROM openjdk:11-jre-slim-buster as deploy
WORKDIR /app
COPY /docs /app/docs
COPY /img /app/img
COPY --from=0 /app/DS-playground/target/DS-Playground*.jar /app/DS-Playground.jar
RUN ls -al
CMD java -jar DS-Playground.jar
