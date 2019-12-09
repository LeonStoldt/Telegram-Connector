# Telegram Bot

> Unser Bot: [http://t.me/MyIndividualAssistentBot](http://t.me/MyIndividualAssistentBot)
>
> Username: `@MyIndividualAssistantBot`
> 
> Anzeigename: `Mia`
>
> [![MIA](https://shaunthesheep.s3.amazonaws.com/character_lula_1_3.png)](https://shaunthesheep.s3.amazonaws.com/character_lula_1_3.png)

## Telegram Connector (Master)

### Was für Aufgaben hat der Telegram Connector?
-	Schnittstelle zum User über die [Telegram API]([https://core.telegram.org/bots](https://core.telegram.org/bots))
-	erhält die Nachrichten durch die [Telegram API]([https://core.telegram.org/bots](https://core.telegram.org/bots))
-	leitet die wichtigsten Informationen an den Distributor weiter, damit die Nachrichten vom richtigen Microservice beantwortet werden
-	nimmt die Nachrichten des Distributors wieder entgegen
-	sendet die fertige Nachricht des Mircoservices wieder an den User zurück

### Welche Technologie wird verwendet?
-	Spring (-Boot)
-	Java 11
-	JUnit

### Was ist beim Einrichten des Telegram Connectors zu beachten?
-	Damit die Telegram API benutzt werden kann wird ein Token benötigt
-	Dieses Token wird aktuell über die Umgebungsvariablen hereingegeben
-	In den Environment Variables also folgenden Eintrag hinzufügen: TOKEN=`ENTER TOKEN HERE`
-	Das Token ist in der Docker compose file als Umgebungsvariable zu finden

## Kommunikationsstandard der Microservices

#### JSON aus dem Telegramconnector an den Distributor (Beispiel)
``` json
{  
    chatId= 1337,
    message= '/start'
    firstName= 'Sebastian'
    lastName= 'Cheng'
    userName= 'sebbl'
    title= 'El Duderino'
    chatType= private
}
```

#### JSON Antwort aus dem Distributor and den Telegramconnector (Beispiel)
``` json
{  
  Moin Sebastian!
  Ich bin Mia, deine individuale Assistentin für alltägliche Probleme.
  Es freut mich sehr, dass du meine Funktionen in Anspruch nehmen möchtest.
  Mithilfe des Kommandos '/hilfe' erzähle ich dir alles über meine bisherigen Funktionen.
  Ich wünsche dir viel Spaß und hoffe, du schreibst mir schon bald.
}
```

Die Antwort kommt direkt als Nachricht zurück, sodass sie direkt an den User weitergeleitet werden kann.

## Weitere Ideen:
-	Admin Panel (`offen`)
-	[Tankpreise]([https://creativecommons.tankerkoenig.de/](https://creativecommons.tankerkoenig.de/)) (**Tim**)
-	[Deutsche Bahn]([https://developer.deutschebahn.com/store/](https://developer.deutschebahn.com/store/)) (**Dominik und Leon**)

> NLP - Prüfen, ob Features in dem Bereich NLP interessant sind.
> -	https://rapidapi.com/systran/api/systran-io-translation-and-nlp
> -	https://dialogflow.com/
> -	https://wit.ai/	