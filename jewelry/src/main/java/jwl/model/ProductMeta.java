/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 04.06.2021 r.
 * 
 * Model dla tabeli produkt metadane.
 * Metadane produktu, po których możemy wywnioskować co to za produkt.
 */
package jwl.model;

public class ProductMeta extends Product{
    protected int idProd;           //identyfikator produktu
    protected float height;         //wysokość produktu
    protected float width;          //szerokość produktu
    protected float lenght;         //długość produktu
    protected float hole;           //wielkość otworu produktu
    protected float weight;         //waga produktu
    protected float diameter;       //śerdnica
    protected int idFabr;           //identyfikator materiału produktu
    protected int idShap;           //identyfikator kształtu produktu
    protected int idCol;            //identyfikator koloru produktu
    protected String description;   //opis produktu
    protected int quantityState;    //ilość na stanie produktu
    protected int quantity;         //ilość w opakowaniu produktu
    protected int vat;              //VAT produktu
    protected int discount;         //przecena produktu
    protected String create;        //data utworzenia produktu
    protected String restock;       //data pojawienia się produktu ponownie w sprzedaży
    protected String source;        //źródło produktu

//-------Konstruktory----------                         //konstruktory modelu
    public ProductMeta() {}      //konstruktor pusty

   //konstruktor pełny
    public ProductMeta(int idProd, float height, float width, float lenght, float hole, 
            float weight, float diameter, int idFabr, int idShap, int idCol, 
            String description,int quantityState, int quantity, int vat, int discount, 
            String create, String restock, String source) {
        this.idProd = idProd;
        this.height = height;
        this.width = width;
        this.lenght = lenght;
        this.hole = hole;
        this.weight = weight;
        this.diameter = diameter;
        this.idFabr = idFabr;
        this.idShap = idShap;
        this.idCol = idCol;
        this.description = description;
        this.quantityState = quantityState;
        this.quantity = quantity;
        this.vat = vat;
        this.discount = discount;
        this.create = create;
        this.restock = restock;
        this.source = source;
    }
   //konstruktor z danymi z tabeli produkt 
    public ProductMeta(int idProd, float height, float width, float lenght, float hole, 
            float weight, float diameter, int idFabr, int idShap, int idCol, 
            String description, int quantityState, int quantity, int vat, 
            int discount, String create, String restock, String source, int id, 
            String name, int images, int idCattag, float price) {
        super(id, name, images, idCattag, price);
        this.idProd = idProd;
        this.height = height;
        this.width = width;
        this.lenght = lenght;
        this.hole = hole;
        this.weight = weight;
        this.diameter = diameter;
        this.idFabr = idFabr;
        this.idShap = idShap;
        this.idCol = idCol;
        this.description = description;
        this.quantityState = quantityState;
        this.quantity = quantity;
        this.vat = vat;
        this.discount = discount;
        this.create = create;
        this.restock = restock;
        this.source = source;
    }
   //konstruktor dla wyświetlania produktu z menu oraz nawigacji
    public ProductMeta(int id, String name, int images, int idCattag, float price, int discount, int state, int color, int shape) {
        super(id, name, images, idCattag, price);
        this.discount = discount;
        this.quantityState = state;
        this.idCol = color;
        this.idShap = shape;
    }
   //konstruktor podsatwowych danych produktu 
    public ProductMeta(int id, String name, int images, int idCattag, float price, int discount) {
        super(id, name, images, idCattag, price);
        this.discount = discount;
    }
   //konstruktor dla wyświetlania dla recenzenta
    public ProductMeta(int id, String name, int img, int idCattag,String description) {
        super(id, name, img, idCattag);
        this.description = description;
    }
   //konstruktor dla aktualizacji produktu przez zaopatrzeniowca
    public ProductMeta(int idProd, float height, float width, float lenght, float hole, 
            float weight, float diameter, int idFabr, int idShap, int idCol, String description, 
            int quantityState, int quantity, int discount, String source) {
        this.idProd = idProd;
        this.height = height;
        this.width = width;
        this.lenght = lenght;
        this.hole = hole;
        this.weight = weight;
        this.diameter = diameter;
        this.idFabr = idFabr;
        this.idShap = idShap;
        this.idCol = idCol;
        this.description = description;
        this.quantityState = quantityState;
        this.quantity = quantity;
        this.discount = discount;
        this.source = source;
    }
   //konstruktor dla dodawania produktu do bazy danych 
    public ProductMeta(int idProd, float height, float width, float lenght, float hole, 
            float weight, float diameter, int idFabr, int idShap, int idCol, String description, 
            int quantityState, int quantity, int discount, String create, String source) {
        this.idProd = idProd;
        this.height = height;
        this.width = width;
        this.lenght = lenght;
        this.hole = hole;
        this.weight = weight;
        this.diameter = diameter;
        this.idFabr = idFabr;
        this.idShap = idShap;
        this.idCol = idCol;
        this.quantityState = quantityState;
        this.description = description;
        this.quantity = quantity;
        this.discount = discount;
        this.create = create;
        this.source = source;
    }
   //konstruktor do sprawdzania ilości na stanie
    public ProductMeta(int idProd, int quantityState, String restock) {
        this.idProd = idProd;
        this.restock = restock;
        this.quantityState = quantityState;
    }
   //podstawowe dane produktu dla koszyka 
    public ProductMeta(int id, String name, int images, int idCattag, Float price, 
            int discount, int quantityState) {
        super(id, name, images, idCattag, price);
        this.quantityState = quantityState;
        this.discount = discount;
    }

//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfikator produktu (FK - klucz obcy)   
    public int getIdProd() {
        return idProd;
    }
  //Wysokość
    public float getHeight() {
        return height;
    }
  //Szerokość
    public float getWidth() {
        return width;
    }
  //Długość
    public float getLenght() {
        return lenght;
    }
  //Wielkość otworu
    public float getHole() {
        return hole;
    }
  //Waga
    public float getWeight() {
        return weight;
    }
  //Średnica
    public float getDiameter() {
        return diameter;
    }
  //Identyfikator materiału (FK - klucz obcy)  
    public int getIdFabr() {
        return idFabr;
    }
  //Identyfikator kształtu (FK - klucz obcy) 
    public int getIdShap() {
        return idShap;
    }
  //Identyfikator koloru (FK - klucz obcy) 
    public int getIdCol() {
        return idCol;
    }
  //Opis
    public String getDescription() {
        return description;
    }
  //Ilość na stanie
    public int getQuantityState() {
        return quantityState;
    }
  //Ilość w opakowaniu
    public int getQuantity() {
        return quantity;
    }
  //VAT
    public int getVat() {
        return vat;
    }
  //Przecena
    public int getDiscount() {
        return discount;
    }
  //Data stworzenia
    public String getCreate() {
        return create;
    }
  //Data ponowienia produktu
    public String getRestock() {
        return restock;
    }
  //Źródło produktu
    public String getSource() {
        return source;
    }
    
//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfikator produktu (FK - klucz obcy)
    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }
  //Wysokość
    public void setHeight(float height) {
        this.height = height;
    }
  //Szerokość
    public void setWidth(float width) {
        this.width = width;
    }
  //Długość
    public void setLenght(float lenght) {
        this.lenght = lenght;
    }
  //Wielkość otworu
    public void setHole(float hole) {
        this.hole = hole;
    }
  //Waga
    public void setWeight(float weight) {
        this.weight = weight;
    }
  //Średnica
    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }
  //Identyfikator materiału (FK - klucz obcy)
    public void setIdFabr(int idFabr) {
        this.idFabr = idFabr;
    }
  //Identyfikator kształtu (FK - klucz obcy)
    public void setIdShap(int idShap) {
        this.idShap = idShap;
    }
  //Identyfikator koloru (FK - klucz obcy) 
    public void setIdCol(int idCol) {
        this.idCol = idCol;
    }
  //Opis
    public void setDescription(String description) {
        this.description = description;
    }
  //Ilość na stanie
    public void setQuantityState(int quantityState) {
        this.quantityState = quantityState;
    }
  //Ilość w opakowaniu
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  //VAT
    public void setVat(int vat) {
        this.vat = vat;
    }
  //Przecena
    public void setDiscount(int discount) {
        this.discount = discount;
    }
  //Data stworzenia
    public void setCreate(String create) {
        this.create = create;
    }
  //Data ponowienia produktu
    public void setRestock(String restock) {
        this.restock = restock;
    }
  //Źródło produktu
    public void setSource(String source) {
        this.source = source;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `product-meta`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `product-meta`">                   
/** 
 * CREATE TABLE `product-meta` (
 *   `id_prod-m` int(11) NOT NULL,
 *   `height` float DEFAULT NULL,
 *   `width` float DEFAULT NULL,
 *   `lenght` float DEFAULT NULL,
 *   `hole` float DEFAULT NULL,
 *   `weight` float DEFAULT NULL,
 *   `diameter` float DEFAULT NULL,
 *   `id_fabr-m` int(11) NOT NULL,
 *   `id_shap-m` int(11) NOT NULL,
 *   `id_col-m` int(11) NOT NULL,
 *   `description` text COLLATE utf8_polish_ci NOT NULL,
 *   `quantity-state` int(11) NOT NULL,
 *   `quantity-m` int(11) NOT NULL,
 *   `discount-m` int(11) DEFAULT NULL,
 *   `vat-m` int(11) NOT NULL DEFAULT 23,
 *   `cerate-m` datetime NOT NULL,
 *   `restock-m` datetime DEFAULT NULL,
 *   `source` text COLLATE utf8_polish_ci DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `product-meta`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `product-meta`">                 
/** 
 * ALTER TABLE `product-meta`
 *   ADD KEY `id_col-m` (`id_col-m`) USING BTREE,
 *   ADD KEY `id_shap-m` (`id_shap-m`) USING BTREE,
 *   ADD KEY `id_fabr-m` (`id_fabr-m`) USING BTREE,
 *   ADD KEY `id_prod-m` (`id_prod-m`) USING BTREE;
 */
//</editor-fold> 
//Ograniczenia dla tabeli `product-meta`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `product-meta`">
/** 
 * ALTER TABLE `product-meta`
 *   ADD CONSTRAINT `product-meta_ibfk_1` FOREIGN KEY (`id_fabr-m`) REFERENCES `fabric` (`id_fabr`),
 *   ADD CONSTRAINT `product-meta_ibfk_2` FOREIGN KEY (`id_shap-m`) REFERENCES `shape` (`id_shap`),
 *   ADD CONSTRAINT `product-meta_ibfk_3` FOREIGN KEY (`id_col-m`) REFERENCES `color` (`id_col`),
 *   ADD CONSTRAINT `product-meta_ibfk_4` FOREIGN KEY (`id_prod-m`) REFERENCES `product` (`id_prod`);
 */
//</editor-fold> 


