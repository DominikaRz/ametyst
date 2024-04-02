/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 25.08.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej akcji.
 * Jest to tabela pomocnicza dla tabeli historii.
 */
package jwl.model.dict;

public class Action {
    protected int id;           //identyfikator akcji
    protected String name;      //nazwa akcji
    
//-------Konstruktory----------                         //konstruktory modelu
    public Action() {}      //konstruktor pusty

   //konstruktor pełny dla akcji
    public Action(int id, String name) {
        this.id = id;
        this.name = name;
    }
        
//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfiaktor (PK - klucz podstawowy)
    public int getId() {
        return id;
    }
  //Nazwa
    public String getName() {
        return name;
    }

//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfiaktor (PK - klucz podstawowy)
    public void setId(int id) {
        this.id = id;
    }
  //Nazwa
    public void setName(String name) {
        this.name = name;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `action`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `action`">                   
/** 
 * CREATE TABLE `action` (
 *   `id_action` int(11) NOT NULL,
 *   `name-a` varchar(40) COLLATE utf8_polish_ci NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `action`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `action`">                 
/** 
 * ALTER TABLE `action`
 *   ADD PRIMARY KEY (`id_action`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `action`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `action`">
/** 
 * ALTER TABLE `action`
 *   MODIFY `id_action` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
 */
//</editor-fold> 
