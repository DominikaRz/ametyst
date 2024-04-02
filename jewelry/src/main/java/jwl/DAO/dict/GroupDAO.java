/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 23.09.2021 r.
 */

package jwl.DAO.dict;

import java.sql.*;
import java.util.*;

import jwl.DAO.Connect;
import jwl.DAO.DAO;
import jwl.model.dict.Group;

public class GroupDAO extends Connect implements DAO<Group> {
//-----Konstruktor-----  
    public GroupDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie grupy
    public boolean create(Group act) throws SQLException {
        String sql = "INSERT INTO `group-g`(`name-g`) "
                     + "VALUES (?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, act.getName());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie isty wszystkich grup
    public List<Group> read() throws SQLException {
        Group gr;
        List<Group> listGr = new ArrayList<>();
        
        String sql = "SELECT `id_group`, `name-g` FROM `group-g` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_group");
                    String name = resultSet.getString("name-g");
                    
                    gr = new Group(id, name);
                    listGr.add(gr);
                }
            }
        disconnect();
        return listGr;
    }
    
    //pobranie danej grupy po identyfikatorze
    public Group read(int id) throws SQLException {
        Group gr = null;
        String sql = "SELECT `name-g` FROM `group-g` WHERE `id_group` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-a");
                    gr = new Group(id, name);
                }
            }
        }
        disconnect();
        return gr;
    }
    
    //pobranie identyfikatora danej grupy po nazwie 
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_group` FROM `group-g` WHERE `name-g` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id  = Integer.parseInt(resultSet.getString("id_group"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //pobranie liczby grup
    public int readHowMany() throws SQLException {
        String sql = "SELECT COUNT(id_group) AS 'howMany' FROM `group-g`"; 
        connect();
       
       int many = 0;
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    many = resultSet.getInt("howMany");
                }
            }
        }
        disconnect();
        return many;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie grupy
    public boolean update(Group act) throws SQLException {
        String sql = "UPDATE `group-g` SET `name-g`= ? "
                    +" WHERE `id_group` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, act.getName());
            statement.setInt(2, act.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie grupy
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `group-g`  WHERE `id_group` = ?";
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
