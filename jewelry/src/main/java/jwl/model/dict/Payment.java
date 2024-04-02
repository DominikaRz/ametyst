/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 28.05.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej płatności.
 * Jest to tabela pomocnicza dla tabeli zamówienie.
 */
package jwl.model.dict;

public class Payment {
    protected int id;               //identyfikator płatności
    protected String name;          //nazwa płatności
    protected String category;      //kategoria płatności

//-------Konstruktory----------                         //konstruktory modelu
    public Payment() {}      //konstruktor pusty

   //konstruktor pełny dla płatności
    public Payment(int id, String name, String cat) {
        this.id = id;
        this.name = name;
        this.category = cat;
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
  //Kategoria
   public String getCategory() {
        return category;
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
  //Kategoria
   public String setCategory() {
        return category;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `payment`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `payment`">                   
/** 
 * CREATE TABLE `payment` (
 *   `id_pay` int(11) NOT NULL,
 *   `name-py` text COLLATE utf8_polish_ci NOT NULL,
 *   `cat-py` varchar(20) COLLATE utf8_polish_ci NOT NULL,
 *   `logo-py` text COLLATE utf8_polish_ci DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `payment`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `payment`">                 
/** 
 * ALTER TABLE `payment`
 *   ADD PRIMARY KEY (`id_pay`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `payment`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `payment`">
/** 
 * ALTER TABLE `payment`
 *   MODIFY `id_pay` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
 */
//</editor-fold> 