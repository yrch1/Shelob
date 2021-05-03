FROM openjdk:8-jdk-alpine

ARG EXECUTABLE_PATH=/opt/shelob

WORKDIR ${EXECUTABLE_PATH}

RUN addgroup -S shelob && adduser -S shelob -G shelob
RUN mkdir -p logs
RUN chown -R shelob:shelob ${EXECUTABLE_PATH}/logs
USER shelob:shelob


ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./shelob.jar
COPY src/main/resources/application.properties ./
ENTRYPOINT ["java","-Djasypt.encryptor.password=supersecretz","-jar","shelob.jar"]