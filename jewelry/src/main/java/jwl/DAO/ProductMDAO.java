/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 26.08.2021 r.
 */

package jwl.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jwl.model.ProductMeta;

public class ProductMDAO extends Connect implements DAO<ProductMeta> {
  
//-----Konstruktor-----  
    public ProductMDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    //----CREATE-----   Tworzenie danych 
    @Override
    //stworzenie dodatkowych danych produktu
    public boolean create(ProductMeta prod_m) throws SQLException {
        String sql = "INSERT INTO `product-meta`(`id_prod-m`, `height`, `width`, `lenght`, "
                + "`hole`, `weight`, `diameter`, `id_fabr-m`, `id_shap-m`, `id_col-m`, "
                + "`description`, `quantity-state`, `quantity-m`, `vat-m`, "
                + "`discount-m`, `cerate-m`, `restock-m`, `source`)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, prod_m.getIdProd());
            statement.setFloat(2, prod_m.getHeight());
            statement.setFloat(3, prod_m.getWidth());
            statement.setFloat(4, prod_m.getLenght());
            statement.setFloat(5, prod_m.getHole());
            statement.setFloat(6, prod_m.getWeight());
            statement.setFloat(7, prod_m.getDiameter());
            statement.setInt(8, prod_m.getIdFabr());
            statement.setInt(9, prod_m.getIdShap());
            statement.setInt(10, prod_m.getIdCol());
            statement.setString(11, prod_m.getDescription());
            statement.setInt(12, prod_m.getQuantityState());
            statement.setInt(13, prod_m.getQuantity());
            statement.setInt(14, prod_m.getVat());
            statement.setInt(15, prod_m.getDiscount());
            statement.setString(16, prod_m.getCreate());
            statement.setString(17, prod_m.getRestock());
            statement.setString(18, prod_m.getSource());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
   //----READ-----  Czytanie danych
    //pobranie danych dla danego produktu
    public ProductMeta read(int id) throws SQLException {
        ProductMeta prod_m = null;
        String sql = "SELECT `height`, `width`, `lenght`, `hole`, `weight`, `diameter`, "
                + "`id_fabr-m`, `id_shap-m`, `id_col-m`, `description`, `quantity-state`, "
                + "`quantity-m`, `vat-m`, `discount-m`, `cerate-m`, `restock-m`, "
                + "`source` FROM `product-meta` WHERE `id_prod-m` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Float hei = resultSet.getFloat("height");
                    Float wid = resultSet.getFloat("width");
                    Float len = resultSet.getFloat("lenght");
                    Float hol = resultSet.getFloat("hole");
                    Float wei = resultSet.getFloat("weight");
                    Float dia = resultSet.getFloat("diameter");
                    int fab = resultSet.getInt("id_fabr-m");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    String desc = resultSet.getString("description");
                    int qs = resultSet.getInt("quantity-state");
                    int qm = resultSet.getInt("quantity-m");
                    int vat = resultSet.getInt("vat-m");
                    int disc = resultSet.getInt("discount-m");
                    String cre = resultSet.getString("cerate-m");
                    String rs = resultSet.getString("restock-m");
                    String src = resultSet.getString("source");
                    
                    prod_m = new ProductMeta(id, hei, wid, len, hol, wei, dia, 
                            fab, sha, col, desc, qs, qm, vat, disc, cre, rs, src);
                }
            }
        }
        return prod_m;
    }
    
    //pobranie wszystkich danych jednego produktu
    public ProductMeta readOne(int id) throws SQLException {
        ProductMeta prod_m = null;
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `id_catag-p`, "
                + "`price-p`, `height`, `width`, `lenght`, `hole`, "
                + "`weight`, `diameter`, `id_fabr-m`, `id_shap-m`, `id_col-m`, "
                + "`description`, `quantity-state`, `quantity-m`, `vat-m`, `discount-m`, "
                + "`cerate-m`, `restock-m`, `source` FROM `product-meta`,`product` "
                + "WHERE `id_prod`=`id_prod-m` AND `id_prod` = ?"; 
        connect();
         
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Float hei = resultSet.getFloat("height");
                    Float wid = resultSet.getFloat("width");
                    Float len = resultSet.getFloat("lenght");
                    Float hol = resultSet.getFloat("hole");
                    Float wei = resultSet.getFloat("weight");
                    Float dia = resultSet.getFloat("diameter");
                    int fab = resultSet.getInt("id_fabr-m");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    String desc = resultSet.getString("description");
                    int qs = resultSet.getInt("quantity-state");
                    int qm = resultSet.getInt("quantity-m");
                    int vat = resultSet.getInt("vat-m");
                    int disc = resultSet.getInt("discount-m");
                    String cre = resultSet.getString("cerate-m");
                    String rs = resultSet.getString("restock-m");
                    String src = resultSet.getString("source");
                    int idP = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    int CT = resultSet.getInt("id_catag-p");
                    Float price = resultSet.getFloat("price-p");
                    
                    prod_m = new ProductMeta(id, hei, wid, len, hol, wei, dia, fab, 
                            sha, col, desc, qs, qm, vat, disc, cre, rs, src, idP, 
                            name, img, CT, price);
                }
            }
        }
        return prod_m;
    }   
    
    //pobranie listy wszystkich danych produktów
    public List<ProductMeta> read() throws SQLException {
        ProductMeta rk;
        List<ProductMeta> listRk = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `id_catag-p`, "
                + "`price-p`, `id_prod-m`, `height`, `width`, `lenght`, `hole`, "
                + "`weight`, `diameter`, `id_fabr-m`, `id_shap-m`, `id_col-m`, "
                + "`description`, `quantity-state`, `quantity-m`, `vat-m`, `discount-m`, "
                + "`cerate-m`, `restock-m`, `source` FROM `product-meta`,`product` "
                + "WHERE `id_prod`=`id_prod-m` ORDER BY `id_prod-m` ASC";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    Float hei = resultSet.getFloat("height");
                    Float wid = resultSet.getFloat("width");
                    Float len = resultSet.getFloat("lenght");
                    Float hol = resultSet.getFloat("hole");
                    Float wei = resultSet.getFloat("weight");
                    Float dia = resultSet.getFloat("diameter");
                    int fab = resultSet.getInt("id_fabr-m");
                    int id = resultSet.getInt("id_prod-m");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    String desc = resultSet.getString("description");
                    int qs = resultSet.getInt("quantity-state");
                    int qm = resultSet.getInt("quantity-m");
                    int vat = resultSet.getInt("vat-m");
                    int disc = resultSet.getInt("discount-m");
                    String cre = resultSet.getString("cerate-m");
                    String rs = resultSet.getString("restock-m");
                    String src = resultSet.getString("source");
                    int idP = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    int CT = resultSet.getInt("id_catag-p");
                    Float price = resultSet.getFloat("price-p");
                    
                    rk = new ProductMeta(id, hei, wid, len, hol, wei, dia, fab, 
                            sha, col, desc, qs, qm, vat, disc, cre, rs, src, idP, 
                            name, img, CT, price);
                    listRk.add(rk);
                }
            }
        return listRk;
    }
    
    //pobranie nowych produktów
    public List<ProductMeta> readNews(String date) throws SQLException {
        ProductMeta pd;
        List<ProductMeta> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `price-p`, `discount-m`, `quantity-state`, `id_catag-p`"
                + ", `id_shap-m`, `id_col-m` FROM `product`, `product-meta` "
                + " WHERE `id_prod`=`id_prod-m` AND `product-meta`.`cerate-m` > ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, date);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    Float price = resultSet.getFloat("price-p");
                    int disc = resultSet.getInt("discount-m");
                    int state = resultSet.getInt("quantity-state");
                    int CT = resultSet.getInt("id_catag-p");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    
                    pd = new ProductMeta(id, name, img, CT, price, disc, state, col, sha);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
    //pobranie danych dla strony głównej (losowanie 9 produktów)
    public List<ProductMeta> readIndexRand() throws SQLException {
        ProductMeta pd;
        List<ProductMeta> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `price-p`, `discount-m`, `quantity-state`, `id_catag-p`"
                + ", `id_shap-m`, `id_col-m` FROM `product`, `product-meta` "
                + " WHERE `id_prod`=`id_prod-m` AND `quantity-state`!=0 ORDER BY RAND ( ) LIMIT 9;";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    Float price = resultSet.getFloat("price-p");
                    int disc = resultSet.getInt("discount-m");
                    int state = resultSet.getInt("quantity-state");
                    int CT = resultSet.getInt("id_catag-p");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    
                    pd = new ProductMeta(id, name, img, CT, price, disc, state, col, sha);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
    //pobranie przecenionych produktów 
    public List<ProductMeta> readDiscount() throws SQLException {
        ProductMeta pd;
        List<ProductMeta> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `price-p`, `discount-m`, `quantity-state`, "
                + "`id_catag-p`, `id_shap-m`, `id_col-m`"
                + " FROM `product`, `product-meta` WHERE `id_prod`=`id_prod-m` AND `discount-m` != 0";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    Float price = resultSet.getFloat("price-p");
                    int disc = resultSet.getInt("discount-m");
                    int state = resultSet.getInt("quantity-state");
                    int CT = resultSet.getInt("id_catag-p");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    
                    pd = new ProductMeta(id, name, img, CT, price, disc, state, col, sha);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
    //pobranie ponowionych (ponownie dostępnych) produktów
    public List<ProductMeta> readRestock(String date) throws SQLException {
        ProductMeta pd;
        List<ProductMeta> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `price-p`, `discount-m`,"
                + " `quantity-state`, `id_catag-p`, `id_shap-m`, `id_col-m`"
                + " FROM `product`, `product-meta` WHERE `id_prod`=`id_prod-m` AND `product-meta`.`restock-m` > ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, date);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    Float price = resultSet.getFloat("price-p");
                    int disc = resultSet.getInt("discount-m");
                    int state = resultSet.getInt("quantity-state");
                    int CT = resultSet.getInt("id_catag-p");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    
                    pd = new ProductMeta(id, name, img, CT, price, disc, state, col, sha);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
    //wyszy=ukanie produktów
    public List<ProductMeta> search(String search) throws SQLException {
        ProductMeta pd;
        List<ProductMeta> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `price-p`, `discount-m`,`quantity-state`, "
                + "`id_catag-p`, `id_shap-m`, `id_col-m` FROM `product`, `product-meta` "
                + "WHERE `id_prod`=`id_prod-m` AND `name-p` LIKE '%"+search+"%'";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    Float price = resultSet.getFloat("price-p");
                    int disc = resultSet.getInt("discount-m");
                    int state = resultSet.getInt("quantity-state");
                    int CT = resultSet.getInt("id_catag-p");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    
                    pd = new ProductMeta(id, name, img, CT, price, disc, state, col, sha);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
    //pobranie listy dla danej kategorii i tagu
    public List<ProductMeta> readCT(int CT) throws SQLException {
        ProductMeta pd;
        List<ProductMeta> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`,  `name-p`, `images-p`, `price-p`, `discount-m`, `quantity-state`, `id_shap-m`, `id_col-m`"
                   + "FROM `product`, `product-meta` WHERE `id_prod`=`id_prod-m` AND `id_catag-p` = ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, CT);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    int disc = resultSet.getInt("discount-m");
                    Float price = resultSet.getFloat("price-p");
                    int state = resultSet.getInt("quantity-state");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    
                    pd = new ProductMeta(id, name, img, CT, price, disc, state, col, sha);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }

    //pobranie produktów dla konkretnej kategorii
    public List<ProductMeta> readCat(int CT) throws SQLException {
        ProductMeta pd;
        List<ProductMeta> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `images-p`, `price-p`, `discount-m`, "
                   + "`quantity-state`, `id_shap-m`, `id_col-m` "
                   + "FROM `product`, `category-tag`, `product-meta`"
                   + "WHERE `id_cat-ct` = ? AND `id_catag`=`id_catag-p` AND `id_prod`=`id_prod-m`";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, CT);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    Float price = resultSet.getFloat("price-p");
                    int disc = resultSet.getInt("discount-m");
                    int state = resultSet.getInt("quantity-state");
                    int sha = resultSet.getInt("id_shap-m");
                    int col = resultSet.getInt("id_col-m");
                    
                    pd = new ProductMeta(id, name, img, CT, price, disc, state, col, sha);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
    //pobranie danych dla korektora
    public List<ProductMeta> readForCensor() throws SQLException {
        ProductMeta pd;
        List<ProductMeta> listPd = new ArrayList<>();
        
        String sql = "SELECT `id_prod`, `name-p`, `id_cat-ct`, `images-p`, `description` "
                   + "FROM `product`, `category-tag`, `product-meta`"
                   + "WHERE  `id_catag`=`id_catag-p` AND `id_prod`=`id_prod-m`";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    int CT = resultSet.getInt("id_cat-ct");
                    String disc = resultSet.getString("description");
                    
                    pd = new ProductMeta(id, name, img, CT, disc);
                    listPd.add(pd);
                }
            }
        }
        return listPd;
    }
    
    //pobranie danych produktu dla koszyka aplikacji
    public ProductMeta readProdBask(int bask) throws SQLException {
        ProductMeta pd = null;
        
        String sql = "SELECT DISTINCT `id_prod`, `discount-m`, `name-p`, `images-p`, `id_catag-p`, `price-p`, `quantity-state` "
                   + "FROM `product`, `basket`, `product-meta` "
                   + "WHERE `id_prod`=`id_prod-b` AND `id_prod`=`id_prod-m` AND `id_prod-b` = ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, bask);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                        int id = resultSet.getInt("id_prod");
                        int discount = resultSet.getInt("discount-m");
                        String name = resultSet.getString("name-p");
                        int img = resultSet.getInt("images-p");
                        int CT = resultSet.getInt("id_catag-p");
                        int state = resultSet.getInt("quantity-state");
                        Float price = resultSet.getFloat("price-p");

                        pd = new ProductMeta(id, name, img, CT, price, discount, state);
                    }
                }
            }
        return pd;
    }
    
    //pobranie danych produktu dla danego zamówienia
    public ProductMeta readProdOrd(int ord) throws SQLException {
        ProductMeta pd = null;
        
        String sql = "SELECT DISTINCT `id_prod`, `discount-m`, `name-p`, `images-p`, `id_catag-p`, `price-p` "
                   + "FROM `product`, `order-product`, `product-meta` "
                   + "WHERE `id_prod`=`id_prod-op` AND `id_prod`=`id_prod-m` AND `id_prod-op` = ?";
        connect();
         
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, ord);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                        int id = resultSet.getInt("id_prod");
                        int discount = resultSet.getInt("discount-m");
                        String name = resultSet.getString("name-p");
                        int img = resultSet.getInt("images-p");
                        int CT = resultSet.getInt("id_catag-p");
                        Float price = resultSet.getFloat("price-p");

                        pd = new ProductMeta(id, name, img, CT, price, discount);
                    }
                }
            }
        return pd;
    }
    
    //pobranie wartości przeceny
    public int readDiscount(int id) throws SQLException {
        String sql = "SELECT `discount-m` FROM `product-meta`"
                   + "WHERE  `id_prod-m` = ?";
        connect();
        
        int discount = 0;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                        discount = resultSet.getInt("discount-m");
                    }
                }
            }
        return discount;
    }
    
    //pobranie ilości na stanie
    public int readState(int id) throws SQLException {
        String sql = "SELECT `quantity-state` FROM `product-meta`"
                   + "WHERE  `id_prod-m` = ?";
        connect();
        
        int quant = 0;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                        quant = resultSet.getInt("quantity-state");
                    }
                }
            }
        return quant;
    }
    
   //----UPDATE-----  Uaktualnianie danych
    @Override
    //aktualizacja danych dodatkowych produktu
    public boolean update(ProductMeta prod_m) throws SQLException {
        String sql = "UPDATE `product-meta` SET `height`=?,`width`=?,`lenght`=?,"
                + "`hole`=?,`weight`=?,`diameter`=?,`id_fabr-m`=?,`id_shap-m`=?,"
                + "`id_col-m`=?,`description`=?,`quantity-state`=?,`quantity-m`=?,"
                + "`discount-m`=?, `source`=? "
                +" WHERE `id_prod-m` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setFloat(1, prod_m.getHeight());
            statement.setFloat(2, prod_m.getWidth());
            statement.setFloat(3, prod_m.getLenght());
            statement.setFloat(4, prod_m.getHole());
            statement.setFloat(5, prod_m.getWeight());
            statement.setFloat(6, prod_m.getDiameter());
            statement.setInt(7, prod_m.getIdFabr());
            statement.setInt(8, prod_m.getIdShap());
            statement.setInt(9, prod_m.getIdCol());
            statement.setString(10, prod_m.getDescription());
            statement.setInt(11, prod_m.getQuantityState());
            statement.setInt(12, prod_m.getQuantity());
            statement.setInt(13, prod_m.getDiscount());
            statement.setString(14, prod_m.getSource());
            statement.setInt(15, prod_m.getIdProd());
            /*
            int idP = resultSet.getInt("id_prod");
                    String name = resultSet.getString("name-p");
                    int img = resultSet.getInt("images-p");
                    int CT = resultSet.getInt("id_catag-p");
                    Float price = resultSet.getFloat("price-p");
                    
                    rk = new ProductMeta(id, hei, wid, len, hol, wei, dia, fab, 
                            sha, col, desc, qs, qm, vat, disc, cre, rs, src, idP, 
                            name, img, CT, price);*/
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
   
    //pełne uaktualnienie produktu
    public boolean updateFull(ProductMeta prod_m) throws SQLException {
        String sql = "UPDATE `product-meta`,`product` SET `name-p`=?, `images-p`=?, `id_catag-p`=?,"
                + "`price-p`=?, `height`=?, `width`=?, `lenght`=?, `hole`=?,`weight`=?, `diameter`=?,"
                + " `id_fabr-m`=?, `id_shap-m`=?, `id_col-m`=?,`description`=?, `quantity-state`=?, "
                + "`quantity-m`=?, `discount-m`=?, `cerate-m`=?, `source`=?, "
                + "`restock-m`=? WHERE `id_prod`=`id_prod-m` AND `id_prod`=?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, prod_m.getName());
            statement.setInt(2, prod_m.getImages());
            statement.setInt(3, prod_m.getIdCattag());
            statement.setFloat(4, prod_m.getPrice());
            statement.setFloat(5, prod_m.getHeight());
            statement.setFloat(6, prod_m.getWidth());
            statement.setFloat(7, prod_m.getLenght());
            statement.setFloat(8, prod_m.getHole());
            statement.setFloat(9, prod_m.getWeight());
            statement.setFloat(10, prod_m.getDiameter());
            statement.setInt(11, prod_m.getIdFabr());
            statement.setInt(12, prod_m.getIdShap());
            statement.setInt(13, prod_m.getIdCol());
            statement.setString(14, prod_m.getDescription());
            statement.setInt(15, prod_m.getQuantityState());
            statement.setInt(16, prod_m.getQuantity());
            statement.setInt(17, prod_m.getDiscount());
            statement.setString(18, prod_m.getSource());
            statement.setString(19, prod_m.getRestock());
            statement.setInt(20, prod_m.getIdProd());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnienie opisu produktu
    public boolean updateDescription(int id, String description) throws SQLException {
        String sql = "UPDATE `product-meta` SET `description`=?"
                    +" WHERE `id_prod-m` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, description);
            statement.setInt(2, id);
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktyalnienie ilości na stanie
    public boolean updateQuant(int id, int quant) throws SQLException {
        String sql = "UPDATE `product-meta` SET `quantity-state`=`quantity-state` - ?"
                +" WHERE `id_prod-m` = ?";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, quant);
            statement.setInt(2, id);
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //odnowienie produktu
    public boolean updateState(ProductMeta prdm) throws SQLException {
        String sql = "UPDATE `product-meta` SET `quantity-state` = ?, `restock-m` = ?"
                    +" WHERE `id_prod-m` = ?";
         
        connect();
         
        boolean deleted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, prdm.getQuantityState());
            statement.setString(2, prdm.getRestock());
            statement.setInt(3, prdm.getIdProd());
            deleted = statement.executeUpdate() > 0;
        }
        disconnect();
        return deleted;
    }
    
  //----DELETE-----  Usuwanie danych
    @Override
    //usunięcie produktu
    public boolean delete(int id) throws SQLException {
        String sql = "UPDATE `product-meta` SET `quantity-state` = 0"
                    +" WHERE `id_prod-m` = ?";
         
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
