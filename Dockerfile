FROM adoptopenjdk/openjdk11:latest
ADD target/TelegramConnector.jar TelegramConnector.jar
EXPOSE 8001
ENTRYPOINT ["java", "-Dtoken=1052831284:AAFhNUColiG4mn0q57qyvePH19M8JsVtMwM", "-jar",  "TelegramConnector.jar"]
