/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 30.05.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej rangi.
 * Każdy pracownik ma swoją rangę. 
 * Ta tabela przechowuje nazwy rang wszystkich uzytkowników zalogowanych.
 */
package jwl.model.dict;

public class Rank {
    protected int id;           //identyfikator rangi
    protected String name;      //nazwa rangi
    
//-------Konstruktory----------                         //konstruktory modelu
    public Rank() {}       //konstruktor pusty

   //konstruktor pełny dla rangi
    public Rank(int id, String name) {
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
//Struktura tabeli dla tabeli `rank`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `rank`">                   
/** 
 * CREATE TABLE `rank` (
 *   `id_rank` int(11) NOT NULL,
 *   `name-r` text COLLATE utf8_polish_ci NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `rank`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `rank`">                 
/** 
 * ALTER TABLE `rank`
 *   ADD PRIMARY KEY (`id_rank`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `rank`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `rank`">
/** 
 * ALTER TABLE `rank`
 *   MODIFY `id_rank` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
 */
//</editor-fold> 