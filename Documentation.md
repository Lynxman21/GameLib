# GameLib
**Autorzy:**  
- Łukasz Krementowski  
- Mateusz Ryś
## Temat projektu: Wypożyczalnia gier




**Technologie użyte w projekcie:**
- PostgreSQL
- Hibernate
- Java 21
- JavaFX

## 1. Lista funkcji aplikacji

Poniżej znajduje się lista głównych funkcji zaimplementowanych w projekcie **GameLib** wraz z krótkim opisem każdej z nich:

- **Przeglądanie dostępnych gier**  
  Umożliwia użytkownikowi przeglądanie pełnej listy gier dostępnych w wypożyczalni wraz z podstawowymi informacjami o każdej z nich (tytuł, wydawca, kategorie).

- **Podgląd wypożyczonych gier**  
  Pozwala użytkownikowi sprawdzić, które gry są aktualnie wypożyczone i do kiedy powinnien nastąpić ich zwrot.

- **Dodanie rezerwacji**  
  Użytkownik może zarezerwować wybraną grę, jeśli jest dostępna jej kopia. 

- **Oddanie gry**  
  Funkcja umożliwia zwrot wypożyczonej gry do wypożyczalni. Po oddaniu kopii gry, staje się ona ponownie dostępna dla innych użytkowników.

- **Sprawdzenie historii wypożyczeń**  
  Użytkownik ma dostęp do historii wszystkich swoich wypożyczeń, wraz z datami wypożyczenia i zwrotu oraz ewentualnymi karami.

- **Sortowanie i filtrowanie (widok)**  
  Pozwala na sortowanie i filtrowanie listy gier według różnych kryteriów, takich jak tytuł, kategoria, dostępność czy wydawca.

- **Suma kar oraz logowanie**  
  System umożliwia użytkownikowi zalogowanie się oraz sprawdzenie sumy naliczonych kar za nieterminowe zwroty.


## 2. Schemat i opis bazy

Poniżej znajduje się opis głównych tabel wykorzystanych w projekcie **GameLib**:

### members
Tabela przechowuje informacje o użytkownikach systemu.
- **member_id** – klucz główny, unikalny identyfikator użytkownika (autoinkrementacja)
- **first_name** – imię użytkownika
- **last_name** – nazwisko użytkownika
- **email** – adres e-mail (unikalny)
- **join_date** – data dołączenia do systemu (domyślnie bieżąca data)
- **street, house_number, postal_code, city, country** – dane adresowe użytkownika

### categories
Tabela przechowuje kategorie gier.
- **category_id** – klucz główny, unikalny identyfikator kategorii (autoinkrementacja)
- **category_name** – nazwa kategorii (unikalna)

### publishers
Tabela przechowuje wydawców gier.
- **publisher_id** – klucz główny, unikalny identyfikator wydawcy (autoinkrementacja)
- **company_name** – nazwa firmy wydawcy (unikalna)
- **city** – miasto siedziby wydawcy

### games
Tabela przechowuje informacje o grach.
- **game_id** – klucz główny, unikalny identyfikator gry (autoinkrementacja)
- **publisher_id** – klucz obcy do tabeli publishers

### game_copies
Tabela przechowuje informacje o poszczególnych kopiach gier dostępnych w wypożyczalni.
- **copy_id** – klucz główny, unikalny identyfikator kopii gry (autoinkrementacja)
- **game_id** – klucz obcy do tabeli games

### curr_borrowed
Tabela przechowuje informacje o aktualnie wypożyczonych grach.
- **borrow_id** – klucz główny, unikalny identyfikator wypożyczenia (autoinkrementacja)
- **member_id** – klucz obcy do tabeli members
- **copy_id** – klucz obcy do tabeli game_copies

### borrowed_hist
Tabela przechowuje historię wszystkich wypożyczeń.
- **hist_id** – klucz główny, unikalny identyfikator rekordu historii (autoinkrementacja)
- **member_id** – klucz obcy do tabeli members
- **copy_id** – klucz obcy do tabeli game_copies

### category_game_link
Tabela łącząca gry z kategoriami (relacja wiele-do-wielu).
- **category_id** – klucz obcy do tabeli categories
- **game_id** – klucz obcy do tabeli games

Każda z powyższych tabel posiada odpowiednie klucze główne i obce, zapewniające spójność danych oraz relacje pomiędzy encjami w systemie.

### Widoki

#### game_count_vw

Widok `game_count_vw` prezentuje listę wszystkich gier dostępnych w wypożyczalni wraz z liczbą dostępnych (niewypożyczonych) kopii każdej gry.  

**Kolumny widoku:**
- `game_id` – identyfikator gry
- `name` – nazwa gry
- `amount` – liczba dostępnych kopii (czyli tych, które nie są obecnie wypożyczone)

## 3. Encje Hibernate (Entities)

Każda encja w folderze `Maven\GameLib\src\main\java\Entities` odpowiada jednej tabeli w bazie danych i jest mapowana za pomocą adnotacji JPA/Hibernate. Poniżej opisano każdą encję, jej powiązanie z tabelą oraz główne funkcje i pola:

### Member (`members`)
- **Mapowanie:** Tabela `members`
- **Pola:** `memberId`, `firstName`, `lastName`, `email`, `joinDate`, `street`, `houseNumber`, `postalCode`, `city`, `country`
- **Opis:** Przechowuje dane użytkowników systemu.

### Category (`categories`)
- **Mapowanie:** Tabela `categories`
- **Pola:** `categoryId`, `categoryName`
- **Opis:** Reprezentuje kategorię gry. Pozwala na grupowanie gier według typu (np. przygodowe, logiczne).

### Publisher (`publishers`)
- **Mapowanie:** Tabela `publishers`
- **Pola:** `publisherId`, `companyName`, `city`
- **Opis:** Przechowuje dane wydawców gier. Pozwala powiązać gry z konkretnym wydawcą.

### Game (`games`)
- **Mapowanie:** Tabela `games`
- **Pola:** `gameId`, `name`, `publisher`, `releaseDate`
- **Opis:** Przechowuje informacje o grach, takie jak tytuł, wydawca i data wydania. 

### GameCopy (`game_copies`)
- **Mapowanie:** Tabela `game_copies`
- **Pola:** `copyId`, `game`
- **Opis:** Reprezentuje pojedynczą kopię gry dostępną w wypożyczalni. Umożliwia śledzenie dostępności egzemplarzy.

### CurrBorrowed (`curr_borrowed`)
- **Mapowanie:** Tabela `curr_borrowed`
- **Pola:** `borrowId`, `member`, `copy`, `borrowedDate`, `dueTo`
- **Opis:** Przechowuje informacje o aktualnie wypożyczonych grach, w tym kto i kiedy wypożyczył daną kopię oraz do kiedy powinna zostać zwrócona.

### BorrowedHist (`borrowed_hist`)
- **Mapowanie:** Tabela `borrowed_hist`
- **Pola:** `histId`, `member`, `copy`, `borrowedDate`, `dueTo`, `actualReturnDate`, `status`, `penalty`
- **Opis:** Przechowuje historię wszystkich wypożyczeń, w tym daty zwrotów i ewentualne kary za przetrzymanie gry.

### CategoryGameLink (`category_game_link`)
- **Mapowanie:** Tabela `category_game_link`
- **Pola:** `category`, `game`
- **Opis:** Realizuje relację wiele-do-wielu między grami a kategoriami. Pozwala przypisać grę do wielu kategorii i odwrotnie.

---

Każda encja posiada odpowiednie adnotacje JPA, relacje oraz metody umożliwiające wygodne zarządzanie danymi w aplikacji.

## 4. Warstwa dostępu do danych (DAO)

W folderze ``Maven\GameLib\src\main\java\dao` znajdują się klasy typu **Data Access Object (DAO)**, które odpowiadają za komunikację aplikacji z bazą danych. DAO oddzielają logikę biznesową od operacji na bazie, zapewniając czytelność i łatwość utrzymania kodu.

### Główne zadania DAO:
- Tworzenie, odczytywanie i usuwanie rekordów w bazie (operacje CRUD)
- Realizacja zapytań do bazy z użyciem JPA/Hibernate
- Obsługa transakcji i wyjątków związanych z bazą danych

### Przykładowe klasy DAO:

#### MemberDAO
- Odpowiada za operacje na encji `Member` (tabela `members`)
- Przykładowe metody: zalogowanie użytkownika

#### GameDAO
- Odpowiada za operacje na encji `Game` (tabela `games`)
- Przykładowe metody: pobieranie gier po kategorii, wyszukiwanie po tytule, lista dostępnych gier

#### BorrowedHistDAO
- Odpowiada za operacje na encji `BorrowedHist` (tabela `borrowed_hist`)
- Przykładowe metody: pobieranie historii wypożyczeń użytkownika, dodawanie nowego wpisu do historii

#### CurrBorrowedDAO
- Odpowiada za operacje na encji `CurrBorrowed` (tabela `curr_borrowed`)
- Przykładowe metody: wypożyczanie gry, zwracanie gry, sprawdzanie aktualnych wypożyczeń

---

DAO są kluczowym elementem architektury aplikacji, zapewniającym wydajny i bezpieczny dostęp do danych.

## 5. Pliki w folderze `sql`

W folderze `sql` znajdują się skrypty SQL służące do zarządzania strukturą i danymi bazy projektu **GameLib**. Każdy plik pełni określoną funkcję:

### Data.sql
Zawiera polecenia `INSERT`, które wstawiają przykładowe, testowe dane do wszystkich głównych tabel bazy. Umożliwia szybkie przygotowanie środowiska do testów i prezentacji działania aplikacji.

### DropTables.sql
Zawiera polecenia `DROP TABLE`, które usuwają wszystkie tabele z bazy danych. Skrypt ten pozwala na szybkie wyczyszczenie struktury bazy przed ponownym utworzeniem tabel.

### Flush.sql
Zawiera polecenia `TRUNCATE`, które usuwają wszystkie dane z tabel `curr_borrowed` oraz `borrowed_hist`. Skrypt ten pozwala na szybkie wyczyszczenie historii i bieżących wypożyczeń bez usuwania całej struktury bazy.

### Tables.sql
Zawiera polecenia `CREATE TABLE`, które tworzą wszystkie tabele wymagane przez aplikację. Skrypt ten definiuje strukturę bazy danych, klucze główne i obce oraz typy danych.

### Views.sql
Zawiera polecenie `CREATE VIEW`, które tworzy widok `game_count_vw`, prezentujący liczbę dostępnych kopii każdej gry.

---

Każdy z tych plików jest wykorzystywany na różnych etapach pracy z bazą danych: od tworzenia struktury, przez wstawianie danych testowych, po czyszczenie i usuwanie tabel.