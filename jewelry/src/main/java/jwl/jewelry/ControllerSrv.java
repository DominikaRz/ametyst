/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 10.05.2021 r.
 */
package jwl.jewelry;


import java.io.*;
import java.sql.*;
import static java.sql.Types.NULL;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import org.jsoup.Jsoup;
 
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

//DAO
import jwl.DAO.dict.*;
import jwl.DAO.*;
//model
import jwl.model.*;
import jwl.model.dict.*;
import jwl.model.link.*;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author DRzepka
 */

@WebServlet(name="ControllerSrv", value="/", asyncSupported = true)
public class ControllerSrv extends HttpServlet {
  //zmienne                                                                    <editor-fold defaultstate="collapsed" desc="zmienne">
    private static final long serialVersionUID = 1L;

    private ProductMDAO prodmDAO;
    private UserDAO userDAO;
    private UserMDAO usermDAO;
    private HistoryDAO histDAO;
  //sesssion
    private HttpSession sess; 
  //date formatters  
    private DateTimeFormatter formatLocalDate; //</editor-fold>
    //inicjalizacja zmiennych 
    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUser");
        String jdbcPassword = getServletContext().getInitParameter("jdbcUserPassw");
        String jdbcHist = getServletContext().getInitParameter("jdbcUsername");              //pobieranie nazwy użytkownika "history"
        String jdbcHistPassw = getServletContext().getInitParameter("jdbcPassword");    //pobieranie hasła użytkownika "history"
     
       //product
        prodmDAO = new ProductMDAO(jdbcURL, jdbcUsername, jdbcPassword);
       //user
        usermDAO = new UserMDAO(jdbcURL, jdbcUsername, jdbcPassword);
        userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
       //history 
        histDAO = new HistoryDAO(jdbcURL, jdbcHist, jdbcHistPassw);
       //formatowanie daty 
        formatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
   //obsługa POST 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
   //obsługa GET 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        
        try {
            switch (action) {
            //generalne    
               case "/home":  showHome(request,response); break;
               case "/error":  error(request,response); break; 
               case "/about": showStatic(request,response, 1); break;
               case "/customer": showStatic(request,response, 2); break;
               case "/contact": showStatic(request,response, 3); break;
               case "/delivery": showStatic(request,response, 4); break;
               case "/payment": showStatic(request,response, 5); break;
               case "/privacy": showStatic(request,response, 6); break;
            //historia
               case "/history": history(request,response); break;   
            //inne servlety: 
               //Menu i produkt w servlecie ControlProducts
               //Odsługa koszyka i zamówienia w servlecie ControlBasket
               //Obsługa logowania w servlecie ControlLogin.java
               //użytkownicy:
                   //Użytkownik bez rangi w servlecie ControlUser.java
                   //Użytkownik z rangą w servlecie ControlUserRnk.java
            //w przypadku braku strony   
               default: 
                   sess=request.getSession(true);
                   sess.setAttribute("err_disc","Brak dostępu!");   //ustawienie konunikatu aby zmylić potencjalnych hakerów
                   response.sendRedirect("home");                   //przekierowanie do strony głównej
               break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
 //generalne
    //przekierowanie do strony głównej
    private void showHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        List<ProductMeta> listProdIndex = prodmDAO.readIndexRand();
        request.setAttribute("listProdI", listProdIndex);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    } 
    
    //przekierowanie do statycznych stron
    private void showStatic(HttpServletRequest request, HttpServletResponse response, int tab)
            throws ServletException, IOException, SQLException {
        
        RequestDispatcher dispatcher;                                           //ustawienie zmiennej przekierowującej
        
        switch(tab){                                                            //dla konkretnych miejsc
            case 1: //o nas
                dispatcher = request.getRequestDispatcher("jsp/static/about.jsp");
                dispatcher.forward(request, response);
             break;
            case 2:  //informacje dla klienta
                dispatcher = request.getRequestDispatcher("jsp/static/customer.jsp");
                dispatcher.forward(request, response);
             break;
            case 3:  //kontakt
                dispatcher = request.getRequestDispatcher("jsp/static/contact.jsp");
                dispatcher.forward(request, response);
             break;
            case 4:  //dostawa
                dispatcher = request.getRequestDispatcher("jsp/static/delivery.jsp");
                dispatcher.forward(request, response);
             break;
            case 5:  //płatność
                dispatcher = request.getRequestDispatcher("jsp/static/payment.jsp");
                dispatcher.forward(request, response);
             break;
            case 6: //polityka prywatności
                dispatcher = request.getRequestDispatcher("jsp/static/privacy.jsp");
                dispatcher.forward(request, response);
             break; 
        }
        
    } 
 
    //obsługa błędu
    private void error(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
            sess=request.getSession(true);
        
            sess.setAttribute("err_disc","Musisz być zalogowany aby skorzystać z tej opcji.");
            response.sendRedirect("login");            
    }
    
    //historia zmian (cofnięcie i ponowienie zapytania)
    @SuppressWarnings("empty-statement")
    public void history(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException, SQLException {
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int id_act = 4; History his = null; boolean q = false;              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now()); String sql = null;        //ustawienie czasu i deklaracja zapytania SQL </editor-fold>
         
      //sprawdzanie czy użytkownik jest zalogowany                                  <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }                 //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException ex){                                            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                          //przekierowanie do strony głównej
            return;                                                                 //przerwanie działania funkcji
        }                                                                           //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) { }                                  //sprawdzanie błędów </editor-fold>
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane                   <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id = 0, action = 0;                                                     //deklaracja zmiennych
        try{
            id = Integer.parseInt(request.getParameter("id"));                      //pobranie identyfikatora historii
            action = Integer.parseInt(request.getParameter("a"));                   //numer akcji jaka ma być wykonana
        }
        catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                          //przekierowanie do strony głównej
            return;                                                                 //przerwanie działania funkcji
        }                                                                           //</editor-fold>
        
        History hist = histDAO.read(id);                                                //pobranie historii na podstawie pobranego dentyfikatora
        
        switch(action){                                                                 //sprawdzanie akcji jaką użytkownik chcę wykonać
            case 1: // = cofnięcie zmiany
              //obsługa historii modyfikacji                                                        <editor-fold defaultstate="collapsed" desc="obsługa historii modyfikacji">
                sql = hist.getQuery_b();                                                            //przypisanie zapytania SQL jako zapytania przed zmianą
                beforeQuery = hist.getQuery_a();                                                    //zamiana miejscami zapytań
                afterQuery = hist.getQuery_b();                                                     //zamiana miejscami zapytań
                description = "Cofnięcie zmiany";   modify = hist.getModify();                      //ustawienie opisu i modyfikacji
                his = new History(NULL, id_usr, id_act, description, now, 
                        beforeQuery, afterQuery, modify);                                           //model historii
              break;                                                                                //</editor-fold> 
            case 2: // = ponowne wykonanie zmiany
              //obsługa historii modyfikacji                                                        <editor-fold defaultstate="collapsed" desc="obsługa historii modyfikacji">
                sql = hist.getQuery_a();                                                            //przypisanie zapytania SQL jako zapytania przed zmianą
                id_act = 6;                                                                         //ustawienie identyfikatora modyfikacji na ponowienie zmiany
                beforeQuery = hist.getQuery_b();                                                    //zamiana miejscami zapytań
                afterQuery = hist.getQuery_a();                                                     //zamiana miejscami zapyta
                description = "Ponowne wykonanie zmiany";  modify = hist.getModify();               //ustawienie opisu i modyfikacji
                his = new History(NULL, id_usr, id_act, description, now, 
                        beforeQuery, afterQuery, modify);                                           //model historii
              break;                                                                                //</editor-fold>
        }
        
      //Obsługa zapytań 
        histDAO.connect();
        try{
            PreparedStatement statement = histDAO.jdbcConnection.prepareStatement(sql);
            q = statement.executeUpdate() > 0;
            if(q){                                                                              //jeśli się powiodło 
                sess.setAttribute("succ_disc","Pomyślnie wykonanio zmianę");                    //wyświetlenie odpowiedniego komunikatu
              //wymuszenie aktualizacji sesji                                   <editor-fold defaultstate="collapsed" desc="wymuszenie aktualizacji sesji">
               //użytkownik
                sess.removeAttribute("listOrder");                              //użytkownik - główna
                //nadpisanie adresów dla użytkowników
                List<UserMeta> usrm = usermDAO.read(id_usr);                    //wczytanie nowych danych
                sess.setAttribute("addresses",usrm);                            //uaktualnienie sesji
                //nadpisanie informacji użytkownika
                User usr = userDAO.read(id_usr);                                //pobranie nowych informacji
                sess.setAttribute("information",usr);                           //iudtawienie nowych informacji w sesji
               //rangi:
                //administrator:
                if(rnk.getId_rank()==1){
                    sess.removeAttribute("listDelivery");                           //administrator - słowinikowe
                    sess.removeAttribute("listUsers");                              //administrator - użytkownicy
                    sess.removeAttribute("listCatTag");                             //administrator - menu
                }
                //pracownik:
                if(rnk.getId_rank()==2){
                    sess.removeAttribute("listOrder");                              // pracownik - główna
                }
                //korektor
                if(rnk.getId_rank()==3){
                    sess.removeAttribute("listRew");                                // korektor - recenzje
                    sess.removeAttribute("listProd");                               // korektor - produkty
                    sess.removeAttribute("listCat");                                // korektor - kategorie
                    sess.removeAttribute("listTag");                                // korektor - tagi
                }
                //zaopatrzeniowiec
                if(rnk.getId_rank()==4){
                    sess.removeAttribute("listProd");                               //zaopatrzeniowiec - produkty
                    sess.removeAttribute("listShp");                                //zaopatrzeniowiec - słowinikowe 
                }                                                                   //</editor-fold> 
            }                
            else{ sess.setAttribute("err_disc","Wystąpił błąd podczas zmiany informacji");}     //w przeciwnym wypadku wyświetlenie odpowiedniego komunikatu o błędzie
            histDAO.disconnect();
            
            q = histDAO.create(his);                                                            //utworzenie historii zdarzeń                                                        
            if(q){sess.setAttribute("succ_disc","Pomyślnie wykonanio zapis w historii."); }     //wyświetlenie odpowiedniego komunikatu
            else{ sess.setAttribute("err_disc","Wystąpił błąd podczas zmiany informacji.");}    //w przeciwnym wypadku wyświetlenie odpowiedniego komunikatu o błędzie
             
        }
        catch (SQLException se){
            sess=request.getSession(true);
            sess.setAttribute("err_disc","Nie można wykonać operacji.");
        } 
        
        String url = request.getHeader("referer");  //przekierowanie do poprzedniej strony
        response.sendRedirect(url);
    }
  
    

  //<editor-fold defaultstate="collapsed" desc="">  </editor-fold>
    
}