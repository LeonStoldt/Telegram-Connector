FROM adoptopenjdk/openjdk11:latest
ADD target/TelegramConnector.jar TelegramConnector.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar",  "TelegramConnector.jar"]
