/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 28.09.2021 r.
 */
package jwl.jewelry;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
 
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.time.*;
import java.time.format.*;
import java.util.stream.*;
import jwl.DAO.*;
import jwl.model.*;
import static org.apache.commons.io.FileUtils.copyFile;

/**
 * ControlFiles.java
 * Ten servlet obsługuje pliki przesłane do aplikacji, tworzenie ścieżki jeśli nie istnieje,
 *         usuwanie zdjęć oraz uaktualnianie ilości zdjęć w bazie danych. 
 * @author DRzepka
 */
@WebServlet(name="ControlFiles", urlPatterns={"/upload", "/delfile", "/updfile", "/redirect"}, asyncSupported = true)
public class ControlFiles extends HttpServlet {
   //zmienne                                                                    <editor-fold defaultstate="collapsed" desc="zmienne">
    private static final long serialVersionUID = 1L;                            //zmienna seryjna                              
    private DateTimeFormatter formatLocalDate;                                  //formatowanie daty
  //tabele produktu i hostorii  
    private ProductDAO prodDAO;                                                 //produkt
    private HistoryDAO histDAO;                                                 //historia
    private UserDAO userDAO;                                                    //użytkownik
  //zmienna wskazująca na ścieżkę projektu                                      //ustawić na właściwą ścieżkę jeśli projekt będzie przeniesiony (bez tego nie zadziała)!
    private final String PATH = "C:\\Users\\DRzepak\\Documents\\NetBeansProjects\\jewelry\\jewelry\\src\\main\\webapp\\img\\CT\\";
    private static final String UPLOAD_DIRECTORY = "upload";                    //ścieżka do tymczasowego przechowania pliku
  //ustawienia przesyłania
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;              // uatawienie pamięci na 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40;             // maksymalna wielkość przesłanego pliku = 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50;             //maksymalny rozmiar pobrania = 50MB  </editor-fold>
   //inicjalizacja zmiennych  
    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");                   //pobieranie url bazy
        String jdbcUsername = getServletContext().getInitParameter("jdbcSupplier");         //pobieranie nazwy użytkownika "supplier"
        String jdbcPassword = getServletContext().getInitParameter("jdbcSupplierPassw");    //pobieranie hasła użytkownika "supplier"
        String jdbcHist = getServletContext().getInitParameter("jdbcHistory");              //pobieranie nazwy użytkownika "history"
        String jdbcHistPassw = getServletContext().getInitParameter("jdbcHistoryPassw");    //pobieranie hasła użytkownika "history"
      //tabele główne 
        prodDAO = new ProductDAO(jdbcURL, jdbcUsername, jdbcPassword);              //produkt
        histDAO = new HistoryDAO(jdbcURL, jdbcHist, jdbcHistPassw);              //historia
        userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);                 //użytkowik
      //data  
        formatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");       //formatowanie daty
    }
    
   //obsługa POST 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        String action = request.getServletPath();                               //sprawdzenie ścieżki z której przyszła odpowiedź
        try {
            switch (action) {                                                   //obsłyga po ścieżce
                case "/upload":  uploadImg(request,response); break;            //przekazywanie pliku
                case "/delfile":  deleteImg(request,response); break;           //usuwanie pliku
                case "/updfile":  updateImg(request,response); break;           //aktualizacja ilości plików w bazie
                case "/redirect":  redirect(request,response); break;           //przekierowanie na inną stronę
            }
        } catch (IOException | SQLException | ServletException ex){             //obsługiwanie błędów
            throw new ServletException(ex);                                     //wyświetlanie błędów serwera
        }
    }
    
   //Obsługa GET    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        doPost(request,response);                                               //przekierowanie do POST
    }
    
    //przekazywanie pliku bezpośrednio do serwera
    private void uploadImg(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession sess = request.getSession(true);                                         //pobranie sesji
               
      //sprawdzanie czy wszystkie wymagane dane zostały przesłane           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
        int id = 0;                                                         //deklaracja zmiennej
        try{ id = Integer.parseInt(request.getParameter("id")); }           //pobieranie id
        catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
      //sprawdzanie czy serwer obsługuje możliwość przesyłania wielu plików         <editor-fold defaultstate="collapsed" desc="sprawdzanie czy serwer obsługuje możliwość przesyłania wielu plików">  
        if (!ServletFileUpload.isMultipartContent(request)) {                       //jeśli nie ma takiej możliwości kończymy tutaj
            sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej       
            response.sendRedirect("home");                                          //przekierowanie do strony głównej
            return;                                                                 //przerwanie działania funkcji
        }                                                                           //</editor-fold>

      //ustawianie przesyłania plików                                               <editor-fold defaultstate="collapsed" desc="ustawianie przesyłania plików">  
        DiskFileItemFactory factory = new DiskFileItemFactory();                    //utworzenie zmiennej
        factory.setSizeThreshold(MEMORY_THRESHOLD);                                 //ustawienie produ pamięci - pliki większe niż ustawione nie będą zachowane w projekcie
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));      //ustawienie tymczasowej lokalizacji plików
        ServletFileUpload upload = new ServletFileUpload(factory);                  //ustawienie zmiennej do przesyłu
        upload.setFileSizeMax(MAX_FILE_SIZE);                                       //ustawienie maksymalnego rozmiaru pliku do przesyłu
        upload.setSizeMax(MAX_REQUEST_SIZE);                                        //ustawienie maksymalnego rozmiaru zapytania (plik + dane formularza)
        String uploadPath = getServletContext().getRealPath("")                     //stworzenie ścieżki aby przechować plik
                + File.separator + UPLOAD_DIRECTORY;                                //(ścieżka relatywna nie znajdująca się na stałe w projekcie) </editor-fold>
      //tworzenie folderu jeśli nie istnieje                                        <editor-fold defaultstate="collapsed" desc="tworzenie folderu jeśli nie istnieje"> 
        File uploadDir = new File(uploadPath);                                      //utworzenie zmiennej plikowej
        if (!uploadDir.exists()) { uploadDir.mkdir(); }                             //jeśli ścieżka nie istnieje folder jest tworzony </editor-fold>
 
      //przesyłanie pliku i umieszczanie go w ścieżce projektu                                                              <editor-fold defaultstate="collapsed" desc="przesyłanie pliku i umieszczanie go w ścieżce projektu">
        int img = 1;                                                                                                        //utworzenie zmiennej sprawdzającej ilość plików
        try {
            @SuppressWarnings("unchecked")                                                                                  //znacznik obsługi błędu
            List<FileItem> formItems = upload.parseRequest(request);                                                        //ustawianie listy plików po żądzaniu
            if (formItems != null && formItems.size() > 0) {                                                                //jeśli lista plików nie jest pusta i ma więcej niż 0 plików
                for (FileItem item : formItems) {                                                                           //dla każdego elementu (pliku)
                    if (!item.isFormField()) {                                                                              //jeśli pole formularza nie jest puste
                        String fileName = new File(item.getName()).getName();                                               //stwórz zmienną plikową z nazwą pliku
                        String copyImagePath = getServletContext().getRealPath("img/CT/") + File.separator + id;            //stwórz ścieżkę kopii
                        File copyDir = new File(copyImagePath);                                                             //utwórz zmienną plikową
                        if (!copyDir.exists()) { copyDir.mkdir(); }                                                         //jeśli folder nie istnieje stwórz go
                        String filePath = copyImagePath + File.separator + fileName;                                        //pobierz ścieżkę pliku na serwerze
                        File storeFile = new File(filePath);                                                                //stwórz plik serwerowy
                        
                        item.write(storeFile);                                                                              //zapisz plik na dysku
                        sess.setAttribute("succ_disc","Dodano pomyślnie plik!");                                            //zapisz kounikat do wyświetlenia w sesji
                        
                     //Konwersja pliku do .jpg
                        BufferedImage originalImage = ImageIO.read(storeFile);                                              //utworzenie zmiennej buforu
                        try{
                            BufferedImage newBufferedImage = new BufferedImage(                                             //tworzenie płutna opartego na oryginalnym obrazie
                                originalImage.getWidth(),                                                                   //pobieranie szerokości oryginału
                                originalImage.getHeight(),                                                                  //pobieranie wysokości oryginału
                                BufferedImage.TYPE_INT_RGB);                             
                            newBufferedImage.createGraphics().drawImage(originalImage,0,0,Color.WHITE,null);                //rysowanie białego tła i umieszzcanie oryginały na nim
                            boolean next = true;                                                                            //utworzenie zmiennej (czy można przejść dalej)
                            String uploadImagePath = PATH + "\\" + id;                                                      //tworzenie ścieżki ostatecznej do folderu w projekcie
                            File targetDir = new File(uploadImagePath);                                                     //utworzenie ostatecznej ścieżki do folderu przypisania w projekcie
                            if (!targetDir.exists()) { targetDir.mkdir(); }                                                 //stworzenie folderu jeśli nie istnieje
                            do{                                                                                             //zaczęcie pętli
                                String targetImagePath = uploadImagePath + File.separator + img + ".jpg";                   //utworzenie ostatecznej ścieżki na serweerze
                                File targetImg = new File(targetImagePath);                                                 //utworzenie ostatecznego pliku na serwerze
                                if (targetImg.exists()) {                                                                   //jeśli plik istnieje
                                    img++;                                                                                  //dodawanie 1 do ilości obrazu 
                                } 
                                else{                                                                                       //w przeciwnym wypadku
                                    ImageIO.write(newBufferedImage, "jpg", targetImg);                                      //zapisywanie plik na serwerze
                                    next = false;                                                                           //ustawianie znacznik (nie trzeba już kontynuować poszukiwań)
                                }
                            }while(next);                                                                                   //zakończenie działania pętli jeśli next == false
                        }
                        catch (IOException ex){                                                                             //wychwytywanie błedów
                           sess.setAttribute("err_disc","Bład konwersji obrazu: " + ex.getMessage());                       //ustawienie odpowiedzniego komunikatu
                        }
                        storeFile.delete();                                                                                 //usunięcie tymczasowego pliku 
                    }
                    img++;                                                                                                  //dodanie 1 do ilości obrazów przed przejściem do następnego pliku
                }
              //sprawdzanie czy w folderze jest więcej plików niż te dodane 
                boolean next = true;                                                                                        //ustawienie znacznika dla pętli
                do{                                                                                                         //zaczęcie pętli
                    String targetImagePath = PATH + "\\" + id + File.separator + img + ".jpg";                              //ustawienie ścieżki pliku
                    File targetImg = new File(targetImagePath);                                                             //ustawienie pliku
                    if (targetImg.exists()) { img++; }                                                                      //jełśi plik istnieje dodaj 1 do ilości obrazów
                    else{ next = false; }                                                                                   //w przeciwnym wypadku ustaw znacznik
                }while(next);                                                                                               //zakończenie pętli jeśli next ==false
            }
        } catch (Exception ex) {                                                                                            //wychwytywanie błedów
            sess.setAttribute("err_disc","Wystąpił błąd: " + ex.getMessage());                                              //ustawienie odpowiedzniego komunikatu
        }                                                                                                                   //</editor-fold>
        
        sess.setAttribute("idImg",id);                                                                                      //ustawienie zmiennej sesyjnej z id produktu gdzie znajduje się obraz
        sess.setAttribute("quantImg",(img-1));                                                                              //ustawienie zmiennej sesyjnej z ilością obrazów
        response.sendRedirect("updfile");                                                                                   //przekierowanie do uaktualnienia ilości obrazów w bazie danych
    } 
    
    //usuwanie zaznaczonych plików
    private void deleteImg(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession sess = request.getSession(true);                                         //pobranie sesji
        
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
        try { rnk = userDAO.checkRankId(id_usr); }                          //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==4)){                              //jeśli użytkownik posiada rangę i jest nią 4 (zaopatrzeniowiec)
          
          //sprawdzanie czy wszystkie wymagane dane zostały przesłane          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
            int id = 0, lgh = 0;                                                //deklaracja zmiennych
            try{ id = Integer.parseInt(request.getParameter("id")); }           //pobieranie id
            catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
                sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                  //przekierowanie do strony głównej
                return;                                                         //przerwanie działania funkcji
            }                                                                   
            try{ lgh =  Integer.parseInt(request.getParameter("lgh")); }        //pobieranie ilości obrazów
            catch (NumberFormatException | NullPointerException ex){            //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
                sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                  //przekierowanie do strony głównej
                return;                                                         //przerwanie działania funkcji
            }                                                                   //</editor-fold>

          //usuwanie plików                                                                     <editor-fold defaultstate="collapsed" desc="usuwanie plików">
            try{  
                String img[]= request.getParameterValues("img");                                //pobranie zaznaczonych checkboxów z formularza
                for (String imgS : img) {                                                       //dla każdego elementu (obrazu)
                    if(imgS != null){                                                           //jeśli obraz został zaznaczony
                        String deleteImagePath = PATH  + id + File.separator + imgS + ".jpg";   //utworzenie ścieżki usuwania dla projektu
                        File selectedFile = new File(deleteImagePath);                          //utworzenie pliku usywania z wcześniej utworzoną ścieżką projektu
                        String copyImagePath = getServletContext().getRealPath("img/CT/")       //utworzenie ścieżki usuwania serwera
                                + File.separator + id + File.separator + imgS + ".jpg";
                        File selectedCopyFile = new File(copyImagePath);                        //utworzenie pliku usywania z wcześniej utworzoną ścieżką serwera
                        if (selectedFile.exists()) {                                            //jeśli plik istnieje 
                            selectedFile.delete();                                              //usuwanie plik z projektu
                            selectedCopyFile.delete();                                          //usuwanie pliku z serwera
                        } 
                    }
                }  
                sess.setAttribute("succ_disc","Pomyślnie usunięto pliki!");                     //wyświetlene odpowiedniego komunikatu
            } catch(Exception ex){                                                              //w wypadku błędu
                sess.setAttribute("err_disc","Błąd usuwania plików.");                          //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                         //zatrzymanie działania funkcji
            }                                                                                   //</editor-fold>  

          //zmiana nazw pozostałych plików (zapobieganie przerw)                                    <editor-fold defaultstate="collapsed" desc="zmiana nazw pozostałych plików (zapobieganie przerw)">
            try{    
                for (int i=1; i<lgh; i++) {                                                         //zaczęcie pętli do ilości obrazów
                    String deleteImagePath = PATH + id + File.separator + i + ".jpg";               //stworzenie zmiennej ścieżki pliku z projektu
                    File selectedFile = new File(deleteImagePath);                                  //stworzenie zmiennej plikowej po ścieżce z projektu
                    String copyImagePath = getServletContext().getRealPath("img/CT/") +             //stworzenie zmiennej ścieżki pliku z serwera
                            File.separator + id + File.separator + i + ".jpg";
                    File selectedCopyFile = new File(copyImagePath);                                //stworzenie zmiennej plikowej po ścieżce z serwera
                    for(int j=1; j<lgh; j++){                                                       //zaczęcie następnej pętli sprawdzającej
                        String nextImagePath = PATH + id + File.separator + (i+j) + ".jpg";         //stworzenie zmiennej ścieżki pliku z projektu dla porównania
                        File nextFile = new File(nextImagePath);                                    //stworzenie zmiennej plikowej po ścieżce z projektu dla porównania
                        String nextCopyImagePath = getServletContext().getRealPath("img/CT/") +     //stworzenie zmiennej ścieżki pliku z serwera dla porównania
                                File.separator + id + File.separator + (i+j) + ".jpg";              
                        File nextCopyFile = new File(nextCopyImagePath);                            //stworzenie zmiennej plikowej po ścieżce z serwera dla porównania
                        if (!selectedFile.exists()) {                                               //jeśli wybrany przed drógą pętlą plik istnieje
                            if (nextFile.exists()) {                                                //jeśli następny plik istnieje
                                copyFile(nextFile, selectedFile);                                   //przekopiowanie plików miejscami w projekcie
                                copyFile(nextCopyFile, selectedCopyFile);                           //przekopiowanie plików miejscami w serwerze
                                nextFile.delete();                                                  //usunięcie następnego pliku w projekcie
                                nextCopyFile.delete();                                              //usunięcie następnego pliku w serwerze
                                break;                                                              //zatrzymanie pętli
                            } 
                        }
                    }    
                }
                sess.setAttribute("succ_disc","Pomyślnie usunięto pliki!");                         //wyświetlenie odpowiedniego komunikatu
            } catch(IOException ex){                                                                //wyświetlenie odpowiedniego komunikatu w przypadku wychwycenia błędu
                sess.setAttribute("err_disc","Nie zmieniono kolejności plików.");                   //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            } 
          //sprawdzenie ilości zdjęć  
            long images = 0;                                                                        //ustawienie znacznika
            Path target = Path.of(PATH + id);                                                       //ustawienie ścieżki
            try (Stream<Path> files = Files.list(target)) {                                         //zaciąganie listy plików w danym katalogu
                images = files.count();                                                             //zapisywanie liczby obrazów w folderze
            }
            catch(Exception ex){ 
                sess.setAttribute("err_disc","Błąd ścieżki. Skontaktuj się z administratorem.");    //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                          //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                             //zatrzymanie działania funkcji
            }                                                                                       //</editor-fold>

            sess.setAttribute("idImg",id);                                                      //ustawienie zmiennej sesyjnej z id produktu gdzie znajduje się obraz
            sess.setAttribute("quantImg",Math.toIntExact(images));                              //ustawienie zmiennej sesyjnej z ilością obrazów
            response.sendRedirect("updfile");                                                   //przekierowanie do uaktualnienia ilości obrazów w bazie danych
    
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
    }
    
    //uaktualnianie ilości obrazów w bazie danych
    private void updateImg(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession sess = request.getSession(true);                                         //pobranie sesji
      //ustawienie zmiennych do zapisania historii                                          <editor-fold defaultstate="collapsed" desc="ustawienie zmiennych do zapisania historii">
        String beforeQuery = null, afterQuery = null, description = null, modify = null;     //zapytanie przed, zapytanie po, opis, modyfikacja
        int user = NULL; History his = null; int action = 2;                                 //użytkownik zmieniający, model historii, akcja
        String now = formatLocalDate.format(LocalDateTime.now());                            //ustawienie czasu </editor-fold>
        
      //sprawdzanie czy użytkownik jest zalogowany                                          <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik jest zalogowany">
        try { user = (int) sess.getAttribute("id_user_logged"); }                           //pobranie zmiennej sesyjnej z id użytkownika
        catch (NullPointerException e) { }                                                  //sprawdzanie błędów </editor-fold>
 
      //sprawdzanie czy użytkownik posiada rangę                            <editor-fold defaultstate="collapsed" desc="sprawdzanie czy użytkownik posiada rangę">
        User rnk = null;                                                    //ustawienie zmiennej
        try { rnk = userDAO.checkRankId(user); }                            //pobranie rangi użytkownika
        catch (NullPointerException e) {                                    //sprawdzanie błędów 
            sess.setAttribute("err_disc","Brak dostępu!");                  //ustawianie zmiennej sesyjnej
            response.sendRedirect("home");                                  //przekierowanie do strony głównej
            return;                                                         //przerwanie działania funkcji
        }                                                                   //</editor-fold>
        
        if((rnk.isRank())&&(rnk.getId_rank()==4)){                              //jeśli użytkownik posiada rangę i jest nią 4 (zaopatrzeniowiec)
          //sprawdzanie czy wszystkie wymagane dane zostały przesłane                           <editor-fold defaultstate="collapsed" desc="sprawdzanie czy wszystkie wymagane dane zostały przesłane">
            int img = 0; int idImg =0;                                                          //deklaracja zmiennych  
            try{
                img = (int)sess.getAttribute("quantImg");                                       //pobieranie id
                idImg = (int)sess.getAttribute("idImg");                                        //pobieranie ilości obrazów
            }catch (NullPointerException ex){                                                   //sprawdzanie błędów, jeśli się pojawią kończymy tutaj
                sess.setAttribute("err_disc","Brak dostępu!");                                  //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                                  //przekierowanie do strony głównej
                return;                                                                         //przerwanie działania funkcji
            }                                                                                   //</editor-fold>

          //ualtualnianie ilości obrazów                                                        <editor-fold defaultstate="collapsed" desc="ualtualnianie ilości obrazów">
            Product pimg = prodDAO.readImg(idImg);                                              //sprawdzenie poprzedniej ilości do historii
            boolean q = prodDAO.updateImg(idImg, img);                                          //uaktualnianie w bazie danych
            if(!q){                                                                             //w przeciwnym wypadku
                sess.setAttribute("err_disc","Błąd zmiany ilości w bazie danych.");             //wyświetlenie odpowiedniego komunikatu o błędzie
                String url = request.getHeader("referer");                                      //przekirowanie do poprzedniej strony
                response.sendRedirect(url);
                return;                                                                         //zatrzymanie działania funkcji
            }                                                                                   //</editor-fold>

          //ustawianie historii modyfikacji                                                                           <editor-fold defaultstate="collapsed" desc="ustawianie historii modyfikacji ">
            beforeQuery = "UPDATE `product` SET `images-p` = "+pimg.getImages()+" WHERE `id_prod` = "+pimg.getId();     //ustawianie zapytania przed modyfikacją          
            afterQuery = "UPDATE `product` SET `images-p` = "+img+" WHERE `id_prod` = "+idImg;                          //ustawianie zapytania po modyfikacji
            description = "Modyfikacja zdjęć";  action = 7; modify="ilość zdjęć:<br>"+pimg.getImages()+" -> "+img;      //ustawianie opisu i modyfikacji
            his = new History(NULL, user, action, description, now, beforeQuery, afterQuery, modify);                   //tworzenie zmiennej historii
            histDAO.create(his);                                                                                        //tworzenie hstorii w bazie danych </editor-fold>

            sess.removeAttribute("idImg");                                                      //usunięcie zmiennej sesyjnej z id produktu
            sess.removeAttribute("quantImg");                                                   //usunięcie zmiennej sesyjnej z ilością zdjeć
            response.sendRedirect("redirect?id="+idImg);                                        //przekierowanie do strony przejściowej
        }
        else{                                                                           //w przeciwnym wypadku (użytkownik nie posiada rangi lub nie jest nią liczba 2)
            sess.setAttribute("err_disc","Brak poprawnych danych!");                    //ustawianie zmiennej sesyjnej
            response.sendRedirect("login");                                             //przekierowanie do strony głównej
            return;                                                                     //przerwanie działania funkcji
        }
        
    } 
    
    //przekierowanie do strony przejściowej
    private void redirect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession sess = request.getSession(true);                                        //pobranie sesji
        String id = request.getParameter("id");                                             //pobranie id produktu
        if(id!=null){                                                                       //jeśli id nie jest puste
            sess.setAttribute("id_img", Integer.parseInt(id));                              //ustaw zmkenną sesyjną pzrechowującą id
            RequestDispatcher dispatcher = request.getRequestDispatcher("refresh.jsp");     //przekieruj do strony pzrejściowej
            dispatcher.forward(request, response);                                          //przekaż zmienne
        }
        else{                                                                               //w przeciwnym wypadku
            String answer = request.getParameter("answ");                                   //pobierz odpowiedź 
            if(answer!=null){                                                               //jeśłi odpowiedź nie jest pusta
                if(answer.equals("acc")){                                                   //jeśłi odpowiedź to "acc"
                    int idi = (int)sess.getAttribute("id_img");                             //pobierz id produktu (obrazów)
                    sess.removeAttribute("id_img");                                         //usuń zmienną sesyjną
                    response.sendRedirect("viewOneProduct?id="+idi+"&t=2");                           //przekieruj do widoku użytkownika
                } 
                else{                                                                       //w przeciwnym wypadku
                    sess.setAttribute("err_disc","Brak dostępu!");                          //ustawianie zmiennej sesyjnej
                    response.sendRedirect("home");                                          //przekierowanie do strony głównej
                    return;                                                                 //przerwanie działania funkcji
                }
            }
            else{                                                                           //w przeciwnym wypadku
                sess.setAttribute("err_disc","Brak dostępu!");                              //ustawianie zmiennej sesyjnej
                response.sendRedirect("home");                                              //przekierowanie do strony głównej
                return;                                                                     //przerwanie działania funkcji
            }
        }
    } 
    
    //krótki opis servletu
    @Override
    public String getServletInfo() {
        return "Ten servlet obsługuje pliki przesłane do aplikacji, tworzenie ścieżki jeśli nie istnieje, "
                + "usuwanie zdjęć oraz uaktualnianie ilości zdjęć w bazie danych. ";
    }
}
//źródło1: https://www.codejava.net/java-ee/servlet/apache-commons-fileupload-example-with-servlet-and-jsp przesyłanie obrazu (część kodu wykorzystana)
//źródło2: https://mkyong.com/java/how-to-convert-inputstream-to-file-in-java/  konwersja strunienia (część kodu wykorzystana)
//źródło3: https://mkyong.com/java/convert-png-to-jpeg-image-file-in-java/  konwersja obrazu 