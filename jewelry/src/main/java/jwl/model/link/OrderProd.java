/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 01.06.2021 r.
 * 
 * Tabela łączy zamówienia z produktami, które były dodane do koszyka użytkownika.
 * Po zatwierdzeniu zamówienia produkty z koszyka wędrują do tej stałej tabeli i zostana tu na stałe.
 */
package jwl.model.link;

import jwl.model.Product;

public class OrderProd extends Product{
    protected int idOrder;              //identyfikator zamówienia jako klucz obcy prowadzący do tabeli zamówienie
    protected int idProd;               //identyfikator produktu jako klucz obcy prowadzący do tabeli produkt
    protected int quantity;             //ilość zamówionych produktów
    protected int discount;             //wielkość zniżki w momencie zamawiania produktu (ulega zmianie alatego musi być tu dodana)
    
//-------Konstruktory----------                         //konstruktory modelu
    public OrderProd() {}      //konstruktor pusty       

   //konstruktor bez uwzględnienia przeceny  
    public OrderProd(int idOrder, int idProd, int quantity) {
        this.idOrder = idOrder;
        this.idProd = idProd;
        this.quantity = quantity;
    }
   //konstruktor z uwzględnieniem przeceny  
    public OrderProd(int idOrder, int idProd, int quantity, int discount) {
        this.idOrder = idOrder;
        this.idProd = idProd;
        this.quantity = quantity;
        this.discount = discount;
    }

//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfikator zamówienia (FK - klucz obcy)
    public int getIdOrder() {
        return idOrder;
    }
  //Identyfikator produktu (FK - klucz obcy)
    public int getIdProd() {
        return idProd;
    }
  //Ilość dodanych produktów
    public int getQuantity() {
        return quantity;
    }
  //Przecena w momencie zakupu 
    public int getDiscount() {
        return discount;
    }

//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfikator zamówienia (FK - klucz obcy)
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
  //Identyfikator produktu (FK - klucz obcy)
    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }
  //Ilość dodanych produktów
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  //Przecena w momencie zakupu 
    public void setDiscount(int discount) {
        this.discount = discount;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `order-product`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `order-product`">                   
/** 
 * CREATE TABLE `order-product` (
 *   `id_order-op` int(11) NOT NULL,
 *   `id_prod-op` int(11) NOT NULL,
 *   `quantity-op` int(11) NOT NULL,
 *   `discount-op` int(11) NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `order-product`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `order-product`">                 
/** 
 * ALTER TABLE `order-product`
 *   ADD KEY `id_prod-op` (`id_prod-op`),
 *   ADD KEY `id_order-op` (`id_order-op`);
 */
//</editor-fold> 
//Ograniczenia dla tabeli `order-product`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `order-product`">
/** 
 * ALTER TABLE `order-product`
 *   ADD CONSTRAINT `order-product_ibfk_2` FOREIGN KEY (`id_order-op`) REFERENCES `order-o` (`id_order`),
 *   ADD CONSTRAINT `order-product_ibfk_3` FOREIGN KEY (`id_prod-op`) REFERENCES `product` (`id_prod`);
 */
//</editor-fold>