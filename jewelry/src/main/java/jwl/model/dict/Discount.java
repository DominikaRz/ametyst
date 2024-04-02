/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 24.08.2021 r.
 * 
 * Model stworzony dla tabeli słownikowej kodu promocyjnego.
 * Jest to tabela pomocnicza dla tabeli zamówienie.
 */

package jwl.model.dict;

public class Discount {
    protected int id;               //identyfikator kodu promocyjnego
    protected String name;          //nazwa kodu promocyjnego
    protected boolean active;       //znacznik informujący o aktywnym kodzie promocyjneym
    protected int value;            //procent znizki

//-------Konstruktory----------                         //konstruktory modelu
    public Discount() {}      //konstruktor pusty

   //konstruktor pełny dla kodu promocyjnego
    public Discount(int id, String name, boolean active, int value) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.value = value;
    }
   //konstruktor dla nazwy, identyfikatora oraz aktywności kodu promocyjnego
    public Discount(int id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }
   //konstruktor dla identyfikatora i aktywności kodu promocyjnego
    public Discount(int id, boolean active) {
        this.id = id;
        this.active = active;
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
  //Znacznik aktywności
    public boolean isActive() {
        return active;
    }
  //Procent zniżki
    public int getValue() {
        return value;
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
  //Znacznik aktywności
    public void setActive(boolean active) {
        this.active = active;
    }
  //Procent zniżki
    public void setValue(int value) {
        this.value = value;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `discount`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `discount`">                   
/** 
 * CREATE TABLE `discount` (
 *   `id_disc` int(11) NOT NULL,
 *   `name-dc` varchar(50) COLLATE utf8_polish_ci NOT NULL,
 *   `active` tinyint(1) NOT NULL,
 *   `value` int(11) NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `discount`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `discount`">                 
/** 
 * ALTER TABLE `discount`
 *   ADD PRIMARY KEY (`id_disc`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `discount`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `discount`">
/** 
 * ALTER TABLE `discount`
 *   MODIFY `id_disc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
 */
//</editor-fold> 
