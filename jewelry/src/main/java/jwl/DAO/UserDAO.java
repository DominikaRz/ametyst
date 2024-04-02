/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 27.05.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jwl.model.User;

public class UserDAO extends Connect implements DAO<User> {
    
//-----Konstruktor-----  
    public UserDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
  //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie użytkownika z rangą i bez niej
    public boolean create(User user) throws SQLException {
       String sql = "INSERT INTO `user` ( `user`, `rank`, `id_rank-u`, `name-u`, "
                   + "`surname-u`, `email`, `telephone`, `newsletter`, `register`) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
       if(!user.isRank()){
              sql = "INSERT INTO `user` ( `user`, `rank`, `name-u`, "
                   + "`surname-u`, `email`, `telephone`, `newsletter`, `register`) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        }
        
        connect();
         
        boolean inserted; int i = 3;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, user.isUser());
            statement.setBoolean(2, user.isRank());
            if(user.isRank()){ statement.setInt(3, user.getId_rank()); i++;}
            statement.setString(i, user.getName()); i++;
            statement.setString(i, user.getSurname()); i++;
            statement.setString(i, user.getEmail()); i++;
            statement.setString(i, user.getTel()); i++;
            statement.setBoolean(i, user.isNewsletter()); i++;
            statement.setString(i, user.getRegist_date()); i++;
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    //utworzenie użytkownika bez rangi
    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO `user` ( `user`, `rank`, `name-u`, "
                   + "`surname-u`, `email`, `telephone`, `newsletter`, `register`) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, user.isUser());
            statement.setBoolean(2, user.isRank());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getTel());
            statement.setBoolean(7, user.isNewsletter());
            statement.setString(8, user.getRegist_date());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie listy użytkowników z rangą
    public List<User> readRnk() throws SQLException {
        User user;
        List<User> listUs = new ArrayList<>();
        
        String sql = "SELECT `id_user`, `user`, `rank`, `id_rank-u`, `name-u`, `surname-u`, "
                + "`email`, `telephone`, `newsletter`, `register` FROM `user`"
                + "WHERE `rank`=1 AND `id_rank-u` IS NOT NULL;"; 
        connect();
         
       try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_user");
                    boolean usr = resultSet.getBoolean("user");
                    boolean rnk = resultSet.getBoolean("rank");
                    int id_rk = resultSet.getInt("id_rank-u");
                    String name = resultSet.getString("name-u");
                    String sname = resultSet.getString("surname-u");
                    String mail = resultSet.getString("email");
                    String tel = resultSet.getString("telephone");
                    boolean news = resultSet.getBoolean("newsletter");
                    String reg = resultSet.getString("register");
                    
                    user = new User(id, usr, rnk, id_rk, name, sname, mail, tel, news, reg);
                    listUs.add(user);
                }
        }
        disconnect();
        return listUs;
    }
    
    //pobranie danych użytkownika z rangą
    public User read(int id) throws SQLException {
        User user = null;
        String sql = "SELECT `user`, `rank`, `id_rank-u`, `name-u`, `surname-u`, "
                + "`email`, `telephone`, `newsletter`, `register` FROM `user`"
                + "WHERE `id_user` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean usr = resultSet.getBoolean("user");
                    boolean rnk = resultSet.getBoolean("rank");
                    int id_rk = resultSet.getInt("id_rank-u");
                    String name = resultSet.getString("name-u");
                    String sname = resultSet.getString("surname-u");
                    String mail = resultSet.getString("email");
                    String tel = resultSet.getString("telephone");
                    boolean news = resultSet.getBoolean("newsletter");
                    String reg = resultSet.getString("register");
                    
                    user = new User(id, usr, rnk, id_rk, name, sname, mail, tel, news, reg);
                }
            }
        }
        disconnect();
        return user;
    }
    
    //pobranie danych użytkownika bez rangi
    public User readUser(int id) throws SQLException {
        User user = null;
        String sql = "SELECT `user`, `name-u`, `surname-u`, "
                + "`email`, `telephone`, `newsletter`, `register` FROM `user`"
                + "WHERE `id_user` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean usr = resultSet.getBoolean("user");
                    String name = resultSet.getString("name-u");
                    String sname = resultSet.getString("surname-u");
                    String mail = resultSet.getString("email");
                    String tel = resultSet.getString("telephone");
                    boolean news = resultSet.getBoolean("newsletter");
                    String reg = resultSet.getString("register");
                    
                    user = new User(id, usr, name, sname, mail, tel, news, reg);
                }
            }
        }
        disconnect();
        return user;
    }
    
    //pobranie identyfikatora użytkownika
    public int readId(String mail) throws SQLException {
        String sql = "SELECT `id_user` FROM `user`"
                   + "WHERE `email` = ?"; 
        connect();
       int id = 0;  
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, mail);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id_user");
                }
            }
        }
        disconnect();
        return id;
    }
    
    //sprawdzenie czy użytkownik posiada rangę oraz jaki ma email
    public User checkRank(int id) throws SQLException {
        User user = null;
        String sql = "SELECT `rank`, `email` FROM `user`"
                + "WHERE `id_user` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean rnk = resultSet.getBoolean("rank");
                    String mail = resultSet.getString("email");
                    
                    user = new User(id, rnk, mail);
                }
            }
        }
        disconnect();
        return user;
    }
    
    //pobranie statusu i identyfikatora rangi
    public User checkRankId(int id) throws SQLException {
        User user = null;
        String sql = "SELECT `rank`, `id_rank-u`, `email` FROM `user`"
                + "WHERE `id_user` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean rnk = resultSet.getBoolean("rank");
                    int id_rk = resultSet.getInt("id_rank-u");
                    String mail = resultSet.getString("email");
                    
                    user = new User(id, rnk, id_rk, mail);
                }
            }
        }
        disconnect();
        return user;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie danych użytkownika
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE `user` SET `user` = ?,`name-u` = ?,`surname-u` = ?,"
                   + "`telephone` = ?, `newsletter` = ?"
                    +" WHERE `id_user` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, user.isUser());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getTel());
            statement.setBoolean(5, user.isNewsletter());
            statement.setInt(6, user.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uakyualnienie podstawowych danych użytkownika
    public boolean updateUsr(User user) throws SQLException {
        String sql = "UPDATE `user` SET `name-u` = ?,`surname-u` = ?,"
                   + "`telephone` = ?, `newsletter` = ?"
                    +" WHERE `id_user` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getTel());
            statement.setBoolean(4, user.isNewsletter());
            statement.setInt(5, user.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnienie newslettera
    public boolean updateNews(User user) throws SQLException {
        String sql = "UPDATE `user` SET `newsletter` = ?"
                    +" WHERE `id_user` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, user.isNewsletter());
            statement.setInt(2, user.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie użytkownika
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `user` WHERE `id_user` = ?";
         
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

