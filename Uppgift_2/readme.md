# Uppgift 2: API-testning: strategier och bästa praxis

| Data            | Värde                    |
|-----------------|--------------------------|
| **Namn**        | David Andreasson         |
| **Email**       | 79davand@gafe.molndal.se |
| **GitHub-länk** | https://github.com/david-andreasson/api-webservices-final-assignment_79davand/tree/main/Uppgift_2                         |

---

## 1. Beskrivning av uppgiften

(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)  
Detta projekt är ett enkelt miniräknar-API byggt med Spring Boot, där jag implementerar fyra endpoints som utför de grundläggande aritmetiska operationerna: addition, subtraktion, multiplikation och division.  
Syftet med uppgiften är att demonstrera hur man bygger ett REST‑API, hanterar fel på ett robust sätt med korrekta HTTP-statuskoder samt testar API:t både manuellt och med automatiserade tester med JUnit och Rest Assured.

### Beskriv skillnaden mellan enhetstestning, integrationstestning och end-to-end-testning. (VG)

(dina tankar här)  
Enhetstestning handlar om att testa enskilda delar av koden, som en metod eller en klass, helt isolerat.  
Syftet är att säkerställa att varje liten bit av logiken fungerar som den ska, utan att andra delar av systemet blandas in.  

I ett integrationstest testas hur olika komponenter fungerar tillsammans.  
Det innebär till exempel att man kontrollerar att controllern och servicen kommunicerar korrekt med varandra och att hela flödet från indata till utdata producerar korrekt resultat.  

End-to-end-testning testar hela systemet, från början till slut. Det innebär att man simulerar en verklig användarresa, från att en klient skickar en förfrågan, genom att API:t bearbetar förfrågan, och hela vägen till att man får tillbaka ett svar.  
Det ser till att alla lager i systemet fungerar ihop som de ska.

### Diskutera varför felhantering och statuskoder är avgörande för robusta API:er. (VG)

(dina tankar här)  
Felhantering och korrekta statuskoder är helt centrala för att ett API ska vara pålitligt.  
När något går fel ger de en tydlig signal till den som använder API:t om vad som är fel.  
Det innebär att om en klient får ett 400 (Bad Request) eller 404 (Not Found) så vet den exakt att något inte stämmer med anropet – till exempel att nödvändiga data saknas eller att en resurs inte finns.  

Detta gör det mycket enklare att felsöka och åtgärda problem.
Dessutom fungerar statuskoder som ett kontrakt mellan API:t och klienterna. Om API:t hela tiden returnerar korrekta statuskoder, kan utvecklare bygga in logik för att hantera olika fel, det kan till exempel vara att visa ett användarvänligt felmeddelande eller att försöka skicka om anropet.  

När fel hanteras på ett kontrollerat sätt minskar risken för att fel sprider sig och att hela systemet krashar.

## 2. Installation och körning

(Instruktioner för hur applikationen byggs och körs. Om externa verktyg krävs, beskriv hur dessa installeras och
används.)  

Klona repot från GitHub:  
```shell 
git clone https://github.com/david-andreasson/api-webservices-final-assignment_79davand.git
```
Efter att ha klonat repot, navigera in i projektmappen för uppgiften:

```code
cd api-webservices-final-assignment_79davand/Uppgift_2
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
Du kan också köra applikationen direkt från din IDE genom att köra main-metoden i Uppgift2Application.

Kör automatiserade tester: För att köra testerna, öppna en terminal i projektmappen och kör:

```shell
mvn test
```

## 3. Användning av API:et

(Beskrivning av API-endpoints med exempelanrop: HTTP-metoder, URL:er och exempel-payloads.)  

Nedan följer en beskrivning av mina API-endpoints, komplett med vilka HTTP-metoder som används, vilka URL:er som anropas samt exempel på payloads och respons:

POST /calculate/add  
Beskrivning: Denna endpoint adderar två tal.  
Payload:

```json
{ "num1": 5, "num2": 10 }
```
Respons:

```json
{ "result": 15 }
```
POST /calculate/subtract  
Beskrivning: Denna endpoint subtraherar det andra talet från det första.  
Payload:

```json
{ "num1": 10, "num2": 5 }
```
Respons:

```json
{ "result": 5 }
```
POST /calculate/multiply  
Beskrivning: Här multipliceras två tal.  
Payload:

```json
{ "num1": 4, "num2": 3 }
```
Respons:

```json
{ "result": 12 }
```
POST /calculate/divide  
Beskrivning: Denna endpoint dividerar det första talet med det andra.  
Payload:

```json
{ "num1": 10, "num2": 2 }
```
Respons:  
```json
{ "result": 5 }
```
Med dessa endpoints kan användare enkelt skicka in de tal de vill räkna med via JSON-payloads, och API:t svarar med ett JSON-objekt där resultatet av beräkningen returneras.  
Detta gör det enkelt att integrera och använda API:t, oavsett om man testar det med Postman, curl eller via automatiserade tester.

## 4. Felhantering

(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)  

I mitt API kan flera typer av fel uppstå, och jag har försökt hantera dem på ett tydligt och enkelt sätt:

Division med noll:
Om någon försöker dividera med noll kastar min CalculatorService ett IllegalArgumentException med meddelandet

```text
Division by zero is not allowed.
```
Detta fångas sedan av min GlobalExceptionHandler, som returnerar ett HTTP 400 Bad Request-svar med just detta meddelande. Det ger användaren en tydlig signal om att operationen inte är giltig.

Ogiltig eller saknad indata:
Om request body inte innehåller den förväntade JSON-strukturen – till exempel om användaren skickar in en felaktig datatyp eller glömmer nödvändiga fält – hanteras detta av Spring Boot:s inbyggda validering.  
Då returneras ett HTTP 400 Bad Request-svar, med en JSON-struktur som förklarar vilka fält som är fel eller saknas.
Exempelvis kan svaret se ut ungefär så här:

```json
{
"timestamp": "2025-03-15T10:47:01.974+00:00",
"status": 400,
"error": "Bad Request",
"message": "Required request body is missing or malformed."
}
```
Felaktig endpoint:
Om någon anropar en endpoint som inte existerar returneras ett 404 Not Found-svar. Detta är standardbeteende i Spring Boot, vilket gör det enkelt för klienten att veta att den begärda resursen inte finns.

## 5. Tester

(Beskriv hur API:et har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)  

API:t har testats på flera nivåer för att säkerställa att det fungerar som det ska:

- Manuell testning:
  Jag testade API:t med verktyg som Postman och curl. Exempelvis gjorde jag ett POST-anrop till /calculate/add med följande payload:

```json
{ "num1": 5, "num2": 10 }
```
och verifierade att svaret blev:

```json
{ "result": 15 }
```
Dessutom testade jag felaktiga scenarion, som division med noll, för att se till att API:t returnerar ett 400 Bad Request med meddelandet "Division by zero is not allowed."

- Enhetstester:
  Jag skrev enhetstester med JUnit för att testa CalculatorService direkt. Dessa tester säkerställer att varje matematisk operation (addition, subtraktion, multiplikation och division) ger förväntat resultat, och att division med noll kastar rätt undantag med korrekt meddelande.  


- Integrationstester:
  Med hjälp av Rest Assured i kombination med JUnit har jag skrivit integrationstester för CalculatorController.  
  Dessa tester anropar API-endpoints och verifierar att hela flödet, från att en request tas emot av controllern till att rätt svar returneras, fungerar som det ska.  

Automatiserade tester körs via Maven-kommandot:

```shell
mvn test
```
Detta ger mig snabb feedback vid kodändringar och säkerställer att API:t är robust. Testerna täcker både logiken i service-lagret och det övergripande flödet i controller-lagret.
## 6. Reflektion

- Vad har varit utmanande i uppgiften?  
  Precis som i förra uppgiften så var en av de största utmaningarna att balansera enkelheten i en MVP med att visa att jag kan implementera robust felhantering, tydlig dokumentation och automatiserade tester.  
  Att få integrationstesterna med Rest Assured att fungera smidigt tillsammans med Spring Boot och se till att alla lager (controller och service) kommunicerar som de ska var också en lärorik process. Där var jag tvungen att ta hjälp av ett gäng youtube-videos och kodexempel från Stack Overflow.  

- Vad skulle kunna förbättras?  
  Jag skulle kunna lägga till fler automatiserade tester, särskilt för att hantera fler fel, och edge-case-scenarier.  
  Till exempel hur API:et beter sig vid extremt stora tal eller andra oväntade indata. Dessa scenarier kan också vara värda att testa även om den grundläggande funktionaliteten redan är 100 % täckt.

- Eventuella lärdomar från implementationen.  
  Genom att göra denna uppgift har jag fått en djupare förståelse för vikten av att ha en tydlig struktur i koden, med en bra separation mellan controller, service och model.  
Jag lärde mig ganska snabbt att ett så enkelt fel som att lägga en mapp fel i mappstrukturen kan få katastrofala följder, med ett API som vägrar starta.   
Jag har också lärt mig hur kritiskt det är med ordentlig felhantering och att använda rätt HTTP-statuskoder, eftersom det underlättar felsökning och integration för så väl klienter som utvecklare och vanliga användare. 