/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 28.05.2021 r.
 * 
 * Model łączący kategorie, tag oraz tabele łącznikową kategoria-tag.
 * Ma za zadanie wyświetlać menu oraz porządkować produkty.
 */
package jwl.model.link;

public class CatTag{
  //zmienne dla jednego wywołania danej tabeli (kategoria/tag)  
    private int id;                         //identyfikator
    protected String name;                  //nazwa rekordu
    protected String desc;                  //opis rekordu
   //zmienne dla kategorii 
    protected int idCat;                    //identyfikator kategori
    protected int idGr;                     //identyfikator grupy do której należy dama kategoria
    protected String nameCat;               //nazwa kategorii
    protected String descriptionCat;        //opis kategorii
   //zmienne dla tagu  
    protected int idTag;                    //identyfikator tagu
    protected String nameTag;               //nazwa tagu
    protected String descriptionTag;        //opis tagu
    
//-------Konstruktory----------                         //konstruktory modelu
    public CatTag() {}       //konstruktor pusty  
    
   //konstruktor dla tabeli łącznikowej kategoria-tag + nazwy kategorii i tagu 
    public CatTag(int id, int idCat, String nameCat, int idTag, String nameTag) {
        this.id = id;
        this.idCat = idCat;
        this.nameCat = nameCat;
        this.idTag = idTag;
        this.nameTag = nameTag;
    }
   //konstruptor podstawowy do przypisania id tageli łącznikowej, zaciągnięciem nazw grupy, kategorii i tagu oraz ich id
    public CatTag(int id, int idCat, int idGr, String nameCat,
            int idTag, String nameTag, String name) {
        this.id = id;
        this.idCat = idCat;
        this.idGr = idGr;
        this.nameCat = nameCat;
        this.idTag = idTag;
        this.nameTag = nameTag;
        this.name = name;
    }
   //konstruktor dla id oraz nazwy danej tabeli (kategorii/tagu)
    public CatTag(int id, String name) {
        this.id = id;
        this.name = name;
    }
   //konstruktor dla administaratora (lista kategorii przu=ypisanych dla danej grupy z nazwą grupy)
    public CatTag(int id, String name, int idGr) {
        this.id = id;
        this.name = name;
        this.idGr = idGr;
    }
   //konstruktor dla kategorii/tagu (zaciąganie id, nazwy i opisu)
    public CatTag(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.desc = description;
    }
   //konstruktor dla tabeli łącznikowej kategoria-tag  
    public CatTag(int id, int idCat, int idTag) {
        this.id = id;
        this.idCat = idCat;
        this.idTag = idTag;
    }
   //konstruktor dla nazw kategorii i tagu 
    public CatTag(String nameCat, String nameTag) {
        this.nameCat = nameCat;
        this.nameTag = nameTag;
    }
     
//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfikator tabeli (PK - klucz podstawowy) 
    public int getId() {
        return id;
    }
  //Nazwa zmiennej z tabeli
    public String getName() {
        return name;
    }
  //Opis zmiennej z tabeli 
    public String getDesc() {
        return desc;
    }
  //Identyfikator kategori (PK - klucz podstawowy / FK = klucz obcy dla tabeli łącznikowej) 
    public int getIdCat() {
        return idCat;
    }
  //Iidentyfikator grupy (FK - klucz obcy dla kategorii)
    public int getIdGr() {
        return idGr;
    }
  //Nazwa kategorii
    public String getNameCat() {
        return nameCat;
    }
  //Opis kategorii  
    public String getDescriptionCat() {
        return descriptionCat;
    }
  //Identyfikator tagu (PK - klucz podstawowy / FK = klucz obcy dla tabeli łącznikowej) 
    public int getIdTag() {
        return idTag;
    }
  //Nazwa tagu 
    public String getNameTag() {
        return nameTag;
    }
  //Opis tagu 
    public String getDescriptionTag() {
        return descriptionTag;
    }
    
   
//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfikator tabeli (PK - klucz podstawowy) 
    public void setId(int id) {
        this.id = id;
    }   
  //Nazwa zmiennej z tabeli
    public void setName(String name) {
        this.name = name;
    } 
  //Opis zmiennej z tabeli  
    public void setDesc(String desc) {
        this.desc = desc;
    }
  //Identyfikato kategori (PK - klucz podstawowy / FK = klucz obcy dla tabeli łącznikowej) 
    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }
  //Identyfikato grupy (FK - klucz obcy dla kategorii)
    public void setIdGr(int idGr) {
        this.idGr = idGr;
    }
  //Nazwa kategorii
    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }
  //Opis kategorii   
    public void setDescriptionCat(String descriptionCat) {
        this.descriptionCat = descriptionCat;
    }
  //Identyfikato tagu (PK - klucz podstawowy / FK = klucz obcy dla tabeli łącznikowej)
    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }
  //Nazwa tagu
    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }
  //Opis tagu   
    public void setDescriptionTag(String descriptionTag) {
        this.descriptionTag = descriptionTag;
    }
}

//Wygląd bazy danych (tabela kategoria):  
//Struktura tabeli dla tabeli `category`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `category`">                   
/** 
 * CREATE TABLE `category` (
 *   `id_cat` int(11) NOT NULL,
 *   `id_group-c` int(11) NOT NULL,
 *   `name-c` text COLLATE utf8_polish_ci NOT NULL,
 *   `description-c` text COLLATE utf8_polish_ci DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `category`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `category`">                 
/** 
 * ALTER TABLE `category`
 *   ADD PRIMARY KEY (`id_cat`),
 *   ADD KEY `id_group` (`id_group-c`)
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `category`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `category`">
/** 
 * ALTER TABLE `category`
 *   MODIFY `id_cat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
 */
//</editor-fold> 
//Ograniczenia dla tabeli `category`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `category`">
/** 
 * ALTER TABLE `category`
 *   ADD CONSTRAINT `category_ibfk_1` FOREIGN KEY (`id_group-c`) REFERENCES `group-g` (`id_group`)
 * COMMIT;
 */
//</editor-fold> 

//Wygląd bazy danych (tabela tag):   
//Struktura tabeli dla tabeli `tag`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `tag`">                   
/** 
 * CREATE TABLE `tag` (
 *   `id_tag` int(11) NOT NULL,
 *   `name-t` text COLLATE utf8_polish_ci NOT NULL,
 *   `description-t` text COLLATE utf8_polish_ci DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `tag`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `tag`">                 
/** 
 * ALTER TABLE `tag`
 *   ADD PRIMARY KEY (`id_tag`)
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `tag`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `tag`">
/** 
 * ALTER TABLE `tag`
 *   MODIFY `id_tag` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=169;
 */
//</editor-fold> 

//Wygląd bazy danych (tabela łącznikowa kategoria-tag): 
//Struktura tabeli dla tabeli `category-tag`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `category-tag`">                   
/** 
 * CREATE TABLE `category-tag` (
 *   `id_catag` int(11) NOT NULL,
 *   `id_cat-ct` int(11) NOT NULL,
 *   `id_tag-ct` int(11) NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `category-tag`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `category-tag`">                 
/** 
 * ALTER TABLE `category-tag`
 *   ADD PRIMARY KEY (`id_catag`),
 *   ADD KEY `id_cat-ct` (`id_cat-ct`),
 *   ADD KEY `id_tag-ct` (`id_tag-ct`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `category-tag`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `category-tag`">
/** 
 * ALTER TABLE `category-tag`
 *   MODIFY `id_catag` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=268
 */
//</editor-fold> 
//Ograniczenia dla tabeli `category-tag`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `category-tag`">
/** 
 * ALTER TABLE `category-tag`
 *   ADD CONSTRAINT `category-tag_ibfk_2` FOREIGN KEY (`id_tag-ct`) REFERENCES `tag` (`id_tag`),
 *   ADD CONSTRAINT `category-tag_ibfk_3` FOREIGN KEY (`id_cat-ct`) REFERENCES `category` (`id_cat`);
 * COMMIT;
  */
//</editor-fold> 
