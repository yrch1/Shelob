FROM openjdk:8-jdk-alpine

RUN addgroup -S shelob && adduser -S shelob -G shelob
USER shelob:shelob

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /opt/shelob/shelob.jar
ENTRYPOINT ["java","-jar","/opt/shelob/shelob.jar"]