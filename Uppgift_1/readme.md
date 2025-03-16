# Uppgift 1: Implementera Swagger i ett Spring Boot-projekt

| Data            | Värde                    |
|-----------------|--------------------------|
| **Namn**        | David Andreasson         |
| **Email**       | 79davand@gafe.molndal.se |
| **GitHub-länk** | https://github.com/david-andreasson/api-webservices-final-assignment_79davand|

---

## 1. Beskrivning av uppgiften  
*(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)*

Det här projektet är ett enkelt Spring Boot‑API för att hantera en bokförteckning med sökfunktion.  
API‑et erbjuder endpoints för att hämta en lista med böcker, hämta en enskild bok baserat på dess id samt att skapa en ny bok.  
Syftet med uppgiften är att demonstrera hur man kan implementera och dokumentera ett REST‑API med Swagger/OpenAPI.  

### Förklara varför API-dokumentation är viktig för både utvecklare och beställare. (VG)

API‑dokumentation är väldigt viktig eftersom:

- För utvecklare: Den fungerar som en bruksanvisning. Utvecklare kan se exakt vilka endpoints som finns, vilka parametrar som behövs, vilka HTTP-metoder som ska användas och vilket format svaren kommer i. Det minskar fel och underlättar vidareutveckling och integration.
- För beställare/användare: Den visar exakt vad API‑et kan göra och vilka data som skickas och tas emot. Detta underlättar kommunikation mellan tekniska och icke-tekniska människor och säkerställer att beställarnas/användarnas krav och förväntningar möts.  

### Reflektera över hur Swagger kan integreras i en CI/CD-pipeline. (VG)

Swagger kan integreras i CI/CD‑pipelines tex genom att:

Automatiskt generera dokumentationen: Vid varje commit eller build så körs ett steg som genererar den senaste Swagger‑specifikationen baserat på dom aktuella annoteringarna i koden.  
Validera dokumentationen: Automatiska tester kan jämföra den genererade dokumentationen med förväntade värden för att säkerställa att API‑ändringar inte orsakar avvikelser i dokumentationen.   
Publicera dokumentationen: Efter en lyckad build kan dokumentationen publiceras till en server eller till ett internt verktyg / hemsida så att den alltid är uppdaterad och tillgänglig för utvecklare, kunder eller vanliga användare.  

### Beskriv hur dokumentationen kan hållas uppdaterad vid API-förändringar. (VG)

Dokumentationen kan hållas uppdaterad genom att:  
- Man använder annoteringar direkt i koden: Eftersom Swagger/OpenAPI genererar dokumentationen baserat på annoteringar, är den alltid i synk med koden. När du ändrar dina endpoints uppdateras dokumentationen automatiskt.
- Regelbundna kodgranskningar. Under kodgranskningar kan man kontrollera att annoteringarna stämmer överens med funktionaliteten.
- Genom att skriva tester som kontrollerar att den genererade Swagger‑specifikationen är korrekt, kan man säkerställa att dokumentationen är korrekt. Detta är dock inget som jag har testat att göra.

## 2. Installation och körning
*(Instruktioner för hur applikationen byggs och körs. Om externa verktyg krävs, beskriv hur dessa installeras och används.)*  

Klona repot från GitHub:  
Exempel:
```shell 
git clone https://github.com/david-andreasson/api-webservices-final-assignment_79davand.git
```
Efter att ha klonat repot, navigera in i projektmappen för uppgiften:

```code
cd api-webservices-final-assignment_79davand/Uppgift_1
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
Öppna Swagger UI:
Navigera med din webläsare till:
```text 

http://localhost:8080/swagger-ui/index.html
```
för att se och testa API‑dokumentationen.


## 3. Användning av API:et
*(Beskrivning av API-endpoints med exempelanrop: HTTP-metoder, URL:er och exempel-payloads.)*  

Exempel på endpoints:  

**GET /books**  
- Beskrivning: Hämtar en lista med alla böcker.  
- Exempelanrop: 
    - HTTP-metod: GET  
    - URL: http://localhost:8080/books  
- Förväntad respons:
```json
{
"data": [
{
"id": 1,
"title": "Neuromancer",
"author": "William Gibson",
"publishedYear": 1984
},
{
"id": 2,
"title": "Count Zero",
"author": "William Gibson",
"publishedYear": 1986
}
]
}
```
...och så vidare


**GET /books/{id}**  
- Beskrivning: Hämtar en bok baserat på det angivna id:t.   
- Exempelanrop: 
    - HTTP-metod: GET  
    - URL: http://localhost:8080/books/2  
- Förväntad respons om boken hittas (200 OK):
```json
{
"id": 2,
"title": "Count Zero",
"author": "William Gibson",
"publishedYear": 1986
}
```
Exakt felmeddelande om boken inte hittas (404 Not Found):
```text
Book not found with id: 2
```
**POST /books**  
- Beskrivning: Lägger till en ny bok.  
- Exempelanrop: 
    - HTTP-metod: POST  
    - URL: http://localhost:8080/books  
- Payload (JSON):
```json

{
"title": "Test Book",
"author": "Author Name",
"publishedYear": 2020
}
```
Förväntad respons (201 Created):
```json

{
"id": 21,
"title": "Test Book",
"author": "Author Name",
"publishedYear": 2020
}
```

## 4. Felhantering
*(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)*  

- GET /books/{id}:
  - Om en bok med det angivna id:t inte finns, returneras ett 404 Not Found-svar med följande exakta meddelande i kroppens innehåll:  
    
```text
Book not found with id: {id}
```

- Exempel: Vid anropet GET /books/55 där ingen bok med id 55 finns, blir svaret:  

```text
Book not found with id: 55
```

- POST /books:
  - Om indata inte uppfyller valideringskraven (t.ex. om title eller author är för kort eller saknas), returneras 400 Bad Request med detaljerade felmeddelanden som beskriver exakt vilka fält som är fel.  



## 5. Tester  
*(Beskriv hur API:et har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)*

API:t har testats manuellt med Postman för att verifiera att:

- GET-anrop returnerar rätt lista med böcker.
- GET med ett specifikt id returnerar rätt bok (200 OK) eller ett felmeddelande (404 Not Found).
- POST-anropet fungerar som tänkt med korrekt validering (om indata är felaktig returneras 400 Bad Request, annars 201 Created med den nya boken).  

## 6. Reflektion
  - *Vad har varit utmanande i uppgiften?*  
    Att balansera mellan en enkel MVP och att visa att man kan hantera validering, felhantering och dokumentation var en utmaning. Man vill liksom alltid göra lite mer och lite mer när man kommer in i ett flow =)  

  - *Vad skulle kunna förbättras?*  
     Det finns ju mycket som skulle kunna läggas till här, tex automatiserade tester, mer felhantering, validation osv. 
     Man skulle kunna lägga till HATEOS, för att göra så att varje bok i svaret man får från API:et innehåller länkar för att uppdatera eller radera boken man frågat efter.

  - *Eventuella lärdomar från implementationen.*  
    Jag tycker att jag har fått mer förståelse för hur viktigt det är med tydlig API‑dokumentation – både för utvecklare och beställare. (Även om jag själv tycker det är ganska trist med dokumentation =P) 
    Jag tänker att projektet visar också hur enkelt det är att integrera Swagger med Spring Boot och hur detta kan bli en naturlig del av en CI/CD‑pipeline för att vara säker på att dokumentationen alltid är aktuell och korrekt.
