FROM adoptopenjdk/openjdk11:latest
ADD target/TelegramConnector.jar TelegramConnector.jar
EXPOSE 8001
ENTRYPOINT ["java", "-Dtoken=1052831284:AAFhNczedNg-P1WfOVas16SqUBeZJSRl6cI", "-jar",  "TelegramConnector.jar"]