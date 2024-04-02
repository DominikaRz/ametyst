/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 10.05.2021 r.
 * 
 * Model oparty na tabeli użytkownika. 
 * Podstawowe dane użytkownika. 
 */

package jwl.model;

public class User {
    protected int id;               //identyfikator użytkownika
    protected boolean user;         //znacznik mówiący czy ma on status użytkownika zwykłego (czy może robić zakupy)
    protected boolean rank;         //znacznik dla rangi (czy jest pracownikiem)
    protected int idRank;           //identyfikator użytkownika (jaki to pracownik)
    protected String name;          //imię
    protected String surname;       //nazwisko
    protected String email;         //e-mail
    protected String tel;           //telefon
    protected boolean newsletter;   //czy zapisany na newsletter
    protected String regist_date;   //data rejestracji

//-------Konstruktory----------                         //konstruktory modelu
    public User() {}      //konstruktor pusty
    
   //konstruktor dla użytkownika z rangą
    public User(int id, boolean user, boolean rank, int id_rank, String name, String surname, 
            String email, String tel, boolean newsletter, String regist_date) {
        this.id = id;
        this.user = user;
        this.rank = rank;
        this.idRank = id_rank;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.tel = tel;
        this.newsletter = newsletter;
        this.regist_date = regist_date;
    }
   //konstruktor dla użytkownika bez rangi
    public User(int id, boolean user, String name, String surname, String email, 
            String tel, boolean newsletter, String regist_date) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.tel = tel;
        this.newsletter = newsletter;
        this.regist_date = regist_date;
    }
   //dane użytkownika dla zamówienia
    public User(int id, String name, String surname, String email, String tel) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.tel = tel;
    }
   //konstruktor dla sprawdzenia rangi (email jako dodatkowy klucz podstawowy)
    public User(int id, boolean rank, String email) {
        this.id = id;
        this.rank = rank;
        this.email = email;
    }
   //konstruktor dla sprawdzenia czy jest to uzytkownik z rangą (email jako dodatkowy klucz podstawowy)
    public User(int id, boolean rank, int id_rank, String email) {
        this.id = id;
        this.rank = rank;
        this.idRank = id_rank;
        this.email = email;
    }
   //konstruktor dla podstawowych danych użytkownika 
    public User(int id, String name, String surname, String tel, boolean news) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.tel = tel;
        this.newsletter = news;
    }
   //konstruktor dla zamówienia 
    public User(int id, String surname, String name) {
        this.id = id;
        this.surname = surname;
        this.name = name;
    }
    
    
//-----------Gettery----------------                    //pobieranie danych z modelu
  //Identyfikator użytkownika (PK - klucz podstawowy)
    public int getId() {
        return id;
    }
  //Znacznik użytkownika
    public boolean isUser() {
        return user;
    }
  //Znacznik rangi
    public boolean isRank() {
        return rank;
    }
  //Identyfiaktor rangi (FK - klucz obcy)
    public int getId_rank() {
        return idRank;
    }
  //Imię
    public String getName() {
        return name;
    }
  //Nazwisko
    public String getSurname() {
        return surname;
    }
  //Email (pomocniczy klucz podstawowy)
    public String getEmail() {
        return email;
    }
  //Telefon
    public String getTel() {
        return tel;
    }
  //Newsletter
    public boolean isNewsletter() {
        return newsletter;
    }
  //Data rejestracji
    public String getRegist_date() {
        return regist_date;
    }
    
//------------Settery--------------------               //osadzanie danych w modelu oraz ich zmiana
  //Identyfikator użytkownika (PK - klucz podstawowy)
    public void setId(int id) {
        this.id = id;
    }
  //Znacznik użytkownika
    public void setName(String name) {
        this.name = name;
    }
  //Znacznik rangi
    public void setSurname(String surname) {
        this.surname = surname;
    }
  //Identyfiaktor rangi (FK - klucz obcy)
    public void setUser(boolean user) {
        this.user = user;
    }
  //Imię
    public void setRank(boolean rank) {
        this.rank = rank;
    }
  //Nazwisko
    public void setId_rank(int id_rank) {
        this.idRank = id_rank;
    }
  //Email (pomocniczy klucz podstawowy)
    public void setEmail(String email) {
        this.email = email;
    }
  //Telefon
    public void setTel(String tel) {
        this.tel = tel;
    }
  //Newsletter
    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }
  //Data rejestracji
    public void setRegist_date(String regist_date) {
        this.regist_date = regist_date;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `user`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `user`">                   
/** 
 * CREATE TABLE `user` (
 *   `id_user` int(11) NOT NULL,
 *   `user` tinyint(1) DEFAULT NULL,
 *   `rank` tinyint(1) DEFAULT NULL,
 *   `id_rank-u` int(11) DEFAULT NULL,
 *   `name-u` varchar(15) COLLATE utf8_polish_ci NOT NULL,
 *   `surname-u` varchar(30) COLLATE utf8_polish_ci NOT NULL,
 *   `email` varchar(100) COLLATE utf8_polish_ci NOT NULL,
 *   `telephone` varchar(11) COLLATE utf8_polish_ci NOT NULL,
 *   `newsletter` tinyint(1) DEFAULT NULL,
 *   `register` datetime NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `user`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `user`">                 
/** 
 * ALTER TABLE `user`
 *   ADD PRIMARY KEY (`id_user`),
 *   ADD UNIQUE KEY `email-u` (`email`),
 *   ADD KEY `id_rank-u` (`id_rank-u`);
 */
//</editor-fold> 
//AUTO_INCREMENT dla tabeli `user`            <editor-fold defaultstate="collapsed" desc="AUTO_INCREMENT dla tabeli `user`">
/** 
 * ALTER TABLE `user`
 *   MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
 */
//</editor-fold> 
//Ograniczenia dla tabeli `user`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `user`">
/** 
 * ALTER TABLE `user`
 *   ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_rank-u`) REFERENCES `rank` (`id_rank`);
 * COMMIT;

 */
//</editor-fold>  