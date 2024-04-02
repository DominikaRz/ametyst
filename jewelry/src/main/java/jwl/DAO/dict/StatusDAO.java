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
import jwl.model.dict.Status;

public class StatusDAO extends Connect implements DAO<Status> { 
    
//-----Konstruktor-----  
    public StatusDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie statusu
    public boolean create(Status stat) throws SQLException {
        String sql = "INSERT INTO `status`(`name-s`, `descr-s`)"
                     + "VALUES (?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, stat.getName());
            statement.setString(2, stat.getDescription());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie pełnej losty statusów
    public List<Status> read() throws SQLException {
        Status stat;
        List<Status> listStat = new ArrayList<>();
        
        String sql = "SELECT `id_stat`, `name-s`, `descr-s`  FROM `status` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_stat");
                    String name = resultSet.getString("name-s");
                    String desc = resultSet.getString("descr-s");
                    
                    stat = new Status(id, name, desc);
                    listStat.add(stat);
                }
            }
        disconnect();
        return listStat;
    }
    
    //pobranie danych statusu
    public Status read(int id) throws SQLException {
        Status stat = null;
        String sql = "SELECT `name-s`, `descr-s` FROM `status` WHERE `id_stat` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-s");
                    String desc = resultSet.getString("descr-s");
                    
                    stat = new Status(id, name, desc);
                }
            }
        }
        disconnect();
        return stat;
    }
    
    //pobranie identyfikatora statusu
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_stat` FROM `status` WHERE `name-s` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_stat"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //pobranie nazwy statusu
    public String getName(int id) throws SQLException {
        String sql = "SELECT `name-s` FROM `status` WHERE `id_stat`= ?"; 
        connect();
        
       String name = ""; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    name = resultSet.getString("name-s");
                }
            }
        }
        disconnect();
        return name;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie statusu
    public boolean update(Status stat) throws SQLException {
        String sql = "UPDATE `status` SET `name-s`= ? , `descr-s` = ?"
                    +" WHERE `id_stat` = ?";
        connect();
        
         
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, stat.getName());
            statement.setString(2, stat.getDescription());
            statement.setInt(3, stat.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie statusu
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `status` WHERE `id_stat` = ?";
         
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
