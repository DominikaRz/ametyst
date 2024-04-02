/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 04.06.2021 r.
 * 
 * Model dla tabeli zamówienie. 
 * Tabela podsatwowa wyświetlająca i zapisująca zamówienia.
 */
package jwl.model;

public class Order {
    protected int id;               //identyfikator zamówienia
    protected int idPay;            //identyfikator płatności
    protected int idUserMeta;       //identyfikator adresu/firmu użytkownika
    protected int idDeliv;          //identyfikator dstawy
    protected String deliv_nr;      //numer do śledzenia przesyłki
    protected int idStat;           //identyfikator statusu
    protected int idDisc;           //identyfikator koduy rabatowego
    protected int idWork;           //identyfikator pracownika obsługującego zamówienie
    protected String name;          //imię zamawiającego
    protected String surname;       //nazwisko zamawiającego 
    protected String email;         //e-mail zamawiającego
    protected String telephone;     //telefon zamawiającego
    protected int vat;              //VAT
    protected double sum;           //suma zamówienia bez dostawy
    protected String comments;      //komentarz do zamówienia
    protected String create;        //data złożenia zamówienia
    protected String update;        //data aktualizacji przez pracownika
    protected String send;          //data wysłania przesyłki
    
//-------Konstruktory----------                         //konstruktory modelu
    public Order() {}      //konstruktor pusty

   //konstruktor pełny 
    public Order(int id, int idPay, int idUserMeta, int idDeliv, String deliv_nr, 
            int idStat, int idDisc, String name, String surname, String email, 
            String telephone, int vat, double sum, String comments, String create, 
            String update, String send) {
        this.id = id;
        this.idPay = idPay;
        this.idUserMeta = idUserMeta;
        this.idDeliv = idDeliv;
        this.deliv_nr = deliv_nr;
        this.idStat = idStat;
        this.idDisc = idDisc;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.vat = vat;
        this.sum = sum;
        this.comments = comments;
        this.create = create;
        this.update = update;
        this.send = send;
    }
   //konstruktor dla zalogowanego użytkownika
    public Order(int id, int idPay, int idUserMeta, int idDeliv, String deliv_nr, 
            int idStat, int idDisc, int vat, double sum, String comments, 
            String create, String update, String send) {
        this.id = id;
        this.idPay = idPay;
        this.idUserMeta = idUserMeta;
        this.idDeliv = idDeliv;
        this.deliv_nr = deliv_nr;
        this.idStat = idStat;
        this.idDisc = idDisc;
        this.vat = vat;
        this.sum = sum;
        this.comments = comments;
        this.create = create;
        this.update = update;
        this.send = send;
    }
   //konstruktor dla zamiany statusu
    public Order(int id, int idStat) {
        this.id = id;
        this.idStat = idStat;
    }
   //konstruktor dla dostawy + zmiany sttusu
    public Order(int id, int idDeliv, String deliv_nr, int idStat) {
        this.id = id;
        this.idDeliv = idDeliv;
        this.deliv_nr = deliv_nr;
        this.idStat = idStat;
    }
   //konstruktor dla aktualizacji pzrez pracownika
    public Order(int id, String deliv_nr, int idStat, String update, String send, int idWorker) {
        this.id = id;
        this.deliv_nr = deliv_nr;
        this.idStat = idStat;
        this.update = update;
        this.send = send;
        this.idWork = idWorker;
    }
   //konstruktor dla wyświetlenia zamówienia
    public Order(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }  
    
//-----------Gettery----------------                    //pobieranie danych z modelu  
  //Identyfikator zamówienia (PK - klucz podstawowy)
    public int getId() {
        return id;
    }
  //Identyfikator płatności (FK - klucz obcy)
    public int getIdPay() {
        return idPay;
    }
  //Identyfikator adresu/firmy (FK - klucz obcy)
    public int getIdUserMeta() {
        return idUserMeta;
    }
  //Identyfikator dostawy (FK - klucz obcy)
    public int getIdDeliv() {
        return idDeliv;
    }
  //Number śledzenia przesyłki
    public String getDeliv_nr() {
        return deliv_nr;
    }
  //Identyfikator statusu (FK - klucz obcy)
    public int getIdStat() {
        return idStat;
    }
  //Identyfikator kodu promocyjnego (FK - klucz obcy)
    public int getIdDisc() {
        return idDisc;
    }
  //Identyfikator pracownika (FK - klucz obcy)
    public int getIdWork() {
        return idWork;
    }
  //Imię
    public String getName() {
        return name;
    }
  //Nazwisko
    public String getSurname() {
        return surname;
    }
  //Email
    public String getEmail() {
        return email;
    }
  //Telefon
    public String getTelephone() {
        return telephone;
    }
  //VAT
    public int getVat() {
        return vat;
    }
  //Suma zamówienia z VAT, bez dostawy
    public double getSum() {
        return sum;
    }
  //Komentarz do zamówienia
    public String getComments() {
        return comments;
    }
  //Data utworzenia
    public String getCreate() {
        return create;
    }
  //Date aktualizacji przez uzytkownika
    public String getUpdate() {
        return update;
    }
  //Date wyałania przesyłki
    public String getSend() {
        return send;
    }
    
//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana  
   //Identyfikator zamówienia (PK - klucz podstawowy)
    public void setId(int id) {
        this.id = id;
    }
  //Identyfikator płatności (FK - klucz obcy)
    public void setIdPay(int idPay) {
        this.idPay = idPay;
    }
  //Identyfikator adresu/firmy (FK - klucz obcy)
    public void setIdUserMeta(int idUserMeta) {
        this.idUserMeta = idUserMeta;
    }
  //Identyfikator dostawy (FK - klucz obcy)
    public void setIdDeliv(int idDeliv) {
        this.idDeliv = idDeliv;
    }
  //Number śledzenia przesyłki
    public void setDeliv_nr(String deliv_nr) {
        this.deliv_nr = deliv_nr;
    }
  //Identyfikator statusu (FK - klucz obcy)
    public void setIdStat(int idStat) {
        this.idStat = idStat;
    }
  //Identyfikator kodu promocyjnego (FK - klucz obcy)
    public void setIdDisc(int idDisc) {
        this.idDisc = idDisc;
    }
  //Identyfikator pracownika (FK - klucz obcy)
    public void setIdWork(int idWork) {
        this.idWork = idWork;
    }
  //Imię
    public void setName(String name) {
        this.name = name;
    }
  //Nazwisko
    public void setSurname(String surname) {
        this.surname = surname;
    }
  //Email
    public void setEmail(String email) {
        this.email = email;
    }
  //Telefon
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
  //VAT
    public void setVat(int vat) {
        this.vat = vat;
    }
  //Suma zamówienia z VAT, bez dostawy
    public void setSum(double sum) {
        this.sum = sum;
    }
  //Komentarz do zamówienia
    public void setComments(String comments) {
        this.comments = comments;
    }
  //Data utworzenia
    public void setCreate(String create) {
        this.create = create;
    }
  //Date aktualizacji przez uzytkownika
    public void setUpdate(String update) {
        this.update = update;
    }
  //Date wyałania przesyłki
    public void setSend(String send) {
        this.send = send;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `order-o`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `order-o`">                   
/** 
 * CREATE TABLE `order-o` (
 *   `id_order` int(11) NOT NULL,
 *   `id_pay-o` int(11) NOT NULL,
 *   `id_user_m-o` int(11) NOT NULL,
 *   `id_del-o` int(11) NOT NULL,
 *   `deliv_nr-o` varchar(50) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `id_stat-o` int(11) NOT NULL,
 *   `id_disc-o` int(11) DEFAULT NULL,
 *   `id_worker` int(11) DEFAULT NULL,
 *   `name-o` varchar(15) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `surname-o` varchar(30) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `email-o` varchar(100) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `telephone-o` varchar(11) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `vat-o` int(11) NOT NULL,
 *   `sum-o` float NOT NULL,
 *   `comments-o` varchar(200) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `create-o` datetime NOT NULL,
 *   `update-o` datetime DEFAULT NULL,
 *   `send-o` datetime DEFAULT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `order-o`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `order-o`">                 
/** 
 * ALTER TABLE `order-o`
 *   ADD PRIMARY KEY (`id_order`),
 *   ADD KEY `id_stat-o` (`id_stat-o`),
 *   ADD KEY `id_del-o` (`id_del-o`),
 *   ADD KEY `id_pay-o` (`id_pay-o`),
 *   ADD KEY `id_user_m-o` (`id_user_m-o`) USING BTREE,
 *   ADD KEY `id_disc-o` (`id_disc-o`),
 *   ADD KEY `id_worker` (`id_worker`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `order-o`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `order-o`">
/** 
 * ALTER TABLE `order-o`
 *   MODIFY `id_order` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;
 */
//</editor-fold> 
//Ograniczenia dla tabeli `order-o`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `order-o`">
/** 
 * ALTER TABLE `order-o`
 *   ADD CONSTRAINT `order-o_ibfk_10` FOREIGN KEY (`id_disc-o`) REFERENCES `discount` (`id_disc`),
 *   ADD CONSTRAINT `order-o_ibfk_2` FOREIGN KEY (`id_pay-o`) REFERENCES `payment` (`id_pay`),
 *   ADD CONSTRAINT `order-o_ibfk_5` FOREIGN KEY (`id_del-o`) REFERENCES `delivery` (`id_deliv`),
 *   ADD CONSTRAINT `order-o_ibfk_8` FOREIGN KEY (`id_user_m-o`) REFERENCES `user-meta` (`id_user_m`),
 *   ADD CONSTRAINT `order-o_ibfk_9` FOREIGN KEY (`id_stat-o`) REFERENCES `status` (`id_stat`);
 *   ADD CONSTRAINT `order-o_ibfk_11` FOREIGN KEY (`id_worker-o`) REFERENCES `user` (`id_user`);
 */
//</editor-fold> 

