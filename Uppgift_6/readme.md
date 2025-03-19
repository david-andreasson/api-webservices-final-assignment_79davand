# Uppgift 6: API med autentisering och begränsning av anrop

| Data            | Värde                                                                                             |
|-----------------|---------------------------------------------------------------------------------------------------|
| **Namn**        | David Andreasson                                                                                  |
| **Email**       | 79davand@gafe.molndal.se                                                                          |
| **GitHub-länk** | https://github.com/david-andreasson/api-webservices-final-assignment_79davand/tree/main/Uppgift_6 |

---

## 1. Beskrivning av uppgiften

(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)  

Uppgiften går ut på att skapa ett Spring Boot API som hanterar användarregistrering, inloggning, utloggning och en skyddad dashboard.  
Syftet är att visa hur man kan implementera säkerhet med JWT och Google OAuth2 samt använda rate limiting för att begränsa antalet anrop.  
Istället för en enkel lista med användare i minnet använder jag en H2-databas för att lagra användardata för exempelkoden som vi har fått använder redan h2-databas, så det var enklare att implementera än att ändra till att spara i en lista.  

### Förklara hur du har implementerat säkerheten och begränsningen av anrop. (VG)

(dina tankar här)  

I mitt API har jag implementerat säkerheten genom att använda JWT för autentisering. Vid inloggning genereras en token som skickas tillbaka till klienten och som sedan används för att komma åt skyddade endpoints (såsom /dashboard och /logout).  
Jag har också implementerat Google OAuth2 så att användare kan logga in med sina Google-konton, vilket gör autentiseringen både flexibel och säker.  
För att begränsa antalet anrop har jag lagt in rate limiting på /register och /login, vilket begränsar en enskild IP-adress till högst 3 anrop per minut.  
Om gränsen överskrids blockeras IP-adressen under 5 minuter. Dessutom övervakar systemet misslyckade inloggningsförsök, och efter tre misslyckade försök från samma IP-adress blockeras den i 15 minuter. Detta skyddar mot brute-force-attacker.  

Tillsammans ger dessa åtgärder en robust säkerhetslösning som både hanterar autentisering och skyddar API:et mot överbelastning och attacker, samtidigt som lösningen är enkel och lätt att underhålla.

### Diskutera olika metoder för att lagra användaruppgifter och autentiseringstokens på ett säkert sätt (även om ni inte implementerar det fullt ut). (VG)

(dina tankar här)  

När det gäller lagring av användaruppgifter är det viktigt att använda säkra databaser med korrekt åtkomstkontroll och kryptering, både i vila och under överföring.  
I en produktionsmiljö bör man även överväga att använda tekniker som rollback-skydd och regelbundna säkerhetskopior, samt att minimera känslig data genom att lagra endast nödvändig information och använda hashade lösenord med starka algoritmer som BCrypt.
När det gäller autentiseringstokens, som JWT, är det viktigt att hantera dem säkert. Det betyder att de bäst lagras i minnet på klientsidan, t.ex. i en säker cookie med HttpOnly-flagga, istället för i lokal lagring där skadlig kod kan nå dem.  
På serversidan kan man använda metoder som token-blacklisting för att ogiltigförklara tokens vid utloggning eller om något verkar misstänkt. Det är också avgörande att tokens har en rimlig livslängd och att de hemliga nycklarna som signerar tokens hanteras tryggt, till exempel med miljövariabler eller ett hanteringssystem som Vault.

Utöver JWT-hantering med säkra cookies och blacklisting, kan det vara smart att ha en backup med sessionshantering. Det innebär att man lagrar lite grundläggande användarinformation i en krypterad session på servern för att snabbt kunna stänga av åtkomsten om något går fel. Det är också bra att rotera nycklar regelbundet så att risken för missbruk minskar om en nyckel skulle bli stulen. Och sist men inte minst – multifaktorautentisering (MFA) kan ge en extra säkerhetsnivå vid inloggningen.


### Reflektera över hur man kan skala denna lösning för att hantera en stor mängd användare och anrop. (VG)

(dina tankar här)  

Jag skulle tänka att vi borde bryta ut rate limiting till en gemensam cache, typ med Redis, så att flera instanser kan dela på samma data.  
Det skulle innebära att om trafiken ökar kan vi köra flera applikationsinstanser bakom en lastbalanserare, vilket hjälper till att fördela belastningen.  
Även om vi använder H2 nu, skulle vi i en skarp miljö byta ut mot en "riktig" databas med stöd för replikering.  
Dessutom kan man fundera på att separera autentiseringslogiken till en egen microservice och använda caching för att minska antalet anrop direkt till databasen.  
På så sätt kan lösningen växa med antalet användare och anrop utan att någon del blir en flaskhals.

### Beskriv hur säkerheten kan göras ännu bättre med hjälp av t.ex. rate limiting, CORS och CSRF-skydd (och vad dessa begrepp innebär). (VG)

(dina tankar här)  

Jag skulle säga att säkerheten kan förbättras ytterligare genom att använda ett striktare rate limiting, vilket hindrar användare från att bombardera API:et med anrop och därmed minskar risken för överbelastningsattacker. Med CORS (Cross-Origin Resource Sharing) kan vi styra exakt vilka domäner som får tillgång till API:et, vilket förhindrar att obehöriga webbplatser gör anrop. Och med CSRF-skydd (Cross-Site Request Forgery) ser vi till att inga oönskade åtgärder kan utföras genom att tvinga användare att göra en handling de inte har avsett, till exempel genom att klicka på en länk på en annan webbplats. Det här är som att ha flera lager av säkerhet, så att om en metod skulle misslyckas så finns det andra skydd på plats.

## 2. Installation och körning

(Instruktioner för hur applikationen byggs och körs. Om externa verktyg krävs, beskriv hur dessa installeras och
används.)

Klona repot från GitHub:
```shell 
git clone https://github.com/david-andreasson/api-webservices-final-assignment_79davand.git
```
Efter att ha klonat repot, navigera in i projektmappen för uppgiften:

```code
cd api-webservices-final-assignment_79davand/Uppgift_6
```
I Application.java finns metoden generateKey() som genererar en säker JWT-nyckel. För att använda denna funktion, kör applikationen med argumentet generate-key. Till exempel, om du använder Maven:  

```shell
mvn spring-boot:run -Dspring-boot.run.arguments=generate-key  
```
Om du kör från din IDE, konfigurera din körkonfiguration så att argumentet generate-key skickas med. När du kör kommandot kommer terminalen att skriva ut en JWT-nyckel – kopiera denna nyckel och uppdatera din .env-fil genom att sätta variabeln JWT_SECRET till det kopierade värdet.  

För att konfigurera Google OAuth2  
Följ dessa steg:

Gå till Google Cloud Console.  
Skapa ett nytt projekt.  
Aktivera "Google+ API"   
Gå till "Credentials".  
Klicka på "Create Credentials" och välj "OAuth client ID".  
Välj "Web application".  
Lägg till tillåtna redirect URI:er:  
http://localhost:8080/login/oauth2/code/google  
http://localhost:8080/oauth2/callback  
Spara klient-ID och klient-hemlighet, och lägg in dessa värden i din .env-fil under GOOGLE_CLIENT_ID och GOOGLE_CLIENT_SECRET.  

Exempel på .env-fil  
Placera .env-filen i projektets rotmapp (samma nivå som pom.xml), med följande innehåll:
```text
JWT_SECRET=din_genererade_jwt_nyckel_här
GOOGLE_CLIENT_ID=din_google_client_id_här
GOOGLE_CLIENT_SECRET=din_google_client_secret_här
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
Du kan också köra applikationen direkt från din IDE genom att köra main-metoden Application.

## 3. Användning av API:et

(Beskrivning av API-endpoints och exempelanrop.)

- **POST /auth/register**
Beskrivning: Registrerar en ny användare.  
Payload:
```json
{
"firstName": "Jason",
"lastName": "Vorhees",
"email": "friday@13th.org",
"password": "mommy"
}
```
Respons: Returnerar ett JSON-objekt med ett JWT-token.  
Exempel:
```json
{
"token": "eyJhbGciOiJIUzI1NiJ9..."
}
```
- **POST /auth/login**  
Beskrivning: Loggar in en användare med e-post och lösenord.  
Payload:
```json
{
"email": "friday@13th.org",
"password": "mommy"
}
```
Respons: Vid lyckad inloggning returneras ett JSON-meddelande i stil med:
```json
{
"message": "OK, friday@13th.org du är inloggad",
"token": "eyJhbGciOiJIUzI1NiJ9..."
}
```
- **GET /dashboard**  

Beskrivning: Skyddad endpoint som endast är tillgänglig för inloggade användare.  
Authorization: Bearer <token>
Respons: Returnerar ett JSON-objekt med ett välkomstmeddelande.  
Exempel:
```json
{
"message": "Welcome to your dashboard!"
}
```
- POST /auth/logout  
Beskrivning: Loggar ut användaren. Detta endpoint är skyddat och kräver en giltig JWT-token i headern.
Authorization: Bearer <token>
Respons: Returnerar ett JSON-meddelande som bekräftar utloggningen.  
Exempel:
```json
{
"message": "You are now logged out"
}
```
 - **Ytterligare endpoints:**
```text
GET /oauth2/success: Hanterar lyckad OAuth2-inloggning, skapar/uppdaterar användare och returnerar ett JWT-token tillsammans med användarens e-post och namn.
GET /oauth2/failure: Hanterar fel vid OAuth2-inloggning genom att returnera ett felmeddelande.
```
## 4. Felhantering

(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)
I detta API hanteras fel på flera sätt för att ge tydliga svar till klienten. Exempel:
Om en användare gör för många anrop (fler än 3 per minut) på /auth/register eller /auth/login, så kontrolleras detta i  RateLimitFilter. Vid överträdelse returneras HTTP-status 429 (Too Many Requests) med meddelandet:
"Rate limit exceeded. Try again later."  
Om inloggningen misslyckas, exempelvis på grund av felaktiga autentiseringsuppgifter, så kastas ett undantag i AuthenticationService vilket fångas i AuthController. Då returneras HTTP-status 401 (Unauthorized) med meddelandet:
"Invalid credentials"

Vid andra fel, exempelvis när en resurs inte hittas, hanteras detta i CustomErrorController som returnerar en lämplig statuskod (t.ex. 404 (Not Found) med meddelandet "Resurs inte hittad", eller 403 (Forbidden) för otillåten åtkomst).

## 5. Tester

(Beskriv hur API:et har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)  
Inga automatiserade tester har implementerats för detta API. Istället har jag testat alla endpoints manuellt med Swagger och Postman.  
Med Swagger kunde jag validera att dokumentationen var korrekt och att alla endpoints svarade med rätt statuskoder och meddelanden. Med Postman testade jag även autentisering, felhantering och rate limiting-funktionaliteten för att säkerställa att API:et uppför sig som förväntat.
## 6. Reflektion

- Vad har varit utmanande i uppgiften?  
  Det största strulet i uppgiften var att få till rätt versioner på JWT-biblioteken – jag fick kämpa med skillnader mellan jwt.parserBuilder och jwt.parser eftersom olika versioner i pom-filen orsakade oväntade beteenden.  
  Dessutom hade jag filer i två olika paket (edu.molndal och com.davanddev) vilket gjorde att beroenden och konfigurationen blev ett stort virrvarr. Att få ihop allt i en enhetlig struktur var extremt frustrerande.  

- Vad skulle kunna förbättras?  
  Säkerheten kan förstärkas med fler åtgärder, exempelvis att implementera CSRF-skydd.  
  Det skulle också vara en bra idé att lägga till automatiserade tester för att snabbare upptäcka fel om man ändrar i koden.  
  Swagger-dokumentationen kan förbättras med mer detaljerade beskrivningar av endpoints, parametrar och exempel på svar, så att det blir ännu tydligare för användare av API:et.
- Eventuella lärdomar från implementationen.  
  Bara för man har mycket exempelkod att utgå från så betyder inte det att projektet blir lätt! Suck....  =)
