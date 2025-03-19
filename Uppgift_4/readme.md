
| Data            | Värde                                                                                             |
|-----------------|---------------------------------------------------------------------------------------------------|
| **Namn**        | David Andreasson                                                                                  |
| **Email**       | 79davand@gafe.molndal.se                                                                          |
| **GitHub-länk** | https://github.com/david-andreasson/api-webservices-final-assignment_79davand/tree/main/Uppgift_4 |

## 1. Beskrivning av uppgiften

(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)

Detta projekt är att implementera en enkel GraphQL-server för hantering av sportresultat.  
Uppgiften demonstrerar hur GraphQL kan användas för att minska onödig datalast jämfört med REST, samt hur man kan implementera grundläggande CRUD-operationer, felhantering och enkel autentisering med Spring Security.   
Projektet är utformat enligt MVP-principer med mockad data för att snabbt kunna verifiera funktionaliteten via t.ex. Postman.

### Förklara skillnaden mellan GraphQL och REST. (VG)

*GraphQL:*  
 - Använder en enda endpoint där klienten specificerar exakt vilka fält den behöver.  
 - Minskar onödig datalast genom att endast returnera de specificerade fälten.  
 - Har ett starkt typat schema vilket underlättar både utveckling och felsökning.  
 - Möjliggör komplexa förfrågningar (t.ex. att hämta relaterad data i en enda anrop).  

*REST:*    
 - Flera endpoints, var och en returnerar fördefinierade datastrukturer.  
 - Risk för över-fetching eller under-fetching, då svaret inte alltid matchar exakt vad klienten behöver.  
 - Mindre flexibilitet i klientens förfrågningar jämfört med GraphQL.  


### Demonstrera hur man kan optimera queries för att minska onödig datalast. (VG)

GraphQL optimerar datalasten genom att klienten väljer vilka fält som ska returneras.  
Några tekniker för att undvika onödig datalast är:  
 - Selektiv datahämtning: Klienten begär bara de fält den behöver, vilket minskar storleken på svaren.  
 - Batched resolvers och DataLoader: Vid relationella data (t.ex. i en riktig databas) kan man gruppera liknande databasfrågor för att undvika N+1-problemet.  
 - Fragmentering och variabler: Klienten kan definiera fragments för återanvändbara delar av förfrågningen, vilket underlättar både läsbarhet och underhåll.  

Ett exempel:  
Utan optimering (hämtar all data):
```graphql
query {
getMatchResults {
matchId
teamA
teamB
scoreA
scoreB
}
}
```
Med optimering (hämtar bara de fält som behövs, t.ex. vid en översikt):  
```graphql
query {
getMatchResults {
matchId
teamA
teamB
}
}
```

## 2. Installation och körning

(Instruktioner för hur applikationen byggs och körs. Om externa verktyg krävs, beskriv hur dessa installeras och används.)

Klona repot från GitHub:
```shell 
git clone https://github.com/david-andreasson/api-webservices-final-assignment_79davand.git
```
Efter att ha klonat repot, navigera in i projektmappen för uppgiften:

```code
cd api-webservices-final-assignment_79davand/Uppgift_4
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
Du kan också köra applikationen direkt från din IDE genom att köra main-metoden i Uppgift4Application.  

När applikationen är igång är GraphQL-endpointen tillgänglig på:
```bash
http://localhost:8080/graphql
```

Kör automatiserade tester (valfritt): Om du vill verifiera att allt fungerar korrekt kan du öppna en terminal i projektmappen och köra:
```shell
mvn test
```

## 3. Användning av API:et

(Beskrivning av GraphQL-endpoints och exempelanrop.)

### Exempel på GraphQL-anrop:

#### Fråga:

```graphql
query {
    getMatchResults {
        matchId
        teamA
        teamB
        scoreA
        scoreB
    }
}
```

#### Svar:

```json
{
    "data": {
        "getMatchResults": [
            {
                "matchId": 1,
                "teamA": "Team X",
                "teamB": "Team Y",
                "scoreA": 2,
                "scoreB": 1
            }
        ]
    }
}
```

## 4. Felhantering

(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)

Jag har implementerat en global felhantering med GlobalGraphQLExceptionResolver som utökar DataFetcherExceptionResolverAdapter.  
Denna komponent fångar upp alla undantag som uppstår i våra GraphQL-resolvers och returnerar ett standardfelmeddelande med feltypen BAD_REQUEST.  

Så här fungerar det:  
Alla undantag i resolvers omvandlas automatiskt till ett GraphQLError med meddelandet:  
"Ett fel uppstod: " + ex.getMessage().  

Feltyp:  
Feltypen sätts till BAD_REQUEST, vilket indikerar att felet ofta beror på felaktiga indata.  

Exempel på felrespons:  
Om ett matchresultat inte hittas kan svaret bli:  
```json
{
"errors": [
{
"message": "Ett fel uppstod: MatchResult not found",
"extensions": {
"errorType": "BAD_REQUEST"
}
}
],
"data": null
}
```
## 5. Tester

(Beskriv hur GraphQL API:et har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)

I projektet har jag använt ett minimalt automatiserat test – standardtestet som kontrollerar att Spring Boot-applikationen startar korrekt (se Uppgift4ApplicationTests). För att verifiera funktionaliteten i GraphQL-API:et har vi huvudsakligen testat manuellt med Postman:  

- Manuella tester med Postman:  
Vi har skickat queries (t.ex. getMatchResults) och mutationer (för att skapa, uppdatera och ta bort matchresultat) för att säkerställa att API:et svarar som förväntat. Dessa tester har även verifierat att felhantering (t.ex. när ett matchresultat inte hittas) fungerar korrekt.  

- Automatiserade tester:
För närvarande ingår endast det standardtest som kontrollerar att applikationskontexten laddas. Ytterligare automatiserade tester kan implementeras vid behov, men för MVP:n har de manuella testerna ansetts tillräckliga.
## 6. Reflektion

-   *Vad har varit utmanande i uppgiften?*  
  Inget direkt, denna var ganska straight forward. Även om jag fick googla mycket.  


 - *Vad skulle kunna förbättras?*
 - Persistens:  
   Istället för att använda en in-memory lagring kan du integrera en riktig databas (t.ex. med Spring Data JPA). Detta skulle göra applikationen mer robust och produktionredo.  

 - Utökad felhantering:  
   Förutom den globala exception resolvern kan du implementera mer specifika undantag och validering på indata, så att felmeddelanden blir ännu tydligare.  

 - Automatiserad testning:
   För att få en högre testtäckning kan du lägga till fler enhetstester och integrationstester för att verifiera att resolvers, service-lager och säkerhet fungerar som tänkt.  

 - Förbättrad säkerhet:
   Även om Basic Auth är tillräckligt för ett MVP, kan du överväga att införa en mer robust lösning (t.ex. JWT-baserad autentisering) för att bättre hantera auktorisering.  


-   *Eventuella lärdomar från implementationen.*

 - Fördelar med GraphQL:  
   Implementeringen visade mig att GraphQL låter klienten precis specificera vilken data som behövs, vilket gör att vi slipper skicka med onödig information – något som ofta händer med REST.  

 - Effektiv felhantering:  
   Genom att använda en global exception resolver kunde jag samla all felhantering på ett ställe. Det gjorde koden mycket lättare att läsa, utan att behöva lägga ut try/catch-block överallt.  

 - Grundläggande autentisering med Spring Security:  
   Att sätta upp Basic Auth med Spring Security var ganska smidigt. Samtidigt insåg jag att om detta skulle bli en produktionslösning så behövdes troligen en mer avancerad metod, som JWT.  

 - Snabb MVP-utveckling:  
   Genom att använda en in-memory datalagring och seed-data kunde jag snabbt testa API:et med Postman och få omedelbar feedback, vilket var superviktigt i utvecklingsfasen.  
---