# Uppgift 3: Implementera en enkel SOAP-webservice

| Data            | Värde |
| --------------- | ----- |
| **Namn**        |       |
| **Email**       |       |
| **GitHub-länk** |       |

---

## 1. Beskrivning av uppgiften

(Kortfattad beskrivning av vad uppgiften innebär och dess syfte.)

## Förklara skillnaden mellan SOAP och REST samt när SOAP fortfarande är relevant. (VG)

(dina tankar här)

### Beskriv hur en klient skulle anropa tjänsten med XML-baserade anrop. (VG)

(dina tankar här)

## 2. Installation och körning

(Instruktioner för hur applikationen byggs och körs. Om externa verktyg krävs, beskriv hur dessa installeras och används.)

## 3. Användning av API:et

(Beskrivning av SOAP-tjänstens funktionalitet och XML-struktur för anrop.)

### Exempel på SOAP-anrop:

#### Begäran:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tel="http://example.com/telefon">
   <soapenv:Header/>
   <soapenv:Body>
      <tel:GetPhoneModelRequest>
         <tel:modelName>iPhone 14</tel:modelName>
      </tel:GetPhoneModelRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Svar:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
   <soapenv:Body>
      <tel:GetPhoneModelResponse>
         <tel:modelName>iPhone 14</tel:modelName>
         <tel:manufacturer>Apple</tel:manufacturer>
         <tel:releaseYear>2022</tel:releaseYear>
      </tel:GetPhoneModelResponse>
   </soapenv:Body>
</soapenv:Envelope>
```

## 4. Felhantering

(Vilka typer av fel kan uppstå och hur de hanteras. Exempel på felmeddelanden och statuskoder som returneras.)

## 5. Tester

(Beskriv hur SOAP-tjänsten har testats. Om tester är automatiserade, förklara hur de körs och vad de testar.)

## 6. Reflektion

-   Vad har varit utmanande i uppgiften?
-   Vad skulle kunna förbättras?
-   Eventuella lärdomar från implementationen.
