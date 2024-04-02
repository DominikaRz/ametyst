/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 27.05.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jwl.model.UserMeta;

public class UserMDAO extends Connect implements DAO<UserMeta>{
      
  //-----Konstruktor-----  
    public UserMDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
  //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie adresu firmy dla zalogowanego uzytkownika
    public boolean create(UserMeta usrm) throws SQLException {
         String sql = "INSERT INTO `user-meta` (`logged`, `id_user-m`, "
                 + "`firm`, `name_firm`, `nip_firm`, `firm_email`, `firm_tel`, `adr_str`, "
                 + "`adr_nr`, `adr_town`, `adr_state`, `adr_code`, `adr_post`, `adr_count`)"
                 + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, usrm.isLogged());
            statement.setInt(2, usrm.getIdUsr());
            statement.setBoolean(3, usrm.isFirm());
            statement.setString(4, usrm.getFirm_name());
            statement.setString(5, usrm.getFirm_nip());
            statement.setString(6, usrm.getFirm_email());
            statement.setString(7, usrm.getFirm_tel());
            statement.setString(8, usrm.getAdr_str());
            statement.setString(9, usrm.getAdr_nr());
            statement.setString(10, usrm.getAdr_town());
            statement.setString(11, usrm.getAdr_state());
            statement.setString(12, usrm.getAdr_code());
            statement.setString(13, usrm.getAdr_post());
            statement.setString(14, usrm.getAdr_count());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    //utworzenie adresu firmy dla niezalogowanego użytkownika
    public boolean createFirmN(UserMeta usrm) throws SQLException {
         String sql = "INSERT INTO `user-meta` (`logged`, "
                 + "`firm`, `name_firm`, `nip_firm`, `firm_email`, `firm_tel`, `adr_str`, "
                 + "`adr_nr`, `adr_town`, `adr_state`, `adr_code`, `adr_post`, `adr_count`)"
                 + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, usrm.isLogged());
            statement.setBoolean(2, usrm.isFirm());
            statement.setString(3, usrm.getFirm_name());
            statement.setString(4, usrm.getFirm_nip());
            statement.setString(5, usrm.getFirm_email());
            statement.setString(6, usrm.getFirm_tel());
            statement.setString(7, usrm.getAdr_str());
            statement.setString(8, usrm.getAdr_nr());
            statement.setString(9, usrm.getAdr_town());
            statement.setString(10, usrm.getAdr_state());
            statement.setString(11, usrm.getAdr_code());
            statement.setString(12, usrm.getAdr_post());
            statement.setString(13, usrm.getAdr_count());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    //utworzenie adresu dla zalogowanego uzytkownika
    public boolean createAdrL(UserMeta usrm) throws SQLException {
         String sql = "INSERT INTO `user-meta` (`logged`, `id_user-m`, "
                 + "`firm`, `adr_str`, `adr_nr`, `adr_town`, `adr_state`, "
                 + "`adr_code`, `adr_post`, `adr_count`)"
                 + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, usrm.isLogged());
            statement.setInt(2, usrm.getIdUsr());
            statement.setBoolean(3, usrm.isFirm());
            statement.setString(4, usrm.getAdr_str());
            statement.setString(5, usrm.getAdr_nr());
            statement.setString(6, usrm.getAdr_town());
            statement.setString(7, usrm.getAdr_state());
            statement.setString(8, usrm.getAdr_code());
            statement.setString(9, usrm.getAdr_post());
            statement.setString(10, usrm.getAdr_count());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    //utworzenie adresu dla niezalogowanego użytkownika
    public boolean createAdrN(UserMeta usrm) throws SQLException {
         String sql = "INSERT INTO `user-meta` (`logged`, "
                 + "`firm`, `adr_str`, `adr_nr`, `adr_town`, `adr_state`, "
                 + "`adr_code`, `adr_post`, `adr_count`)"
                 + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, usrm.isLogged());
            statement.setBoolean(2, usrm.isFirm());
            statement.setString(3, usrm.getAdr_str());
            statement.setString(4, usrm.getAdr_nr());
            statement.setString(5, usrm.getAdr_town());
            statement.setString(6, usrm.getAdr_state());
            statement.setString(7, usrm.getAdr_code());
            statement.setString(8, usrm.getAdr_post());
            statement.setString(9, usrm.getAdr_count());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }

    //----READ-----  Czytanie danych
    //pobranie wszystkich adresów dla danego użytkownika
    public List<UserMeta> read(int id) throws SQLException {
        UserMeta userm;
        List<UserMeta> lictCol = new ArrayList<>();
        
        String sql = "SELECT `logged`, `id_user_m`, `firm`, `name_firm`, `nip_firm`, "
                + "`firm_email`, `firm_tel`, `adr_str`, `adr_nr`, `adr_town`, "
                + "`adr_state`, `adr_code`, `adr_post`, `adr_count` FROM `user-meta`"
                + "WHERE `id_user-m` = ?"; 
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    int idM = resultSet.getInt("id_user_m");
                    boolean log = resultSet.getBoolean("logged");
                    boolean fr = resultSet.getBoolean("firm");
                    String nameF = resultSet.getString("name_firm");
                    String nipF = resultSet.getString("nip_firm");
                    String mailF = resultSet.getString("firm_email");
                    String telF = resultSet.getString("firm_tel");
                    String str = resultSet.getString("adr_str");
                    String nr = resultSet.getString("adr_nr");
                    String twn = resultSet.getString("adr_town");
                    String stat = resultSet.getString("adr_state");
                    String cod = resultSet.getString("adr_code");
                    String pst = resultSet.getString("adr_post");
                    String ctr = resultSet.getString("adr_count");
                    
                    userm = new UserMeta(idM, id, log, fr, nameF, nipF, mailF, telF, 
                           str, nr, twn, stat, cod, pst, ctr);
                    lictCol.add(userm);
                }
            }
        }
        disconnect();
        return lictCol;
    }
    
    //pobranie adresów dla pracownika
    public List<UserMeta> readForWorker() throws SQLException {
        UserMeta user;
        List<UserMeta> listUs = new ArrayList<>();
        
        String sql = "SELECT `id_user_m`, `id_user`, `name-u`, `surname-u`, `email`, `telephone` "
                + "FROM `user`, `user-meta` WHERE `id_user`=`id_user-m`"; 
        connect();
         
       try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int idM = resultSet.getInt("id_user_m");
                    int id = resultSet.getInt("id_user");
                    String name = resultSet.getString("name-u");
                    String sname = resultSet.getString("surname-u");
                    String mail = resultSet.getString("email");
                    String tel = resultSet.getString("telephone");
                    
                    user = new UserMeta(idM, id, name, sname, mail, tel);
                    listUs.add(user);
                }
        }
        disconnect();
        return listUs;
    }
    
    //pobranie jednego adresu dla pracownika
    public UserMeta readForWorker(int idM) throws SQLException {
        UserMeta user = null;
        String sql = "SELECT `id_user_m`, `id_user`, `name-u`, `surname-u`, `email`, `telephone` "
                + "FROM `user`, `user-meta` WHERE `id_user`=`id_user-m` AND `id_user_m` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, idM);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_user");
                    String name = resultSet.getString("name-u");
                    String sname = resultSet.getString("surname-u");
                    String mail = resultSet.getString("email");
                    String tel = resultSet.getString("telephone");
                    
                    user = new UserMeta(idM, id, name, sname, mail, tel);
                }
            }
        }
        disconnect();
        return user;
    }
        
    //sprawdzanie identyfikatora po adresie zamówienia
    public int read(UserMeta usrm) throws SQLException {
        
        String sql = "SELECT `id_user_m` FROM `user-meta` "
                + "WHERE `adr_str` = ? AND `adr_nr` = ? AND `adr_town` = ? "
                + "AND `adr_code` = ? AND `adr_post` = ? AND `adr_state` = ? AND `adr_count` = ? AND `firm` = ?";
        if(usrm.isFirm()){ sql += " AND `name_firm` = ? AND `nip_firm` = ? "
                + "AND `firm_email` = ? AND `firm_tel` = ? ";}
        if(usrm.isLogged()){ sql += " AND `logged` = ? AND `id_user-m` = ? "; }
        connect();
         
       int idU = 0, i=9;
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, usrm.getAdr_str());
            statement.setString(2, usrm.getAdr_nr());
            statement.setString(3, usrm.getAdr_town());
            statement.setString(4, usrm.getAdr_code());
            statement.setString(5, usrm.getAdr_post());
            statement.setString(6, usrm.getAdr_state());
            statement.setString(7, usrm.getAdr_count());
            statement.setBoolean(8, usrm.isFirm());
           if(usrm.isFirm()){ 
            statement.setString(i, usrm.getFirm_name()); i++;
            statement.setString(i, usrm.getFirm_nip()); i++;
            statement.setString(i, usrm.getFirm_email()); i++;
            statement.setString(i, usrm.getFirm_tel()); i++;
           }
           if(usrm.isLogged()){
             statement.setBoolean(i, usrm.isLogged()); i++;  
             statement.setInt(i, usrm.getIdUsr()); i++;  
           }
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    idU = resultSet.getInt("id_user_m");
                }
            }
        }
        disconnect();
        return idU;
    }
    
    //pobranie adresu dla zalogowanego użytkownika
    public UserMeta readAdrL(int id) throws SQLException {
        UserMeta userm = null;
        String sql = "SELECT `id_user`, `name-u`, `surname-u`, `email`, `telephone`, "
                + "`logged`, `id_user-m`, `firm_tel`, `adr_str`, `adr_nr`, "
                + "`adr_town`,`adr_state`, `adr_code`, `adr_post`, `adr_count` "
                + "FROM `user-meta`, `user` "
                + "WHERE `id_user-m` = `id_user` AND `id_user_m` = ?";
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean log = resultSet.getBoolean("logged");
                    int idU = resultSet.getInt("id_user-m");
                    String str = resultSet.getString("adr_str");
                    String nr = resultSet.getString("adr_nr");
                    String twn = resultSet.getString("adr_town");
                    String stat = resultSet.getString("adr_state");
                    String cod = resultSet.getString("adr_code");
                    String pst = resultSet.getString("adr_post");
                    String ctr = resultSet.getString("adr_count");
                    int idUs = resultSet.getInt("id_user");
                    String name = resultSet.getString("name-u");
                    String sname = resultSet.getString("surname-u");
                    String mail = resultSet.getString("email");
                    String tel = resultSet.getString("telephone");
                    
                   userm = new UserMeta(id, log, idU, str, nr, twn, stat, cod, pst, 
                           ctr, idUs, name, sname, mail, tel);
                }
            }
        }
        disconnect();
        return userm;
    }
    
    //pobranie adresu firmy dla zalogowanego użytkownika
    public UserMeta readFirmL(int id) throws SQLException {
        UserMeta userm = null;
        String sql = "SELECT `id_user`, `name-u`, `surname-u`, `email`, `telephone`,"
                + "`logged`, `id_user-m`, `firm`, `name_firm`, `nip_firm`, "
                + "`firm_email`, `firm_tel`, `adr_str`, `adr_nr`, `adr_town`, "
                + "`adr_state`, `adr_code`, `adr_post`, `adr_count` FROM `user-meta`, `user`"
                + "WHERE `id_user-m` = `id_user` AND `id_user_m` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean log = resultSet.getBoolean("logged");
                    int idU = resultSet.getInt("id_user-m");
                    boolean fr = resultSet.getBoolean("firm");
                    String nameF = resultSet.getString("name_firm");
                    String nipF = resultSet.getString("nip_firm");
                    String mailF = resultSet.getString("firm_email");
                    String telF = resultSet.getString("firm_tel");
                    String str = resultSet.getString("adr_str");
                    String nr = resultSet.getString("adr_nr");
                    String twn = resultSet.getString("adr_town");
                    String stat = resultSet.getString("adr_state");
                    String cod = resultSet.getString("adr_code");
                    String pst = resultSet.getString("adr_post");
                    String ctr = resultSet.getString("adr_count");
                    int idUs = resultSet.getInt("id_user");
                    String name = resultSet.getString("name-u");
                    String sname = resultSet.getString("surname-u");
                    String mail = resultSet.getString("email");
                    String tel = resultSet.getString("telephone");
                    
                   userm = new UserMeta(id, log, idU, fr, nameF, nipF, mailF, telF, 
                           str, nr, twn, stat, cod, pst, ctr, idUs, name, sname, mail, tel);
                }
            }
        }
        disconnect();
        return userm;
    }
    
    //pobranie adresu dla niezalogowanego użytkownika
    public UserMeta readAdrN(int id) throws SQLException {
        UserMeta userm = null;
        String sql = "SELECT `logged`, `firm_tel`, `adr_str`, `adr_nr`, "
                + "`adr_town`,`adr_state`, `adr_code`, `adr_post`, `adr_count` "
                + "FROM `user-meta` "
                + "WHERE `id_user_m` = ?";
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean log = resultSet.getBoolean("logged");
                    String str = resultSet.getString("adr_str");
                    String nr = resultSet.getString("adr_nr");
                    String twn = resultSet.getString("adr_town");
                    String stat = resultSet.getString("adr_state");
                    String cod = resultSet.getString("adr_code");
                    String pst = resultSet.getString("adr_post");
                    String ctr = resultSet.getString("adr_count");
                    
                   userm = new UserMeta(id, log, str, nr, twn, stat, cod, pst, ctr);
                }
            }
        }
        disconnect();
        return userm;
    }
    
    //pobranie adresu firmy dla niezalogowanego użytkownika
    public UserMeta readFirmN(int id) throws SQLException {
        UserMeta userm = null;
        String sql = "SELECT `logged`, `firm`, `name_firm`, `nip_firm`, "
                + "`firm_email`, `firm_tel`, `adr_str`, `adr_nr`, `adr_town`, "
                + "`adr_state`, `adr_code`, `adr_post`, `adr_count` FROM `user-meta`"
                + "WHERE `id_user_m` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean log = resultSet.getBoolean("logged");
                    boolean fr = resultSet.getBoolean("firm");
                    String nameF = resultSet.getString("name_firm");
                    String nipF = resultSet.getString("nip_firm");
                    String mailF = resultSet.getString("firm_email");
                    String telF = resultSet.getString("firm_tel");
                    String str = resultSet.getString("adr_str");
                    String nr = resultSet.getString("adr_nr");
                    String twn = resultSet.getString("adr_town");
                    String stat = resultSet.getString("adr_state");
                    String cod = resultSet.getString("adr_code");
                    String pst = resultSet.getString("adr_post");
                    String ctr = resultSet.getString("adr_count");
                    
                   userm = new UserMeta(id, log, fr, nameF, nipF, mailF, telF, 
                           str, nr, twn, stat, cod, pst, ctr);
                }
            }
        }
        disconnect();
        return userm;
    }
    
    //sprawdzenie czy użytkownik jest użytkownikiem zlogowanym
    public UserMeta checkLogged(int id) throws SQLException {
        UserMeta userm = null;
        String sql = "SELECT `logged` FROM `user-meta`"
                + "WHERE `id_user_m` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean log = resultSet.getBoolean("logged");
                    
                   userm = new UserMeta(id, log);
                }
            }
        }
        disconnect();
        return userm;
    }
    
    //sprawdzenie czy adres należy do firmy
    public UserMeta checkFirm(int id) throws SQLException {
        UserMeta userm = null;
        String sql = "SELECT `firm` FROM `user-meta`"
                + "WHERE `id_user_m` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boolean firm = resultSet.getBoolean("firm");
                    
                   userm = new UserMeta(firm, id);
                }
            }
        }
        disconnect();
        return userm;
    }
    
   //----UPDATE----- Uaktualnianie danych
    @Override
    //uaktualnienie afresu firmy
    public boolean update(UserMeta usrm) throws SQLException {
        String sql = "UPDATE `user-meta` SET `logged` = ?,`firm` = ?, "
                + "`name_firm` = ?,`nip_firm` = ?,`firm_email` = ?,`firm_tel` = ?,"
                + "`adr_str` = ?,`adr_nr` = ?,`adr_town` = ?,`adr_state` = ?,"
                + "`adr_code` = ?,`adr_post` = ?,`adr_count` = ? "
                + " WHERE `id_user_m` = ?";
        connect();
         
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, usrm.isLogged());
            statement.setBoolean(2, usrm.isFirm());
            statement.setString(3, usrm.getFirm_name());
            statement.setString(4, usrm.getFirm_nip());
            statement.setString(5, usrm.getFirm_email());
            statement.setString(6, usrm.getFirm_tel());
            statement.setString(7, usrm.getAdr_str());
            statement.setString(8, usrm.getAdr_nr());
            statement.setString(9, usrm.getAdr_town());
            statement.setString(10, usrm.getAdr_state());
            statement.setString(11, usrm.getAdr_code());
            statement.setString(12, usrm.getAdr_post());
            statement.setString(13, usrm.getAdr_count());
            statement.setInt(14, usrm.getIdMeta());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnienie afresu użytkownika
    public boolean updateAdr(UserMeta usrm) throws SQLException {
        String sql = "UPDATE `user-meta` SET `logged` = ?,`firm` = ?,"
                + "`adr_str` = ?,`adr_nr` = ?,`adr_town` = ?,`adr_state` = ?,"
                + "`adr_code` = ?,`adr_post` = ?,`adr_count` = ? "
                + " WHERE `id_user_m` = ?";
        connect();
         
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setBoolean(1, usrm.isLogged());
            statement.setBoolean(2, usrm.isFirm());
            statement.setString(3, usrm.getAdr_str());
            statement.setString(4, usrm.getAdr_nr());
            statement.setString(5, usrm.getAdr_town());
            statement.setString(6, usrm.getAdr_state());
            statement.setString(7, usrm.getAdr_code());
            statement.setString(8, usrm.getAdr_post());
            statement.setString(9, usrm.getAdr_count());
            statement.setInt(10, usrm.getIdMeta());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }

  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie adresu
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `user-meta` WHERE `id_user_m` = ?";
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
