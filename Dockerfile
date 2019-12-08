FROM adoptopenjdk/openjdk11:latest
ADD target/TelegramConnector.jar TelegramConnector.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar",  "TelegramConnector.jar"]
