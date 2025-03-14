# Komplett Checklista för API & Webservices-uppgifter

## Uppgift 6: API med autentisering och begränsning av anrop

### Generella krav

- [X] Koden är skriven i Java 21
- [ ] Koden följer principerna för clean code
- [X] Projektet är i sin egen katalog med strukturen:
  ```
  Uppgift_6/kod
  Uppgift_6/README.md
  ```
- [X] All kod är versionshanterad och tillgänglig på GitHub
- [ ] GitHub-repositoryt är korrekt namngett: `api-webservices-final-assignment_{ditt_gafe_namn}`
- [ ] Minimal implementation (MVP-tillvägagångssätt) med mockad data istället för riktiga databaser

### Inlämningschecklista

- [X] Projektet finns i ett GitHub-repository
  - [X] Namngivning: `api-webservices-final-assignment_{ditt_gafe_namn}/Uppgift_6`
- [X] Projektstruktur följer kraven:
  ```
  Uppgift_6/kod
  Uppgift_6/README.md
  ```

### Implementationschecklista

- [ ] JWT används för autentisering
- [ ] Rate limiting är implementerad
- [ ] Obligatoriska endpoints är implementerade:
  - [ ] POST /register - För registrering av nya användare (öppen för alla)
  - [ ] POST /login - För användarinloggning med e-post och lösenord (öppen för alla)
  - [ ] GET /dashboard - Skyddad endpoint som endast är tillgänglig för inloggade användare
  - [ ] POST /logout - För användarutloggning (skyddad endpoint)
- [ ] Säkerhetsfunktioner:
  - [ ] Begränsning av anrop till max 3 anrop per minut per IP-adress för /register och /login
  - [ ] IP-blockering i 5 minuter vid överträdelse av anropsgränser
  - [ ] IP-blockering i 15 minuter efter tre misslyckade inloggningsförsök från en IP-adress
  - [ ] JWT (JSON Web Tokens) för autentisering och sessionshantering
  - [ ] Inloggning returnerar JSON-meddelande: `{"message": "OK, {username} du är inloggad"}`
  - [ ] Inloggning vidarebefordrar automatiskt användare till /dashboard
  - [ ] Google OAuth-implementation för inloggning
  - [ ] /logout kräver JWT-autentisering
  - [ ] /dashboard kräver JWT-autentisering
  - [ ] Enkel användarhantering med lista över användare i minnet
  - [ ] API-dokumentation med Swagger
- [ ] Felhantering är implementerad:
  - [ ] API:et returnerar korrekta statuskoder och felmeddelanden

### README.md innehåller

- [ ] Ditt namn finns i tabellen i Readmefilen
- [ ] Ditt email finns i tabellen i Readmefilen
- [ ] Github länk finns i Readmefilen
- [ ] Beskrivning av uppgiften
- [ ] Installation och körning
- [ ] Beskrivning av säkerhetsimplementering och skalningsstrategier **(VG)**
- [ ] Diskussion om metoder för säker lagring av användardata och autentiseringstokens **(VG)**
- [ ] Reflektion över skalning av lösningen för många användare och anrop **(VG)**
- [ ] Beskrivning av förbättrad säkerhet med rate limiting, CORS och CSRF-skydd **(VG)**

## Slutlig inlämning

- [ ] Alla projekt är zippade i en enda fil
- [ ] Inga enskilda filer är bifogade
- [ ] Inga `.git`-mappar är inkluderade
- [ ] Inga `target`-mappar är inkluderade
- [ ] Inga IDE-specifika filer är inkluderade
