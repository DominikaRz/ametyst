/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 04.06.2021 r.
 * 
 * Model dla tabeli produktu z podstawowymi danymi produktu.
 */
package jwl.model;

public class Product {
    protected int id;           //identyfikator produktu
    protected String name;      //nazwa produktu
    protected int images;       //ilość zdjęć produktu
    protected int idCattag;     //przypisanie produktu do menu (grupa -> kategoria -> tag)
    protected float price;      //cena produktu
    
//-------Konstruktory----------                         //konstruktory modelu
    public Product() {}      //konstruktor pusty

   //konstruktor podsatwowy
    public Product(int id, String name, int images, int idCattag, float price) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.idCattag = idCattag;
        this.price = price;
    }
   //konstruktor dla wyświetlania metadanych 
    public Product(int id, String name, int img, int idCattag) {
        this.id = id;
        this.name = name;
        this.images = img;
        this.idCattag = idCattag;
    }
   //konstruktor dla zmiany ilości obrazów 
    public Product(int id, int images) {
        this.id = id;
        this.images = images;
    }
    
//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfikator produktu (PK - klucz podstawowy) 
    public int getId() {
        return id;
    }
  //Nazwa
    public String getName() {
        return name;
    }
  //Ilość obrazów
    public int getImages() {
        return images;
    }
  //Identyfikator tabeli łącznikowej z menu (FK - klucz obcy)
    public int getIdCattag() {
        return idCattag;
    }
  //Cena
    public float getPrice() {
        return price;
    }
    
//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfikator produktu (PK - klucz podstawowy)
    public void setId(int id) {
        this.id = id;
    }
  //Nazwa
    public void setName(String name) {
        this.name = name;
    }
  //Ilość obrazów
    public void setImages(int images) {
        this.images = images;
    }
  //Identyfikator tabeli łącznikowej z menu (FK - klucz obcy)
    public void setIdCattag(int idCattag) {
        this.idCattag = idCattag;
    }
  //Cena
    public void setPrice(float price) {
        this.price = price;
    } 
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `product`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `product`">                   
/** 
 * CREATE TABLE `product` (
 *   `id_prod` int(11) NOT NULL,
 *   `name-p` text COLLATE utf8_polish_ci NOT NULL,
 *   `images-p` int(11) NOT NULL,
 *   `id_catag-p` int(11) NOT NULL,
 *   `price-p` float NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `product`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `product`">                 
/** 
 * ALTER TABLE `product`
 *   ADD PRIMARY KEY (`id_prod`),
 *   ADD KEY `id_catag-p` (`id_catag-p`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `product`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `product`">
/** 
 * ALTER TABLE `product`
 *   MODIFY `id_prod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
 */
//</editor-fold> 
//Ograniczenia dla tabeli `product`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `product`">
/** 
 * ALTER TABLE `product`
 *   ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`id_catag-p`) REFERENCES `category-tag` (`id_catag`);
 */
//</editor-fold> 


