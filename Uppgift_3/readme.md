# Uppgift 3: Implementera en enkel SOAP-webservice

| Data            | Värde                                                                                             |
|-----------------|---------------------------------------------------------------------------------------------------|
| **Namn**        | David Andreasson                                                                                  |
| **Email**       | 79davand@gafe.molndal.se                                                                          |
| **GitHub-länk** | https://github.com/david-andreasson/api-webservices-final-assignment_79davand/tree/main/Uppgift_3 |

---

## 1. Beskrivning av uppgiften

(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)

Detta projekt går ut på att implementera en SOAP-webbtjänst för att hantera en lista med telefonmodeller med CRUD-operationer.  
Syftet med uppgiften är att visa hur man bygger en SOAP-tjänst med Spring Boot, där man kan använda ett XSD-schema tillsammans med JAXB för att generera de nödvändiga klasserna.  
Genom att arbeta med denna uppgift får jag praktisk erfarenhet av att skapa SOAP-endpoints, konfigurera WSDL-generering samt hantera XML-bindning.

## Förklara skillnaden mellan SOAP och REST samt när SOAP fortfarande är relevant. (VG)

(dina tankar här)

SOAP (Simple Object Access Protocol) är ett standardiserat protokoll för att utbyta XML-meddelanden mellan system.  
Det använder en strikt struktur med ett definierat WSDL (Web Services Description Language) kontrakt, vilket gör att både meddelandestruktur och felhantering hanteras på ett standardiserat sätt.  
SOAP är protokolloberoende och erbjuder inbyggda säkerhetsfunktioner samt transaktionsstöd.

REST (Representational State Transfer) är däremot en arkitekturstil som bygger direkt på HTTP:s metoder (GET, POST, PUT, DELETE, osv) och använder ofta JSON eller XML som meddelandeformat.  
REST är enklare, mer lättviktigt och enklare att implementera och skala, vilket gör det till det vanligaste valet för moderna webb-API:er.

SOAP är fortfarande relevant i sammanhang där du behöver ett starkt, kontraktsbaserat gränssnitt med avancerade säkerhets- och transaktionskrav.  

Till exempel:  
    Enterprise-integration: Stora organisationer med komplexa systemlandskap, där robust säkerhet, transaktionshantering och standardiserade felhanteringsmekanismer är avgörande.  
    Legacy-system: Många äldre system bygger på SOAP, så det kan vara nödvändigt att använda SOAP för att kommunicera med dessa system.  
    Finansiella och hälsosektorer: Där hög säkerhet, pålitlighet och strikt uppfyllelse av standarder är ett måste.   

Medan REST är det populäraste och enklaste valet för många moderna applikationer, erbjuder SOAP pålitliga funktioner som gör det oumbärligt i miljöer där säkerhet, transaktioner och formella kontrakt är kritiska.  

### Beskriv hur en klient skulle anropa tjänsten med XML-baserade anrop. (VG)

(dina tankar här)

Exempel på hur en klient anropar tjänsten med XML-baserade anrop:  

Skapa ett SOAP-meddelande: Klienten konstruerar ett XML-baserat meddelande som följer SOAP-standarden. Ett exempel för att hämta en telefonmodell (GetPhoneModelRequest) kan se ut så här:  
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
xmlns:ph="http://example.org/phonemodels">
<soapenv:Header/>
<soapenv:Body>
<ph:GetPhoneModelRequest>
<ph:id>1</ph:id>
</ph:GetPhoneModelRequest>
</soapenv:Body>
</soapenv:Envelope>
```
Lägga till Basic Authentication: För att autentisera sig skickar klienten inloggningsuppgifter via HTTP-huvudet. I ett verktyg som Postman ställer du in Basic Auth med ditt användarnamn och lösenord (t.ex. soapuser och soappassword).  
Med cURL ser det ut så här:
```code
curl -X POST http://localhost:8080/ws \
-H "Content-Type: text/xml" \
-u soapuser:soappassword \
-d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ph="http://example.org/phonemodels">
<soapenv:Header/>
<soapenv:Body>
<ph:GetPhoneModelRequest>
<ph:id>1</ph:id>
</ph:GetPhoneModelRequest>
</soapenv:Body>
</soapenv:Envelope>'
```
Serverns svar: Om anropet lyckas returnerar tjänsten ett SOAP-svar med data i XML-format. Ett exempel på ett svar kan vara:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
xmlns:ph="http://example.org/phonemodels">
<soapenv:Body>
<ph:GetPhoneModelResponse>
<ph:phoneModel>
<ph:id>1</ph:id>
<ph:modelName>Nokia 3210</ph:modelName>
<ph:manufacturer>Nokia</ph:manufacturer>
<ph:releaseYear>1999</ph:releaseYear>
</ph:phoneModel>
</ph:GetPhoneModelResponse>
</soapenv:Body>
</soapenv:Envelope>
```
Sammanfattningsvis:

    - Klienten skapar ett XML-baserat SOAP-meddelande med rätt envelope, header och body.
    - Inloggningsuppgifter skickas via Basic Auth i HTTP-huvudet.
    - Servern bearbetar meddelandet och returnerar ett XML-svar enligt den definierade WSDL och XSD.

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

Kör automatiserade tester (valfritt): Om du vill verifiera att allt fungerar korrekt kan du öppna en terminal i projektmappen och köra:  
```shell
mvn test
```

## 3. Användning av API:et

(Beskrivning av SOAP-tjänstens funktionalitet och XML-struktur för anrop.)

SOAP‑tjänsten i detta projekt erbjuder funktionalitet för att hämta och skapa telefonmodeller. I den implementerade tjänsten används ett unikt id för att identifiera varje telefonmodell. I vårt in-memory-repository finns exempelvis telefonmodellerna "Nokia 3210" (id 1), "Nokia 3310" (id 2) och "Ericsson T28" (id 3).

**Exempel på SOAP-anrop**  
Begäran:  
För att hämta information om telefonmodellen "Nokia 3210" (med id 1) skickas följande XML-baserade begäran:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tel="http://example.org/phonemodels">
<soapenv:Header/>
<soapenv:Body>
<tel:GetPhoneModelRequest>
<tel:id>1</tel:id>
</tel:GetPhoneModelRequest>
</soapenv:Body>
</soapenv:Envelope>
```
Svar:  
Om telefonmodellen hittas, returnerar tjänsten ett SOAP-svar med detaljerad information:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
<soapenv:Body>
<tel:GetPhoneModelResponse xmlns:tel="http://example.org/phonemodels">
<tel:phoneModel>
<tel:id>1</tel:id>
<tel:modelName>Nokia 3210</tel:modelName>
<tel:manufacturer>Nokia</tel:manufacturer>
<tel:releaseYear>1999</tel:releaseYear>
</tel:phoneModel>
</tel:GetPhoneModelResponse>
</soapenv:Body>
</soapenv:Envelope>
```

## 4. Felhantering

(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)  

I den här SOAP-tjänsten har vi endast implementerat en grundläggande felhantering.  
Validering av indata och hantering av ogiltiga förfrågningar:
Om en klient begär en telefonmodell med ett id som inte finns i vårt in-memory-repository, till exempel om någon skickar en förfrågan med id som inte matchar någon modell, så hanterar applikationen detta genom att returnera ett svar med fördefinierade standardvärden.  
Exempelvis:
```text
id: -1
modelName: "NotFound"
manufacturer: "N/A"
releaseYear: 0
```
Detta gör att klienten tydligt ser att ingen giltig data hittades, istället för att tjänsten skulle ge ett tomt svar eller krascha.

## 5. Tester

(Beskriv hur SOAP-tjänsten har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)

SOAP‑tjänsten har testats manuellt med Postman. Jag skapade en request i Postman där jag angav SOAP‑XML som body och satte "Content-Type" till "text/xml".  
Genom att skicka olika SOAP‑anrop, till exempel ett anrop med ett giltigt id för att hämta en telefonmodell, samt ett anrop med ett ogiltigt id för att testa felhantering, kunde jag verifiera att tjänsten svarade enligt specifikationerna.  

Exempel med Postman:  
Öppna Postman och skapa ett nytt request.  
Välj metoden POST och ange URL: http://localhost:8080/ws  
I fliken "Body", välj "raw" och ställ in formatet till "Text" (eller "XML").  
Klistra in följande exempel-SOAP-meddelande för att hämta en telefonmodell:  
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pho="http://example.org/phonemodels">
<soapenv:Header/>
<soapenv:Body>
<pho:GetPhoneModelRequest>
<pho:id>1</pho:id>
</pho:GetPhoneModelRequest>
</soapenv:Body>
</soapenv:Envelope>
```
Skicka anropet och kontrollera att svaret innehåller telefonmodellens data som förväntat, till exempel med data för "Nokia 3210".  
För ogiltiga id returneras ett svar med fördefinierade värden (t.ex. "NotFound").  
Genom att köra liknande anrop för att testa alla endpoints, inklusive att skapa en ny telefonmodell, säkerställde jag att tjänsten fungerar enligt kraven.  
Automatiserade tester körs med Maven-kommandot mvn test för att kontrollera att applikationen startar korrekt och att den övergripande kontexten laddas utan fel.  

## 6. Reflektion

- Vad har varit utmanande i uppgiften?  
  Uppgiften var ganska utmanande eftersom vi bara har haft en lektion om SOAP. Jag behövde därför luta mig mycket på exempelkod, dokumentation och fråga ChatGPT om hjälp för att förstå koncepten och implementera tjänsten korrekt.  


- Vad skulle kunna förbättras?  
  Det skulle vara bra att utöka tjänsten med fler CRUD-operationer och lägga till en mer avancerad felhantering. Med fler funktioner och robustare felhantering hade tjänsten kunnat bli ännu mer komplett och användbar i ett verkligt scenario.  


- Eventuella lärdomar från implementationen.  
  En viktig lärdom är att även om SOAP har sina fördelar (särskilt inom områden som kräver starka kontrakt och WS-Security), så tycker jag att REST-mönstret är betydligt enklare att följa och implementera. Det är en bra påminnelse om att välja rätt verktyg för rätt ändamål, beroende på vilka krav och erfarenheter man har.