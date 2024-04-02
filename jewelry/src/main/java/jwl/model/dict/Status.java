/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 30.05.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej statusu.
 * Obsługuje status zamówienia.
 */
package jwl.model.dict;

public class Status {
    protected int id;                   //identyfikator statusu
    protected String name;              //nazwa statusu
    protected String description;       //opis statusu
    
//-------Konstruktory----------                         //konstruktory modelu
    public Status() {}      //konstruktor pusty

   //konstruktor pełny dla statusu
    public Status(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
//-----------Getter's----------------
  //Identyfiaktor (PK - klucz podstawowy)
    public int getId() {
        return id;
    }
  //Nazwa
    public String getName() {
        return name;
    }
  //Opis
    public String getDescription() {
        return description;
    }

//------------Setter's--------------------
  //Identyfiaktor (PK - klucz podstawowy)
    public void setId(int id) {
        this.id = id;
    }
  //Nazwa
    public void setName(String name) {
        this.name = name;
    }
  //Opis
    public void setDescription(String description) {
        this.description = description;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `status`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `status`">                   
/** 
 * CREATE TABLE `status` (
 *   `id_stat` int(11) NOT NULL,
 *   `name-s` varchar(30) COLLATE utf8_polish_ci NOT NULL,
 *   `descr-s` text COLLATE utf8_polish_ci NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `status`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `status`">                 
/** 
 * ALTER TABLE `status`
 *   ADD PRIMARY KEY (`id_stat`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `status`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `status`">
/** 
 * ALTER TABLE `status`
 *   MODIFY `id_stat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
 */
//</editor-fold> 


