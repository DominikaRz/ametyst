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
import jwl.model.dict.Color;
//import org.jsoup.Jsoup;


public class ColorDAO extends Connect implements DAO<Color> {
  
//-----Konstruktor-----  
    public ColorDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie koloru po konstruktorze 
    public boolean create(Color col) throws SQLException {
        String sql = "INSERT INTO `color`(`id_col`, `name-cl`)"
                     + "VALUES (?, ?)";
        connect();
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, col.getId());
            statement.setString(2, col.getName());
            inserted = statement.executeUpdate() > 0; 
        }
        disconnect();
        return inserted;
    }
    
    //utworzenie koloru po nazwie
    public boolean create(String name) throws SQLException {
        String sql = "INSERT INTO `color`(`name-cl`)"
                     + "VALUES (?)";
        connect();
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie wszystkich kolorów
    public List<Color> read() throws SQLException {
        Color col;
        List<Color> lictCol = new ArrayList<>();
        
        String sql = "SELECT `id_col`, `name-cl` FROM `color` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_col");
                    String name = resultSet.getString("name-cl");
                    
                    col = new Color(id, name);
                    lictCol.add(col);
                }
            }
        disconnect();
        return lictCol;
    }
    
    //pobranie nazwy koloru po identyfikatorze
    public Color getName(int id) throws SQLException {
        Color col = null;
        String sql = "SELECT `name-cl` FROM `color` WHERE `id_col` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-cl");
                    
                    col = new Color(id, name);
                }
            }
        }
        disconnect();
        return col;
    }
    
    //pobranie identyfikatora koloru po nazwie
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_col` FROM `color` WHERE `name-cl` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id  = Integer.parseInt(resultSet.getString("id_col"));
                }
            }
        }
        disconnect();
        return id;
    }

    //pobranie nazwy koloru do łańcucha zanków
    public String getNamec(int id) throws SQLException {
        String sql = "SELECT `name-cl` FROM `color` WHERE `id_col` = ?"; 
        connect();
        
       String name = ""; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    name  = resultSet.getString("name-cl");
                }
            }
        }
        disconnect();
        return name;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie nazwy koloru
    public boolean update(Color col) throws SQLException {
        String sql = "UPDATE `color` SET `name-cl`= ? "
                    +" WHERE `id_col` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, col.getName());
            statement.setInt(2, col.getId());
            updated = statement.executeUpdate() > 0; //
            statement.close();
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie koloru
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `color` WHERE `id_col` = ?";
         
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
