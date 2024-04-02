/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 30.05.2021 r.
 */

package jwl.DAO.dict;

import java.sql.*;
import java.util.*;

import jwl.DAO.Connect;
import jwl.DAO.DAO;
import jwl.model.dict.Payment;

public class PaymentDAO  extends Connect implements DAO<Payment> {
  
//-----Konstruktor-----  
    public PaymentDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie formy płatności
    public boolean create(Payment pay) throws SQLException {
        String sql = "INSERT INTO `payment`(`name-py`, `cat-py`)"
                   + "VALUES (?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, pay.getName());
            statement.setString(2, pay.getCategory());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie pełnej listy płatności
    public List<Payment> read() throws SQLException {
        Payment pay;
        List<Payment> listPay = new ArrayList<>();
        
        String sql = "SELECT `id_pay`, `name-py`, `cat-py` FROM `payment` "
                + "WHERE 1 ORDER BY `cat-py` DESC";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_pay");
                    String name = resultSet.getString("name-py");
                    String cat = resultSet.getString("cat-py");
                    
                    pay = new Payment(id, name, cat);
                    listPay.add(pay);
                }
            }
        disconnect();
        return listPay;
    }
    
    //pobranie danej formy płatności
    public Payment read(int id) throws SQLException {
        Payment pay = null;
        String sql = "SELECT `name-py`, `cat-py` FROM `payment` "
                   + "WHERE `id_pay` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-py");
                    String cat = resultSet.getString("cat-py");
                    
                    pay = new Payment(id, name, cat);
                }
            }
        }
        disconnect();
        return pay;
    }
    
    //pobranie identyfikatora płatności po nazwie i kategorii
    public int getId(String name, String cat) throws SQLException {
        String sql = "SELECT `id_pay` FROM `payment` WHERE `name-py` = ? AND `cat-py` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, cat);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_pay"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //pobranie identyfikatora płatności po nazwie
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_pay` FROM `payment` WHERE `name-py` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_pay"));
                }
            }
        }
        disconnect();
        return id;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie formy płatności
    public boolean update(Payment pay) throws SQLException {
        String sql = "UPDATE `payment` SET `name-py`= ?, `cat-py`= ? "
                   + "WHERE `id_pay` = ?";
        connect();
        
         
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, pay.getName());
            statement.setString(2, pay.getCategory());
            statement.setInt(3, pay.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie formy płatności
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `payment` WHERE `id_pay` = ?";
         
        connect();
         
        boolean deleted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            deleted = statement.executeUpdate() > 0;
        }
        disconnect();
        return deleted;
    }
}
