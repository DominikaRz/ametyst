/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 07.11.2021 r.
 */
package jwl.jewelry;

import java.io.*;
import java.sql.*;
import static java.sql.Types.NULL;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import jwl.DAO.*;
import jwl.DAO.dict.*;
import jwl.model.*;
import jwl.model.dict.*;
import jwl.model.link.*;
import org.jsoup.Jsoup;

/**
 * ControlUser.java
 * Ten servlet obsługuje użytkownika zalogowanego i wszystko z nim związane.
 * @author DRzepka
 */

@WebServlet(name="ControlUser", urlPatterns={"/register", "/addRegister", "/orders", "/settings", 
            "/updatePassw", "/updateInform", "/updateAddrr", "/addAddrr"}, asyncSupported = true)
public class ControlUser extends HttpServlet {
    
  //zmienne                                               <editor-fold defaultstate="collapsed" desc="zmienne">
    private static final long serialVersionUID = 1L;
   //tabele główne
    private ProductMDAO prodmDAO;    //produkt
    private LoginDAO loginDAO;       //login
    private UserDAO userDAO;         //użytkownik
    private UserMDAO usermDAO;       //adres
    private OrderDAO ordDAO;         //zamówienie
    private HistoryDAO histDAO;      //historia
  //tabela łącznikowa
    private OrderPDAO ordpDAO;      //zamówienie-produkt(stałe dla zamówienia)
  //tabele słownikowe
    private DeliveryDAO delDAO;     //dostawa
    private PaymentDAO payDAO;      //płatność
    private StatusDAO statDAO;      //status zamówienia
    private DiscountDAO discDAO;    //przecena
  //sesja
    private HttpSession sess; 
  //formatowanie daty dla konkretnej zmiennej czasu
    private DateTimeFormatter formatLocalDate;      //</editor-fold>  
 //inicjalizacja zmiennych 
    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");                   //pobieranie url bazy
        String jdbcUsername = getServletContext().getInitParameter("jdbcUser");             //pobieranie nazwy użytkownika "user"
        String jdbcPassword = getServletContext().getInitParameter("jdbcUserPassw");        //pobieranie hasła użytkownika "user"
        String jdbcHist = getServletContext().getInitParameter("jdbcHistory");              //pobieranie nazwy użytkownika "history"
        String jdbcHistPassw = getServletContext().getInitParameter("jdbcHistoryPassw");    //pobieranie hasła użytkownika "history"
       
     //tabele główne
        prodmDAO = new ProductMDAO(jdbcURL, jdbcUsername, jdbcPassword);    //produkt
        loginDAO = new LoginDAO(jdbcURL, jdbcUsername, jdbcPassword);       //login
        userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);         //użytkownik
        usermDAO = new UserMDAO(jdbcURL, jdbcUsername, jdbcPassword);       //adres
        ordDAO = new OrderDAO(jdbcURL, jdbcUsername, jdbcPassword);         //zamówienie
        histDAO = new HistoryDAO(jdbcURL, jdbcHist, jdbcHistPassw);         //historia
     //tabela łącznikowa
        ordpDAO = new OrderPDAO(jdbcURL, jdbcUsername, jdbcPassword);       //zamówienie-produkt(stałe dla zamówienia) 
     //tabele słownikowe 
        delDAO = new DeliveryDAO(jdbcURL, jdbcUsername, jdbcPassword);      //dostawa
        payDAO = new PaymentDAO(jdbcURL, jdbcUsername, jdbcPassword);       //płatność
        statDAO = new StatusDAO(jdbcURL, jdbcUsername, jdbcPassword);       //status zamówienia
        discDAO = new DiscountDAO(jdbcURL, jdbcUsername, jdbcPassword);     //przecena
     //formatowanie czasu    
        formatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   //formatowanie daty 
    }
    
    //Obsługa POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);                        //przekierowanie do GET
    }
    
    //Obsługa GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getServletPath();                           //sprawdzenie ścieżki z której przyszła odpowiedź
        try{
            switch (action) {                                               //obsłyga po ścieżce
               case "/register": register(request,response); break;         //przekierowanie do strony rejestracji 
               case "/addRegister": regUser(request,response); break;       //rejestracja użytkownika
               case "/orders": orderUser(request,response); break;          //widok zamówień (strona główna użytkownika bez rangi)
               case "/settings": settingsUser(request,response); break;     //przekierowanie do strony ustawień
               case "/updatePassw": updatePassw(request,response); break;   //uaktualnianie hasła
               case "/updateInform": updateInform(request,response); break; //uaktualnianie informacji o użytkowniku
               case "/updateAddrr": updateAddrr(request,response); break;   //uaktualnianie zapisanego adresu użytkownika
               case "/addAddrr": insertAddrr(request,response); break;      //dodawanie nowego adresu/firmy użytkownika
            }
        } catch (SQLException ex) { throw new ServletException(ex); }       //wyświetlanie błędów serwera
    }
    
    //przekierowanie do strony rejestracji 
    private void register(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
        String url = request.getHeader("referer");                                      //sprawdzanie wcześniejszej strony
        String urls[] = url.split("/");                                                 //podzielenie całej ścieżki
        sess.setAttribute("refererURL", urls[urls.length-1]);                           //ustawienie zmiennej sesyjnej z poprzednią stroną
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/users/RegForm.jsp");     //przekierownie do pliku
        dispatcher.forward(request, response);                                          //przekazanie parametrów
    }
    
    //rejestracja użytkownika
    private void regUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 9;                                                     //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
        
      //sprawdzanie czy wszystkie wymagane dane hasła zostały przesłane             <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane hasła zostały przesłane">
        String passw = null, passw_a = null; int id = NULL;                                       //deklaracja zmiennych hasła
        try{                                                                        //pobieranie danych
          //zmienne hasła
            passw = Jsoup.parse(request.getParameter("password")).text();           //hasło
            passw_a = Jsoup.parse(request.getParameter("password_again")).text();   //potwierdzenie hasła 
        }
        catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
            String url = request.getHeader("referer");                              //sprawdzanie wcześniejszej strony
            if((url!=null)){                                                        //jeśli strona poprzednia nie jest pusta
                String urls[] = url.split("/");                                     //podzielenie całej ścieżki
                if((urls[urls.length-1].equals("register"))){                       //jeśli jest to strona rejestracji
                    sess.setAttribute("err_disc","Brak poprawnych danych!");        //ustawianie zmiennej sesyjnej
                    response.sendRedirect("register");                              //przekierowanie do formularza rejestracji
                    return;                                                         //przerwanie działania funkcji 
                }
                else{                                                               //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                    response.sendRedirect("home");                                  //przekierowanie do strony głównej
                    return;                                                         //przerwanie działania funkcji
                }
            }
            else{                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Brak dostępu!");                      //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                      //przekierowanie do strony głównej
                return;                                                             //przerwanie działania funkcji
            }
        }                                                                           //</editor-fold>
        
        if(passw.equals(passw_a)){                                                      //sprawdzanie poprawności hasła (zabezpieczenie na wypadek gdyby użytkownik wyłączył walidacje)
          //sprawdzanie czy wszystkie wymagane dane użytkownika zostały przesłane       <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane użytkownika zostały przesłane">
            String mail, name, surname, tel;                                            //deklaracja zmiennych informacyjnych
            try{                                                                        //pobieranie danych
              //zmienne informacyjne 
                mail = Jsoup.parse(request.getParameter("mail")).text();                //pobranie e-mailu
                name = Jsoup.parse(request.getParameter("name")).text();                //pobranie imienia
                surname = Jsoup.parse(request.getParameter("surname")).text();          //pobranie nazwiska
                tel = request.getParameter("tel");                                      //pobranie telefonu
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                          //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>

          //sprawdzanie czy wszystkie wymagane dane adresu zostały przesłane            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane adresu zostały przesłane">
            String str,anr,acode,apost,town,astate,acount;                              //deklaracja zmiennych adresu
            String firm = null,fname = null,fnip = null,fmail = null,
                    ftel = null; boolean firm_m = false;                                //deklaracja zmiennych firmy
            boolean islogg_m = true;                                                    //dodatkowe zmienne
            try{                                                                        //pobieranie danych
              //zmienne adresu  
                str = Jsoup.parse(request.getParameter("street")).text();               //ulica
                anr = request.getParameter("nbr");                                      //numer domu/nieszkania/bloku...
                acode = request.getParameter("code");                                   //kod pocztowy
                apost = Jsoup.parse(request.getParameter("post")).text();               //poczta
                town = Jsoup.parse(request.getParameter("town")).text();                //miejscowość
                astate = Jsoup.parse(request.getParameter("state")).text();             //województwo
                acount = Jsoup.parse(request.getParameter("country")).text();           //kraj
               //znacznik firmy 
                firm = request.getParameter("firmCh");
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                          //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>

          //sprawdzanie czy mail jest unikalny/istnieje w bazie danych
            int id_user = userDAO.readId(mail);                                                         //sprawdzanie czy dany mail jest wykożystany w bazie danych
            if(id_user!=0){                                                                             //jeśli tak
                sess.setAttribute("err_disc","Błąd rejestracji! Osoba o takich danych istnieje.");      //wyświetl odpowiedni komunikat
                response.sendRedirect("register");                                                      //przekieruj do strony rejestracji 
            }
            else{                                                                           //w przeciwnym wypadku
              //rejestracja użytkownika
               //tworzenie użytkownika                                                                          <editor-fold defaultstate="collapsed" desc="tworzenie użytkownika">
                String reg = formatLocalDate.format(LocalDateTime.now());                                       //pobieranie czasu rejestracji
                User user = new User(0, true, false, NULL, name, surname, mail, tel, true, reg);                //model dla użytkownika
                boolean q1 = userDAO.create(user);                                                              //utworzenie użytkownika
                if(q1){ sess.setAttribute("succ_disc","Pomyślnie dodano użytkownika.");}                        //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                else{                                                                                           //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania użytkownika.");               //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                                                  //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                                                     //zatrzymanie działania funkcji
                }                                                                                             
                id_usr = userDAO.readId(mail);                                                                  //pobranie id użytkownika poprzez adres e-mail </editor-fold>
               //tworzenie loginu                                                                               <editor-fold defaultstate="collapsed" desc="tworzenie loginu">
                Login log = new Login(id_usr, mail, passw, reg);                                                //model dla 
                boolean q2 = loginDAO.create(log);                                                              //utworzenie loginu
                if(q2){ sess.setAttribute("succ_disc","Pomyślnie dodano login.");}                              //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                else{                                                                                           //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania loginu.");                    //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                                                  //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                                                     //zatrzymanie działania funkcji
                }                                                                                             //</editor-fold>
                
              //ustawianie historii modyfikacji                                                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
               //ustawienie zapytania "przed zmianą" dla historii
                beforeQuery = "DELETE FROM `user` WHERE `id_user` = "+id_usr+";"                                                //usuwanie użytkownika
                    + "DELETE FROM `login` WHERE `id_user-l` = "+id_usr+";";                                                    //usuwanie loginu
               //ustawienie zapytania SQL "po zmianie" dla historii
                afterQuery = "INSERT INTO `user` ( `user`, `rank`, `id_rank-u`, `name-u`, "
                    + "`surname-u`, `email`, `telephone`, `newsletter`, `register`) "
                    + "VALUES (true, false, NULL, '"+user.getName()+"', '"+user.getSurname()+"', '"+user.getEmail()+"',"
                    + " '"+user.getTel()+"', "+user.isNewsletter()+", '"+user.getRegist_date()+"'); "                           //dodawanie użytkownika      
                    + "INSERT INTO `login` (`id_user-l`, `login`, `password`, `last_log`) "
                    + "VALUES ("+id_usr+", '"+log.getLogin()+"', '"+log.getPassw()+"', '"+log.getLast_log()+"');";              //dodawanie loginu                            
                description = "Dodanie nowego użytkownika";                                                                     //ustawienie opisu 
                modify="imię: "+user.getName()+"<br>nazwisko: "+user.getSurname()+"<br>mail: "+user.getEmail();                 //ustawienie modyfikacji  </editor-fold>
                
                if (null != firm){                                                          //jeśli jest to firma
                  //sprawdzanie czy wszystkie wymagane dane firmy zostały przesłane         <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane firmy zostały przesłane">
                    try{                                                                    //pobieranie danych
                      //zmienne firmy
                        firm_m = true;
                        fname = Jsoup.parse(request.getParameter("name_firm")).text();      //nazwa firmy
                        fnip = Jsoup.parse(request.getParameter("nip")).text();             //NIP
                        fmail = Jsoup.parse(request.getParameter("mail_firm")).text();      //e-mail firmy
                        ftel = Jsoup.parse(request.getParameter("tel_firm")).text();        //telefon firmy
                    }
                    catch (NumberFormatException | NullPointerException ex){                //sprawdzanie błędów
                        sess.setAttribute("err_disc","Brak dostępu!");                      //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                      //przekierowanie do strony głównej
                        return;                                                             //przerwanie działania funkcji
                    }                                                                       //</editor-fold>

                  //dodanie pierwszego adresu firmy      <editor-fold defaultstate="collapsed" desc="dodanie pierwszego adresu firmy">                                               
                    UserMeta userm = new UserMeta(0, id_usr, islogg_m, firm_m, fname,                               
                            fnip,fmail,ftel,str,anr,town,astate,acode,apost,acount);                                //model dla adresu firmy
                    boolean q3 = usermDAO.create(userm);                                                            //utworzenie adresu firmy
                    if(q3){ sess.setAttribute("succ_disc","Pomyślnie dodano użytkownika z adresem firmy.");}        //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{                                                                                           //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania adresu firmy.");              //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                                  //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                                     //zatrzymanie działania funkcji
                    }
                    id = usermDAO.read(userm);                                                                      //pobranie id adresu firmy </editor-fold>
                    
                  //ustawianie historii modyfikacji                                                                                     <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery +="DELETE FROM `user-meta` WHERE `id_user_m` = "+id+"; ";                                               //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery +="INSERT INTO `user-meta` (`logged`, `id_user-m`, `firm`, `name_firm`, `nip_firm`, `firm_email`, "
                        + "`firm_tel`, `adr_str`, `adr_nr`, `adr_town`, `adr_state`, `adr_code`, `adr_post`, `adr_count`) "
                        + "VALUES (true, "+id_usr+", "+firm_m+", '"+fname+"', '"+fnip+"', "+fmail+", '"+ftel+"', '"+str+"', "
                        + "'"+anr+"', '"+town+"', '"+astate+"', '"+acode+"', '"+apost+"', '"+acount+"');";                              //ustawienie zapytania SQL "po zmianie" dla historii </editor-fold>
                }
                else{                                                                       //jeśli nie jest to firma
                  //dodawanie danych                                                                     <editor-fold defaultstate="collapsed" desc="dodawanie danych">
                    UserMeta userm = new UserMeta(0, islogg_m, id_usr, str, anr, town, astate, 
                            acode,apost,acount);                                                            //model do dodania adresu firmy
                    boolean q = usermDAO.createAdrL(userm);                                                 //dodawanie danych 
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano użytkownika z adresem.");}       //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{                                                                                   //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania adresu.");            //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                             //zatrzymanie działania funkcji
                    }                                                                                       //</editor-fold>

                  //ustawianie historii modyfikacji                                                                                     <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery += "DELETE FROM `user-meta` WHERE `id_user_m` = "+id_usr;                                               //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery += "INSERT INTO `user-meta` (`logged`, `id_user-m`, `firm`, `adr_str`, `adr_nr`, `adr_town`, "
                          + "`adr_state`, `adr_code`, `adr_post`, `adr_count`) VALUES ("+islogg_m+", "+id_usr+", false,"
                          + " '"+str+"', '"+anr+"', '"+town+"', '"+astate+"', '"+acode+"', '"+apost+"', '"+acount+"')";                 //ustawienie zapytania SQL "po zmianie" dla historii </editor-fold> 
                }
                History his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);        //stworzenie modelu historii
                histDAO.create(his);                                                                                       //utworzenie historii
          
              //ustawianie zmiennych sesyjnych                                                          <editor-fold defaultstate="collapsed" desc="ustawianie zmiennych sesyjnych">     
                
                sess.setAttribute("userMeta_id",id);                                                    //ustawienie zmiennej id adresu pierwotnego
                sess.setAttribute("name",name);                                                         //ustawienie zmiennej imienia
                sess.setAttribute("surname",surname);                                                   //ustawienie zmiennej nazwiska
                sess.setAttribute("mail",mail);                                                         //ustawienie zmiennej e-mailu
                sess.setAttribute("tel",tel);                                                           //ustawienie zmiennej numeru telefonu </editor-fold> 
                
              //przekierowanie do odpowiedniej strony                                                   <editor-fold defaultstate="collapsed" desc="przekierowanie do odpowiedniej strony">     
                String url = (String)sess.getAttribute("refererURL");                                   //sprawdzanie wcześniejszej strony
                if(url.equals("basket1")){                                                              //jesli jest to strona koszyka
                    sess.setAttribute("succ_disc","To zamówienie zapisze się na nowym koncie!");        //ustawianie zmiennej sesyjnej
                    sess.setAttribute("id_user_logged",id_usr);                                         //ustawienie zmiennej identyfiaktra użytkownika
                    List<UserMeta> usrm = usermDAO.read(id_usr);                                        //wczytanie danych adresów
                    sess.setAttribute("addresses",usrm);                                                //ustawienie zmiennej sesyjnej
                    response.sendRedirect("summaryBask");                                               //przekierowanie do strony podsumowania zamówienia
                    return;                                                                             //przerwanie działania funkcji
                }
                else{                                                                                   //w przeciwnym wypadku
                    sess.setAttribute("succ_disc","Możesz się teraz zalogować!");                       //ustawianie zmiennej sesyjnej
                    response.sendRedirect("login");                                                     //przekierowanie do formularza rejestracji
                    return;                                                                             //przerwanie działania funkcji</editor-fold> 
                }
            }
        }
        else{                                                                                   //jeśli hasła nie są takie same
            sess.setAttribute("err_disc","Błędne dane!");                                       //ustawianie zmiennej sesyjnej
            response.sendRedirect("register");                                                  //przekierowanie do strony głównej
            return;                                                                             //przerwanie działania funkcji</editor-fold> 
        }
        sess.removeAttribute("refererURL");                                                     //usunięcie zmiennej URL
    } 
    
    //widok zamówień (strona główna uzytkownika zamolowanego bez rangi)
    @SuppressWarnings("unchecked")
    private void orderUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
     
        sess=request.getSession(true);                                                //pobranie sesji
        
      //sprawdzanie czy użytkownik jest zalogowany                                              <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                                         //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }                             //pobranie zmiennej sesyjnej z id użytkownika
        catch (Exception e) {                                                                   //sprawdzanie błędów
            sess.setAttribute("err_disc","Musisz być zalogowany aby skorzystać z tej opcji.");  //wyświetlenie odpowiedniego komunikatu
            response.sendRedirect("login");                                                     //przekierowanie do strony logowania
        }                                                                                       //</editor-fold>
         
      //deklaracja zmiennych list                                                                       <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych list">   
        List<UserMeta> usrm = new ArrayList<>();                                                        //lista adresów
        List<Order> orderch = new ArrayList<>();                                                        //lista sprawdzenia zamówień
        List<Order> order = new ArrayList<>();                                                          //lista zamówień
        List<OrderProd> orderp = new ArrayList<>();                                                     //lista produktów w zamówieniu
        List<ProductMeta> prodm = new ArrayList<>();                                                    //lista produktów
        List<Boolean> rew = new ArrayList<>();                                                          //lista recenzji </editor-fold>
         
      //sprawdzanie czy zmienne sesyjne są już zadeklarowane i ich deklaracja przy braku                <editor-fold defaultstate="collapsed" desc="sprawdzanie czy zmienne sesyjne są już zadeklarowane i ich deklaracja przy braku ">
        usrm =  (List<UserMeta>) sess.getAttribute("addresses");                                        //sprawdzanie adresów
        orderch = (List<Order>) sess.getAttribute("listOrder");                                         //sprawdzanie listy zamówień
        if((orderch==null)){                                                                            //jeśli zamówienie jest puste
            for (UserMeta usr : usrm) {                                                                 //dla każdego adresu 
                order.addAll(ordDAO.readUsr(usr.getIdMeta()));                                          //dodawanie wszystkich zamówień
            }
            for (Order ord : order) {                                                                   //dla każdego zamówienia
                orderp.addAll(ordpDAO.read(ord.getId()));                                               //dodawanie listy zamówionych produktów
                rew.add(ordpDAO.isFullRev(ord.getId()));                                                //sprawdzanie czy wszystkie produkty zostały zrecenzowane
            }
            for (OrderProd ordp : orderp) {                                                             //dla każdego zamówionego produktu
                prodm.add(prodmDAO.readProdOrd(ordp.getIdProd()));                                      //dodawanie informacji o owym produkcie
            }
            sess.setAttribute("listUserm", usrm);                                                       //dodawanie listy adresów do sesji
            sess.setAttribute("listOrder", order);                                                      //dodawanie listy zamówień do sesji
            sess.setAttribute("listOrderp", orderp);                                                    //dodawanie listy zmówionych produktów do sesji
            sess.setAttribute("listProdMt", prodm);                                                      //dodawanie listy produktów do sesji
            sess.setAttribute("reviewed", rew);                                                         //dodawanie znacznika recenzji do sesji
        }
        else{                                                                                           //jeśli zamówienie nie jest puste
            sess.setAttribute("listUserm", usrm);                                                       //uaktualnij listę z adresem
        }                                                                                               //</editor-fold>
             
      //pobieranie dodatkowych, potrzebnych list                                                        <editor-fold defaultstate="collapsed" desc="pobieranie dodatkowych, potrzebnych list">   
        List<Status> listStat = statDAO.read();                                                         //lista statusów
        request.setAttribute("listStat", listStat);                                                     //deklaracja dla JSP
        List<Delivery> listDel = delDAO.read();                                                         //lista dostaw
        request.setAttribute("listDel", listDel);                                                       //deklaracja dla JSP
        List<Payment> listPay = payDAO.read();                                                          //lista płatności
        request.setAttribute("listPay", listPay);                                                       //deklaracja dla JSP
        List<Discount> listDisc = discDAO.read();                                                       //lista kodów promocyjnych
        request.setAttribute("listDisc", listDisc);                                                     //deklaracja dla JSP </editor-fold>

        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/users/indexUsr.jsp");    //przekierownie do pliku
        dispatcher.forward(request, response);                                          //przekazanie parametrów
        
    } 
    
    //przekierowanie do ustawień
    private void settingsUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
     
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/users/UpdateUsr.jsp");    //przekierownie do pliku
        dispatcher.forward(request, response);                                           //przekazanie parametrów
    } 
    
    //uaktualnianie hasła
    private void updatePassw(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
     
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = null;    //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 2;                                                     //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
        
      //sprawdzenie czy użytownik jest zalogowany                                           <editor-fold defaultstate="collapsed" desc="sprawdzenie czy użytownik jest zalogowany">  
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }                         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NumberFormatException | NullPointerException ex){                            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                                  //przekierowanie do strony głównej
            return;                                                                         //przerwanie działania funkcji
        }                                                                                   //</editor-fold>   
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane                           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        String passw_p, passw, passw_a;                                                     //ustawienie zmiennych
        try{ 
            passw_p = Jsoup.parse(request.getParameter("password_previous")).text();        //pobranie wcześniejszego hasła
            passw = Jsoup.parse(request.getParameter("password")).text();                   //pobranie nowego hasła
            passw_a = Jsoup.parse(request.getParameter("password_again")).text();           //pobranie potwierdzenia hasła
        }
        catch (NumberFormatException | NullPointerException ex){                            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                                  //przekierowanie do strony głównej
            return;                                                                         //przerwanie działania funkcji
        }                                                                                   //</editor-fold>  
        
        Login log = loginDAO.read(id_usr);                                              //pobranie danych logowania
         
      //sprawdzenie poprawności danych i ich aktualizacja                                           <editor-fold defaultstate="collapsed" desc="sprawdzenie poprawności danych i ich aktualizacja">
        if(log.getPassw().equals(passw_p)){                                                         //jeśli pobrane od uzytkownika hasło jest takie same jak w bazie danych
            if(passw.equals(passw_a)){                                                              //oraz jeśli nowe hasło jest takie same jak potwierdzone
                Login insert = new Login(id_usr, log.getLogin(), passw);                            //tworzenie modelu do aktualizacji
                boolean q = loginDAO.updatePass(insert);                                            //aktualizacja hasła
                if(q){ sess.setAttribute("succ_disc","Pomyślnie zmieniono hasło.");}                //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                else{                                                                               //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Wystąpił błąd podczas zmiany hasła.");            //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                                         //zatrzymanie działania funkcji
                }
            }
            else{ sess.setAttribute("err_disc","Potwierdzenie hasła jest błędne!"); }               //wyświetlenie komunikatu o błednym haśle potwierdzonym
        }
        else{ sess.setAttribute("err_disc","Coś poszło nie tak. Hasło niepoprawne!"); }             //wyświetlenie komunikatu o błednym haśle pierwotnym </editor-fold>  
                                                                                    
      //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
        beforeQuery = "UPDATE `login` SET `password` = '"+log.getPassw()
                +"' WHERE `id_user-l` = "+id_usr;                                                   //ustawienie zapytania "przed zmianą" dla historii
        afterQuery = "UPDATE `login` SET `password` = '"+passw+"' WHERE `id_user-l` = "+id_usr;     //ustawienie zapytania SQL "po zmianie" dla historii
        description = "Zmiana hasła użytkownika";  modify="Dodano nowe hasło.";                     //ustawienie opisu i tekstu modyfikacji
        History his = new History(NULL, id_usr, action, description, now, 
                beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
        histDAO.create(his);                                                                        //dodanie historii do bazy danych </editor-fold>
        
        response.sendRedirect("settings");                                              //przekierowanie do strony ustawień
    } 
    
    //zmiana informacji użytkownika
    private void updateInform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
     
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = null;    //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 2;                                                     //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
        
      //sprawdzenie czy użytownik jest zalogowany                                           <editor-fold defaultstate="collapsed" desc="sprawdzenie czy użytownik jest zalogowany">  
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }                         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NumberFormatException | NullPointerException ex){                            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                                  //przekierowanie do strony głównej
            return;                                                                         //przerwanie działania funkcji
        }                                                                                   //</editor-fold>   
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane                           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        String name, surname, tel, news_b; boolean news = false;                            //ustawienie zmiennych
        try{ 
            name = Jsoup.parse(request.getParameter("name")).text();                        //pobranie imienia 
            surname = Jsoup.parse(request.getParameter("surname")).text();                  //pobranie nazwiska
            tel = request.getParameter("tel");                                              //pobranie telefonu
            news_b = request.getParameter("newsletter");                                    //pobranie newslettera
        }
        catch (NumberFormatException | NullPointerException ex){                            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                                  //przekierowanie do strony głównej
            return;                                                                         //przerwanie działania funkcji
        }                                                                                   //</editor-fold>  
        
        User userb = userDAO.readUser(id_usr);                                              //ustawienie zmiennej na potrzeby historii
      //uaktualnienie danych                                                                <editor-fold defaultstate="collapsed" desc="uaktualnienie danych">
        if (null != news_b) news = true;                                                    //jeśli newsletter jest zaznaczony ustawianie go na prwadę
        User user = new User(id_usr, name, surname, tel, news);                             //model zmiany informacji
        boolean q = userDAO.updateUsr(user);                                                //zmiana informacji
        if(q){ sess.setAttribute("succ_disc","Pomyślnie zmieniono informacje.");}           //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
        else{                                                                               //w przeciwnym wypadku
            sess.setAttribute("err_disc","Wystąpił błąd podczas zmiany informacji.");       //wyświetlenie odpowiedniego komunikatu o błędzie
            String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
            response.sendRedirect(url);
            return;                                                                         //zatrzymanie działania funkcji
        }                                                                                   //</editor-fold> 
            
        User usr = userDAO.read(id_usr);                                                    //pobranie nowych informacji
        sess.setAttribute("information",usr);                                               //iudtawienie nowych informacji w sesji
        
      //ustawianie historii modyfikacji                                                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
        beforeQuery = "UPDATE `user` SET `name-u` =' "+userb.getName()+"',`surname-u` = '"+userb.getSurname()+"',"
                   + "`telephone` = '"+userb.getTel()+"', `newsletter` = "+userb.isNewsletter()+""
                    +" WHERE `id_user` = "+id_usr;                                                                                  //ustawienie zapytania "przed zmianą" dla historii
        afterQuery = "UPDATE `user` SET `name-u` = '"+user.getName()+"',`surname-u` = '"+user.getSurname()+"',"
                   + "`telephone` = '"+user.getTel()+"', `newsletter` = "+userb.isNewsletter()+""
                    +" WHERE `id_user` = "+id_usr;                                                                                  //ustawienie zapytania SQL "po zmianie" dla historii
        description = "Modyfikacja informacji o użytkowniku";                                                                       //ustawienie opisu
        if(!userb.getName().equals(usr.getName())){ modify="imię: "+userb.getName()+" -> "+usr.getName()+"<br>"; }                  //sprawdzanie zmiany imienia
        if(!userb.getSurname().equals(usr.getSurname())){ modify+="nazwisko: "+userb.getSurname()+" -> "+usr.getSurname()+"<br>"; } //sprawdzanie zmiany nazwiska
        if(!userb.isNewsletter()==usr.isNewsletter()){ modify+="news: "+userb.isNewsletter()+" -> "+usr.isNewsletter()+"<br>"; }  //sprawdzanie zmiany newslettera
        History his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);                         //ustawienie modelu historii
        histDAO.create(his);                                                                                                        //dodanie historii do bazy danych </editor-fold>
        
        response.sendRedirect("settings");                                                  //przekierowanie do strony ustawień
    } 
    
    //zmiana adresu użytkownika
    private void updateAddrr(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
     
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 2;                                                     //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu
        UserMeta user = null;                                                               //zmienna pomocnicza </editor-fold>
        
      //sprawdzenie czy użytownik jest zalogowany                                           <editor-fold defaultstate="collapsed" desc="sprawdzenie czy użytownik jest zalogowany">  
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }                         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NumberFormatException | NullPointerException ex){                            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                                  //przekierowanie do strony głównej
            return;                                                                         //przerwanie działania funkcji
        }                                                                                   //</editor-fold>   
      
      //sprawdzanie czy wszystkie wymagane dane adresu zostały przesłane            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane adresu zostały przesłane">
        String str,anr,acode,apost,town,astate,acount;                              //deklaracja zmiennych adresu
        String fname = null,fnip = null,fmail = null,ftel = null; boolean firm_m = false; //deklaracja zmiennych firmy
        int id = NULL; boolean islogg_m = true;                                     //dodatkowe zmienne
        try{                                                                        //pobieranie danych
            id = Integer.parseInt(request.getParameter("idMeta"));                      //identyfikator adresu
          //zmienne adresu  
            str = Jsoup.parse(request.getParameter("street")).text();               //ulica
            anr = request.getParameter("nbr");                                      //numer domu/nieszkania/bloku...
            acode = request.getParameter("code");                                   //kod pocztowy
            apost = Jsoup.parse(request.getParameter("post")).text();               //poczta
            town = Jsoup.parse(request.getParameter("town")).text();                //miejscowość
            astate = Jsoup.parse(request.getParameter("state")).text();             //województwo
            acount = Jsoup.parse(request.getParameter("country")).text();           //kraj
        }
        catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                          //przekierowanie do strony głównej
            return;                                                                 //przerwanie działania funkcji
        }                                                                           //</editor-fold>
        
        UserMeta firm = usermDAO.checkFirm(id);                                     //sprawdzenie czy dany adres jest firmą
        if (firm.isFirm() != false){                                                //jeśli jest to firma
          //sprawdzanie czy wszystkie wymagane dane firmy zostały przesłane         <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane firmy zostały przesłane">
            try{                                                                    //pobieranie danych
              //zmienne firmy
                firm_m = true;
                fname = Jsoup.parse(request.getParameter("name_firm")).text();      //nazwa firmy
                fnip = Jsoup.parse(request.getParameter("nip")).text();             //NIP
                fmail = Jsoup.parse(request.getParameter("mail_firm")).text();      //e-mail firmy
                ftel = Jsoup.parse(request.getParameter("tel_firm")).text();        //telefon firmy
            }
            catch (NumberFormatException | NullPointerException ex){                //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak dostępu!");                      //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                      //przekierowanie do strony głównej
                return;                                                             //przerwanie działania funkcji
            }                                                                       //</editor-fold>
            
          //aktualizacja danych                                                                     <editor-fold defaultstate="collapsed" desc="aktualizacja danych">
            UserMeta userm = new UserMeta(id, id_usr, islogg_m, firm_m, fname,
                fnip,fmail,ftel,str,anr,town,astate,acode,apost,acount);                            //model do uaktualnienia adresu firmy
            
            user = usermDAO.readFirmN(id);                                                          //definicja zmiennej dla historii
            boolean q = usermDAO.update(userm);                                                     //uaktualnienie danych
            if(q){ sess.setAttribute("succ_disc","Pomyślnie zmieniono adres firmy.");}              //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas zmiany adresu firmy.");         //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>
         
          //ustawianie historii modyfikacji                                                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "UPDATE `user-meta` SET `logged` = "+user.isLogged()+",`firm` = "+user.isFirm()+", `name_firm` = '"+user.getFirm_name()+"',"
                + "`nip_firm` = '"+user.getFirm_nip()+"',`firm_email` = '"+user.getFirm_email()+"',`firm_tel` = '"+user.getFirm_tel()+"', "
                + "`adr_str` = '"+user.getAdr_str()+"',`adr_nr` = '"+user.getAdr_nr()+"',`adr_town` = '"+user.getAdr_town()+"',"
                + "`adr_state` = '"+user.getAdr_state()+"', `adr_code` = '"+user.getAdr_code()+"', `adr_post` = '"+user.getAdr_post()+"',"
                + "`adr_count` = '"+user.getAdr_count()+"' WHERE `id_user_m` = '"+id+"'";                                                                  //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "UPDATE `user-meta` SET `logged` = "+islogg_m+",`firm` = "+firm_m+", `name_firm` = '"+fname+"',"
                + "`nip_firm` = '"+fnip+"',`firm_email` = '"+fmail+"',`firm_tel` = '"+ftel+"', `adr_str` = '"+str+"',"
                + "`adr_nr` = '"+anr+"',`adr_town` = '"+town+"',`adr_state` = '"+astate+"', `adr_code` = '"+acode+"',"
                + "`adr_post` = '"+apost+"',`adr_count` = '"+acount+"' WHERE `id_user_m` = '"+id+"'";                                                                                                         //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Modyfikacja adresu firmy";                                                                                                       //ustawienie opisu 
            if(!user.getFirm_name().equals(fname)){ modify="nazwa firmy: "+user.getFirm_name()+" -> "+fname+"<br>"; }                                       //sprawdzanie zmiany nazwy firmy
            if(!user.getFirm_email().equals(fmail)){ modify+="email firmy: "+user.getFirm_email()+" -> "+fmail+"<br>"; }                                    //sprawdzanie zmiany e-mailu
            if(!user.getFirm_nip().equals(fnip)){ modify+="NIP: "+user.getFirm_nip()+" -> "+fnip+"<br>"; }                                                  //sprawdzanie zmiany numeru NIP
            if(!user.getFirm_tel().equals(ftel)){ modify+="telefon firmy: "+user.getFirm_tel()+" -> "+ftel+"<br>"; }                                                     //sprawdzanie zmiany telefonu firmy
            if(!user.getAdr_str().equals(str)){ modify+="ulica: "+user.getAdr_str()+" -> "+str+"<br>"; }                                                         //sprawdzanie zmiany ulicy
            if(!user.getAdr_nr().equals(anr)){ modify+="numer: "+user.getAdr_nr()+" -> "+anr+"<br>"; }                                                           //sprawdzanie zmiany numeru mieszkania
            if(!user.getAdr_town().equals(town)){ modify+="miejscowość: "+user.getAdr_town()+" -> "+town+"<br>"; }                                                     //sprawdzanie zmiany miejscowości
            if(!user.getAdr_state().equals(astate)){ modify+="województwo: "+user.getAdr_state()+" -> "+astate+"<br>"; }                                               //sprawdzanie zmiany województwa
            if(!user.getAdr_code().equals(acode)){ modify+="kod pocztowy: "+user.getAdr_code()+" -> "+acode+"<br>"; }                                                   //sprawdzanie zmiany kodu pocztowego
            if(!user.getAdr_post().equals(apost)){ modify+="poczta: "+user.getAdr_post()+" -> "+apost+"<br>"; }                                                   //sprawdzanie zmiany poczty
            if(!user.getAdr_count().equals(acount)){ modify+="kraj: "+user.getAdr_count()+" -> "+acount+"<br>"; }                                               //sprawdzanie zmiany kraju </editor-fold>
        }
        else{                                                                       //jeśli nie jest to firma
          //aktualizacja danych                                                                     <editor-fold defaultstate="collapsed" desc="aktualizacja danych">
            UserMeta userm = new UserMeta(id, islogg_m, id_usr, str, anr, town, astate, 
                    acode,apost,acount);                                                            //model do uaktualnienia adresu firmy
            user = usermDAO.readAdrN(id);                                                           //definicja zmiennej dla historii
            boolean q = usermDAO.updateAdr(userm);                                                  //uaktualnienie danych 
            if(q){ sess.setAttribute("succ_disc","Pomyślnie zmieniono adres.");}                    //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas zmiany adresu.");               //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>
            
          //ustawianie historii modyfikacji                                                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "UPDATE `user-meta` SET `logged` = "+user.isLogged()+", `adr_str` = '"
                +user.getAdr_str()+"',`adr_nr` = '"+user.getAdr_nr()+"',"
                + "`adr_town` = '"+user.getAdr_town()+"', `adr_state` = '"+user.getAdr_state()
                +"', `adr_code` = '"+user.getAdr_code()+"', "+ "`adr_post` = '"+user.getAdr_post()+"',`adr_count` = '"
                +user.getAdr_count()+"' WHERE `id_user_m` = '"+id+"'";                                                                 //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "UPDATE `user-meta` SET `logged` = "+islogg_m+", `adr_str` = '"+str+"',"
                + "`adr_nr` = '"+anr+"',`adr_town` = '"+town+"',`adr_state` = '"+astate+"', `adr_code` = '"+acode+"',"
                + "`adr_post` = '"+apost+"',`adr_count` = '"+acount+"', `id_user-m` = '"+id_usr+"' "
                + " WHERE `id_user_m` = '"+id+"'";                                                                                     //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Modyfikacja adresu";                                                                                         //ustawienie opisu
            if(!user.getAdr_str().equals(str)){ modify+="ulica: "+user.getAdr_str()+" -> "+str+"<br>"; }                                     //sprawdzanie zmiany ulicy
            if(!user.getAdr_nr().equals(anr)){ modify+="numer: "+user.getAdr_nr()+" -> "+anr+"<br>"; }                                       //sprawdzanie zmiany numeru mieszkania
            if(!user.getAdr_town().equals(town)){ modify+="miejscowość: "+user.getAdr_town()+" -> "+town+"<br>"; }                                 //sprawdzanie zmiany miejscowości
            if(!user.getAdr_state().equals(astate)){ modify+="województwo: "+user.getAdr_state()+" -> "+astate+"<br>"; }                           //sprawdzanie zmiany województwa
            if(!user.getAdr_code().equals(acode)){ modify+="kod pocztowy: "+user.getAdr_code()+" -> "+acode+"<br>"; }                               //sprawdzanie zmiany kodu pocztowego
            if(!user.getAdr_post().equals(apost)){ modify+="poczta: "+user.getAdr_post()+" -> "+apost+"<br>"; }                               //sprawdzanie zmiany poczty
            if(!user.getAdr_count().equals(acount)){ modify+="kraj: "+user.getAdr_count()+" -> "+acount+"<br>"; }                           //sprawdzanie zmiany kraju </editor-fold>
        }
        History his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);   //stworzenie modelu historii
        histDAO.create(his);                                                                                  //utworzenie historii
        
      //uaktualnienie sesji
        List<UserMeta> usrm = usermDAO.read(id_usr);                                                //wczytanie nowych danych
        sess.setAttribute("addresses",usrm);                                                        //uaktualnienie sesji
        
        response.sendRedirect("settings");                                                  //przekierowanie do strony ustawień                                                          
       
    } 
     
    //dodawanie adresu użytkownika
    private void insertAddrr(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 1;                                                     //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu
        UserMeta user = null;                                                               //zmienna pomocnicza </editor-fold>
        
      //sprawdzenie czy użytownik jest zalogowany                                           <editor-fold defaultstate="collapsed" desc="sprawdzenie czy użytownik jest zalogowany">  
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }                         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NumberFormatException | NullPointerException ex){                            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                                  //przekierowanie do strony głównej
            return;                                                                         //przerwanie działania funkcji
        }                                                                                   //</editor-fold>   
      
      //sprawdzanie czy wszystkie wymagane dane adresu zostały przesłane            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane adresu zostały przesłane">
        String str,anr,acode,apost,town,astate,acount;                              //deklaracja zmiennych adresu
        String firm = null,fname = null,fnip = null,fmail = null,ftel = null; boolean firm_m = false; //deklaracja zmiennych firmy
        int id = NULL; boolean islogg_m = true;                                     //dodatkowe zmienne
        try{                                                                        //pobieranie danych
          //zmienne adresu  
            str = Jsoup.parse(request.getParameter("street")).text();               //ulica
            anr = request.getParameter("nbr");                                      //numer domu/nieszkania/bloku...
            acode = request.getParameter("code");                                   //kod pocztowy
            apost = Jsoup.parse(request.getParameter("post")).text();               //poczta
            town = Jsoup.parse(request.getParameter("town")).text();                //miejscowość
            astate = Jsoup.parse(request.getParameter("state")).text();             //województwo
            acount = Jsoup.parse(request.getParameter("country")).text();           //kraj
           //znacznik firmy 
            firm = request.getParameter("firmCh");
        }
        catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                          //przekierowanie do strony głównej
            return;                                                                 //przerwanie działania funkcji
        }                                                                           //</editor-fold>
        
        if (null != firm){                                                          //jeśli jest to firma
          //sprawdzanie czy wszystkie wymagane dane firmy zostały przesłane         <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane firmy zostały przesłane">
            try{                                                                    //pobieranie danych
              //zmienne firmy
                firm_m = true;
                fname = Jsoup.parse(request.getParameter("name_firm")).text();      //nazwa firmy
                fnip = Jsoup.parse(request.getParameter("nip")).text();             //NIP
                fmail = Jsoup.parse(request.getParameter("mail_firm")).text();      //e-mail firmy
                ftel = Jsoup.parse(request.getParameter("tel_firm")).text();        //telefon firmy
            }
            catch (NumberFormatException | NullPointerException ex){                //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak dostępu!");                      //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                      //przekierowanie do strony głównej
                return;                                                             //przerwanie działania funkcji
            }                                                                       //</editor-fold>
            
          //dodawanie danych                                                                         <editor-fold defaultstate="collapsed" desc="dodawanie danych">
            UserMeta userm = new UserMeta(id, id_usr, islogg_m, firm_m, fname,
                fnip,fmail,ftel,str,anr,town,astate,acode,apost,acount);                                //model do dodania adresu firmy
            
            boolean q = usermDAO.create(userm);                                                         //dodawnaie danych
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano adres firmy.");}                     //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                       //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania adresu firmy.");          //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                                 //zatrzymanie działania funkcji
            }                                                                                           //</editor-fold>
              
          //ustawianie historii modyfikacji                                                                                         <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            int idF = usermDAO.read(userm);
            beforeQuery = "DELETE FROM `user-meta` WHERE `id_user_m` = "+idF;                                                       //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "INSERT INTO `user-meta` (`logged`, `id_user-m`, `firm`, `name_firm`, `nip_firm`,"
                    + " `firm_email`, `firm_tel`, `adr_str`, `adr_nr`, `adr_town`, `adr_state`, `adr_code`, "
                    + " `adr_post`, `adr_count`) VALUES ("+islogg_m+", "+id_usr+", "+firm_m+",'"+fname+"', '"+fnip+"', "
                    + "'"+fmail+"', '"+ftel+"', '"+str+"', '"+anr+"', '"+town+"', '"+astate+"', '"+acode+"',"
                    + " '"+apost+"', '"+acount+"')";                                                                                //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Dodanie nowego adresu firmy";                                                                            //ustawienie opisu   
            modify="nazwa firmy: "+userm.getFirm_name()+"<br>email firmy: "+userm.getFirm_email()+"<br>NIP: "+userm.getFirm_nip()
                    +"<br>telefon firmy: "+userm.getFirm_tel()+"<br>ulica: "+userm.getAdr_str()+"<br>numer: "+userm.getAdr_nr()
                    +"<br>miejscowość: "+userm.getAdr_town()+"<br>województwo: "+userm.getAdr_state()
                    +"<br>kod pocztowy: "+userm.getAdr_code()+"<br>poczta: "+userm.getAdr_post()+"<br>kraj: "+userm.getAdr_count();  //ustawienie modyfikacji  </editor-fold>
        }
        else{                                                                       //jeśli nie jest to firma
          //dodawanie danych                                                                     <editor-fold defaultstate="collapsed" desc="dodawanie danych">
            UserMeta userm = new UserMeta(id, islogg_m, id_usr, str, anr, town, astate, 
                    acode,apost,acount);                                                            //model do dodania adresu firmy
            boolean q = usermDAO.createAdrL(userm);                                                 //dodawanie danych 
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano adres.");}                       //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania adresu.");            //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>
            
          //ustawianie historii modyfikacji                                                                                       <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            int idA = usermDAO.read(userm);
            beforeQuery = "DELETE FROM `user-meta` WHERE `id_user_m` = "+idA;                                                       //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "INSERT INTO `user-meta` (`logged`, `id_user-m`, `firm`, `adr_str`, `adr_nr`, `adr_town`, "
                  + "`adr_state`, `adr_code`, `adr_post`, `adr_count`) VALUES ("+islogg_m+", "+id_usr+", false,"
                  + " '"+str+"', '"+anr+"', '"+town+"', '"+astate+"', '"+acode+"', '"+apost+"', '"+acount+"')";                     //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Dodanie nowego adresu";                                                                                  //ustawienie opisu 
            modify="ulica: "+userm.getAdr_str()+"<br>numer: "+userm.getAdr_nr()+"<br>miejscowość: "+userm.getAdr_town()
                    +"<br>województwo: "+userm.getAdr_state()+"<br>kod pocztowy: "+userm.getAdr_code()
                    +"<br>poczta: "+userm.getAdr_post()+"<br>kraj: "+userm.getAdr_count();                                          //ustawienie modyfikacji </editor-fold>
        }
        History his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);        //stworzenie modelu historii
        histDAO.create(his);                                                                                       //utworzenie historii
        
      //uaktualnienie sesji
        List<UserMeta> usrm = usermDAO.read(id_usr);                                                //wczytanie nowych danych
        sess.setAttribute("addresses",usrm);                                                        //uaktualnienie sesji
        
        response.sendRedirect("settings");                                                  //przekierowanie do strony ustawień
    } 
    
    //krótki opis servletu
    @Override
    public String getServletInfo() {
        return "Ten servlet obsługuje użytkownika zalogowanego i wszystko z nim związane.";
    }
    
    //"ręczny debuger"
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String q1, String q2)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControlUser</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> " + q1 +  "</h1>");
            out.println("<h1> " + q2 + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}