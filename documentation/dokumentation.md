#Notes
UUID und Nickname trennen für Annonymisierung
Invoices sind mit öffnetlichen Schlüsseln verschlüsselt und müssen

Transaktionen (UUID[sender], UUID[receiver], amount, [cryptData])


#Kapitel 1: Einführung
##Übersicht über die Applikation
[Was macht die Applikation? Wie funktioniert sie? Welches Problem löst sie/welchen Zweck hat sie?]

##Wie startet man die Applikation?
[Wie startet man die Applikation? Welche Voraussetzungen werden benötigt? Schritt-für-SchrittAnleitung]

##Wie testet man die Applikation?
[Wie testet man die Applikation? Welche Voraussetzungen werden benötigt? Schritt-für-SchrittAnleitung]


#Kapitel 2: Clean Architecture
##Was ist Clean Architecture?
[allgemeine Beschreibung der Clean Architecture in eigenen Worten]

##Analyse der Dependency Rule
[(1 Klasse, die die Dependency Rule einhält und eine Klasse, die die Dependency Rule verletzt);
jeweils UML der Klasse und Analyse der Abhängigkeiten in beide Richtungen (d.h., von wem hängt die
Klasse ab und wer hängt von der Klasse ab) in Bezug auf die Dependency Rule]
###Positiv-Beispiel: Dependency Rule
###Negativ-Beispiel: Dependency Rule

##Analyse der Schichten
[jeweils 1 Klasse zu 2 unterschiedlichen Schichten der Clean-Architecture: jeweils UML der Klasse
(ggf. auch zusammenspielenden Klassen), Beschreibung der Aufgabe, Einordnung mit Begründung in
die Clean-Architecture]
###Schicht: [Name]
###Schicht: [Name]


#Kapitel 3: SOLID
##Analyse Single-Responsibility-Principle (SRP)
[jeweils eine Klasse als positives und negatives Beispiel für SRP; jeweils UML der Klasse und
Beschreibung der Aufgabe bzw. der Aufgaben und möglicher Lösungsweg des Negativ-Beispiels (inkl.
UML)]
###Positiv-Beispiel
###Negativ-Beispiel

##Analyse Open-Closed-Principle (OCP)
[jeweils eine Klasse als positives und negatives Beispiel für OCP; jeweils UML der Klasse und
Analyse mit Begründung, warum das OCP erfüllt/nicht erfüllt wurde – falls erfüllt: warum hier
sinnvoll/welches Problem gab es? Falls nicht erfüllt: wie könnte man es lösen (inkl. UML)?]
###Positiv-Beispiel
###Negativ-Beispiel

##Analyse Liskov-Substitution- (LSP), Interface-Segreggation- (ISP),

##Dependency-Inversion-Principle (DIP)
[jeweils eine Klasse als positives und negatives Beispiel für entweder LSP oder ISP oder DIP); jeweils
UML der Klasse und Begründung, warum man hier das Prinzip erfüllt/nicht erfüllt wird]
[Anm.: es darf nur ein Prinzip ausgewählt werden; es darf NICHT z.B. ein positives Beispiel für LSP
und ein negatives Beispiel für ISP genommen werden]
###Positiv-Beispiel
###Negativ-Beispiel


#Kapitel 4: Weitere Prinzipien
##Analyse GRASP: Geringe Kopplung
[jeweils eine bis jetzt noch nicht behandelte Klasse als positives und negatives Beispiel geringer
Kopplung; jeweils UML Diagramm mit zusammenspielenden Klassen, Aufgabenbeschreibung und
Begründung für die Umsetzung der geringen Kopplung bzw. Beschreibung, wie die Kopplung aufgelöst
werden kann]
###Positiv-Beispiel
###Negativ-Beispiel

##Analyse GRASP: Hohe Kohäsion
[eine Klasse als positives Beispiel hoher Kohäsion; UML Diagramm und Begründung, warum die
Kohäsion hoch ist]

##Don’t Repeat Yourself (DRY)
[ein Commit angeben, bei dem duplizierter Code/duplizierte Logik aufgelöst wurde; Code-Beispiele
(vorher/nachher); begründen und Auswirkung beschreiben]


#Kapitel 5: Unit Tests
##10 Unit Tests
[Nennung von 10 Unit-Tests und Beschreibung, was getestet wird]

Unit Test           |Beschreibung
-----               |----
| Klasse#Methode    |               | 
|                   |               |
|                   |               |
|                   |               |
|                   |               |
|                   |               |
|                   |               |
|                   |               |
|                   |               |
|                   |               |


##ATRIP: Automatic
[Begründung/Erläuterung, wie ‘Automatic’ realisiert wurde]

##ATRIP: Thorough
[jeweils 1 positives und negatives Beispiel zu ‘Thorough’; jeweils Code-Beispiel, Analyse und
Begründung, was professionell/nicht professionell ist]

##ATRIP: Professional
[jeweils 1 positives und negatives Beispiel zu ‘Professional’; jeweils Code-Beispiel, Analyse und
Begründung, was professionell/nicht professionell ist]

##Code Coverage
[Code Coverage im Projekt analysieren und begründen]

##Fakes und Mocks
[Analyse und Begründung des Einsatzes von 2 Fake/Mock-Objekten; zusätzlich jeweils UML
Diagramm der Klasse]

#Kapitel 6: Domain Driven Design
##Ubiquitous Language
[4 Beispiele für die Ubiquitous Language; jeweils Bezeichung, Bedeutung und kurze Begründung,
warum es zur Ubiquitous Language gehört]

Bezeichnung | Bedeutung | Begründung
----        | ----      | ----
|           |           |           |
|           |           |           |
|           |           |           |
|           |           |           |

##Entities
[UML, Beschreibung und Begründung des Einsatzes einer Entity; falls keine Entity vorhanden:
ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]

##Value Objects
[UML, Beschreibung und Begründung des Einsatzes eines Value Objects; falls kein Value Object
vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]

##Repositories
[UML, Beschreibung und Begründung des Einsatzes eines Repositories; falls kein Repository
vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]

##Aggregates
[UML, Beschreibung und Begründung des Einsatzes eines Aggregates; falls kein Aggregate vorhanden:
ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist]


#Kapitel 7: Refactoring
##Code Smells
[jeweils 1 Code-Beispiel zu 2 Code Smells aus der Vorlesung; jeweils Code-Beispiel und einen
möglichen Lösungsweg bzw. den genommen Lösungsweg beschreiben (inkl. (Pseudo-)Code)]

##2 Refactorings
[2 unterschiedliche Refactorings aus der Vorlesung anwenden, begründen, sowie UML vorher/nachher
liefern; jeweils auf die Commits verweisen]


#Kapitel 8: Entwurfsmuster
[2 unterschiedliche Entwurfsmuster aus der Vorlesung (oder nach Absprache auch andere) jeweils
sinnvoll einsetzen, begründen und UML-Diagramm]

Entwurfsmuster: Jan Pfenning
Entwurfsmuster: Yannic Hemmer