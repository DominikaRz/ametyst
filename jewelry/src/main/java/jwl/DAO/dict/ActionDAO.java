/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 25.08.2021 r.
 */
 //here some database magic happens

package jwl.DAO.dict;

import java.sql.*;
import java.util.*;

import jwl.DAO.Connect;
import jwl.DAO.DAO;
import jwl.model.dict.Action;

public class ActionDAO  extends Connect implements DAO<Action> {
//-----Konstruktor-----  
    public ActionDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie akcji
    public boolean create(Action act) throws SQLException {
        String sql = "INSERT INTO `action`(`name-a`) "
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
    //pobranie listy wszystkich akcji
    public List<Action> read() throws SQLException {
        Action act;
        List<Action> listFb = new ArrayList<>();
        
        String sql = "SELECT `id_action`, `name-a` FROM `action` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_action");
                    String name = resultSet.getString("name-a");
                    
                    act = new Action(id, name);
                    listFb.add(act);
                }
            }
        disconnect();
        return listFb;
    }
    
    //pobranie jednej akcji
    public Action read(int id) throws SQLException {
        Action act = null;
        String sql = "SELECT `name-a` FROM `action` WHERE `id_action` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-a");
                    act = new Action(id, name);
                }
            }
        }
        disconnect();
        return act;
    }
    
    //pobranie identyfikatora akcji po nazwie
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_action` FROM `action` WHERE `name-sh` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_action"));
                }
            }
        }
        disconnect();
        return id;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie nazwy akcji
    public boolean update(Action act) throws SQLException {
        String sql = "UPDATE `action` SET `name-a`= ? "
                    +" WHERE `id_action` = ?";
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
    //usunięcie akcji
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `action`  WHERE `id_action` = ?";
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
