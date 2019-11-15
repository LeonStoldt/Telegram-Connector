# Telegram Bot

> Unser Bot: [http://t.me/MyIndividualAssistentBot](http://t.me/MyIndividualAssistentBot)
>
> Username: `@MyIndividualAssistantBot`
> 
> Anzeigename: `Mia`

## Telegram Connector (Master)

### Was für Aufgaben hat der Telegram Connector?
-	ist die Schnittstelle zum User
-	erhält die Nachrichten durch die [Telegram API]([https://core.telegram.org/bots](https://core.telegram.org/bots))
-	erkennt den Befehl der Nachricht
-	leitet die Parameter der Nachricht an den richtigen Microservice weiter
-	nimmt die Nachrichten des Mircoservices wieder entgegen
-	sendet die fertige Nachricht des Mircoservices wieder an den User zurück

### Welche Technologie wird verwendet?
-	Spring (-Boot, -Data)
-	Java 11
-	JUnit
-	Mockito

### Was ist beim Einrichten des Telegram Connectors zu beachten?
-	Damit die Telegram API benutzt werden kann wird ein Token benötigt
-	Dieses Token wird aktuell über die Umgebungsvariablen hereingegeben
-	In den Environment Variables also folgenden Eintrag hinzufügen: TELEGRAM_API_TOKEN=`ENTER TOKEN HERE`
-	Das Token wird nicht veröffentlicht und kann sich von Leon Stoldt besorgt werden

## Kommunikationsstandard der Microservices

#### JSON aus dem Master (Telegramconnector) an die Microservices
``` json
{  
  "source": "telegramConnector",  
  "destination": "WeatherService",  
  "timestamp": "2019-11-11 2:57pm UTC",  
  "command": {
			  "city" : "Hamburg",
			  "country_code" : "DE",
			  "forecast" : false
			  }  
}
```

:information_source: `command` liefert die Parameter, die von dem Mikroservice bzw. der API benötigt werden. Idealerweise sollte sich hier auf ein gemeinsames DTO (Data Transfer Object) geeinigt werden.

#### JSON aus einem Microservice and den Master (Telegramconnector)
``` json
{  
  "source": "WeatherService",  
  "destination": "telegramConnector",  
  "status_code": 200,  
  "timestamp": "2019-11-11 2:58pm UTC",  
  "message": "This output message will be displayed by telegram [Telegram Markdown Support]"
}
```

:information_source: `message` soll bereits so aufgearbeitet sein, dass sie direkt an Telegram verschickt werden kann.


## TODOs:
-	Dockerfiles erstellen
-	Docker Compose vs Kubernetes klären
-	Server (AWS o.ä.)
-	Prüfen von Verhalten bei mehreren Instanzen

## Weitere Ideen:
-	Admin Panel (`offen`)
-	Postgres in Docker (**Leon**)
-	Nordbahn (**Leon**)
-	Weather Service (**Tim**)
-	[Tankpreise]([https://creativecommons.tankerkoenig.de/](https://creativecommons.tankerkoenig.de/)) (**Tim**)
-	[NFL Service](https://api.nfl.com) (**Jan**)
-	[Deutsche Bahn]([https://developer.deutschebahn.com/store/](https://developer.deutschebahn.com/store/)) (**Dominik und Leon**)
-	[Google Translate]([https://cloud.google.com/translate/docs/](https://cloud.google.com/translate/docs/)) (**Sebastian**)

> NLP - schauen, ob wir das einbringen können
> -	https://rapidapi.com/systran/api/systran-io-translation-and-nlp
> -	https://dialogflow.com/
> -	https://wit.ai/	