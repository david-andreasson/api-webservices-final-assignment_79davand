# Inlämningsuppgifter: API och Webservices

## Introduktion

Dessa uppgifter är utformade för att visa att ni kan hantera API-utveckling med Spring Boot. Varje uppgift är fristående och fokuserar på ett specifikt område inom API-utveckling. Grundläggande funktionalitet (G) är ett krav för godkänt, medan väl godkänt (VG) kräver djupare analys och reflektion.

**Viktigt:** Uppgifterna ska implementeras enligt **MVP-principen** (Minimum Viable Product). Det betyder att endast kärnfunktionaliteten ska byggas – allt annat ska mockas. Ingen databas behövs, det räcker att endpoints svarar korrekt enligt specifikationen. Det är helt OK att ha samma grundprojekt som mall, och sedan bara bygga in de nödvändiga delarna för varje uppgift. Det är viktigt att fokusera på att implementera de specifika kraven för varje uppgift och att dokumentera detta på ett tydligt sätt.

**Enskilt arbete.** Varje student ska genomföra uppgifterna individuellt. Diskussion och idéutbyte är tillåtet och uppmuntras, dock ska varje student skriva sin egen kod och dokumentation.

**Deadline: 23 mars kl. 23:59**

## Krav på inlämning

-   Dessa uppgifter syftar till att testa era kunskaper i **Spring Boot och API\*\***:er\*\*.
-   Dokumentationen är avgörande för bedömningen:
    -   **Ingen dokumentation = IG**
    -   **Kort eller minimal dokumentation = G**
    -   **VG kräver att dokumentationen är grundlig och nyanserad, så att en nybörjare förstår den.**
-   Alla delar av implementationen måste förklaras, inklusive valda tekniker och lösningar. Du är läraren som ska förklara för en nybörjare hur det fungerar.
-   **All kod ska vara versionerad och tillgänglig på klassens GitHub**. Länken till repot ska inkluderas i README.md.
-   Ditt repo ska heta `api-webservices-final-assignment_{ditt_gafe_namn}`, exempelvis `api-webservices-final-assignment_marmed02`.

-   Felhantering är viktig:
    -   **Endpoints måste ge lämpliga felmeddelanden** om felaktig data skickas.
    -   Använd rätt **HTTP-statuskoder** och tydliga felmeddelanden för att underlätta felsökning.
-   Varje uppgift ska läggas i en egen katalog enligt strukturen:
    ```
    uppgift1/kod
    uppgift1/README.md
    uppgift2/kod
    uppgift2/README.md
    uppgift3/kod
    uppgift3/README.md
    ...
    ```
-   Ett projekt per uppgift (lättare att rätta)
-   Alla uppgifter ska zippas i en fil vid inlämning.
    -   Inga enskilda filer ska bifogas.
    -   Inga `.git`-mappar ska inkluderas.
    -   Inga `target`-mappar ska inkluderas.
    -   Inga IDE-specifika filer ska inkluderas.
-   Kod ska vara skriven i **Java 21** och följa principerna för **clean code**.

---

## Uppgifter

### Uppgift 1: Implementera Swagger i ett Spring Boot-projekt

**Tema:** Böcker (bokförteckning och sökfunktion)

**Mål:** Att visa att ni kan dokumentera API:er med Swagger/OpenAPI.

**G:**

-   Skapa ett enkelt Spring Boot API med minst två endpoints för att hämta och lägga till böcker.
-   Integrera Swagger/OpenAPI för att dokumentera API:et.

**VG:**

-   Förklara varför API-dokumentation är viktig för både utvecklare och beställare.
-   Reflektera över hur Swagger kan integreras i en CI/CD-pipeline.
-   Beskriv hur dokumentationen kan hållas uppdaterad vid API-förändringar.

---

### Uppgift 2: API-testning: strategier och bästa praxis

**Tema:** Bilar (registrering av bilmodeller och sökning)

**Mål:** VIsa att ni förstår API-testning genom att bygga en enkel miniräknare som API.

**G:**

-   Implementera ett API med fyra endpoints: addition, subtraktion, multiplikation och division.
-   Testa API:et manuellt med Postman eller curl.

**VG:**

-   Beskriva skillnaden mellan enhetstestning, integrationstestning och end-to-end-testning.
-   Implementera automatiserade tester med JUnit och RestAssured.
-   Diskutera varför felhantering och statuskoder är avgörande för robusta API:er.

---

### Uppgift 3: Implementera en enkel SOAP-webservice

**Tema:** Telefonmodeller (hantera lista över mobiltelefoner)

**Mål:** Visa att ni kan hur SOAP fungerar i moderna system.

**G:**

-   Skapa en SOAP-webbtjänst med CRUD-operationer på en lista med telefonmodeller.

**VG:**

-   Förklara skillnaden mellan SOAP och REST samt när SOAP fortfarande är relevant.
-   Implementera säkerhet med WS-Security eller Basic Authentication.
-   Beskriva hur en klient skulle anropa tjänsten med XML-baserade anrop.

---

### Uppgift 4: Implementera en enkel GraphQL-server

**Tema:** Sportresultat (hantering av matcher och resultat)

**Mål:** Att visa att ni kan arbeta med GraphQL och dess fördelar över REST.

**G:**

-   Skapa en GraphQL-server som hanterar CRUD på en lista med sportresultat.

**VG:**

-   Förklara skillnaden mellan GraphQL och REST.
-   Demonstrera hur man kan optimera queries för att minska onödig datalast.
-   Implementera en enkel autentisering för att begränsa åtkomst till vissa data.

---

### Uppgift 5: Paketering och körning av Spring Boot-applikation

**Tema:** Melloomröstning (hantering av röster och resultat)

**Mål:** Att visa att ni kan köra Spring Boot-applikationer fristående.

**G:**

-   Visa hur applikationen kan byggas och köras som en JAR-fil.

**VG:**

-   Förklara skillnaden mellan en **fat JAR** (som inkluderar alla beroenden) och en vanlig JAR.
-   Beskriva olika sätt att distribuera en Spring Boot-applikation utan Docker.
-   Demonstrera hur miljövariabler kan användas för att hantera olika driftsmiljöer.

---

### Uppgift 6: API med autentisering och begränsning av anrop

**Tema:** Användarhantering (registrering, inloggning, utloggning och dashboard)

**Mål:** Att visa att ni kan implementera säkerhet och begränsa anrop i ett API.

**G:**

-   Skapa ett Spring Boot API med följande endpoints:

    -   `POST /register`: Registrering av ny användare (öppen för alla).
    -   `POST /login`: Inloggning av användare med e-post och lösenord (öppen för alla).
    -   `GET /dashboard`: Skyddad endpoint som endast är tillgänglig för inloggade användare.
    -   `POST /logout`: Utloggning av användare (skyddad endpoint).
    -   Implementera följande säkerhetsfunktioner:
    -   Begränsning av anrop till max 3 anrop per minut per IP-adress för `/register` och `/login`. Vid överträdelse ska IP-adressen blockeras i 5 minuter.
    -   Tre misslyckade inloggningsförsök från en IP-adress ska resultera i att IP-adressen blockeras i 15 minuter.
    -   Använd JWT (JSON Web Tokens) för att hantera autentisering och sessioner.
    -   Inloggning ska returnera ett JSON-meddelande: `{"message": "OK, {username} du är inloggad"}` och automatiskt vidarebefordra användaren till `/dashboard`.
    -   Implementera Google OAuth för inloggning.
    -   `/logout` ska kräva autentisering via JWT.
    -   `/dashboard` ska kräva autentisering via JWT.
    -   Implementera en enkel användarhantering med en lista av användare i minnet.
    -   Dokumentera API:et med Swagger.

**VG:**

-   Förklara hur du har implementerat säkerheten och begränsningen av anrop.
-   Diskutera olika metoder för att lagra användaruppgifter och autentiseringstokens på ett säkert sätt (även om ni inte implementerar det fullt ut).
-   Reflektera över hur man kan skala denna lösning för att hantera en stor mängd användare och anrop.
-   Beskriv hur säkerheten kan göras ännu bättre med hjälp av t.ex. rate limiting, CORS och CSRF-skydd (och vad dessa begrepp innebär).

---

## README-mall

För varje uppgift ska en README.md-fil bifogas som innehåller följande sektioner:

### 1. Beskrivning av uppgiften

-   Kortfattad beskrivning av vad uppgiften innebär och dess syfte.

### 2. Installation och körning

-   Instruktioner för hur applikationen byggs och körs.
-   Om externa verktyg krävs, beskriv hur dessa installeras och används.

### 3. Användning av API:et

-   Beskrivning av API-endpoints med exempelanrop (HTTP-metoder, URL:er, och exempel-payloads).
-   Förväntade svar från API:et, inklusive HTTP-statuskoder.

### 4. Felhantering

-   Vilka typer av fel kan uppstå och hur de hanteras.
-   Exempel på felmeddelanden och statuskoder som returneras.

### 5. Tester

-   Beskriv hur API:et har testats.
-   Om tester är automatiserade, förklara hur de körs och vad de testar.

### 6. Reflektion

-   Vad har varit utmanande i uppgiften?
-   Vad skulle kunna förbättras?
-   Eventuella lärdomar från implementationen.

---

## Bedömning

-   **IG:** Ingen eller ofullständig dokumentation.
-   **G:** Grundläggande implementation och dokumentation som beskriver vad som gjorts.
-   **VG:** Djupare förståelse, reflektion och tydlig dokumentation som en nybörjare kan följa.

### Inlämning

-   Skicka in en **zippad** fil med alla uppgifter enligt strukturen.
-   README.md ska innehålla alla punkter enligt uppgiftsbeskrivningen.
-   Koden ska vara läsbar, strukturerad och följa goda kodningsprinciper.
