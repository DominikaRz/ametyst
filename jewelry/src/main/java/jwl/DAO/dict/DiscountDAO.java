/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 24.08.2021 r.
 */

package jwl.DAO.dict;

import java.sql.*;
import java.util.*;

import jwl.DAO.Connect;
import jwl.DAO.DAO;
import jwl.model.dict.Discount;

public class DiscountDAO extends Connect implements DAO<Discount> {
  
//-----Konstruktor-----  
    public DiscountDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie kodu promocyjnego za pomocą konstruktora
    public boolean create(Discount disc) throws SQLException {
        String sql = "INSERT INTO `discount`(`id_disc`, `name-dc`, `active`, `value`)"
                     + "VALUES (?, ?, ?, ?)";
        connect();
        
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, disc.getId());
            statement.setString(2, disc.getName());
            statement.setBoolean(3, disc.isActive());
            statement.setInt(4, disc.getValue());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    //utworzenie kodu promocyjnego za zmiennych
    public boolean create(String name, Boolean active) throws SQLException {
        String sql = "INSERT INTO `discount`(`name-dc`, `active`)"
                     + "VALUES (?, ?)";
        connect();
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setBoolean(2, active);
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie wszystkich kodów promocyjnych
    public List<Discount> read() throws SQLException {
        Discount disc;
        List<Discount> lictCol = new ArrayList<>();
        
        String sql = "SELECT `id_disc`, `name-dc`, `active`, `value` FROM `discount` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_disc");
                    String name = resultSet.getString("name-dc");
                    Boolean active = resultSet.getBoolean("active");
                    int value = resultSet.getInt("value");
                    
                    disc = new Discount(id, name, active, value);
                    lictCol.add(disc);
                }
            }
        disconnect();
        return lictCol;
    }
    
    //pobranie danego kodu promocyjnego po identyfikatorze
    public Discount read(int id) throws SQLException {
        Discount disc = null;
        String sql = "SELECT `name-dc`, `active`, `value` FROM `discount` WHERE `id_disc` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-dc");
                    Boolean active = resultSet.getBoolean("active");
                    int value = resultSet.getInt("value");
                    
                    disc = new Discount(id, name, active, value);
                }
            }
        }
        disconnect();
        return disc;
    }
   
    //pobranie identyfikatora danego kodu promocyjnego po nazwie
    public Discount read(String name) throws SQLException {
        Discount disc = null;
        String sql = "SELECT `id_disc`, `active` FROM `discount` WHERE `name-dc` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_disc");
                    Boolean active = resultSet.getBoolean("active");
                    
                    disc = new Discount(id, name, active);
                }
            }
        }
        disconnect();
        return disc;
    }
    
    //sprawdzenie czy kod promocyjny jest aktywny
    public Discount isActive(int id) throws SQLException {
        Discount disc = null;
        String sql = "SELECT `active` FROM `discount` WHERE `id_disc` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Boolean active = resultSet.getBoolean("active");
                    
                    disc = new Discount(id, active);
                }
            }
        }
        disconnect();
        return disc;
    }
    
    //pobranie identyfikatora danego kodu promocyjnego po nazwie do zmiennej liczbowej
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_disc` FROM `discount` WHERE `name-dc` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_disc"));
                }
            }
        }
        disconnect();
        return id;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie aktywności i wartości kodu promocyjnego
    public boolean update(Discount disc) throws SQLException {
        String sql = "UPDATE `discount` SET `active`= ?"
                    +" WHERE `id_disc` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, disc.isActive());
            statement.setInt(2, disc.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie kodu promocyjnego
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `discount` WHERE `id_disc` = ?";
         
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
