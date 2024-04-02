/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 26.08.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jwl.model.Product;
import jwl.model.link.Basket;

public class ProductDAO extends Connect implements DAO<Product> {
  
//-----Konstruktor-----  
    public ProductDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie produktu
    public boolean create(Product prd) throws SQLException {
        String sql = "INSERT INTO `product`(`name-p`, `images-p`, `id_catag-p`, `price-p`) "
                   + "VALUES (?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, prd.getName());
            statement.setInt(2, prd.getImages());
            statement.setInt(3, prd.getIdCattag());
            statement.setFloat(4, prd.getPrice());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie produktu po jego identyfikatorze
    public Product read(int id) throws SQLException {
        Product prd = null;
        String sql = "SELECT `name-p`, `images-p`, `id_catag-p`, `price-p` FROM `product` "
                + "WHERE `id_prod` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    int CT = resultSet.getInt("id_catag-p");
                    Float price = resultSet.getFloat("price-p");
                    
                    prd = new Product(id, name, img, CT, price);
                }
            }
        }
        return prd;
    }
    
    //pobranie listy wszystkich prouktów
    public List<Product> read() throws SQLException {
        Product pd;
        List<Product> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `id_catag-p`, `price-p` "
                   + "FROM `product` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    int CT = resultSet.getInt("id_catag-p");
                    Float price = resultSet.getFloat("price-p");
                    
                    pd = new Product(id, name, img, CT, price);
                    listPd.add(pd);
                }
            }
        return listPd;
    }
    
    //pobranie ilości obrazów
    public Product readImg(int id) throws SQLException {
        Product prd = null;
        String sql = "SELECT `images-p` FROM `product` "
                + "WHERE `id_prod` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int img = resultSet.getInt("images-p");
                    
                    prd = new Product(id, img);
                }
            }
        }
        return prd;
    }
    
    //pobranie identyfikatora produktu
    public int readId(Product prd) throws SQLException {
        String sql = "SELECT `id_prod` FROM `product` "
                + "WHERE `images-p` = ? AND `name-p` = ? "
                + "AND `id_catag-p` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, prd.getImages());
            statement.setString(2, prd.getName());
            statement.setInt(3, prd.getIdCattag());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id_prod");
                }
            }
        }
        return id;
    }
    
    //pobranie kategorii i tagu produktu
    public List<Product> readCT(int CT) throws SQLException {
        Product pd;
        List<Product> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `price-p`"
                   + "FROM `product` WHERE  `id_catag-p` = ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, CT);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    Float price = resultSet.getFloat("price-p");
                    
                    pd = new Product(id, name, img, CT, price);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
    //pobranie produktów dla danej kategorii
    public List<Product> readCat(int CT) throws SQLException {
        Product pd;
        List<Product> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `price-p` "
                   + "FROM `product`, `category-tag` "
                   + "WHERE `id_cat-ct` = ? AND `id_catag` = `id_catag-p`";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, CT);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    Float price = resultSet.getFloat("price-p");
                    
                    pd = new Product(id, name, img, CT, price);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //uaktualnienie danych produktu
    public boolean update(Product prd) throws SQLException {
        String sql = "UPDATE `product` SET `name-p` = ?,`id_catag-p` = ?,"
                   + "`price-p` = ? WHERE `id_prod` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, prd.getName());
            statement.setInt(2, prd.getIdCattag());
            statement.setFloat(3, prd.getPrice());
            statement.setInt(4, prd.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnienie ilości obrazów
    public boolean updateImg(int id, int img) throws SQLException {
        String sql = "UPDATE `product` SET `images-p` = ? "
                   + "WHERE `id_prod` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, img);
            statement.setInt(2, id);
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie produktu
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `product` WHERE `id_prod` = ?";
         
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
