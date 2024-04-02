/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * dated: 25.08.2021 r.
 * 
 * Tabela opiera się o sesje w przypadku użytkownika niezalogowanego oraz
 * o klucz użytkownika zalogowanego. Jest to tabela tymczasowa.
 */

package jwl.model.link;

public class Basket {
    protected String session;       //identyfikator sesji w formie tablicy znaków (String)
    protected int idUser;           //identyfikator użytkownika zalogowanego (klucz obcy z opcją pustą (NULL))
    protected int idProd;           //identyfikator produktu (klucz obcy)
    protected int quantity;         //ilość danego produktu w koszyku
    protected String date;          //data wygaśnięcia sesji

    
//-------Konstruktory----------                         //konstruktory modelu
    public Basket() {}      //konstruktor pusty
    
   //konstruktor podstawowy 
    public Basket(String session, int id_user, int id_prod, int quantity, String date) {
        this.session = session;
        this.idUser = id_user;
        this.idProd = id_prod;
        this.quantity = quantity;
        this.date = date;
    }
   //konstruktor dla użytkownika niezalogowanego 
    public Basket(String session, int id_prod, int quantity, String date) {
        this.session = session;
        this.idProd = id_prod;
        this.quantity = quantity;
        this.date = date;
    }
   //konstruktor dla użytkownika zalogowanego (muszą się różnić aby się nie nadpisywały)
    public Basket(int id_user, int id_prod, int quantity) {
        this.idUser = id_user;
        this.idProd = id_prod;
        this.quantity = quantity;
    }
   //konstruktor dla sesji
    public Basket(String session, String date) {
        this.session = session;
        this.date = date;
    }
    
    
//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfikator sesji (pliki cookies)
    public String getSession() {
        return session;
    }
  //Identyfikator użytkownika (FK - klucz obcy)
    public int getIdUser() {
        return idUser;
    }
  //Identyfikator produktu (FK - klucz obcy)
    public int getIdProd() {
        return idProd;
    }
  //Ilość elementu w koszyku
    public int getQuantity() {
        return quantity;
    }
  //Data utworzenia 
    public String getDate() {
        return date;
    }
//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfikator sesji (pliki cookies)
    public void setSession(String session) {
        this.session = session;
    }
  //Identyfikator użytkownika (FK - klucz obcy)
    public void setIdUser(int id_user) {
        this.idUser = id_user;
    }
  //Identyfikator produktu (FK - klucz obcy)
    public void setIdProd(int id_prod) {
        this.idProd = id_prod;
    }
  //Ilość elementu w koszyku
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  //Data utworzenia 
    public void setDate(String date) {
        this.date = date;
    }
}
//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `basket`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `basket`">                   
/** 
 * CREATE TABLE `basket` (
 *  `id_session-b` text COLLATE utf8_polish_ci DEFAULT NULL,
 *  `id_user-b` int(11) DEFAULT NULL,
 *  `id_prod-b` int(11) NOT NULL,
 *  `quantity-b` int(11) NOT NULL,
 *  `expires-b` datetime DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `basket`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `basket`">                 
/** 
 * ALTER TABLE `basket`
 *   ADD KEY `id_prod-b` (`id_prod-b`),
 *   ADD KEY `id_user-b` (`id_user-b`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `basket`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `basket`">
/** 
 * ALTER TABLE `basket`
 *   ADD CONSTRAINT `basket_ibfk_3` FOREIGN KEY (`id_prod-b`) REFERENCES `product` (`id_prod`),
 *   ADD CONSTRAINT `basket_ibfk_4` FOREIGN KEY (`id_user-b`) REFERENCES `user` (`id_user`);
 */
//</editor-fold> 