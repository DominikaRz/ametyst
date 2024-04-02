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
import jwl.model.dict.Shape;

public class ShapeDAO  extends Connect implements DAO<Shape> {
 
//-----Konstruktor-----  
    public ShapeDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie kształtu
    public boolean create(Shape shap) throws SQLException {
        String sql = "INSERT INTO `shape`(`name-sh`)"
                     + "VALUES (?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, shap.getName());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie pełnej listy kształtów
    public List<Shape> read() throws SQLException {
        Shape shp;
        List<Shape> listShap = new ArrayList<>();
        
        String sql = "SELECT `id_shap`, `name-sh` FROM `shape` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_shap");
                    String name = resultSet.getString("name-sh");
                    
                    shp = new Shape(id, name);
                    listShap.add(shp);
                }
            }
        disconnect();
        return listShap;
    }
    
    //pobranie kształtu po identyfikatorze
    public Shape read(int id) throws SQLException {
        Shape shap = null;
        String sql = "SELECT `name-sh` FROM `shape` WHERE `id_shap` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-sh");
                    
                    shap = new Shape(id, name);
                }
            }
        }
        disconnect();
        return shap;
    }
    
    //pobranie identyfikatora kształtu po nazwie
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_shap` FROM `shape` WHERE `name-sh` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_shap"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //ponranie nazwy pształtu po identyfikatorze
    public String getName(int id) throws SQLException {
        String sql = "SELECT `name-sh` FROM `shape` WHERE `id_shap` = ?"; 
        connect();
        
       String name = ""; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    name= resultSet.getString("name-sh");
                }
            }
        }
        disconnect();
        return name;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie kształtu
    public boolean update(Shape shap) throws SQLException {
        String sql = "UPDATE `shape` SET `name-sh`= ? "
                    +" WHERE `id_shap` = ?";
        connect();
        
         
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, shap.getName());
            statement.setInt(2, shap.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie kształtu
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `shape` WHERE `id_shap` = ?";
         
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
