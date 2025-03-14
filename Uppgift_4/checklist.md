# Komplett Checklista för API & Webservices-uppgifter

## Uppgift 4: Implementera en enkel GraphQL-server

### Generella krav

- [X] Koden är skriven i Java 21
- [ ] Koden följer principerna för clean code
- [x] Projektet är i sin egen katalog med strukturen:
  ```
  Uppgift_4/kod
  Uppgift_4/README.md
  ```
- [X] All kod är versionshanterad och tillgänglig på GitHub
- [ ] GitHub-repositoryt är korrekt namngett: `api-webservices-final-assignment_{ditt_gafe_namn}`
- [ ] Minimal implementation (MVP-tillvägagångssätt) med mockad data istället för riktiga databaser

### Inlämningschecklista

- [X] Projektet finns i ett GitHub-repository
  - [X] Namngivning: `api-webservices-final-assignment_{ditt_gafe_namn}/Uppgift_4`
- [X] Projektstruktur följer kraven:
  ```
  Uppgift_4/kod
  Uppgift_4/README.md
  ```

### Implementationschecklista

- [ ] GraphQL-server är implementerad med CRUD-operationer för sportresultat
- [ ] Felhantering är implementerad:
  - [ ] API:et returnerar rätt GraphQL-felmeddelanden
- [ ] Autentisering är implementerad **(VG)**
  - [ ] Enkel autentisering för att begränsa åtkomst till vissa data

### README.md innehåller

- [ ] Ditt namn finns i tabellen i Readmefilen
- [ ] Ditt email finns i tabellen i Readmefilen
- [ ] Github länk finns i Readmefilen
- [ ] Beskrivning av uppgiften
- [ ] Installation och körning
- [ ] Användning av API:et (queries, mutationer, exempelanrop)
- [ ] Felhantering (statuskoder och felmeddelanden)
- [ ] Tester (hur testning genomförts)
- [ ] Reflektion (utmaningar, förbättringar, lärdomar)
- [ ] Förklarat skillnaden mellan GraphQL och REST **(VG)**
- [ ] Beskrivit hur man optimerar queries för att minska onödig datalast **(VG)**
