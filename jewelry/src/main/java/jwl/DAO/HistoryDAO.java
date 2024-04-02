/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 26.08.2021 r.
 */
package jwl.DAO;

import java.sql.*;
import java.util.*;

import jwl.model.History;

public class HistoryDAO extends Connect implements DAO<History>  {
  
//-----Konstruktor-----  
    public HistoryDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----   Tworzenie danych
    @Override
    //utworzenie historii
    public boolean create(History his) throws SQLException {
        String sql = "INSERT INTO `history`(`id_user-h`, `id_act-h`, "
                   + "`description-h`, `date-h`, `query_before-h`, `query_after-h`, `modify-h`) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        connect();
        
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, his.getIdUser());
            statement.setInt(2, his.getIdAct());
            statement.setString(3, his.getDescription());
            statement.setString(4, his.getDate());
            statement.setString(5, his.getQuery_b());
            statement.setString(6, his.getQuery_a());
            statement.setString(7, his.getModify());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    
   //----READ----- Czytanie danych
    //odczytanie wszystkich danych z tabeli historii
    public List<History> read() throws SQLException {
        History his;
        List<History> listHis = new ArrayList<>();
        
        String sql = "SELECT  `id_his`, `id_user-h`, `id_act-h`, `description-h`, "
                + "`date-h`, `query_before-h`, `query_after-h`, `modify-h` FROM `history` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_his");
                    int idU = resultSet.getInt("id_user-h");
                    int idA = resultSet.getInt("id_act-h");
                    String desc = resultSet.getString("description-h");
                    String date = resultSet.getString("date-h");
                    String query_b = resultSet.getString("query_before-h");
                    String query_a = resultSet.getString("query_after-h");
                    String mod = resultSet.getString("modify-h");
                    
                    his = new History(id, idU, idA, desc, date, query_b, query_a, mod);
                    listHis.add(his);
                }
            }
        disconnect();
        return listHis;
    }
   
    //odczytanie wszystkich danych z tabeli historii dla danego użytkownika
    public List<History> readUsr(int idU) throws SQLException {
        History his;
        List<History> listHis = new ArrayList<>();
        
        String sql = "SELECT `id_his`, `id_act-h`, `description-h`, "
                + "`date-h`, `query_before-h`, `query_after-h`, `modify-h` FROM `history` "
                + "WHERE `id_user-h` = ? ORDER BY `date-h` DESC";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, idU);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_his");
                    int idA = resultSet.getInt("id_act-h");
                    String desc = resultSet.getString("description-h");
                    String date = resultSet.getString("date-h");
                    String query_b = resultSet.getString("query_before-h");
                    String query_a = resultSet.getString("query_after-h");
                    String mod = resultSet.getString("modify-h");
                    
                    his = new History(id, idU, idA, desc, date, query_b, query_a, mod);
                    listHis.add(his);
                }
            }
        }
        disconnect();
        return listHis;
    }
    
    //odczytanie jednego rekordu historii
    public History read(int id) throws SQLException {
        History his = null;
        String sql = "SELECT  `id_user-h`, `id_act-h`, `description-h`, "
                   + "`date-h`, `query_before-h`, `query_after-h`, `modify-h` FROM `history` "
                   + "WHERE `id_his` = ?";
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idU = resultSet.getInt("id_user-h");
                    int idA = resultSet.getInt("id_act-h");
                    String desc = resultSet.getString("description-h");
                    String date = resultSet.getString("date-h");
                    String query_b = resultSet.getString("query_before-h");
                    String query_a = resultSet.getString("query_after-h");
                    String mod = resultSet.getString("modify-h");
                    
                    his = new History(id, idU, idA, desc, date, query_b, query_a, mod);
                }
            }
        }
        disconnect();
        return his;
    }
    
    //sprawdzenie zapytań
    public History checkQ(int id) throws SQLException {
        History his = null;
        String sql = "SELECT `query_before-h`, `query_after-h` FROM `history` "
                   + "WHERE `id_his` = ?";
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String query_b = resultSet.getString("query_before-h");
                    String query_a = resultSet.getString("query_after-h");
                    
                    his = new History(id, query_b, query_a);
                }
            }
        }
        disconnect();
        return his;
    }
    
    //sprawdzenie czy użytkownik posiada historię 
    public Boolean ifUsrHasHistory(int idUsr) throws SQLException {
        
        String sql = "SELECT CASE WHEN EXISTS ( "
                + "SELECT `id_user` FROM `user` WHERE `id_user` IN( "
                + "SELECT `id_user-h` FROM `history`) "
                + "AND `id_user` = ? AND `rank` = 1) THEN TRUE ELSE FALSE END AS bool";
        connect();
        
        Boolean query = true;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, idUsr);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    query = resultSet.getBoolean("bool");
                }
            }
        }
        disconnect();
        return query;
    }
    
    
   //----UPDATE----- Uaktualnianie danych 
    @Override
    //uaktualnienie wszystkich danych historii
    public boolean update(History his) throws SQLException {
        String sql = "UPDATE `history` SET `id_act-h` = ?,`description-h` = ?,"
                   + "`date-h` = ?,`query_before-h` = ?,`query_after-h` = ?, `modify-h`= ?"
                    +" WHERE `id_his` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, his.getIdAct());
            statement.setString(2, his.getDescription());
            statement.setString(3, his.getDate());
            statement.setString(4, his.getQuery_b());
            statement.setString(5, his.getQuery_a());
            statement.setString(6, his.getModify());
            statement.setInt(7, his.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie jednego rekordu historii z danym identyfikatorem
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `history` WHERE `id_his` = ?";
         
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
