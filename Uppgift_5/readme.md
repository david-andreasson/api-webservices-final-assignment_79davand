# Uppgift 5: Paketering och körning av Spring Boot-applikation

| Data            | Värde                                                                                             |
|-----------------|---------------------------------------------------------------------------------------------------|
| **Namn**        | David Andreasson                                                                                  |
| **Email**       | 79davand@gafe.molndal.se                                                                          |
| **GitHub-länk** | https://github.com/david-andreasson/api-webservices-final-assignment_79davand/tree/main/Uppgift_5 |

---

## 1. Beskrivning av uppgiften

(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)

Detta projekt är en minimal implementering av en Melodifestival-omröstning, där användare kan skicka in röster och se aktuella resultat via ett enkelt REST-API. Syftet är att demonstrera hur man kan bygga, paketera och köra en fristående Spring Boot-applikation som en JAR-fil, med fokus på snabb utveckling och enkelhet.

# Förklara skillnaden mellan en **fat JAR** (som inkluderar alla beroenden) och en vanlig JAR. (VG)

(dina tankar här)

En fat JAR (även kallad uber-JAR) är en JAR-fil som inte bara innehåller applikationens egna klasser, utan även alla externa beroenden som applikationen behöver för att köras.  
Detta innebär att man får en självständig, körbar fil som kan distribueras och exekveras utan att extra bibliotek eller konfiguration krävs vid körning.  
Det är särskilt användbart för microservices eller miljöer där enkel distribution är ett krav.

En vanlig JAR (thin JAR) däremot innehåller enbart den kompilerade koden för applikationen, utan att inkludera beroenden som behövs.  
Vid körning måste dessa beroenden hanteras separat, till exempel genom att ställa in en classpath eller använda ett beroendehanteringssystem som till exempel Maven.  
Detta kan ge en mer modulär struktur och mindre filstorlek, men kräver att miljön där applikationen körs har rätt bibliotek tillgängliga.  

- Fat JAR:  
Inkluderar både applikationskod och alla nödvändiga beroenden.  
Är självständig och enkel att distribuera (en enda fil).  
Har större filstorlek och kan innehålla duplicerad kod om samma bibliotek används av flera applikationer.  

- Vanlig JAR:  
Innehåller endast applikationskod.  
Kräver att beroenden hanteras och distribueras separat vid körning.  
Resulterar ofta i mindre filer och en mer modulär hantering av bibliotek, men med en mer komplex distributionsprocess.  

Denna skillnad påverkar hur man väljer att paketera och distribuera en applikation. Fat JAR-filer förenklar deployment genom att minska antalet filer som behöver hanteras, medan vanliga JAR-filer ger större flexibilitet i hanteringen av beroenden, men kräver en mer noggrann miljökonfiguration.  

### Beskriv olika sätt att distribuera en Spring Boot-applikation utan Docker. (VG)

(dina tankar här)
Här är fyra exempel på sätt att distribuera en Spring Boot-applikation utan Docker:

- Fat JAR:
Man packar allt – både koden och alla beroenden – i en enda körbar JAR-fil. Det gör att man bara behöver köra java -jar app.jar och allt funkar direkt, utan att man behöver hantera extra filer.

- Thin JAR med externt hanterade beroenden:
Här packar man bara sin applikationskod i JAR-filen och hanterar beroendena separat, till exempel via classpath. Resultatet blir en mindre JAR, men man måste se till att alla nödvändiga bibliotek är tillgängliga i den miljö där man kör applikationen.

- PaaS (Platform as a Service):
Ett annat alternativ är att ladda upp sin JAR-fil till en tjänst som Heroku och Render. Dessa plattformar tar hand om allt från skalning till loggning, vilket gör att man slipper oroa sig för infrastrukturen.
Detta är nästan samma sätt som jag kör Quiztjänsten på. Jag har kopplat Render till mitt Github-repo, så när jag gjort en ändring i koden så går jag till min dashboard på Render och klickar på "Clear build cache and deploy". Detta bygger en ny version av min applikation och delar ut den online.

- WAR-förpackning för servlet-containrar:
Ett annat alternativ är att förpacka applikationen som en WAR-fil om man vill deploya den i en traditionell servlet-container, som Tomcat eller Jetty. Då lägger man in WAR-filen i containern och kör den därifrån.

### Demonstrera hur miljövariabler kan användas för att hantera olika driftsmiljöer. (VG)

(dina tankar här)

I spring boot finns en inbyggd funktion som heter Spring Profiles. Med den kan man skapa olika konfigurationsfiler för olika miljöer, till exempel en för utveckling och en för produktion.  
Genom att sätta miljövariabeln SPRING_PROFILES_ACTIVE bestämmer du vilken profil som ska användas, så att applikationen automatiskt laddar rätt inställningar för den miljön. Det är ett väldigt smidigt sätt att hantera olika driftsmiljöer utan att behöva ändra massor i koden.  
Om du sätter den till "dev" så laddas utvecklingsinställningarna (till exempel enklare databaskonfiguration, mer detaljerad loggning), medan "prod" laddar produktionsinställningar (till exempel säkrare databaskonfiguration, lägre loggnivåer).

Man kan även använda andra miljövariabler för att ställa in specifika inställningar, som API-nyckelar eller andra konfigurationer. Detta gör att man kan ändra applikationens beteende utan att röra koden – perfekt för att snabbt anpassa konfigurationen beroende på var applikationen körs.

## 2. Installation och körning

(Instruktioner för hur applikationen byggs och körs. Om externa verktyg krävs, beskriv hur dessa installeras och används.)

Klona repot från GitHub:
```shell 
git clone https://github.com/david-andreasson/api-webservices-final-assignment_79davand.git
```
Efter att ha klonat repot, navigera in i projektmappen för uppgiften:

```code
cd api-webservices-final-assignment_79davand/Uppgift_5
```

Se till att Java 21 och Maven är installerat på din dator.
<details><summary>Installation av Java 21 och Maven <------------- Klicka här för installationsanvisningar</summary>

### Installera Java 21

**Windows:**
1. Gå till [Oracle Java 21 download page](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) eller använd en öppen källkodsversion som [Eclipse Temurin](https://adoptium.net/).
2. Ladda ner rätt installerare för ditt system.
3. Kör installeraren och följ anvisningarna.
4. Ställ in miljövariabeln `JAVA_HOME` till installationsmappen och lägg till `%JAVA_HOME%\bin` i din PATH.
5. Verifiera installationen med kommandot:
   ```code
   java -version
   ```
**macOS:**
Använd Homebrew:

```code
brew install openjdk@21
```
Följ Homebrew-anvisningarna för att ställa in JAVA_HOME och uppdatera din PATH.
Verifiera installationen:
```code
java -version
```
Ubuntu/Debian:
Uppdatera paketlistan:
```code
sudo apt-get update
```
Installera Java 21:
```code
sudo apt-get install openjdk-21-jdk
```
Verifiera installationen:
```code
java -version  
```
## Installera Maven
Windows:
Ladda ner den senaste Maven-binära zip-filen från Apache Maven.
Packa upp zip-filen till en mapp du väljer.
Ställ in miljövariabeln MAVEN_HOME till den uppackade mappen och lägg till %MAVEN_HOME%\bin i din PATH.
Verifiera installationen:
```code
mvn -version
```
macOS:

Installera Maven med Homebrew:
```code
brew install maven
```
Verifiera installationen:
```code
mvn -version
```
Ubuntu/Debian:

Uppdatera paketlistan:
```code
sudo apt-get update
```
Installera Maven:
```code
sudo apt-get install maven
```
Verifiera installationen:
```code
mvn -version
```
</details> 

Bygg projektet med Maven:
Öppna en terminal i projektmappen och kör:
```shell 
mvn clean install
```
Starta applikationen:
Kör:
```shell 
mvn spring-boot:run
```
Applikationen startar på port 8080 som standard.  
Du kan också köra applikationen direkt från din IDE genom att köra main-metoden i Uppgift5Application.

Kör automatiserade tester (valfritt): Om du vill verifiera att allt fungerar korrekt kan du öppna en terminal i projektmappen och köra:
```shell
mvn test
```

### Bygga och köra JAR-filen:

```sh
mvn clean package
java -jar target/Uppgift_5-0.0.1-SNAPSHOT.jar
```

## 3. Användning av API:et

API:et består av två huvud-endpoints:  

POST /vote  
Används för att lägga till en röst. Vid ett anrop skickar du en JSON-body med en nyckel "contestant", till exempel:  

```json
{ "contestant": "Artist A" }
```
Svaret blir en bekräftelse med ett meddelande, till exempel.:  

```json
{ "message": "Vote registered" }
```
GET /results  
Denna endpoint hämtar de aktuella röstresultaten. Svaret är en JSON som visar varje artists namn tillsammans med antalet röster, till exempel:  

```json
{ "results": { "Artist A": 120, "Artist B": 80 } }
```

## 4. Felhantering

(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)  

I den här MVP:n förlitar jag mig på Spring Boots standardfelhantering. Det innebär att:  
Om någon gör ett felaktigt anrop, till exempel genom att skicka in en POST-förfrågan utan att ange nyckeln "contestant", returneras ett 400 Bad Request med ett standardmeddelande som beskriver problemet.  
Om någon försöker anropa en endpoint som inte finns, hanteras det automatiskt med ett 404 Not Found-svar.  
Vid oväntade interna fel skickas ett 500 Internal Server Error, där Spring Boot genererar ett JSON-svar med felmeddelande.  
I en mer utvecklad version skulle man kunna införa specifika @ExceptionHandler-metoder för att få mer detaljerade felmeddelanden, men för denna super-enkla MVP är den inbyggda felhanteringen helt tillräcklig.  

## 5. Tester

(Beskriv hur API:et har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)  

Jag har bara testat API:et manuellt med Postman. Till exempel:  
Vid ett POST-anrop till /vote skickade jag in en JSON-body som { "contestant": "Artist A" } och verifierade att svaret innehöll { "message": "Röst registrerad" }.  
Vid ett GET-anrop till /results kontrollerade jag att röstresultaten uppdaterades korrekt.  
Utöver de manuella testerna finns ett automatiserat test, Uppgift5ApplicationTests, som bara kontrollerar att applikationskontexten laddas utan problem.   
Detta test kan köras med kommandot:  

```shell
mvn test
```
Detta ger en grundläggande säkerhet att applikationen startar som den ska, medan de manuella testerna bekräftar att API-endpoints fungerar enligt förväntan.  

## 6. Reflektion

-   Vad har varit utmanande i uppgiften?
    Denna var inte utmanande. Det är att skriva all dokumentationen som är utmanande =)

-   Vad skulle kunna förbättras?
    Oj, det är massor. Applikationen skulle kunnas byggas ut åt alla möjliga håll. 

- Implementera en mer detaljerad felhantering med specifika undantag istället för att enbart lita på standardfel från Spring Boot.
- Använda en riktig databas istället för in-memory lagring för att göra applikationen mer robust och skalbar.
- Införa loggning för att enklare kunna felsöka och övervaka applikationen i en produktionsmiljö.
- Implementera en säkerhetslösning (t.ex. JWT) om applikationen skulle växa och hantera känslig data, eller så att samma person inte kan lägga 8000 röster.

-   Eventuella lärdomar från implementationen.
    Jag insåg hur enkelt Spring Boot gör det att snabbt få en fristående applikation att köra, och hur praktiskt det är att testa API:et med verktyg som Postman.
