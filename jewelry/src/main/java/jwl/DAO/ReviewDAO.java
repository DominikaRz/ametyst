/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 26.08.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import java.util.*;

import jwl.model.Review;

public class ReviewDAO extends Connect implements DAO<Review> {
  
//-----Konstruktor-----  
    public ReviewDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //tworzenie recenzji
    public boolean create(Review rew) throws SQLException {
        String sql = "INSERT INTO `review`(`id_prod-rw`, `id_order-rw`, "
                   + "`name-rw`, `stars`, `content`, `publication`)"
                     + "VALUES (?, ?, ?, ?, ?, ?)";
        connect();
        boolean inserted;
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, rew.getIdProd());
            statement.setInt(2, rew.getIdOrder());
            statement.setString(3, rew.getName());
            statement.setInt(4, rew.getStars());
            statement.setString(5, rew.getContent());
            statement.setString(6, rew.getPublication());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ----- Czytanie danych
    //pobranie listy wszystkich recenzji
    public List<Review> read() throws SQLException {
        Review rew;
        List<Review> lictCol = new ArrayList<>();
        
        String sql = "SELECT `id_rev`, `id_prod-rw`, `id_order-rw`, `name-rw`, `stars`, "
                + "`content`, `publication`, `reply`, `response` FROM `review` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_rev");
                    int idP = resultSet.getInt("id_prod-rw");
                    int idO = resultSet.getInt("id_order-rw");
                    String name = resultSet.getString("name-rw");
                    int stars = resultSet.getInt("stars");
                    String cont = resultSet.getString("content");
                    String pub = resultSet.getString("publication");
                    String rep = resultSet.getString("reply");
                    String resp = resultSet.getString("response");
                    
                    rew = new Review(id, idP, idO, name, stars, cont, pub, rep, resp);
                    lictCol.add(rew);
                }
            }
        disconnect();
        return lictCol;
    }
    
    //pobranie recenzji dla konkretnego produktu
    public List<Review> read(int idP) throws SQLException {
        Review rew;
        List<Review> listRew = new ArrayList<>();
        
        String sql = "SELECT `id_rev`, `id_order-rw` , `name-rw`, `stars`, "
                + "`content`, `publication`, `reply`, `response` "
                + " FROM `review` WHERE `id_prod-rw` = ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, idP);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_rev");
                    int idO = resultSet.getInt("id_order-rw");
                    String name = resultSet.getString("name-rw");
                    int stars = resultSet.getInt("stars");
                    String cont = resultSet.getString("content");
                    String pub = resultSet.getString("publication");
                    String rep = resultSet.getString("reply");
                    String resp = resultSet.getString("response");
                    
                    rew = new Review(id, idP, idO, name, stars, cont, pub, rep, resp);
                    listRew.add(rew);
                }
            }
        }
        disconnect();
        return listRew;
    }
    
    //pobranie jednej recenzji po identyfikatorze
    public Review readOne(int id) throws SQLException {
        Review rew = null;
        String sql = "SELECT `id_prod-rw`, `id_order-rw` , `name-rw`, `stars`, "
                + "`content`, `publication`, `reply`, `response` "
                + " FROM `review` WHERE `id_rev` = ?";
        connect();
         
         try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idP = resultSet.getInt("id_prod-rw");
                    int idO = resultSet.getInt("id_order-rw");
                    String name = resultSet.getString("name-rw");
                    int stars = resultSet.getInt("stars");
                    String cont = resultSet.getString("content");
                    String pub = resultSet.getString("publication");
                    String rep = resultSet.getString("reply");
                    String resp = resultSet.getString("response");
                    
                    rew = new Review(id, idP, idO, name, stars, cont, pub, rep, resp);
                }
            }
        }
        disconnect();
        return rew;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie recenzji dla użytkownika
    public boolean update(Review rew) throws SQLException {
        String sql = "UPDATE `review` SET `id_prod-rw` = ?,`id_order-rw` = ?,"
                + "`name-rw` = ?,`stars` = ?,`content` = ?,`publication` = ? "
                    +" WHERE `id_rev` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, rew.getIdProd());
            statement.setInt(2, rew.getIdOrder());
            statement.setString(3, rew.getName());
            statement.setInt(4, rew.getStars());
            statement.setString(5, rew.getContent());
            statement.setString(6, rew.getPublication());
            statement.setInt(7, rew.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
        
    }
    
    //uaktualnienie odpowiedzi korektora 
    public boolean updateForCensor(Review rew) throws SQLException {
        String sql = "UPDATE `review` SET `reply` = ?,`response` = ? "
                    +" WHERE `id_rev` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, rew.getReply());
            statement.setString(2, rew.getResponse());
            statement.setInt(3, rew.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie recenzji
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `review` WHERE `id_rev` = ?";
         
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
