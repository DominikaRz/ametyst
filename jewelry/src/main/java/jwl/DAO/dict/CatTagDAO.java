/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 28.05.2021 r.
 */
package jwl.DAO.dict;

import java.sql.*;
import java.util.*;
import jwl.DAO.Connect;
import jwl.DAO.DAO;

import jwl.model.link.CatTag;

public class CatTagDAO extends Connect implements DAO<CatTag>{
    
  //-----Konstruktor-----     
    public CatTagDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
  //----CREATE-----    Tworzenie danych
    @Override
    //utworzenie kategorii, tagu i ich połączenia w tabeli łacznikowej
    public boolean create(CatTag CT) throws SQLException {
        String sql1 = "INSERT INTO `category`(`name-c`) VALUES (?);";
        String sql2 = "INSERT INTO `tag`(`name-t`) VALUES (?);";
        String sql3 = "INSERT INTO `category-tag`(`id_cat-ct`, `id_tag-ct`) "
                       + "VALUES (?,?);";
        connect();
        
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql1)) {
            statement.setString(1, CT.getNameCat());
            inserted = statement.executeUpdate() > 0;
        }
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql2)) {
            statement.setString(1, CT.getNameTag());
            inserted = statement.executeUpdate() > 0;
        }
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql3)) {
            statement.setInt(1, CT.getIdCat());
            statement.setInt(2, CT.getIdTag());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }
    
    //utworzenie połączenia istniejących danych (tagu i kategorii)
    public boolean createCatTag(CatTag CT) throws SQLException {
        String sql = "INSERT INTO `category-tag`(`id_cat-ct`, `id_tag-ct`) "
                       + "VALUES (?,?);";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, CT.getIdCat());
            statement.setInt(2, CT.getIdTag());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }    
    
    //utworzenie kategorii
    public boolean createCat(CatTag CT) throws SQLException {
        String sql = "INSERT INTO `category`(`name-c`, `id_group-c`) "
                       + "VALUES (?, ?);";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, CT.getName());
            statement.setInt(2, CT.getIdGr());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }    
    
    //utworzenie tagu
    public boolean createTag(CatTag CT) throws SQLException {
        String sql = "INSERT INTO `tag`(`name-t`) VALUES (?);";
        connect();
         
        boolean inserted;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, CT.getName());
            inserted = statement.executeUpdate() > 0;
        }
        disconnect();
        return inserted;
    }    
    
    
//----READ-----  Czytanie danych
    //pobranie listy kategorii i tagów
    public List<CatTag> read() throws SQLException {
        CatTag CT;
        List<CatTag> listCT = new ArrayList<>();
        String sql = "SELECT `id_catag`, `id_group-c` ,`id_cat-ct`, `name-c`, `id_tag-ct`, `name-t`, `name-g` "
                    + "FROM `category`,`tag`,`category-tag`, `group-g` "
                    + "WHERE `id_cat`=`id_cat-ct` AND `id_tag`=`id_tag-ct` AND `id_group`=`id_group-c` "
                    + "ORDER BY `category`.`id_group-c` ASC";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int id = resultSet.getInt("id_catag");
                    int idCat = resultSet.getInt("id_cat-ct");
                    int idGr = resultSet.getInt("id_group-c");
                    String nameCat = resultSet.getString("name-c");
                    int idTag = resultSet.getInt("id_tag-ct");
                    String nameTag = resultSet.getString("name-t");
                    String nameGr = resultSet.getString("name-g");
                    
                    CT = new CatTag(id, idCat, idGr, nameCat, idTag, nameTag, nameGr);
                    listCT.add(CT);
                }
            }
        disconnect();
        return listCT;
    }
    
    //pobranielisty kategorii
    public List<CatTag> getCategory() throws SQLException {
        CatTag CT;
        List<CatTag> listCT = new ArrayList<>();
        String sql = "SELECT `id_cat`, `name-c`, `description-c` FROM `category` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int idCat = resultSet.getInt("id_cat");
                    String nameCat = resultSet.getString("name-c");
                    String descCat = resultSet.getString("description-c");
                    
                    CT = new CatTag(idCat, nameCat, descCat);
                    listCT.add(CT);
                }
            }
        disconnect();
        return listCT;
    }
    
    //pobranie listy tagów
    public List<CatTag> getTag() throws SQLException {
        CatTag CT;
        List<CatTag> listCT = new ArrayList<>();
        String sql = "SELECT `id_tag`, `name-t`, `description-t` FROM `tag` WHERE 1";
        connect();
         
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int idTag = resultSet.getInt("id_tag");
                    String nameTag = resultSet.getString("name-t");
                    String descTag = resultSet.getString("description-t");
                    
                    CT = new CatTag(idTag, nameTag, descTag);
                    listCT.add(CT);
                }
            }
        disconnect();
        return listCT;
    }
    
    //pobranie identyfikatora tagu i kategorii po id tabeli łącznikowej
    public CatTag getIds(int id) throws SQLException {
        CatTag CT = null;
        String sql = "SELECT `id_cat-ct`, `id_tag-ct` FROM `category`,`tag`,`category-tag` "
                   + "WHERE `id_cat`=`id_cat-ct` AND `id_tag`=`id_tag-ct` AND id_catag = ?";
          
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idCat = resultSet.getInt("id_cat-ct");
                    int idTag = resultSet.getInt("id_tag-ct");
                    
                    CT = new CatTag(id, idCat, idTag);
                }
            }
        }
        disconnect();
        return CT;
    }
    
    //pobranie nazw tagu i kategorii po id tabeli łącznikowej
    public CatTag getNames(int id) throws SQLException {
        CatTag CT = null;
        String sql = "SELECT `name-c`, `name-t` FROM `category`,`tag`,`category-tag` "
                   + "WHERE `id_cat`=`id_cat-ct` AND `id_tag`=`id_tag-ct` AND id_catag = ?";
          
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nameCat = resultSet.getString("name-c");
                    String nameTag = resultSet.getString("name-t");
                    
                    CT = new CatTag(nameCat, nameTag);
                }
            }
        }
        disconnect();
        return CT;
    }
    
    //pobranie identyfikatora i nazwy kategorii 
    public CatTag readCat(int id) throws SQLException {
        CatTag CT = null;
        String sql = "SELECT `id_cat`, `name-c` FROM `category` "
                   + "WHERE `id_cat` = ?";
          
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idCat = resultSet.getInt("id_cat");
                    String nameCat = resultSet.getString("name-c");
                    
                    CT = new CatTag(idCat, nameCat);
                }
            }
        }
        disconnect();
        return CT;
    }//get description of categoty
    
    //pobranie identyfikatora i nazwy tagu
    public CatTag readTag(int id) throws SQLException {
        CatTag CT = null;
        String sql = "SELECT `id_tag`, `name-t` FROM `tag` "
                   + "WHERE `id_tag` = ? ";
          
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idTag = resultSet.getInt("id_tag");
                    String nameTag = resultSet.getString("name-t");
                    
                    CT = new CatTag(idTag, nameTag);
                }
            }
        }
        disconnect();
        return CT;
    }
    
    //pobranie wszystkich danych kategorii
    public CatTag getCat(int id) throws SQLException {
        CatTag CT = null;
        String sql = "SELECT `id_cat`, `name-c`, `description-c` FROM `category`,`tag`,`category-tag` "
                   + "WHERE `id_catag` = ? AND `id_cat`=`id_cat-ct` AND `id_tag`=`id_tag-ct` ";
          
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idCat = resultSet.getInt("id_cat");
                    String nameCat = resultSet.getString("name-c");
                    String descCat = resultSet.getString("description-c");
                    
                    CT = new CatTag(idCat, nameCat, descCat);
                }
            }
        }
        disconnect();
        return CT;
    }
    
    //pobranie wszystkich danych tagu
    public CatTag getTag(int id) throws SQLException {
        CatTag CT = null;
        String sql = "SELECT `id_tag`, `name-t`, `description-t` FROM `category`,`tag`,`category-tag` "
                   + "WHERE `id_catag` = ? AND `id_cat`=`id_cat-ct` AND `id_tag`=`id_tag-ct` ";
          
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idTag = resultSet.getInt("id_tag");
                    String nameTag = resultSet.getString("name-t");
                    String descTag = resultSet.getString("description-t");
                    
                    CT = new CatTag(idTag, nameTag, descTag);
                }
            }
        }
        disconnect();
        return CT;
    }
    
    //pobranie opisu kategorii
    public CatTag getDescCat(int id) throws SQLException {
        CatTag CT = null;
        String sql = "SELECT  `name-c`, `description-c` FROM `category` "
                   + "WHERE `id_cat` = ?";
          
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nameCat = resultSet.getString("name-c");
                    String descCat = resultSet.getString("description-c");
                    
                    CT = new CatTag(id, nameCat, descCat);
                }
            }
        }
        disconnect();
        return CT;
    }
    
    //pobranie opisu tagu
    public CatTag getDescTag(int id) throws SQLException {
        CatTag CT = null;
        String sql = "SELECT  `name-t`, `description-t` FROM `tag` "
                   + "WHERE `id_tag` = ?";
          
        connect();
        
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nameTag = resultSet.getString("name-t");
                    String descTag = resultSet.getString("description-t");
                    
                    CT = new CatTag(id, nameTag, descTag);
                }
            }
        }
        disconnect();
        return CT;
    }
    
    //pobranie ostatniego identyfikatora kategorii
    public int getLastIdCat() throws SQLException {
        int id = 0;
        String sql = "SELECT MAX(id_cat) FROM category WHERE 1;";
        connect();
        
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    id = resultSet.getInt("MAX(id_cat)");
                }
            }
        disconnect();
        return id;
    }
    
    //pobranie ostatniego identyfikatora tagu
    public int getLastIdTag() throws SQLException {
        int id = 0;
        String sql = "SELECT MAX(id_tag) FROM tag WHERE 1;";
        connect();
        
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    id = resultSet.getInt("MAX(id_tag)");
                }
            }
        disconnect();
        return id;
    }
    
    //pobranie identyfikatora kategorii po nazwie
    public int getIdCat(String name) throws SQLException {
        String sql = "SELECT `id_cat` FROM `category` WHERE `name-c` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id  = Integer.parseInt(resultSet.getString("id_cat"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //pobranie identyfikatora tagu po nazwie
    public int getIdTag(String name) throws SQLException {
        String sql = "SELECT `id_tag` FROM `tag` WHERE `name-t` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, name);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id  = Integer.parseInt(resultSet.getString("id_tag"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //pobranie identyfikatora tabeli łącznikowej po identyfikatorach kategorii i tagu
    public int getIdCatTag(int cat, int tag) throws SQLException {
        String sql = "SELECT `id_catag` FROM `category-tag` WHERE `id_tag-ct` = ? AND `id_cat-ct` = ?"; 
        connect();
        
       int id = 0; 
       try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, tag);
            statement.setInt(2, cat);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id  = Integer.parseInt(resultSet.getString("id_catag"));
                }
            }
        }
        disconnect();
        return id;
    }
    
    //pobranie kategorii na potrzeby admina (wyświetlanie menu)
    public List<CatTag> forAdminCat() throws SQLException {
        CatTag CT;
        List<CatTag> listCT = new ArrayList<>();
        String sql = "SELECT `id_cat`, `name-c`, `id_group-c`FROM `category` "
                + "WHERE `id_cat` NOT IN( SELECT `id_cat-ct` "
                + "FROM `category`,`category-tag`,`tag` "
                + "WHERE `id_cat`=`id_cat-ct`AND `id_tag`=`id_tag-ct`) "
                + "ORDER BY `id_group-c` ASC";
        connect();
        
        try (Statement statement = jdbcConnection.createStatement(); 
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                    int idCat = resultSet.getInt("id_cat");
                    String nameCat = resultSet.getString("name-c");
                    int idGr = resultSet.getInt("id_group-c");
                    
                    CT = new CatTag(idCat, nameCat, idGr);
                    listCT.add(CT);
                }
            }
        disconnect();
        return listCT;
    }
    
  //----UPDATE-----   Uaktualnianie danych
    @Override
    //uaktyalnienie tabeli łącznikowej
    public boolean update(CatTag CT) throws SQLException {
        String sql = "UPDATE `category-tag` SET `id_tag-ct`=?, `id_cat-ct` = ?"
                  + " WHERE `id_catag` = ?;";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, CT.getIdTag());
            statement.setInt(2, CT.getIdCat());
            statement.setInt(3, CT.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
   
    //uaktualnienie nazwy kategorii
    public boolean updateCat(CatTag CT) throws SQLException {
        String sql = "UPDATE `category` SET `name-c`=? "
                  + " WHERE `id_cat` = ?;";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, CT.getName());
            statement.setInt(2, CT.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnienie opisu kategorii
    public boolean updateCatDesc(int id, String description) throws SQLException {
        String sql = "UPDATE `category` SET `description-c` = ? "
                  + " WHERE `id_cat` = ?;";
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
   
    //uaktualnienie nazwy tagu
    public boolean updateTag(CatTag CT) throws SQLException {
        String sql = "UPDATE `tag` SET `name-t`=? "
                  + " WHERE `id_tag` = ?;";
        connect();
        
        boolean updated;
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, CT.getName());
            statement.setInt(2, CT.getId());
            updated = statement.executeUpdate() > 0;
        }
        disconnect();
        return updated;
    }
    
    //uaktualnienie opisu tagu
    public boolean updateTagDesc(int id, String description) throws SQLException {
        String sql = "UPDATE `tag` SET `description-t`=? "
                  + " WHERE `id_tag` = ?;";
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

  //----DELETE-----    Usuwanie danych
    @Override
    //usunięcie połączenia kategorii i tagu z tabeli łącznikowej
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM `category-tag` "
                   + "WHERE `id_catag` = ?";
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