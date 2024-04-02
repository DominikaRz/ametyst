/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 23.09.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej grupy.
 * Jest to tabela pomocnicza w menu. 
 * Do niej przypisane są wszystkie kategporie.
 */
package jwl.model.dict;

public class Group {
    protected int id;           //identyfikator grupy
    protected String name;      //nazwa grupy
    
//-------Konstruktory----------                         //konstruktory modelu
    public Group() {}      //konstruktor pusty
    
   //konstruktor pełny dla grupy
    public Group(int id, String name) {
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

//Wygląd bazy danych (tabela grupa):   
//Struktura tabeli dla tabeli `group-g`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `group-g`">                   
/** 
 * CREATE TABLE `group-g` (
 *   `id_group` int(11) NOT NULL,
 *   `name-g` varchar(30) COLLATE utf8_polish_ci NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `group-g`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `group-g`">                 
/** 
 * ALTER TABLE `group-g`
 *   ADD PRIMARY KEY (`id_group`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `group-g`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `group-g`">
/** 
 * ALTER TABLE `group-g`
 *   MODIFY `id_group` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
 */
//</editor-fold>