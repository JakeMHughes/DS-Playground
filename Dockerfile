FROM alpine/git AS clone
WORKDIR /app
RUN git clone https://github.com/JakeMHughes/datasonnet-mapper && cd datasonnet-mapper && git checkout dataweave



FROM maven:3.6.3-jdk-8 AS build
COPY --from=0 /app/datasonnet-mapper /app/datasonnet
WORKDIR /app/datasonnet
RUN  mvn -Ddockerfile.skip install
COPY src /app/DS-playground/src
COPY pom.xml /app/DS-playground
WORKDIR /app/DS-playground
RUN mvn package


FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=1 /app/DS-playground/target/DS-Playground*.jar /app/DS-Playground.jar
RUN ls -al 
CMD java -jar DS-Playground.jar
