/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 14.11.2021 r.
 */
package jwl.jewelry;

import java.io.*;
import java.sql.*;
import static java.sql.Types.NULL;
import java.time.*;
import java.time.format.*;
import java.util.*;
import org.jsoup.Jsoup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

//DAO
import jwl.DAO.*;
//model
import jwl.model.*;

/**
 * ControllerServlet.java
 * Ten servlet obsługuje logowanie użytkownika zarówno z rangą jak i bez niej.
 * @author DRzepka
 */

@WebServlet(name="ControlLogin", urlPatterns={"/login", "/loging", "/logout", "/redirectRnk"}, asyncSupported = true)
public class ControlLogin extends HttpServlet {
  //zmienne                                               <editor-fold defaultstate="collapsed" desc="zmienne">
    private static final long serialVersionUID = 1L;
  //tabele główne
    private LoginDAO loginDAO;       //login
    private UserDAO userDAO;         //użytkownik
    private UserMDAO usermDAO;       //adres
    private HistoryDAO histDAO;      //historia
  //sesja
    private HttpSession sess; 
  //formatowanie daty dla konkretnej zmiennej czasu
    private DateTimeFormatter formatLocalDate;      //</editor-fold> 
    //inicjalizacja zmiennych 
    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");                   //pobieranie url bazy
        String jdbcUsername = getServletContext().getInitParameter("jdbcLogin");            //pobieranie nazwy użytkownika "login"
        String jdbcPassword = getServletContext().getInitParameter("jdbcLoginPassw");       //pobieranie hasła użytkownika "login"
        String jdbcHist = getServletContext().getInitParameter("jdbcHistory");              //pobieranie nazwy użytkownika "history"
        String jdbcHistPassw = getServletContext().getInitParameter("jdbcHistoryPassw");    //pobieranie hasła użytkownika "history"
       
      //tabele główne
        loginDAO = new LoginDAO(jdbcURL, jdbcUsername, jdbcPassword);       //login
        userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);         //użytkownik
        usermDAO = new UserMDAO(jdbcURL, jdbcUsername, jdbcPassword);       //adres
        histDAO = new HistoryDAO(jdbcURL, jdbcHist, jdbcHistPassw);         //historia
      //formatowanie czasu    
        formatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   //formatowanie daty 
    }
    
    //Obsługa POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    //Obsługa GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getServletPath();                               //sprawdzenie ścieżki z której przyszła odpowiedź
        try {
            switch (action) {                                                   //obsłyga po ścieżce
               case "/login": showLog(request,response);  break;                //przekierownaie do strony logowania
               case "/loging": logIn(request,response); break;                  //logowanie użytkownika
               case "/logout": logOut(request,response); break;                 //wylogowanie użytkownika
               case "//redirectRnk": logOut(request,response); break;                 //wylogowanie użytkownika
            }
        } catch (SQLException ex) { throw new ServletException(ex); }           //obsługiwanie błędów
    }

    //przekierownaie do strony logowania
    private void showLog(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/log/LogIn.jsp");    //przekierownie do pliku
        dispatcher.forward(request, response);                                           //przekazanie parametrów
    }
    
    //logowanie użytkownika
    private void logIn(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane                   <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        String login = null, passw = null;                                          //deklaracja zmiennych hasła
        try{                                                                        //pobieranie danych
          //zmienne hasła
            login = request.getParameter("login");                                  //login = mail
            passw = Jsoup.parse(request.getParameter("passw")).text();              //hasło 
        }
        catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
            String url = request.getHeader("referer");                              //sprawdzanie wcześniejszej strony
            if((url!=null)){                                                        //jeśli strona poprzednia nie jest pusta
                String urls[] = url.split("/");                                     //podzielenie całej ścieżki
                if(urls[urls.length-1].equals("login")){                           //jeśli jest to strona rejestracji
                    sess.setAttribute("err_disc","Brak poprawnych danych!");        //ustawianie zmiennej sesyjnej
                    response.sendRedirect("login");                                 //przekierowanie do formularza rejestracji
                    return;                                                         //przerwanie działania funkcji 
                }
                else{                                                               //w przeciwnym wypadku
                    if(urls[urls.length-1].equals("basket1")){                      //jeśli jest to strona koszyka
                        sess.setAttribute("err_disc","Brak poprawnych danych!");    //ustawianie zmiennej sesyjnej
                        response.sendRedirect("basket1");                           //przekierowanie do formularza rejestracji
                        return;                                                     //przerwanie działania funkcji 
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    }
                }
            }
            else{                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Brak dostępu!");                      //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                      //przekierowanie do strony głównej
                return;                                                             //przerwanie działania funkcji
            }
        }                                                                           //</editor-fold>
        
        Login log = loginDAO.read(login);                                           //pobranie danych z tabeli login poprzez login(e-mail)
        if((log!=null)){
            
            if(log.getPassw().equals(passw)){                                           //jeśli hasło podane w formularzu jest takie samo jak to z bazy danych 
              //uaktualnianie daty logowania                                            <editor-fold defaultstate="collapsed" desc="uaktualnianie daty logowania">          
                String lastLog = loginDAO.getLastLog(login);                            //pobranie ostatniej daty dla historii
                String last_log = formatLocalDate.format(LocalDateTime.now());          //ustawienie czasu serwera
                Login logging = new Login(log.getId(), login, passw, last_log);         //model logowania
                boolean q = loginDAO.updateLastLog(logging);                            //zmiana czasu logowania
                if(q){ sess.setAttribute("succ_disc","Logowanie przebiegło pomyślnie.");}//jeśli się udało wyświetl odpowiedni komunikat
                else{                                                                    //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Błąd logowania.");                    //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                          //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                             //zatzrymanie działania funkcji
                }                                                                       //</editor-fold>

              //ustawianie historii modyfikacji                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                String now = formatLocalDate.format(LocalDateTime.now());                       //ustawienie czasu servera na potzreby historii
                String beforeQuery = "Opcja niedostępna",  afterQuery = "Opcja niedostępna";    //ustawienie zapytań SQL (opcja niedostępna aby nie można było zmienić czasu ostatniego ologowania z poziomu zwykłego użytkownika)       
                String description = "Nowe logowanie użytkownika"; int action = 5;              //ustawienie opisu oraz identyfikatora akcji
                String modify = "poprzednie logowanie: <br>"+lastLog;                               //przypisanie zmiennej modyfikacji
                History his = new History(NULL, log.getId(), action, description, 
                        now, beforeQuery, afterQuery, modify);                                  //model dla historii
                q = histDAO.create(his);                                                        //dodanie historii do bazy danych 
                if(!q){ sess.setAttribute("err_disc","Błąd logowania.");}                       //jeśli nie dało się dodać historii powiadom o błędzie </editor-fold>

              //przypisywanie zmiennych sesyjnych                                       <editor-fold defaultstate="collapsed" desc="przypisywanie zmiennych sesyjnych">
                sess.setAttribute("id_user_logged",log.getId());                        //dodanie identyfikatora użytkownika w sesji
                //sess.setAttribute("succ_disc",sess.getAttribute("id_user_logged"));
               //zapisywanie podstwowychdanych  
                User usr = userDAO.read(log.getId());                                   //pobranie danych użytkownika
                sess.setAttribute("information",usr);                                   //zapisanie w sesji informacji o użytkowniku
               //zapisywanie adresów  
                List<UserMeta> usrm = usermDAO.read(log.getId());                       //pobranie listy adresów
                sess.setAttribute("addresses",usrm);                                    //zapisanie w sesji listy adresów użytkownika
                int id_meta = 0;                                                        //przypisanie zmiennej dla pierwszego adresu
                sess.setMaxInactiveInterval(172800);                                    //ustalenie sesji na dwa dni
                for (UserMeta item : usrm) { id_meta = item.getIdMeta(); break; }       //wyciąganie pierwszego adresu z listy
                sess.setAttribute("userMeta_id",id_meta);                               //dodanie pierwszego adresu użytkownika do sesji
               //sprawdzanie rangi  
                if(usr.isRank()==true){                                                 //jeśli jest to uzytkownik z rangą
                    sess.setAttribute("rank",usr.getId_rank());                         //dodanie id rangi do sesji
                }
               //dodanie podstawowych danych dla koszyka  
                sess.setAttribute("name",usr.getName());                                //zapisanie imienia w sesji
                sess.setAttribute("surname",usr.getSurname());                          //zapisanie nazwiska w sesji
                sess.setAttribute("mail",usr.getEmail());                               //zapisanie e-mailu w sesji
                sess.setAttribute("tel",usr.getTel());                                  //zapisanie numeru telefonu w sesji </editor-fold>

              //przekierowanie do odpowiedniej strony                                           <editor-fold defaultstate="collapsed" desc="przekierowanie do odpowiedniej strony">     
                String url = request.getHeader("referer");                                      //sprawdzanie wcześniejszej strony
                if((url!=null)){                                                                //jeśli strona poprzednia nie jest pusta
                    String urls[] = url.split("/");                                             //podzielenie całej ścieżki
                    if((urls[urls.length-1].equals("basket1"))){                                //jeśli jest to strona rejestracji
                        response.sendRedirect("summaryBask");                                   //przekierowanie do formularza rejestracji
                        return;                                                                 //przerwanie działania funkcji 
                    }
                    else{                                                                       //w przeciwnym wypadku
                        if(usr.isRank()==true){ sendTo(request, response, usr.getId_rank()); }  //jeśli jest to użytkownik z rangą przekieruj ją w odpowiednie miejsce
                        else{ sendTo(request, response, 0); }                                   //w przecienym wypadku (bez rangi) przekieruj w odpowiednie miejsce
                    }
                }
                else{                                                                           //w przeciwnym wypadku
                    if(usr.isRank()==true){ sendTo(request, response, usr.getId_rank()); }      //jeśli jest to użytkownik z rangą przekieruj ją w odpowiednie miejsce
                    else{ sendTo(request, response, 0); }                                       //w przecienym wypadku (bez rangi) przekieruj w odpowiednie miejsce 
                }                                                                               //</editor-fold>
            }
            else{                                                                       //w przeciwnym wypadku (jeśli hasła nie są takie same)
                sess.setAttribute("err_disc","Login lub hasło nieoprawne.");            //zapisanie odpowiedniego komunikatu
                response.sendRedirect("login");                                         //przekierowanie do strony logowania
            } 
        }
        else{                                                                       //w przeciwnym wypadku (jeśli hasła nie są takie same)
            sess.setAttribute("err_disc","Login lub hasło nieoprawne.");            //zapisanie odpowiedniego komunikatu
            response.sendRedirect("login");                                         //przekierowanie do strony logowania
        } 
    }
    
    //przekierowanie w odpowiednie miejsce
    private void sendTo(HttpServletRequest request, HttpServletResponse response, int choose) 
            throws SQLException, IOException {
        
        switch(choose){                                                             //przekierowanie w odpowiednie miejsce
            case 0: response.sendRedirect("orders"); break;                         //dla użytkownika bez rangi
            case 1: response.sendRedirect("administrator?t=1"); break;              //dla administratora
            case 2: response.sendRedirect("worker"); break;                         //dla pracownika
            case 3: response.sendRedirect("corrector?t=1"); break;                  //dla korektora
            case 4: response.sendRedirect("supplier?t=1"); break;                   //dla zaopatzreniowca
        }
    }
    
    //przekierowanie do odpowiedniego widoku rangi z poziomu podstawowej aplikajci
    private void redirectRnk(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        History his; int id_usr = 0; int action = 1;                                                     //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
        
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        finally{
           sendTo(request, response, rnk.getId_rank());                     //przekierownie do odpowiedniejstrony
        }
    }
    
    
  
    //wylogowanie użytkownika
    private void logOut(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
            sess=request.getSession(true);                                          //pobranie sesji poprzedniej
            
          //sprawdzanie czy użytkownik jest zalogowany                              <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
            int id_usr = 0;                                                         //ustawienie zmiennej
            try { id_usr = (int) sess.getAttribute("id_user_logged"); }             //pobranie zmiennej sesyjnej z id użytkownika
            catch (NullPointerException e) {                                        //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak dostępu!");                      //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                      //przekierowanie do strony głównej
                return;                                                             //przerwanie działania funkcji
            }                                                                       //</editor-fold>
            
            sess.invalidate();                                                      //usunięcie sesje poprzedniej
            
            sess=request.getSession(true);                                          //pobranie nowej sesji 
            sess.setAttribute("succ_disc","Wylogowanie przebiegło pomyślnie!");     //zapisanie odpowiedniego komunikatu
            response.sendRedirect("home");                                          //przekierowaie do strony głównej
    }
       
    //krótki opis servletu
    @Override
    public String getServletInfo() {
        return "Ten servlet obsługuje logowanie użytkownika zarówno z rangą jak i bez niej.";
    }

    //"ręczny debuger"
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControlLogin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControlLogin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}