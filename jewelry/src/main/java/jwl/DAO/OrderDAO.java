 /**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 26.08.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import java.util.*;
import static java.sql.Types.NULL;

import jwl.model.Order;

public class OrderDAO extends Connect implements DAO<Order> {
  
//-----Konstruktor-----  
    public OrderDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie zamówienia z kodem promocyjnym
    public boolean create(Order ord) throws SQLException {
        String sql = "INSERT INTO `order-o`(`id_pay-o`, `id_user_m-o`, "
                + "`id_del-o`, `deliv_nr-o`, `id_stat-o`, `id_disc-o`,  "
                + "`name-o`, `surname-o`, `email-o`, `telephone-o`, `vat-o`, `sum-o`, "
                + "`comments-o`, `create-o`, `update-o`, `send-o`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, ord.getIdPay());
            statement.setInt(2, ord.getIdUserMeta());
            statement.setInt(3, ord.getIdDeliv());
            statement.setString(4, ord.getDeliv_nr());
            statement.setInt(5, ord.getIdStat());
            statement.setInt(6, ord.getIdDisc());
            statement.setString(7, ord.getName());
            statement.setString(8, ord.getSurname());
            statement.setString(9, ord.getEmail());
            statement.setString(10, ord.getTelephone());
            statement.setInt(11, ord.getVat());
            statement.setDouble(12, ord.getSum());
            statement.setString(13, ord.getComments());
            statement.setString(14, ord.getCreate());
            statement.setString(15, ord.getUpdate());
            statement.setString(16, ord.getSend());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    //utworzenie zamówienia bezz kodu promocyjnego
    public boolean createOrd(Order ord) throws SQLException {
        String sql = "INSERT INTO `order-o`(`id_pay-o`, `id_user_m-o`, "
                + "`id_del-o`, `deliv_nr-o`, `id_stat-o`,  "
                + "`name-o`, `surname-o`, `email-o`, `telephone-o`, `vat-o`, `sum-o`, "
                + "`comments-o`, `create-o`, `update-o`, `send-o`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, ord.getIdPay());
            statement.setInt(2, ord.getIdUserMeta());
            statement.setInt(3, ord.getIdDeliv());
            statement.setString(4, ord.getDeliv_nr());
            statement.setInt(5, ord.getIdStat());
            statement.setString(6, ord.getName());
            statement.setString(7, ord.getSurname());
            statement.setString(8, ord.getEmail());
            statement.setString(9, ord.getTelephone());
            statement.setInt(10, ord.getVat());
            statement.setDouble(11, ord.getSum());
            statement.setString(12, ord.getComments());
            statement.setString(13, ord.getCreate());
            statement.setString(14, ord.getUpdate());
            statement.setString(15, ord.getSend());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ----- Czytanie danych
    //wypisanie wszystkich zamówień
    public List<Order> read() throws SQLException {
        Order ord;
        List<Order> listOd = new ArrayList<>();
        
       String sql = "SELECT `id_order`, `id_pay-o`, `id_user_m-o`, `id_del-o`, `deliv_nr-o`, "
                + "`id_stat-o`, `id_disc-o`, `name-o`, `surname-o`, "
                + "`email-o`, `telephone-o`, `vat-o`, `sum-o`, `comments-o`, `create-o`, "
                + "`update-o`, `send-o` FROM `order-o` WHERE 1"; 
        connect();
        
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_order");
                    int idPy = resultSet.getInt("id_pay-o");
                    int idUM = resultSet.getInt("id_user_m-o");
                    int idDel = resultSet.getInt("id_del-o");
                    String del_nr = resultSet.getString("deliv_nr-o");
                    int idStat = resultSet.getInt("id_stat-o");
                    int idDisc = resultSet.getInt("id_disc-o");
                    String name = resultSet.getString("name-o");
                    String sname = resultSet.getString("surname-o");
                    String email = resultSet.getString("email-o");
                    String tel = resultSet.getString("telephone-o");
                    int vat = resultSet.getInt("vat-o");
                    Double sum = resultSet.getDouble("sum-o");
                    String comm = resultSet.getString("comments-o");
                    String creat = resultSet.getString("create-o");
                    String update = resultSet.getString("update-o");
                    String send = resultSet.getString("send-o");
                    
                    ord = new Order(id, idPy, idUM, idDel, del_nr, idStat, idDisc, 
                            name, sname, email,tel, vat, sum, comm, creat, 
                            update, send);
                    listOd.add(ord);
                }
            }
        disconnect();
        return listOd;
    } 
    
    //wypisanie wszystkich zamówień dla danego pracownika
    public List<Order> readWorker(int idW) throws SQLException {
        Order ord;
        List<Order> listOd = new ArrayList<>();
        
       String sql = "SELECT `id_order`, `id_pay-o`, `id_user_m-o`, `id_del-o`, `deliv_nr-o`, "
                + "`id_stat-o`, `id_disc-o`, `name-o`, `surname-o`, "
                + "`email-o`, `telephone-o`, `vat-o`, `sum-o`, `comments-o`, `create-o`, "
                + "`update-o`, `send-o` FROM `order-o` WHERE `id_worker`="+idW+" OR `id_worker` IS NULL"; 
        connect();
        
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_order");
                    int idPy = resultSet.getInt("id_pay-o");
                    int idUM = resultSet.getInt("id_user_m-o");
                    int idDel = resultSet.getInt("id_del-o");
                    String del_nr = resultSet.getString("deliv_nr-o");
                    int idStat = resultSet.getInt("id_stat-o");
                    int idDisc = resultSet.getInt("id_disc-o");
                    String name = resultSet.getString("name-o");
                    String sname = resultSet.getString("surname-o");
                    String email = resultSet.getString("email-o");
                    String tel = resultSet.getString("telephone-o");
                    int vat = resultSet.getInt("vat-o");
                    Double sum = resultSet.getDouble("sum-o");
                    String comm = resultSet.getString("comments-o");
                    String creat = resultSet.getString("create-o");
                    String update = resultSet.getString("update-o");
                    String send = resultSet.getString("send-o");
                    
                    ord = new Order(id, idPy, idUM, idDel, del_nr, idStat, idDisc, 
                            name, sname, email,tel, vat, sum, comm, creat, 
                            update, send);
                    listOd.add(ord);
                }
            }
        disconnect();
        return listOd;
    } 
    
    //pobranie danych zamówień dla danego adresu
    public List<Order> readUsr(int id_usr) throws SQLException {
        Order ord;
        List<Order> listOd = new ArrayList<>();
        
       String sql = "SELECT `id_order`, `id_pay-o`, `id_del-o`, `deliv_nr-o`, "
                + "`id_stat-o`, `id_disc-o`, `name-o`, `surname-o`, "
                + "`email-o`, `telephone-o`, `vat-o`, `sum-o`, `comments-o`, `create-o`, "
                + "`update-o`, `send-o` FROM `order-o` WHERE `id_user_m-o` = ?"; 
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id_usr);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_order");
                    int idPy = resultSet.getInt("id_pay-o");
                    int idDel = resultSet.getInt("id_del-o");
                    String del_nr = resultSet.getString("deliv_nr-o");
                    int idStat = resultSet.getInt("id_stat-o");
                    int idDisc = resultSet.getInt("id_disc-o");
                    String name = resultSet.getString("name-o");
                    String sname = resultSet.getString("surname-o");
                    String email = resultSet.getString("email-o");
                    String tel = resultSet.getString("telephone-o");
                    int vat = resultSet.getInt("vat-o");
                    Double sum = resultSet.getDouble("sum-o");
                    String comm = resultSet.getString("comments-o");
                    String creat = resultSet.getString("create-o");
                    String update = resultSet.getString("update-o");
                    String send = resultSet.getString("send-o");
                    
                    ord = new Order(id, idPy, id_usr, idDel, del_nr, idStat, idDisc, 
                            name, sname, email,tel, vat, sum, comm, creat, 
                            update, send);
                    listOd.add(ord);
                }
            }
        }
        disconnect();
        return listOd;
    } 
    
    //pobranie jednego zamówienia
    public Order read(int id) throws SQLException {
        Order ord = null;
        String sql = "SELECT `id_pay-o`, `id_user_m-o`, `id_del-o`, `deliv_nr-o`, "
                + "`id_stat-o`, `id_disc-o`, `name-o`, `surname-o`, "
                + "`email-o`, `telephone-o`, `vat-o`, `sum-o`, `comments-o`, `create-o`, "
                + "`update-o`, `send-o` FROM `order-o` WHERE `id_order` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idPy = resultSet.getInt("id_pay-o");
                    int idUM = resultSet.getInt("id_user_m-o");
                    int idDel = resultSet.getInt("id_del-o");
                    String del_nr = resultSet.getString("deliv_nr-o");
                    int idStat = resultSet.getInt("id_stat-o");
                    int idDisc = resultSet.getInt("id_disc-o");
                    String name = resultSet.getString("name-o");
                    String sname = resultSet.getString("surname-o");
                    String email = resultSet.getString("email-o");
                    String tel = resultSet.getString("telephone-o");
                    int vat = resultSet.getInt("vat-o");
                    Double sum = resultSet.getDouble("sum-o");
                    String comm = resultSet.getString("comments-o");
                    String creat = resultSet.getString("create-o");
                    String update = resultSet.getString("update-o");
                    String send = resultSet.getString("send-o");
                    
                    ord = new Order(id, idPy, idUM, idDel, del_nr, idStat, idDisc, 
                            name, sname, email,tel, vat, sum, comm, creat, 
                            update, send);
                }
            }
        }
        disconnect();
        return ord;
    }
    
    //pobranie imienia i nazwiska osoby, która złożyła zamówienie
    public Order readName(int id) throws SQLException {
        Order ord = null;
        String sql = "SELECT `name-o`, `surname-o` "
                + " FROM `order-o` WHERE `id_order` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-o");
                    String sname = resultSet.getString("surname-o");
                    
                    ord = new Order(id, name, sname);
                }
            }
        }
        disconnect();
        return ord;
    }
    
    //pobranie identyfikatora zamówienia 
    public int readId(Order ord) throws SQLException {
        String sql = "SELECT `id_order` FROM `order-o` "
                + "WHERE `id_pay-o` = ? AND `id_user_m-o` = ? AND `id_del-o` = ? "
                + "AND `create-o` = ? "; 
        connect();
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, ord.getIdPay());
            statement.setInt(2, ord.getIdUserMeta());
            statement.setInt(3, ord.getIdDeliv());
            statement.setString(4, ord.getCreate());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id_order");
                }
            }
        }
        disconnect();
        return id;
    }
    
    //sprawdzenie statusu zamówienia
    public Order checkStat(int id) throws SQLException {
        Order ord = null;
        String sql = "SELECT `id_stat-o` WHERE `id_order` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idStat = resultSet.getInt("id_stat-o");
                    
                    ord = new Order(id, idStat);
                }
            }
        }
        disconnect();
        return ord;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie podstawowych danych
    public boolean update(Order ord) throws SQLException {
        String sql = "UPDATE `order-o` SET `deliv_nr-o`=?,`id_stat-o`=?,"
                + "`vat-o`=?,`sum-o`=?,`update-o`=?,`send-o`=? WHERE `id_order` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, ord.getDeliv_nr());
            statement.setInt(2, ord.getIdStat());
            statement.setInt(3, ord.getVat());
            statement.setDouble(4, ord.getSum());
            statement.setString(5, ord.getUpdate());
            statement.setString(6, ord.getSend());
            statement.setInt(7, ord.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnienie danych dla pracownika
    public boolean updateForWorker(Order ord) throws SQLException {
        String sql = "UPDATE `order-o` SET `deliv_nr-o`=?,`id_stat-o`=?,"
                + "`update-o`=?,`send-o`=?, `id_worker`=? WHERE `id_order` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, ord.getDeliv_nr());
            statement.setInt(2, ord.getIdStat());
            statement.setString(3, ord.getUpdate());
            statement.setString(4, ord.getSend());
            statement.setInt(5, ord.getIdWork());
            statement.setInt(6, ord.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE----- Usuwanie danych
    @Override
    //usunięcie zamówienia
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `order-o` WHERE `id_order` = ?";
         
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

