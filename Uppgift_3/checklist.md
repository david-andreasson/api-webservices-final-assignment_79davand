# Komplett Checklista för API & Webservices-uppgifter

## Uppgift 3: Implementera en enkel SOAP-webbtjänst

### Generella krav

- [X] Koden är skriven i Java 21
- [ ] Koden följer principerna för clean code
- [x] Projektet är i sin egen katalog med strukturen:
  ```
  Uppgift_3/kod
  Uppgift_3/README.md
  ```
- [X] All kod är versionshanterad och tillgänglig på GitHub
- [ ] GitHub-repositoryt är korrekt namngett: `api-webservices-final-assignment_{ditt_gafe_namn}`
- [ ] Minimal implementation (MVP-tillvägagångssätt) med mockad data istället för riktiga databaser

### Inlämningschecklista

- [X] Projektet finns i ett GitHub-repository
  - [X] Namngivning: `api-webservices-final-assignment_{ditt_gafe_namn}/Uppgift_3`
- [X] Projektstruktur följer kraven:
  ```
  Uppgift_3/kod
  Uppgift_3/README.md
  ```

### Implementationschecklista

- [ ] SOAP-webbtjänst är implementerad med CRUD-operationer
- [ ] Felhantering är implementerad:
  - [ ] API:et returnerar rätt SOAP-felmeddelanden
- [ ] Säkerhet är implementerad **(VG)**
  - [ ] WS-Security eller Basic Authentication är implementerad

### README.md innehåller

- [ ] Ditt namn finns i tabellen i Readmefilen
- [ ] Ditt email finns i tabellen i Readmefilen
- [ ] Github länk finns i Readmefilen
- [ ] Beskrivning av uppgiften
- [ ] Installation och körning
- [ ] Användning av API:et (endpoints, exempelanrop, HTTP-metoder)
- [ ] Felhantering (statuskoder och felmeddelanden)
- [ ] Tester (hur testning genomförts)
- [ ] Reflektion (utmaningar, förbättringar, lärdomar)
- [ ] Förklarat skillnaden mellan SOAP och REST **(VG)**
- [ ] Beskrivit hur en klient skulle anropa tjänsten med XML-baserade anrop **(VG)**
