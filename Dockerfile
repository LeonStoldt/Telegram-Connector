FROM openjdk:11
ADD target/TelegramConnector.jar TelegramConnector.jar
EXPOSE 8075
ENTRYPOINT ["java", "-Dtoken=1052831284:AAFhNczedNg-P1WfOVas16SqUBeZJSRl6cI", "-jar",  "target/TelegramConnector.jar"]