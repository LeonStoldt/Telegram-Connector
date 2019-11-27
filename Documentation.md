
  
# Hausarbeit Cloud Computing   
> Gruppenmitglieder: Sebastian Lüders, Tim Rader, Leon Stoldt, Dominik Wilms    
 ## Einleitung    
 #### Einführung in den Kontext:  
Um das Projekt und den Nutzen am Besten zu präsentieren, gehen wir von folgender realistischen Situation aus:  
  
Sebastian steht in Tornesch am Bahnhof und wartet auf seinen Zug. Er nutzt einen günstigen Mobilfunktarif, der sein monatliches Datenvolumen auf wenige Hundert Megabyte begrenzt und bei dem der Provider nur in wenigen Orten gut ausgebaute Infrastruktur besitzt.

An diesem Tag sind durch eine Störung die Anzeigetafeln des Bahnhofs ausgefallen und der Zug scheint verspätet zu sein. Sebastian möchte sich über die Verspätung informieren und navigiert hierzu mit seinem Smartphone auf die Website der Nordbahn. Durch seine schlechte Internetverbindung lädt die Seite nur partiell und die Auskunft der Bahn zögert sich durch Laden von Blogs, Newseinträge etc. hinaus, obwohl es Sebastian überhaupt nicht interessiert. Die einzige Information, die er wissen möchte ist, wann sein nächster Zug in Tornesch abfährt. Sebastian ist genervt, dass er seine gewünschten Informationen nicht einfacher bekommen könnte.
Nachdem der Zug eine halbe Stunde später ankam, erhält Sebastian eine Textnachricht von seinem bevorstehenden Date. Sie fragt, ob sie sich im Kino oder auf dem Weihnachtsmarkt treffen wollen. Dafür möchte Sebastian einen Blick auf die Wettervorhersage werfen und er öffnet seine Wetter-App. Durch die schlechte Internetverbindung kann die App sich jedoch nicht synchronisieren, da eine konstante Internetverbindung fehlt. Nach ständigem Drücken des Synchronisieren-Buttons ist Sebastian nun endgültig genervt und denkt sich, wie schön es wäre, wenn er alle Informationen über eine einzige Anwendung abfragen könnte ohne ständig aktualisieren zu müssen und dabei noch Datenvolumen sparen würde.

Diese Situation soll der Chatbot lösen, indem er die Funktionen vereint und Alltagsprobleme, wie die beispielhaft geschilderten, löst. Hierbei entsteht durch die Anfrage und die Antwort (sobald Internet verfügbar ist) ein asynchroner Kommunikationsprozess, bei dem die Antwort nicht aktiv eingeholt werden muss. Dies spart viele Internetrequests pro App oder Website, da eine einzige Anfrage als Textnachricht gesendet wird und die Antwort mit den Informationen als simple Textnachricht selbst mit niedriger Internetverbindung empfangen werden kann. Somit spart sich der Benutzer auch Datenvolumen.

...

Dieses Cloud Computing Projekt ist ein Telegram-Bot, mit dem über die offizielle Telegram-Api kommuniziert werden kann.    
Der Bot reagiert eventgetrieben auf Nachrichten von Telegramnutzern und kann auf einen Pool von Services zurückgreifen, die die angefragten Informationen liefern.    
Diese Services sind jeweils an eine Api angebunden und liefern die Informationen über den Telegram Service zum Benutzer zurück.   

- mobil gut zu brauchen ---> auch bei schlechtem Internet bekomme ich schnell   
- Alles auf Textnachricht, keine Bilder die   
- geladen werden müssen  
- wenig Datenvolumen    

#### Problembeschreibung: - (evtl schon durch Fallbeispiel abgedeckt?)
- Probleme des Alltags erleichtern durch Applikationen, die ohnehin schon benutzt werden  
- Telegram Bot als Chatbot, da Nachrichten schreiben für jeden Alltag und kein großer Aufwand ist  
  
#### Anforderungsdefinition:  

**fachlich:**
Für unsere User soll die Anwendung vor allem eine einfache und schnelle Hilfe im Alltag sein. Nach dem Motto: "Eine App für Alles" hat der Benutzer die Möglichkeit über eine zentrale Application - in unserem Fall Telegram - sämtliche Abfragen zu tätigen, die Alltag erleichtern. 

Bsp. 
Minimalitisch: Ich bekomme nur wirklich das zurück was ich brauche. Die Apps verfügen in der Regel über mehr Funktionen. Der Anwender braucht häufig aber nur ein Bruchteil der verfügbaren Informationen.

Zeit sparen, weil ich die Abfragen schneller durchführen kann. -->
Bsp. Abfahrt ab Elmshorn. Hier müsste ich erst die DB App raussuchen, dann auf die Abfahrtseite navigieren, dann Elmshorn eingeben --> etc

Es geht wirklich darum, Aufgaben/Probleme, die häufig auftreten zu vereinfachen.
Ziel ist die kurze und präzise Beantwortung von Fragen bzw. Lieferung von Informationen an den anfragenden Nutzer  

**technisch:**
   -  hohe Verfügbarkeit  
   -  hohe Resilienz  
   -  geringe Latenz -> schnelle Antwortzeiten  
   -  asynchrone Kommunikation per REST  
   -  hohe Erweiterbarkeit  
   -  hohe Skalierbarkeit  
   -  kleine, unabhängige self-contained-systems für einen abgeschlossenen, fachlichen Kontext pro Service  
   -  unabhängige Deployments (CI / CD)  
   -  hohe Anpassungsfähigkeit und nachhaltige Vereinfachung späterer Implementierungen und der einzelnen Services
   -  dezentrale Datenhaltung  
   - Architektur - Master Slave?
  
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