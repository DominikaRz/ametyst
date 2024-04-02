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
import jwl.model.dict.Delivery;

public class DeliveryDAO extends Connect implements DAO<Delivery> {
//-----Konstruktor-----  
    public DeliveryDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie dostawy
    public boolean create(Delivery del) throws SQLException {
        String sql = "INSERT INTO `delivery`(`name-d`, `price-d`)"
                     + "VALUES (?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, del.getName());
            statement.setDouble(2, del.getPrice());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych    
    //pobranie wszystkich metod dostawy
    public List<Delivery> read() throws SQLException {
        Delivery del;
        List<Delivery> licDel = new ArrayList<>();
        
        String sql = "SELECT `id_deliv`, `name-d`, `price-d` FROM `delivery` "
                   + "WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_deliv");
                    String name = resultSet.getString("name-d");
                    double price = resultSet.getDouble("price-d");
                    
                    del = new Delivery(id, name, price);
                    licDel.add(del);
                }
            }
        disconnect();
        return licDel;
    }
    
    //pobranie dostawy po identyfikatorze
    public Delivery read(int id) throws SQLException {
        Delivery del = null;
        String sql = "SELECT `name-d`, `price-d` FROM `delivery` "
                + "WHERE `id_deliv` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-d");
                    double price = resultSet.getDouble("price-d");
                    
                    del = new Delivery(id, name, price);
                }
            }
        }
        disconnect();
        return del;
    }
    
    //pobranie identyfikatora dostawy po nazwie
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_deliv` FROM `delivery` WHERE `name-d` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_deliv"));
                }
            }
        }
        disconnect();
        return id;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie nazwy i ceny dostawy
    public boolean update(Delivery del) throws SQLException {
        String sql = "UPDATE `delivery` SET `name-d`= ?, `price-d`= ?"
                    +" WHERE `id_deliv` = ?";
        connect();
        
         
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, del.getName());
            statement.setDouble(2, del.getPrice());
            statement.setInt(3, del.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie dostawy
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `delivery` WHERE `id_deliv` = ?";
         
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
