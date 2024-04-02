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
import jwl.model.dict.Fabric;

public class FabricDAO  extends Connect implements DAO<Fabric> {
//-----Konstruktor-----  
    public FabricDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie materiału
    public boolean create(Fabric fab) throws SQLException {
        String sql = "INSERT INTO `fabric`(`name-fb`)"
                     + "VALUES (?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, fab.getName());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie wszystkich materiałów
    public List<Fabric> read() throws SQLException {
        Fabric fab;
        List<Fabric> listFb = new ArrayList<>();
        
        String sql = "SELECT `id_fabr`, `name-fb` FROM `fabric` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_fabr");
                    String name = resultSet.getString("name-fb");
                    
                    fab = new Fabric(id, name);
                    listFb.add(fab);
                }
            }
        disconnect();
        return listFb;
    }
    
    //pobranie danego materiału po identyfikatorze
    public Fabric read(int id) throws SQLException {
        Fabric fab = null;
        String sql = "SELECT `name-fb` FROM `fabric` WHERE `id_fabr` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-fb");
                    fab = new Fabric(id, name);
                }
            }
        }
        disconnect();
        return fab;
    }
    
    //pobranie identyfikatora danego materiału po nazwie
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_fabr` FROM `fabric` WHERE `name-fb` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_fabr"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //pobranie nazwy danego materiału po identyfikatorze
    public String getName(int id) throws SQLException {
        String sql = "SELECT `name-fb` FROM `fabric` WHERE `id_fabr` = ?"; 
        connect();
        
       String name = ""; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    name = resultSet.getString("name-fb");
                }
            }
        }
        disconnect();
        return name;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie materiału
    public boolean update(Fabric fab) throws SQLException {
        String sql = "UPDATE `fabric` SET `name-fb`= ? "
                    +" WHERE `id_fabr` = ?";
        connect();
        
         
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, fab.getName());
            statement.setInt(2, fab.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie materiału
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `fabric` WHERE `id_fabr` = ?";
         
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
