# Projekt PAP

## Temat
System obsługi sieci hoteli.
Aplikacja desktopowa w Javie połączona z bazą danych służąca do obsługi sieci hoteli. Będzie umożliwiać wyszukiwanie pokojów według potrzeb, sprawdzenie stanu dostępności pokoju, rezerwację go. Może też umożliwiać opłaty, wysyłanie ofert i promocji, opinie o obiektach.

## Uruchomienie aplikacji
### Wymagania wstępne
Program uruchamiany jest na systemie **Linux**.\
Aby móc uruchomić aplikację, należy zainstalować:
- **OpenJDK 21**
- **Maven**
- **Docker**

### Uruchomienie
Aby skonfigurować aplikację, należy uruchomić skrypt **initialize.sh**:
```
sudo bash initialize.sh
```
Skrypt tworzy i konfiguruje bazę danych, a następnie buduje aplikację.\
Po zbudowaniu aplikacji można ją uruchomić za pomocą poniższej komendy:
```
mvn exec:java@default
```
**@TODO**
Jest to wersja aplikacji dla zwykłych użytkowników. Aby uruchomić panel administracyjny, należy użyć poniższej komendy:
```
mvn exec:java@admin
```

## Konfiguracja deweloperska
Baza wykorzystywana w projekcie to **PostgreSQL**.
Dane do logowania do bazy danych:
- host: localhost
- port: 5432
- user: postgres
- password: root
- database: postgres

Możliwe jest uruchomienie projektu w IntelliJ IDEA po utworzeniu i dodaniu informacji do bazy danych.
Aplikacje dostępne przy kompilacji:
- `pap.UploadImages` - aplikacja do uploadu zdjęć do bazy danych
- `pap.Default` - aplikacja dla zwykłych użytkowników
- **@TODO** `pap.Admin` - aplikacja dla administratorów

## Autorzy
    Adrian Murawski
    Angelika Ostrowska
    Jakub Bąba
    Kacper Straszak