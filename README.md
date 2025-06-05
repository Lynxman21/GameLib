# Auorzy: Mateusz Ryś, Łukasz Krementowski
## Temat projektu: Wypożyczalnia gier
Projekt na przedmiot Bazy Danych 2

### Legenda
- ✅ - Funkcja gotowa
- ✔️ - Funkcja prawie gotowa (do konsultacji, małe poprawki)
- 🚧 - Work in progress
- 🛑 - Funkcja nie została rozpoczęta
### Pomysły na funkcje

- Przeglądanie dostępnych gier ✔️
- Podgląd co wypożyczone ✔️
- dodanie rezerwacji ✅
- oddanie gry ✔️
- sprawdzenie historii ✔️
- sorty\filtrowanie (widok) ✔️
- suma kar oraz login ✔️

## Jak działają klasy:
### CurrBorrowedDAO
- ```java 
    public void addBorrowedRecord(int gameId, int memberId, LocalDate borrowedDate, LocalDate dueTo)
    ```  
    Metoda przyjmuje ID użytkownika, ID gry którą wypożycza, datę kiedy to wypożyczył oraz datę do kiedy ma nastąpić zwrot. Jeśli parametry się zgadzają, to do tabeli CurrBorrowed dodaje się wiersz o podanych parametrach. Funkcja automatycznie przeszukuje czy gra ma dostępne kopie oraz sprawdza czy użytkownik o podanym ID istnieje.

- ```java 
    public void removeBorrowedRecord(int borrowedId)
    ```  
    Metoda usuwa record o podanym ID. Przydatne w momencie oddania gry.

### BorrowedHistDAO
- ```java 
    public void addBorrowedHistoryRecord(int currBorrowedId)
    ```
    Metoda przyjmuje ID z recordu z tabeli CurrBorrowed i zczytuje z niego dane, zapisując je wszystkie. Narazie nie ma systemu kary ani innych rzeczy zaimplementowanych. (Można zrobić że jak ktoś oddał bo dueTo to po prostu stała kara 20 zł albo coś żeby łatwo było)

- ```java 
    public List<BorrowedHist> checkBorrowedHistForMember(Member member)
    ```
    Metoda zwraca listę wierszy powiązanych z podanym użytkownikiem (NIE ID!!). Tutaj miałem właśnie wątpliwości i musimy to przegadać, ale nie wiem czy lepiej jest operować na ID jako intach po prostu czy przekazywać całe obiekty. To jest do dyskusji. 

### DataGenerator i DatabaseConnectionTester
Klasy testowe, nie będą używane później w projekcie. 
