/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 27.05.2021 r.
 */
package jwl.DAO;

import java.sql.*;

public interface DAO<T> {  
    
  //-----Connect-----     
    //public void connect() throws SQLException;
  //-----Disconnect-----   
    //public void disconnect() throws SQLException;
    
  //-----CREATE----- 
    public boolean create(T value) throws SQLException;
    
    //public T read() throws SQLException;
  //-----UPDATE-----   
    public boolean update(T value) throws SQLException;
  //-----DELETE-----   
    public boolean delete(int id) throws SQLException;    
}
