/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 15.11.2021 r.
 */
package jwl.jewelry;

import java.io.*;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * ControlUserRank.java
 * Ten servlet obsługuje użytkowników zalogowanych z rangą.
 *  Rangi po identyfikatorze:
 *    1 - administrator     (zarządza niestałymi tabelami słownikowymi; 
 *                              ma wgląd do historii każdego użytkownika zalogowanego; 
 *                              zarządza menu aplikacji;)
 *    2 - pracownik         (edytuje status zamówienia;   
 *                              dodaje numer przesyłki)
 *    3 - korektor          (odpowiada na recenzje użytkowników;   
 *                              edytuje opisy produktów, kategorii i tagów)
 *    4 - zaopatrzeniowiec  (zarządza produktami (dodaje, uaktualnia stan, edytuje, dodaje zdjęcia); 
 *                              zarządza tabelami słowinikowymi powiązanymi z produktem (kształt, kolor, materiał))             
 * @author DRzepka
 */

@WebServlet(name="ControlUserRank", urlPatterns={
    "/administrator", "/usersHistory", "/addUsers", "/deleteUser", "/addTag",
                      "/addCategory", "/addGroup", "/editTag", "/editCategory",
    "/worker", "/viewOrder", "/modifyOrder",
    "/corrector", "/viewCategory", "/viewTag", "/viewProduct", "/viewReview",
                  "/modifyCategory", "/modifyTag", "/modifyProduct", "/modifyReview",
    "/supplier", "/viewOneProduct", "/updateProduct", "/addProduct", "/deleteProduct",
                 "/restockProduct",       
    "/updateDictionary", "/insertDictionary", "/deleteDictionary"}, asyncSupported = true)
public class ControlUserRank extends HttpServlet {
   //zmienne                                                                    <editor-fold defaultstate="collapsed" desc="zmienne">
    private static final long serialVersionUID = 1L;
    
    private ProductDAO prodSDAO;        //podstawowy produkt dla zaopatrzeniowca
    private ProductMDAO prodmSDAO;      //informacje o produkcie dla zaopatrzeniowca
    private ProductMDAO prodmWDAO;      //informacje o produkcie dla pracownika
    private ProductMDAO prodmCDAO;      //informacje o produkcie dla korektora
    private ReviewDAO rewDAO;           //recenzja dla korektora
    private LoginDAO loginDAO;          //login dla administratora
    private UserDAO userADAO;           //informacje o użytkowniku dla administratora
    private UserDAO userWDAO;           //informacje o użytkowniku dla pracownika         
    private UserDAO userCDAO;           //informacje o użytkowniku dla korektora
    private UserDAO userSDAO;           //informacje o użytkowniku dla zaopatrzeniowca
    private UserMDAO usermDAO;          //adresy użytkowników dla pracownika
    private OrderDAO ordWDAO;           //zamówienia dla pracownika
    private OrderDAO ordCDAO;           //zamówienia dla korektora
    private OrderPDAO ordpDAO;          //tabela łącznikowa produktów i zamówień dla pracownika
    private HistoryDAO histDAO;         //historia
    private HistoryDAO histADAO;         //historia dla administratora
  //dictionary tables  
    private CatTagDAO ctADAO;           //tabela kategorii, tagów i łącząca te dwie dla a
    private CatTagDAO ctCDAO;           //tabela kategorii, tagów i łącząca te dwie dla korektora
    private CatTagDAO ctSDAO;           //tabela kategorii, tagów i łącząca te dwie dla zaopatrzeniowca
    private GroupDAO grADAO;            //tabela grup dla administratora
    private GroupDAO grSDAO;            //tabela grup dla zaopatrzeniowca
    private ColorDAO colDAO;            //wyświetlanie tabeli koloru
    private ShapeDAO shpDAO;            //wyświetlanie tabeli kształtu
    private FabricDAO fabricDAO;        //wyświetlanie tabeli materiału
    private DeliveryDAO delDAO;         //wyświetlanie tabeli dostawy
    private PaymentDAO payDAO;          //wyświetlanie tabeli płatności
    private DiscountDAO discDAO;        //wyświetlanie tabeli kodów promocyjnych
    private ColorDAO colUDAO;           //uaktualnianie tabeli koloru
    private ShapeDAO shpUDAO;           //uaktualnianie tabeli kształtu
    private FabricDAO fabricUDAO;       //uaktualnianie tabeli materiału
    private DeliveryDAO delUDAO;        //uaktualnianie tabeli dostawy
    private PaymentDAO payUDAO;         //uaktualnianie tabeli płatności
    private DiscountDAO discUDAO;       //uaktualnianie tabeli kodów promocyjnych
    
    private ColorDAO colIDAO;           //dodawanie tabeli koloru
    private ShapeDAO shpIDAO;           //dodawanie tabeli kształtu
    private FabricDAO fabricIDAO;       //dodawanie tabeli materiału
    private DeliveryDAO delIDAO;        //dodawanie tabeli dostawy
    private PaymentDAO payIDAO;         //dodawanie tabeli płatności
    private DiscountDAO discIDAO;       //dodawanie tabeli kodów promocyjnych
    
    private ColorDAO colDDAO;           //usuwanie z tabeli koloru
    private ShapeDAO shpDDAO;           //usuwanie z tabeli kształtu
    private FabricDAO fabricDDAO;       //usuwanie z tabeli materiału
    private DeliveryDAO delDDAO;        //usuwanie z tabeli dostawy
    private PaymentDAO payDDAO;         //usuwanie z tabeli płatności
    private DiscountDAO discDDAO;       //usuwanie z tabeli kodów promocyjnych
    
    private RankDAO rankDAO;            //tabela rangi
    private StatusDAO statDAO;          //tabela statusu
    private ActionDAO actDAO;           //tabela akcji
  //sesssion
    private HttpSession sess; 
  //date formatters  
    private SimpleDateFormat formatter;
    private DateTimeFormatter formatLocalDate;  //</editor-fold>
    //inicjalizacja zmiennych 
    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");                           //pobieranie url bazy
        String jdbcAdmin = getServletContext().getInitParameter("jdbcAdmin");                       //pobieranie nazwy użytkownika "administrator"
        String jdbcAdminPass = getServletContext().getInitParameter("jdbcAdminPassw");              //pobieranie hasła użytkownika "administrator"
        String jdbcWorker = getServletContext().getInitParameter("jdbcWorker");                     //pobieranie nazwy użytkownika "worker"
        String jdbcWorkerPassw = getServletContext().getInitParameter("jdbcWorkerPassw");           //pobieranie hasła użytkownika "worker"
        String jdbcCorrector = getServletContext().getInitParameter("jdbcCorrector");               //pobieranie nazwy użytkownika "corrector"
        String jdbcCorrectorPassw = getServletContext().getInitParameter("jdbcCorrectorPassw");     //pobieranie hasła użytkownika "corrector"
        String jdbcSupplier = getServletContext().getInitParameter("jdbcSupplier");                 //pobieranie nazwy użytkownika "supplier"
        String jdbcSupplierPassw = getServletContext().getInitParameter("jdbcSupplierPassw");       //pobieranie hasła użytkownika "supplier"
        String jdbcDictSelect = getServletContext().getInitParameter("jdbcDictSelect");             //pobieranie nazwy użytkownika "dictSelect"
        String jdbcDictSelectPassw = getServletContext().getInitParameter("jdbcDictSelectPassw");   //pobieranie hasła użytkownika "dictSelect"
        String jdbcDictUpdate = getServletContext().getInitParameter("jdbcDictUpdate");             //pobieranie nazwy użytkownika "dictUpdate"
        String jdbcDictUpdatePassw = getServletContext().getInitParameter("jdbcDictUpdatePassw");   //pobieranie hasła użytkownika "dictUpdate"
        String jdbcDictInsert = getServletContext().getInitParameter("jdbcDictInsert");             //pobieranie nazwy użytkownika "dictIndert"
        String jdbcDictInsertPassw = getServletContext().getInitParameter("jdbcDictInsertPassw");   //pobieranie hasła użytkownika "dictIndert"
        String jdbcDictDelete = getServletContext().getInitParameter("jdbcDictDelete");             //pobieranie nazwy użytkownika "dictDelete"
        String jdbcDictDeletePassw = getServletContext().getInitParameter("jdbcDictDeletePassw");   //pobieranie hasła użytkownika "dictDelete"
        String jdbcHist = getServletContext().getInitParameter("jdbcHistory");                      //pobieranie nazwy użytkownika "history"
        String jdbcHistPassw = getServletContext().getInitParameter("jdbcHistoryPassw");            //pobieranie hasła użytkownika "history"
       
       //product
        prodSDAO = new ProductDAO(jdbcURL, jdbcSupplier, jdbcSupplierPassw);
        prodmSDAO = new ProductMDAO(jdbcURL, jdbcSupplier, jdbcSupplierPassw);
        prodmWDAO = new ProductMDAO(jdbcURL, jdbcWorker, jdbcWorkerPassw);
        prodmCDAO = new ProductMDAO(jdbcURL, jdbcCorrector, jdbcCorrectorPassw);
       //review 
        rewDAO = new ReviewDAO(jdbcURL, jdbcCorrector, jdbcCorrectorPassw);
       //login 
        loginDAO = new LoginDAO(jdbcURL, jdbcAdmin, jdbcAdminPass);
       //user
        userADAO = new UserDAO(jdbcURL, jdbcAdmin, jdbcAdminPass);
        userWDAO = new UserDAO(jdbcURL, jdbcWorker, jdbcWorkerPassw);
        userCDAO = new UserDAO(jdbcURL, jdbcCorrector, jdbcCorrectorPassw);
        userSDAO = new UserDAO(jdbcURL, jdbcSupplier, jdbcSupplierPassw);
        usermDAO = new UserMDAO(jdbcURL, jdbcWorker, jdbcWorkerPassw);
       //order 
        ordWDAO = new OrderDAO(jdbcURL, jdbcWorker, jdbcWorkerPassw);
        ordCDAO = new OrderDAO(jdbcURL, jdbcCorrector, jdbcCorrectorPassw);
       //basket + order-product(const basket)  
        ordpDAO = new OrderPDAO(jdbcURL, jdbcWorker, jdbcWorkerPassw);
       //history 
        histDAO = new HistoryDAO(jdbcURL, jdbcHist, jdbcHistPassw);
        histADAO = new HistoryDAO(jdbcURL, jdbcAdmin, jdbcAdminPass);
     //dictionary tables  
       //Menu
        ctADAO = new CatTagDAO(jdbcURL, jdbcAdmin, jdbcAdminPass);
        ctCDAO = new CatTagDAO(jdbcURL, jdbcCorrector, jdbcCorrectorPassw);
        ctSDAO = new CatTagDAO(jdbcURL, jdbcSupplier, jdbcSupplierPassw);
        grADAO = new GroupDAO(jdbcURL, jdbcAdmin, jdbcAdminPass);
        grSDAO = new GroupDAO(jdbcURL, jdbcSupplier, jdbcSupplierPassw);
      //for Product 
        colDAO = new ColorDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
        fabricDAO = new FabricDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
        delDAO = new DeliveryDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
        shpDAO = new ShapeDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
        colUDAO = new ColorDAO(jdbcURL, jdbcDictUpdate, jdbcDictUpdatePassw);
        fabricUDAO = new FabricDAO(jdbcURL, jdbcDictUpdate, jdbcDictUpdatePassw);
        delUDAO = new DeliveryDAO(jdbcURL, jdbcDictUpdate, jdbcDictUpdatePassw);
        shpUDAO = new ShapeDAO(jdbcURL, jdbcDictUpdate, jdbcDictUpdatePassw);
        colIDAO = new ColorDAO(jdbcURL, jdbcDictInsert, jdbcDictInsertPassw);
        fabricIDAO = new FabricDAO(jdbcURL, jdbcDictInsert, jdbcDictInsertPassw);
        delIDAO = new DeliveryDAO(jdbcURL, jdbcDictInsert, jdbcDictInsertPassw);
        shpIDAO = new ShapeDAO(jdbcURL, jdbcDictInsert, jdbcDictInsertPassw);
        colDDAO = new ColorDAO(jdbcURL, jdbcDictDelete, jdbcDictDeletePassw);
        fabricDDAO = new FabricDAO(jdbcURL, jdbcDictDelete, jdbcDictDeletePassw);
        delDDAO = new DeliveryDAO(jdbcURL, jdbcDictDelete, jdbcDictDeletePassw);
        shpDDAO = new ShapeDAO(jdbcURL, jdbcDictDelete, jdbcDictDeletePassw);
       //for Order 
        payDAO = new PaymentDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
        discDAO = new DiscountDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
        payUDAO = new PaymentDAO(jdbcURL, jdbcDictUpdate, jdbcDictUpdatePassw);
        discUDAO = new DiscountDAO(jdbcURL, jdbcDictUpdate, jdbcDictUpdatePassw);
        payIDAO = new PaymentDAO(jdbcURL, jdbcDictInsert, jdbcDictInsertPassw);
        discIDAO = new DiscountDAO(jdbcURL, jdbcDictInsert, jdbcDictInsertPassw);
        payDDAO = new PaymentDAO(jdbcURL, jdbcDictDelete, jdbcDictDeletePassw);
        discDDAO = new DiscountDAO(jdbcURL, jdbcDictDelete, jdbcDictDeletePassw);
        statDAO = new StatusDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
       //for user 
        rankDAO = new RankDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
       //for history 
        actDAO = new ActionDAO(jdbcURL, jdbcDictSelect, jdbcDictSelectPassw);
        
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
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
        
        String action = request.getServletPath();                                   //sprawdzenie ścieżki z której przyszła odpowiedź
        try {
            switch (action) {                                                       //obsłyga po ścieżce
              //administrator
                case "/administrator": indexAdmin(request,response); break;         //strona główna administratora
                case "/usersHistory": viewUsersHistory(request,response); break;    //strona widoku histroii uzytkowników
                case "/addUsers": addUserAdmin(request,response); break;            //dodawanie pracowników
                case "/deleteUser": deleteUserAdmin(request,response); break;       //usuwanie pracowników 
                case "/addTag": addTagAdmin(request,response); break;               //dodawanie nowego tagu
                case "/addCategory": addCategoryAdmin(request,response); break;     //dodawanie nowej kategorii
                case "/addGroup": addGroupAdmin(request,response); break;           //dodawanie nowej grupy
                case "/editTag": updateTagAdmin(request,response); break;           //modyfikacja tagu w menu
                case "/editCategory": updateCategoryAdmin(request,response); break; //modyfikacja kategorii w menu
                
              //pracownik
                case "/worker": indexWorker(request,response); break;                 //strona główna pracownika
                case "/viewOrder": viewOrderForWorker(request,response); break;       //wyświetlanie zamówień dla pracownika
                case "/modifyOrder": updateViewWorker(request,response); break;       //modyfikacja zamówień dla pracownika
              
              //korektor 
                case "/corrector": indexCorrector(request,response); break;            //strona główna korektora
               //widok
                case "/viewReview": viewForCorrector(request,response,1); break;       //widok recenzji korektora
                case "/viewProduct": viewForCorrector(request,response,2); break;      //widok produktów korektora
                case "/viewCategory": viewForCorrector(request,response,3); break;     //widok kategorii korektora
                case "/viewTag": viewForCorrector(request,response,4); break;          //widok tagów korektora
               //modyfikacji 
                case "/modifyReview": updateViewCorrector(request,response,1); break;    //modyfikacja recenzji przez korektora
                case "/modifyProduct": updateViewCorrector(request,response,2); break;   //modyfikacja produktu przez korektora
                case "/modifyCategory": updateViewCorrector(request,response,3); break;  //modyfikacja kategori przez korektora
                case "/modifyTag": updateViewCorrector(request,response,4); break;       //modyfikacja tagu przez korektora
                
              //zaopatrzeniowiec 
                case "/supplier": indexSupplier(request,response); break;                     //strona główna zaopatrzeniowca
                case "/viewOneProduct": viewForSupplier(request,response); break;             //widok jednego produktu
                case "/updateProduct": updateProductForSupplier(request,response); break;     //uaktualnienie produktu
                case "/addProduct": addProductForSupplier(request,response); break;           //dodanie nowego produktu
                case "/deleteProduct": deleteProductForSupplier(request,response); break;     //usunięcie produktu (ilość na stanie stary -> 0)
                case "/restockProduct": restockProductForSupplier(request,response); break;   //odnowienie produktu (ilość na stanie 0 -> nowy)
                 
              //inne
                case "/updateDictionary": updateDict(request,response); break;          //aktualizacja tabel słownikowych dla administratora i zaopatrzeniowca
                case "/insertDictionary": insertDict(request,response); break;          //dodawanie danych w tabelach słownikowych dla administratora i zaopatrzeniowca
                case "/deleteDictionary": deleteDict(request,response); break;          //usuwanie danych w tabelach słownikowych dla administratora i zaopatrzeniowca
            }
        } catch (SQLException ex) { throw new ServletException(ex); } //obsługiwanie błędów
        catch (Exception ex) {
            Logger.getLogger(ControlUserRank.class.getName()).log(Level.SEVERE, null, ex);
        }           
    }
    
    
  //ADMINISTRATOR  
    //strona główna administratora  
    @SuppressWarnings("unchecked")
    private void indexAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        RequestDispatcher dispatcher;                                           //deklaracja zmiennej przekierowującej do danej strony
            
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę  ">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //jeśli użytkownik posiada rangę i jest nią 1 (administrator)
          //sprawdzenie czy przesłano numer zakładki                        <editor-fold defaultstate="collapsed" desc="sprawdzenie czy przesłano numer zakładki">
            int tabl = 0;                                                   //ustawienie początkowej wartości
            try{ tabl = Integer.parseInt(request.getParameter("t")); }      //pobranie zakładki
            catch(NumberFormatException x) {                                //sprawdzanie błędów 
                sess.setAttribute("err_disc","Brak poprawnych danych!");    //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                             //przekierowanie do strony głównej
                return;                                                     //przerwanie działania funkcji
            }                                                               //</editor-fold>
            switch(tabl){                                                       //sprawdzenie do której zakładki przenieść użytkownika
                case 1: // = tabele słownikowe
                  //deklaracja zmiennych list                               <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych list">   
                    List<Color> listCol = new ArrayList<>();                //lista koloru
                    List<Delivery> listDel = new ArrayList<>();             //lista dostawy
                    List<Fabric> listFbr = new ArrayList<>();               //lista materiałów
                    List<Payment> listPay = new ArrayList<>();              //lista płatności
                    List<Shape> listShp = new ArrayList<>();                //lista kształtu
                    List<Discount> listDisc = new ArrayList<>();            //lista kodów promocyjnych
                    List<Discount> discch = new ArrayList<>();              //lista pomocnicza </editor-fold>
                  //sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)  <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)">  
                    discch = (List<Discount>) sess.getAttribute("listDelivery");//sprawdzanie listy zamówień
                    if(discch==null){                                           //jeśli lista jest pusta
                        listCol = colDAO.read();                                //pobieranie listy kolorów
                        sess.setAttribute("listColor", listCol);                //zapisanie listyw sesji
                        listDel = delDAO.read();                                //pobieranie listy dostaw                
                        sess.setAttribute("listDelivery", listDel);             //zapisanie listy w sesji
                        listFbr = fabricDAO.read();                             //pobieranie listy materiałów
                        sess.setAttribute("listFbric", listFbr);                //zapisanie listy w sesji
                        listPay = payDAO.read();                                //pobieranie listy płatności
                        sess.setAttribute("listPayment", listPay);              //zapisanie listy w sesji
                        listShp = shpDAO.read();                                //pobieranie listy kształtów
                        sess.setAttribute("listShpe", listShp);                 //zapisanie listy w sesji
                        listDisc = discDAO.read();                              //pobieranie listy kodów promocyjnych
                        sess.setAttribute("listDiscount", listDisc);            //zapisanie listy w sesji 
                    }                                                           //</editor-fold>
                break;
                case 2: // = użytkownicy i ich historia
                  //deklaracja zmiennych list                               <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych list">   
                    List<User> listUser = new ArrayList<>();                //lista użytkowników 
                    List<Rank> listRnk = new ArrayList<>();                 //lista rang 
                    List<Rank> listRnk2 = new ArrayList<>();                //lista rang 
                    List<User> userch = new ArrayList<>();                  //lista sprawdzajaca </editor-fold>
                  //sprawdzenie i przypisanie zmiennej sesyjnej (jeśli brak)    <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennej sesyjnej (jeśli brak)">  
                    userch = (List<User>) sess.getAttribute("listUsers");       //sprawdzanie listy uzytkowników
                    if(userch==null){                                           //jeśli lista jest pusta
                        listUser = userADAO.readRnk();                              //pobranie listy użytkowników
                        sess.setAttribute("listUsers", listUser);               //zapisanie listy w sesji
                        listRnk2 = rankDAO.read();                              //pobranie listy użytkowników
                        sess.setAttribute("listRnk", listRnk2);                 //zapisanie listy w sesji
                        for(User item : listUser){
                            Rank rankUsr = rankDAO.read(item.getId_rank());     //pobranie listy użytkowników
                            listRnk.add(rankUsr);
                        }
                        sess.setAttribute("listRank", listRnk);                 //zapisanie listy w sesji
                      //sprawdzanie czy użytkownik posiada historię
                        List<Boolean> listHistUsr = new ArrayList<>();              //lista historii
                        for(User item : listUser){                                  //dla każdego użytkownika
                            listHistUsr.add(histADAO.ifUsrHasHistory(item.getId())); //sprawdzenie czy historia istanieje
                        }
                        sess.setAttribute("history", listHistUsr);
                    }                                                           //</editor-fold>
                  
                break;
                case 3: // = edycja menu
                  //deklaracja zmiennych list                                   <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych list">   
                    List<CatTag> listCT = new ArrayList<>();                    //lista łącznikowa = kategorie-tagi 
                    List<Group> listGr = new ArrayList<>();                     //lista grup
                    List<CatTag> listCat = new ArrayList<>();                   //lista kategorii
                    List<CatTag> CatTag = new ArrayList<>();                    //lista sprawdzajaca </editor-fold>
                  //sprawdzenie i przypisanie zmiennej sesyjnej (jeśli brak)    <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennej sesyjnej (jeśli brak)">  
                   CatTag = (List<CatTag>) sess.getAttribute("listCatTag");     //sprawdzanie listy łącznkowej
                    if(CatTag==null){                                           //jeśli lista jest pusta
                        listCT = ctADAO.read();                                  //pobranie listy łącznikowej tag-kategoria                      
                        sess.setAttribute("listCatTag",listCT);                 //zapisanie listy w sesji
                        listGr = grADAO.read();                                  //pobranie listy grupy
                        sess.setAttribute("listGroup",listGr);                  //zapisanie listy w sesji
                        listCat = ctADAO.forAdminCat();                          //pobranie listy kategorii
                        sess.setAttribute("listCategories",listCat);            //zapisanie listy w sesji
                    }                                                           //</editor-fold>
                 break;
            }
            request.setAttribute("t", tabl);                                            //przekazanie znacznika zakładki do pliku JSP
            dispatcher = request.getRequestDispatcher("jsp/usersRnk/Administrator.jsp");//przekierownie do pliku
            dispatcher.forward(request, response);                                      //przekazanie parametrów
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 1)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    } 
    
    //strona widoku histroii użytkowników
    private void viewUsersHistory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        RequestDispatcher dispatcher;                                           //deklaracja zmiennej przekierowującej do danej strony
            
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę  ">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==1)){                                      //jeśli użtkownik posiada rangę i jest nią ranga 1 (administrator)
          //sprawdzanie czy id i nazwa zostały przesłane                                 <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id i nazwa zostały przesłane">
            int id;                                                                     //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("id")); }                   //pobranie id 
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak identyfikatora użytkownika!");       //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                                         //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
          //przypisanie odpowiednich zmiennych                                          <editor-fold defaultstate="collapsed" desc="przypisanie odpowiednich zmiennych">
            User user = userADAO.read(id);                                               //pobranie podstawowych danych użytkownika
            request.setAttribute("user", user);                                         //zapisanie zmiennej do przesłania do pliku JSP
            Rank rankUsr = rankDAO.read(user.getId_rank());                             //pobranie rangi użytkownika
            request.setAttribute("rankUsr", rankUsr);                                   //zapisanie zmiennej do przesłania do pliku JSP
            List<History> listHist = histADAO.readUsr(id);                               //pobranie historii użytkownika
            request.setAttribute("listHist", listHist);                                 //zapisanie zmiennej do przesłania do pliku JSP
            List<Action> act = actDAO.read();                                           //pobranie listy ahcji dla historii
            request.setAttribute("act", act);                                           //zapisanie zmiennej do przesłania do pliku JSP </editor-fold>
            
            dispatcher = request.getRequestDispatcher("jsp/usersRnk/ViewRnk.jsp");                   //przekierowanie do strony widoków
            dispatcher.forward(request, response);
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 1)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //dodawanie pracowników dla administratora
    private void addUserAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, Exception{
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 1;                                                     //użytkownik zmieniający, model historii, akcja
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
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&(rnk.getId_rank()==1)){                          //jeśli jest to użytkownik z rangą równą 1 (administrtor)
          //sprawdzanie czy wszystkie wymagane dane hasła zostały przesłane             <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane hasła zostały przesłane">
            boolean isusr_u = false, rnk_u = false, news_u = false;                     //deklaracja zmiennych
            String nameu="", snameu="", mailu="", telu="", isusr="", news ="";          //deklaracja zmiennych
            int id_rank = 0;                                                            //deklaracja zmiennych
            try{                                                                        //pobieranie danych
               isusr = request.getParameter("usr_isusr");                               //czy jest to uzytkownik
               id_rank = Integer.parseInt(request.getParameter("usr_rnk"));             //identyfikator rangi
               nameu = Jsoup.parse(request.getParameter("usr_name")).text();            //imię użytkownika
               snameu = Jsoup.parse(request.getParameter("usr_sname")).text();          //nazwisko 
               mailu = Jsoup.parse(request.getParameter("usr_emali")).text();           //e-mail
               telu = Jsoup.parse(request.getParameter("usr_phone")).text();            //telefon
               news = request.getParameter("usr_news");                                 //newsletter
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("administrator?t=2");                             //przerwanie działania funkcji
            }
            finally{                                                                      
              //ustawianie znaczników                                               <editor-fold defaultstate="collapsed" desc="ustawianie znaczników">  
                if(isusr != null){ isusr_u = true; }                                //czy jest użytkownikiem
                if(news != null){ news_u = true; }                                  //czy chce się zapisać na newsletter
                if(0 != id_rank){ rnk_u = true; }                                   //czy posiada rangę </editor-fold>
            }                                                                           //</editor-fold>
          //sprawdzanie czy hasło zosatło przesłane                                     <editor-fold defaultstate="collapsed" desc="sprawdzanie czy hasło zosatło przesłane ">  
            String pass = "", generate;                                                 //deklaracja zmiennych
            try{
                generate = request.getParameter("generete");                            //sprawdzenie czy hasło tzreba wygenerować
                if(generate!=null){ pass = RandomPassword.generate(); }                 //jeśli tak to generowanie hasła
                else{ pass = Jsoup.parse(request.getParameter("usr_pass")).text(); }    //jeśli nie pobranie hasła z pola tekstowego 
            }
            catch (NumberFormatException | NullPointerException ex){}                    //obsługa błedów
            finally{
                if(pass.equals("")){                                                    //jeśli hasło dalej jest puste
                    sess.setAttribute("err_disc","Brak poprawnych danych!");            //ustawianie zmiennej sesyjnej
                    response.sendRedirect("administrator?t=2");                         //przerwanie działania funkcji 
                }
            }                                                                           //</editor-fold>     
          //dodawanie użytkownika i loginu                                                          <editor-fold defaultstate="collapsed" desc="dodawanie użytkownika i loginu">  
            String reg = formatLocalDate.format(LocalDateTime.now());                               //pobranie czasu rejestracji                  
            
            User usera = new User(0, isusr_u, rnk_u, id_rank, nameu, 
                    snameu, mailu, telu, news_u, reg);                                              //model użytkownika
            boolean q = userADAO.create(usera);                                                      //utworzenie użytkownika
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano użytkownika.");}                 //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania użytkownika.");       //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }   
            int id_usrl = userADAO.readId(mailu);
            
            Login log = new Login(id_usrl, mailu, pass, reg);                                       //model loginu
            q = loginDAO.create(log);                                                               //utworzenie loginu
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano login.");}                       //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania loginu.");            //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>
        
          //wysłanie emaila powiadamiającego                                                    <editor-fold defaultstate="collapsed" desc="wysłanie emaila powiadamiającego">    
            String to = mailu;                                                                  //do kogo ma być wysłany email
            String subject = "Dane logowania do wytryny ametyst";                               //temat wiadomości
            String message =  "<h2>Administrator aplikacji właśnie założył Pani/Panu "
                    + "konto z rangą: <i>"+rankDAO.getName(id_rank)+"</i></h2> "
                    + "<h3>Poniżej znajdują się dane logowania:</h3>"
                    + "<h2>Login: <b>"+mailu+"</b></h2>"
                    + "<h2>Hasło: <b>"+pass+"</b></h2>"
                    + "<h4>Życzymy udanego korzystania z aplikacji</h4>";                       //treść wiadomości
            SendMail.send(to, subject, message);                                                //przekierowanie do klasy wysyłającej </editor-fold>
            
          //ustawianie historii modyfikacji                                                                         <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "DELETE FROM `product` WHERE `id_prod` = "+id_usrl;                                       //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "INSERT INTO `user` ( `user`, `rank`, `id_rank-u`, `name-u`, `surname-u`, "
                        + "`email`, `telephone`, `newsletter`, `register`) "
                        + "VALUES ("+usera.isUser()+", "+usera.isRank()+", "+usera.getId_rank()+", "
                        + "'"+usera.getName()+"', "+ usera.getSurname()+", "+usera.getEmail()+", "
                        + usera.getTel()+", "+usera.isNewsletter()+", "+usera.getRegist_date()+")";                 //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Dodanie nowego użytownika z hasłem";                                                     //ustawienie opisu 
            modify="imię: "+usera.getName()+",<br>nazwisko: "+ usera.getSurname()+",<br>email: "
                    +usera.getEmail()+",<br>telefon: "+usera.getTel();                                              //ustawienie tekstu modyfikacji
            History his = new History(NULL, id_usr, action, description, now, 
                    beforeQuery, afterQuery, modify);                                                               //ustawienie modelu historii
            q = histDAO.create(his);                                                                                //dodanie historii do bazy danych 
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}             //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold>
        }
        else{                                                               //w przeciwnym wypadku
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji 
        }      
        sess.removeAttribute("listUsers");                                  //wymuszenie pobrania nowych danych
        response.sendRedirect("administrator?t=2");                         //przekierowanie na stronę admina
    }
 
    //usuwanie pracowników dla administratora  
    private void deleteUserAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, Exception{
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        History his; int id_usr = 0; int action = 3;                                                     //użytkownik zmieniający, model historii, akcja
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
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&(rnk.getId_rank()==1)){                          //jeśli jest to użytkownik z rangą równą 1 (administrtor)
          
          //sprawdzanie czy id zostało przesłane                                        <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id zostało przesłane">
            int id;                                                                     //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("id")); }                   //pobranie id 
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak odpowiednich danych!");       //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                                         //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
                        
          //usuwanie użytkownika i loginu                                                           <editor-fold defaultstate="collapsed" desc="usuwanie użytkownika i loginu">  
            Login log = loginDAO.read(id);                                                          //pobranie loginu do historii
            boolean q = loginDAO.delete(id);                                                        //usunięcie loginu (klucz obcy dla użytkownika)
            if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto login.");}                     //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{ //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania loginu.");             //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }
            User usr = userADAO.read(id);                                                            //pobranie danych dla historii
            q = userADAO.delete(id);                                                                 //usunięcie użytkownika
            if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto użytkownika.");}               //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{ //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania użytkownika.");        //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>  
            
          //wysłanie emaila powiadamiającego                                                    <editor-fold defaultstate="collapsed" desc="wysłanie emaila powiadamiającego">    
            String to = usr.getEmail();                                                         //do kogo ma być wysłany email
            String subject = "Informacja o usunięciu konta";                                    //temat wiadomości
            String message =  "<h2>Administrator usunął Pani/Pana "
                    + "konto z rangą: <i>"+rankDAO.getName(usr.getId_rank())+"</i></h2> "
                    + "<h4>Z tego powodu możliowść logowania zostaje usunięta"
                    + "<br>Życzymy miłego dnia</h4>";                                           //treść wiadomości
            SendMail.send(to, subject, message);                                                //przekierowanie do klasy wysyłającej </editor-fold>
            
          //ustawianie historii modyfikacji                                                                         <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "INSERT INTO `user` ( `id_user`, `user`, `rank`, `id_rank-u`, `name-u`, "
                    + "`surname-u`, `email`, `telephone`, `newsletter`, `register`) "
                    + "VALUES ("+usr.getId()+", "+usr.isUser()+", "+usr.isRank()
                    +", "+usr.getId_rank()+", '"+usr.getName()+"', '"+usr.getSurname()
                    +"', '"+usr.getEmail()+"', '"+usr.getTel()+"', "+usr.isNewsletter()
                    +", '"+usr.getRegist_date()+"');"
                    + "INSERT INTO `login` (`id_user-l`, `login`, `password`, `last_log`)"
                    + "VALUES ("+log.getId()+", '"+log.getLogin()+"', SHA2('"
                    + log.getPassw()+"', 384), '"+log.getLast_log()+"')";                                   //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "DELETE FROM `login` WHERE `id_user-l` = "+log.getId()
                    + "DELETE FROM `user` WHERE `id_user` = "+usr.getId();                                  //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Usunięcie użytkownika";                                                          //ustawienie opisu 
            modify="imię: "+usr.getName()+"<br>nazwisko:"+usr.getSurname()
                    +"e-mail: "+usr.getEmail();                                                             //ustawienie tekstu modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);     //ustawienie modelu historii
            q = histDAO.create(his);                                                                        //dodanie historii do bazy danych 
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}     //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold>
        }
        else{                                                               //w przeciwnym wypadku
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji 
        }      
        sess.removeAttribute("listUsers");                                  //wymuszenie pobrania nowych danych
        response.sendRedirect("administrator?t=2");                         //przekierowanie na stronę admina
    }
 
    //dodawanie nowego tagu
    private void addTagAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, Exception{
        
        sess=request.getSession(true);                                                  //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = "", afterQuery = "", description = null, modify = "";          //zapytanie przed, zapytanie po, opis, modyfikacja
        History his; int id_usr = 0; int action = 1; boolean q = false;                     //użytkownik zmieniający, model historii, akcja
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
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&(rnk.getId_rank()==1)){                          //jeśli jest to użytkownik z rangą równą 1 (administrtor)
          //sprawdzanie czy id zostało przesłane                                <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id zostało przesłane">
            int id= 0;                                                          //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("cat")); }          //pobieranie danych
            catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");        //ustawianie zmiennej sesyjnej
                response.sendRedirect("administrator?t=3");                     //przekierowanie do strony administratora
                return;                                                         //przerwanie działania funkcji
            }                                                                   //</editor-fold>
            
          //sprawdzanie tagów                                                     <editor-fold defaultstate="collapsed" desc="sprawdzanie tagów">  
            String[] tags = null;                                               //deklaracja zmiennej
            try{ tags = request.getParameterValues("multi_tags"); }             //pobranie tagów
            catch (NumberFormatException | NullPointerException ex){ }          //sprawdzanie błędów </editor-fold>
            CatTag CT = null; CatTag CTnames = null;                                                    //deklaracja dodatkowych zmiennych
            if(tags!=null){                                                                             //jeśli tagi nie są puste
                modify="nazwa: ";                                                                       //przygotowanie zmiennej modyfikacji historii
                for (String tag1 : tags) {                                                              //dla każdego tagu 
                  //dodawanie istniejących tagów                                                        <editor-fold defaultstate="collapsed" desc="dodawanie istniejących tagów">  
                    CT = new CatTag(0, id, Integer.parseInt(tag1));                                     //tworzenie konstruktora tagu
                    q = ctADAO.createCatTag(CT);                                                         //dodanie tagu do bazy danych
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano tagi.");}                    //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{ //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania tagów.");         //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                         //zatrzymanie działania funkcji
                    }                                                                                   //</editor-fold>  
                      
                    CTnames = ctADAO.readTag(Integer.parseInt(tag1));                                    //wczytanie nazwy tagu dla historii zdarzeń
                    int id_cattag = ctADAO.getIdCatTag(id, Integer.parseInt(tag1));                      //pobranie identyfikatora tagu dla historii zdarzeń

                  //ustawianie wstępnej historii modyfikacji                                             <editor-fold defaultstate="collapsed" desc="ustawianie wstępnej historii modyfikacji">
                    beforeQuery += "DELETE FROM `category-tag` WHERE `id_catag` = "+id_cattag+";";     //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery += "INSERT INTO `category-tag`(`id_cat-ct`, `id_tag-ct`) "
                            + "VALUES ("+CT.getIdCat()+","+CT.getIdTag()+");'";                         //ustawienie zapytania SQL "po zmianie" dla historii
                    modify += CTnames.getName()+",<br>";                                                //ustawienie modyfikacji </editor-fold>
                }
            }
            else{
              //sprawdzanie nazwy                                                    <editor-fold defaultstate="collapsed" desc="sprawdzanie nazwy">  
                String name = null;                                                  //deklaracja zmiennej
                try{ name = Jsoup.parse(request.getParameter("name")).text(); }      //pobranie nazwy
                catch (NumberFormatException | NullPointerException ex){ }           //sprawdzanie błędów </editor-fold>
                
                if(!name.equals("")){
                  //dodawanie nazwy nowego tagu                                                                 <editor-fold defaultstate="collapsed" desc="dodawanie nazwy nowego tagu">  
                    CatTag tagA = new CatTag(0, name);                                                          //utworzenie konstruktora tagu
                    q = ctADAO.createTag(tagA);                                                                  //dodanie tagu do bazy    
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano tag do bazy.");}                     //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania tagu.");}               //w przeciwnym wypadku wyświetlenie odpowiedniego komunikatu o błędzie
                    int id_tag = ctADAO.getIdTag(name);                                                          //pobranie identyfikatora tagu
                    CT = new CatTag(0, id, id_tag);                                                             //utworzenie konstruktora tabeli łącznikowej
                    q = ctADAO.createCatTag(CT);                                                                 //tworzenie połączenia kategorii i tagu
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano tag.");}                             //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{ //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas łączenia tagu i  kategorii.");      //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                                 //zatrzymanie działania funkcji
                    }                                                                                           //</editor-fold> 
                    
                    int id_catTag = ctADAO.getIdCatTag(id, id_tag);                                              //pobranie identyfikatora tagu i kategorii dla historii zdarzeń
                    
                  //ustawianie wstępnej historii modyfikacji                                <editor-fold defaultstate="collapsed" desc="ustawianie wstępnej historii modyfikacji">
                    beforeQuery = "DELETE FROM `category-tag` "
                            +"WHERE `id_catag` = "+id_catTag+"; "
                            +"DELETE FROM `tag`  WHERE `id_tag` = "+id_tag;                 //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "INSERT INTO `tag`(`name-t`) VALUES ('"+name+"');"
                            +"INSERT INTO `category-tag`(`id_cat-ct`, `id_tag-ct`) "
                            +"VALUES ("+id_tag+","+id+");";                                //ustawienie zapytania SQL "po zmianie" dla historii
                    modify="nazwa: "+name;                                                 //ustawienie modyfikacji </editor-fold>
                } 
                else{
                    sess.setAttribute("err_disc","Musisz podać prawidłowe dane.");;                  //ustawianie zmiennej sesyjnej
                    response.sendRedirect("administrator?t=3");                                      //przekierowanie do strony administratora
                    return;                                                                          //przerwanie działania funkcji
                }
            }
              //ustawianie historii modyfikacji                                                                         <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                description = "Dodanie nowego tagu do kategorii";
                his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);                                                               //ustawienie modelu historii
                q = histDAO.create(his);                                                                                //dodanie historii do bazy danych 
                if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}             //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold>
        }
        else{                                                               //w przeciwnym wypadku
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji 
        }      
        sess.removeAttribute("listCatTag");                                  //wymuszenie pobrania nowych danych
        response.sendRedirect("administrator?t=3");                         //przekierowanie na stronę admina
    }
    
    //dodawanie nowej kategorii
    private void addCategoryAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, Exception{
        
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
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&(rnk.getId_rank()==1)){                          //jeśli jest to użytkownik z rangą równą 1 (administrtor)
          //sprawdzanie czy wszystkie wymagane dane zostały przesłane             <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
            String name=""; int id_gr = 0;                                                            //deklaracja zmiennych
            try{                                                                        //pobieranie danych
              id_gr = Integer.parseInt(request.getParameter("gr"));
              name = Jsoup.parse(request.getParameter("name")).text(); 
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("administrator?t=3");                             //przerwanie działania funkcji
            }                                                                           //</editor-fold>
               
          //dodawanie kategorii                                                                     <editor-fold defaultstate="collapsed" desc="dodawanie kategorii">  
            CatTag catA = new CatTag(0, name, id_gr);                                               //model kategorii
            boolean q = ctADAO.createCat(catA);                                                      //utworzenie kategorii
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano kategorię.");}                   //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania kategorii.");         //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>  
            
            int id_cat = ctADAO.getIdCat(name);                                                      //pobranie id kategorii </editor-fold>
           
          //ustawianie historii modyfikacji                                                              <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "DELETE FROM `group-g` WHERE `id_group` = "+id_cat;                           //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "INSERT INTO `group-g`(`name-g`) VALUES ('"+name+"')";                         //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Dodanie nowej kategorii"; modify="nazwa: "+name;                             //ustawienie opisu i tekstu modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify); //ustawienie modelu historii
            q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold>
        
            sess.removeAttribute("listCatTag");                                  //wymuszenie pobrania nowych danych
            response.sendRedirect("administrator?t=3");                         //przekierowanie na stronę admina
        
        }
        else{                                                               //w przeciwnym wypadku
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
        } 
    }
 
    //dodawanie nowej grupy
    private void addGroupAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, Exception{
        
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
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&(rnk.getId_rank()==1)){                          //jeśli jest to użytkownik z rangą równą 1 (administrtor)
          //sprawdzanie czy wszystkie wymagane dane zostały przesłane                    <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
           String name_g = "", name2 = "";                                              //deklaracja zmiennych
           try{
               name_g = Jsoup.parse(request.getParameter("name")).text();               //pobranie nazwy grupy
               name2 = Jsoup.parse(request.getParameter("name2")).text();               //pobranie nazwy pierwszej kategorii w grupie
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("administrator?t=3");                             //przerwanie działania funkcji
            }                                                                           //</editor-fold>
          
          //dodawanie grupy do bazy danych                                                          <editor-fold defaultstate="collapsed" desc="dodawanie grupy do bazy danych">  
            Group group = new Group(0, name_g);                                                     //model grupy                
            boolean q = grADAO.create(group);                                                        //utworzenie grupy
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano grupę.");}                       //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania grupy.");             //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatzrymanie działania funkcji
            }
            int id_g = grADAO.getId(name_g);                                                         //pobranie identyfikatora utworzonej grupy </editor-fold>
                
          //ustawianie historii modyfikacji                                                                         <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "DELETE FROM `group-g` WHERE `id_group` = "+id_g;                                         //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "INSERT INTO `group-g`(`name-g`) VALUES ('"+name_g+"')";                                   //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Dodanie nowej grupy"; modify="nazwa: "+name_g;                                           //ustawienie opisu i tekstu modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);             //ustawienie modelu historii
            q = histDAO.create(his);                                                                                //dodanie historii do bazy danych 
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}             //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold>
            
          //dodawanie pierwszej kategorii do bazy danych                                            <editor-fold defaultstate="collapsed" desc="dodawanie pierwszej kategorii do bazy danych">  
            CatTag catA = new CatTag(0, name2, id_g);                                               //model kategorii 
            q = ctADAO.createCat(catA);                                                              //utworzenie kategorii
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano grupę.");}                       //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{ //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania grupy.");             //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>  
            
            int id_cat = ctADAO.getIdCat(name2);                                                     //pobranie identyfikatora utworzonej kategorii </editor-fold>
                 
          //ustawianie historii modyfikacji                                                                         <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "DELETE FROM `category` WHERE `id_cat` = "+id_cat;                                       //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "INSERT INTO `category`(`name-c`) VALUES ('"+name2+"')";                                    //ustawienie zapytania SQL "po zmianie" dla historii
            description = "Dodanie nowej kategorii do grupy"; modify="nazwa: "+name2+", <br>grupa: "+name_g;        //ustawienie opisu i tekstu modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);             //ustawienie modelu historii
            q = histDAO.create(his);                                                                                //dodanie historii do bazy danych 
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}             //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold>
       
        }
        else{                                                               //w przeciwnym wypadku
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji 
        }      
        sess.removeAttribute("listCatTag");                                 //wymuszenie pobrania nowych danych
        response.sendRedirect("administrator?t=3");                         //przekierowanie na stronę admina
    }
 
    //modyfikacja tagu w menu
    private void updateTagAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int action = 2; History his = null; boolean q = false;                              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //jeśli użytkownik posiada rangę i jest nią 1 (administrator)
         
          //sprawdzanie czy id i nazwa zostały przesłane                               <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id i nazwa zostały przesłane">
            int id; String name;                                                               //deklaracja zmiennych
            try{ 
                id = Integer.parseInt(request.getParameter("tag"));                      //pobranie id 
                name = Jsoup.parse(request.getParameter("name")).text();               //pobranie nazwy 
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("administrator?t=3");                             //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
              
          //uaktualnianie tagu                                                          <editor-fold defaultstate="collapsed" desc="uaktualnianie tagu">  
            CatTag tagb = ctADAO.readTag(id);                                            //pobranie tagu dla historii zdarzeń
            CatTag tag = new CatTag(id, name);                                          //utworzenie konstruktora
            q = ctADAO.updateTag(tag);                                                   //uaktualnienie tagu
            if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano tag.");}          //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                       //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas edycji tagu.");     //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                              //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                 //zatzrymanie działania funkcji
            }                                                                           //</editor-fold>
            
          //ustawianie historii modyfikacji                                                                     <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "UPDATE `tag` SET `name-t`= '"+tagb.getName()+"' WHERE `id_tag` = "+tagb.getId();     //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "UPDATE `tag` SET `name-t`= '"+tag.getName()+"' WHERE `id_tag` = "+tag.getId();        //ustawienie zapytania "po zmianie" dla historii
            description = "Modyfikacja tagu"; modify="nazwa: "+tagb.getName()+" > "+tag.getName();              //ustalenie opisu i modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);         //ustalenie konstruktora
            q = histDAO.create(his);                                                                            //tworzenie historii
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}       //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
              
            sess.removeAttribute("listCatTag");                                  //wymuszenie pobrania nowych danych
            response.sendRedirect("administrator?t=3");                         //przekierowanie na stronę admina
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony logowania
            return;                                                                     //przerwanie działania funkcji
        }
    } 

    //modyfikacja kategorii w menu
    private void updateCategoryAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int action = 2; History his = null; boolean q = false;                              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userADAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //jeśli użytkownik posiada rangę i jest nią 1 (administrator)
         
          //sprawdzanie czy id i nazwa zostały przesłane                               <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id i nazwa zostały przesłane">
            int id; String name;                                                               //deklaracja zmiennych
            try{ 
                id = Integer.parseInt(request.getParameter("cat"));                      //pobranie id 
                name = Jsoup.parse(request.getParameter("name")).text();               //pobranie nazwy 
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("administrator?t=3");                             //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
              
          //uaktualnianie kategorii                                                             <editor-fold defaultstate="collapsed" desc="uaktualnianie kategorii">  
            CatTag catb = ctADAO.readCat(id);                                                    //pobranie kategorii dla historii zdarzeń
            CatTag cat = new CatTag(id, name);                                                  //utworzenie konstruktora
            q = ctADAO.updateCat(cat);                                                           //uaktualnienie kategorii
            if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano kategorię.");}            //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                               //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas edycji kategorii.");        //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                         //zatrzymanie działania funkcji
            }                                                                                   //</editor-fold>  
            
          //ustawianie historii modyfikacji                                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "UPDATE `category` SET `name-c`= '"
                    +catb.getName()+"' WHERE `id_cat` = "+catb.getId();                                     //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "UPDATE `category` SET `name-c`= '"
                    +cat.getName()+"' WHERE `id_cat` = "+cat.getId();                                       //ustawienie zapytania "po zmianie" dla historii
            description = "Modyfikacja kategorii"; modify="nazwa: "+catb.getName()+" > "+cat.getName();     //ustalenie opisu i modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);     //ustalenie konstruktora                                                  //tworzenie historii
            q = histDAO.create(his);                                                                        //uaktualnienie kategorii
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}   //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                
            sess.removeAttribute("listCatTag");                                  //wymuszenie pobrania nowych danych
            response.sendRedirect("administrator?t=3");                         //przekierowanie na stronę admina
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony logowania
            return;                                                                     //przerwanie działania funkcji
        }
    } 

    
  //PRACOWNIK 
   //strona główna pracownika  
    @SuppressWarnings("unchecked")
    private void indexWorker(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji    
        
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę  ">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userWDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==2)){                              //jeśli użytkownik posiada rangę i jest nią 2 (pracownik)
          
          //deklaracja zmiennych list                                           <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych list">   
            List<Order> orders = new ArrayList<>();                             //stworzenie listy zamówień
            List<Order> orde = new ArrayList<>();                               //stworzenie listy zamówień
            List<UserMeta> usr = new ArrayList<>();                             //stworzenie listy użytkowników
            List<Integer> quant = new ArrayList<>();                            //stworzenie listyilości produktów </editor-fold>
            
          //sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)  <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)">  
            orders = (List<Order>) sess.getAttribute("listOrder");            //sprawdzanie listy zamówień
            if(orders==null){                                                    //jeśli lista jest pusta
              //wypisanie zamówień                                             <editor-fold defaultstate="collapsed" desc="wypisanie zamówień ">
                List<Order> ord = ordWDAO.readWorker(id_usr);                    //pobranie listy zamówień przypisanych do pracownika
                for (Order order : ord) {
                    orde.add(order);                                            //dodanie zamówienia
                }
                List<OrderProd> ordp= new ArrayList<>();                        //ustawienie listy
                int sum = 0;                                                    //deklaracja wstępna
                for (Order order : orde) {                                      //dla każdego zamówienia
                    sum=0;                                                      //deklaracja przed następnym zamówieniem
                    ordp= ordpDAO.read(order.getId());                          //pobranie listy produktów w zamówieniu
                    for (OrderProd orderp : ordp) {                             //dla każdego produktu
                        sum += orderp.getQuantity();                            //sumowanie produktów
                    }
                    quant.add(sum);                                             //dodanie sumy produktów do listy
                }
                usr = usermDAO.readForWorker();                                 //pobranie listy użytkowników dla pracownika </editor-fold>
              //przypisanie zmiennych sesyjnych                         <editor-fold defaultstate="collapsed" desc="przypisanie zmiennych sesyjnych">  
                sess.setAttribute("listOrder", orde);                   //zapisanie listy w sesji
                sess.setAttribute("listUser", usr);                     //zapisanie listy w sesji
                sess.setAttribute("listQuantity", quant);               //zapisanie listy w sesji </editor-fold>
            }                                                               //</editor-fold>
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/usersRnk/Worker.jsp");    //przekierownie do pliku
            dispatcher.forward(request, response);                                                 //przekazanie parametrów
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //widok zamówień pracownika
    private void viewOrderForWorker(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji    
        
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userWDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==2)){                              //jeśli użytkownik posiada rangę i jest nią 2 (pracownik)
         
          //sprawdzanie czy id zostało przesłane                                        <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id zostało przesłane">
            int id;                                                                     //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("id")); }                   //pobranie id 
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("worker");                                        //przekierowanie do strony pracownika
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
          //ustawienie zamówienia                                               <editor-fold defaultstate="collapsed" desc="ustawienie zamówienia">
            Order ord = ordWDAO.read(id);                                        //pobranie danych zamówienia
            request.setAttribute("order", ord);                                 //deklaracja dla pliku JSP
            
          //ustawianie tabel słownikowych                                       <editor-fold defaultstate="collapsed" desc="ustawianie tabel słownikowych">
            Status stat = statDAO.read(ord.getIdStat());                        //ststus zamówienia
            request.setAttribute("stat", stat);                                 //deklaracja dla pliku JSP
            List<Status> listStat = statDAO.read();                             //lista statusów
            request.setAttribute("listStat", listStat);                         //deklaracja dla pliku JSP
            Delivery del = delDAO.read(ord.getIdDeliv());                       //dostawa zamówienia
            request.setAttribute("deliv", del);                                 //deklaracja dla pliku JSP
            Payment pay = payDAO.read(ord.getIdPay());                          //metoda płatności zamówienia
            request.setAttribute("pay", pay);                                   //deklaracja dla pliku JSP </editor-fold>
          //ustawienie adresów
            UserMeta usrm =  usermDAO.readFirmN(ord.getIdUserMeta());           //pobranie danych adresu i firmy
            request.setAttribute("userm", usrm);                                //deklaracja dla pliku JSP
          //ustawienie użytkowiników
            UserMeta usr = usermDAO.readForWorker(ord.getIdUserMeta());         //pobranie danych użytkownika
            request.setAttribute("user", usr);                                  //deklaracja dla pliku JSP
          //ustawienie produktów
            List<OrderProd> orderp =  ordpDAO.read(ord.getId());                //pobranie listy łącznikowej produktów
            request.setAttribute("listOrderp", orderp);                         //deklaracja dla pliku JSP
            List<ProductMeta> prodm =  new ArrayList<>();                       //ustawienie listy produktów powiżanych z zamówionymi
            for (OrderProd ordp : orderp) {                                     //dla każdego produktu
                prodm.add(prodmWDAO.readProdOrd(ordp.getIdProd()));              //dodaj do listy odpowiadające dane produktu
            }
            request.setAttribute("listProdm", prodm);                           //deklaracja dla pliku JSP   </editor-fold>                        

            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/usersRnk/ViewRnk.jsp"); //przekierownie do pliku
            dispatcher.forward(request, response);                                      //przekazanie parametrów
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //uaktualnianie statusu zamówień pracownika
    private void updateViewWorker(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int action = 2; History his = null; boolean q = false;                              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userWDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==2)){                              //jeśli użytkownik posiada rangę i jest nią 2 (pracownik)
         
          //sprawdzanie czy id i ststus zostały przesłane                               <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id i ststus zostały przesłane">
            int id, stat;                                                               //deklaracja zmiennych
            try{ 
                id = Integer.parseInt(request.getParameter("id"));                      //pobranie id 
                stat  = Integer.parseInt(request.getParameter("status"));               //pobranie statusu 
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("worker");                                        //przekierowanie do strony pracownika
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
            Order order = ordWDAO.read(id);                                                          //pobranie danych zamówienia
            if((order.getIdStat()<=stat)){                                                          //jeśli status teraźniejszy jest mniejszy/równy od podanego
                String del_nr = null;                                                               //ustawienie numeru wysyłki
                if(stat>=6){ del_nr = Jsoup.parse(request.getParameter("delivery_nr")).text(); }    //gdy status jest większy lub równy 6("Wysłano") modyfikuj numer dostawy
                String update = formatLocalDate.format(LocalDateTime.now());                        //ustawienie czasu aktualizacji statusu
                String send = null;                                                                 //ustawienie czasu wysyłki
                if(stat==6){ send = formatLocalDate.format(LocalDateTime.now()); }                  //gdy status jest równy 6("Wysłano") ustal czas wysyłki
                if(stat==2){                                                                        //jeśli status jest równy 2(akceptacja) 
                    List<OrderProd> orderp =  ordpDAO.read(id);                                     //pobierz produkty wchodzące w skład zamówinia
                    for (OrderProd ordp : orderp) {                                                 //dla każdego produktu
                        prodmWDAO.updateQuant(ordp.getIdProd(), ordp.getQuantity());                 //zmień ilość produktu na stanie
                    }
                }
                Order ord = new Order(id, del_nr, stat, update, send, id_usr);                          //utwórz konstruktor zamówienia
                q = ordWDAO.updateForWorker(ord);                                                        //aktualizuj zamówienie
                if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano zamówienie.");}               //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                else{                                                                                   //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Wystąpił błąd podczas edycji zamówienia.");           //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                                             //zatrzymanie działania funkcji
                }
                  
              //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                beforeQuery = "UPDATE `order-o` SET `deliv_nr-o`='"+order.getDeliv_nr()
                        +"',`id_stat-o`="+order.getIdStat()+"" + ",`update-o'`="+order.getUpdate()
                        +"',`send-o`='"+order.getSend()+"' WHERE `id_order` = "+order.getId();              //ustawienie zapytania "przed zmianą" dla historii
                afterQuery = "UPDATE `order-o` SET `deliv_nr-o`='"+ord.getDeliv_nr()
                        +"',`id_stat-o`="+ord.getIdStat()+"" + ",`update-o`='"+ord.getUpdate()
                        +"',`send-o`='"+ord.getSend()+"' WHERE `id_order` = "+ord.getId();                  //ustawienie zapytania "po zmianie" dla historii
                if(ord.getDeliv_nr() == null ? order.getDeliv_nr() != null : !ord.getDeliv_nr().equals(order.getDeliv_nr())) {
                    if(ord.getIdStat()!=order.getIdStat()){ 
                        description = "Zmaina statusu i numeru przesyłki"; action = 2; 
                        modify="status: "+statDAO.getName(order.getIdStat())+" > "+statDAO.getName(ord.getIdStat())
                                +"<br>nr: "+ord.getDeliv_nr();
                    }    
                    else{                                                                                   //w przeciwnym wypadku ustalenie odpowiedniego opisu i akcji
                        description = "Modyfikacja numeru przesyłki"; action = 2; 
                        modify = order.getDeliv_nr()+" > "+ord.getDeliv_nr();
                    }
                }
                else {                                                                                      //w przeciwnym wypadku ustalenie odpowiedniego opisu i akcji
                    description = "Zmaina statusu"; action = 2;
                    modify="status: "+statDAO.getName(order.getIdStat())+" > "+statDAO.getName(ord.getIdStat()); 
                }                                          
                his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);           //ustalenie konstruktora
                q = histDAO.create(his);                                                                      //tworzenie historii
                if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                
                sess.removeAttribute("listOrder");              //usunięcie zmiennej sesyjnej do aktualizacji danych
            }
            else {
                sess.setAttribute("err_disc","Nie można zmienić statusu ponieważ jest on niższy niż wcześniej dodany."); //jeśli status jest nieprawidłowy
            }       
            response.sendRedirect("viewOrder?id="+id);                     //przekierowanie do poprzedniej
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony logowania
            return;                                                                     //przerwanie działania funkcji
        }
    } 

    
  //KOREKTOR 
    //strona główna korektora
    @SuppressWarnings("unchecked")
    private void indexCorrector(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji    
        
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę  ">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userCDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==3)){                              //jeśli użytkownik posiada rangę i jest nią 3 (korektor)
          //sprawdzenie czy przesłano numer zakładki                        <editor-fold defaultstate="collapsed" desc="sprawdzenie czy przesłano numer zakładki">
            int tabl = 0;                                                   //ustawienie początkowej wartości
            try{ tabl = Integer.parseInt(request.getParameter("t")); }      //pobranie zakładki
            catch(NumberFormatException x) {                                //sprawdzanie błędów 
                sess.setAttribute("err_disc","Brak poprawnych danych!");    //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                             //przekierowanie do strony głównej
                return;                                                     //przerwanie działania funkcji
            }                                                               //</editor-fold>
            //deklaracja zmiennych list                                         <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych list">   
            List<CatTag> listCat = new ArrayList<>();                           //stworzenie listy kategorii
            List<CatTag> listCats = new ArrayList<>();                          //stworzenie listy sprawdzającej kategorie
            List<CatTag> listTag = new ArrayList<>();                           //stworzenie listy tagów
            List<CatTag> listTags = new ArrayList<>();                          //stworzenie listy sprawdzającej tagi
            List<ProductMeta> listProd = new ArrayList<>();                     //stworzenie listy produktów       
            List<ProductMeta> listProds = new ArrayList<>();                    //stworzenie listy sprawdzającej produkty       
            List<Review> listRew = new ArrayList<>();                           //stworzenie listy recenzji 
            List<Review> listRews = new ArrayList<>();                          //stworzenie listy sprawdzającej recenzje </editor-fold>
            switch(tabl){                                                       //sprawdzenie do której zakładki przenieść użytkownika
                case 1: // = recenzje
                  //sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)  <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)">  
                    listRews = (List<Review>) sess.getAttribute("listRew");      //sprawdzanie listy recenzji
                    if(listRews==null){                                            //jeśli lista jest pusta
                      //przypisanie zmiennych sesyjnych                             <editor-fold defaultstate="collapsed" desc="przypisanie zmiennych sesyjnych">  
                        listProd = prodmCDAO.readForCensor();                       //pobranie listy produktów
                        sess.setAttribute("listProd", listProd);                    //zapisanie listy w sesji 
                        listRew = rewDAO.read();                                    //pobranie listy recenzji
                        sess.setAttribute("listRew", listRew);                      //zapisanie listy w sesji </editor-fold>
                    }                                                               //</editor-fold>
                break;
                case 2: // = produkty 
                  //sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)          <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)">  
                    listProds = (List<ProductMeta>) sess.getAttribute("listProd");      //sprawdzanie listy zamówień
                    if(listProds==null){                                                //jeśli lista jest pusta
                      //przypisanie zmiennych sesyjnych                             <editor-fold defaultstate="collapsed" desc="przypisanie zmiennych sesyjnych">  
                        listProd = prodmCDAO.readForCensor();                       //pobranie listy produktów
                        sess.setAttribute("listProd", listProd);                    //zapisanie listy w sesji </editor-fold>
                    }                                                               //</editor-fold>
                break;
                case 3: // = kategorie
                  //sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)          <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)">  
                    listTags = (List<CatTag>) sess.getAttribute("listTag");         //sprawdzanie listy tagów
                    if(listTags==null){                                             //jeśli lista jest pusta
                      //przypisanie zmiennych sesyjnych                              <editor-fold defaultstate="collapsed" desc="przypisanie zmiennych sesyjnych">  
                        listTag = ctCDAO.getTag();                                   //pobranie listy tagów
                        sess.setAttribute("listTag", listTag);                       //zapisanie listy w sesji </editor-fold>
                    }                                                                //</editor-fold>
                 break;
                case 4: // = tagi
                  //sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)          <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)">  
                    listCats = (List<CatTag>) sess.getAttribute("listCat");             //sprawdzanie listy kategorii
                    if(listCats==null){                                                //jeśli lista jest pusta
                      //przypisanie zmiennych sesyjnych                                 <editor-fold defaultstate="collapsed" desc="przypisanie zmiennych sesyjnych">  
                        listCat = ctCDAO.getCategory();                                 //pobranie listy kategorii
                        sess.setAttribute("listCat", listCat);                          //zapisanie listy w sesji </editor-fold>
                    }                                                                   //</editor-fold>
                 break;
            }
            
            //przekierownie do pliku
            request.setAttribute("t", tabl);                                                            //przekazanie znacznika zakładki do pliku JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/usersRnk/Corrector.jsp");  //przekierownie do pliku
            dispatcher.forward(request, response);                                                      //przekazanie parametrów
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //widoki recenzji, produktów, kategorii i tagów dla korektora
    private void viewForCorrector(HttpServletRequest request, HttpServletResponse response, int tab)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji    
        
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userCDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==3)){                              //jeśli użytkownik posiada rangę i jest nią 3 (korektor)
          //sprawdzanie czy id zostało przesłane                                        <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id zostało przesłane">
            int id;                                                                     //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("id")); }                   //pobranie id 
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("corrector?t=1");                                 //przekierowanie do strony korektora
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            switch(tab){
                case 1: //Recenzje                                                              <editor-fold defaultstate="collapsed" desc="Recenzje">
                  //ponieranie szczegółów recenzji 
                    Review rev = rewDAO.readOne(id);                                            //pobranie recenzji
                    request.setAttribute("rev", rev);                                           //deklaracja dla pliku JSP
                  //pobranie produktu zrecenzowanego  
                    ProductMeta productM = prodmCDAO.readOne(rev.getIdProd());                   //podranie produktu
                    request.setAttribute("productM", productM);                                 //deklaracja dla pliku JSP
                  //pobieranie imienia i nazwiska użytkownika
                    Order names = ordCDAO.readName(rev.getIdOrder());                            //pobranie imion
                    if(names.getName()!=null){ request.setAttribute("names", names); }          //jeśli imię jest w zamówieniu deklaracja dla pliku JSP
                    else {                                                                      //w przeciwnym wypadku
                        User names_usr = userCDAO.readUser(rev.getIdOrder());                    //pobierz imię z profilu użytkownika
                        request.setAttribute("names", names_usr);                               //deklaracja dla pliku JSP
                    }
                 break;                                                                         //</editor-fold>
                case 2: //Produkty                                                              <editor-fold defaultstate="collapsed" desc="Produkty">
                  //pobranie danych produktu  
                    ProductMeta prodM = prodmCDAO.readOne(id);                                   //pobranie danych
                    request.setAttribute("prod", prodM);                                        //deklaracja dla pliku JSP
                  //pobranie zmiennych tabel słownikowych dla danego produktu  
                    Fabric fbr = fabricDAO.read(prodM.getIdFabr());                             //pobranie materiału
                    request.setAttribute("fabr", fbr);                                          //deklaracja dla pliku JSP
                    Color col = colDAO.getName(prodM.getIdCol());                               //pobranie koloru
                    request.setAttribute("colo", col);                                          //deklaracja dla pliku JSP
                    Shape shp = shpDAO.read(prodM.getIdShap());                                 //pobranie kształtu
                    request.setAttribute("shap", shp);                                          //deklaracja dla pliku JSP
                 break;                                                                         //</editor-fold>
                case 3: //Kategorie                                                              <editor-fold defaultstate="collapsed" desc="Kategorie">
                  //pobranie wszsytkich danych z kategorii  
                    CatTag cat = ctCDAO.getDescCat(id);                                          //pobranie po identyfikatorze
                    request.setAttribute("cat", cat);                                           //deklaracja dla pliku JSP                                         
                 break;                                                                         //</editor-fold>
                case 4: //Tagi                                                                  <editor-fold defaultstate="collapsed" desc="Tagi">
                  //pobranie wszsytkich danych z tagów 
                    CatTag tag = ctCDAO.getDescTag(id);                                          //pobranie po identyfikatorze
                    request.setAttribute("tag", tag);                                           //deklaracja dla pliku JSP  
                 break;                                                                         //</editor-fold>
            }
           //deklaracja zmiennych globalnych wspólnych dla powyższych        <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych globalnych wspólnych dla powyższych">
            List<CatTag> listCat = ctCDAO.getCategory();                     //lista wszystkich kategorii
            request.setAttribute("listCat", listCat);                       //deklaracja dla pliku JSP 
            List<CatTag> listTag = ctCDAO.getTag();                          //lista wszystkich tagów
            request.setAttribute("listTag", listTag);                       //deklaracja dla pliku JSP 
            List<ProductMeta> listProd = prodmCDAO.readForCensor();          //lista wszystkich produktów
            request.setAttribute("listProd", listProd);                     //deklaracja dla pliku JSP 
            request.setAttribute("t", tab);                                 //ustawienie znacznika karty do pliku JSP </editor-fold>
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/usersRnk/ViewRnk.jsp"); //przekierownie do pliku
            dispatcher.forward(request, response);                                      //przekazanie parametrów
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //modyfikacja recenzji, produktu, kategori i tagu dla korektora
    private void updateViewCorrector(HttpServletRequest request, HttpServletResponse response, int tab)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int action = 2; History his = null; boolean q = false;                              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userCDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==3)){                              //jeśli użytkownik posiada rangę i jest nią 3 (korektor)
          //sprawdzanie czy id zostało przesłane                                        <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id zostało przesłane">
            int id;                                                                     //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("id")); }                    //pobranie id 
            
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");               //ustawianie zmiennej sesyjnej
                response.sendRedirect("corrector");                                     //przekierowanie do strony korektora
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
            switch(tab){
                case 1: //Recenzja                                                                      <editor-fold defaultstate="collapsed" desc="Recenzja">
                  //sprawdzanie czy dodatkowe dane zostały przesłane                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy dodatkowe dane zostały przesłane ">
                    String reply;                                                               //deklaracja zmiennych
                    try{ reply = Jsoup.parse(request.getParameter("answer")).text(); }          //pobranie odpowiedzi 
                    catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                        sess.setAttribute("err_disc","Brak wymaganych danych!");                //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    }                                                                           //</editor-fold>
                    
                    if(!reply.isEmpty()){                                                               //jeśli odpowiedź jest pusta
                        String  create = formatLocalDate.format(LocalDateTime.now());                   //pobranie czasu lokalnego
                        Review revi = rewDAO.readOne(id);                                               //pobranie danych recenzji do historii
                        Review rev = new Review(id, reply, create);                                     //utworzenie konstruktora
                        q = rewDAO.updateForCensor(rev);                                                    
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie ustawoino odpowiedź na recenzję.");}    //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                   //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas ustawiania odpowiedzi.");       //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                             //zatrzymanie działania funkcji
                        } 

                      //ustawianie historii modyfikacji                                                              <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "UPDATE `review` SET `reply` = '"+revi.getReply()
                                +"',`response` = '"+revi.getResponse()+"'"
                                +" WHERE `id_rev` = "+revi.getId();                                                  //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "UPDATE `review` SET `reply` = '"+
                                rev.getReply()+"',`response` = '"+rev.getResponse()+"'"
                                +" WHERE `id_rev` = "+rev.getId();                                                   //ustawienie zapytania "po zmianie" dla historii
                        description = "Modyfikacja odpowiedzi na recenzje";                                          //ustawienie opisu histori
                        modify = "odp: "+rev.getResponse();                                                          //ustawienie modyfikacji histori
                        his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);  //utworzenie konstruktora
                        q = histDAO.create(his);                                                                     //utworzenie historii   
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}//wyświetlenie komunikatu w przypadku błędu </editor-fold> 
                        
                        sess.removeAttribute("listRew");                                           //usunięcie zmiennej sesyjnej do aktualizacji danych
                        response.sendRedirect("viewReview?id="+id);
                    }
                    else{
                        sess.setAttribute("err_disc","Złe dane!");                              //ustawianie zmiennej sesyjnej
                        response.sendRedirect("viewReview?id="+id);                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    }
                 break;                                                                                              //</editor-fold> 
                case 2: //Produkt                                                                           <editor-fold defaultstate="collapsed" desc="Produkt">
                  //sprawdzanie czy dodatkowe dane zostały przesłane                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy dodatkowe dane zostały przesłane ">
                    String desc;                                                                //deklaracja zmiennych
                    try{ desc = Jsoup.parse(request.getParameter("description")).text(); }      //pobranie odpowiedzi 
                    catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                        sess.setAttribute("err_disc","Brak wymaganych danych!");                //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    }                                                                           //</editor-fold>

                    if(!desc.isEmpty()){                                                                   //jeśli odpowiedź jest pusta
                        ProductMeta produm = prodmCDAO.read(id);                                             //pobranie danych produktu do hstorii zdarzeń
                        q = prodmCDAO.updateDescription(id, desc);                                           //uaktualnienie opisu
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie ustawoino opis produktu.");}        //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                               //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas ustawiania opisu.");        //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                         //zatrzymanie działania funkcji
                        }

                      //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "UPDATE `product-meta` SET `description`='"
                                +produm.getDescription()+"'  WHERE `id_prod-m` = "+produm.getIdProd();               //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "UPDATE `product-meta` SET `description`='"
                                +desc+"'  WHERE `id_prod-m` = "+id;                                                  //ustawienie zapytania "po zmianie" dla historii
                        description = "Modyfikacja opisu produktu";                                                  //ustawienie opisu histori
                        modify = "opis: "+produm.getDescription()+" <br>> "+desc;                                    //ustawienie modyfikacji histori
                        his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);  //utworzenie konstruktora
                        q = histDAO.create(his);                                                                     //utworzenie historii   
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}//wyświetlenie komunikatu w przypadku błędu </editor-fold>
                        
                        sess.removeAttribute("listProd");                                           //usunięcie zmiennej sesyjnej do aktualizacji danych
                        response.sendRedirect("viewProduct?id="+id);                                                //przekierowanie do strony produktu
                    }
                    else{
                        sess.setAttribute("err_disc","Złe dane!");                              //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    }
                 break;                                                                                          //</editor-fold>
                case 3: //Kategoria                                                                             <editor-fold defaultstate="collapsed" desc="Kategoria">
                    //sprawdzanie czy dodatkowe dane zostały przesłane                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy dodatkowe dane zostały przesłane ">
                    String descr;                                                               //deklaracja zmiennych
                    try{ descr = Jsoup.parse(request.getParameter("description")).text(); }     //pobranie odpowiedzi 
                    catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                        sess.setAttribute("err_disc","Brak wymaganych danych!");                //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    }                                                                           //</editor-fold>

                    if(!descr.isEmpty()){                                                                       //jeśli odpowiedź jest pusta
                        CatTag catDesc = ctCDAO.getDescCat(id);                                                  //pobranie danych kategorii wymaganych do historii zdarzeń
                        q = ctCDAO.updateCatDesc(id, descr);                                                     //uaktualnianie kategorii
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie ustawoino opis kategorii.");}           //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{ //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas ustawiania opisu.");            //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                             //zatrzymanie działania funkcji
                        }
                          
                      //ustawianie historii modyfikacji                                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "UPDATE `category` SET `description-c`='"+catDesc.getDesc()+"' "
                            + " WHERE `id_cat` = "+catDesc.getId();                                                     //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "UPDATE `category` SET `description-c`='"+descr+"'  WHERE `id_cat` = "+id;         //ustawienie zapytania "po zmianie" dla historii
                        description = "Modyfikacja opisu kategori";                                                     //ustawienie opisu histori
                        modify = "opis: "+catDesc.getDesc()+" <br>> "+descr;                                            //ustawienie modyfikacji histori
                            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify); //utworzenie konstruktora
                        q = histDAO.create(his);                                                                        //utworzenie historii   
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}   //wyświetlenie komunikatu w przypadku błędu </editor-fold>
                    
                        sess.removeAttribute("listCat");                                           //usunięcie zmiennej sesyjnej do aktualizacji danych
                        response.sendRedirect("viewCategory?id="+id);                                    //przekierowanie do strony kategorii
                    }
                    else{
                        sess.setAttribute("err_disc","Złe dane!");                              //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    }
                 break;                                                                                          //</editor-fold>
                case 4: //Tag                                                                                    <editor-fold defaultstate="collapsed" desc="Tag">
                  //sprawdzanie czy dodatkowe dane zostały przesłane                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy dodatkowe dane zostały przesłane ">
                    String descri;                                                               //deklaracja zmiennych
                    try{ descri = Jsoup.parse(request.getParameter("description")).text(); }     //pobranie odpowiedzi 
                    catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                        sess.setAttribute("err_disc","Brak wymaganych danych!");                //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    }                                                                           //</editor-fold>

                    if(!descri.isEmpty()){                                                                          //jeśli odpowiedź jest pusta
                        CatTag tagDesc = ctCDAO.getDescTag(id);                                                      //pobranie danych tagu wymaganych do historii zdarzeń                                           
                        q = ctCDAO.updateTagDesc(id, descri);                                                        //uaktualnianie tagu
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie ustawoino opis tagu.");}                    //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas ustawiania opisu.");                //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        } 
                        
                      //ustawianie historii modyfikacji                                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "UPDATE `tag` SET `description-t`='"+tagDesc.getDesc()+"' "
                            + " WHERE `id_tag` = "+tagDesc.getId();                                                     //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "UPDATE `tag` SET `description-t`='"+descri+"' WHERE `id_tag` = "+id;              //ustawienie zapytania "po zmianie" dla historii
                        description = "Modyfikacja opisu kategori";                                                     //ustawienie opisu histori
                        modify = "opis: "+tagDesc.getDesc()+" <br>> "+descri;                                           //ustawienie modyfikacji histori
                        his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);     //utworzenie konstruktora
                        histDAO.create(his);                                                                            //utworzenie historii   
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}   //wyświetlenie komunikatu w przypadku błędu </editor-fold>
                    
                        sess.removeAttribute("listTag");                                           //usunięcie zmiennej sesyjnej do aktualizacji danych
                        response.sendRedirect("viewTag?id="+id);                                    //przekierowanie do strony tagu
                    }
                    else{
                        sess.setAttribute("err_disc","Złe dane!");                              //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                          //przekierowanie do strony głównej
                        return;                                                                 //przerwanie działania funkcji
                    }
                 break;                                                                                          //</editor-fold>
            }
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony logowania
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    
   //ZAOPATRZENIOWIEC 
    //strona główna zaopatrzeniowca
    @SuppressWarnings("unchecked")
    private void indexSupplier(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji 
        RequestDispatcher dispatcher;                                           //przekierowanie
        
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==4)){                              //jeśli użytkownik posiada rangę i jest nią 4 (zaopatrzeniowiec)
          //sprawdzenie czy przesłano numer zakładki                        <editor-fold defaultstate="collapsed" desc="sprawdzenie czy przesłano numer zakładki">
            int tab = 0;                                                   //ustawienie początkowej wartości
            try{ tab = Integer.parseInt(request.getParameter("t")); }      //pobranie zakładki
            catch(NumberFormatException x) {                                //sprawdzanie błędów 
                sess.setAttribute("err_disc","Brak poprawnych danych!");    //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                             //przekierowanie do strony głównej
                return;                                                     //przerwanie działania funkcji
            }                                                               //</editor-fold>
            switch(tab){
                case 1: //Produkty                                                       <editor-fold defaultstate="collapsed" desc="Produkty">  
                    //deklaracja zmiennych list                                           <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych list">   
                      List<CatTag> listCT = new ArrayList<>();                           //stworzenie listy kategorii
                      List<Product> listPrd = new ArrayList<>();                           //stworzenie listy kategorii
                      List<Product> listPrduct = new ArrayList<>();                           //stworzenie listy kategorii
                      List<ProductMeta> listProdM= new ArrayList<>();                           //stworzenie listy kategorii
                      List<Group> listGroup = new ArrayList<>();                           //stworzenie listy kategorii
                      List<Fabric> listFbri= new ArrayList<>();                           //stworzenie listy kategorii
                      List<Shape> listShpe= new ArrayList<>();                           //stworzenie listy kategorii
                      List<Color> listColo= new ArrayList<>();                           //stworzenie listy kategorii
                      List<CatTag> listCTs = new ArrayList<>();                           //stworzenie listy tagów</editor-fold>

                    //sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)          <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)">  
                      listPrduct = (List<Product>) sess.getAttribute("listProd");      //sprawdzanie listy zamówień
                      if(listPrduct==null){                                                //jeśli lista jest pusta
                        //przypisanie zmiennych sesyjnych                              <editor-fold defaultstate="collapsed" desc="przypisanie zmiennych sesyjnych">  
                          listCT = ctSDAO.read();                                       //pobranie listy menu
                          sess.setAttribute("listCT", listCT);                         //zapisanie listy w sesji
                          listPrd = prodSDAO.read();                                    //pobranie listy produktów
                          sess.setAttribute("listProd", listPrd);                      //zapisanie listy w sesji
                          listProdM = prodmSDAO.read();                                 //pobranie listy danych produktów
                          sess.setAttribute("listProdM", listProdM);                   //zapisanie listy w sesji
                          
                          int group = grSDAO.readHowMany();                             //pobranie ilości grup
                          sess.setAttribute("group", group);                           //zapisanie ilości grup w sesji
                          listGroup = grSDAO.read();                                    //pobranie listy grup
                          sess.setAttribute("listGroup", listGroup);                   //zapisanie listy w sesji

                          listFbri = fabricDAO.read();                                 //pobranie listy materiałów
                          sess.setAttribute("listFbr", listFbri);                      //zapisanie listy w sesji
                          listShpe = shpDAO.read();                                    //pobranie listy kształtów
                          sess.setAttribute("listShp", listShpe);                      //zapisanie listy w sesji
                          listColo = colDAO.read();                                    //pobranie listy kolorów
                          sess.setAttribute("listCol", listColo);                      //zapisanie listy w sesji
                          listCTs = ctSDAO.read();                                      //pobranie listy menu
                          sess.setAttribute("listCT", listCTs);                        //zapisanie listy w sesji </editor-fold>
                      }                                                                //</editor-fold>
                 break;                                                                //</editor-fold>
                case 2: //Słownikowe                                                        <editor-fold defaultstate="collapsed" desc="Słownikowe">   
                  //deklaracja zmiennych list                                         <editor-fold defaultstate="collapsed" desc="deklaracja zmiennych list">   
                    List<Fabric> listFbr = new ArrayList<>();                         //stworzenie listy materiałów
                    List<Color> listCol = new ArrayList<>();                          //stworzenie listy kolorów
                    List<Shape> listShp = new ArrayList<>();                          //stworzenie listy kształtów
                    List<Shape> listShape = new ArrayList<>();                        //stworzenie listy kształtów do sprawdzenia </editor-fold>
            
                  //sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)          <editor-fold defaultstate="collapsed" desc="sprawdzenie i przypisanie zmiennych sesyjnych (jeśli brak)">  
                    listShape = (List<Shape>) sess.getAttribute("listShp");             //sprawdzanie listy zamówień
                    if(listShape==null){                                                //jeśli lista jest pusta
                      //przypisanie zmiennych sesyjnych                             <editor-fold defaultstate="collapsed" desc="przypisanie zmiennych sesyjnych">  
                        listFbr = fabricDAO.read();                                 //pobranie listy materiałów
                        sess.setAttribute("listFbr", listFbr);                      //zapisanie listy w sesji 
                        listCol = colDAO.read();                                    //pobranie listy kolorów
                        sess.setAttribute("listCol", listCol);                      //zapisanie listy w sesji 
                        listShp = shpDAO.read();                                    //pobranie listy kształtów
                        sess.setAttribute("listShp", listShp);                      //zapisanie listy w sesji 
                        sess.setAttribute("t", tab);                                //zapisanie listy w sesji  </editor-fold>
                    }                                                                   //</editor-fold>
                    
                 break;  //</editor-fold>
            }
            
            request.setAttribute("t", tab);
            dispatcher = request.getRequestDispatcher("jsp/usersRnk/Supplier.jsp");    //przekierownie do pliku
            dispatcher.forward(request, response);                                                 //przekazanie parametrów
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //widok jednego produktu
    private void viewForSupplier(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji    
        
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==4)){                              //jeśli użytkownik posiada rangę i jest nią 4 (zaopatrzeniowiec)
          //sprawdzanie czy id zostało przesłane                                        <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id zostało przesłane">
            int id;                                                                     //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("id")); }                   //pobranie id 
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("supplier?t=1");                                  //przekierowanie do strony zaopatrzeniowca
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
            ProductMeta prodM = prodmSDAO.readOne(id);                       //pobranie danychproduktu po identyfiratorze 
            request.setAttribute("prodm", prodM);                           //deklaracja dla pliku JSP

            Fabric fbr = fabricDAO.read(prodM.getIdFabr());                 //pobranie materiału
            request.setAttribute("fabr", fbr);                              //deklaracja dla pliku JSP
            Color col = colDAO.getName(prodM.getIdCol());                   //pobranie koloru 
            request.setAttribute("colo", col);                              //deklaracja dla pliku JSP
            Shape shp = shpDAO.read(prodM.getIdShap());                     //pobranie kształtu 
            request.setAttribute("shap", shp);                              //deklaracja dla pliku JSP
            CatTag ct = ctSDAO.getNames(prodM.getIdCattag());                //pobranie menu
            request.setAttribute("ct", ct);                                 //deklaracja dla pliku JSP
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/usersRnk/ViewRnk.jsp"); //przekierownie do pliku
            dispatcher.forward(request, response);                                      //przekazanie parametrów
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //uaktualnienie produktu
    private void updateProductForSupplier(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int action = 2; History his = null; boolean q = false;                              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==4)){                              //jeśli użytkownik posiada rangę i jest nią 4 (zaopatrzeniowiec)
          //sprawdzanie czy podstawowe dane dla produktu zostały przesłane             <editor-fold defaultstate="collapsed" desc="sprawdzanie czy podstawowe dane dla produktu zostały przesłane ">
            int id, prd_ct, img = 0; String name_prd; Float price_prd;                 //deklaracja zmiennych
            try{ 
                id = Integer.parseInt(request.getParameter("id"));                     //pobranie id 
                name_prd = Jsoup.parse(request.getParameter("prod_name")).text();      //pobranie nazwy produktu 
                prd_ct = Integer.parseInt(request.getParameter("prod_ct"));            //pobranie lokalizacji w menu 
                price_prd = Float.parseFloat(request.getParameter("prod_prc"));        //pobranie ceny produktu
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("supplier?t=1");                                  //przekierowanie do strony zaopatrzeniowca
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
          //uaktowalnienie produktu                                                     <editor-fold defaultstate="collapsed" desc="uaktowalnienie produktu">
            ProductMeta Prod = prodmSDAO.readOne(id);                                    //pobranie danych do historii
            Product Prd = new Product(id, name_prd, img, prd_ct, price_prd);            //stworzenie konstruktora
            q = prodSDAO.update(Prd);                                                    //uaktualnienie podstawowych danych produktu
            if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano podstawowe "
                    + "dane produktu.");}                                               //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{ //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas edycji "
                    + "podstawowych danych produktu.");                                 //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                              //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                 //zatrzymanie działania funkcji
            }                                                                           //</editor-fold>  
            
          //sprawdzanie czy dane dla produktu zostały przesłane                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy dane dla produktu zostały przesłane ">
            int fab, sha, colr, qs, qm, disc; String desc, src;                             //deklaracja zmiennych
            Float hei, wid, len, hol, wei, dia;                                             //deklaracja zmiennych
            try{ 
                hei = Float.parseFloat(request.getParameter("prodm_height"));               //pobranie wysokości
                wid = Float.parseFloat(request.getParameter("prodm_width"));                //pobranie szerokości
                len = Float.parseFloat(request.getParameter("prodm_lenght"));               //pobranie długości
                hol = Float.parseFloat(request.getParameter("prodm_hole"));                 //pobranie wielkości otworu
                wei = Float.parseFloat(request.getParameter("prodm_weight"));               //pobranie wagi
                dia = Float.parseFloat(request.getParameter("prodm_diameter"));             //pobranie średnicy
                fab = Integer.parseInt(request.getParameter("prodm_fabr"));                 //pobranie identyfikatora materiału
                sha = Integer.parseInt(request.getParameter("prodm_shap"));                 //pobranie identyfikatora kształtu
                colr = Integer.parseInt(request.getParameter("prodm_color"));               //pobranie identyfiratora koloru
                desc = Jsoup.parse(request.getParameter("prodm_desc")).text();              //pobranie opisu
                qs = Integer.parseInt(request.getParameter("prod_state"));                  //pobranie ilości na stanie
                qm = Integer.parseInt(request.getParameter("prod_quant"));                  //pobranie ilości w opakowaniu
                disc = Integer.parseInt(request.getParameter("prodm_discount"));            //pobranie znizki
                src = Jsoup.parse(request.getParameter("prodm_src")).text();                //pobranie źródła
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak wymaganych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                          //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
          //uaktowalnienie danych produktu                                                  <editor-fold defaultstate="collapsed" desc="uaktowalnienie danych produktu">
            ProductMeta PrdM = new ProductMeta(id, hei, wid, len, hol, wei, dia, 
                        fab, sha, colr, desc, qs, qm, disc, src);                           //tworzenie konstruktora
            q = prodmSDAO.update(PrdM);                                                      //uaktualnianie danych szczegółowych produktu
            if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano produkt.");}          //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                           //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas edycji produktu.");     //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                  //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                     //zatrzymanie działania funkcji
            }                                                                               //</editor-fold> 

          //ustawianie historii modyfikacji                                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "UPDATE `product-meta`,`product` SET `name-p`='"+Prod.getName()
                    +"," + " `id_catag-p`="+Prod.getIdCattag()+", `price-p`="+Prod.getPrice()
                    +", `height`="+Prod.getHeight()+", "+ "`width`="+Prod.getWidth()
                    +", `lenght`="+Prod.getLenght()+", `hole`="+Prod.getHole()
                    +",`weight`="+Prod.getWeight()+", "+ "`diameter`="+Prod.getDiameter()
                    +", `id_fabr-m`="+Prod.getIdFabr()+", `id_shap-m`="+Prod.getIdShap()
                    + ", `id_col-m`="+Prod.getIdCol()+",`description`='"+Prod.getDescription()
                    +"', `quantity-state`="+Prod.getQuantityState()+", " + "`quantity-m`="
                    +Prod.getQuantity()+", `discount-m`="+Prod.getDiscount()+" `source`='"
                    +Prod.getSource()+"' WHERE `id_prod`=`id_prod-m` AND `id_prod`="+Prod.getId();          //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "UPDATE `product-meta`,`product` SET `name-p`='"+Prd.getName()
                    + " `id_catag-p`="+Prd.getIdCattag()+", `price-p`="+Prd.getPrice()
                    +", `height`="+PrdM.getHeight()+", " + "`width`="+PrdM.getWidth()
                    +", `lenght`="+PrdM.getLenght()+", `hole`="+PrdM.getHole()
                    +",`weight`="+PrdM.getWeight()+", "+ "`diameter`="+PrdM.getDiameter()
                    +", `id_fabr-m`="+PrdM.getIdFabr()+", `id_shap-m`="+PrdM.getIdShap()+", "
                    + "`id_col-m`="+PrdM.getIdCol()+",`description`='"
                    +PrdM.getDescription()+"', `quantity-state`="+PrdM.getQuantityState()+", "
                    + "`quantity-m`="+PrdM.getQuantity()+", `discount-m`="
                    +PrdM.getDiscount()+ " `source`='"+PrdM.getSource()
                    +"' WHERE `id_prod`=`id_prod-m` AND `id_prod`="+PrdM.getId();         //ustawienie zapytania "po zmianie" dla historii
            description = "Modyfikacja produktu";      
            //sprawdzanie co zosatło zmodyfikowane
            if(!Prod.getName().equals(Prd.getName())){ modify += "nazwa: "+Prod.getName()+" > "+Prd.getName()+"<br>";}
            if(Prod.getIdCattag()!=Prd.getIdCattag()) { modify += "menu: "+Prod.getIdCattag()+" > "+Prd.getIdCattag()+"<br>"; }
            if(Prod.getPrice()!=Prd.getPrice()) { modify += "cena: "+Prod.getPrice()+" > "+Prd.getPrice()+"<br>"; }
            if(Prod.getHeight()!=PrdM.getHeight()) { modify += "wys.: "+Prod.getHeight()+" > "+PrdM.getHeight()+"<br>"; }
            if(Prod.getWidth()!=PrdM.getWidth()) { modify += "szer.: "+Prod.getWidth()+" > "+PrdM.getWidth()+"<br>"; }
            if(Prod.getLenght()!=PrdM.getLenght()) { modify += "dł.: "+Prod.getLenght()+" > "+PrdM.getLenght()+"<br>"; }
            if(Prod.getHole()!=PrdM.getHole()) { modify += "otwór: "+Prod.getHole()+" > "+PrdM.getHole()+"<br>"; }
            if(Prod.getWeight()!=PrdM.getWeight()) { modify += "waga: "+Prod.getWeight()+" > "+PrdM.getWeight()+"<br>"; }
            if(Prod.getDiameter()!=PrdM.getDiameter()) { modify += "śr.: "+Prod.getDiameter()+" > "+PrdM.getDiameter()+"<br>"; }
            if(Prod.getIdFabr()!=PrdM.getIdFabr()) { modify += "materiał: "+fabricDAO.getName(Prod.getIdFabr())+" > "+fabricDAO.getName(PrdM.getIdFabr())+"<br>"; }
            if(Prod.getIdShap()!=PrdM.getIdShap()) { modify += "kształt: "+shpDAO.getName(Prod.getIdShap())+" > "+shpDAO.getName(PrdM.getIdShap())+"<br>"; }
            if(Prod.getIdCol()!=PrdM.getIdCol()) { modify += "kolor: "+colDAO.getNamec(Prod.getIdCol())+" > "+colDAO.getNamec(PrdM.getIdCol())+"<br>"; }
            if(!Prod.getDescription().equals(PrdM.getDescription())) { modify += "opis <br>"; }
            if(Prod.getQuantityState()!=PrdM.getQuantityState()) { modify += "stan: "+Prod.getQuantityState()+" > "+PrdM.getQuantityState()+"<br>"; }
            if(Prod.getQuantity()!=PrdM.getQuantity()) { modify += "ilość: "+Prod.getQuantity()+" > "+PrdM.getQuantity()+"<br>"; }
            if(Prod.getDiscount()!=PrdM.getDiscount()) { modify += "zniżka: "+Prod.getDiscount()+" > "+PrdM.getDiscount()+"<br>"; }
            if(!Prod.getSource().equals(PrdM.getSource())) { modify += "źródło: "+Prod.getSource()+" > "+PrdM.getSource()+"<br>"; }
             
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);         //ustalenie konstruktora
            histDAO.create(his);                                                                                //tworzenie historii
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}       //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
            
            sess.removeAttribute("listProd");                                   //wymuszenie aktualizacji produktów w sesji
            response.sendRedirect("viewOneProduct?id="+id);                     //przekierowanie
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony logowania
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //dodanie nowego produktu
    private void addProductForSupplier(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int action = 1; History his = null; boolean q = false;                              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==4)){                              //jeśli użytkownik posiada rangę i jest nią 4 (zaopatrzeniowiec)
          
          //sprawdzanie czy podstawowe dane dla produktu zostały przesłane             <editor-fold defaultstate="collapsed" desc="sprawdzanie czy podstawowe dane dla produktu zostały przesłane ">
            int CT, prd_ct, img=0; String name_prd; Float price_prd;                     //deklaracja zmiennych
            try{
                name_prd = Jsoup.parse(request.getParameter("prod_name")).text();      //pobranie nazwy produktu 
                CT = Integer.parseInt(request.getParameter("CT"));                     //pobranie kategorii
                prd_ct = Integer.parseInt(request.getParameter("s"+CT));               //pobranie lokalizacji w menu
                price_prd = Float.parseFloat(request.getParameter("prod_prc"));        //pobranie ceny produktu
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("supplier?t=1");                                  //przekierowanie do strony zaopatrzeniowca
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
          //dodanie produktu                                                    <editor-fold defaultstate="collapsed" desc="dodanie produktu">
            Product Prd = new Product(NULL, name_prd, img, prd_ct, price_prd);          //stworzenie konstruktora
            q = prodSDAO.create(Prd);                                                    //dodanie podstawowych danych produktu do bazy
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano podstawowe "
                    + "dane produktu.");}                                               //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                       //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania "
                    + "podstawowych danych produktu.");                                 //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                              //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                 //zatrzymanie działania funkcji
            } 
            int id = prodSDAO.readId(Prd);                                               //pobranie id nowo dodanego produktu </editor-fold>
            
          //sprawdzanie czy dane dla produktu zostały przesłane                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy dane dla produktu zostały przesłane ">
            int fab, sha, colr, qs, qm; String desc, src;                             //deklaracja zmiennych
                                                         //deklaracja zmiennych
            try{ 
                fab = Integer.parseInt(request.getParameter("prodm_fabr"));                 //pobranie identyfikatora materiału
                sha = Integer.parseInt(request.getParameter("prodm_shap"));                 //pobranie identyfikatora kształtu
                colr = Integer.parseInt(request.getParameter("prodm_color"));               //pobranie identyfiratora koloru
                desc = Jsoup.parse(request.getParameter("prodm_desc")).text();              //pobranie opisu
                qs = Integer.parseInt(request.getParameter("prod_state"));                  //pobranie ilości na stanie
                qm = Integer.parseInt(request.getParameter("prod_quant"));                  //pobranie ilości w opakowaniu
                src = Jsoup.parse(request.getParameter("prodm_src")).text();                //pobranie źródła
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak wymaganych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                          //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
          //sprawdzanie czy dane wielkości dla produktu zostały przesłane                      <editor-fold defaultstate="collapsed" desc="sprawdzanie czy dane wielkości dla produktu zostały przesłane ">
            int disc=0; Float hei=0.0f, wid=0.0f, len=0.0f, hol=0.0f, wei=0.0f, dia=0.0f;      //deklaracja zmiennych
           //pobranie wysokości
            try{ hei = Float.parseFloat(request.getParameter("prodm_height")); }          
            catch (NumberFormatException | NullPointerException ex){ }
           //pobranie szerokości
            try{ wid = Float.parseFloat(request.getParameter("prodm_width")); }           
            catch (NumberFormatException | NullPointerException ex){ }
           //pobranie długości
            try{ len = Float.parseFloat(request.getParameter("prodm_lenght")); }          
            catch (NumberFormatException | NullPointerException ex){ }
           //pobranie wielkości otworu
            try{ hol = Float.parseFloat(request.getParameter("prodm_hole")); }                
            catch (NumberFormatException | NullPointerException ex){ }
           //pobranie wagi
            try{ wei = Float.parseFloat(request.getParameter("prodm_weight")); }              
            catch (NumberFormatException | NullPointerException ex){ }
           //pobranie średnicy
            try{ dia = Float.parseFloat(request.getParameter("prodm_diameter")); }            
            catch (NumberFormatException | NullPointerException ex){ }
           //pobranie znizki
            try{ disc = Integer.parseInt(request.getParameter("prodm_discount")); }       
            catch (NumberFormatException | NullPointerException ex){ }                          //</editor-fold>
            
          //dodanie danych produktu                                                             <editor-fold defaultstate="collapsed" desc="dodanie danych produktu">
            String crt = formatLocalDate.format(LocalDateTime.now());                           //pobranie czasu stworzenia produktu
            ProductMeta PrdM = new ProductMeta(id, hei, wid, len, hol, wei, dia, 
                        fab, sha, colr, desc, qs, qm, disc, crt, src);                          //tworzenie konstruktora
            q = prodmSDAO.create(PrdM);                                                          //dodanie danych szczegółowych produktu
            if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano produkt.");}                 //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                               //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania produktu.");      //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                         //zatrzymanie działania funkcji
            }                                                                                   //</editor-fold>

          //ustawianie historii modyfikacji                                                                     <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "DELETE FROM `product` WHERE `id_prod` = "+id+";" 
                    + "DELETE FROM `product-meta` WHERE `id_prod-m` = "+PrdM.getIdProd();                       //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "INSERT INTO `product`(`name-p`, `images-p`, `id_catag-p`, `price-p`) "
                    + "VALUES ('"+Prd.getName()+"', "+Prd.getImages()+", "+Prd.getIdCattag()+", "
                    +Prd.getPrice()+");" + "INSERT INTO `product-meta`(`id_prod-m`, `height`, "
                    + "`width`, `lenght`, `hole`, `weight`, `diameter`, `id_fabr-m`, `id_shap-m`, "
                    + "`id_col-m`, `description`, `quantity-state`, `quantity-m`, ` vat-m`, `discount-m`, "
                    + "`cerate-m`, `restock-m`, `source`) VALUES ("+PrdM.getIdProd()+", "+PrdM.getHeight()
                    +", "+PrdM.getWidth()+", "+PrdM.getLenght()+", "+PrdM.getHole()+", "+PrdM.getWeight()
                    +", "+PrdM.getDiameter()+", "+PrdM.getIdFabr()+", "+PrdM.getIdShap()
                    +", "+PrdM.getIdCol()+", '"+PrdM.getDescription()+"', "+PrdM.getQuantityState()
                    +", "+PrdM.getQuantity()+",  "+PrdM.getVat()+", "+PrdM.getDiscount()
                    +", '"+PrdM.getCreate()+"', '"+PrdM.getRestock()+"', '"+PrdM.getSource()+"')";              //ustawienie zapytania "po zmianie" dla historii
            description = "Dodanie nowego produktu";                                                            //ustawienie opisu
            modify += "nazwa: "+Prd.getName()+"<br>cena: "+Prd.getPrice()+"<br> "
                    + "ilość: "+PrdM.getQuantity()+"<br> zniżka: "+PrdM.getDiscount()+"<br>"
                    + "źródło: "+PrdM.getSource()+"<br>";                                                       //ustawienie modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);         //ustalenie konstruktora
            histDAO.create(his);                                                                                //tworzenie historii
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}       //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
            
            sess.removeAttribute("listProd");                                   //wymuszenie aktualizacji produktów w sesji
            response.sendRedirect("supplier?t=1");                              //przekierowanie do strony poprzedniej jaką jest strona główna zaopatrzeniowca
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony logowania
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //usunięcie produktu (ilość na stanie stary -> 0)
    private void deleteProductForSupplier(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int action = 3; History his = null; boolean q = false;                              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==4)){                              //jeśli użytkownik posiada rangę i jest nią 4 (zaopatrzeniowiec)
          //sprawdzanie czy id zostało przesłane                                        <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id zostało przesłane">
            int id;                                                                     //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("id")); }                    //pobranie id 
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("supplier?t=1");                                  //przekierowanie do strony zaopatrzeniowca
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
          //usunięcie produktu                                                                      <editor-fold defaultstate="collapsed" desc="usunięcie produktu">
            int state = prodmSDAO.readState(id);                                                     //pobranie stanu dla historii zdażeń
            q = prodmSDAO.delete(id);                                                                //"usunięcie" produktu = zredukowanie stanu do 0
            if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto produkt.");}                   //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                                   //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania produktu.");           //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>

          //ustawianie historii modyfikacji                                                                     <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "UPDATE `product-meta` SET `quantity-state` = "+state 
                    +"WHERE `id_prod-m` = "+id;                                                                 //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "UPDATE `product-meta` SET `quantity-state` = 0" 
                    +"WHERE `id_prod-m` = "+id;                                                                 //ustawienie zapytania "po zmianie" dla historii
            description = "Usunięcie ilości produktów na stanie.";                                              //ustawienie opisu
            modify="ilość: "+state+" > 0";                                                                      //ustawienie modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);         //stworzenie konstruktora
            q = histDAO.create(his);                                                                            //dodanie historii zdarzeń
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}       //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
          
            sess.removeAttribute("listProd");                                   //wymuszenie aktualizacji produktów w sesji
            response.sendRedirect("supplier?t=1");                              //przekierowanie do strony poprzedniej jaką jest strona główna zaopatrzeniowca
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony logowania
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //odnowienie produktu (ilość na stanie 0 -> nowy)
    private void restockProductForSupplier(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException{
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";      //zapytanie przed, zapytanie po, opis, modyfikacja
        int action = 10; History his = null; boolean q = false;                              //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
       
      //sprawdzanie czy użytkownik jest zalogowany                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        int id_usr = 0;                                                     //ustawienie zmiennej
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==4)){                              //jeśli użytkownik posiada rangę i jest nią 4 (zaopatrzeniowiec)
          //sprawdzanie czy id zostało przesłane                                        <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id zostało przesłane">
            int id, state;                                                                     //deklaracja zmiennych
            try{ 
                id = Integer.parseInt(request.getParameter("id"));                     //pobranie id 
                state = Integer.parseInt(request.getParameter("state"));               //ilość na stanie
            }
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("supplier?t=1");                                  //przekierowanie do strony zaopatrzeniowca
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            
          //odnowienie produktu                                                                 <editor-fold defaultstate="collapsed" desc="odnowienie produktu">
            String restock = formatLocalDate.format(LocalDateTime.now());                       //ustawienie daty odnowienia
            ProductMeta prodm = prodmSDAO.read(id);                                              //pobranie danych do historii
            ProductMeta prdm = new ProductMeta(id, state, restock);                             //tworzenie konstruktora
            q = prodmSDAO.updateState(prdm);                                                     //"odnowienie" produktu = ustawienie nowiego stanu
            if(q){ sess.setAttribute("succ_disc","Pomyślnie odnowiono produkt.");}              //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
            else{                                                                               //w przeciwnym wypadku
                sess.setAttribute("err_disc","Wystąpił błąd podczas odnawiania produktu.");     //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                         //zatrzymanie działania funkcji
            }                                                                                   //</editor-fold> 

          //ustawianie historii modyfikacji                                                                     <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
            beforeQuery = "UPDATE `product-meta` SET `quantity-state` = "
                    +prodm.getQuantityState()+ ", `restock-m` = '"
                    +prodm.getRestock()+"' WHERE `id_prod-m` = "+prodm.getIdProd();                             //ustawienie zapytania "przed zmianą" dla historii
            afterQuery = "UPDATE `product-meta` SET `quantity-state` = "
                    +prdm.getQuantityState()+ ", `restock-m` = '"+prdm.getRestock()
                    +"' WHERE `id_prod-m` = "+prdm.getIdProd();                                                 //ustawienie zapytania "po zmianie" dla historii
            description = "Modyfikacja ilości produktu na stanie.";                                             //ustawienie opisu
            modify="ilość: 0 > "+state;                                                                         //ustawienie modyfikacji
            his = new History(NULL, id_usr, action, description, now, beforeQuery, afterQuery, modify);         //stworzenie konstruktora
            q = histDAO.create(his);                                                                            //dodanie historii zdarzeń
            if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas modyfiakcji historii zdarzeń.");}       //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
          
            sess.removeAttribute("listProd");                                   //wymuszenie aktualizacji produktów w sesji
            response.sendRedirect("supplier?t=1");                              //przekierowanie do strony poprzedniej jaką jest strona główna zaopatrzeniowca
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony logowania
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
   //TABELE SŁOWINIOWE 
    //edycja danych w tabelach słownikowych (zakładka 1 (t=1))
    private void updateDict(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        int idch = 0;                                                           //deklaracja zmiennej sprawdzającej czy danych już nie ma w bazie
       
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";    //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 2; History his = null; boolean q = false;              //użytkownik zmieniający, model historii, akcja
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
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&((rnk.getId_rank()==1)||(rnk.getId_rank()==4))){                                       //jeśli użytkownik posiada rangę i jest nią 1(administrator) lub 4(zaopatrzeniowiec)
          //sprawdzenie czy przesłano numer zakładki                        <editor-fold defaultstate="collapsed" desc="sprawdzenie czy przesłano numer zakładki">
            int tabl = 0;                                                   //ustawienie początkowej wartości
            try{ tabl = Integer.parseInt(request.getParameter("t")); }      //pobranie zakładki
            catch(NumberFormatException x) {                                //sprawdzanie błędów 
                sess.setAttribute("err_disc","Brak poprawnych danych!");    //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                             //przekierowanie do strony głównej
                return;                                                     //przerwanie działania funkcji
            }                                                               //</editor-fold>
          //sprawdzanie czy id i nazwa zostały przesłane                                    <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id i nazwa zostały przesłane">
                int id; String name;                                                        //deklaracja zmiennych
                try{ 
                    id = Integer.parseInt(request.getParameter("idMod"));                   //pobranie id     
                    name = Jsoup.parse(request.getParameter("name")).text();                //pobranie nazwy
                } 
                catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                    sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                    response.sendRedirect("login");                                         //przekierowanie do strony głównej
                    return;                                                                 //przerwanie działania funkcji
                }                                                                                           //</editor-fold>
            switch(tabl){                                                                                       //sprawdzenie do jakiego uaktualnienia przenieść użytkownika
                case 1: // = kolor
                  //sprawdzenie czy taki kolor istnieje w bazie danych                                                      <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taki kolor istnieje w bazie danych">
                    try{  
                        idch = colIDAO.getId(name);                                                                          //pobranie identyfikatora jeśli istnieje
                        if(idch!=0){                                                                                        //jeśli istnieje 
                            sess.setAttribute("err_disc","Nie można uaktualnić koloru! Podany kolor istnieje w bazie danych.");  //ustawianie zmiennej sesyjnej
                            if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                           //przekierowanie do strony administratora
                            if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                                //przekierowanie do strony zaopatrzeniowca
                            return;                                                                                         //przerwanie działania funkcji 
                        }
                    }
                    catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                  //uaktualnienie koloru                                                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie koloru">
                    Color Colb = colUDAO.getName(id);                                                         //pobranie wcześniejszego koloru na potzreby historii
                    Color Col = new Color(id, name);                                                         //model koloru
                    
                    q = colUDAO.update(Col);                                                                  //uaktualnienie koloru
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano kolor.");}                     //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{                                                                                   //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas edycji koloru.");               //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                             //zatrzymanie działania funkcji
                    }                                                                                       //</editor-fold> 
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "UPDATE `color` SET `name-cl`= '"+Colb.getName()
                            +"' WHERE `id_col` = "+Colb.getId();                                                //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "UPDATE `color` SET `name-cl`= '"+Col.getName()
                            +"' WHERE `id_col` = "+Col.getId();                                                 //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Modyfikacja koloru";  modify="nazwa: "+Colb.getName()+" > "+Col.getName();   //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Color> listCol = colUDAO.read();                                //pobieranie listy kolorów
                    sess.setAttribute("listColor", listCol);                            //zapisanie listyw sesji administrator
                    sess.setAttribute("listCol", listCol);                              //zapisanie listyw sesji zaopatrzeniowiec </editor-fold>
                 break;
                case 2: // = kształt
                  //sprawdzenie czy taki kształt istnieje w bazie danych                                                        <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taki kształt istnieje w bazie danych">
                    try{  
                        idch = shpIDAO.getId(name);                                                                              //pobranie identyfikatora jeśli istnieje
                        if(idch!=0){                                                                                            //jeśli istnieje
                            sess.setAttribute("err_disc","Nie można uaktualnić kształtu! Podany kształt istnieje w bazie danych.");  //ustawianie zmiennej sesyjnej
                            if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                               //przekierowanie do strony administratora
                            if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                                    //przekierowanie do strony zaopatrzeniowca
                            return;                                                                                             //przerwanie działania funkcji 
                        }
                    }
                    catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                  //uaktualnienie kształtu                                                                      <editor-fold defaultstate="collapsed" desc="uaktualnienie kształtu">
                    Shape Shpb = shpUDAO.read(id);                                                               //pobranie wcześniejszego kształtu na potrzeby historii                                                   
                    Shape Shp =new Shape(id, name);                                                             //model kształtu
                    
                    q = shpUDAO.update(Shp);                                                                     //uaktualnienie kształtu
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano kształt.");}                      //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{                                                                                       //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas edycji kształtu.");                 //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                                 //zatrzymanie działania funkcji
                    }                                                                                           //</editor-fold> 
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "UPDATE `shape` SET `name-sh`= '"+Shpb.getName()
                            +"' WHERE `id_shap` = "+Shpb.getId();                                                //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "UPDATE `shape` SET `name-sh`= '"+Shp.getName()
                            +"' WHERE `id_shap` = "+Shp.getId();                                                 //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Modyfikacja kształtu"; modify+="nazwa: "+Shpb.getName()+" > "+Shp.getName();  //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Shape> listShp = shpUDAO.read();                                //pobieranie listy kształtów
                    sess.setAttribute("listShpe", listShp);                             //zapisanie listy w sesji administrator
                    sess.setAttribute("listShp", listShp);                             //zapisanie listy w sesji zaopatrzeniowiec </editor-fold>
                 break;
                case 3: // = materiał
                  //sprawdzenie czy taki materiał istnieje w bazie danych                                                           <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taki materiał istnieje w bazie danych">
                    try{  
                        idch = fabricUDAO.getId(name);                                                                               //pobranie identyfikatora jeśli istnieje
                        if(idch!=0){                                                                                                //jeśli istnieje 
                            sess.setAttribute("err_disc","Nie można uaktualnić materiału! Podany materiał istnieje w bazie danych.");    //ustawianie zmiennej sesyjnej
                            if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                                   //przekierowanie do strony administratora
                            if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                                        //przekierowanie do strony zaopatrzeniowca
                            return;                                                                                                 //przerwanie działania funkcji 
                        }
                    }
                    catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                  //uaktualnienie materiału                                                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie materiału">
                    Fabric Fabb = fabricUDAO.read(id);                                                           //pobranie wcześniejszego materiału na potzreby historii
                    Fabric Fab = new Fabric(id, name);                                                          //model materiału
                    
                    q = fabricUDAO.update(Fab);                                                                  //uaktualnienie materiału
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano materiał.");}                     //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{                                                                                       //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas edycji materiału.");                //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                                 //zatrzymanie działania funkcji
                    }                                                                                           //</editor-fold>  
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "UPDATE `fabric` SET `name-fb`= '"+Fabb.getName()
                            +"' WHERE `id_fabr` = "+Fabb.getId();                                               //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "UPDATE `fabric` SET `name-fb`= '"+Fab.getName()
                            +"' WHERE `id_fabr` = "+Fab.getId();                                                //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Modyfikacja materiału"; modify="nazwa: "+Fabb.getName()+" > "+Fab.getName(); //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                         <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Fabric> listFbr = fabricUDAO.read();                                //pobieranie listy materiałów
                    sess.setAttribute("listFbric", listFbr);                                //zapisanie listy w sesji administrator
                    sess.setAttribute("listFbr", listFbr);                                //zapisanie listy w sesji zaopatrzeniowiec </editor-fold>
                 break;
                case 4: //= dostawa
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //tylko dla administaratora
                      //sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect                       <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect">
                        float price_dv;                                                                             //deklaracja zmiennych
                        try{ price_dv = Float.parseFloat(request.getParameter("price")); }                          //pobranie ceny dostawy
                        catch (NumberFormatException | NullPointerException ex){                                    //sprawdzanie błędów
                            String url = request.getHeader("referer");                                              //sprawdzanie wcześniejszej strony
                            if((url!=null)){                                                                        //jeśli strona poprzednia nie jest pusta
                                sess.setAttribute("err_disc","Brak poprawnych danych!");                            //ustawianie zmiennej sesyjnej
                                response.sendRedirect("register");                                                  //przekierowanie do formularza rejestracji
                                return;                                                                             //przerwanie działania funkcji 
                            }
                            else{                                                                                   //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Brak dostępu!");                                      //ustawianie zmiennej sesyjnej
                                response.sendRedirect("home");                                                      //przekierowanie do strony głównej
                                return;                                                                             //przerwanie działania funkcji
                            }
                        }                                                                                           //</editor-fold>
                      //sprawdzenie czy taka dostawa istnieje w bazie danych                                                      <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taka dostawa istnieje w bazie danych">
                        try{  
                            idch = delIDAO.getId(name);                                                                              //pobranie identyfikatora jeśli istnieje
                            if(idch!=0){                                                                                            //jeśli istnieje 
                                sess.setAttribute("err_disc","Nie można uaktualnić dostawy! Podana dostawa istnieje w bazie danych.");   //ustawianie zmiennej sesyjnej
                                response.sendRedirect("administrator?t=1");                                                         //przekierowanie do strony administratora
                                return;                                                                                             //przerwanie działania funkcji 
                            }
                        }
                        catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                      //uaktualnienie dostawy                                                                      <editor-fold defaultstate="collapsed" desc="uaktualnienie dostawy">
                        Delivery Delivb = delUDAO.read(id);                                                          //pobranie wcześniejszej dostawy na potrzeby historii                                                   
                        Delivery Deliv = new Delivery(id, name, price_dv);                                          //model dostawy

                        q = delUDAO.update(Deliv);                                                                   //uaktualnienie dostawy
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano dostawę.");}                      //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas edycji dostawy.");                  //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        }                                                                                           //</editor-fold> 
                      //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "UPDATE `delivery` SET `name-d`= '"+Delivb.getName()+"', `price-d`= "
                                +Delivb.getPrice()+" WHERE `id_deliv` = "+Delivb.getId();                           //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "UPDATE `delivery` SET `name-d`= '"+Deliv.getName()+"', `price-d`= "
                                +Deliv.getPrice()+" WHERE `id_deliv` = "+Deliv.getId();                             //ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Modyfikacja dostawy";                                                        //ustawienie opisu 
                        if(Delivb.getName().equals(Deliv.getName())){ modify+="nazwa: "+Delivb.getName()+" > "+Deliv.getName();}//ustawienie tekstu modyfikacji
                        if(Delivb.getPrice()!=Deliv.getPrice()){ modify+="cena: "+Delivb.getPrice()+" > "+Deliv.getPrice();}//ustawienie tekstu modyfikacji
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                        q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Delivery> listDel = delUDAO.read();                             //pobieranie listy dostaw                
                        sess.setAttribute("listDelivery", listDel);                         //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    }
                 break;
                case 5: // = płatność
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //tylko dla administaratora
                      //sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect                       <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect">
                        String form_p;                                                                              //deklaracja zmiennych
                        try{ form_p = Jsoup.parse(request.getParameter("category")).text(); }                       //pobranie kategori płatności
                        catch (NumberFormatException | NullPointerException ex){                                    //sprawdzanie błędów
                            String url = request.getHeader("referer");                                              //sprawdzanie wcześniejszej strony
                            if((url!=null)){                                                                        //jeśli strona poprzednia nie jest pusta
                                sess.setAttribute("err_disc","Brak poprawnych danych!");                            //ustawianie zmiennej sesyjnej
                                response.sendRedirect("register");                                                  //przekierowanie do formularza rejestracji
                                return;                                                                             //przerwanie działania funkcji 
                            }
                            else{                                                                                   //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Brak dostępu!");                                      //ustawianie zmiennej sesyjnej
                                response.sendRedirect("home");                                                      //przekierowanie do strony głównej
                                return;                                                                             //przerwanie działania funkcji
                            }
                        }                                                                                           //</editor-fold>
                      //sprawdzenie czy taka płatność istnieje w bazie danych                                                      <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taka płatność istnieje w bazie danych">
                        try{  
                            idch = payIDAO.getId(name);                                                                                  //pobranie identyfikatora jeśli istnieje
                            if(idch!=0){                                                                                                //jeśli istnieje
                                sess.setAttribute("err_disc","Nie można uaktualnić płatności! Podana płatność istnieje w bazie danych.");    //ustawianie zmiennej sesyjnej
                                response.sendRedirect("administrator?t=1");                                                             //przekierowanie do strony administratora
                                return;                                                                                                 //przerwanie działania funkcji 
                            }
                        }
                        catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                      //uaktualnienie płatności                                                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie płatności">
                        Payment Payb = payUDAO.read(id);                                                             //pobranie wcześniejszj płatności na potzreby historii                                                   
                        Payment Pay = new Payment(id, name, form_p);                                                //model płatności

                        q = payUDAO.update(Pay);                                                                     //uaktualnienie płatności
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano płatność.");}                     //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas edycji płatności.");                //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        }                                                                                           //</editor-fold> 
                      //ustawianie historii modyfikacji                                                                     <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "UPDATE `payment` SET `name-py`= '"+Payb.getName()+"', `cat-py`= '"
                                +Payb.getCategory()+"'"+ " WHERE `id_pay` = "+Payb.getId();                                 //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "UPDATE `payment` SET `name-py`= '"+Pay.getName()+"', `cat-py`= '"
                                +Pay.getCategory()+"'"+ " WHERE `id_pay` = "+Pay.getId();                                   //ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Modyfikacja płatności";                                                              //ustawienie opisu 
                        if(Payb.getName().equals(Pay.getName())){ modify+="nazwa: "+Payb.getName()+" > "+Pay.getName();}
                        if(Payb.getCategory().equals(Pay.getCategory())){ modify+="kategoria: "+Payb.getCategory()+" > "+Pay.getCategory();}//ustawienie tekstu modyfikacji
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                        q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Payment> listPay = payUDAO.read();                              //pobieranie listy płatności
                        sess.setAttribute("listPayment", listPay);                          //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    }
                 break;
                case 6: // = kod promocyjny
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //tylko dla administaratora
                      //sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect                       <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect">
                        boolean active;                                                                             //deklaracja zmiennych
                        try{ active = Boolean.parseBoolean(request.getParameter("active")); }                       //pobranie znacznika aktywności
                        catch (NumberFormatException | NullPointerException ex){                                    //sprawdzanie błędów
                            String url = request.getHeader("referer");                                              //sprawdzanie wcześniejszej strony
                            if((url!=null)){                                                                        //jeśli strona poprzednia nie jest pusta
                                sess.setAttribute("err_disc","Brak poprawnych danych!");                            //ustawianie zmiennej sesyjnej
                                response.sendRedirect("register");                                                  //przekierowanie do formularza rejestracji
                                return;                                                                             //przerwanie działania funkcji 
                            }
                            else{                                                                                   //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Brak dostępu!");                                      //ustawianie zmiennej sesyjnej
                                response.sendRedirect("home");                                                      //przekierowanie do strony głównej
                                return;                                                                             //przerwanie działania funkcji
                            }
                        }                                                                                           //</editor-fold>
                      //sprawdzenie czy taki kod promocyjny istnieje w bazie danych                                                <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taki kod promocyjny istnieje w bazie danych">
                        try{  
                            idch = discIDAO.getId(name);                                                                                 //pobranie identyfikatora jeśli istnieje
                            if((idch!=0)&&(idch!=id)){                                                                                                //jeśli istnieje
                                sess.setAttribute("err_disc","Nie można uaktualnić kodu promocyjnego! Podany kod istnieje w bazie danych."); //ustawianie zmiennej sesyjnej
                                response.sendRedirect("administrator?t=1");                                                             //przekierowanie do strony administratora
                                return;                                                                                                 //przerwanie działania funkcji 
                            }
                        }
                        catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                      //uaktualnienie płatności                                                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie płatności">
                        Discount Discb = discUDAO.read(id);                                                           //pobranie wcześniejszego kodu promocyjnego na potrzeby historii                                                   
                        Discount Disc = new Discount(id, name, active);                                              //model kodu promocyjnego

                        q = discUDAO.update(Disc);                                                                   //uaktualnienie kodu promocyjnego
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie edytowano kod promocyjny.");}               //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas edycji kodu promocyjnego.");        //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        }                                                                                           //</editor-fold>
                      //ustawianie historii modyfikacji                                                                     <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "UPDATE `discount` SET `name-dc`= '"+Discb.getName()+"', `active`= "
                                +Discb.isActive()+" WHERE `id_disc` = "+Discb.getId();                                      //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "UPDATE `discount` SET `name-dc`= '"+Discb.getName()+"', `active`= "
                                +Disc.isActive()+" WHERE `id_disc` = "+Disc.getId();                                        //ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Modyfikacja zniżki";                                                                 //ustawienie opisu 
                        if(Discb.getName().equals(Disc.getName())){ modify+="nazwa: "+Discb.getName()+" > "+Disc.getName();}
                        if(Discb.isActive()!=Disc.isActive()){ modify+="opis: "+Discb.isActive()+" > "+Disc.isActive();}    //ustawienie tekstu modyfikacji
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                        q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Discount> listDisc = discIDAO.read();                           //pobieranie listy kodów promocyjnych
                        sess.setAttribute("listDiscount", listDisc);                        //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    }
                 break;
            }
            //przekierowanie użytkowników                                                     <editor-fold defaultstate="collapsed" desc="przekierowanie użytkowników">
              if(rnk.getId_rank()==1){                                                        //jeśli jest to administrator
                  response.sendRedirect("administrator?t=1");                                 //przeierowanie do odpowiedniego pliku
              }
              if(rnk.getId_rank()==4){                                                        //jeśli jest to zaopatrzeniowiec
                  response.sendRedirect("supplier?t=2");                                      //przekierownie do zaopatrzeniowca
              }                                                                               //</editor-fold>
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 1)
            sess.setAttribute("err_disc","Brak poprawnych danych!x");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    } 
    
    //dodawanie danych w tabelach słownikowych (zakładka 1 (t=1))
    private void insertDict(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";    //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 1; History his = null; boolean q = false;              //użytkownik zmieniający, model historii, akcja
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
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&(rnk.getId_rank()==1)||(rnk.getId_rank()==4)){                                       //jeśli użytkownik posiada rangę i jest nią 1(administrator) lub 4(zaopatrzeniowiec)
            int idch = 0;                                                               //deklaracja zmiennej sprawdzającej czy danych już nie ma w bazie
          //sprawdzenie czy przesłano numer zakładki                        <editor-fold defaultstate="collapsed" desc="sprawdzenie czy przesłano numer zakładki">
            int tabl = 0;                                                   //ustawienie początkowej wartości
            try{ tabl = Integer.parseInt(request.getParameter("t")); }      //pobranie zakładki
            catch(NumberFormatException x) {                                //sprawdzanie błędów 
                sess.setAttribute("err_disc","Brak poprawnych danych!");    //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                             //przekierowanie do strony głównej
                return;                                                     //przerwanie działania funkcji
            }                                                               //</editor-fold>
          //sprawdzanie czy nazwa została przesłana                                     <editor-fold defaultstate="collapsed" desc="sprawdzanie czy nazwa została przesłana">
            String name;                                                                //deklaracja zmiennej
            try{ name = Jsoup.parse(request.getParameter("name")).text(); }            //pobranie nazwy
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                                         //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                           //</editor-fold>
            switch(tabl){                                                                                       //sprawdzenie do jakiego uaktualnienia przenieść użytkownika
                case 1: // = kolor
                  //sprawdzenie czy taki kolor istnieje w bazie danych                                                      <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taki kolor istnieje w bazie danych">
                    try{  
                        idch = colIDAO.getId(name);                                                                          //pobranie identyfikatora jeśli istnieje
                        if(idch!=0){                                                                                        //jeśli istnieje 
                            sess.setAttribute("err_disc","Nie można dodać koloru! Podany kolor istnieje w bazie danych.");  //ustawianie zmiennej sesyjnej
                            if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                           //przekierowanie do strony administratora
                            if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                                //przekierowanie do strony zaopatrzeniowca
                            return;                                                                                         //przerwanie działania funkcji 
                        }
                    }
                    catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                  //dodanie koloru                                                                              <editor-fold defaultstate="collapsed" desc="dodanie koloru">
                    q = colIDAO.create(name);                                                                    //dodanie koloru
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano kolor.");}                           //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{                                                                                       //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania koloru.");                //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                                 //zatrzymanie działania funkcji
                    }
                    int id_c = colIDAO.getId(name);                                                              //pobranie identyfikatora koloru na potrzeby historii </editor-fold> 
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "DELETE FROM `color` WHERE `id_col` = "+id_c;                                 //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "INSERT INTO `color`(`name-cl`) VALUES ('"+name+"')";                          //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Dodanie nowego koloru";  modify="nazwa: "+name;                              //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Color> listCol = colIDAO.read();                                //pobieranie listy kolorów
                    sess.setAttribute("listColor", listCol);                            //zapisanie listyw sesji </editor-fold>
                break;
                case 2:  // = kształt
                  //sprawdzenie czy taki kształt istnieje w bazie danych                                                        <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taki kształt istnieje w bazie danych">
                    try{  
                        idch = shpIDAO.getId(name);                                                                              //pobranie identyfikatora jeśli istnieje
                        if(idch!=0){                                                                                            //jeśli istnieje
                            sess.setAttribute("err_disc","Nie można dodać kształtu! Podany kształt istnieje w bazie danych.");  //ustawianie zmiennej sesyjnej
                            if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                               //przekierowanie do strony administratora
                            if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                                    //przekierowanie do strony zaopatrzeniowca
                            return;                                                                                             //przerwanie działania funkcji 
                        }
                    }
                    catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                  //dodanie kształtu                                                                            <editor-fold defaultstate="collapsed" desc="dodanie kształtu">
                    Shape Shp =new Shape(NULL, name);                                                           //model kształtu
                    
                    q = shpIDAO.create(Shp);                                                                     //dodanie kształtu
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano kształt.");}                         //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{                                                                                       //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania kształtu.");              //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                                 //zatrzymanie działania funkcji
                    } 
                    int id_shp = shpIDAO.getId(name);                                                            //pobranie identyfikatora kształtu na potrzeby historii </editor-fold> 
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "DELETE FROM `shape` WHERE `id_shap` = "+id_shp;                              //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "INSERT INTO `shape`(`name-sh`) VALUES ('"+name+"')";                          //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Dodanie nowego kształtu"; modify+="nazwa: "+name;                            //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Shape> listShp = shpIDAO.read();                                //pobieranie listy kształtów
                    sess.setAttribute("listShpe", listShp);                             //zapisanie listy w sesji  </editor-fold>
                break;
                case 3: // = materiał
                  //sprawdzenie czy taki materiał istnieje w bazie danych                                                           <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taki materiał istnieje w bazie danych">
                    try{  
                        idch = fabricUDAO.getId(name);                                                                               //pobranie identyfikatora jeśli istnieje
                        if(idch!=0){                                                                                                //jeśli istnieje 
                            sess.setAttribute("err_disc","Nie można dodać materiału! Podany materiał istnieje w bazie danych.");    //ustawianie zmiennej sesyjnej
                            if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                                   //przekierowanie do strony administratora
                            if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                                        //przekierowanie do strony zaopatrzeniowca
                            return;                                                                                                 //przerwanie działania funkcji 
                        }
                    }
                    catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                  //dodanie materiału                                                                          <editor-fold defaultstate="collapsed" desc="dodanie materiału">
                    Fabric Fab = new Fabric(NULL, name);                                                        //model materiału
                    
                    q = fabricIDAO.create(Fab);                                                                  //dodawanie materiału
                    if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano materiał.");}                        //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                    else{                                                                                       //w przeciwnym wypadku
                        sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania materiału.");             //wyświetlenie odpowiedniego komunikatu o błędzie
                        String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                        response.sendRedirect(url);
                        return;                                                                                 //zatrzymanie działania funkcji
                    } 
                    int id_fabr = fabricIDAO.getId(name);                                                        //pobranie identyfikatora materiału na potrzeby historii </editor-fold> 
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "DELETE FROM `fabric` WHERE `id_fabr` = "+id_fabr;                            //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "INSERT INTO `fabric`(`name-fb`) VALUES ('"+name+"')";                         //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Dodanie nowego materiału"; modify="nazwa: "+name;                            //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                         <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Fabric> listFbr = fabricIDAO.read();                                //pobieranie listy materiałów
                    sess.setAttribute("listFbric", listFbr);                                //zapisanie listy w sesji </editor-fold>
                break;
                case 4: //= dostawa
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                                  //tylko dla administaratora
                      //sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect                       <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect">
                        float price_dv;                                                                             //deklaracja zmiennych
                        try{ price_dv = Float.parseFloat(request.getParameter("price")); }                          //pobranie ceny dostawy
                        catch (NumberFormatException | NullPointerException ex){                                    //sprawdzanie błędów
                            String url = request.getHeader("referer");                                              //sprawdzanie wcześniejszej strony
                            if((url!=null)){                                                                        //jeśli strona poprzednia nie jest pusta
                                sess.setAttribute("err_disc","Brak poprawnych danych!");                            //ustawianie zmiennej sesyjnej
                                response.sendRedirect("register");                                                  //przekierowanie do formularza rejestracji
                                return;                                                                             //przerwanie działania funkcji 
                            }
                            else{                                                                                   //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Brak dostępu!");                                      //ustawianie zmiennej sesyjnej
                                response.sendRedirect("home");                                                      //przekierowanie do strony głównej
                                return;                                                                             //przerwanie działania funkcji
                            }
                        }                                                                                           //</editor-fold>
                      //sprawdzenie czy taka dostawa istnieje w bazie danych                                                      <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taka dostawa istnieje w bazie danych">
                        try{  
                            idch = delIDAO.getId(name);                                                                              //pobranie identyfikatora jeśli istnieje
                            if(idch!=0){                                                                                            //jeśli istnieje 
                                sess.setAttribute("err_disc","Nie można dodać dostawy! Podana dostawa istnieje w bazie danych.");   //ustawianie zmiennej sesyjnej
                                response.sendRedirect("administrator?t=1");                                                         //przekierowanie do strony administratora
                                return;                                                                                             //przerwanie działania funkcji 
                            }
                        }
                        catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                      //dodanie dostawy                                                                             <editor-fold defaultstate="collapsed" desc="dodanie dostawy">                                                   
                        Delivery Deliv = new Delivery(NULL, name, price_dv);                                        //model dostawy

                        q = delIDAO.create(Deliv);                                                                   //dodanie dostawy
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano dostawę.");}                         //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania dostawy.");               //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        } 
                        int id_del = delIDAO.getId(name);                                                            //pobranie wcześniejszej dostawy na potrzeby historii </editor-fold> 
                      //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "DELETE FROM `delivery` WHERE `id_deliv` = "+id_del;                           //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "INSERT INTO `delivery`(`name-d`, `price-d`) VALUES ('"+name+"', "+price_dv+")";//ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Dodanie nowej dostawy"; modify="nazwa: '"+name+"',<br>cena: "+price_dv;       //ustawienie opisu i ustawienie tekstu modyfikacji
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                        q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Delivery> listDel = delIDAO.read();                             //pobieranie listy dostaw                
                        sess.setAttribute("listDelivery", listDel);                         //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    }
                break;
                case 5: // = płatność
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                                  //tylko dla administaratora
                      //sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect                       <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect">
                        String form_p;                                                                              //deklaracja zmiennych
                        try{ form_p = Jsoup.parse(request.getParameter("category")).text(); }                       //pobranie kategori płatności
                        catch (NumberFormatException | NullPointerException ex){                                    //sprawdzanie błędów
                            String url = request.getHeader("referer");                                              //sprawdzanie wcześniejszej strony
                            if((url!=null)){                                                                        //jeśli strona poprzednia nie jest pusta
                                sess.setAttribute("err_disc","Brak poprawnych danych!");                            //ustawianie zmiennej sesyjnej
                                response.sendRedirect("register");                                                  //przekierowanie do formularza rejestracji
                                return;                                                                             //przerwanie działania funkcji 
                            }
                            else{                                                                                   //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Brak dostępu!");                                      //ustawianie zmiennej sesyjnej
                                response.sendRedirect("home");                                                      //przekierowanie do strony głównej
                                return;                                                                             //przerwanie działania funkcji
                            }
                        }                                                                                           //</editor-fold>
                      //sprawdzenie czy taka płatność istnieje w bazie danych                                                      <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taka płatność istnieje w bazie danych">
                        try{  
                            idch = payIDAO.getId(name);                                                                                  //pobranie identyfikatora jeśli istnieje
                            if(idch!=0){                                                                                                //jeśli istnieje
                                sess.setAttribute("err_disc","Nie można dodać płatności! Podana płatność istnieje w bazie danych.");    //ustawianie zmiennej sesyjnej
                                response.sendRedirect("administrator?t=1");                                                             //przekierowanie do strony administratora
                                return;                                                                                                 //przerwanie działania funkcji 
                            }
                        }
                        catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                      //dodanie płatności                                                                           <editor-fold defaultstate="collapsed" desc="dodanie płatności">                                                  
                        Payment Pay = new Payment(NULL, name, form_p);                                              //model płatności

                        q = payIDAO.create(Pay);                                                                     //dodanie płatności
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano płatność.");}                        //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania płatności.");             //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        } 
                        int id_pay = payIDAO.getId(name, form_p);                                                    //pobranie identyfikatora płatności na potzreby historii  </editor-fold> 
                      //ustawianie historii modyfikacji                                                               <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "DELETE FROM `payment` WHERE `id_pay` = "+id_pay;                               //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "INSERT INTO `payment`(`name-py`, `cat-py`) VALUES ('"+name+"', '"+form_p+"')";  //ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Dodanie nowej formy płatności"; modify="nazwa: "+name+"',<br>forma: '"+form_p; //ustawienie opisu i tekstu modyfikacji
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                     //ustawienie modelu historii
                        q = histDAO.create(his);                                                                      //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}   //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Payment> listPay = payIDAO.read();                              //pobieranie listy płatności
                        sess.setAttribute("listPayment", listPay);                          //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    }
                break;
                case 6:  // = kod promocyjny
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                                  //tylko dla administaratora
                      //sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect                       <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie dodatkowe dane zostały przesłane + redirect">
                        boolean active; int value;                                                                  //deklaracja zmiennych
                        try{ 
                            active = Boolean.parseBoolean(request.getParameter("active"));                         //pobranie znacznika aktywności
                            value = Integer.parseInt(request.getParameter("values"));                              //pobranie wartości przeceny
                        }
                        catch (NumberFormatException | NullPointerException ex){                                    //sprawdzanie błędów
                            String url = request.getHeader("referer");                                              //sprawdzanie wcześniejszej strony
                            if((url!=null)){                                                                        //jeśli strona poprzednia nie jest pusta
                                sess.setAttribute("err_disc","Brak poprawnych danych!");                            //ustawianie zmiennej sesyjnej
                                response.sendRedirect("register");                                                  //przekierowanie do formularza rejestracji
                                return;                                                                             //przerwanie działania funkcji 
                            }
                            else{                                                                                   //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Brak dostępu!");                                      //ustawianie zmiennej sesyjnej
                                response.sendRedirect("home");                                                      //przekierowanie do strony głównej
                                return;                                                                             //przerwanie działania funkcji
                            }
                        }                                                                                           //</editor-fold>
                      //sprawdzenie czy taki kod promocyjny istnieje w bazie danych                                                <editor-fold defaultstate="collapsed" desc="sprawdzenie czy taki kod promocyjny istnieje w bazie danych">
                        try{  
                            idch = discIDAO.getId(name);                                                                                 //pobranie identyfikatora jeśli istnieje
                            if(idch!=0){                                                                                                //jeśli istnieje
                                sess.setAttribute("err_disc","Nie można dodać kodu promocyjnego! Podany kod istnieje w bazie danych."); //ustawianie zmiennej sesyjnej
                                response.sendRedirect("administrator?t=1");                                                             //przekierowanie do strony administratora
                                return;                                                                                                 //przerwanie działania funkcji 
                            }
                        }
                        catch (SQLException | NullPointerException ex){ }                    //sprawdzanie błędów </editor-fold>
                      //dodanie kodu promocyjnego                                                                   <editor-fold defaultstate="collapsed" desc="dodanie kodu promocyjnego">                                                
                        Discount Disc = new Discount(NULL, name, active, value);                                    //model kodu promocyjnego

                        q = discIDAO.create(Disc);                                                                   //dodanie kodu promocyjnego
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano kod promocyjny.");}                  //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas edycji dodawnia promocyjnego.");    //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        } 
                        int id_dc = discIDAO.getId(name);                                                            //pobranie wcześniejszego kodu promocyjnego na potrzeby historii    </editor-fold> 
                      //ustawianie historii modyfikacji                                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "DELETE FROM `discount` WHERE `id_disc` = "+id_dc;                                //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "INSERT INTO `discount`(`name-dc`, `active`) VALUES ('"+name+"', '"+active+"')";   //ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Dodanie nowego kodu zniżkowego"; modify="nazwa: "+name+",<br>aktywny: ";                                                                 //ustawienie opisu i tekstu modyfikacji
                        if(active){ modify+="tak"; } else{ modify+="nie"; }                                             //dodanie znacznika po polsku
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                       //ustawienie modelu historii
                        q = histDAO.create(his);                                                                        //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}     //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Discount> listDisc = discIDAO.read();                           //pobieranie listy kodów promocyjnych
                        sess.setAttribute("listDiscount", listDisc);                        //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    } 
                break;
            }
            //przekierowanie użytkowników                                                     <editor-fold defaultstate="collapsed" desc="przekierowanie użytkowników">
              if(rnk.getId_rank()==1){                                                        //jeśli jest to administrator
                  response.sendRedirect("administrator?t=1");                                 //przeierowanie do odpowiedniego pliku
              }
              if(rnk.getId_rank()==4){                                                        //jeśli jest to zaopatrzeniowiec
                  response.sendRedirect("supplier?t=2");                                      //przekierownie do zaopatrzeniowca
              }                                                                               //</editor-fold>
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 1)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    } 
    
    //usuwanie danych w tabelach słownikowych (zakładka 1 (t=1))
    private void deleteDict(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess=request.getSession(true);                                          //pobranie sesji poprzedniej
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = "";    //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 3; History his = null; boolean q = false;              //użytkownik zmieniający, model historii, akcja
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
        try { rnk = userSDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>

        if((rnk.isRank())&&(rnk.getId_rank()==1)||(rnk.getId_rank()==4)){                                       //jeśli użytkownik posiada rangę i jest nią 1(administrator) lub 4(zaopatrzeniowiec)
          //sprawdzenie czy przesłano numer zakładki                        <editor-fold defaultstate="collapsed" desc="sprawdzenie czy przesłano numer zakładki">
            int tabl = 0;                                                   //ustawienie początkowej wartości
            try{ tabl = Integer.parseInt(request.getParameter("t")); }      //pobranie zakładki
            catch(NumberFormatException x) {                                //sprawdzanie błędów 
                sess.setAttribute("err_disc","Brak poprawnych danych!");    //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                             //przekierowanie do strony głównej
                return;                                                     //przerwanie działania funkcji
            }                                                               //</editor-fold>
          //sprawdzanie czy id został przesłany                                             <editor-fold defaultstate="collapsed" desc="sprawdzanie czy id został przesłany">
            int id;                                                                     //deklaracja zmiennej
            try{ id = Integer.parseInt(request.getParameter("idMod"));  }               //pobranie id   
            catch (NumberFormatException | NullPointerException ex){                    //sprawdzanie błędów
                sess.setAttribute("err_disc","Brak poprawnych danych!");                //ustawianie zmiennej sesyjnej
                response.sendRedirect("login");                                         //przekierowanie do strony głównej
                return;                                                                 //przerwanie działania funkcji
            }                                                                            //</editor-fold>
            switch(tabl){                                                                                       //sprawdzenie do jakiego uaktualnienia przenieść użytkownika
                case 1: // = kolor
                  //usuwanie koloru                                                                             <editor-fold defaultstate="collapsed" desc="usuwanie koloru"> 
                    Color col = colDDAO.getName(id);                                                             //pobranie modelu koloru na potrzeby historii
                    try{
                        q = colDDAO.delete(id);                                                                      //usuwanie koloru
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto kolor.");}                         //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania koloru.");                 //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        }
                    }
                    catch (SQLException | NullPointerException ex){ 
                        sess.setAttribute("err_disc","Nie można usunąć koloru! Jest on używany przez produkty.");  //ustawianie zmiennej sesyjnej
                        if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                      //przekierowanie do strony administratora
                        if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                           //przekierowanie do strony zaopatrzeniowca
                        return;                                                                                    //przerwanie działania funkcji 
                       
                    }                                                                                               //</editor-fold> 
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "INSERT INTO `color`(`id_col`,`name-cl`) "
                            + "VALUES ("+id+",'"+col.getName()+"')";                                            //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "DELETE FROM `color` WHERE `id_col` = "+id;                                    //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Usunięcie koloru"; modify="nazwa: "+col.getName();                           //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Color> listCol = colDDAO.read();                                //pobieranie listy kolorów
                    sess.setAttribute("listColor", listCol);                            //zapisanie listyw sesji </editor-fold>
                break;
                case 2:  // = kształt
                  //usuwanie kształtu                                                                           <editor-fold defaultstate="collapsed" desc="usuwanie kształtu">
                    Shape Shp = shpDDAO.read(id);                                                                //pobranie modelu kształtu na potrzeby historii
                    try{
                        q = shpDDAO.delete(id);                                                                      //usuwanie kształtu
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto kształt.");}                       //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania kształtu.");               //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        } 
                    }
                    catch (SQLException | NullPointerException ex){                                                 //sprawdzanie błędów 
                        sess.setAttribute("err_disc","Nie można usunąć kształtu! Jest on użytany przez produkty."); //ustawianie zmiennej sesyjnej
                        if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                       //przekierowanie do strony administratora
                        if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                            //przekierowanie do strony zaopatrzeniowca
                        return;                                                                                     //przerwanie działania funkcji 
                    }                                                                                               //</editor-fold>
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "INSERT INTO `shape`(`id_shap`,`name-sh`) "
                            + "VALUES ("+id+", '"+Shp.getName()+"')";                                           //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "DELETE FROM `shape` WHERE `id_shap` = "+id;                                   //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Usunięcie kształtu"; modify="nazwa: "+Shp.getName();                         //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Shape> listShp = shpDDAO.read();                                //pobieranie listy kształtów
                    sess.setAttribute("listShpe", listShp);                             //zapisanie listy w sesji  </editor-fold> 
                break;
                case 3: // = materiał
                  //usuwanie materiału                                                                          <editor-fold defaultstate="collapsed" desc="usuwanie materiału">
                    Fabric Fab = fabricDDAO.read(id);                                                            //pobranie modelu materiału na potrzeby historii
                    try{
                        q = fabricDDAO.delete(id);                                                                   //usuwanie materiału
                        if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto materiał.");}                      //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                        else{                                                                                       //w przeciwnym wypadku
                            sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania materiału.");              //wyświetlenie odpowiedniego komunikatu o błędzie
                            String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                            response.sendRedirect(url);
                            return;                                                                                 //zatrzymanie działania funkcji
                        }
                    }
                    catch (SQLException | NullPointerException ex){                                                 //sprawdzanie błędów
                        sess.setAttribute("err_disc","Nie można usunąć materiału! Jest on użytany przez produkty."); //ustawianie zmiennej sesyjnej
                        if(rnk.getId_rank()==1){ response.sendRedirect("administrator?t=1");}                       //przekierowanie do strony administratora
                        if(rnk.getId_rank()==4){ response.sendRedirect("supplier?t=1");}                            //przekierowanie do strony zaopatrzeniowca
                        return;                                                                                     //przerwanie działania funkcji 
                    }                                                                                               //</editor-fold>
                  //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                    beforeQuery = "INSERT INTO `fabric`(`id_fabr`,`name-fb`) "
                            + "VALUES ("+id+",'"+Fab.getName()+"')";               //ustawienie zapytania "przed zmianą" dla historii
                    afterQuery = "DELETE FROM `fabric` WHERE `id_fabr` = "+id;                                  //ustawienie zapytania SQL "po zmianie" dla historii
                    description = "Usunięcie materiału"; modify="nazwa: "+Fab.getName();                        //ustawienie opisu i tekstu modyfikacji
                    his = new History(NULL, id_usr, action, description, now, 
                            beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                    q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                    if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                  //uaktualnienie zmiennej sesyjnej                                         <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                    List<Fabric> listFbr = fabricDDAO.read();                                //pobieranie listy materiałów
                    sess.setAttribute("listFbric", listFbr);                                //zapisanie listy w sesji </editor-fold>
                break;
                case 4: //= dostawa
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //tylko dla administaratora
                      //usuwanie dostawy                                                                             <editor-fold defaultstate="collapsed" desc="usuwanie dostawy">  
                        Delivery Deliv = delDDAO.read(id);                                                            //pobranie wcześniejszej dostawy na potrzeby historii                                                 
                        try{
                            q = delDDAO.delete(id);                                                                       //usunięcie dostawy
                            if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto dostawę.");}                        //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                            else{                                                                                        //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania dostawy.");                 //wyświetlenie odpowiedniego komunikatu o błędzie
                                String url = request.getHeader("referer");                                               //przekirowanie do poprzedniej strony
                                response.sendRedirect(url);
                                return;                                                                                  //zatrzymanie działania funkcji
                            }
                        }
                        catch (SQLException | NullPointerException ex){ 
                            sess.setAttribute("err_disc","Nie można usunąć dostawy! Jest ona użytana przez zamówienia."); //ustawianie zmiennej sesyjnej
                            response.sendRedirect("administrator?t=1");                                                 //przekierowanie do strony administratora
                            return;                                                                                     //przerwanie działania funkcji 
                        }                                                                                               //</editor-fold> 
                      //ustawianie historii modyfikacji                                                             <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "INSERT INTO `delivery`(`id_deliv`, `name-d`, `price-d`) VALUES ("
                                +id+",'"+Deliv.getName()+"', "+Deliv.getPrice()+")";                                 //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "DELETE FROM `delivery` WHERE `id_deliv` = "+id;                               //ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Usunięcie dostawy";                                                          //ustawienie opisu
                        modify="nazwa: '"+Deliv.getName()+"'<br>cena: "+Deliv.getPrice();                           //ustawienie tekstu modyfikacji
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                        q = histDAO.create(his);                                                                    //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");} //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Delivery> listDel = delDDAO.read();                             //pobieranie listy dostaw                
                        sess.setAttribute("listDelivery", listDel);                         //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    }
                break;
                case 5: // = płatność
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //tylko dla administaratora
                      //usuwanie płatności                                                                          <editor-fold defaultstate="collapsed" desc="usuwanie płatności">                                                  
                        Payment Pay = payDDAO.read(id);                                                              //pobranie modelu płatności na potrzeby historii 
                        try{ 
                            q = payDDAO.delete(id);                                                                      //usunięcie płatności
                            if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto płatność.");}                      //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                            else{                                                                                       //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Wystąpił błąd podczas usuwania płatności.");              //wyświetlenie odpowiedniego komunikatu o błędzie
                                String url = request.getHeader("referer");                                              //przekirowanie do poprzedniej strony
                                response.sendRedirect(url);
                                return;                                                                                 //zatrzymanie działania funkcji
                            }   
                        }
                        catch (SQLException | NullPointerException ex){                                                 //sprawdzanie błędów
                            sess.setAttribute("err_disc","Nie można usunąć płatności! Jest ona użytana przez zamówienia.");   //ustawianie zmiennej sesyjnej
                            response.sendRedirect("administrator?t=1");                                                     //przekierowanie do strony administratora
                            return;                                                                                         //przerwanie działania funkcji 
                        }                                                                                                   //</editor-fold> 
                      //ustawianie historii modyfikacji                                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "INSERT INTO `payment`(`id_pay`,`name-py`, `cat-py`) "
                                +"VALUES ("+id+",'"+Pay.getName()+ "', '"+Pay.getCategory()+"')";                       //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "DELETE FROM `payment` WHERE `id_pay` = "+id;                                      //ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Usunięcie formy płatności";                                                      //ustawienie opisu
                        modify="nazwa: "+Pay.getName()+"', forma: '"+Pay.getCategory()+"'";                             //ustawienie tekstu modyfikacji
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                     //ustawienie modelu historii
                        q = histDAO.create(his);                                                                      //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}   //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Payment> listPay = payDDAO.read();                              //pobieranie listy płatności
                        sess.setAttribute("listPayment", listPay);                          //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    }
                break;
                case 6:  // = kod promocyjny
                    if((rnk.isRank())&&(rnk.getId_rank()==1)){                              //tylko dla administaratora
                      //usuwanie kodu promocyjnego                                                                      <editor-fold defaultstate="collapsed" desc="usuwanie kodu promocyjnego">                                                
                        Discount Disc = discDDAO.read(id);                                                               //pobranie wcześniejszego kodu promocyjnego na potrzeby historii
                        try{ 
                            q = discDDAO.delete(id);                                                                         //usunięcie kodu promocyjnego
                            if(q){ sess.setAttribute("succ_disc","Pomyślnie usunięto kod promocyjny.");}                    //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                            else{                                                                                           //w przeciwnym wypadku
                                sess.setAttribute("err_disc","Wystąpił błąd podczas edycji usuwania kodu promocyjnego.");   //wyświetlenie odpowiedniego komunikatu o błędzie
                                String url = request.getHeader("referer");                                                  //przekirowanie do poprzedniej strony
                                response.sendRedirect(url);
                                return;                                                                                     //zatrzymanie działania funkcji
                            } 
                        }
                        catch (SQLException | NullPointerException ex){                                                             //sprawdzanie błędów
                            sess.setAttribute("err_disc","Nie można usunąć kodu promocyjnego! Jest on użytany przez zamówienia.");  //ustawianie zmiennej sesyjnej
                            response.sendRedirect("administrator?t=1");                                                             //przekierowanie do strony administratora
                            return;                                                                                                 //przerwanie działania funkcji 
                        }                                                                                                           //</editor-fold>
                      //ustawianie historii modyfikacji                                                                 <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji">
                        beforeQuery = "INSERT INTO `discount`(`id_disc`,`name-dc`, `value`, `active`) "
                                +"VALUES ("+id+",'"+Disc.getName()+"', "+Disc.getValue()+" '"+Disc.isActive()+"')";     //ustawienie zapytania "przed zmianą" dla historii
                        afterQuery = "DELETE FROM `discount` WHERE `id_disc` = "+id;                                    //ustawienie zapytania SQL "po zmianie" dla historii
                        description = "Usunięcie kodu zniżkowego"; modify="nazwa: "+Disc.getName()+", aktywny: ";       //ustawienie opisu i tekstu modyfikacji
                        if(Disc.isActive()){ modify+="tak"; } else{ modify+="nie"; }                                    //dodanie znacznika po polsku
                        his = new History(NULL, id_usr, action, description, now, 
                                beforeQuery, afterQuery, modify);                                                       //ustawienie modelu historii
                        q = histDAO.create(his);                                                                        //dodanie historii do bazy danych 
                        if(!q){ sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania historii zdarzeń.");}     //jeśli się niepowiodło wyświetlenie odpowiedniego komunikatu o błędzie </editor-fold> 
                      //uaktualnienie zmiennej sesyjnej                                     <editor-fold defaultstate="collapsed" desc="uaktualnienie zmiennej sesyjnej">
                        List<Discount> listDisc = discDDAO.read();                           //pobieranie listy kodów promocyjnych
                        sess.setAttribute("listDiscount", listDisc);                        //zapisanie listy w sesji  </editor-fold>
                    }
                    else{
                        sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                        response.sendRedirect("home");                                  //przekierowanie do strony głównej
                        return;                                                         //przerwanie działania funkcji
                    } 
                break;
            }
            //przekierowanie użytkowników                                                     <editor-fold defaultstate="collapsed" desc="przekierowanie użytkowników">
              if(rnk.getId_rank()==1){                                                        //jeśli jest to administrator
                  response.sendRedirect("administrator?t=1");                                 //przeierowanie do odpowiedniego pliku
              }
              if(rnk.getId_rank()==4){                                                        //jeśli jest to zaopatrzeniowiec
                  response.sendRedirect("supplier?t=2");                                      //przekierownie do zaopatrzeniowca
              }                                                                               //</editor-fold>
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 1)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    } 
    
    
    //wyświetlanie w servlecie
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControlUserRank</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControlUserRank at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    //krótki opis servletu
    @Override
    public String getServletInfo() {
        return "Serwlet służący do obsługiżądań użytkowników z rangą.";
    }
}
