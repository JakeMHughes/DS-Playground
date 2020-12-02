FROM alpine as node
RUN apk add --update npm
WORKDIR /app
COPY package.json /app/package.json
RUN npm install
RUN ls -al


FROM maven:3.6.3-jdk-11-slim AS build
COPY src /app/DS-playground/src
COPY pom.xml /app/DS-playground
COPY docs /app/DS-playground/docs
COPY img /app/DS-playground/img
RUN rm -rf /app/DS-playground/src/resources/static/ace
COPY --from=0 /app/node_modules/ace-builds/src /app/datasonnet/src/resources/static/ace
WORKDIR /app/DS-playground
RUN mvn package


FROM openjdk:11-jre-slim-buster
WORKDIR /app
COPY /docs /app/docs
COPY /img /app/img
COPY --from=1 /app/DS-playground/target/DS-Playground*.jar /app/DS-Playground.jar
RUN ls -al
CMD java -jar DS-Playground.jar
