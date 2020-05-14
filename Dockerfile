FROM openjdk:8
COPY ./target/DS-Playground-0.0.1-SNAPSHOT.jar /DS-Playground.jar
WORKDIR /
EXPOSE 8080
CMD java -jar DS-Playground.jar
