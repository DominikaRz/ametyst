/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 25.08.2021 r.
 * 
 * Model dla tabeli historii zdarzeń.
 * Opiera się o zmiany w bazie danych. Większość najważniejszych zmian można cofnąć 
 * za pomocą odpowiednich przycisków znajdujących się w widoku aplikacji.
 */

package jwl.model;

public class History {
    protected int id;                       //idemtyfikator zdarzenia
    protected int idUser;                   //identyfikator uzytkownika, który wykonał akcje
    protected int idAct;                    //identyfikator akcje
    protected String description;           //opis zdarzenia
    protected String date;                  //data wykonania operacji
    protected String query_b;               //zapytanie MySQL przed zmianą
    protected String query_a;               //zapytanie MySQL po zmianie
    protected String modify;                //zmienna modyfikacji (co zostało zmienione)
    
//-------Konstruktory----------                         //konstruktory modelu
    public History() {}      //konstruktor pusty

   //konstruktor dla zdarzenia bez modyfikacji 
    public History(int id, int idUser, int idAct, String description, String date, 
            String query_b, String query_a) {
        this.id = id;
        this.idUser = idUser;
        this.idAct = idAct;
        this.description = description;
        this.date = date;
        this.query_b = query_b;
        this.query_a = query_a;
    }
   //konstruktor dla zdarzenia z modyfikacją
    public History(int id, int idUser, int idAct, String description, String date, 
            String query_b, String query_a, String modify) {
        this.id = id;
        this.idUser = idUser;
        this.idAct = idAct;
        this.description = description;
        this.date = date;
        this.query_b = query_b;
        this.query_a = query_a;
        this.modify = modify;
    }
  //konstruktor dla zdarzenia bez zapytań MySQL
    public History(int id, int idUser, int idAct, String description, String date) {
        this.id = id;
        this.idUser = idUser;
        this.idAct = idAct;
        this.description = description;
        this.date = date;
    }
  //konstruktor dla ustawienia zapytań
    public History(int id, String query_b, String query_a) {
        this.id = id;
        this.query_b = query_b;
        this.query_a = query_a;
    }
    
//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfiaktor histroii (PK - klucz podstawowy)
    public int getId() {
        return id;
    }
  //Identyfiaktor dla użytkownika zmieniającego (FK - klucz obcy)
    public int getIdUser() {
        return idUser;
    }
  //Identyfiaktor akcji (FK - klucz obcy)
    public int getIdAct() {
        return idAct;
    }
  //Opis akcji
    public String getDescription() {
        return description;
    }
  //Data modyfikacji
    public String getDate() {
        return date;
    }
  //Zapytanie przed zmianą
    public String getQuery_b() {
        return query_b;
    }
  //Zapytanie po zmianie
    public String getQuery_a() {
        return query_a;
    }
  //Co zmieniono
    public String getModify() {
        return modify;
    }
    
//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfiaktor histroii (PK - klucz podstawowy)
    public void setId(int id) {
        this.id = id;
    }
  //Identyfiaktor dla użytkownika zmieniającego (FK - klucz obcy)
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
  //Identyfiaktor akcji (FK - klucz obcy)
    public void setIdAct(int idAct) {
        this.idAct = idAct;
    }
  //Opis akcji
    public void setDescription(String description) {
        this.description = description;
    }
  //Data modyfikacji
    public void setDate(String date) {
        this.date = date;
    }
  //Zapytanie przed zmianą
    public void setQuery_b(String query_b) {
        this.query_b = query_b;
    }
  //Zapytanie po zmianie
    public void setQuery_a(String query_a) {
        this.query_a = query_a;
    }
  //Co zmieniono
    public void setModify(String modify) {
        this.modify = modify;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `history`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `history`">                   
/** 
 * CREATE TABLE `history` (
 *   `id_his` int(11) NOT NULL,
 *   `id_user-h` int(11) NOT NULL,
 *   `id_act-h` int(11) NOT NULL,
 *   `description-h` text COLLATE utf8_polish_ci NOT NULL,
 *   `date-h` datetime NOT NULL,
 *   `query_before-h` text COLLATE utf8_polish_ci NOT NULL,
 *   `query_after-h` text COLLATE utf8_polish_ci NOT NULL,
 *   `modify-h` text COLLATE utf8_polish_ci DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `history`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `history`">                 
/** 
 * ALTER TABLE `history`
 *   ADD PRIMARY KEY (`id_his`),
 *   ADD KEY `id_user-h` (`id_user-h`),
 *   ADD KEY `id_act-h` (`id_act-h`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `history`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `history`">
/** 
 * ALTER TABLE `history`
 *   MODIFY `id_his` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=373;
 */
//</editor-fold> 
//Ograniczenia dla tabeli `history`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `history`">
/** 
 * ALTER TABLE `history`
 *   ADD CONSTRAINT `history_ibfk_1` FOREIGN KEY (`id_act-h`) REFERENCES `action` (`id_action`),
 *   ADD CONSTRAINT `history_ibfk_2` FOREIGN KEY (`id_user-h`) REFERENCES `user` (`id_user`);
 */
//</editor-fold> 


