/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 30.05.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej materiału.
 * Jest to tabela pomocnicza dla tabeli produktu.
 */
package jwl.model.dict;

public class Fabric {
    protected int id;           //identyfikator materiału
    protected String name;      //nazwa materiału
    
//-------Konstruktory----------                         //konstruktory modelu
    public Fabric() {}      //konstruktor pusty
    
   //konstruktor pełny dla materiału
    public Fabric(int id, String name) {
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
//Struktura tabeli dla tabeli `fabric`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `fabric`">                   
/** 
 * CREATE TABLE `fabric` (
 *   `id_fabr` int(11) NOT NULL,
 *   `name-fb` text COLLATE utf8_polish_ci NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `fabric`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `fabric`">                 
/** 
 * ALTER TABLE `fabric`
 *   ADD PRIMARY KEY (`id_fabr`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `fabric`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `fabric`">
/** 
 * ALTER TABLE `fabric`
 *   MODIFY `id_fabr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;
 */
//</editor-fold> 
