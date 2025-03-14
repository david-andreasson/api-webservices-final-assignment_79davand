# Uppgift 6: API med autentisering och begränsning av anrop

| Data            | Värde |
| --------------- | ----- |
| **Namn**        |       |
| **Email**       |       |
| **GitHub-länk** |       |

---

## 1. Beskrivning av uppgiften

(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)

### Förklara hur du har implementerat säkerheten och begränsningen av anrop. (VG)

(dina tankar här)

### Diskutera olika metoder för att lagra användaruppgifter och autentiseringstokens på ett säkert sätt (även om ni inte implementerar det fullt ut). (VG)

(dina tankar här)

### Reflektera över hur man kan skala denna lösning för att hantera en stor mängd användare och anrop. (VG)

(dina tankar här)

### Beskriv hur säkerheten kan göras ännu bättre med hjälp av t.ex. rate limiting, CORS och CSRF-skydd (och vad dessa begrepp innebär). (VG)

(dina tankar här)

## 2. Installation och körning

(Instruktioner för hur applikationen byggs och körs. Om externa verktyg krävs, beskriv hur dessa installeras och används.)

## 3. Användning av API:et

(Beskrivning av API-endpoints och exempelanrop.)

### Exempel på endpoints:

-   **POST** /register

    -   Beskrivning: Registrerar en ny användare
    -   Payload: `{ "email": "user@example.com", "password": "securepassword" }`
    -   Respons: `{ "message": "Användare registrerad" }`

-   **POST** /login

    -   Beskrivning: Loggar in användaren och returnerar en JWT-token
    -   Payload: `{ "email": "user@example.com", "password": "securepassword" }`
    -   Respons: `{ "token": "eyJhbGciOiJI..." }`

-   **GET** /dashboard

    -   Beskrivning: Skyddad endpoint, endast för inloggade användare
    -   Header: `Authorization: Bearer <token>`
    -   Respons: `{ "message": "Välkommen till dashboarden!" }`

-   **POST** /logout
    -   Beskrivning: Loggar ut användaren
    -   Header: `Authorization: Bearer <token>`
    -   Respons: `{ "message": "Utloggad" }`

## 4. Felhantering

(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)

## 5. Tester

(Beskriv hur API:et har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)

## 6. Reflektion

-   Vad har varit utmanande i uppgiften?
-   Vad skulle kunna förbättras?
-   Eventuella lärdomar från implementationen.
