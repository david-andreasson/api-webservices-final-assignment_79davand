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
