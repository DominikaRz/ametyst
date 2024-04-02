/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 02.10.2021 r.
 */
package jwl.jewelry;

import java.io.*;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jwl.DAO.*;
import jwl.DAO.dict.*;
import jwl.model.link.*;
import jwl.model.dict.*;
import jwl.model.*;
import org.jsoup.Jsoup;

/**
 * ControlBasket.java
 * Ten servlet obsługuje zamówienie oraz koszyk uzytkownika zalogowanego i sesyjnego.
 * @author DRzepka
 */

@WebServlet(name="ControlBasket", urlPatterns={"/addBask", "/delBask", "/updBask", "/basket", 
            "/nextBasket","/basket1", "/NLbasket", "/addUMBask", "/summaryBask", "/addOrder", 
            "/checkDisc", "/updBaskAdr", "/delAllBask", "/order"}, asyncSupported = true)
public class ControlBasket extends HttpServlet {
   //zmienne                                               <editor-fold defaultstate="collapsed" desc="zmienne">
    private static final long serialVersionUID = 1L;
  //tabele główne  
    private ProductMDAO prodmDAO;   //produkt
    private OrderDAO ordDAO;        //zamówienie
    private UserMDAO usermDAO;      //użytkownik
    private HistoryDAO histDAO;     //historia
  //tabele łącznikowe
    private BasketDAO baskDAO;      //koszyk(przejściowa)
    private OrderPDAO ordpDAO;      //zamówienie-produkt(stałe dla zamówienia)
  //tabele słownikowe 
    private DeliveryDAO delDAO;     //dostawa
    private PaymentDAO payDAO;      //płatność
    private StatusDAO statDAO;      //status zamówienia
    private DiscountDAO discDAO;    //przecena
  //sesja
    private HttpSession sess;
  //formatowanie daty dla konkretnej zmiennej
    private SimpleDateFormat formatter;
    private DateTimeFormatter formatLocalDate;
  //</editor-fold>  
   //inicjalizacja zmiennych 
    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");                   //pobieranie url bazy
        String jdbcUsername = getServletContext().getInitParameter("jdbcUserNL");           //pobieranie nazwy użytkownika "userNL"
        String jdbcPassword = getServletContext().getInitParameter("jdbcUserNLPassw");      //pobieranie hasła użytkownika "userNL"
        String jdbcHist = getServletContext().getInitParameter("jdbcHistory");              //pobieranie nazwy użytkownika "history"
        String jdbcHistPassw = getServletContext().getInitParameter("jdbcHistoryPassw");    //pobieranie hasła użytkownika "history"
     
     //tabele główne
        prodmDAO = new ProductMDAO(jdbcURL, jdbcUsername, jdbcPassword);    //produkt
        usermDAO = new UserMDAO(jdbcURL, jdbcUsername, jdbcPassword);       //użytkownik
        ordDAO = new OrderDAO(jdbcURL, jdbcUsername, jdbcPassword);         //zamówienie
        histDAO = new HistoryDAO(jdbcURL, jdbcHist, jdbcHistPassw);         //historia 
     //tabele łącznikowe 
        baskDAO = new BasketDAO(jdbcURL, jdbcUsername, jdbcPassword);       //koszyk(przejściowa)
        ordpDAO = new OrderPDAO(jdbcURL, jdbcUsername, jdbcPassword);       //zamówienie-produkt(stałe dla zamówienia) 
     //tabele słownikowe 
        delDAO = new DeliveryDAO(jdbcURL, jdbcUsername, jdbcPassword);      //dostawa
       //dla zamówienia
        payDAO = new PaymentDAO(jdbcURL, jdbcUsername, jdbcPassword);       //płatność
        statDAO = new StatusDAO(jdbcURL, jdbcUsername, jdbcPassword);       //status zamówienia
        discDAO = new DiscountDAO(jdbcURL, jdbcUsername, jdbcPassword);     //przecena
        
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                //formatowanie daty
        formatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   //formatowanie daty
    }

   //obsługa POST 
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
               case "/addBask": insertBasket(request,response); break;          //dodawanie do koszyka
               case "/basket": viewBasket(request,response); break;             //wyświetlanie koszyka
               case "/delBask": deleteBasket(request,response); break;          //usuwanie z koszyka
               case "/delAllBask": deleteAllBasket(request,response); break;    //opróżnianie koszyka
               case "/updBask": updateBasket(request,response); break;          //aktualizacja ilości produktu w koszyku
               case "/checkDisc": checkDisc(request,response); break;           //sprawdzenie kodu promocyjnego
               case "/nextBasket": nextBasket(request,response); break;         //zapisanie wyborów w sesji
               case "/basket1": basketStep2(request,response); break;            //przejście do następnego kroku (wybór: zalogowny, rejestracja, bez konta)
               case "/NLbasket": BasketNL(request,response);  break;            //wpisanie danych zamówienia
               case "/addUMBask": insertUMBasket(request,response); break;      //dodanie danych zamówienia
               case "/updBaskAdr": updateBaskAddrr(request,response); break;    //aktualizacja danych zamówienia
               case "/summaryBask": sumBasket(request,response); break;         //wyświetlenie podsumowania zamówienia
               case "/addOrder": addOrder(request,response); break;             //dodanie zamówienia do daby danych
               case "/order": order(request,response); break;                   //wyświetlanie zamówienia dla użytkownika niezalogowaego
            }
        } catch (SQLException ex) { throw new ServletException(ex); }           //obsługiwanie błędów
    }
    
  //dodawanie produktu do koszyka + dodawanie sesji jeśli nie istnieje
    private void insertBasket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess = request.getSession();                                        //pobranie sesji
        
     //sprawdzanie czy wszystkie wymagane dane zostały przesłane            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id_prod = 0, quant = 0;                                         //deklaracja zmiennych
        try{ id_prod = Integer.parseInt(request.getParameter("id_pr")); }   //pobieranie id produktu
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }
        try{ quant = Integer.parseInt(request.getParameter("quantity")); }  //pobieranie ilości produktu
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów    
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
     //pobranie sesji i daty jej wygaśniecia                                <editor-fold defaultstate="collapsed" desc="pobranie sesji i daty jej wygaśniecia">
        String session = null;                                              //ustawienie zmiennej
        Cookie[] cookies = request.getCookies();                            //pobranie ciasteczek
        for (Cookie cookie : cookies) {                                     //filtrowanie ciasteczek
            if (cookie.getName().equals("session")) {                       //jeśli nazwa ciasteczka to session 
                session = cookie.getValue();                                //wyciąganie aktualnej sesji
                break;                                                      //po znalezieniu przerwij poszukiwania
            }
        }
        if(session==null){                                                  //jeśli nie znaleziono sesji
            sess.setMaxInactiveInterval(2*24*60*60);                        //ustaw sesję na 2 dni
            Cookie ck = new Cookie("session",sess.getId());                 //ustawianie aktualnej sesji do ciasteczka (JSESSIONID, które jest ustawiane automatycznie)
            ck.setMaxAge(62*24*60*60);                                      //zmiana aktualnej daty wygaśnięcia ciasteczna na: 60 s *60 min * 24 h * 62 d = ok. 2 miesiące
            response.addCookie(ck);                                         //dodawanie ciasteczka
            session = sess.getId();                                         //ustawianie sesji w zmiennej
        }                                                                   //</editor-fold>
     //pobranie daty wygaśnięcia (przybliżony czas)  musi być osobno!       <editor-fold defaultstate="collapsed" desc="pobranie daty wygaśnięcia (przybliżony czas)  musi być osobno!">
        String exp = null;                                                  //ustawienie zmiennej
        for (Cookie cookie : cookies) {                                     //filtrowanie ciasteczek
            if (cookie.getName().equals("expire_sess")) {                   //jeśli nazwa ciasteczka to expire_sess
                exp = cookie.getValue();                                    //przypisanie wartości
                break;                                                      //po znalezieniu przerwij poszukiwania
            }
        }
        if(exp==null){                                                      //jeśli nie znaleziono daty wygaśnięcia
            Date date =  new Date(sess.getCreationTime());                  //tworzenie zmiennej daty
            java.sql.Date expire = new java.sql.Date(date.getTime() + 62l*24l*60l*60l*1000l); //przeliczanie daty z typu long d*h*m*s*ms (około 2 miesiące)
            exp = formatter.format(expire);                                 //przekształcanie na ciąg znaków (String) dzięki formaterowi
            Cookie cook = new Cookie("expire_sess",exp);                    //ustawianie nowego ciasteczka
            cook.setMaxAge(62*24*60*60);                                    //zmiana aktualnej daty wygaśnięcia ciasteczna na: 60 s *60 min * 24 h * 62 d = ok. 2 miesiące
            response.addCookie(cook);                                       //dodawanie ciasteczka
            sess.setAttribute("payment", "12");                             //ustawienie domyślnej płatności 
            sess.setAttribute("delivery", "11");                            //ustawienie domyślnej dostawy
        }                                                                   //</editor-fold>
        
     //sprawdzanie czy użytkownik jest zalogowany                           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) { }                                  //sprawdzanie błędów   </editor-fold>
        
     //dodawanie/uaktualnianie stanu produktu do koszyka                    <editor-fold defaultstate="collapsed" desc="dodawanie/uaktualnianie stanu produktu do koszyka">
        boolean acc = false;                                                //ustawienie znacznika akceptacji
        if((id_usr!=0)&&(id_usr!=NULL)){                                    //jeśli użytkownik jest zalogowany
            Basket bask = new Basket(null, id_usr, id_prod, quant, exp);    //ustawianie koszyk z id użytkownika
            boolean exist = baskDAO.checkUsr(bask);                         //sprawdzanie czy produkt jest już w koszyku
            if(exist==true){                                                //jeśli istnieje
                boolean q = baskDAO.updateBaskUsr(bask);                    //dodawanie do istniejącego
                if(q){ acc = true;}                                         //jeśli się udało -> ustawianie zmiennej akceptacyjnej
                else{ sess.setAttribute("err_disc","Błąd polecenia."); }    //w przeciwnym wypadku powiadom o błędzie
             }     
            else{ baskDAO.createUsr(bask); }                                //w przeciwnym wypadku tworzy nowy rekord w bazie danych
        }
        else{                                                               //jeśli użytkownik NIE jest zalogowany
            Basket bask = new Basket(session, NULL, id_prod, quant, exp);   //tworzy koszyk z sesją
            boolean exist = baskDAO.checkSess(bask);                        //sprawdzanie czy produkt jest już w koszyku
            if(exist==true){                                                //jeśli istnieje
                boolean q =  baskDAO.updateBaskSess(bask);                  //dodanie do istniejącego
                if(q){ acc = true; }                                        //jeśli się udało -> ustawianie znacznika akceptacji
                else{ sess.setAttribute("err_disc","Błąd polecenia."); }    //w przeciwnym wypadku powiadom o błędzie
            }
            else{                                                           //w przeciwnym wypadku
                boolean q = baskDAO.create(bask);                           //tworzy nowy rekord w bazie danych
                if(q){ acc = true; }                                        //jeśli się udało -> ustawianie znacznik akceptacji
                else{ sess.setAttribute("err_disc","Błąd polecenia."); }    //w przeciwnym wypadku powiadom o błędzie
            }                       
        }
        sess.setAttribute("acc", acc);                                      //dodanie znacznika akceptacji  </editor-fold>
        
     //przekierowanie do poprzedniej strony                                <editor-fold defaultstate="collapsed" desc="przekierowanie do poprzedniej strony">   
        String url = request.getHeader("referer");                           //pobierz wcześniejszą ścieżkę
        response.sendRedirect(url);                                         //przekierowanie do wcześniejszej strony </editor-fold>
    }                                                                       
    
   //wyświetlanie koszyka
    private void viewBasket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
        
      //pobieranie zmiennej sesyjnej                                        <editor-fold defaultstate="collapsed" desc="pobieranie zmiennej sesyjnej">
        String session = "";                                                //ustawienie zmiennej
        Cookie[] cookies = request.getCookies();                            //pobranie ciasteczek
        for (Cookie cookie : cookies) {                                     //filtrowanie ciasteczek
            if (cookie.getName().equals("session")) {                       //jeśli nazwa ciasteczka to session 
                session = cookie.getValue();                                //wyciąganie aktualnej sesji
                break;                                                      //po znalezieniu przerwij poszukiwania
            }
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) { }                                  //sprawdzanie błędów   </editor-fold>
       
      //wczytanie koszyka dla użytkownika zalogowanego/seyjnego/przepisz koszyk                                     <editor-fold defaultstate="collapsed" desc="wczytanie koszyka dla użytkownika zalogowanego/esyjnego/przepisz koszyk ">
        List<Basket> listBask = baskDAO.readSess(session);                                                          //czytanie koszyka dla pobranej sesji
        if(id_usr!=0){                                                                                              //jeśli użytkownik się zalogował/jest zalogowany
        //jest już w navie użytkownika zalogowanego
            if(!session.equals("")){                                                                                //jeśli zmienna sesyjna nie jest pusta
                for (Basket item : listBask) {                                                                      //dla każdego przedmoitu w koszyku                                                                                   //w przeciwnym wypadku (produkt nie istnieje w koszyku użytkownika)
                    boolean q = baskDAO.updateSessToUsr(item, id_usr);                                              //przypisz przedmiot do użytkownika zalogowanego
                    if(q){ sess.setAttribute("succ_disc","Dodano koszyk do aktualnego uzytkownika."); }             //jeśli się udało
                    else{ sess.setAttribute("err_disc","Błąd dodania do uzytkownika aktualnego koszyka.");}         //w przeciwnym wypadku wyświetlanie komunikatu o błędzie
                }
                listBask = baskDAO.readUsr(id_usr);                                                                 //wyświetl koszyk użytkownika
            }
            else{ listBask = baskDAO.readUsr(id_usr);}                                                              //w przeciwnym przypadku wczytaj koszyk użytkownika (jeśłi sesja jest pusta i użytkownik jest zalogowany)
        }
        request.setAttribute("listBask", listBask);                                                                 //ustaw zmienną dla pliku JSP </editor-fold>
        
      //wczytanie listy dostaw i płatności                                  <editor-fold defaultstate="collapsed" desc="wczytanie listy dostaw i płatności ">
        List<Delivery> listDel = delDAO.read();                             //wczytaj listę możliwych dostaw 
        request.setAttribute("listDel", listDel);                           //ustaw zmienną dla pliku JSP
        List<Payment> listPay = payDAO.read();                              //wczytaj listę możliwych płatności
        request.setAttribute("listPay", listPay);                           //ustaw zmienną dla pliku JSP </editor-fold>
      //pobieranie z sesji kodu promocyjnego                                <editor-fold defaultstate="collapsed" desc="pobieranie z sesji kodu promocyjnego">  
        int id_disc = 0;                                                    //ustawienie zmiennej
        try{ id_disc = (int) sess.getAttribute("discount_id");              //pobranie id kodu promocyjnego z zmiennej sesji
        } catch (NullPointerException e) {}                                 //sprawdzanie błędów   </editor-fold>
      //wczytywanie wartości i nazwy promocji                                <editor-fold defaultstate="collapsed" desc="wczytywanie wartości i nazwy promocji">
        String name = null;                                                 //ustawienie zmiennej nazwy
        int value = 0;                                                      //ustawienie zmiennej wartości
        if(id_disc!=0){                                                     //jeśli id kodu promocyjnego został zaciągnięty
            Discount disc = discDAO.read(id_disc);                          //wczytaj wartość i nazwę
            try {
                name = disc.getName();                                      //przypisz nazwę
                value = disc.getValue();                                    //przypisz wartość
            } catch (NullPointerException e) {}                             //sprawdzanie błędów
        }
        request.setAttribute("disc_name", name);                            //ustaw zmienną dla pliku JSP
        request.setAttribute("disc_value", value);                          //ustaw zmienną dla pliku JSP </editor-fold>
        
      //wczytywanie listy produktów pasujących do koszyka                                       <editor-fold defaultstate="collapsed" desc="wczytywanie listy produktów pasujących do koszyka">
        List<ProductMeta> listProd = new ArrayList<>();                                         //tworzenie listy przeznaczonej na produkty
        for (Basket item : listBask) {                                                          //dla każdego elementu w koszyku
            int quant = ordpDAO.quantityProd(item.getIdProd());                                 //sprawdź ilość w zamówieniach niezaakceptowanych
            ProductMeta prodm = prodmDAO.readProdBask(item.getIdProd());                        //wczytaj produkt z koszyka przez idProduktu
            if((prodm.getQuantityState()-quant-item.getQuantity())>=0){ listProd.add(prodm);}   //jeśli ilość na stanie - ilość w zamówieniach niezaakceptowanych - ilość, którą wybraliśmy jest większa/równa 0 przypisz produkt do listy
            else{                                                                               //w przeciwnym wypadku (jeśli jest mniejsze od zera)
                if(id_usr!=0){ baskDAO.deleteUsrId(id_usr, item.getIdProd());}                  //jeśli użytkownik jest zalogowany usuń produkt z jego koszyka
                else{ baskDAO.deleteSessId(session, item.getIdProd());}                         //w przeciwnym wypadku (jeśli jest mniejsze od zera i użytkownik nie jest zalogowany) usuń z koszyka sesji
                sess.setAttribute("err_disc","Musieliśmy usunąć produkt: "+
                        prodm.getName()+". Nie jest już dostępny.");                            //wyświetl komunikat
            }
        }
        request.setAttribute("listProd", listProd);                                             //ustaw zmienną dla pliku JSP </editor-fold>
        
      //przekierowanie do poprzedniej strony                                        <editor-fold defaultstate="collapsed" desc="przekierowanie do poprzedniej strony">   
       RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/basket/Basket.jsp");   //przekierowanie do pliku JSP
        dispatcher.forward(request, response);                                      //</editor-fold>
    } 
    
   //usuń jeden produkt z koszyka
    private void deleteBasket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id = 0;                                                         //deklaracja zmiennej
        try{ id = Integer.parseInt(request.getParameter("id")); }           //pobieranie id produktu
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //pobieranie zmiennej sesyjnej                                        <editor-fold defaultstate="collapsed" desc="pobieranie zmiennej sesyjnej">
        String session = "";                                                //ustawienie zmiennej
        Cookie[] cookies = request.getCookies();                            //pobranie ciasteczek
        for (Cookie cookie : cookies) {                                     //filtrowanie ciasteczek
            if (cookie.getName().equals("session")) {                       //jeśli nazwa ciasteczka to session 
                session = cookie.getValue();                                //wyciąganie aktualnej sesji
                break;                                                      //po znalezieniu przerwij poszukiwania
            }
        }                                                                   //</editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                      //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }          //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {}                                    //sprawdzanie błędów </editor-fold>
        
      //usuwanie produktu                                                               <editor-fold defaultstate="collapsed" desc="usuwanie produktu">
        if(id_usr!=0){                                                                  //jeśli użytkownik jest zalogowany
            boolean q = baskDAO.deleteUsrId(id_usr, id);                                //usunięcie produkt z koszyka użytkownika (przypisanie do zmiennej = sprawdzenie czy usunięto)
            if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto przedmiot.");}     //jeśli się udało powiadom o poprawnym usunięciu
            else{ sess.setAttribute("err_disc","Błąd polecenia.");}                     //w przeciwnym wypadku powiadom o błędzie
        }
        else{ boolean q = baskDAO.deleteSessId(session, id);                            //usunięcie produkt z koszyka sesyjnego (przypisanie do zmiennej = sprawdzenie czy usunięto)
            if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto przedmiot.");}     //jeśli się udało powiadom o poprawnym usunięciu
            else{ sess.setAttribute("err_disc","Błąd polecenia.");}                     //w przeciwnym wypadku powiadom o błędzie 
        }                                                                               //</editor-fold>
        
        response.sendRedirect("basket");                                    //przekierowanie do koszyka
    } 
    
   //opróżnianie koszyka 
    private void deleteAllBasket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
        
      //pobieranie zmiennej sesyjnej                                        <editor-fold defaultstate="collapsed" desc="pobieranie zmiennej sesyjnej">
        String session = "";                                                //ustawienie zmiennej
        Cookie[] cookies = request.getCookies();                            //pobranie ciasteczek
        for (Cookie cookie : cookies) {                                     //filtrowanie ciasteczek
            if (cookie.getName().equals("session")) {                       //jeśli nazwa ciasteczka to session 
                session = cookie.getValue();                                //wyciąganie aktualnej sesji
                break;                                                      //po znalezieniu przerwij poszukiwania
            }
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik jest zalogowany                           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                      //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }          //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {}                                    //sprawdzanie błędów </editor-fold>
        
      //opróżnienie koszyka                                                             <editor-fold defaultstate="collapsed" desc="opróżnienie koszyka">
        if(id_usr!=0){                                                                  //jeśli użytkownik jest zalogowany
            boolean q = baskDAO.delete(id_usr);                                         //opróżnienie koszyka użytkownika zalogowanego
            if(q){ sess.setAttribute("succ_disc","Pomyślnie opróżniono koszyk.");}      //jeśli się udało powiadom o poprawnym usunięciu
            else{ sess.setAttribute("err_disc","Błąd polecenia.");}                     //w przeciwnym wypadku powiadom o błędzie
        }
        else{                                                                           //jeśli nie jest to użytkownik zaologowany
            boolean q = baskDAO.delete(session);                                        //opróżnienie koszyka sesyjnego
            if(q){ sess.setAttribute("succ_disc","Pomyślnie opróżniono koszyk.");}      //jeśli się udało powiadom o poprawnym usunięciu
            else{ sess.setAttribute("err_disc","Błąd polecenia.");}                     //w przeciwnym wypadku powiadom o błędzie
        }                                                                               //</editor-fold>
        
        response.sendRedirect("basket");                                    //przekierowanie do koszyka
    } 
    
   //aktualizacja koszyka po sprawdzeniu ilości produktów
    private void updateBasket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id = 0, qt = 0;                                                 //deklaracja zmiennych
        try{ id = Integer.parseInt(request.getParameter("id")); }           //pobieranie id produktu
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }
        try{ qt = Integer.parseInt(request.getParameter("nbr")); }          //pobieranie ilości
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //pobieranie zmiennej sesyjnej                                        <editor-fold defaultstate="collapsed" desc="pobieranie zmiennej sesyjnej">
        String session = "";                                                //ustawienie zmiennej
        Cookie[] cookies = request.getCookies();                            //pobranie ciasteczek
        for (Cookie cookie : cookies) {                                     //filtrowanie ciasteczek
            if (cookie.getName().equals("session")) {                       //jeśli nazwa ciasteczka to session 
                session = cookie.getValue();                                //wyciąganie aktualnej sesji
                break;                                                      //po znalezieniu przerwij poszukiwania
            }
        }                                                                   //</editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                                  <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                             //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }                 //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {}                                           //sprawdzanie błędów </editor-fold>
        
      //sprawdzenie ilości na stanie + aktualizacja koszyka                         <editor-fold defaultstate="collapsed" desc="sprawdzenie ilości na stanie + aktualizacja koszyka">
        int quant = ordpDAO.quantityProd(id);                                       //wczytanie ilości w zamówieniach niezaakceptowanych
        int state = prodmDAO.readState(id);                                         //wczytanie ilości na stanie
        if((state-quant-qt)>=0){                                                    //jeśli stan-ilość w zamówieniach-ilość wybrana jest większa/równa 0 
           Basket bask = new Basket(session, id_usr, id, qt, null);                 //wczytaj koszyk
            if(id_usr!=0){                                                          //jeśli jest to użytkownik zalogowany
                boolean q = baskDAO.updateUsr(bask);                                //zaktualizowanie koszyka dla uzytkownika zalogowanego
                if(q){ sess.setAttribute("succ_disc","Zaktualizowano stan.");}      //jeśli się udało powiadom o poprawnym usunięciu
                else{ sess.setAttribute("err_disc","Błąd polecenia.");}             //w przeciwnym wypadku powiadom o błędzie 
            }
            else{                                                                   //w przeciwnym wypadku
                boolean q = baskDAO.update(bask);                                   //zaktualizowanie koszyka dla sesji
                if(q){ sess.setAttribute("succ_disc","Zaktualizowano stan.");}      //jeśli się udało powiadom o poprawnym usunięciu
                else{ sess.setAttribute("err_disc","Błąd polecenia.");}             //w przeciwnym wypadku powiadom o błędzie 
            }
        }
        else{ sess.setAttribute("err_disc","Brak podanej liczby na stanie.");}      //jeśli równanie nie jest prawdziwe </editor-fold>
        
        response.sendRedirect("basket");                                    //przekierowanie do koszyka
    } 
    
   //sprawdzanie czy kod promocyjny istnieje / jest aktywny
    private void checkDisc(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        String name = "";                                                   //deklaracja zmiennej
        try{ name = Jsoup.parse(request.getParameter("name")).text(); }     //pobieranie id produktu
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //akceptacja/odrzycenie kodu promocyjnego                                         <editor-fold defaultstate="collapsed" desc="akceptacja/odrzycenie kodu promocyjnego">
        Discount disc = discDAO.read(name);                                             //wczytanie kodu promocyjnego po nazwie
        try {
            if((disc.isActive())){                                                      //jeśli jest aktywny
               sess.setAttribute("discount_id",disc.getId());                           //ustawianie zmiennej sesyjnej
               sess.setAttribute("succ_disc","Dodano kod promocyjny."); //wyświetlanie komunikatu
            }
            else{                                                                       //w przecienym wypadku (nieaktywny)
                sess.setAttribute("discount_id",0);                                     //ustawianie zmiennej sesyjnej na 0 (brak kodu)
                sess.setAttribute("err_disc","Brak podanego kodu lub kod nieaktywny."); //wyświetlanie komunikatu
            }  
        } catch (NullPointerException e) {                                              //sprawdzanie błędów
            sess.setAttribute("discount_id",0);                                         //ustawianie zmiennej sesyjnej na 0
            sess.setAttribute("err_disc","Brak podanego kodu lub kod nieaktywny.");     //wyświetlanie komunikatu 
        }                                                                               //</editor-fold>
       
        response.sendRedirect("basket");                                    //przekierowanie do koszyka
    } 
    
   //pokaż następny krok + przekaż parametry     
    private void nextBasket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
     
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id_disc = 0, id_pay = 12, id_del = 11;                          //deklaracja zmiennych
        try{ 
            id_pay = Integer.parseInt(request.getParameter("radio1"));      //pobieranie id produktu
            id_del = Integer.parseInt(request.getParameter("radio2"));      //pobieranie id produktu
        }
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }
        try{ id_disc = (int) sess.getAttribute("discount_id"); }            //sprawdzanie czy id kodu promocyjnego zostało wpisane
        catch (NumberFormatException | NullPointerException ex){ }          //sprawdzanie błędów </editor-fold>

        sess.setAttribute("payment_id", id_pay);                            //ustawienie id płatności w sesji
        sess.setAttribute("delivery_id", id_del);                           //ustawienie id dostaway w sesji
        sess.setAttribute("discount_id", id_disc);                          //ustawienie id płatności w sesji

      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {}                                   //sprawdzanie błędów </editor-fold>  

      //przekierowania                                                                      <editor-fold defaultstate="collapsed" desc="przekierowania">
        if(id_usr!=0){ response.sendRedirect("summaryBask");}                               //jeśli użytkownik jest zalogowany przekieworanie nastąpi do podsumowania
        else{ response.sendRedirect("basket1"); }                                           //w przeciwnym wypadku przekieruj do następnego kroku </editor-fold> 
  } 
    //pokaż krok 2
    private void basketStep2(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
        
        //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id_disc = 0, deliv = 11, paym = 12;                             //do sprawdzania czy wcześniejsze kroki zostały zaakceptowane
        try{ 
            id_disc = (int) sess.getAttribute("discount_id");               //sprawdzanie czy id kodu promocyjnego zostało wpisane
            deliv = (int) sess.getAttribute("delivery_id");                 //sprawdzanie czy id dostawy zostało wpisane
            paym = (int) sess.getAttribute("payment_id");                   //sprawdzanie czy id płatności zostało wpisane
        }
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
         
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/basket/Basket1.jsp");     //przekieruj do następnego kroku
        dispatcher.forward(request, response);                                          //prześlij atrybuty
  } 

   //pokaż formularz u żytkownika niezalogowanego    
    private void BasketNL(HttpServletRequest request, HttpServletResponse response)
             throws SQLException, ServletException, IOException {

            sess=request.getSession(true);                                      //pobranie sesji
            
          //sprawdzanie czy użytkownik jest zalogowany i zabronienie dostępu    <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany i zabronienie dostępu">
            int id_usr = 0;                                                     //ustawienie zmiennej
            try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
            catch (NullPointerException e) {}                                   //sprawdzanie błędów
            if(id_usr!=0) {                                                     //jeśli jest zalogowany nie powinien mieć tu wstępu
                sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                  //przekierowanie do strony głównej
                return;                                                         //przerwanie działania funkcji  
            }                                                                   //</editor-fold> 

          //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
            int id_disc = 0, deliv = 11, paym = 12;                             //do sprawdzania czy wcześniejsze kroki zostały zaakceptowane
            try{ 
                id_disc = (int) sess.getAttribute("discount_id");               //sprawdzanie czy id kodu promocyjnego zostało wpisane
                deliv = (int) sess.getAttribute("delivery_id");                 //sprawdzanie czy id dostawy zostało wpisane
                paym = (int) sess.getAttribute("payment_id");                   //sprawdzanie czy id płatności zostało wpisane
            }
                catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
           
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/basket/BasketNL.jsp");     //przekieruj do następnego kroku
        dispatcher.forward(request, response);                                          //</editor-fold> 
    }  
    
   //dodawanie/uaktualnianie adresu i firmy
    private void insertUMBasket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
        
      //sprawdzanie czy użytkownik jest zalogowany i zabronienie dostępu    <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany i zabronienie dostępu">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {}                                   //sprawdzanie błędów
        if(id_usr!=0) {                                                     //jeśli jest zalogowany nie powinien mieć tu wstępu
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji  
        }                                                                   //</editor-fold> 
     
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane                   <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        String name, surname,mail,tel;                                              //deklaracja zmiennych osobowych
        String firm,fname,fnip,fmail,ftel; boolean firm_m;                          //deklaracja zmiennych firmy
        String str,anr,acode,apost,town,astate,acount;                              //deklaracja zmiennych adresu
        int id_disc = 0, deliv = 11, paym = 12;                                     //do sprawdzania czy wcześniejsze kroki zostały zaakceptowane
        try{ 
            id_disc = (int) sess.getAttribute("discount_id");               //sprawdzanie czy id kodu promocyjnego zostało wpisane
            deliv = (int) sess.getAttribute("delivery_id");                 //sprawdzanie czy id dostawy zostało wpisane
            paym = (int) sess.getAttribute("payment_id");                   //sprawdzanie czy id płatności zostało wpisane
        }
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }
        try{                                                                        //pobieranie danych
          //zmienne osobowe  
            name = Jsoup.parse(request.getParameter("name")).text();                //imię
            surname = Jsoup.parse(request.getParameter("surname")).text();          //nazwisko
            mail = Jsoup.parse(request.getParameter("mail")).text();                //adres e-mail
            tel = request.getParameter("tel");                                      //telefon
          //zmienne firmy  
            firm = request.getParameter("firmCh");                                  //znacznik (czy jest to firma) 
            fname = null; fnip = null; fmail = null; ftel = null; firm_m = false;   //deklaracja zmiennych
            if (null != firm){                                                      //jeśli jest to firma
                firm_m = true;                                                      //ustaw znacznik
                fname = Jsoup.parse(request.getParameter("name_firm")).text();      //nazwa firmy
                fnip = Jsoup.parse(request.getParameter("nip")).text();             //NIP
                fmail = Jsoup.parse(request.getParameter("mail_firm")).text();      //e-mail firmy
                ftel = Jsoup.parse(request.getParameter("tel_firm")).text();        //telefon firmy
            }
          //zmienne adresu  
            str = Jsoup.parse(request.getParameter("street")).text();               //ulica
            anr = request.getParameter("nbr");                                      //numer domu/nieszkania/bloku...
            acode = request.getParameter("code");                                   //kod pocztowy
            apost = Jsoup.parse(request.getParameter("post")).text();               //poczta
            town = Jsoup.parse(request.getParameter("town")).text();                //miejscowość
            astate = Jsoup.parse(request.getParameter("state")).text();             //województwo
            acount = Jsoup.parse(request.getParameter("country")).text();           //kraj
        }
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik dodał już adres                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik dodał już adres"> 
        int id_usrMeta = 0;                                                 //ustawienie id adresu (domyślnie 0) 
        try { id_usrMeta = (int) sess.getAttribute("userMeta_id"); }        //sprawdzenie czy w sesji znajduje się id adresu
        catch (NullPointerException e) {}                                   //sprawdzanie błędów </editor-fold>
        
        UserMeta userm = new UserMeta(id_usrMeta, NULL, false, firm_m, fname,
                fnip,fmail,ftel,str,anr,town,astate,acode,apost,acount);        //tworzenie adresu w oparciu o przesłane dane
        
      //dodawanie/uaktualnianie adresu i firmy                                  <editor-fold defaultstate="collapsed" desc="dodawanie/uaktualnianie adresu i firmy">  
        if(id_usrMeta!=0){                                                      //jeśli adres użytkownika jest już dodany
            boolean q = usermDAO.update(userm);                                 //uaktualnij go o wpisane dane 
            if(q){ sess.setAttribute("succ_disc","Dane zostały zapisane.");}    //jeśli się udało powiadom o poprawnym usunięciu
            else{ sess.setAttribute("err_disc","Błąd polecenia.");}             //w przeciwnym wypadku powiadom o błędzie 
        }
        else{                                                                   //w przeciwnym wypadku
            int id = usermDAO.read(userm);                                      //sprawdź czy adres znajduje się w bazie i wypisz jego id
            if(id==NULL){                                                       //jeśli nie znaleziono
                if(firm_m){ 
                    boolean q = usermDAO.createFirmN(userm);                            //stwórz adres dla firmy
                    if(q){ sess.setAttribute("succ_disc","Dane zostały zapisane.");}    //jeśli się udało powiadom o poprawnym usunięciu
                    else{ sess.setAttribute("err_disc","Błąd polecenia.");}             //w przeciwnym wypadku powiadom o błędzie
                }
                else{
                    boolean q = usermDAO.createAdrN(userm);                             //stwórz adres
                    if(q){ sess.setAttribute("succ_disc","Dane zostały zapisane.");}    //jeśli się udało powiadom o poprawnym usunięciu
                    else{ sess.setAttribute("err_disc","Błąd polecenia.");}             //w przeciwnym wypadku powiadom o błędzie
                }
                id = usermDAO.read(userm);                                      //pobierz jego id
            }
            sess.setAttribute("userMeta_id",id);                                //zapisz adres w sesji     
        }                                                   
        sess.setAttribute("name",name);                                     //zapisz imię w sesji  
        sess.setAttribute("surname",surname);                               //zapisz nazwisko w sesji  
        sess.setAttribute("mail",mail);                                     //zapisz adres e-mail w sesji  
        sess.setAttribute("tel",tel);                                       //zapisz telefon w sesji </editor-fold>
        
        response.sendRedirect("summaryBask");                                    //przekierowanie do podsumowania
    } 
        
   //podsumowanie zamówienia  
    private void sumBasket(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
     
        sess=request.getSession(true);                                      //pobranie sesji
        
      //pobieranie zmiennej sesyjnej                                        <editor-fold defaultstate="collapsed" desc="pobieranie zmiennej sesyjnej">
        String session = "";                                                //ustawienie zmiennej
        Cookie[] cookies = request.getCookies();                            //pobranie ciasteczek
        for (Cookie cookie : cookies) {                                     //filtrowanie ciasteczek
            if (cookie.getName().equals("session")) {                       //jeśli nazwa ciasteczka to session 
                session = cookie.getValue();                                //wyciąganie aktualnej sesji
                break;                                                      //po znalezieniu przerwij poszukiwania
            }
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id = 0, id_disc = 0, deliv = 11, paym = 12;                     //deklaracja zmiennych
        try { 
            id = (int) sess.getAttribute("userMeta_id");                    //pobieranie id adresu
            id_disc = (int) sess.getAttribute("discount_id");               //pobieranie id kodu promocyjnego
            deliv = (int) sess.getAttribute("delivery_id");                 //pobieranie id dostawy
            paym = (int) sess.getAttribute("payment_id");                   //pobieranie id płatności
        }
        catch (NullPointerException e) {                                    //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie kodu promocyjnego                                       <editor-fold defaultstate="collapsed" desc="sprawdzanie kodu promocyjnego">
        int value = 0;                                                      //ustawienie zmiennej
        if(id_disc!=0){                                                     //jeśli id kodu nie równa się 0
            Discount disc = discDAO.read(id_disc);                          //wczytaj dane kodu
            try {
                value = disc.getValue();                                    //ustawienie wartości kodu
            } catch (NullPointerException e) {}                             //sprawdzanie błędów
        }
        request.setAttribute("disc_value", value);                          //ustawienie zmiennej wartości kodu dla pliku JSP </editor-fold> 
       
      //wyświetlanie adresu i firmy                                         <editor-fold defaultstate="collapsed" desc="wyświetlanie adresu i firmy">
        try{
            UserMeta usrmF= usermDAO.checkFirm(id);                         //sprawdzenie czy jest to firma
            UserMeta usrmL= usermDAO.checkLogged(id);                       //sprawdzenie czy jest to użytkownik zalogowany
            UserMeta listUser = null;                                       //ustawienie zmiennej dla adresu
            if(usrmL.isLogged()){                                           //jeśli jest to użytkownik zalogowany
                if(usrmF.isFirm()){ listUser = usermDAO.readFirmL(id);}     //jeśli jest to firma wpisanie adresu użytkownika zalogowanego z firmą
                else{ listUser = usermDAO.readAdrL(id); }                   //w przeciwnym wypadku wpisanie adresu użytkownika zalogowanego bez firmy
            }
            else{                                                           //w przeciwnym wypadku (nie jest to uzytkownik zalogowany)
                if(usrmF.isFirm()){ listUser = usermDAO.readFirmN(id); }    //jeśli jest to firma wpisanie adresu sesji
                else{ listUser = usermDAO.readAdrN(id); }                   //w przeciwnym wypadku wpisanie adresu sesji
            }  
          request.setAttribute("userm", listUser);                          //ustawienie zmiennej dla pliku JSP
        }
        catch(NumberFormatException | NullPointerException e){ }            //sprawdzanie błędów </editor-fold>
      
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_logg = 0;                                                    //ustawienie zmiennej
        try { id_logg = (int) sess.getAttribute("id_user_logged"); }        //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {}                                   //sprawdzanie błędów </editor-fold>  
        
      //pobieranie zawartości koszyka                                       <editor-fold defaultstate="collapsed" desc="pobieranie zawartości koszyka">
        List<Basket> listBask = new ArrayList<>();                          //usatwianie listy koszyka
        if(id_logg!=0){ listBask = baskDAO.readUsr(id_logg); }              //jeśli użytkownik jest zalogowany pobieranie jego koszyk
        else{ listBask = baskDAO.readSess(session); }                       //w przeciwnym wupadku pobieranie koszyka sesji
        request.setAttribute("listBask", listBask);                         //ustawienie zmiennej dla pliku JSP
        List<ProductMeta> listProd = new ArrayList<>();                     //ustawienie listy produktów
        for (Basket item : listBask) {                                      //dla każdego elementu
            ProductMeta prodm = prodmDAO.readProdBask(item.getIdProd());    //pobieranie produktu pasującego do koszyka
            listProd.add(prodm);                                            //dodanie produktu do listy
        }
        request.setAttribute("listProd", listProd);                         //ustawienie zmiennej dla pliku JSP
        Delivery del = delDAO.read(deliv);                                  //pobieranie dostawy
        request.setAttribute("delivery", del);                              //ustawienie zmiennej dla pliku JSP
        Payment pay = payDAO.read(paym);                                    //pobieranie płatności
        request.setAttribute("payment", pay);                               //ustawienie zmiennej dla pliku JSP </editor-fold>
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/basket/Summary.jsp");     //przekierowanie do podsumowania
        dispatcher.forward(request, response);                                          //przekazanie zmiennych 
    } 
    
   //zmiana adresu (opcja dla użytkownika zalogowanego) 
    private void updateBaskAddrr(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                      //pobranie sesji
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id = 0;                                                         //ustawienie zmiennej
        try{ 
            id = Integer.parseInt(request.getParameter("choice"));          //pobranie id adresu użytkownika
        }
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        sess.setAttribute("userMeta_id",id);                                //ustawienie zmiennej sesyjnej
        response.sendRedirect("summaryBask");                               //przekierowanie do podsumowania
    } 
    
   //dodawanie zamówienia do bazy danych
    private void addOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
      
        sess=request.getSession(true);                                                      //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = null;    //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 1; History his = null; int action = 8;                                 //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
            
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane                           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        String reg;                                                                         //ustawienie zmiennej
        try{ reg = request.getParameter("regul"); }                                         //podranie zmiennej regulaminu
        catch (NumberFormatException | NullPointerException ex){                            //sprawdzanie błędów
            sess.setAttribute("err_disc","Brak dostępu!");                                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                                  //przekierowanie do strony głównej
            return;                                                                         //przerwanie działania funkcji
        }                                                                                   //</editor-fold>  
        
      //po zaakceptowaniu regulaminu  (warunek w razie usunięcia przez użytkownika walidacji)
        if(!reg.equals("")){
          //pobieranie zmiennej sesyjnej                                                    <editor-fold defaultstate="collapsed" desc="pobieranie zmiennej sesyjnej">
            String session = null;                                                          //ustawienie zmiennej
            Cookie[] cookies = request.getCookies();                                        //pobranie ciasteczek
            for (Cookie cookie : cookies) {                                                 //filtrowanie ciasteczek
                if (cookie.getName().equals("session")) {                                   //jeśli nazwa ciasteczka to session 
                    session = cookie.getValue();                                            //wyciąganie aktualnej sesji
                    break;                                                                  //po znalezieniu przerwij poszukiwania
                }
            }                                                                               //</editor-fold> 
          //sprawdzenie czy użytownik jest zalogowany                                       <editor-fold defaultstate="collapsed" desc="sprawdzenie czy użytownik jest zalogowany">  
            try { id_usr = (int) sess.getAttribute("id_user_logged"); }                     //pobranie zmiennej sesyjnej z id użytkownika
            catch (NullPointerException e) { }                                              //sprawdzanie błędów </editor-fold> 
          //pobieranie zmiennych sesyjnych                                                  <editor-fold defaultstate="collapsed" desc="pobieranie zmiennych sesyjnych ">  
            int id = NULL, id_disc = NULL, deliv = NULL, paym = NULL;                       //ustawienie zmiennych liczbowych
            String name = null, surname= null, tel = null, mail = null, comm = null;        //ustawienie ciągów znakowych
            try {  
                id = (int) sess.getAttribute("userMeta_id");                                //id adresu
                id_disc = (int) sess.getAttribute("discount_id");                           //id kodu promocyjnego                  
                deliv = (int) sess.getAttribute("delivery_id");                             //id dostawy
                paym = (int) sess.getAttribute("payment_id");                               //id płatności
                name = (String) sess.getAttribute("name");                                  //imię
                surname = (String) sess.getAttribute("surname");                            //nazwisko
                tel = (String) sess.getAttribute("tel");                                    //telefon
                mail = (String) sess.getAttribute("mail");                                  //e-mail
                comm = Jsoup.parse(request.getParameter("comm")).text();                    //komentarz
                if(comm.equals("")) comm = null;                                            //komentarz
            } catch (NullPointerException e) {}                                             //sprawdzanie błędów </editor-fold> 
          //sumowanie produktów                                                             <editor-fold defaultstate="collapsed" desc="sumowanie produktów">  
            Double sum = 0.0;                                                               //ustawianie zmiennej sumy
            List<Basket> listBask = baskDAO.readSess(session);                              //wczytywanie koszyka sesji do listy
            if(id_usr!=1){ listBask = baskDAO.readUsr(id_usr);}                             //jeśli użytkownik jest zalogowany następuje nadpisanie koszyka 
            for (Basket item : listBask) {                                                  //dla każdego elementu koszyka
                ProductMeta prodm = prodmDAO.readProdBask(item.getIdProd());                //przypisanie pasującego produktu
                sum += (prodm.getPrice()-((prodm.getDiscount()/100.0)*prodm.getPrice()))*item.getQuantity();  //obliczenie sumy
            }                                                                               //</editor-fold> 
          //sprawdzenie kodu promocyjnego                                                   <editor-fold defaultstate="collapsed" desc="sprawdzenie kodu promocyjnego">  
            int value = 0;                                                                  //ustawienie zmiennej
            if(id_disc!=NULL){                                                              //jeśli id kodu nie jest puste
                Discount disc = discDAO.read(id_disc);                                      //odczytanie kodu promocyjnego
                try { value = disc.getValue(); }                                            //przypisanie wartości kodu
                catch (NullPointerException e) {}                                           //sprawdzenie błędów
            }
            sum -= (value/100.0)*sum;                                                       //dodanie kodu do sumy ostatecznej zamówienia </editor-fold> 
          //tworzenie zmiennej zamówienia                                                   <editor-fold defaultstate="collapsed" desc="tworzenie zmiennej zamówienia">
            String create = formatLocalDate.format(LocalDateTime.now());                    //ustawienie czasu serwera
            Order Ord = new Order(0,paym,id,deliv,null,1,id_disc,name,
                    surname,mail,tel,23,sum,comm,create,null,null);                         //utworzenie zmiennej zamówienia </editor-fold>
          //dodawanie zamówienia do bazy danych                                              <editor-fold defaultstate="collapsed" desc="dodawanie zamówienia do bazy danych"> 
            if(id_disc!=0){                                                                 //jeśli kod promocyjny jest dodany (ze względu na NULL w kluczu obcym)
                boolean q = ordDAO.create(Ord);                                             //tworzenie zamówienia z kodem promocyjnym
                if(q){ sess.setAttribute("succ_disc","Zamówienie zostało dodane.");}        //jeśli się udało wyświetl odpowiedni komunikat
                else{                                                                       //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Błąd dodania zamówienia.");               //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                              //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                                 //zatrzymanie działania funkcji
                } 
            }
            else{                                                                           //w przeciwnym wypadku
                boolean q = ordDAO.createOrd(Ord);                                          //tworzenie zamówienia bez kodu promocyjnego
                if(q){ sess.setAttribute("succ_disc","Zamówienie zostało dodane.");}        //jeśli się udało wyświetl odpowiedni komunikat
                else{                                                                       //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Błąd dodania zamówienia.");               //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                              //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                                 //zatrzymanie działania funkcji
                }
            }                                                                               //</editor-fold>
            int order = ordDAO.readId(Ord);                                                   //wczytanie id zamówienia   
          //ustawianie historii modyfikacji                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            if(id_usr!=1){                                                                  //jeśli użytkownik zalogowany (jest to tylko wiadomość informacyjna dla uytkowników z rangą)
                beforeQuery = "Opcja niedostępna"; afterQuery = "Opcja niedostępna";        //ze wgledu na brak mozliwości cofnięcia tej opcji widnieje taki komunikat
                description = "Dodanie nowego zamówienia"; modify="id zamówienia: "+order;  //ustawienie opisu i tekstu modyfikacji
                his = new History(NULL, id_usr, action, description, now, 
                        beforeQuery, afterQuery, modify);                                   //ustawienie modelu historii
                histDAO.create(his);                                                        //dodanie historii do bazy danych
            }                                                                               //</editor-fold>
          //przeniesienie zawartości koszyka do tabeli zamówienie-produkt                           <editor-fold defaultstate="collapsed" desc="przeniesienie zawartości koszyka do tabeli zamówienie-produkt ">
            int discoun = 0;                                                                        //ustawienie wartości promocji (ponieważ może się zmienić)
            for (Basket item : listBask) {                                                          //dla każdego elementu koszyka
                discoun = prodmDAO.readDiscount(item.getIdProd());                                  //wczytanie zniżki produktu
                OrderProd op = new OrderProd(order, item.getIdProd(), 
                        item.getQuantity(), discoun);                                               //stworzenie modelu tabeli łącznikowej zamówienie-produkt 
                boolean q = ordpDAO.create(op);                                                     //stworzenie rekordu w tabeli łącznikowej
                if(q){ sess.setAttribute("succ_disc","Migracja tabel przebiegła pomyślnie");}       //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                else{                                                                               //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Wystąpił błąd podczas migracji koszyka");         //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                                         //zatrzymanie działania funkcji
                } 
            }                                                                                       //</editor-fold>
          //usuwanie rekordów z koszyka                                                             <editor-fold defaultstate="collapsed" desc="usuwanie rekordów z koszyka">
            if(id_usr!=1){                                                                          //jeśli użytkownik jest zalogowany
                boolean q = baskDAO.delete(id_usr);                                                 //usuń koszyk użytkownika zalogowanego
                if(q){ sess.setAttribute("succ_disc","W pełni dodano zamówienie");}                 //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                else{ sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania koszyka");}      //w przeciwnym wypadku wyświetlenie odpowiedniego komunikatu o błędzie
            }  
            else{                                                                                   //w przeciwnym wypadku (sesja)
                boolean q = baskDAO.delete(session);                                                //usuń koszyk sesji
                if(q){ sess.setAttribute("succ_disc","W pełni dodano zamówienie");}                 //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                else{ sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania koszyka");}      //w przeciwnym wypadku wyświetlenie odpowiedniego komunikatu o błędzie
            }                                                                                       //</editor-fold>
            if(id_usr!=1){ sess.removeAttribute("listOrder"); }                             //usunięcie parametru do ponownego pobrania z bazy danych dla uzytkownika zalogowanego
            sess.setAttribute("ord",order);                                                 //ustawianie zmiennej sesyjnej do odczytania zamówienia
            response.sendRedirect("order?acc=2");                                           //przekierowanie do koszyka
        }
      //jeśli użytkownik nie zaakceptował regulaminu
        else{                                                                               
            sess.setAttribute("err_disc","Musisz zaakceptować regulamin!");                 //wyświetlenie odpowiedniego komunikatu
            response.sendRedirect("summaryBask");                                           //przekierowanie do podsumowania
        }
    } 
    
    //pokazanie zamówienia przez formularz oraz po jego dodaniu do bazy danych
    private void order(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
            sess=request.getSession(true);                                                      //pobranie sesji
            
          //sprawdzanie czy akceptacja już nastąpiła                                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy akceptacja już nastąpiła">
            int acc = 0;                                                                        //ustawienie zmiennej
            try{ acc = Integer.parseInt(request.getParameter("acc")); }                         //podranie zmiennej akceptacji
            catch (NumberFormatException | NullPointerException ex){}                           //</editor-fold>  
            
            if((acc==1)||(acc==2)){
                int idOrder = 0; String name = null, sname = null, mail = null;                 //ustawienie zmiennych
                if(acc==1){
                  //sprawdzanie czy wszystkie wymagane dane zostały przesłane                   <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
                    try{ 
                        idOrder = Integer.parseInt(request.getParameter("order"));               //pobranie id zamówienia
                        name = Jsoup.parse(request.getParameter("name")).text();                 //pobranie imię
                        sname = Jsoup.parse(request.getParameter("surname")).text();             //pobranie nazwisko
                        mail = Jsoup.parse(request.getParameter("mail")).text();                 //pobranie adres e-mail
                    }
                    catch (NumberFormatException | NullPointerException ex){                     //sprawdzanie błędów
                        sess.setAttribute("err_disc","Brak wszystkich zmiennych!");              //ustawianie zmiennej sesyjnej
                        response.sendRedirect("order");                                          //przekierowanie z powrotem do strony zamówienia
                        return;                                                                  //przerwanie działania funkcji
                    }
                    request.setAttribute("name", name);                                          //ustawianie zmiennych dla JSP
                    request.setAttribute("surname", sname);                                      //ustawianie zmiennych dla JSP </editor-fold> 
                }
                if(acc==2){
                  //sprawdzanie czy jest to strona zamówienia końcowego                         <editor-fold defaultstate="collapsed" desc="sprawdzanie czy jest to strona zamówienia końcowego">
                    try{ idOrder = (int)sess.getAttribute("ord"); }                             //pobranie id zamówienia z sesji
                    catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                        sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    } 
                    //sprawdzenie czy użytownik jest zalogowany                                       <editor-fold defaultstate="collapsed" desc="sprawdzenie czy użytownik jest zalogowany">  
                    int id_usr = 0;
                    try { id_usr = (int) sess.getAttribute("id_user_logged"); }                     //pobranie zmiennej sesyjnej z id użytkownika
                    catch (NullPointerException e) { }                                              //sprawdzanie błędów </editor-fold> 
                    if(id_usr!=NULL){ response.sendRedirect("orders"); }
                    else{
                        Order ord = ordDAO.read(idOrder);                                           //pobranie zmiennych
                        name = ord.getName(); sname = ord.getSurname(); mail = ord.getEmail();      //przypisanie wartości do późniejszego sprawdzenia </editor-fold> 
                    }
                }                                                                                
              //wyświetlanie zamówienia                                                         <editor-fold defaultstate="collapsed" desc="wyświetlanie zamówienia">
                Order order = ordDAO.read(idOrder);                                             //wczytywanie zamówienia po id
                if(((order.getName().equals(name))&&(order.getSurname().equals(sname))&&
                        (order.getEmail().equals(mail)))){                                      //jeśli wszystkie wpisane pola zgadzają się z tym co jest w bazie
                    List<OrderProd> orderp =  ordpDAO.read(order.getId());                      //czytanie elementów zamówienia
                    List<ProductMeta> prodm =  new ArrayList<>();                               //stworzenie listy produktów
                    Boolean rew =  ordpDAO.isFullRev(order.getId());                            //sprawdzenie czy całe zamówienie jest recenzowane (wszystkie elementy)
                    UserMeta usrm = usermDAO.readFirmN(order.getIdUserMeta());                  //wczytanie adresu i firmy użytkownika po id
                    for (OrderProd ordp : orderp) {                                             //po każdym elemencie z zamówienia
                        prodm.add(prodmDAO.readProdOrd(ordp.getIdProd()));                      //dodawanie dane produktu do listy
                    }
                    request.setAttribute("userm", usrm);                                        //ustawienie elementu dla JSP
                    request.setAttribute("order", order);                                       //ustawienie elementu dla JSP
                    request.setAttribute("listOrderp", orderp);                                 //ustawienie elementu dla JSP
                    request.setAttribute("listProdm", prodm);                                   //ustawienie elementu dla JSP
                    request.setAttribute("reviewed", rew);                                      //ustawienie elementu dla JSP
                    request.setAttribute("acc", acc);                                           //ustawienie elementu dla JSP

                    Delivery del = delDAO.read(order.getIdDeliv());                             //pobranie dostawy
                    request.setAttribute("del", del);                                           //ustawienie elementu dla JSP
                    Payment pay = payDAO.read(order.getIdPay());                                //pobranie płatności
                    request.setAttribute("pay", pay);                                           //ustawienie elementu dla JSP
                    Discount disc = discDAO.read(order.getIdDisc());                            //pobranie kodu rabatowego
                    request.setAttribute("disc", disc);                                         //ustawienie elementu dla JSP
                    Status stat = statDAO.read(order.getIdStat());                              //pobranie statusu
                    request.setAttribute("stat", stat);                                         //ustawienie elementu dla JSP
                }
                else{ sess.setAttribute("err_disc","Nie znaleziono zamówienia!");}              //jeśli dane się nie zgadzają wyświetl odpowiedni komunikat </editor-fold>
            }
 
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/basket/Order.jsp");               //przekierownie do widoku zamówienia
        dispatcher.forward(request, response);                                                  //przekazanie parametrów
        
    } 
   
    //krótki opis servletu
    @Override
    public String getServletInfo() {
        return "Ten servlet obsługuje zamówienie oraz koszyk uzytkownika zalogowanego i sesyjnego.";
    }
}
