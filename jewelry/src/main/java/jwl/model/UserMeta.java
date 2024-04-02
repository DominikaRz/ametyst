/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 25.08.2021 r.
 * 
 * Model dla tabeli adresów i informacji o firmie. Przechowuje wszystkie adresy i informacje o firmie
 * znajdujące się w aplikacji dla danego użytkownika (zalogowanego i niezalogowanego).
 */

package jwl.model;

public class UserMeta extends User {
    protected int idMeta;               //identyfikator metadanych
    protected boolean logged;           //znacznik informujący czy użytkownik jest zalogowany
    protected int idUsr;                //identyfikator użytkownika zalogowanego
    protected boolean firm;             //znacznik informujący czy użytkownik jest to firma
   //firma 
    protected String firm_name;         //nazwa firmy
    protected String firm_nip;          //NiP firmy
    protected String firm_email;        //e-mail firmy
    protected String firm_tel;          //telefon firmy
   //adres 
    protected String adr_str;           //ulica
    protected String adr_nr;            //numer lokalu
    protected String adr_town;          //miejscowość
    protected String adr_state;         //województwo
    protected String adr_code;          //kod pocztowy
    protected String adr_post;          //poczta (miasto)
    protected String adr_count;         //kraj

//-------Konstruktory----------                         //konstruktory modelu
    public UserMeta() {}      //konstruktor pusty
  
   //konstruktor podstawowy dla firmy 
    public UserMeta(int id_meta, int id_usr, boolean logged, boolean firm, String firm_name, 
            String firm_nip, String firm_email, String firm_tel, String adr_str, 
            String adr_nr, String adr_town, String adr_state, 
            String adr_code, String adr_post, String adr_count) {
        this.idMeta = id_meta;
        this.idUsr = id_usr;
        this.logged = logged;
        this.firm = firm;
        this.firm_name = firm_name;
        this.firm_nip = firm_nip;
        this.firm_email = firm_email;
        this.firm_tel = firm_tel;
        this.adr_str = adr_str;
        this.adr_nr = adr_nr;
        this.adr_town = adr_town;
        this.adr_state = adr_state;
        this.adr_code = adr_code;
        this.adr_post = adr_post;
        this.adr_count = adr_count;
    }
   //konstruktor podstawowy dla adresu
    public UserMeta(int idMeta, boolean logged, int idUsr, String adr_str, String adr_nr, String adr_town, 
            String adr_state, String adr_code, String adr_post, String adr_count) {
        this.idMeta = idMeta;
        this.logged = logged;
        this.idUsr = idUsr;
        this.adr_str = adr_str;
        this.adr_nr = adr_nr;
        this.adr_town = adr_town;
        this.adr_state = adr_state;
        this.adr_code = adr_code;
        this.adr_post = adr_post;
        this.adr_count = adr_count;
    }
   //konstruktor dla użytkownika zalogowanego (adres)
    public UserMeta(int id_meta, boolean logged, int id_usr, String adr_str, String adr_nr, String adr_town, 
            String adr_state, String adr_code, String adr_post, String adr_count, int id, String name, 
            String surname, String email, String tel) {
        super(id, name, surname, email, tel);
        this.idMeta = id_meta;
        this.logged = logged;
        this.idUsr = id_usr;
        this.adr_str = adr_str;
        this.adr_nr = adr_nr;
        this.adr_town = adr_town;
        this.adr_state = adr_state;
        this.adr_code = adr_code;
        this.adr_post = adr_post;
        this.adr_count = adr_count;
    }
   //konstruktor dla użytkownika zalogowanego z firmą
    public UserMeta(int id_meta, boolean logged, int id_usr, boolean firm, String firm_name, 
            String firm_nip, String firm_email, String firm_tel, String adr_str, String adr_nr, 
            String adr_town, String adr_state, String adr_code, String adr_post, String adr_count, 
            int id, String name, String surname, String email, String tel) {
        super(id, name, surname, email, tel);
        this.idMeta = id_meta;
        this.logged = logged;
        this.idUsr = id_usr;
        this.firm = firm;
        this.firm_name = firm_name;
        this.firm_nip = firm_nip;
        this.firm_email = firm_email;
        this.firm_tel = firm_tel;
        this.adr_str = adr_str;
        this.adr_nr = adr_nr;
        this.adr_town = adr_town;
        this.adr_state = adr_state;
        this.adr_code = adr_code;
        this.adr_post = adr_post;
        this.adr_count = adr_count;
    }
   //konstruktor dla użytkownika niezalogowanego (adres)
    public UserMeta(int id_meta, boolean logged, String adr_str, String adr_nr, 
            String adr_town, String adr_state, String adr_code, String adr_post, String adr_count) {
        this.idMeta = id_meta;
        this.logged = logged;
        this.adr_str = adr_str;
        this.adr_nr = adr_nr;
        this.adr_town = adr_town;
        this.adr_state = adr_state;
        this.adr_code = adr_code;
        this.adr_post = adr_post;
        this.adr_count = adr_count;
    }
   //konstruktor dla użytkownika niezalogowanego z firmą
    public UserMeta(int id, boolean logged, boolean firm, String firm_name, String firm_nip, 
             String firm_email, String firm_tel, String adr_str, String adr_nr, String adr_town,
             String adr_state, String adr_code, String adr_post, String adr_count) {
        this.idMeta = id;
        this.logged = logged;
        this.firm = firm;
        this.firm_name = firm_name;
        this.firm_nip = firm_nip;
        this.firm_email = firm_email;
        this.firm_tel = firm_tel;
        this.adr_str = adr_str;
        this.adr_nr = adr_nr;
        this.adr_town = adr_town;
        this.adr_state = adr_state;
        this.adr_code = adr_code;
        this.adr_post = adr_post;
        this.adr_count = adr_count;
    }
   //konstruktor dla sprawdzenia czy jest to uzytkownik zalogowany
    public UserMeta(int id, boolean logged) {
        this.idMeta = id;
        this.logged = logged;
    }
   //konstruktor dla sprawdzenia czy jest to firma
    public UserMeta(boolean firm, int id) {
        this.idMeta = id;
        this.firm = firm;
    }
   //konstruktor dla uzytkownika zalogowanego i jego podstawowych danych
    public UserMeta(int idMeta, int id, String name, String surname, String email, String tel) {
        super(id, name, surname, email, tel);
        this.idMeta = idMeta;
    }
    
//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfikator metadanych (PK - klucz podstawowy)
     public int getIdMeta() {
        return idMeta;
    }
  //Znacznik użytkownika zalogowanego
    public boolean isLogged() {    
        return logged;
    }
  //Identyfikator użytkownika (FK - klucz obcy)
    public int getIdUsr() {
        return idUsr;
    } 
  //Znacznik firmy
    public boolean isFirm() {
        return firm;
    }
  //Nazwa firmy
    public String getFirm_name() {
        return firm_name;
    }
  //NIP firmy 
    public String getFirm_nip() {
        return firm_nip;
    }
  //Email firmy
    public String getFirm_email() {
        return firm_email;
    }
  //Telefon firmy
    public String getFirm_tel() {
        return firm_tel;
    }
  //Adres - ulica
    public String getAdr_str() {
        return adr_str;
    }
  //Adres - numer 
    public String getAdr_nr() {
        return adr_nr;
    }
  //Adres - miejscowość 
    public String getAdr_town() {
        return adr_town;
    }
  //Adres - województwo 
    public String getAdr_state() {
        return adr_state;
    }
  //Adres - kod pocztowy 
    public String getAdr_code() {
        return adr_code;
    }
  //Adres - poczta 
    public String getAdr_post() {
        return adr_post;
    }
  //Adres - kraj 
    public String getAdr_count() {
        return adr_count;
    }

//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfikator metadanych (PK - klucz podstawowy)
    public void setIdMeta(int id) {    
        this.idMeta = id;
    }
  //Znacznik użytkownika zalogowanego
    public void setLogged(boolean logged) {
        this.logged = logged;
    }
  //Identyfikator użytkownika (FK - klucz obcy)
    public void setIdUsr(int id_usr) {
        this.idUsr = id_usr;
    }
  //Znacznik firmy
    public void setFirm(boolean firm) {
        this.firm = firm;
    }
  //Nazwa firmy
    public void setFirm_name(String firm_name) {
        this.firm_name = firm_name;
    }
  //NIP firmy   
    public void setFirm_nip(String firm_nip) {
        this.firm_nip = firm_nip;
    }
  //Email firmy
    public void setFirm_email(String firm_email) {
        this.firm_email = firm_email;
    }
  //Telefon firmy
    public void setFirm_tel(String firm_tel) {
        this.firm_tel = firm_tel;
    }
  //Adres - ulica  
    public void setAdr_str(String adr_str) {
        this.adr_str = adr_str;
    }
  //Adres - numer  
    public void setAdr_nr(String adr_nr) {
        this.adr_nr = adr_nr;
    }
  //Adres - miejscowość  
    public void setAdr_town(String adr_town) {
        this.adr_town = adr_town;
    }
  //Adres - województwo   
    public void setAdr_state(String adr_state) {
        this.adr_state = adr_state;
    }
  //Adres - kod pocztowy 
    public void setAdr_code(String adr_code) {
        this.adr_code = adr_code;
    }
  //Adres - poczta 
    public void setAdr_post(String adr_post) {
        this.adr_post = adr_post;
    }
  //Adres - kraj 
    public void setAdr_count(String adr_count) {
        this.adr_count = adr_count;
    }    
}


//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `user-meta`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `user-meta`">                   
/** 
 * CREATE TABLE `user-meta` (
 *   `id_user_m` int(11) NOT NULL,
 *   `logged` tinyint(1) NOT NULL,
 *   `id_user-m` int(11) DEFAULT NULL,
 *   `firm` tinyint(1) NOT NULL DEFAULT 0,
 *   `name_firm` text COLLATE utf8_polish_ci DEFAULT NULL,
 *   `nip_firm` varchar(15) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `firm_email` varchar(100) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `firm_tel` varchar(11) COLLATE utf8_polish_ci DEFAULT NULL,
 *   `adr_str` varchar(60) COLLATE utf8_polish_ci NOT NULL,
 *   `adr_nr` varchar(10) COLLATE utf8_polish_ci NOT NULL,
 *   `adr_town` varchar(45) COLLATE utf8_polish_ci NOT NULL,
 *   `adr_state` varchar(20) COLLATE utf8_polish_ci NOT NULL,
 *   `adr_code` varchar(6) COLLATE utf8_polish_ci NOT NULL,
 *   `adr_post` varchar(45) COLLATE utf8_polish_ci NOT NULL,
 *   `adr_count` varchar(20) COLLATE utf8_polish_ci NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `user-meta`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `user-meta`">                 
/** 
 * ALTER TABLE `user-meta`
 *   ADD PRIMARY KEY (`id_user_m`),
 *   ADD KEY `id_user-m` (`id_user-m`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `user-meta`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `user-meta`">
/** 
 * ALTER TABLE `user-meta`
 *   MODIFY `id_user_m` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;
 */
//</editor-fold> 
//Ograniczenia dla tabeli `user-meta`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `user-meta`">
/** 
 * ALTER TABLE `user-meta`
 *   ADD CONSTRAINT `user-meta_ibfk_1` FOREIGN KEY (`id_user-m`) REFERENCES `user` (`id_user`),
 *   ADD CONSTRAINT `user-meta_ibfk_2` FOREIGN KEY (`id_user_m`) REFERENCES `order-o` (`id_user_m-o`);
 * COMMIT;
 */
//</editor-fold> 
