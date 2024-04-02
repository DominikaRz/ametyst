/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 30.05.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej kształtu.
 * Jest to tabela pomocnicza dla tabeli produktu.
 */
package jwl.model.dict;

public class Shape {
    protected int id;           //identyfikator kształtu
    protected String name;      //nazwa kształtu
    
//-------Konstruktory----------                         //konstruktory modelu
    public Shape() {}      //konstruktor pusty

   //konstruktor pełny dla kształtu
    public Shape(int id, String name) {
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
//Struktura tabeli dla tabeli `shape`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `shape`">                   
/** 
 * CREATE TABLE `shape` (
 *   `id_shap` int(11) NOT NULL,
 *   `name-sh` text COLLATE utf8_polish_ci NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `shape`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `shape`">                 
/** 
 * ALTER TABLE `shape`
 *   ADD PRIMARY KEY (`id_shap`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `shape`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `shape`">
/** 
 * ALTER TABLE `shape`
 *   MODIFY `id_shap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
 */
//</editor-fold>  
