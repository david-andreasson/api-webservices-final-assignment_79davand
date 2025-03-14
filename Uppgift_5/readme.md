# Uppgift 5: Paketering och körning av Spring Boot-applikation

| Data            | Värde |
| --------------- | ----- |
| **Namn**        |       |
| **Email**       |       |
| **GitHub-länk** |       |

---

## 1. Beskrivning av uppgiften

(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)

# Förklara skillnaden mellan en **fat JAR** (som inkluderar alla beroenden) och en vanlig JAR. (VG)

(dina tankar här)

### Beskriv olika sätt att distribuera en Spring Boot-applikation utan Docker. (VG)

(dina tankar här)

### Demonstrera hur miljövariabler kan användas för att hantera olika driftsmiljöer. (VG)

(dina tankar här)

## 2. Installation och körning

(Instruktioner för hur applikationen byggs och körs. Om externa verktyg krävs, beskriv hur dessa installeras och används.)

### Bygga och köra JAR-filen:

```sh
mvn clean package
java -jar target/melloomrostning-1.0.jar
```

## 3. Användning av API:et

(Beskrivning av API-endpoints och exempelanrop.)

### Exempel på endpoints:

-   **POST** /vote

    -   Beskrivning: Lägger till en röst
    -   Payload: `{ "contestant": "Artist A" }`
    -   Respons: `{ "message": "Röst registrerad" }`

-   **GET** /results
    -   Beskrivning: Hämtar aktuella röstresultat
    -   Respons: `{ "results": [{ "contestant": "Artist A", "votes": 120 }] }`

## 4. Felhantering

(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)

## 5. Tester

(Beskriv hur API:et har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)

## 6. Reflektion

-   Vad har varit utmanande i uppgiften?
-   Vad skulle kunna förbättras?
-   Eventuella lärdomar från implementationen.
