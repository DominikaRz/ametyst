/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 30.05.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej dostaw.
 * Jest to tabela pomocnicza dla tabeli zamówienia.
 */
package jwl.model.dict;

public class Delivery {
    protected int id;           //identyfikator dostawy
    protected String name;      //nazwa dostawy
    protected double price;     //cena dostawy
    
//-------Konstruktory----------                         //konstruktory modelu
    public Delivery() {}      //konstruktor pusty
    
   //konstruktor pełny dla dostawy
    public Delivery(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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
  //Cena
    public double getPrice() {
        return price;
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
  //Cena
    public double setPrice() {
        return price;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `delivery`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `delivery`">                   
/** 
 * CREATE TABLE `delivery` (
 *   `id_deliv` int(11) NOT NULL,
 *   `name-d` text COLLATE utf8_polish_ci NOT NULL,
 *   `price-d` float NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli ``                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `delivery`">                 
/** 
 * ALTER TABLE `delivery`
 *   ADD PRIMARY KEY (`id_deliv`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `delivery`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `delivery`">
/** 
 * ALTER TABLE `delivery`
 *   MODIFY `id_deliv` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
 */
//</editor-fold> 