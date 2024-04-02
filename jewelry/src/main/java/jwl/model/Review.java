/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 25.08.2021 r.
 * 
 * Model dla tabeli recenzje/komentarze.
 * Wystawianie recenzji produktów i mozliwość odpowiedzi dla pracownika na te recenzje.
 */

package jwl.model;

public class Review {
    protected int id;                   //identyfikator recenzji
    protected int idProd;               //identyfikator produktu dla którego recenzja jest wystawiana
    protected int idOrder;              //identyfikator zamówienia (po nim możemy zobaczyć kto wystawił recenzje)
    protected String name;              //nazwa recenzji
    protected int stars;                //ilość gwiazdek (1-5)
    protected String content;           //recenzja
    protected String publication;       //data wystawienia recenzji
    protected String reply;             //odpowiedź pracownika
    protected String response;          //data wystawienia odpowiedzi
    
      
//-------Konstruktory----------                         //konstruktory modelu
    public Review() {}      //konstruktor pusty
   
   //konstruktor dla wyświetlenia recenzji bez odpowiedzi
    public Review(int id, int idProd, int idOrder, String name, int stars, 
            String content, String publication) {
        this.id = id;
        this.idProd = idProd;
        this.idOrder = idOrder;
        this.name = name;
        this.stars = stars;
        this.content = content;
        this.publication = publication;
    }
   //konstruktor dla wyświetlenia recenzji z odpowiedzią 
    public Review(int id, int idProd, int idOrder, String name, int stars, String content, 
            String publication, String reply, String response) {
        this.id = id;
        this.idProd = idProd;
        this.idOrder = idOrder;
        this.name = name;
        this.stars = stars;
        this.content = content;
        this.publication = publication;
        this.reply = reply;
        this.response = response;
    }
   //konstruktor dla odpowiedzi na recenzje
    public Review(int id, String reply, String response) {
        this.id = id;
        this.reply = reply;
        this.response = response;
    }
    
//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfikator recenzji (PK - klucz podstawowy)
    public int getId() {
        return id;
    }
  //Identyfikator produktu (FK - klucz obcy) 
    public int getIdProd() {
        return idProd;
    }
  //Identyfikator zamówienia  (FK - klucz obcy)
    public int getIdOrder() {
        return idOrder;
    }
  //Nazwa recenzji
    public String getName() {
        return name;
    }
  //Ilość gwiazdek
    public int getStars() {
        return stars;
    }
  //Treść recenzji
    public String getContent() {
        return content;
    }
  //Data publikacji recenzji
    public String getPublication() {
        return publication;
    }
  //Odpowiedź od pracownika 
    public String getReply() {
        return reply;
    }
  //Data publikacji odpowiedzi
    public String getResponse() {
        return response;
    }
    
//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfikator recenzji (PK - klucz podstawowy)
    public void setId(int id) {
        this.id = id;
    }
  //Identyfikator produktu (FK - klucz obcy)
    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }
  //Identyfikator zamówienia  (FK - klucz obcy)
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
  //Nazwa recenzji
    public void setName(String name) {
        this.name = name;
    }
  //Ilość gwiazdek
    public void setStars(int stars) {
        this.stars = stars;
    }
  //Treść recenzji
    public void setContent(String content) {
        this.content = content;
    }
  //Data publikacji recenzji
    public void setPublication(String publication) {
        this.publication = publication;
    }
  //Odpowiedź od pracownika 
    public void setReply(String reply) {
        this.reply = reply;
    }
  //Data publikacji odpowiedzi
    public void setResponse(String response) {
        this.response = response;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `review`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `review`">                   
/** 
 * CREATE TABLE `review` (
 *   `id_rev` int(11) NOT NULL,
 *   `id_prod-rw` int(11) NOT NULL,
 *   `id_order-rw` int(11) NOT NULL,
 *   `name-rw` varchar(30) COLLATE utf8_polish_ci NOT NULL,
 *   `stars` int(11) NOT NULL,
 *   `content` varchar(1000) COLLATE utf8_polish_ci NOT NULL,
 *   `publication` datetime NOT NULL,
 *   `reply` text COLLATE utf8_polish_ci DEFAULT NULL,
 *   `response` datetime DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `review`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `review`">                 
/** 
 * ALTER TABLE `review`
 *   ADD PRIMARY KEY (`id_rev`),
 *   ADD KEY `id_order-rw` (`id_order-rw`) USING BTREE,
 *   ADD KEY `id_prod-rw` (`id_prod-rw`) USING BTREE;
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `review`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `review`">
/** 
 * ALTER TABLE `review`
 *   MODIFY `id_rev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
 */
//</editor-fold> 
//Ograniczenia dla tabeli `review`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `review`">
/** 
 * ALTER TABLE `review`
 *   ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`id_order-rw`) REFERENCES `order-o` (`id_order`),
 *   ADD CONSTRAINT `review_ibfk_3` FOREIGN KEY (`id_prod-rw`) REFERENCES `product` (`id_prod`);
 */
//</editor-fold> 
