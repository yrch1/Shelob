FROM openjdk:8-jdk-alpine

ARG EXECUTABLE_PATH=/opt/shelob

#https://serverfault.com/questions/683605/docker-container-time-timezone-will-not-reflect-changes
ENV TZ=Europe/Madrid
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR ${EXECUTABLE_PATH}

RUN addgroup -S shelob && adduser -S shelob -G shelob
RUN mkdir -p logs
RUN chown -R shelob:shelob ${EXECUTABLE_PATH}/logs
USER shelob:shelob


ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./shelob.jar
COPY src/main/resources/application.properties ./
ENTRYPOINT ["java","-Djasypt.encryptor.password=supersecretz","-jar","shelob.jar"]