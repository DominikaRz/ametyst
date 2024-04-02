/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 30.05.2021 r.
 */

package jwl.DAO.dict;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jwl.DAO.Connect;
import jwl.DAO.DAO;
import jwl.model.dict.Rank;

public class RankDAO extends Connect implements DAO<Rank> {
  
//-----Konstruktor-----  
    public RankDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie rangi
    public boolean create(Rank rnk) throws SQLException {
        String sql = "INSERT INTO `rank`(`name-r`)"
                     + "VALUES (?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, rnk.getName());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ----- Czytanie danych
    //pobranie danej pangi po identyfikatorze
    public Rank read(int id) throws SQLException {
        Rank rnk = null;
        String sql = "SELECT `name-r` FROM `rank` WHERE `id_rank` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-r");
                    
                    rnk = new Rank(id, name);
                }
            }
        }
        disconnect();
        return rnk;
    }
    
    //pobranie pełnej listy rang
    public List<Rank> read() throws SQLException {
        Rank rk;
        List<Rank> listRk = new ArrayList<>();
        
        String sql = "SELECT `id_rank`, `name-r` FROM `rank` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_rank");
                    String name = resultSet.getString("name-r");
                    
                    
                    rk = new Rank(id, name);
                    listRk.add(rk);
                }
            }
        disconnect();
        return listRk;
    }
    
    //pobranie identyfikatpra rangi
    public int getId(String name) throws SQLException {
        String sql = "SELECT `id_rank` FROM `rank` WHERE `name-r` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = Integer.parseInt(resultSet.getString("id_rank"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //pobranie nazwy rangi
    public String getName(int id) throws SQLException {
        String sql = "SELECT `name-r` FROM `rank` WHERE `id_rank` = ?"; 
        connect();
        
       String name = ""; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    name = resultSet.getString("name-r");
                }
            }
        }
        disconnect();
        return name;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie rangi
    public boolean update(Rank rnk) throws SQLException {
        String sql = "UPDATE `rank` SET `name-r`= ? "
                    +" WHERE `id_rank` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, rnk.getName());
            statement.setInt(2, rnk.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE----- Usuwanie danych
    //usunięcie rangi
    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `rank` WHERE `id_rank` = ?";
         
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
