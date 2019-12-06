<!-- # Hausarbeit Cloud Computing 
> Gruppenmitglieder: Sebastian Lüders, Tim Rader, Leon Stoldt, Dominik Wilms  
DECKBLATT
INAHLTSVERZEICHNIS
QUELLEBNVERZEICHNIS
-->
 ## Einleitung 
 #### Einführung in den Kontext: 
Diese Arbeit entsteht im Rahmen des Wahlpflichtmoduls "Cloud - Grundlagen und Praxis". Die Aufgabe besteht darin als Gruppe eine beliebig wählbare Applikation zu entwerfen, die mit Docker containerisiert und mit einem Tool der Wahl orchestriert wird. Dies soll vor allem in Hinblick auf die Aspekte der Skalierbarkeit und Erweiterbarkeit der Anwendung entwickelt werden.
Um unsere Applikation und ihren Nutzen am Besten zu präsentieren, gehen wir von folgendem realistischen Fallbeispiel/Szenario aus: 
  
Sebastian steht in Tornesch am Bahnhof und wartet auf seinen Zug. Er nutzt einen günstigen Mobilfunktarif, der sein monatliches Datenvolumen auf wenige Hundert Megabyte begrenzt und bei dem der Provider nur in wenigen Orten gut ausgebaute Infrastruktur besitzt.
An diesem Tag sind durch eine Störung die Anzeigetafeln des Bahnhofs ausgefallen und der Zug scheint verspätet zu sein. Sebastian möchte sich über die Verspätung informieren und navigiert hierzu mit seinem Smartphone auf die Website der Nordbahn. Durch seine schlechte Internetverbindung lädt die Seite nur partiell und die Auskunft der Bahn zögert sich durch Laden von Blogs, Newseinträge etc. hinaus, obwohl es Sebastian überhaupt nicht interessiert. Die einzige Information, die er wissen möchte ist, wann sein nächster Zug in Tornesch abfährt. Er ist genervt, dass er seine gewünschten Informationen nicht einfacher bekommen könnte.
Nachdem der Zug eine halbe Stunde später ankam, erhält Sebastian eine Textnachricht von seinem bevorstehenden Date. Sie fragt, ob die beiden sich im Kino oder auf dem Weihnachtsmarkt treffen wollen. Dafür möchte Sebastian einen Blick auf die Wettervorhersage werfen und er öffnet seine Wetter-App. Durch die schlechte Internetverbindung kann die App sich jedoch nicht synchronisieren, da eine konstante Internetverbindung vorausgesetzt wird. Nach ständigem Drücken des Synchronisieren-Buttons ist Sebastian nun endgültig genervt und denkt sich, wie schön es wäre, wenn alle Informationen über eine einzige Anwendung abgefragt werden können, ohne ständig aktualisieren zu müssen und dabei noch Datenvolumen sparen.

Diese Situation soll ein Chatbot lösen, indem er die Funktionen vereint und Alltagsprobleme, wie die beispielhaft geschilderten, löst. Hierbei entsteht durch die Anfrage und die Antwort (sobald Internet verfügbar ist) ein asynchroner Kommunikationsprozess, bei dem die Antwort nicht aktiv eingeholt werden muss. Dies spart viele Internetrequests pro App oder Website, da eine einzige Anfrage als Textnachricht gesendet wird und die Antwort mit den Informationen als simple Textnachricht selbst mit niedriger Internetverbindung empfangen werden kann. Somit spart sich der Benutzer zusätzlich Datenvolumen.

Hierfür haben wir einen Telegram-Chatbot erstellt, der auf den Namen MIA (My Individual Assistent) hört und mit der offiziellen Telegram-Api kommuniziert. Der Bot reagiert eventgetrieben auf Nachrichten von Telegramnutzern und kann auf einen Pool von Services zurückgreifen, die die angefragten Informationen liefern. 
Diese Services sind jeweils an eine API angebunden und liefern die Informationen über den Telegram Service zum Benutzer zurück. 

#### Anforderungsdefinition: 
**fachlich:**
Für die User soll die Anwendung vor allem eine einfache und schnelle Hilfe im Alltag sein. Nach dem Motto: "Eine App für Alles" hat der Benutzer die Möglichkeit über eine zentrale Application - in unserem Fall Telegram - sämtliche Abfragen zu tätigen, um tägliche Aufgaben zu erleichtern. 
Die Bedienung für den User soll möglichst einfach und unkompliziert sein und startet mit einem simplen Usermanagement, mit dem der Benutzer Einstellungen vornehmen kann. So lässt sich MIA mit den Befehlen "/start" und "/stop" aktivieren bzw. deaktivieren. Mit dem Start-Befehl meldet man sich bei MIA an, damit sie den User speichern kann und wieder erkennt. Mit dem Stop-Befehl werden die Daten erstmal nicht gelöscht, sondern der User nur deaktiviert. 
Die gespeicherten Daten können mit "/info" ausgegeben werden und auf Anfrage mit "/delete" gelöscht werden.

MIA soll bei minimalen Funktionen und einem schlichten Design den größtmöglichen Informationsgehalt liefern. Das bedeutet, der User bekommt auch wirklich nur die Informationen zurück, die für ihn relevant sind. Viele Applikationen verfügen in der Regel über einen deutlich größeren Funktionsumfang, wovon der Anwender meist nur ein Bruchteil benötigt. 
Die Kommunikation der Daten definiert bereits den Informationsgehalt, durch die simple Anzeige von reinem Text. Dadurch, dass Bilder oder Icons nicht mit geladen werden, wird die Antwortzeit und die Größe der Datenpakete reduziert. Dabei sollten sowohl die Antwortzeit als auch das Ausführen der Abfrage so wenig Zeit wie möglich verbrauchen.

Bezogen auf das obige Szenario stellt sich die folgende Frage: Wann fährt die nächste Bahn ab Elmshorn?
Um dies herauszufinden müssen wir zunächst die DB App /Homepage suchen, öffnen und die Ladezeit ertragen. Dann geht es über das Bürger-Menü zum Reiter "Abfahrt/Ankunft".  Nach dem eingeben der Abfahrtstation und dem anschließenden Klick auf "Suchen", werden meistens die gewünschten Informationen angezeigt. Diese simple Abfrage dauert im Regelfall und bei einer WLAN-Verbindung ca. 20 Sekunden.

Als Basis Übertragungsmedium für unsere Services dient Telegram. Der Cloud-Messenger ist mit über 200 Millionen aktiven Usern eine bekannte WhatsApp Alternative. [^1] Die native Telegram App lässt sich auf  allen gängigen Plattformen installieren oder auf Wunsch auch als Web-App über [web.telegram.org](https://web.telegram.org/) aufrufen.  Eine Device Unabhängigkeit ist dementsprechend gewährleistet. 

Auch wenn Speicherplatzmangel eigentlich der Vergangenheit angehören sollte, gibt es immernoch User, die über Speichermangel auf dem Smartphone klagen. Der Telegram- Bot minimiert die Anzahl der notwendigen App Installationen. Die besondere Lightweight Variante sorgt zudem für einen aufgeräumten Screen und löst den App-Dschungel auf.


[^1]:https://telegram.org/blog/200-million
---
**technisch:**
--> Leon Job
Unsere Applikation basiert auf einer Micoroservice-Archiktekur. Die Vorteile gegenüber einem Monolithen spielen uns in die Hände. 
Wenn sich Apis ändern, dann funktioniert ein servie nicht --> Kann seperat angepasst werden + seperates DEPLOYMENT!!!! (vs. Monolith)
 -  hohe Verfügbarkeit --> Warum wichtig?
 -  hohe Resilienz 
 -  geringe Latenz -> schnelle Antwortzeiten 
 -  asynchrone Kommunikation per REST 
 -  hohe Erweiterbarkeit MSA
 -  hohe Skalierbarkeit MSA
 -  kleine, unabhängige self-contained-systems für einen abgeschlossenen, fachlichen Kontext pro Service 
 - wartbarkeit
 -  unabhängige Deployments (CI / CD) 
 -  hohe Anpassungsfähigkeit und nachhaltige Vereinfachung späterer Implementierungen und der einzelnen Services
 -  dezentrale Datenhaltung 
 - Architektur - Master Slave?
 
  ## Applikationsbeschreibung
  - Allgemein, das wir mit Json arbeiten (unsere Basis)
  - Rest API
  
  ### Applikationsausführung/Kommunikation
  - Stichwort "Domain Driven Design"? 
  ### Komponenten
Die Komponenten lassen sich in der Architekturskizze betrachten. 

**Telegram BOT**
Über BotFather lassen sich neue Bots erstellen, die in Telegram laufen[^11] Ein Bot ist ein besonderer Telegram Account, der ohne eine Telefonnummer funktioniert. Über Nachrichten und Befehle lässt sich der Bot steuern.[^10] 
[^10]:[https://core.telegram.org/api#bot-api](https://core.telegram.org/api#bot-api)
[^11]:[https://core.telegram.org/bots](https://core.telegram.org/bots)

**Distribution Service**
User Management, Profileservice /Start /Stop 
Anbindung an DB
--> Leon Job (Bild + Text)
**Service Wikipedia**
Wikipedia ist seit Jahren die beliebteste Anlaufstelle auf der Suche nach schnellen Informationen. Der Service akzeptiert einen Suchbegriff und gibt über MIA eine kurze Zusammenfassung mit Link der Wiki Seite an den User zurück, sofern die Seite existiert. Sollten die Informationen aus der Zusammenfassung nicht ausreichen, kann der Nutzer auf den Link gehen und erspart sich die Browser Navigation über die Suchmaschine zur Seite. Bei mehreren Treffern listet MIA alle gefunden Einträge auf. Findet MIA keinen Wikipediaeintrag für den Suchbegriff, gibt es einen Hinweis, dass der Suchbegriff zu keinem Eintrag führt.
**Service Nordbahn**
Viele Bahn Pendler nutzen die Nordbahn regelmäßig und viele davon sind Studenten der Nordakademie. Informationen zu den Zügen gibt es ausschließlich auf der Seite [nordbahn.de](https://nordbahn.de), da eine App nicht existiert. MIA schreibt dem Nutzer auf Anfrage, wann der nächste Zug für den gewünschten Bahnhof abfährt. Sie gibt des Weiteren Informationen über das Abfahrgleis, mögliche Verspätungen, Ausfälle und einen eingerichteten Ersatzverkehr.   
**Service Wetter**
--> Tims Job
**Service URL Shortener**
Lange URLs sind gerade im mobilen Zeitalter ein Dorn im Auge. Sei es in Kommentaren, auf Blocks oder in Social Media Kanälen. Lange Links auf kleinem Display sind sehr umständlich und schlecht lesbar. Um nicht selbst einen solch nervenaufreibenden Link zu posten, hat MIA einen URL Shortener Service, der die "GooLNK.com" - API benutzt und gewünschte URLs kürzt.
**Service Google Translator**
--> Seppels Job
  ### Architekturskizze
  --> Leon Job (BILD + Text))
## Technologien zur Anforderungserfüllung 
Die Open Source Containertechnik mit Docker ist 2013 veröffentlicht worden und eine attraktive Alternative zur beliebten Virtualisierung. Auf Windows, MAC, Linux und  vielen anderen Distributionen lässt sich Docker installieren.[^2] Hinter Docker steht die Technik der Containerisierung. Das Prinzip der Container ist recht simpel. Bei den großen Schiffcontainern (ISO-Container) gibt es bestimmte Standards wie z.B die Größe und die Festmachpunkte. Damit lässt sich der Container auf jedem beliebigen Containerschiff transportieren und im Hafen von Kränen auf Güterzuge oder LKWs verladen. Welchen Inhalt der Container hat, spielt zunächst kein Rolle. Darüber freut sich auch der Entwickler. Der Code inklusive aller Abhängigkeiten und Konfigurationen befindet sich in dem Container. Der Entwickler schafft mit einer installierten docker Version die Grundlage für alle Container und kann sich vollständig auf das entwickeln am Code fokussieren.
Container haben im Gegensatz zu dem Prinzip der Virtualisierung einen entscheidenen Vorteil: Es gibt nur ein Betriebssystem, auf dem alle Container laufen und deutlich weniger Ressourcen verwenden. Dadurch stehen mehr Performance und Speicher für die containerisierten Anwendungen zur Verfügung.[^4] Docker Container eigenen sich wunderbar für modere Software auf der Basis von Mikroservices.
In den Container können unsere Services isoliert und Unabhängig laufen. Ein Service kann jederzeit pausiert, bearbeitet oder deployed werden, ohne dass andere Services davon etwas mitbekommen oder beeinflusst werden.[^3]
Besonders angenehm sind die Container bei der Verwendung unterschiedlicher Programmiersprachen und Versionen. Unsere Services wurden überwiegend mit Java 11 gebaut. Die Wikipedia Anwendung ist allerdings ein Python 3.8 Projekt.  Dank der Container ist die Sprache und Version irrelevant, solange für die Kommunikation eine gemeinsame Schnittstelle existiert. Jeder Service bekommt die benötigten Pakete in seinem Container installiert. So bleibt das Hostsystem frei von jeglicher Softwareinstallation.

Unsere Services sollen jederzeit verfügbar sein, schnell skalieren und sich einfach managen lassen.
Kubernetes ist ein von Google entwickeltes Open Source System, das Container automatisiert verwaltet, bereitstellt und skaliert. MIA ist zur Zeit an eine kleinen Anzahl an Services angeschlossen und bei einer geringen Anzahl an Usern sind die Container noch einzeln zu überblicken.  Werden es +100 verschiedene Services, die stark skaliert werden müssen, hat kein Entwickler mehr alle Container im Überblick. Mit Container Orchestration werden alle Container überwacht und organisiert. Kubernetes benötigt allerdings die Informationen welche Container in welcher Anzahl laufen sollen.[^6]Fällt zum Beispiel ein Container aus, kümmert sich Kubernetes darum, die gewünschte Anzahl der Container  wiederherzustellen. 
Ein Load Balancer weiß welcher Service auf welchen und wie vielen Container läuft.  **Wie findet hier die Aufteilung statt???** Der Load Balancer verteilt die Lasten unter den laufenden Containern. Kubernetes sorgt für nahezu unendliche automatische Skalierbarkeit, hohe Ausfallsicherheit, gute Wartbarkeit und unabhängige Deployments.

Das Spring Boot Framework bringt viel hilfreiche Klassenbibliotheken für die Java Plattform mit. Der Zugriff auf die Datenbank läuft über Spring Data. Mit unseren Services, die auf Java 11 (LTS) und Python 3.8 laufen, sind wir auf aktuellen Versionen unterwegs.Der Python Service nutzt Flask als Lightweight Web Applikation Framework. Die Java Anwendungen werden mittels Junit 5 getestet.

[^6]: [https://kubernetes.io/de/](https://kubernetes.io/de/)
[^4]:[https://www.docker.com/resources/what-container](https://www.docker.com/resources/what-container)
[^3]: [https://aws.amazon.com/de/containers/](https://aws.amazon.com/de/containers/)
[^2]: [https://www.docker.com/resources/what-container](https://www.docker.com/resources/what-container)
  
### Anforderungserfüllung im Hinblick auf Skalierbarkeit / Erweiterbarkeit 
Auf Anforderungserfüllungen einzeln eingehen 
  
Skalierbarkeit: 
- einzelne Services in Dockercontainern beliebig viel hochfahren 
- viele Dockercontainer hochfahren und durch LoadBalancer (Kubernetes / Docker Compose) Traffic verteilen 
- Bottleneck: Telegram API zur Rückführung der Antworten an den Nutzer 
  
Erweiterbarkeit: 
Gute / leichte Erweiterbarkeit durch: 
- eigenen Service in belibiger Programmiersprache bauen 
- Service in einem Dockercontainer hochfahren 
- Service per Anleitung in den TelegramService einbinden 
  
### Offene Punkte und Verbesserungsmöglichkeiten 
MIA lässt sich zum aktuellen Zeitpunkt ausschließlich über die Textfeld Eingabemaske mit den von uns definierten Befehlen steuern. Außerdem kann der Nutzer MIA aktivieren und deaktivieren. Die Möglichkeit besteht bei den Services aktuell noch nicht. Für eine besser Übersicht der Funktionen und Einstellungen biete sich ein grafisches Web Admin Panel an. Hier hat der Benutzer zum einen die Möglichkeit seine Services zu konfigurieren und die Befehle seinen Wünschen entsprechend anzupassen. Außerdem ist es wünschenswert, ein kleines Dashboard mit Informationen über den bisherigen Datenverbrauch und Statistiken über die Anfragen an die Services anzubieten.

Viele Menschen haben durch ihren Job, ihre Kinder oder ihr Hobby geregelte Tagesabläufe. Das bedeutet im Fall von festen Arbeitszeiten, wird jeden Morgen der Zug um die gleiche Zeit gewählt wird. Für die passende Outfit Wahl könnte die aktuelle und zu erreichte Tagestemperatur interessant sein. Je nach Regenwahrscheinlichkeit wird der Schirm eingepackt. Der User sendet also jeden Werktag die gleiche Anfrage - um eine ähnliche Uhrzeit - an die Nordbahn und Wetter API. Routinen nehmen an der Stelle die Arbeit ab und eigenen sich wunderbar für das automatisierte Auszuführen von Abfragen. Routinen für die einzelnen Services lassen dann im Admin Panel einrichten und verwalten. 

Der Funktionsumfang von MIA ist mit 5 Anwendungen recht gering. Funktionen lassen sich dank der  gewählten Architektur Stück für Stück ergänzen. Die Anbindung über eine API ist besonders einfach implementiert und mit wenig Code möglich.
 
Der Translation Service übersetzt jede Sprache, die der Google Translator unterstützt. Momentan allerdings nur ins Deutsche. Wünschenswert ist demnach eine Erweiterung, mit der ein User die Zielsprache auswählen kann.
 
Die Kommunikation unter den kommunizierenden Services ist nicht über SSL verschlüsselt. Zwischen dem Distribution Service und der Telegram API werden personenbezogene Daten Übertragen. Darunter fallen unter anderm ChatID, Status (aktiv/deaktiviert), Vorname, Nachname und Benutzername. Eine Handynummer wird nicht übermittelt. Auch die Nachricht mit der Abfrage kann abgefangen werden. Bei den aktuellen Services sehen wir bei bewusster Anwendung noch kein extremes Sicherheitsrisiko. Über soziale Medien lassen sich deutlich mehr personenbezogene Informationen einfach abgreifen wie z.B. genaue Standorte mit Bild oder Bekannte Personen.

- Auf Telegram Buttons eingehen (bietet viele Möglichkeiten zur Kommunikation 
--> Leons Job
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
warum docker? 
unterschiedeliche Progammiersprachen (Java/python)
Microservices isoliert durch container
Unsere Services laufen in einzelnen Containern.

 ## Fazit
 --> Job ALLE!
 Kubernetes sehr mächtig
 wo waren die Probleme?
 was lief gut
 was war schwierig?
 Trotzdem ist MIA für uns eine Hilfe im Alltag