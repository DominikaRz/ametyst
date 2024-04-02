/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 10.05.2021 r.
 * 
 * Model dla tabeli logowania użytkowinika.
 * Podstawowe dane uwierzytelniania użytkownika.
 */

package jwl.model;

public class Login {
    protected int id;               //idetyfikator użytkownika
    protected String login;         //login (e-mail użytkownika)
    protected String passw;         //hasło
    protected String last_log;      //data ostatniego logowania
    
//-------Konstruktory----------                         //konstruktory modelu
    public Login() {}      //konstruktor pusty
    
   //konstruktor podstawowy
    public Login(int id, String login, String passw, String lastLog_date) {
        this.id = id;
        this.login = login;
        this.passw = passw;
        this.last_log = lastLog_date;
    }
   //konstruktor dla sprawdzenia logowania
    public Login(int id, String login, String passw) {
        this.id = id;
        this.login = login;
        this.passw = passw;
    }
   //konstruktor dla sprawdzenia loginu
    public Login(int id, String login) {
        this.id = id;
        this.login = login;
    }

//-----------Gettery----------------                    //pobieranie danych z modelu
  //Idetyfikator użytkownika (FK - klucz obcy)
    public int getId() {
        return id;
    }
  //Login
    public String getLogin() {
        return login;
    }
  //Hasło
    public String getPassw() {
        return passw;
    }
  //Data ostatniego logowania
    public String getLast_log() {
        return last_log;
    }
    
//------------Setter's--------------------
  //Idetyfikator użytkownika (FK - klucz obcy)
    public void setId(int id) {
        this.id = id;
    }
  //Login
    public void setLogin(String login) {
        this.login = login;
    }
  //Hasło
    public void setPassw(String passw) {
        this.passw = passw;
    }
  //Data ostatniego logowania
    public void setLast_log(String lastLog_date) {
        this.last_log = lastLog_date;
    }
}

//Wygląd bazy danych:  
//Struktura tabeli dla tabeli `login`          <editor-fold defaultstate="collapsed" desc="Struktura tabeli dla tabeli `login`">                   
/** 
 * CREATE TABLE `login` (
 *   `id_user-l` int(11) NOT NULL,
 *   `login` varchar(100) COLLATE utf8_polish_ci NOT NULL,
 *   `password` varchar(25) COLLATE utf8_polish_ci NOT NULL,
 *   `last_log` datetime NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
 */
//</editor-fold> 
//Indeksy dla tabeli `login`                   <editor-fold defaultstate="collapsed" desc="Indeksy dla tabeli `login`">                 
/** 
 * ALTER TABLE `login`
 *   ADD UNIQUE KEY `email-l` (`login`),
 *   ADD KEY `id_user-l` (`id_user-l`);
 */
//</editor-fold>  
//Ograniczenia dla tabeli `login`            <editor-fold defaultstate="collapsed" desc="Ograniczenia dla tabeli `login`">
/** 
 * ALTER TABLE `login`
 *   ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`id_user-l`) REFERENCES `user` (`id_user`); 
 */
//</editor-fold> 
