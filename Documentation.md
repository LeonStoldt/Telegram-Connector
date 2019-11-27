
# Hausarbeit Cloud Computing  

> Gruppenmitglieder: Sebastian Lüders, Tim Rader, Leon Stoldt, Dominik Wilms  
  
## Einleitung  
  
#### Einführung in den Kontext:
Dieses Cloud Computing Projekt ist ein Telegram-Bot, mit dem über die offizielle Telegram-Api kommuniziert werden kann.  
Der Bot reagiert eventgetrieben auf Nachrichten von Telegramnutzern und kann auf einen Pool von Services zurückgreifen, die die angefragten Informationen liefern.  
Diese Services sind jeweils an eine Api angebunden und liefern die Informationen über den Telegram Service zum Benutzer zurück. 

#### Problembeschreibung:
-	Probleme des Alltags erleichtern durch Applikationen, die ohnehin schon benutzt werden
-	Telegram Bot als Chatbot, da Nachrichten schreiben für jeden Alltag und kein großer Aufwand ist

#### Anforderungsdefinition:
-	Ziel ist die kurze und präzise Beantwortung von Fragen bzw. Lieferung von Informationen an den anfragenden Nutzer
-	Anforderungsdefinitionen sind konkret:
	-	hohe Verfügbarkeit
	-	hohe Resilienz
	-	geringe Latenz -> schnelle Antwortzeiten
	-	asynchrone Kommunikation per REST
	-	hohe Erweiterbarkeit
	-	hohe Skalierbarkeit
	-	kleine, unabhängige self-contained-systems für einen abgeschlossenen, fachlichen Kontext pro Service
	-	unabhängige Deployments (CI / CD)
	-	hohe Anpassungsfähigkeit und nachhaltige Vereinfachung späterer Implementierungen
	-	dezentrale Datenhaltung

## Technologien zur Anforderungserfüllung  
  
## Applikationsbeschreibung  
  
### Architekturbeschreibung und Kommunikation  

- Stichwort "Domain Driven Design"?  

### Anforderungserfüllung im Hinblick auf Skalierbarkeit / Erweiterbarkeit  

Auf Anforderungserfüllungen einzeln eingehen

Skalierbarkeit:
-	einzelne Services in Dockercontainern beliebig viel hochfahren
-	viele Dockercontainer hochfahren und durch LoadBalancer (Kubernetes / Docker Compose) Traffic verteilen
-	Bottleneck: Telegram API zur Rückführung der Antworten an den Nutzer **[Bleibt zu testen, ob multiple API-Connections möglich sind]**

Erweiterbarkeit:
Gute / leichte Erweiterbarkeit durch:
-	eigenen Service in belibiger Programmiersprache bauen
-	Service in einem Dockercontainer hochfahren
-	Service per Anleitung in den TelegramService einbinden
  
### Offene Punkte und Verbesserungsmöglichkeiten  

- SSL?  

## Ablauf der Softwareentwicklung  

Werkzeuge:
-	IntelliJ IDEA
-	Docker
-	DrawIO
-	Kubernetes / Docker Compose ?

Testing:
- Unit Tests
- Testsuite mit Testaccount und Service-Kommunikation Testen?

CI:
-	gitlab CI für einzelne Projekte
-	weitere CI? (Docker in CI einbinden?)
  
## Evaluation der Architektur  
  
## Fazit