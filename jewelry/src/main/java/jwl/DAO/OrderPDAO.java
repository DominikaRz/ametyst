/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 26.08.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import java.util.*;

import jwl.model.link.OrderProd;

public class OrderPDAO extends Connect implements DAO<OrderProd> {
  
//-----Konstruktor-----  
    public OrderPDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie produktów powiązanych z zamówieniem
    public boolean create(OrderProd ord_p) throws SQLException {
        String sql = "INSERT INTO `order-product`(`id_order-op`, `id_prod-op`, `quantity-op`, `discount-op`)"
                     + "VALUES (?, ?, ?, ?)";
        connect();
        
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, ord_p.getIdOrder());
            statement.setInt(2, ord_p.getIdProd());
            statement.setInt(3, ord_p.getQuantity());
            statement.setInt(4, ord_p.getDiscount());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie listy wszystkich produktów z zamówienia
    public List<OrderProd> read(int id) throws SQLException {
        OrderProd ord_p;
        List<OrderProd> listOrdP = new ArrayList<>();
        
        String sql = "SELECT `id_prod-op`, `quantity-op`, `discount-op` FROM `order-product` "
                   + "WHERE `id_order-op` = ?";
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    int idP = resultSet.getInt("id_prod-op");
                    int qt = resultSet.getInt("quantity-op");
                    int dc = resultSet.getInt("discount-op");
                    
                    ord_p = new OrderProd(id, idP, qt, dc);
                    listOrdP.add(ord_p);
                }
            }
        }
        disconnect();
        return listOrdP;
    }
    
    //pobranie ilości niezrecenzowanych produktów z zamówienia
    public List<OrderProd> readUnrew(int idOrd) throws SQLException {
        OrderProd ord_p;
        List<OrderProd> listOrdP = new ArrayList<>();
        
        String sql = "SELECT `id_prod-op`, `id_order-op`, `quantity-op`, `discount-op` "
                + "FROM `order-product` WHERE `id_prod-op` NOT IN( "
                + "SELECT `id_prod-rw` FROM `review` "
                + "WHERE `id_order-op`=`id_order-rw`) AND `id_order-op` = ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, idOrd);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    int idP = resultSet.getInt("id_prod-op");
                    int qt = resultSet.getInt("quantity-op");
                    int dc = resultSet.getInt("discount-op");
                    
                    ord_p = new OrderProd(idOrd, idP, qt, dc);
                    listOrdP.add(ord_p);
                }
            }
        }
        disconnect();
        return listOrdP;
    }
    
    //sprawdzenie czy zamówienie jest w pełni zrecenzowane
    public Boolean isFullRev(int idOrd) throws SQLException {
        
        String sql = "SELECT CASE WHEN EXISTS( "
                + "SELECT `id_prod-op`, `id_order-op` FROM `order-product` "
                + "WHERE `id_prod-op` NOT IN"
                + "( SELECT `id_prod-rw` FROM `review` WHERE `id_order-op`=`id_order-rw`) "
                + "AND `id_order-op` = ?) THEN TRUE ELSE FALSE END AS bool ";
        connect();
        
        Boolean query = true;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, idOrd);
            
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    query = resultSet.getBoolean("bool");
                }
            }
        }
        disconnect();
        return query;
    }
    
    //sprawdzenie ilości na stanie (produkt) możliwej do zakupu
    public int quantityProd(int idProd) throws SQLException {
        
        String sql = "SELECT SUM(`quantity-op`) AS 'int' FROM `order-product`, `order-o` "
                + "WHERE `id_prod-op`="+idProd+" AND `id_stat-o`=1 AND `id_order-op`=`id_order`";
        connect();
        
        int query = 0;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    query = resultSet.getInt("int");
                }
            }
        }
        disconnect();
        return query;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    public boolean update(OrderProd ord_p) throws SQLException {
        return false; 
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie rekordów dla danego zamówienia
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `order-product` "
                   + "WHERE `id_order-op` = ?";
         
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

