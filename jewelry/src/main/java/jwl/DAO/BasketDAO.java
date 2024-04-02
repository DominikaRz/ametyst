/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 26.08.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import static java.sql.Types.NULL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

import jwl.model.link.Basket;

public class BasketDAO extends Connect implements DAO<Basket> {
  
//-----Konstruktor-----  
    public BasketDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----  Tworzenie danych
   @Override
    //tworzenie dla użytkownika niezalogowanego
    public boolean create(Basket bask) throws SQLException {
        String sql = "INSERT INTO `basket`(`id_session-b`, `id_prod-b`, `quantity-b`, `expires-b`)"
                     + "VALUES (?, ?, ?, ?)";
        connect();
        
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, bask.getSession());
            statement.setInt(2, bask.getIdProd());
            statement.setInt(3, bask.getQuantity());
            statement.setString(4, bask.getDate());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    //tworzenie dla użytkownika zalogowanego
    public boolean createUsr(Basket bask) throws SQLException {
        String sql = "INSERT INTO `basket`(`id_session-b`, `id_user-b`, `id_prod-b`, `quantity-b`, `expires-b`)"
                     + "VALUES (?, ?, ?, ?, ?)";
        connect();
        
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, bask.getSession());
            statement.setInt(2, bask.getIdUser());
            statement.setInt(3, bask.getIdProd());
            statement.setInt(4, bask.getQuantity());
            statement.setString(5, bask.getDate());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ----- Czytanie danych
    //wyświetlanie wszystkich danych dla użytkownika zalogowanego
    public List<Basket> readUsr(int id) throws SQLException {
        Basket bask;
        List<Basket> listBask = new ArrayList<>();
        
        String sql = "SELECT `id_prod-b`, `quantity-b` FROM `basket` "
                   + "WHERE `id_user-b` = ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    int idP = resultSet.getInt("id_prod-b");
                    int qt = resultSet.getInt("quantity-b");
                    
                    bask = new Basket(id, idP, qt);
                    listBask.add(bask);
                }
            }
        }
        disconnect();
        return listBask;
    }
    
    //wyświetlanie wszystkich danych dla sesji (użytkownika niezalogowanego)
    public List<Basket> readSess(String id) throws SQLException {
        Basket bask;
        List<Basket> listBask = new ArrayList<>();
        
        String sql = "SELECT `id_prod-b`, `quantity-b`, `expires-b` FROM `basket` "
                   + "WHERE `id_session-b` = ? ";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    int idP = resultSet.getInt("id_prod-b");
                    int qt = resultSet.getInt("quantity-b");
                    String ct = resultSet.getString("expires-b");
                    
                    bask = new Basket(id, idP, qt, ct);
                    listBask.add(bask);
                }
            }
        }
        disconnect();
        return listBask;
    }
    
    //sprawdzanie czyprodukt istnieje w danej sesji
    public boolean checkSess(Basket bask) throws SQLException {
        String sql = "SELECT CASE WHEN EXISTS ( "
                   + "SELECT `quantity-b`, `expires-b` FROM `basket` WHERE `id_session-b` = ? AND `id_prod-b` = ?"
                   + ") "
                   + "THEN 'TRUE' "
                   + "ELSE 'FALSE' "
                   + "END AS quer"; 
        connect();
       boolean result = false; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, bask.getSession());
            statement.setInt(2, bask.getIdProd());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                     result = resultSet.getBoolean("quer");
                }
            }
        }
        disconnect();
        return result;
    }
    
    //sprawdzanie czyprodukt istnieje u danego użytkownika
    public boolean checkUsr(Basket bask) throws SQLException {
        String sql = "SELECT CASE WHEN EXISTS ( "
                   + "SELECT `quantity-b`, `expires-b` FROM `basket` WHERE `id_user-b` = ? AND `id_prod-b` = ?"
                   + ") "
                   + "THEN 'TRUE' "
                   + "ELSE 'FALSE' "
                   + "END AS quer"; 
        connect();
       boolean result = false; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, bask.getIdUser());
            statement.setInt(2, bask.getIdProd());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                     result = resultSet.getBoolean("quer");
                }
            }
        }
        disconnect();
        return result;
    }
    
   //----UPDATE----- Uaktualnianie danych
    @Override
    //uaktualnianie ilości produktów dla sesji
    public boolean update(Basket bask) throws SQLException { //update session
        String sql = "UPDATE `basket` SET `quantity-b` = ? " 
                    +"WHERE `id_session-b` = ? AND `id_prod-b` = ?";
        
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, bask.getQuantity());
            statement.setString(2, bask.getSession());
            statement.setInt(3, bask.getIdProd());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnianie ilości produktów dla użytkownika
    public boolean updateUsr(Basket bask) throws SQLException {
        String sql = "UPDATE `basket` SET `quantity-b` = ? "
                    +"WHERE `id_user-b` = ? AND `id_prod-b` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, bask.getQuantity());
            statement.setInt(2, bask.getIdUser());
            statement.setInt(3, bask.getIdProd());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //obsługa przejścia produktów z sesji dl=o koszyka użytkownika zalogowanego
    public boolean updateSessToUsr(Basket bask, int id) throws SQLException { 
        boolean updated = false;
        Basket basku = new Basket(id, bask.getIdProd(), bask.getQuantity());
        boolean exist = checkUsr(basku);
        //jeślidany produkt już istnieje w koszyku użytkownika wczytaj dane
        if(exist){
            String sql = "SELECT `quantity-b` FROM `basket` WHERE `id_user-b` = "+id+" AND `id_prod-b`="+bask.getIdProd(); 
            connect();
            int result = NULL; 
            try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                         result = resultSet.getInt("quantity-b");
                    }
                }
            }
            disconnect();
            //dodaj istniejącą ilość
            if(result!=NULL){
                basku.setQuantity(bask.getQuantity()+result);
                updated = updateUsr(basku);
                updated = deleteSessId(bask.getSession(), bask.getIdProd());
            }
            else{ 
                updated=false; 
            }
        }
        //jeśli nie to przypisz koszyk do użytkownika zapisując jego identyfikator i zerując sesje
        else{
            String sql = "UPDATE `basket` SET `id_user-b` = ?, `id_session-b` = ? "  
                        +"WHERE `id_session-b` = ? AND `id_prod-b` = ?";
            connect();
            try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setString(2, null);
                statement.setString(3, bask.getSession());
                statement.setInt(4, bask.getIdProd());
                updated = statement.executeUpdate() > 0;
            }
            disconnect();
        }
        return updated;
    }
    
    //uaktualnianie ilości produktów w koszyku dla sesji 
    public boolean updateBaskSess(Basket bask) throws SQLException { //update session
        //pobranie ilości znajdującej się w koszyku
        String sql = "SELECT `quantity-b` FROM `basket` "
                   + "WHERE `id_session-b` = ? AND `id_prod-b` = ?"; 
        connect();
       int result = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, bask.getSession());
            statement.setInt(2, bask.getIdProd());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                     result = resultSet.getInt("quantity-b");
                }
            }
        }
        disconnect();
        //uaktualnienie ilości
        sql = "UPDATE `basket` SET `quantity-b` = ? + "+result
             +" WHERE `id_session-b` = ? AND `id_prod-b` = ?";
        
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, bask.getQuantity());
            statement.setString(2, bask.getSession());
            statement.setInt(3, bask.getIdProd());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnianie ilości produktów w koszyku dla użytkownika
    public boolean updateBaskUsr(Basket bask) throws SQLException { //update session
        //pobranie ilości znajdującej się w koszyku
        String sql = "SELECT `quantity-b` FROM `basket` "
                   + "WHERE `id_user-b` = ? AND `id_prod-b` = ?"; 
        connect();
       int result = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, bask.getIdUser());
            statement.setInt(2, bask.getIdProd());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                     result = resultSet.getInt("quantity-b");
                }
            }
        }
        disconnect();
        //uaktualnienie ilości
        sql = "UPDATE `basket` SET `quantity-b` = ? + "+result
             +" WHERE `id_user-b` = ? AND `id_prod-b` = ?";
        
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, bask.getQuantity());
            statement.setInt(2, bask.getIdUser());
            statement.setInt(3, bask.getIdProd());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie koszyka użytkownika
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `basket` "
                   + "WHERE `id_user-b` = ?";
         
        connect();
         
        boolean deleted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            deleted = statement.executeUpdate() > 0;
        }
        disconnect();
        return deleted;
    }
    
    //usunięcie koszyka dla sesji
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM `basket` "
                   + "WHERE `id_session-b` = ?";
         
        connect();
         
        boolean deleted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, id);
            deleted = statement.executeUpdate() > 0;
        }
        disconnect();
        return deleted;
    }
    
    //usunięcie jednego produktu dla użytkownika
    public boolean deleteUsrId(int id, int idP) throws SQLException {
        String sql = "DELETE FROM `basket` "
                   + "WHERE `id_user-b` = ?  AND `id_prod-b` = ?";
         
        connect();
         
        boolean deleted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, idP);
            deleted = statement.executeUpdate() > 0;
        }
        disconnect();
        return deleted;
    }
    
    //usunięcie jednego produktu dla sesji
    public boolean deleteSessId(String id, int idP) throws SQLException {
        String sql = "DELETE FROM `basket` "
                   + "WHERE `id_session-b` = ? AND `id_prod-b` = ?";
         
        connect();
         
        boolean deleted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.setInt(2, idP);
            deleted = statement.executeUpdate() > 0;
        }
        disconnect();
        return deleted;
    }
   
    //usunięcie koszyka po wygaśnięciu sesji
    public boolean deleteSessTime() throws SQLException {
        Date date =  new Date(); 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        String now = formatter.format(date);
        
        Basket bask;
        List<Basket> listBask = new ArrayList<>();
        String sql = "SELECT DISTINCT `expires-b`,`id_session-b` FROM `basket` "
                   + "WHERE `expires-b` < ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, now);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    String exp = resultSet.getString("expires-b");
                    String ses = resultSet.getString("id_session-b");
                    
                    bask = new Basket(ses, exp);
                    listBask.add(bask);
                }
            }
        }
        disconnect();
        boolean deletedList = true;
        sql = "DELETE FROM `basket` "
            + "WHERE `id_session-b` = ?";
        
        for (Basket item : listBask) {
            connect();
            boolean deleted;
            try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
                statement.setString(1, item.getSession());
                deleted = statement.executeUpdate() > 0;
                if(deleted == false) deletedList = false;
            }
        disconnect();
        }
        return deletedList;
    }
}
    