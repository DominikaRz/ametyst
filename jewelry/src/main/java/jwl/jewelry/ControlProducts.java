/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 04.10.2021 r.
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
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import jwl.DAO.*;
import jwl.DAO.dict.*;
import jwl.model.*;
import jwl.model.dict.*;
import jwl.model.link.*;
import org.jsoup.Jsoup;

/**
 * ControlProducts.java
 * Ten servlet obsługuje produkty. Wyświetla ścieżki, opisy oraz produkty.
           Obsługuje również wyszukiwanie produktów.
 * @author DRzepka
 */
@WebServlet(name="ControlProducts", urlPatterns={"/categories", "/tags", "/product", "/search", 
    "/list", "/news", "/discount", "/restock", "/review", "/addReview"}, asyncSupported = true)
public class ControlProducts extends HttpServlet {
   //zmienne                                                                      <editor-fold defaultstate="collapsed" desc="zmienne">
    private static final long serialVersionUID = 1L;                            //zmienna seryjna
  //tabele główne  
    private ProductMDAO prodmDAO;                                               //produkt                                             
    private ReviewDAO rewDAO;                                                   //recenzja
    private HistoryDAO histDAO;                                                 //historia
  //tabela łącznikowa  
    private OrderPDAO ordpDAO;                                                  //zamówienie-produkt
  //tabele słownikowe  
    private CatTagDAO ctDAO;                                                    //menu
    private ColorDAO colDAO;                                                    //kolor
    private FabricDAO fabricDAO;                                                //materiał
    private ShapeDAO shpDAO;                                                    //kształt
  //inne
    private HttpSession sess;                                                   //zmienna sesyjna
  //formatowanie daty
    private SimpleDateFormat formatter; 
    private DateTimeFormatter formatLocalDate;                                  //</editor-fold>
   //inicjalizacja zmiennych  
    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");                   //pobieranie url bazy
        String jdbcHist = getServletContext().getInitParameter("jdbcHistory");              //pobieranie nazwy użytkownika "history"
        String jdbcHistPassw = getServletContext().getInitParameter("jdbcHistoryPassw");    //pobieranie hasła użytkownika "history"
        String jdbcProd = getServletContext().getInitParameter("jdbcProducts");             //pobieranie nazwy użytkownika "products"
        String jdbcProdPassw = getServletContext().getInitParameter("jdbcProductsPassw");   //pobieranie hasła użytkownika "products"
        String jdbcUser = getServletContext().getInitParameter("jdbcUserNL");               //pobieranie nazwy użytkownika "user"
        String jdbcUserPassw = getServletContext().getInitParameter("jdbcUserNLPassw");     //pobieranie hasła użytkownika "user"
      //tabele główne
        prodmDAO = new ProductMDAO(jdbcURL, jdbcProd, jdbcProdPassw);            //produkt
        rewDAO = new ReviewDAO(jdbcURL, jdbcUser, jdbcUserPassw);                //recenzja
        histDAO = new HistoryDAO(jdbcURL, jdbcHist, jdbcHistPassw);              //historia
      //tabele łącznikowe  
        ordpDAO = new OrderPDAO(jdbcURL, jdbcUser, jdbcUserPassw);               //dla ilości produktów w zamówieniach jeszcze nie zaakceptowanych
      //tabele słownikowe
        ctDAO = new CatTagDAO(jdbcURL, jdbcProd, jdbcProdPassw);                 //menu
       //dla produktu 
        colDAO = new ColorDAO(jdbcURL, jdbcProd, jdbcProdPassw);                 //kolor
        fabricDAO = new FabricDAO(jdbcURL, jdbcProd, jdbcProdPassw);             //materiał
        shpDAO = new ShapeDAO(jdbcURL, jdbcProd, jdbcProdPassw);                 //kształt
     //inne   
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                    //formatowanie daty
        formatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");       //formatowanie daty
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
               case "/product": showListP(request,response); break;         //wyświetlanie pojedynczego produktu
               case "/search": search(request,response);  break;            //wyszukiwanie produkty
               case "/categories": showCT(request,response, 1);  break;     //pokazanie menu po kategoriach (wybór = 1)
               case "/tags": showCT(request,response, 2);  break;           //pokazanie menu po tagach (wybór = 2)
               case "/news": showCT(request,response, 3); break;            //pokazanie nowych produktów  (wybór = 3)
               case "/discount": showCT(request,response, 4); break;        //pokazanie produktów w promocji (wybór = 4)
               case "/restock": showCT(request,response, 5); break;         //pokazanie ponowionych produktów (ang. restock) (wybór = 5)
               case "/review": showRew(request,response); break;            //wystawianie recenzji dla użytkownika zalogowanego i niezalogowanego, który dokonał zakupu produktów
               case "/addReview": addRew(request,response); break;          //dodanie recenzji do bazy danych + historia dla użytkownika zalogowanego
            }
        } catch (SQLException ex) { throw new ServletException(ex); }       //wyświetlanie błędów serwera
    }
    
    //połączenia w menu i nawigacji 
    private void showCT(HttpServletRequest request, HttpServletResponse response, int choice)
            throws SQLException, ServletException, IOException {
        
        sess = request.getSession();                                                //pobranie sesji
        
      //delkaracja zmiennych podstawowych                                           <editor-fold defaultstate="collapsed" desc="delkaracja zmiennych podstawowych ">  
        int id = 0;                                                                 //deklaracja id produktu
        String time = null; java.sql.Date clacul = null;                            //deklaracja zmiennych czasowych
        Date date =  new Date(sess.getCreationTime());                              //definicja teraźniejszego czasu
       //listy do filtrowania
        List<Shape> listShp = shpDAO.read();                                        //lista kształtów
        request.setAttribute("listShp", listShp);                                   //deklaracja dla JSP
        List<Color> listCol = colDAO.read();                                        //lista kolorów
        request.setAttribute("listCol", listCol);                                   //deklaracja dla JSP </editor-fold>
      //wybranie co chce się wyświetlić  
        switch(choice){
          //wyświetlanie wszystkich produktów z danej kategorii                     <editor-fold defaultstate="collapsed" desc="wyświetlanie wszystkich produktów z danej kategorii">  
            case 1:
              //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
                try{ id = Integer.parseInt(request.getParameter("id")); }           //pobieranie id
                catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
                    sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                    response.sendRedirect("home");                                  //przekierowanie do strony głównej
                    return;                                                         //przerwanie działania funkcji
                }                                                                   //</editor-fold> 
                List<ProductMeta> listProdC = prodmDAO.readCat(id);                 //ustawianie listy produktów z danej kategorii
                request.setAttribute("listProdC", listProdC);                       //deklaracja dla JSP
                CatTag cat = ctDAO.getDescCat(id);                                  //pobranie opisu kategorii
                request.setAttribute("cat", cat);                                   //deklaracja dla JSP
                break;                                                              //przerwanie </editor-fold>
          //wyświetlanie wszystkich produktów dla danego tagu                       <editor-fold defaultstate="collapsed" desc="wyświetlanie wszystkich produktów dla danego tagu">  
            case 2:
              //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
                try{ id = Integer.parseInt(request.getParameter("id")); }           //pobieranie id
                catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
                    sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                    response.sendRedirect("home");                                  //przekierowanie do strony głównej
                    return;                                                         //przerwanie działania funkcji
                }                                                                   //</editor-fold>   
                List<ProductMeta> listProd = prodmDAO.readCT(id);                   //ustawianie listy produktów z danego tagu
                request.setAttribute("listProd", listProd);                         //deklaracja dla JSP
                CatTag tag = ctDAO.getTag(id);                                      //pobranie informacji o tagu (dla ścieżki i opisu)
                request.setAttribute("tag", tag);                                   //deklaracja dla JSP
                CatTag catname = ctDAO.getCat(id);;                                 //pobranie informacji o kategorii (dla ścieżki)
                request.setAttribute("catname", catname);                           //deklaracja dla JSP
                break;                                                              //przerwanie </editor-fold>
          //wyświetlanie nowości                                                   <editor-fold defaultstate="collapsed" desc="wyświetlanie nowości">
            case 3:
                clacul = new java.sql.Date(date.getTime() - 8l*24l*60l*60l*1000l);  //obliczenie czasu z long aby zmienić datę d*h*m*s*ms (około 8 dni)
                time = formatter.format(clacul);                                    //konwertowanie na ciąg znaków                       
                request.setAttribute("time", time);                                 //deklaracja dla JSP 
                List<ProductMeta> listProdN = prodmDAO.readNews(time);              //pobranie listy nowości z czasem około 8 dni
                request.setAttribute("listProdN", listProdN);                       //deklaracja dla JSP                            
                break;                                                              //przerwanie </editor-fold>
          //wyświetlanie promocji                                                   <editor-fold defaultstate="collapsed" desc="wyświetlanie promocji">
            case 4:
                List<ProductMeta> listProdDc = prodmDAO.readDiscount();             //pobieranie listy promocji
                request.setAttribute("listProdDc", listProdDc);                     //deklaracja dla JSP   
                break;                                                              //przerwanie </editor-fold>
          //wyświetlanie prodóktów ponownie dostępnych                              <editor-fold defaultstate="collapsed" desc="wyświetlanie prodóktów ponownie dostępnych">
            case 5:
                clacul = new java.sql.Date(date.getTime() - 14l*24l*60l*60l*1000l); //obliczenie czasu z long aby zmienić datę d*h*m*s*ms (około 14 dni)
                time = formatter.format(clacul);                                    //konwertowanie na ciąg znaków                                  
                List<ProductMeta> listProdRs = prodmDAO.readRestock(time);          //pobranie listy ponowionych produktów z czasem około 14 dni
                request.setAttribute("listProdRs", listProdRs);                     //deklaracja dla JSP  
                break;                                                              //przerwanie </editor-fold>
        }
        request.setAttribute("t", choice);                                                      //deklaracja dla JSP wyboru      
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/products/ListCT.jsp"); //przekierownie do listy
        dispatcher.forward(request, response);                                                  //przekazanie parametrów
    } 
    
    //wyświetlanie pojedynczego produktu  
    private void showListP(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
         
        sess = request.getSession();                                                //pobranie sesji
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id = 0;                                                         //deklaracja zmiennej         
        try{ id = Integer.parseInt(request.getParameter("id")); }           //pobieranie id
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold> 
      //pobranie danych produktu                                            <editor-fold defaultstate="collapsed" desc="pobranie danych produktu">
        ProductMeta prodM = prodmDAO.readOne(id);                           //pobranie danych dla jednego produktu
        request.setAttribute("prodm", prodM);                               //deklaracja dla JSP </editor-fold>
        if(prodM!=null){
      //pobranie danych do ścieżki produktu                                 <editor-fold defaultstate="collapsed" desc="pobranie danych do ścieżki produktu">
        CatTag names = ctDAO.getNames(prodM.getIdCattag());                 //pobranie nazw kategorii i tagu
        request.setAttribute("names", names);                               //deklaracja dla JSP
        CatTag ids = ctDAO.getIds(prodM.getIdCattag());                     //pobranie id kategorii i tagu
        request.setAttribute("ids", ids);                                   //deklaracja dla JSP </editor-fold>
      //pobranie danych słownikowych                                        <editor-fold defaultstate="collapsed" desc="pobranie danych słownikowych">
        Fabric fbr = fabricDAO.read(prodM.getIdFabr());                     //pobranie materiału 
        request.setAttribute("fabr", fbr);                                  //deklaracja dla JSP
        Color col = colDAO.getName(prodM.getIdCol());                       //pobranie koloru
        request.setAttribute("colo", col);                                  //deklaracja dla JSP
        Shape shp = shpDAO.read(prodM.getIdShap());                         //pobranie kształtu
        request.setAttribute("shap", shp);                                  //deklaracja dla JSP </editor-fold>
      //pobranie ilości w zamówieniach jeszcze nie zaakceptowanych          <editor-fold defaultstate="collapsed" desc="pobranie ilości w zamówieniach jeszcze nie zaakceptowanych">
        int quant = ordpDAO.quantityProd(id);                               //pobranie ilości w zamówieniach jeszcze nie zaakceptowanych
        request.setAttribute("quant", quant);                               //deklaracja dla JSP </editor-fold>
      //pobranie listy komentarzy/recenzji                                   <editor-fold defaultstate="collapsed" desc="pobranie listy komentarzy/recenzji">
        List<Review> listRew = rewDAO.read(id);                             //pobranie listy komentarzy/recenzji
        request.setAttribute("listRew", listRew);                           //deklaracja dla JSP </editor-fold>
        }
        else{
            sess.setAttribute("err_disc","Brak produktu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/products/Product.jsp"); //przekierowanie do produktu
        dispatcher.forward(request, response);                                      //przekazanie parametrów
    }
      
    //wyszukiwanie danych produktów
    private void search(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess = request.getSession();                                                //pobranie sesji
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        String search = null;                                               //deklaracja zmiennej         
        try{ search = Jsoup.parse(request.getParameter("search")).text(); } //pobieranie parametru
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }
        request.setAttribute("search", search);                             //deklaracja dla JSP </editor-fold>
        List<ProductMeta> prm = prodmDAO.search(search);                    //pobranie listy pasującej do frazy
        request.setAttribute("listProd", prm);                              //deklaracja dla JSP
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/products/Search.jsp"); //przekierowanie do wyszukiwania
        dispatcher.forward(request, response);                                     //przekazanie parametrów
    } 
    
    //strona wystawiania recenzji
    private void showRew(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess = request.getSession();                                                //pobranie sesji
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id = 0;                                                         //deklaracja zmiennej         
        try{ id = Integer.parseInt(request.getParameter("id")); }           //pobieranie id
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold> 
      //pobieranie danych i ich deklaracja dla JSP                          <editor-fold defaultstate="collapsed" desc="pobieranie danych i ich deklaracja dla JSP">
        List<OrderProd> orderp = ordpDAO.readUnrew(id);                    //pobieranie listy produktów do zrecenzowania
        List<ProductMeta> prodm =  new ArrayList<>();                       //deklaracja listy podstawowych danych produktów
        for (OrderProd ordp : orderp) {                                     //dla każdego niezrecenzowanego produktu
            prodm.add(prodmDAO.readProdOrd(ordp.getIdProd()));              //zaciąganie danych owego produktu
        }
        request.setAttribute("listProdm", prodm);                           //deklaracja dla JSP listy danych produktów
        request.setAttribute("listOrderp", orderp);                         //deklaracja dla JSP listy niezrecenzowanych produktów
        request.setAttribute("order", id);                                  //deklaracja dla JSP identyfikatora zamówienia </editor-fold> 

        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/products/Review.jsp"); //przekierowanie do wyszukiwania
        dispatcher.forward(request, response);                                     //przekazanie parametrów
    }
    
    //dodawanie recenzji
    private void addRew(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        
        sess = request.getSession();                                                //pobranie sesji
        
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = null;    //zapytanie przed, zapytanie po, opis, modyfikacja
        int id_usr = 0; int action = 2;                                                     //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                           //ustawienie czasu </editor-fold>
        
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int order, quant;                                                   //deklaracja zmiennych         
        try{ 
            order = Integer.parseInt(request.getParameter("ord"));          //pobieranie zamówienia
            quant = Integer.parseInt(request.getParameter("quant"));        //pobieranie ilości (button)
        }
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold> 
        
      //sprawdzenie czy użytownik jest zalogowany                                           <editor-fold defaultstate="collapsed" desc="sprawdzenie czy użytownik jest zalogowany">  
        try { id_usr = (int) sess.getAttribute("id_user_logged"); }                         //pobranie zmiennej sesyjnej z id użytkownika
        catch (NumberFormatException | NullPointerException ex){}                           //</editor-fold> 
        
      //sprawdznie dokładnych zmiennych i dodawanie recenzji  <editor-fold defaultstate="collapsed" desc="sprawdznie dokładnych zmiennych i dodawanie recenzji">
        int starsr = 0, prod = 0;                                               //deklaracja zmiennych liczbowych 
        String product = null, st = null, names = null, contentr = null;        //deklaracja zmiennych znakowych
        for(int i=1; i<quant; i++){                                             //dla każdego możliwego produktu do oceny 
            st = request.getParameter("stars"+i);                               //pobranie ilości gwiazdek dla danego produktu
            names = request.getParameter("name"+i);                             //pobranie nazwy recenzji dla danego produktu
            contentr = request.getParameter("review"+i);                        //pobranie treści recenzji dla danego produktu 
            product = request.getParameter("product"+i);                        //pobranie identyfikatora produktu dla danego produktu 
            String publication = formatLocalDate.format(LocalDateTime.now());   //pobranie czasu serwerowego dla publikacji

            if((!st.equals(""))&&(!names.equals(""))&&(!contentr.equals(""))){  //jeśli ilość gwiazdek, zazwa i treść recenzji nie są puste
                starsr = Integer.parseInt(st);                                  //parsowanie ilości gwiazdek
                prod = Integer.parseInt(product);                               //parsowanie idektyfikatora produktu
                names = Jsoup.parse(names).text();                              //parsowanie nazwy recenzji
                contentr = Jsoup.parse(contentr).text();                        //parsowanie treści recenzji

                Review rev = new Review(0, prod, order, names, starsr,
                        contentr, publication);                                                         //konstruowanie modelu recenzji
                boolean q = rewDAO.create(rev);                                                         //utworzenie recenzji
                if(q){ sess.setAttribute("succ_disc","Pomyślnie dodano recezje");}                      //jeśli się powiodło wyświetlenie odpowiedniego komunikatu
                else{                                                                                   //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Wystąpił błąd podczas dodawania "+i+" recenzji.");    //wyświetlenie odpowiedniego komunikatu o błędzie
                    String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                    response.sendRedirect(url);
                    return;                                                                             //zatzrymanie działania funkcji
                }
             
              //ustawianie historii modyfikacji dla użytkownika zalogowanego                                   <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji dla użytkownika zalogowanego ">
                if(id_usr!=0){
                    beforeQuery = "INSERT INTO `review`(`id_prod-rw`, `id_order-rw`,"
                           + " `name-rw`, `stars`, `content`, `publication`)"
                           + "VALUES ("+rev.getIdProd()+", "+rev.getIdOrder()+" , "+rev.getName()
                           +", "+rev.getStars()+", "+rev.getContent()+", "+rev.getPublication()+")"+id_usr;    //ustawienie zapytania "przed zmianą" dla historii
                   afterQuery = "DELETE FROM `review` WHERE `id_prod-rw` = '"+rev.getIdProd()
                           +"' AND `id_order-rw` = "+rev.getIdOrder();                                         //ustawienie zapytania SQL "po zmianie" dla historii
                   description = "Dodanie recenzji";  modify="gwiazdki: "+rev.getStars()+"<br>nazwa: "+rev.getName()
                           +"<br>treść: "+rev.getContent();                                                    //ustawienie opisu i tekstu modyfikacji
                   History his = new History(NULL, id_usr, action, description, now, 
                           beforeQuery, afterQuery, modify);                                                   //ustawienie modelu historii
                   histDAO.create(his);                                                                        //dodanie historii do bazy danych  
                }                                                                                              //</editor-fold>
            }
        }                                                                       //</editor-fold> 
        
     //przekierowanie do strony zamówienia   
        if(id_usr!=0){ response.sendRedirect("orders"); }                                   //przekierownaie dla użytkownika zalogowanego
        else{ sess.setAttribute("ord", order); response.sendRedirect("order?acc=2"); }      //przekierownaie dla użytkownika niezalogowanego
    }
    
    //krótki opis o servlecie
    @Override
    public String getServletInfo() {
        return "Ten servlet obsługuje produkty. Wyświetla ścieżki, opisy oraz produkty. "
                + "Obsługuje również wyszukiwanie produktów.";
    }
}