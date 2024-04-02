/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 30.05.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej koloru.
 * Jest to tabela pomocnicza dla tabeli produktu.
 */
package jwl.model.dict;

public class Color {
    protected int id;           //identyfikator koloru
    protected String name;      //nazwa koloru
    
//-------Konstruktory----------                         //konstruktory modelu
    public Color() {}      //konstruktor pusty

   //konstruktor pełny dla koloru
    public Color(int id, String name) {
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
//Struktura tabeli dla tabeli `color`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `color`">                   
/** 
 * CREATE TABLE `color` (
 *   `id_col` int(11) NOT NULL,
 *   `name-cl` text COLLATE utf8_polish_ci NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `color`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `color`">                 
/** 
 * ALTER TABLE `color`
 *   ADD PRIMARY KEY (`id_col`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `color`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `color`">
/** 
 * ALTER TABLE `color`
 *   MODIFY `id_col` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
 */
//</editor-fold> 