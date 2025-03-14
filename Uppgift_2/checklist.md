# Komplett Checklista för API & Webservices-uppgifter

## Uppgift 2: API-testning: strategier och bästa praxis

### Generella krav

- [X] Koden är skriven i Java 21
- [ ] Koden följer principerna för clean code
- [x] Projektet är i sin egen katalog med strukturen:
  ```
  Uppgift_2/kod
  Uppgift_2/README.md
  ```
- [X] All kod är versionshanterad och tillgänglig på GitHub
- [ ] GitHub-repositoryt är korrekt namngett: `api-webservices-final-assignment_{ditt_gafe_namn}`
- [ ] Minimal implementation (MVP-tillvägagångssätt) med mockad data istället för riktiga databaser

### Inlämningschecklista

- [X] Projektet finns i ett GitHub-repository
  - [X] Namngivning: `api-webservices-final-assignment_{ditt_gafe_namn}/Uppgift_2`
- [X] Projektstruktur följer kraven:
  ```
  Uppgift_2/kod
  Uppgift_2/README.md
  ```

### Implementationschecklista

- [ ] Fyra endpoints är implementerade:
  - [ ] Addition
  - [ ] Subtraktion
  - [ ] Multiplikation
  - [ ] Division
- [ ] Felhantering är implementerad:
  - [ ] API:et returnerar rätt HTTP-statuskoder
  - [ ] API:et returnerar tydliga felmeddelanden
- [ ] Tester har genomförts:
  - [ ] API:et har testats manuellt med Postman eller curl
- [ ] Automatiserade tester är implementerade med JUnit och RestAssured **(VG)**

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
- [ ] Beskrivit skillnaden mellan enhetstestning, integrationstestning och end-to-end-testning **(VG)**
- [ ] Diskuterat varför felhantering och statuskoder är avgörande för robusta API:er **(VG)**
