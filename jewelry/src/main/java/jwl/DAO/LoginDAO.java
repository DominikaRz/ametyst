/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 20.05.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import java.util.*;
import jwl.model.Login;

public class LoginDAO extends Connect implements DAO<Login>{
    
//-----Konstruktor-----      
    public LoginDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
   //----CREATE-----  Tworzenie danych 
    @Override
    //utworzenie danych logowania
    public boolean create(Login login) throws SQLException {
        String sql = "INSERT INTO `login` (`id_user-l`, `login`, `password`, `last_log`) "
                     + "VALUES (?, ?, ?, ?)";
        //SHA2(?, 384)   SHA1(?)
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, login.getId());
            statement.setString(2, login.getLogin());
            statement.setString(3, login.getPassw());
            statement.setString(4, login.getLast_log());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
   
   //----READ-----  Czytanie danych
    //pobranie danych logowania dla danego loginu użytkownika (e-mail)
    public Login read(String txt) throws SQLException {
        Login login = null;
        String sql = "SELECT `id_user-l`, `password`, `last_log` FROM `login` "
                + "WHERE `login` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, txt);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer id = resultSet.getInt("id_user-l");
                    String passw = resultSet.getString("password");
                    String last_log = resultSet.getString("last_log");
                    
                    login = new Login(id, txt, passw, last_log);
                }
            }
        }
        disconnect();
        return login;
    }
    
    //pobranie danych logowania dla identyfikatora użytkownika
    public Login read(int id) throws SQLException {
        Login login = null;
        String sql = "SELECT `login`, `password`, `last_log` FROM `login` "
                + "WHERE `id_user-l` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String log = resultSet.getString("login");
                    String passw = resultSet.getString("password");
                    String last_log = resultSet.getString("last_log");
                    
                    login = new Login(id, log, passw, last_log);
                }
            }
        }
        disconnect();
        return login;
    }
      
    //sprawdzenie czy hasło i loin istnieją oraz pobranie identyfikatora i ostatniego logowania
    public Login check(String log, String pass) throws SQLException {
        Login login = null;
        String sql = "SELECT `id_user-l`, `last_log` FROM `login` "
                + "WHERE `login` = ? AND `password` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, log);
            statement.setString(2, pass);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer id = resultSet.getInt("id_user-l");
                    String last_log = resultSet.getString("last_log");
                    
                    login = new Login(id, log, pass, last_log);
                }
            }
        }
        disconnect();
        return login;
    }
      
    //pobranie identyfikatora odpowiadającego loginowi
    public Login getID(String log) throws SQLException {
        Login login = null;
        String sql = "SELECT `id_user-l` FROM `login` "
                + "WHERE `login` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, log);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer id = resultSet.getInt("id_user-l");
                    
                    login = new Login(id, log);
                }
            }
        }
        disconnect();
        return login;
    }
    
    //pobranie ostatniej daty logowania
    public String getLastLog(String log) throws SQLException {
        String sql = "SELECT `last_log` FROM `login` "
                + "WHERE `login` = ?"; 
        connect();
         
       String lastLog = ""; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, log);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    lastLog = resultSet.getString("last_log");
                }
            }
        }
        disconnect();
        return lastLog;
    }

   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie wszystkich danych logowania
    public boolean update(Login login) throws SQLException {
        String sql = "UPDATE `login` SET `login` = ?, `password` = ?, `last_log` = ?"
                    +" WHERE `id_user-l` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, login.getLogin());
            statement.setString(2, login.getPassw());
            statement.setString(3, login.getLast_log());
            statement.setInt(4, login.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;     
    }
     
    //uaktualnienie hasła
    public boolean updatePass(Login login) throws SQLException {
        String sql = "UPDATE `login` SET `password` = ?"
                    +" WHERE `id_user-l` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, login.getPassw());
            statement.setInt(2, login.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;     
    }
     
    //uaktualnienie daty ostatniego logowania
    public boolean updateLastLog(Login login) throws SQLException {
        String sql = "UPDATE `login` SET `last_log` = ?"
                    +" WHERE `id_user-l` = ?";
        connect();
        
         
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, login.getLast_log());
            statement.setInt(2, login.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;     
    }
    
   //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie danych logowania
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `login` WHERE `id_user-l` = ?";
         
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

