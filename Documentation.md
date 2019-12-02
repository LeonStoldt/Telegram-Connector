
  
# Hausarbeit Cloud Computing 
> Gruppenmitglieder: Sebastian Lüders, Tim Rader, Leon Stoldt, Dominik Wilms 
 ## Einleitung 
 #### Einführung in den Kontext: 
Um das Projekt und den Nutzen am Besten zu präsentieren, gehen wir von folgendem realistischen Fallbeispiel/Szenario aus: 
  
Sebastian steht in Tornesch am Bahnhof und wartet auf seinen Zug. Er nutzt einen günstigen Mobilfunktarif, der sein monatliches Datenvolumen auf wenige Hundert Megabyte begrenzt und bei dem der Provider nur in wenigen Orten gut ausgebaute Infrastruktur besitzt.
An diesem Tag sind durch eine Störung die Anzeigetafeln des Bahnhofs ausgefallen und der Zug scheint verspätet zu sein. Sebastian möchte sich über die Verspätung informieren und navigiert hierzu mit seinem Smartphone auf die Website der Nordbahn. Durch seine schlechte Internetverbindung lädt die Seite nur partiell und die Auskunft der Bahn zögert sich durch Laden von Blogs, Newseinträge etc. hinaus, obwohl es Sebastian überhaupt nicht interessiert. Die einzige Information, die er wissen möchte ist, wann sein nächster Zug in Tornesch abfährt. Sebastian ist genervt, dass er seine gewünschten Informationen nicht einfacher bekommen könnte.
Nachdem der Zug eine halbe Stunde später ankam, erhält Sebastian eine Textnachricht von seinem bevorstehenden Date. Sie fragt, ob die beiden sich im Kino oder auf dem Weihnachtsmarkt treffen wollen. Dafür möchte Sebastian einen Blick auf die Wettervorhersage werfen und er öffnet seine Wetter-App. Durch die schlechte Internetverbindung kann die App sich jedoch nicht synchronisieren, da eine konstante Internetverbindung vorausgesetzt wird. Nach ständigem Drücken des Synchronisieren-Buttons ist Sebastian nun endgültig genervt und denkt sich, wie schön es wäre, wenn alle Informationen über eine einzige Anwendung abgefragt werden können, ohne ständig aktualisieren zu müssen und dabei noch Datenvolumen sparen.
Diese Situation soll der Chatbot lösen, indem er die Funktionen vereint und Alltagsprobleme, wie die beispielhaft geschilderten, löst. Hierbei entsteht durch die Anfrage und die Antwort (sobald Internet verfügbar ist) ein asynchroner Kommunikationsprozess, bei dem die Antwort nicht aktiv eingeholt werden muss. Dies spart viele Internetrequests pro App oder Website, da eine einzige Anfrage als Textnachricht gesendet wird und die Antwort mit den Informationen als simple Textnachricht selbst mit niedriger Internetverbindung empfangen werden kann. Somit spart sich der Benutzer auch Datenvolumen.

Unser Cloud Computing Projekt ist ein Telegram-Chatbot und hört auf den Namen MIA (My Individual Bot), mit dem über die offizielle Telegram-Api kommuniziert werden kann. 
Der Bot reagiert eventgetrieben auf Nachrichten von Telegramnutzern und kann auf einen Pool von Services zurückgreifen, die die angefragten Informationen liefern. 
Diese Services sind jeweils an eine API angebunden und liefern die Informationen über den Telegram Service zum Benutzer zurück. 

#### Anforderungsdefinition: 
**fachlich:**
Für unsere User soll die Anwendung vor allem eine einfache und schnelle Hilfe im Alltag sein. Nach dem Motto: "Eine App für Alles" hat der Benutzer die Möglichkeit über eine zentrale Application - in unserem Fall Telegram - sämtliche Abfragen zu tätigen, um tägliche Aufgaben zu erleichtern. 
"Einfach" geht schon bei der Bedienung los. Über ein implementiertes Usermanagement kann der Benutzer Einstellungen vornehmen. So lässt sich mit den Befehlen "Start" & "Stop"  MIA aktivieren oder deaktivieren. Sollte dem User zum Beispiel die tägliche "Guten Morgen" Nachricht mit dem Wetter auf Dauer nerven, lässt sich MIA abschalten. Zusätzlich gibt es die Möglichkeit Nutzerinformationen anzeigen zulassen und diese zu löschen.

MIA soll bei minimalen Funktionen und einem schlichten Design den größtmöglichen Informationsgehalt liefern. Das bedeutet, der User bekommt auch wirklich nur die Informationen zurück, die für ihn relevant sind. Viele Applikationen verfügen in der Regel über einen deutlich größeren Funktionsumfang. Der Anwender benötigt häufig aber nur ein Bruchteil der verfügbaren Informationen. Zu dem minimalen Funktionsumfang zählt unter anderem auch die reine Textausgabe. Bilder oder Icons werden nicht mit geladen. Das reduziert Antwortzeiten und produziert kleinere Datenpakete.
Außerdem spielt der Faktor Zeit eine wesentliche Rolle, was sowohl die Antwortzeit als auch das Ausführen der Abfrage angeht.
Ein Beispiel zur Demonstration:
Wann fährt die nächste Bahn ab Elmshorn? Um das herauszufinden müssen wir zunächst die DB App /Homepage suchen, öffnen und die Ladezeit genießen. Dann geht es über das Bürger-Menü zur Abfahrt/Ankunft.  Nach dem eingeben der Abfahrtstation und dem anschließenden Klick auf "Suchen", werden meistens die gewünschten Informationen angezeigt. 

Als Basis für unsere Services dient Telegram. Der Cloud-Messenger ist mit über 200 Millionen aktiven Usern eine bekannte WhatsApp Alternative. [^1] Die native Telegram App lässt sich auf  allen gängigen Plattformen installieren oder auf Wunsch auch als Web-App über [web.telegram.org](https://web.telegram.org/) aufrufen.  Eine Device Unabhängigkeit ist dementsprechend gewährleistet. 

  -Speicherplatzprobleme --> Nur eine App installiert
Außerdem eventuell ein Dashboard mit der History und/oder einer einer Statistik/Logging der Anfragen pro User.  Oder  das ich sehen kann, welcher User wieviele Anfragen auf welchem Service gemacht hat??

[^1]:https://telegram.org/blog/200-million
**technisch:**
Unsere Applikation basiert auf einer Micoroservice-Archiktekur. Die Vorteile gegenüber einem Monolithen spielen uns in die Hände. 
Wenn sich Apis ändern, dann funktioniert ein servie nicht --> Kann seperat angepasst werden + seperates DEPLOYMENT!!!! (vs. Monolith)
 -  hohe Verfügbarkeit --> Warum wichtig?
 -  hohe Resilienz 
 -  geringe Latenz -> schnelle Antwortzeiten 
 -  asynchrone Kommunikation per REST 
 -  hohe Erweiterbarkeit MSA
 -  hohe Skalierbarkeit MSA
 -  kleine, unabhängige self-contained-systems für einen abgeschlossenen, fachlichen Kontext pro Service 
 - -wartbarkeit
 -  unabhängige Deployments (CI / CD) 
 -  hohe Anpassungsfähigkeit und nachhaltige Vereinfachung späterer Implementierungen und der einzelnen Services
 -  dezentrale Datenhaltung 
 - Architektur - Master Slave?
 - 
# Telegram APIs

We offer two kinds of APIs for developers. The  [**Bot API**](https://core.telegram.org/api#bot-api)  allows you to easily create programs that use Telegram messages for an interface. The  [**Telegram API and TDLib**](https://core.telegram.org/api#tdlib-build-your-own-telegram)  allow you to build your own customized Telegram clients. You are welcome to use both APIs free of charge.

You can also add  [**Telegram Widgets**](https://core.telegram.org/widgets)  to your website.

Designers are welcome to create  [**Animated Stickers**](https://core.telegram.org/animated_stickers)  or  [**Custom Themes**](https://core.telegram.org/themes)  for Telegram.

----------

### [](https://core.telegram.org/api#bot-api)Bot API

[![](https://core.telegram.org/file/811140934/1/tbDSLHSaijc/fdcc7b6d5fb3354adf "The Botfather. Click for hi-res picture")](https://core.telegram.org/file/811140327/1/zlN4goPTupk/9ff2f2f01c4bd1b013)

This API allows you to connect bots to our system.  [**Telegram Bots**](https://core.telegram.org/bots)  are special accounts that do not require an additional phone number to set up. These accounts serve as an interface for code running somewhere on your server.

To use this, you don't need to know anything about how our MTProto encryption protocol works — our intermediary server will handle all encryption and communication with the Telegram API for you. You communicate with this server via a simple HTTPS-interface that offers a simplified version of the Telegram API.
  
## Technologien zur Anforderungserfüllung 
 ## Applikationsbeschreibung 
 ### Architekturbeschreibung und Kommunikation 
- Stichwort "Domain Driven Design"? 
  
### Anforderungserfüllung im Hinblick auf Skalierbarkeit / Erweiterbarkeit 
Auf Anforderungserfüllungen einzeln eingehen 
  
Skalierbarkeit: 
- einzelne Services in Dockercontainern beliebig viel hochfahren 
- viele Dockercontainer hochfahren und durch LoadBalancer (Kubernetes / Docker Compose) Traffic verteilen 
- Bottleneck: Telegram API zur Rückführung der Antworten an den Nutzer **[Bleibt zu testen, ob multiple API-Connections möglich sind]** 
  
Erweiterbarkeit: 
Gute / leichte Erweiterbarkeit durch: 
- eigenen Service in belibiger Programmiersprache bauen 
- Service in einem Dockercontainer hochfahren 
- Service per Anleitung in den TelegramService einbinden 
  
### Offene Punkte und Verbesserungsmöglichkeiten 
- SSL? 
  
## Ablauf der Softwareentwicklung 
Werkzeuge: 
- IntelliJ IDEA 
- Docker 
- DrawIO 
- Kubernetes / Docker Compose ? 
  
Testing: 
- Unit Tests 
- Testsuite mit Testaccount und Service-Kommunikation Testen? 
  
CI: 
- gitlab CI für einzelne Projekte 
- weitere CI? (Docker in CI einbinden?) 
  
## Evaluation der Architektur 
 ## Fazit